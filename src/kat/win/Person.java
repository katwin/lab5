package kat.win;

import java.util.Objects;

public class Person extends Creature {
    Location place;

    public Person (String name) {
        super(name);
    }

    @Override
    public void move(Location place) {
        this.place = place;
        switch (this.place) {
            case FIELD: {
                System.out.println(name + "пришел к друзьям на поле");
                break;
            }
            case REEDS: {
                System.out.println(name + "полез в камыши искать Тигрулю");
                break;
            }
            case PATH: {
                System.out.println(name + " идет по дорожке");
                break;
            }
            case BRIDGE: {
                System.out.println(name + " подошел к мосту");
            }
        }
    }

    void mood() {
        System.out.println("Настроение у него - солнечное и безоблачное");
    }

    void think() {
        switch (place) {
            case PATH: {
                System.out.print("\"Если я встану на нижнюю перекладину перил моста и наклонюсь над рекой, ");
                System.out.print("я узнаю все-все на свете и тогда я расскажу все это Пуху, который пока ");
                System.out.println("еще знает не все на свете,\" - подумал " + name);
                break;
            }
            case BRIDGE: {
                if (getKnowledge().equals("Знает всё на свете")) {
                    System.out.println("Я теперь знаю всё-всё на свете, и я должен рассказать об этом Пуху!");
                } else if ((place.getNoise() == 0 )  || (place.getNoise() == 1)) {
                    System.out.println("Сегодня день, когда нужно что-то сделать. - подумал " + name + ", увидев своих друзей.");
                }
                else {
                    System.out.println("Никаких планов на день. - подумал " + name);
                }
                break;
            }
            default: {
                System.out.println("Никаких планов на день. - подумал " + name);
                break;
            }
        }
    }

    public void know() {
        addKnowledge(" знает, сколько будет дважды девятнадцать");
        if (place.equals(Location.BRIDGE)) {
            if ((place.getNoise() == 0 )  || (place.getNoise() == 1)) {
                addKnowledge(" знает базовую математику");
            } else {
                addKnowledge(" знает все-все на свете");
            }
        }
        System.out.println(name + getKnowledge()[getKnowledge().length - 1]);
    }

    static class mind {
        String dreams;
        String relations;
        void knowWhatInHead() {
            dreams = "хочу больше друзей";
            relations = "хорошие отношения с друзьями, плохие со школой";
            System.out.println(dreams + " , " + relations);

        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return false;
        Animal animal = (Animal) o;
        return this.place == animal.place
                && name == animal.name;
    }

    @Override
    public String toString() {
        return "Person name" + name + "place" + place;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, place);
    }
}
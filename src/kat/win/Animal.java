package kat.win;

import java.util.Objects;

public class Animal extends Creature {
    Location place;

    Animal(String name) {
        super(name);
    }

    public void move(Location place) {
        this.place = place;
        switch (this.place) {
            case FIELD: {
                System.out.println(name + " находится на поле");
                this.place.getNoise();
                break;
            }
            case REEDS: {
                System.out.println(name + " вышел из камышей в поле");
                this.place.getNoise();
                break;
            }
            case PATH: {
                System.out.println(name + " пошел домой по дорожке");
                this.place.getNoise();
                break;
            }
            case BRIDGE: {
                System.out.println(name + " стоит на мосту и ждет Кристофера");
                this.place.getNoise();
                break;
            }
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
        return "Animals name" + name + "place" + place;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, place);
    }
}

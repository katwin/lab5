package kat.win;

import java.util.ArrayList;
import java.util.Objects;

public class Game {
    private ArrayList<Game> players = new ArrayList<>();
    private final int speed = 10;
    private int weight;
    private int size;
    private String playername;

    public ArrayList<Game> getPlayers() {
        return players;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public String getPlayername() {
        return playername;
    }

    void addToGame(Creature creature, int weight, int size) {
        System.out.println(creature + " присоединился к игре ");
        Game game = new Game();
        players.add(game);
        setSize(size);
        setWeight(weight);
        setPlayername(creature.name);
    }

    void ready () throws ZeroMembersException {
        if (players.size()==0) throw new ZeroMembersException("Никто не играет в игру", players.size());
        System.out.println("Количество игроков = " + players.size());
        System.out.println("Скорость течения реки = " + speed);
    }

    class ChanceToWin {
        int chance;
        void knowChance() throws ZeroSizeException {
            try {
                if (size == 0)
                {
                    throw new ZeroSizeException("Палочка не существует", size);
                }
                else{
                    System.out.println("Палочка подходит для игры");
                }
                this.chance = (size + (weight/(size*4) + speed)*3);
            }
            catch (ZeroSizeException e) {
                System.out.println("Возникло исключение, " + e.getNum());
            }
            System.out.println("Коэффициент победы = " + this.chance);
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
        Game game = (Game) o;
        return this.weight == game.weight
                && size == game.size;
    }

    @Override
    public String toString() {
        return "Game name - Пустяки ";
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, size);
    }

}
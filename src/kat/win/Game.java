package kat.win;

import java.util.ArrayList;
import java.util.Objects;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();

    private final int speed = 10;
    private int weight;
    private int size;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public void add(Player player){
        players.add(player);
    }

    void addToGame(String name, int weight, int size) {
        //System.out.println(name + " присоединился к игре ");
        Player player = new Player();
        player.setName(name);
        player.setSize(size);
        player.setWeight(weight);
        players.add(player);
        setSize(size);
        setWeight(weight);
    }

    void addToGame(Player player) {
        //player.setSize(size);
        //player.setWeight(weight);
        players.add(player);
        //setSize(size);
        //setWeight(weight);
    }

    void ready () throws ZeroMembersException {
        //if (players.size()==0) throw new ZeroMembersException("Никто не играет в игру", players.size());
        //System.out.println("Количество игроков = " + players.size());
        //System.out.println("Скорость течения реки = " + speed);
    }

    class ChanceToWin {
        int chance;
        void knowChance() throws ZeroSizeException {
            try {
                if (size == 0)
                {
                    throw new ZeroSizeException("Палочка не существует", size);
                }
                this.chance = (size + (weight/(size*4) + speed)*3);
            }
            catch (ZeroSizeException e) {
                System.out.println("Возникло исключение, " + e.getNum());
            }
            //System.out.println("Шанс победить = " + this.chance);
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
    public int hashCode() {
        return Objects.hash(weight, size);
    }

}
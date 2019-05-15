package kat.win;

public class Player {
    private int size;
    private int weight;
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) return false;
        return size == ((Player) obj).getSize() && weight == ((Player) obj).getWeight() && name.equals(((Player) obj).getName());
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }
}
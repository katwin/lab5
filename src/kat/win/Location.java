package kat.win;

public enum  Location {
    FIELD(0),
    REEDS(1),
    PATH(2),
    BRIDGE(3);
    private final int noise;
    public int getNoise() {
        return noise;
    }

    Location(int noise) {
        this.noise = noise;
    }
}
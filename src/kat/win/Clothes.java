package kat.win;

public class Clothes {
    private String type;
    private String size;
    public Clothes(String type, String size) {
        this.type=type;
        this.size=size;
    }
    public String getSize(){
        return size;
    }

    public String getType() {
        return type;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }
}

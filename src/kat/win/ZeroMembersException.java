package kat.win;

public class ZeroMembersException extends Exception {
    private int num;
    public int getNum(){return num;}
    public ZeroMembersException(String message, int num) {
        System.out.println(message);
        this.num=num;
    }
}
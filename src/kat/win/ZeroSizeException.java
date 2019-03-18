package kat.win;

public class ZeroSizeException extends RuntimeException {
    private int num;

    public int getNum() {
        return num;
    }

    ZeroSizeException(String message, int num) {
        System.out.println(message);
    }
}
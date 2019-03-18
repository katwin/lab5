package kat.win;

import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Main {
    static Animal Pooh = new Animal("Винни");
    static Animal Ia = new Animal("Иа");
    static Animal Tiger = new Animal("Тигруля");
    static Person Chris = new Person("Кристофер Робин");
    private static String autosaveFilename = "save.xml";

    public static void main(String[] args) {
        actions();
        game();
    }

    private static void actions() {
        Pooh.move(Location.FIELD);
        Pooh.ask("Как тебя зовут?");
        System.out.println(Ia.answer("Меня зовут..."));
        Tiger.move(Location.REEDS);
        Chris.move(Location.PATH);
        Chris.mood();
        Chris.think();
        Chris.move(Location.BRIDGE);
        Chris.think();
        Chris.know();
    }

    private static void game() {
        Game game = new Game();
        Game.ChanceToWin chance = game.new ChanceToWin();
        game.addToGame(Pooh, 10, 0);
        save(Pooh, );
        chance.knowChance();
        game.addToGame(Tiger, 30, 20);
        save(Tiger, );
        chance.knowChance();
        try {
            game.ready();
        }
        catch (ZeroMembersException e) {
            System.out.println(e.getNum());
        }
    }

    private static void save(Game game) {
        Runtime.getRuntime().addShutdownHook( new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("\nАвтосохранение...");
                    writer(game, new OutputStreamWriter(new FileOutputStream(autosaveFilename), StandardCharsets.UTF_8));
                    System.out.println("Сохранено в файл " + autosaveFilename);
                } catch (IOException e) {
                    System.out.println("Не удалось выполнить автосохранение: " + e.getMessage());
                }
            }
        }));
    }

    private static void writer(Game game, OutputStreamWriter oswriter) throws IOException {
        oswriter.write("<?xml version=\"1.0\"?>\n");
        oswriter.write("<players>\n");
        for (Game g: game.getPlayers()) {
            oswriter.write("player\n");
            oswriter.write("<name>" + g.getPlayername() + "</name>\n");
            oswriter.write("<weight>" + g.getWeight() + "</weight>\n");
            oswriter.write("<size>" + g.getSize() + "</size>\n");
            oswriter.write("  </player>\n");
        }
        oswriter.write("</players>\n");
        oswriter.flush();
    }
}

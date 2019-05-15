package kat.win;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

public class Main {

    static Animal Pooh = new Animal("Винни");
    static Animal Ia = new Animal("Иа");
    static Animal Tiger = new Animal("Тигруля");
    static Person Chris = new Person("Кристофер Робин");
    static Date date = null;
    static Game game = new Game();
    static boolean multiline = false;

    public static void main(String[] args) {
        Scanner asshole = new Scanner(System.in);
        System.out.println("Введите имя файла");
        String butt = asshole.next() + ".xml";
        DataController.AUTOSAVE=butt;
        //Runtime.getRuntime().addShutdownHook(new Thread(() -> DataController.save(game)));
        date = Calendar.getInstance().getTime();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String content = getFileContent();
            if (content.equals("")) {
                content = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                        "<players>\n" +
                        "</players>";
            }
            DataController.load(content, game);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.out.println("Произошёл троллинг. Исключение " + e.getClass());
            System.exit(0);
        }
        //actions();
        game();

        while (true) {
            try {
                System.out.print("> ");
                String command;
                if (multiline) {
                    command = customCommandSplit(getMultilineCommand(reader));

                } else {
                    command = reader.readLine();
                }
                for (String comm : command.split("\n")) {
                    processCommand(comm);
                }
            } catch (IOException e) {
                System.out.println("Не удалось прочитать стандартный поток вввода: " + e.getMessage());
        }   catch (NullPointerException e) {
                System.exit(0);
            }
        }
    }

    private static String customCommandSplit(String command) {
        char[] chars = command.toCharArray();
        StringBuilder builder = new StringBuilder();
        boolean inParam = false;
        for (char ch : chars) {
            if (ch == '{')
                inParam = true;
            if (ch == '}')
                inParam = false;
            if (inParam && ch == '\n')
                continue;
            builder.append(ch);
        }
        return builder.toString();
    }

     private static String getMultilineCommand(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String str;
        do {
            str = reader.readLine();
            builder.append(str + "\n");
        } while (!str.endsWith(";"));
        return builder.toString().replace(";", "");
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
        //Game.ChanceToWin chance = game.new ChanceToWin();
        //game.addToGame(Pooh.name, 10, 2);
        //chance.knowChance();
        //game.addToGame(Tiger.name, 30, 20);
        //game.addToGame(Chris.name, 25, 15);
        //chance.knowChance();
        try {
            game.ready();
            DataController.save(game);
        }
        catch (ZeroMembersException e) {
            //System.out.println(e.getNum());
        }
    }

    private static String getFileContent() throws IOException {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(DataController.AUTOSAVE));
             InputStreamReader reader = new InputStreamReader(inputStream)
        ) {
            StringBuilder fileContent = new StringBuilder();
            int current;
            do {
                current = reader.read();
                if (current != -1)
                    fileContent.append((char)current);
            } while (current != -1);

            return fileContent.toString();
        }
    }

    private static void showPlayers () {
        if (game.getPlayers().size() != 0) {
            System.out.println("Игроки: ");
            for (int i = 0; i < game.getPlayers().size(); i++) {
                System.out.println(
                        "Игрок №" + (i+1) + ": \n" +
                                "   Имя: " + game.getPlayers().get(i).getName() + "\n" +
                                "   Длина палочки: " + game.getPlayers().get(i).getSize() + "\n" +
                                "   Вес палочки: " + game.getPlayers().get(i).getWeight() + "\n"
                );
            }
        } else {
            System.out.println("Никто не хочет с тобой играть :с");
        }
    }

    private static void removeFirstPlayer() {
        if (game.getPlayers().size() != 0) game.getPlayers().remove(0);
    }

    private static void info() {
        game.getPlayers().size();
        game.getPlayers().getClass().getName();
        System.out.println(
                "Количество игроков: " + game.getPlayers().size() + "\n" +
                        "Тип коллекции: " + game.getPlayers().getClass().getName() + "\n" +
                        "Дата создания: " + date.toString() + "\n"
        );
    }

    private static void addPlayer(Player player) {
        try {
            if (player != null) {
                game.addToGame(player);
            } else {
                System.out.println("Нельзя так издеваться, тут же пусто!");
            }
        } catch (NumberFormatException | NoSuchElementException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Такой себе json-формат у вас, конечно.");
        }
    }

    private static Player jsonToPlayer(String json) {
        try {
            if (json == null) { return null; }
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Player player = gson.fromJson(json, Player.class);
            return player;
        } catch (Exception e) {
            System.out.println("Такой себе json-формат у вас, конечно.");
        }
        return null;
    }

    private static void removePlayer(String json) {
        Player player=jsonToPlayer(json);
        for (int i = 0; i < game.getPlayers().size(); i++) {
            if (game.getPlayers().get(i).equals(player)) {
                game.getPlayers().remove(i);
            }
        }
    }

    private static void addIfMax(String json) {
        try {
            Player player=jsonToPlayer(json);
            Player player2 = findMax();
            if(player2 == null) {
                game.addToGame(player.getName(), player.getWeight(), player.getSize());
            } else if (player != null) {
                if (player.getWeight() > player2.getWeight()) {
                    game.addToGame(player.getName(), player.getWeight(), player.getSize());
                }
                else {
                    System.out.println("Вы недостаточно большой.");
                }
            }
            else{
                System.out.println("Некорректные данные.");
            }
        }  catch (NullPointerException e) {
            System.out.println("Нельзя так издеваться, тут же пусто!");
        } catch (NumberFormatException | NoSuchElementException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Такой себе json-формат у вас, конечно.");
        }
    }

    private static void addIfMin(String json) {
        try {
            Player player=jsonToPlayer(json);
            Player player2 = findMin();
            if (player2 == null) {
                game.addToGame(player.getName(), player.getWeight(), player.getSize());
            } else if (player != null) {
                if (player.getWeight() < player2.getWeight()) {
                    game.addToGame(player.getName(), player.getWeight(), player.getSize());
                } else {
                    System.out.println("Вы недостаточно маленький.");
                }
            } else {
                System.out.println("Некорректные данные.");
            }
        } catch (NullPointerException e) {
            System.out.println("Нельзя так издеваться, тут же пусто!");
        } catch (NumberFormatException | NoSuchElementException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Такой себе json-формат у вас, конечно.");
        }
    }

    private static Player findMax() {
        Player player = null;
        if (game.getPlayers().size() > 0) {
            for (Player p: game.getPlayers()) {
                if (player == null) {
                    player = p;
                }
                if (p.getWeight() > player.getWeight()) {
                    player = p;
                }
            }
        }
        return player;
    }

    private static Player findMin() {
        Player player = null;
        if (game.getPlayers().size() > 0) {
            for (Player p: game.getPlayers() ) {
                if (player == null) {
                    player = p;
                }
                if (p.getWeight() < player.getWeight()) {
                    player = p;
                }
            }
        }
        return player;
    }

    private static Command extractCommand(String query) {
        int spaceIndex = query.indexOf(' ');
       // int enterIndex = query.indexOf('\n');
        if (spaceIndex == -1)
            return new Command(query, null);
        else
            return new Command(query.substring(0, spaceIndex), query.substring(spaceIndex+1));
    }

    private static class Command {
        String name;
        String argument;

        Command (String name, String argument) {
            this.name = name;
            this.argument = argument;
        }
    }

    private static String  processCommand(String command) {
        if (command == null)
            System.exit(0);
        //String[] data = command.split(" ");
        if (game == null)
            return "Ошибка обработки запроса: целевой объект не указан";
        Command command1 = extractCommand(command);
        if (command1.name.isEmpty())
            return "";
        if (command1.argument != null) {
            command1.argument = command1.argument.replaceAll(" ", "");
            command1.argument = command1.argument.replaceAll("\n", "");
            command1.argument = command1.argument.replaceAll("\t", "");
        }
        switch (command1.name) {
            case "info": {
                info();
                break;
            }
            case "remove_first": {
                removeFirstPlayer();
                break;
            }
            case "show": {
                showPlayers();
                break;
            }
            case "add": {
                addPlayer(jsonToPlayer(command1.argument));
                break;
            }
            case "remove": {
                removePlayer(command1.argument);
                break;
            }
            case "add_if_max": {
                addIfMax(command1.argument);
                break;
            }
            case "add_if_min": {
                addIfMin(command1.argument);
                break;
            }
            case "multiline":
                multiline = !multiline;
                return "Многострочные команды " + (multiline ? "включены. Используйте '}' для завешения команды." : "выключены");
            case "help": {
                System.out.println(" \n" +
                        "info - Показывает информацию о коллекции\n" +
                        "remove_first - Удаляет первого игрока\n" +
                        "show - Показывает информацию об игроках\n" +
                        "add - Добавляет игрока\n" +
                        "remove - Удаляет игрока\n" +
                        "add_if_max -  Добавляет игрока в игру, если его палочка тяжелее остальных палочек\n" +
                        "add_if_min - Добавляет игрока в игру, если его палочка легче остальных палочек\n"  +
                        "Пример ввода игрока:\n" + "{\"name\":\"Lupa\",\"size\":17,\"weight\":24}");
                break;
            }
            default: {
                System.out.println("Вы меня, конечно, извините...");
                break;
            }
        }
        return "";
    }

}

package kat.win;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class Main {
    private static XmlController xmlController;
    private static JsonController jsonController = new JsonController();
    private static Game game = new Game();
    static boolean multiline = false;
    /**
     * Основной метод класса.
     * Отвечает за работу консоли, переключение режима вводимых команд, запускает сохранение/загрузку из файла.
     * @param args
     */
    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            String butt = args[0] + ".xml";
            xmlController = new XmlController(butt);
        } else {
            xmlController = new XmlController("autosave");
        }

        try {
            xmlController.load(xmlController.getFileContent(), game);
            xmlController.save(game);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.out.println("Произошёл троллинг. Исключение " + e.getClass());
            System.exit(-1);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print("> ");
                String command;
                if (multiline) {
                    command = customCommandSplit(getMultilineCommand(reader));
                } else {
                    command = reader.readLine();
                }
                if (command == null)
                    System.exit(0);
                for (String comm : command.split("\n")) {
                    processCommand(comm);
                }
            } catch (Exception e) {
                System.out.println("Что-то пошло не так");
            }
        }
    }

    /**
     * Метод для определения начала/конца аргумента в json-формате
     * @param command
     * @return
     */
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

    /**
     * Обрабатывает команды, вводимые в режиме multiline.
     * @param reader
     * @return
     * @throws IOException
     */
    private static String getMultilineCommand(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String str;
        do {
            str = reader.readLine();
            builder.append(str + "\n");
        } while (!str.endsWith(";"));
        return builder.toString().replace(";", "");
    }

    /**
     *
     * @param query
     * @return
     */
    private static Command extractCommand(String query) {
        int spaceIndex = query.indexOf(' ');
        if (spaceIndex == -1)
            return new Command(query, null);
        else
            return new Command(query.substring(0, spaceIndex), query.substring(spaceIndex + 1));
    }

    /**
     * Разделяет полученную строку на команду и ее аргумент.
     */
    private static class Command {
        String name;
        String argument;
        Command(String name, String argument) {
            this.name = name;
            this.argument = argument;
        }
    }

    /**
     * Метод обработки вводимых пользователем данных в консоль, центр управления командами и "общения" с пользователем.
     * @param command
     * @return
     */
    private static String processCommand(String command) {
        if (command == null)
            System.exit(0);
        Command command1 = extractCommand(command);
        if (command1.name.isEmpty())
            return "";
        if (command1.argument != null) {
            command1.argument = command1.argument.replaceAll(" ", "");
            command1.argument = command1.argument.replaceAll("\n", "");
            command1.argument = command1.argument.replaceAll("\t", "");
        }
        switch (command1.name) {
            case "info":
                game.info();
                break;

            case "remove_first":
                game.removeFirstPlayer();
                break;

            case "show":
                game.showPlayers();
                break;

            case "add":
                game.addToGame(jsonController.jsonToPlayer(command1.argument));
                break;

            case "remove":
                game.removePlayer(jsonController.jsonToPlayer(command1.argument));
                break;

            case "add_if_max":
                game.addIfMax(jsonController.jsonToPlayer(command1.argument));
                break;

            case "add_if_min":
                game.addIfMin(jsonController.jsonToPlayer(command1.argument));
                break;

            case "multiline":
                multiline = !multiline;
               System.out.println("Многострочные команды " + (multiline ? "включены. Используйте '}' для завешения команды." : "выключены"));
                break;
            case "help":
                System.out.println(" \n" +
                        "info - Показывает информацию о коллекции\n" +
                        "remove_first - Удаляет первого игрока\n" +
                        "show - Показывает информацию об игроках\n" +
                        "add - Добавляет игрока\n" +
                        "remove - Удаляет игрока\n" +
                        "add_if_max -  Добавляет игрока в игру, если его палочка тяжелее остальных палочек\n" +
                        "add_if_min - Добавляет игрока в игру, если его палочка легче остальных палочек\n" +
                        "Пример ввода игрока:\n" + "{\"name\":\"Lupa\",\"size\":17,\"weight\":24}");
                break;

            default:
                System.out.println("Вы меня, конечно, извините...");
                break;

        }
        return "";
    }

}

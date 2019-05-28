package kat.win;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Класс, отвечающий за обработку json.
 */
public class JsonController {

    /**
     * Обрабатывает данные из формата json, создает игрока и задает обработанные данные его как поля.
     * @param json
     * @return
     */
    public Player jsonToPlayer(String json) {
        if (json == null) {
            System.out.println("Не задан json");
            return null;
        }
        Player player;
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            player = gson.fromJson(json, Player.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Такой себе json-формат у вас, конечно.");
            return null;
        }
        if (player.getName() == null) {
            System.out.println("Не задано имя");
            return null;
        }
        if (player.getWeight() <= 0) {
            System.out.println("Не задан вес");
            return null;
        }
        if (player.getSize() <= 0) {
            System.out.println("Не задан размер");
            return null;
        }
        return player;
    }
}

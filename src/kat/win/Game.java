package kat.win;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Класс, отвечающий за игру, в которой участвуют игроки-элементы коллекции.
 * Хранит методы, имеющие отношение непосредственно к игрокам и их характеристикам.
 */
public class Game {
    private List<Player> players = new ArrayList<>();
    private Date date = new Date();

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Создает игрока, присваивает ему входные характеристики.
     * @param name
     * @param weight
     * @param size
     */
    void addToGame(String name, int weight, int size, Clothes clothes) {
        Player player = new Player();
        player.setName(name);
        player.setSize(size);
        player.setWeight(weight);
        player.setClothes(clothes);
        addToGame(player);
    }

    /**
     * Проверяет существование игрока и отправляет его в метод addSilent.
     * @param player
     */
    void addToGame(Player player) {
        if (player == null)
            return;
        System.out.println(player.getName() + " присоединился к игре ");
        addSilent(player);
    }

    /**
     * Добавляет игрока в коллекцию и сортирует ее.
     * @param player
     */
    public void addSilent(Player player) {
        if (player == null)
            return;
        players.add(player);
        Collections.sort(players);
    }

    /**
     * Удаляет игрока из коллекции.
     * @param player
     */
    public void removePlayer(Player player) {
        for (int i = 0; i < getPlayers().size(); i++) {
            if (getPlayers().get(i).equals(player)) {
                getPlayers().remove(i);
            }
        }
    }

    /**
     * Добавляет игрока в коллекцию, если вес палочки игрока больше максимального веса палочки в игре.
     * @param player
     */
    public void addIfMax(Player player) {
        Player player2 = findMax();
        if (player2 == null) {
            addToGame(player.getName(), player.getWeight(), player.getSize(), player.getClothes());
        } else if (player != null) {
            if (player.getWeight() > player2.getWeight()) {
                addToGame(player.getName(), player.getWeight(), player.getSize(), player.getClothes());
            } else {
                System.out.println("Вы недостаточно большой.");
            }
        } else {
            System.out.println("Некорректные данные.");
        }
    }

    /**
     * Добавляет игрока в коллекцию, если вес палочки игрока меньше минимального веса палочки в игре.
     * @param player
     */
    public void addIfMin(Player player) {
        Player player2 = findMin();
        if (player2 == null) {
            addToGame(player.getName(), player.getWeight(), player.getSize(), player.getClothes());
        } else if (player != null) {
            if (player.getWeight() < player2.getWeight()) {
                addToGame(player.getName(), player.getWeight(), player.getSize(), player.getClothes());
            } else {
                System.out.println("Вы недостаточно маленький.");
            }
        } else {
            System.out.println("Некорректные данные.");
        }
    }

    /**
     * Отображает отсортированный список игроков-элементов коллекции и их характеристики.
     */
    public void showPlayers() {
        if (getPlayers().size() != 0) {
            System.out.println("Игроки: ");
            for (int i = 0; i < getPlayers().size(); i++) {
                System.out.println(
                        "Игрок №" + (i + 1) + ": \n" +
                                "   Имя: " + getPlayers().get(i).getName() + "\n" +
                                "   Длина палочки: " + getPlayers().get(i).getSize() + "\n" +
                                "   Вес палочки: " + getPlayers().get(i).getWeight() + "\n" +
                                "   Тип одежды: " + getPlayers().get(i).getClothes().getType() + "\n" +
                                "   Размер одежды: " + getPlayers().get(i).getClothes().getSize()
                );
            }
        } else {
            System.out.println("Никто не хочет с тобой играть :с");
        }
    }

    /**
     * Удаляет первого игрока коллекции.
     */
    public void removeFirstPlayer() {
        if (getPlayers().size() != 0) getPlayers().remove(0);
    }

    /**
     * Отображает основную информацию об игре(коллекции).
     */
    public void info() {
        System.out.println(
                "Количество игроков: " + getPlayers().size() + "\n" +
                        "Тип коллекции: " + getPlayers().getClass().getName() + "\n" +
                        "Дата создания: " + date.toString() + "\n"
        );
    }

    /**
     * Находит игрока, вес палочки которого является максимальным среди остальных.
     * @return
     */
    private Player findMax() {
        Player player = null;
        if (getPlayers().size() > 0) {
            for (Player p : getPlayers()) {
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

    /**
     * Находит игрока, вес палочки которого является минимальным среди остальных.
     * @return
     */
    private Player findMin() {
        Player player = null;
        if (getPlayers().size() > 0) {
            for (Player p : getPlayers()) {
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
}
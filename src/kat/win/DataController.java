package kat.win;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DataController {

    public static String AUTOSAVE;
    public static void save(Game game) {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("\nАвтосохранение...");
                    writer(game, new OutputStreamWriter(new FileOutputStream(AUTOSAVE), StandardCharsets.UTF_8));
                    System.out.println("Сохранено в файл " + AUTOSAVE);
                } catch (IOException e) {
                    System.out.println("Не удалось выполнить автосохранение: " + e.getMessage());
                }
            }
        }));
    }

    private static void writer(Game game, OutputStreamWriter oswriter) throws IOException {
        oswriter.write("<?xml version=\"1.0\"?>\n");
        oswriter.write("<players>\n");
        for (Player player : game.getPlayers()) {
            oswriter.write("  <player>\n");
            oswriter.write("    <name>" + player.getName() + "</name>\n");
            oswriter.write("    <weight>" + player.getWeight() + "</weight>\n");
            oswriter.write("    <size>" + player.getSize() + "</size>\n");
            oswriter.write("  </player>\n");
        }
        oswriter.write("</players>\n");
        oswriter.flush();
        oswriter.close();
    }


    public static void load(String xml, Game game) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        Element root = document.getDocumentElement();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node current = nodes.item(i);
            if (current.getNodeType() != Node.TEXT_NODE) {
                NodeList properties = current.getChildNodes();
                Integer size = null, weight = null;
                String name = null;
                for (int j = 0; j < properties.getLength(); j++) {
                    Node property = properties.item(j);
                    switch (property.getNodeName()) {
                        case "name": {
                            name = property.getTextContent();
                            break;
                        }
                        case "weight": {
                            try {
                                weight = Integer.parseInt(property.getTextContent());
                            } catch (NumberFormatException nfe) {
                                throw new SAXException("<weight> в <player> должен хранить целое число");
                            }
                            break;
                        }
                        case "size": {
                            try {
                                size = Integer.parseInt(property.getTextContent());
                            } catch (NumberFormatException nfe) {
                                throw new SAXException("<size> в <player> должен хранить целое число");
                            }
                            break;
                        }

                    }
                }
                if (size == null) {
                    throw new SAXException("<size> обязателен, но не указан в <player>");
                }
                if (weight == null) {
                    throw new SAXException("<weight> обязателен, но не указан в <player>");
                }
                Player player = new Player();
                if (size != null) player.setSize(size);
                if (weight != null) player.setWeight(weight);
                if (name != null) player.setName(name);
                game.add(player);
            }
        }
    }

}
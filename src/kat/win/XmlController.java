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

/**
 * Класс, отвечающий за XML-файл, его загрузку, сохранение и запись.
 */
public class XmlController {
    private String filename;

    public XmlController(String filename) {
        this.filename = filename;
    }

    /**
     * Сохраняет данные в файл.
     * @param game
     */
    public void save(Game game) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("\nАвтосохранение...");
                    writer(game, new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8));
                    System.out.println("Сохранено в файл " + filename);
                } catch (IOException e) {
                    System.out.println("Не удалось выполнить автосохранение: " + e.getMessage());
                }
            }
        }));
    }

    /**
     * Заполняет XML-файл.
     * @return
     * @throws IOException
     */
    public String getFileContent() throws IOException {
        File file = new File(filename);
        if (!file.exists())
            return getDefaultXml("");
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
             InputStreamReader reader = new InputStreamReader(inputStream)
        ) {
            StringBuilder fileContent = new StringBuilder();
            int current;
            do {
                current = reader.read();
                if (current != -1)
                    fileContent.append((char) current);
            } while (current != -1);
            return getDefaultXml(fileContent.toString());
        }
    }

    /**
     * Обрабатывает данные перед записью в XML-файл.
     * Если файл пуст, записывает кодировку и тег начала/конца коллекции.
     * @param content
     * @return
     */
    private String getDefaultXml(String content) {
        if (content.isEmpty()) {
            content = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<players>\n" +
                    "</players>";
        }
        return content;
    }

    /**
     * Записывает (добавляет) игроков в файл.
     * @param game
     * @param oswriter
     * @throws IOException
     */
    private void writer(Game game, OutputStreamWriter oswriter) throws IOException {
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

    /**
     * Загружает игрока в коллекцию.
     * @param xml
     * @param game
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public void load(String xml, Game game) throws ParserConfigurationException, IOException, SAXException {
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
                if (name == null) {
                    throw new SAXException("<name> обязателен, но не указан в <player>");
                }
                if (size == null) {
                    throw new SAXException("<size> обязателен, но не указан в <player>");
                }
                if (weight == null) {
                    throw new SAXException("<weight> обязателен, но не указан в <player>");
                }
                Player player = new Player();
                player.setSize(size);
                player.setWeight(weight);
                player.setName(name);
                game.addSilent(player);
            }
        }
    }

}
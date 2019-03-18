package kat.win;

class HoosegowStateController {
    static void saveState(Hoosegow hoosegow, OutputStreamWriter writer) throws IOException {
        writer.write("<?xml version=\"1.0\"?>\n");
        writer.write("<state>\n");
        writer.write("  <timestamp>" + hoosegow.getCreatedDate().getTime() + "</timestamp>\n");
        for (Creature creature : hoosegow.getCollection()) {
            writer.write("  <creature>\n");
            writer.write("    <x>" + creature.getX() + "</x>\n");
            writer.write("    <y>" + creature.getY() + "</y>\n");
            writer.write("    <width>" + creature.getWidth() + "</width>\n");
            writer.write("    <height>" + creature.getHeight() + "</height>\n");
            writer.write("    <name>" + creature.getName() + "</name>\n");
            writer.write("  </creature>\n");
        }
        writer.write("</state>\n");
        writer.flush();
    }
}
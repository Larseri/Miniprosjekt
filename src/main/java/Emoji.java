import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.*;

public class Emoji {

    public static String emoji;
    final public static String coffee = "☕";
    final public static String star = "☆";
    final public static String faceMask = "\uD83D\uDE37";
    final public static String virus = "\uD83D\uDC79";
    final public static String antibac = "🧴";
    public static Random r = new Random();

    public static Position virusPosition;
    public static List<Position> virusPositionList = new ArrayList<>();
    public static Map<String, String> emojiMap = new HashMap<>();

    public Emoji() {
    }

    public static String getEmoji() {
        return emoji;
    }

    public static void setEmoji(String emoji) {
        Emoji.emoji = emoji;
    }

    public static String getCoffee() {
        return coffee;
    }

    public static String getStar() {
        return star;
    }

    public static String getFaceMask() {
        return faceMask;
    }

    public static String getVirus() {
        return virus;
    }

    public static String getAntibac() {
        return antibac;
    }

    public static void initializeEmoji(Terminal terminal, int count) throws IOException {
        if (count == 5 || count % 50 == 0) {
            initializeVirus(terminal);
        } else if (count % 20 == 0) {
            initializeCoffee(terminal);
        } else if (count % 30 == 0) {
            initializeFaceMask(terminal);
            initializeAntibac(terminal);
        } else if (count % 10 == 0) {
            initializeStar(terminal);
        }
    }

    public static void initializeVirus(Terminal terminal) throws IOException {
        boolean virusRun = true;
        while (virusRun) {
            virusPosition = new Position(r.nextInt(80), r.nextInt(24));
            virusRun = Wall.checkCollisionWithWall(virusPosition.getX(), virusPosition.getY());
        }
        emojiMap.put(virusPosition.getX() + "," + virusPosition.getY(), "virus");
        virusPositionList.add(virusPosition);
        terminal.setCursorPosition(virusPosition.getX(), virusPosition.getY());
        terminal.putString(Emoji.getVirus());
        terminal.flush();
    }

    public static void initializeCoffee(Terminal terminal) throws IOException {
        Position tempPos = null;
        boolean tempRun = true;
        while (tempRun) {
            tempPos = new Position(r.nextInt(80), r.nextInt(24));
            tempRun = Wall.checkCollisionWithWall(tempPos.getX(), tempPos.getY());
        }
        emojiMap.put(tempPos.getX() + "," + tempPos.getY(), "coffee");
        terminal.setCursorPosition(tempPos.getX(), tempPos.getY());
        terminal.putString(Emoji.getCoffee());
        terminal.flush();
    }

    public static void initializeFaceMask(Terminal terminal) throws IOException {
            Position tempPos = null;
            boolean tempRun = true;
            while (tempRun) {
                tempPos = new Position(r.nextInt(80), r.nextInt(24));
                tempRun = Wall.checkCollisionWithWall(tempPos.getX(), tempPos.getY());
            }
        emojiMap.put(tempPos.getX() + "," + tempPos.getY(), "faceMask");
            terminal.setCursorPosition(tempPos.getX(), tempPos.getY());
            terminal.putString(Emoji.getFaceMask());
            terminal.flush();
    }

    public static void initializeAntibac(Terminal terminal) throws IOException {
        Position tempPos = null;
        boolean tempRun = true;
        while (tempRun) {
            tempPos = new Position(r.nextInt(80), r.nextInt(24));
            tempRun = Wall.checkCollisionWithWall(tempPos.getX(), tempPos.getY());
        }
        emojiMap.put(tempPos.getX() + "," + tempPos.getY(), "antibac");
        terminal.setCursorPosition(tempPos.getX(), tempPos.getY());
        terminal.putString(Emoji.getAntibac());
        terminal.flush();
    }

    public static void initializeStar(Terminal terminal) throws IOException {
        Position tempPos = null;
        boolean tempRun = true;
        while (tempRun) {
            tempPos = new Position(r.nextInt(80), r.nextInt(24));
            tempRun = Wall.checkCollisionWithWall(tempPos.getX(), tempPos.getY());
        }
        emojiMap.put(tempPos.getX() + "," + tempPos.getY(), "star");
        terminal.setCursorPosition(tempPos.getX(), tempPos.getY());
        terminal.putString(Emoji.getStar());
        terminal.flush();
    }

    public static void moveVirus(Terminal terminal, int count, int playerX, int playerY) throws IOException {
        if (count >= 5) {
            for (int i = 0; i < virusPositionList.size(); i++) {
                boolean tempRun = true;
                Position oldPos = new Position(virusPositionList.get(i).getX(), virusPositionList.get(i).getY());
                while (tempRun) {
                    virusPositionList.get(i).setX(oldPos.getX() - 1 + r.nextInt(3));
                    virusPositionList.get(i).setY(oldPos.getY() - 1 + r.nextInt(3));
                    tempRun = Wall.checkCollisionWithWall(virusPositionList.get(i).getX(), virusPositionList.get(i).getY());
                }
                terminal.setCursorPosition(oldPos.getX(), oldPos.getY());
                terminal.putCharacter(' ');
                terminal.setCursorPosition(virusPositionList.get(i).getX(), virusPositionList.get(i).getY());
                terminal.putString(Emoji.getVirus());
                terminal.flush();
                emojiMap.remove(oldPos.getX() + "," + oldPos.getY(), "virus");
                emojiMap.put(virusPositionList.get(i).getX() + "," + virusPositionList.get(i).getY(), "virus");
            }
        }

    }

    public static String checkEmojiHit(Terminal terminal, int playerX, int playerY) {
        boolean hit = emojiMap.containsKey(playerX + "," + playerY);
        if (hit) {
            String tempString = emojiMap.get(playerX + "," + playerY);
            emojiMap.remove(playerX + "," + playerY);
            return tempString;
        }
        return "none";
    }

}

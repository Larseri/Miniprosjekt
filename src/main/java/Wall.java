import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Wall {

    public static List<Position> wall = new ArrayList<>();
    public static Position startPos;
    public static Position endPos;
    final static char block = '\u2588';
    public static List<Position> allWalls = new ArrayList<>();

    public static void drawAllWalls(Terminal terminal) throws IOException {
        List<Position> startPos = new ArrayList<>();
        List<Position> endPos = new ArrayList<>();

        startPos.add(new Position(10,5));
        endPos.add(new Position(30, 5));

        startPos.add(new Position(50, 5));
        endPos.add(new Position(70, 5));

        startPos.add(new Position(10, 10));
        endPos.add(new Position(30, 10));

        startPos.add(new Position(50, 10));
        endPos.add(new Position(70, 10));

        startPos.add(new Position(10, 15));
        endPos.add(new Position(30, 15));

        startPos.add(new Position(50, 15));
        endPos.add(new Position(70, 15));

        startPos.add(new Position(10, 20));
        endPos.add(new Position(30, 20));

        startPos.add(new Position(50, 20));
        endPos.add(new Position(70, 20));

        startPos.add(new Position(10, 10));
        endPos.add(new Position(30, 10));

        startPos.add(new Position(50, 10));
        endPos.add(new Position(70, 10));

        startPos.add(new Position(35, 3));
        endPos.add(new Position(45, 3));

        startPos.add(new Position(35, 8));
        endPos.add(new Position(45, 8));

        startPos.add(new Position(35, 13));
        endPos.add(new Position(45, 13));

        startPos.add(new Position(35, 18));
        endPos.add(new Position(45, 18));

        createAllWalls(startPos, endPos);

        for (Position pos : allWalls) {
            terminal.setCursorPosition(pos.getX(), pos.getY());
            terminal.putCharacter(block);
        }
    }

    public static void createAllWalls(List<Position> startPositions, List<Position> endPositions) {
        for (int i = 0; i < startPositions.size(); i++) {
            List<Position> tempWall = createWall(startPositions.get(i), endPositions.get(i));
            allWalls.addAll(tempWall);
        }
    }

    public static List<Position> createWall(Position startPos, Position endPos) {
        if (startPos.getY() == endPos.getY()) {
            for (int i = startPos.getX(); i <= endPos.getX(); i++) {
                wall.add(new Position(i, startPos.getY()));
            }
        } else if (startPos.getX() == endPos.getX()) {
            for (int i = startPos.getY(); i <= endPos.getY(); i++) {
                wall.add(new Position(startPos.getX(), i));
            }
        }
        return wall;
    }

    public static boolean checkCollisionWithWall(int x, int y) {
        boolean collision = false;
        for (Position p : allWalls) {
            if (p.getX() == x && p.getY() == y) {
                collision = true;
            }
        }
        return collision;
    }

}

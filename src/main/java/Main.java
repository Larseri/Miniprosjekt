import com.googlecode.lanterna.graphics.StyleSet;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.io.IOException;
import java.util.*;



public class Main {

    public static int playerX;
    public static int playerY;

    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setTerminalEmulatorFontConfiguration(SwingTerminalFontConfiguration.getDefaultOfSize(20));
        Terminal terminal = defaultTerminalFactory.createTerminal();
        Wall.drawAllWalls(terminal);
        terminal.flush();
        terminal.setCursorVisible(false);


        KeyStroke keyStroke;
        KeyType type;
        Character c;

        boolean continueReadingInput = true;

        int sizeX = terminal.getTerminalSize().getColumns();
        int sizeY = terminal.getTerminalSize().getRows();
        playerX = sizeX / 2;
        playerY = sizeY / 2;
        int oldPlayerX;
        int oldPlayerY;
        final char player = '\u26F9';

        int count = 0;
        int points = 0;
        int boozt = 0;
        boolean slow = false;
        int slowCount = 0;

        Position[] explosion = new Position[8];
        Position[] oldExplosion = new Position[8];

        terminal.setCursorPosition(playerX, playerY);
        terminal.putCharacter(player);
        terminal.flush();
        terminal.setCursorVisible(false);

        while (continueReadingInput) {
            oldPlayerX = playerX;
            oldPlayerY = playerY;

            do {
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);
            count++;

            type = keyStroke.getKeyType();
            c = keyStroke.getCharacter(); // used Character, not char because it might be null

            if (type == KeyType.Escape) {
                continueReadingInput = false;
                System.out.println("Exiting...");
                terminal.close();
            }

            if (boozt > 0 && boozt < 11) {
                movePlayer(type);
                movePlayer(type);
                boozt++;
            } else if (boozt == 11) {
                boozt = 0;
                movePlayer(type);
            } else if (slow) {
                if (slowCount % 2 == 0) {
                    movePlayer(type);
                }
                slowCount++;
            } else if (!slow && slowCount > 0) {
                slowCount = 0;
            } else if (!slow && slowCount == 0) {
                movePlayer(type);
            } else {
                movePlayer(type);
            }

            terminal.setCursorPosition(oldPlayerX, oldPlayerY);
            terminal.putCharacter(' ');
            if (Wall.checkCollisionWithWall(playerX, playerY)) {
                playerX = oldPlayerX;
                playerY = oldPlayerY;
            }
            terminal.setCursorPosition(playerX, playerY);
            terminal.putCharacter(player);
            terminal.setCursorPosition(0,0);
            terminal.putString("Points: " + points);
            terminal.flush();
            terminal.setCursorVisible(false);

            Emoji.initializeEmoji(terminal, count);

            Emoji.moveVirus(terminal, count, playerX, playerY);

            String tempString = Emoji.checkEmojiHit(terminal, playerX, playerY);
            if (tempString.equals("star")) {
                points++;
                terminal.setCursorPosition(0,0);
                terminal.putString("Points: " + points);
                terminal.flush();
            } else if (tempString.equals("coffee")) {
                boozt++;
                terminal.setCursorPosition(playerX, playerY);
                terminal.putCharacter(' ');
                movePlayer(type);
                terminal.setCursorPosition(playerX, playerY);
                terminal.putCharacter(player);
                terminal.flush();
            } else if (tempString.equals("faceMask")) {
                slow = true;
                slowCount++;
                terminal.setCursorPosition(playerX, playerY);
                terminal.putCharacter(' ');
                movePlayer(type);
                terminal.setCursorPosition(playerX, playerY);
                terminal.putCharacter(player);
                terminal.flush();
            } else if (tempString.equals("antibac")) {
                slow = false;
                terminal.setCursorPosition(playerX, playerY);
                terminal.putCharacter(' ');
                movePlayer(type);
                terminal.setCursorPosition(playerX, playerY);
                terminal.putCharacter(player);
                terminal.flush();
            } else if (tempString.equals("virus")) {
                boolean run = true;
                for (int i = 0; i < oldExplosion.length; i++) {
                    oldExplosion[i] = new Position(playerX, playerY);
                }
                terminal.setCursorPosition(playerX, playerY);
                terminal.putCharacter('X');
                terminal.flush();
                Thread.sleep(100);
                while (run) {
                    for (int i = 1; i < 10; i++) {
                        explosion[0] = new Position(playerX + i, playerY + i);
                        explosion[1] = new Position(playerX, playerY + i);
                        explosion[2] = new Position(playerX - i, playerY + i);
                        explosion[3] = new Position(playerX - i, playerY);
                        explosion[4] = new Position(playerX - i, playerY - i);
                        explosion[5] = new Position(playerX, playerY - i);
                        explosion[6] = new Position(playerX + i, playerY - i);
                        explosion[7] = new Position(playerX + i, playerY);
                        for (int j = 0; j < explosion.length; j++) {
                            terminal.setCursorPosition(oldExplosion[j].getX(), oldExplosion[j].getY());
                            terminal.putCharacter(' ');
                            terminal.setCursorPosition(explosion[j].getX(), explosion[j].getY());
                            terminal.putCharacter('X');
                            terminal.flush();
                        }
                        for (int k = 0; k < explosion.length; k++) {
                            oldExplosion[k] = explosion[k];
                        }

                        Thread.sleep(50);
                    }
                    run = false;
                }
                continueReadingInput = false;
                terminal.clearScreen();
                terminal.setCursorPosition(0,0);
                terminal.putString("Sorry, you got corona! Game over! Your final score was: " + points);
                terminal.flush();
                Thread.sleep(5000);
                terminal.close();
            }


//            if (virusBoolean) {
//                Position tempPos = new Position(virusPosition.getX(), virusPosition.getY());
//                virusPosition.setX(virusPosition.getX() - 1 + r.nextInt(3));
//                virusPosition.setY(virusPosition.getY() - 1 + r.nextInt(3));
//
//                for (Position position : wall) {
//                    if (virusPosition.getX() == position.getX() && virusPosition.getY() == position.getY()) {
//                        virusPosition.setX(tempPos.getX());
//                        virusPosition.setY(tempPos.getY());
//                        terminal.setCursorPosition(position.getX(), position.getY());
//                        terminal.putCharacter(block);
//                        terminal.setCursorPosition(virusPosition.getX(), virusPosition.getY());
//                        terminal.putString(virus);
//                        terminal.flush();
//                    } else {
//                        terminal.setCursorPosition(tempPos.getX(), tempPos.getY());
//                        terminal.putString(" ");
//                        terminal.setCursorPosition(virusPosition.getX(), virusPosition.getY());
//                        terminal.putString(virus);
//                        terminal.flush();
//                    }
//
//                }
//
//
//
//            }




//                for (int i = 0; i < oldExplosion.length; i++) {
//                    oldExplosion[i] = virusPosition;
//                }
/*

                if (playerX == virusPosition.getX() && playerY == virusPosition.getY()) {
                    terminal.putCharacter('O');
                    terminal.flush();
                    Thread.sleep(100);
                    for (int i = 1; i < 10; i++) {
                        explosion[0] = new Position(playerX + i, playerY + i);
                        explosion[1] = new Position(playerX, playerY + i);
                        explosion[2] = new Position(playerX - i, playerY + i);
                        explosion[3] = new Position(playerX - i, playerY);
                        explosion[4] = new Position(playerX - i, playerY - i);
                        explosion[5] = new Position(playerX, playerY - i);
                        explosion[6] = new Position(playerX + i, playerY - i);
                        explosion[7] = new Position(playerX + i, playerY);
                        for (int j = 0; j < explosion.length; j++) {
                            terminal.setCursorPosition(oldExplosion[j].getX(), oldExplosion[j].getY());
                            terminal.putCharacter(' ');
                            terminal.setCursorPosition(explosion[j].getX(), explosion[j].getY());
                            terminal.putCharacter('O');
                            terminal.flush();
                        }
                        for (int k = 0; k < explosion.length; k++) {
                            oldExplosion[k] = explosion[k];
                        }

                        Thread.sleep(50);
                    }
                    continueReadingInput = false;
                    terminal.setCursorPosition(sizeX / 2, sizeY / 2);
                    terminal.putString("Sorry, you got corona! Game over!");
                    terminal.flush();
                    Thread.sleep(5000);
                    terminal.close();
                } else {
                    terminal.putCharacter(player);
                    terminal.flush();
                    terminal.setCursorVisible(false);
                }
*/

/*
            if (type == KeyType.Enter) {
                column++;
                row = 0;
                terminal.setCursorPosition(row, column);
                terminal.flush();
            } else if (type == KeyType.Escape) {
                continueReadingInput = false;
                System.out.println("Exiting...");
                terminal.close();
            } else {
                row++;
                terminal.setCursorPosition(row, column);
                terminal.putCharacter(c);
                terminal.flush();
            }
*/

            }


        }

        public static void movePlayer(KeyType type) {
            switch (type) {
                case ArrowUp:
                    playerY--;
                    break;
                case ArrowDown:
                    playerY++;
                    break;
                case ArrowLeft:
                    playerX--;
                    break;
                case ArrowRight:
                    playerX++;
                    break;
            }
        }

}

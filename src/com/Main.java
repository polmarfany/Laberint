package com;

import java.util.Scanner;

public class Main {

    public static int MAX = 20; //num de blocks en axis X
    public static int MAY = 20; //num de blocks en axis Y

    //el següent seran els tipus de TILES, el que seria una "casella"
    final private static char PLAYER = 'X';
    final private static char BLANK = '_';
    final private static char WALL = 'W';
    final private static char BOMB = 'B';
    final private static char PICKAXE = 'P';
    final private static char EXIT= 'E';
    final private static char START = 'S';

    private static boolean ongoingGame = true;
    private static char [][] mazeBaseMap ={{WALL, EXIT, BLANK, BLANK, BLANK},
                                    {WALL, BLANK, BLANK, BLANK, BLANK},
                                    {WALL, BLANK, BLANK, BLANK, BLANK},
                                    {WALL, WALL, BLANK, BLANK, BLANK},
                                    {WALL, BLANK, BLANK, BLANK, BLANK}};
    private static char [][] mazeThroughRun;
    private static char [][] mazeShownPlayer;

    private static int currentPositionX;
    private static int currentPositionY;
    private static int exitPositionX;
    private static int exitPositionY;
    private static int newPositionX = 0;
    private static int newPositionY = 0;

    private static int numberPickaxe = 0;
    private static int lives = 5;
    private static String messageAfterAction;

    private static Scanner teclat = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("This program is the Maze Game. Rules are: ...... ");
        mazeThroughRunConstructor();

        while (ongoingGame) {

            String inputMove;
            boolean inputMoveCheck = true;

            mapShowToPlayer();
            System.out.println(messageAfterAction);

            System.out.println("Please select a direction: ");

            while (inputMoveCheck) {

                inputMoveCheck = false;
                inputMove = teclat.nextLine().toUpperCase();

                if (!inputMove.equals("RIGHT") && !inputMove.equals("LEFT") && !inputMove.equals("UP") && !inputMove.equals("DOWN") && !inputMove.equals("HELP")) {
                    inputMoveCheck = true;
                    System.out.println("You need to introduce a correct direction. Please re-try");
                }

                else if (inputMove.equals("HELP"))
                    askHelp();

                else
                    newPossiblePosition(inputMove);

            }
        }
    }

    private static void mazeThroughRunConstructor() {
        mazeThroughRun = mazeBaseMap;
        currentPositionX = 1; //de moment es comencara a aquesta posicio, quan es faci un randomitzador s'hauria de buscar la posició de 'S'
        currentPositionY = 1;
        exitPositionX = 0; //el mateix, es podria fer un randomitzador per a posar exits (nomes en les cantonades)
        exitPositionY = 1;
        mazeShownPlayer = mazeThroughRun;
        mazeShownPlayer[currentPositionX][currentPositionY] = PLAYER;
        //Possibilitat de ampliar-ho amb randomitzadors
    }

    private static void askHelp() {

    }

    private static void newPossiblePosition(String input) {

        switch (input) {
            case "RIGHT":
                newPositionX = currentPositionX + 1;
                newPositionY = currentPositionY;
            break;

            case "LEFT":
                newPositionX = currentPositionX - 1;
                newPositionY = currentPositionY;
            break;

            case "UP":
                newPositionX = currentPositionX;
                newPositionY = currentPositionY + 1;
            break;

            case "DOWN":
                newPositionX = currentPositionX;
                newPositionY = currentPositionY - 1;
            break;
        }

        newMovement(newPositionX, newPositionY);
    }

    private static void newMovement (int newX, int newY) {
        char newPositionValue = mazeBaseMap[newX][newY];

        switch (newPositionValue) {
            case BLANK:
                updateShownMap();
                messageAfterAction = "You have moved.";
            break;

            case WALL:
                System.out.println("You have hit a wall.");
                if (numberPickaxe > 0) {
                    boolean inputBreakWallCheck = true;
                    while (inputBreakWallCheck) {
                        System.out.println("You have " + numberPickaxe + " pickaxe/s. Do you want to use it? (Y or N).");
                        char inputBreakWall = teclat.next().charAt(0);

                        if (inputBreakWall == 'Y') {
                            inputBreakWallCheck = false;
                            mazeBaseMap[newX][newY] = BLANK;
                            messageAfterAction = "You have destroyed the wall, passing through.";

                        } else if (inputBreakWall == 'N') {
                            inputBreakWallCheck = false;
                            messageAfterAction = "You have decided to not use it. You can't go through the wall.";

                        } else System.out.println("You need to answer with Yes or No. ");
                    }
                }

                else {
                    messageAfterAction = "You cannot go through it.";
                }
            break;

            case BOMB:
                lives--;
                mazeBaseMap[newX][newY] = BLANK;

                currentPositionX = newX;
                currentPositionY = newY;

                messageAfterAction = "You have trespassed a bomb, you now have "+lives+" lives.";
            break;

            case PICKAXE:
                System.out.println("You found a Pickaxe. Do you want to take it? (Even if you dont take it, you can still pass through.");
                boolean inputTakePickaxeCheck = true;
                while (inputTakePickaxeCheck) {
                    char inputTakePickaxe = teclat.next().charAt(0);
                    if (inputTakePickaxe == 'Y') {
                        inputTakePickaxeCheck = false;
                        mazeBaseMap[newX][newY] = BLANK;
                        numberPickaxe++;

                    } else if (inputTakePickaxe == 'N') {
                        inputTakePickaxeCheck = false;
                        messageAfterAction = "You have decided to not take it, but you are on its position.";

                    } else System.out.println("You need to answer with Yes or No. ");
                }

            break;

            case EXIT:
                currentPositionX = newX;
                currentPositionY = newY;

                messageAfterAction = "You have found the exit! Congratulations, the game has ended.";
                ongoingGame = false;

            break;

        }

    }

    private static void updateShownMap() {
        mazeShownPlayer[currentPositionX][currentPositionY] = mazeThroughRun[currentPositionX][currentPositionY];

        mazeShownPlayer[newPositionX][newPositionY] = PLAYER; //d'aquesta manera, s'aconsegueix que el PLAYER nomes es mostri en el ShownMap
        //i amb el mapTroughRun tens el historic exacte de que és cada casella
        currentPositionX = newPositionX;
        currentPositionY = newPositionY;

    }

    private static void mapShowToPlayer() {



    }
}

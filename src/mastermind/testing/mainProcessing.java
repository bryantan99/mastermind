package fop_assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainProcessing {

    public static List<Player> playersList = new ArrayList();

    public static void main(String[] args) throws IOException {
        Scanner sca = new Scanner(System.in);
        String input;
        String userName = null;
        int count = 0;
        long startTime = 0;
        long endTime = 0;
        long time;
        int[] scoreArray = new int[10];
        while (true) {
            //the user interaface
            System.out.println("---------------------------------------------------------");
            System.out.println("                  Welcome to Mastermind                  ");
            System.out.println("---------------------------------------------------------");
            System.out.println("                   1:Single Player game                  ");
            System.out.println("                   2:Multiplayer game                    ");
            System.out.println("                   3:How-to-Play                         ");
            System.out.println("                   4:Test AI                             ");
            System.out.println("                  -1:Quit game                           ");
            System.out.println("---------------------------------------------------------");
            //user input
            System.out.print("                    No. of choice : ");
            input = sca.next();
            System.out.println("---------------------------------------------------------");

            //judge the input
            switch (input) {
                case ("1"):
                    int number;
                    System.out.print("Length of colour code (2-8) : ");
                    number = sca.nextInt();
                    while (number > 8 || number < 2) {
                        System.out.print("The range is only from 2 to 8. Please enter again : ");
                        number = sca.nextInt();
                    }
                    System.out.print("What's your name?           : ");
                    String name = sca.next();
                    Mastermind mu = new Mastermind(number, name);
                    userName = mu.getName();
                    //System.out.println("Loading----------");
                    mu.user_generate();
                    System.out.println("                    Let the game begins !");
                    startTime = System.currentTimeMillis();
                    mu.user_process();
                    endTime = System.currentTimeMillis();
                    time = (endTime - startTime) / 1000;
                    System.out.println("Total time used :" + time + "s");
                    Player player = new Player(name, mu.user_score(time, count, scoreArray), mu.level());
                    playersList.add(player);
                    System.out.println("Your score      :" + player.getScore());
                    swap(playersList);

                    System.out.println("\n-----------------------------------------------------------");
                    System.out.println("\t\t\tHALL OF FAME  ");
                    mu.show_score(count, scoreArray);
                    count++;

                    break;
                case ("2"):
                    int number2;
                    System.out.print("Length of colour code (2-8) : ");
                    number2 = sca.nextInt();
                    while (number2 > 8 || number2 < 2) {
                        System.out.print("The range is only from 2 to 8. Please enter again : ");
                        number2 = sca.nextInt();
                    }
                    Mastermind mu2 = new Mastermind(number2);
                    //System.out.println("Loading----------");
                    mu2.Multi_usersGenerate();
                    mu2.Multi_usersProcessing();

                    break;
                case ("3"):
                    System.out.println("\t\t\tRules");
                    System.out.println("1) Computer picks a sequence of colours represented by ");
                    System.out.println("   numbers. The code length is decided by user.");
                    System.out.println("2) 'x' = The position and colour is correct.");
                    System.out.println("   'o' = The colour is correct but its position is wrong.");
                    System.out.println("3) Player wins when the code sequence is guessed correctly.\n");

                    break;
                case ("-1"):
                    record(scoreArray, count, userName);
                    System.out.println("\n\n                    See you next time!\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Illegal input choice.");
            }

            //if users finish the game. it needs go back the menu again
            System.out.println("-----------------------------------------------------------");
            System.out.println("                Enter 1 to go back main menu               ");
            System.out.println("                Enter -1 to quit the game                  ");
            System.out.println("-----------------------------------------------------------");
            //user input
            System.out.print("                    No. of choice : ");
            input = sca.next();

            switch (input) {
                case ("1"): {
                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
                    break;
                }
                case ("-1"):
                    record(scoreArray, count, userName);
                    System.out.println("\n\n                    See you next time!\n");
                    System.exit(0);
                    break;
                default: {
                    System.out.println("\nIllegal input choice. You will be directed to main menu.");
                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
                }
            }
        }
    }

    public static void record(int score[], int count, String name) throws IOException {
        File file = new File("record.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Mastermind.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mastermind.class.getName()).log(Level.SEVERE, null, ex);
        }

        fw.write("------ HALL OF FAME -----" + System.getProperty("line.separator"));
        for (int n = 0; n <= count - 1; n++) {
            if (n == 0) {
                fw.write("1ST " + playersList.get(n).getName() + " \t" + playersList.get(n).getScore() + System.getProperty("line.separator"));
            }
            if (n == 1) {
                fw.write("2ND " + playersList.get(n).getName() + " \t" + playersList.get(n).getScore() + System.getProperty("line.separator"));
            }
            if (n == 2) {
                fw.write("3RD " + playersList.get(n).getName() + " \t" + playersList.get(n).getScore() + System.getProperty("line.separator"));
            } else if (n > 2) {
                fw.write((n + 1) + "TH " + playersList.get(n).getName() + " \t" + playersList.get(n).getScore() + System.getProperty("line.separator"));
            }
        }

        fw.close();
    }

    public static void swap(List<Player> temp) {
        Player player = new Player();
        int max = 0;
        for (int i = 0; i < temp.size() - 1; i++) {
            for (int j = 0; j < temp.size() - 1 - i; j++) {
                if (temp.get(j).getScore() < temp.get(j + 1).getScore()) {
                    player = temp.get(j);
                    temp.set(j, temp.get(j + 1));
                    temp.set(j + 1, player);
                }
            }
        }
    }
}

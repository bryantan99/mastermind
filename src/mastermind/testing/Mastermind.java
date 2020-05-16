
package fop_assignment;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author kyle chen
 */
public class Mastermind implements allMethods {

    //that is the file implements all the methods from the interface allmethods.
    private String[] ballColors = {"Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Purple", "Brown"};
    private Integer[] guess;
    private Integer[] answer;
    private Integer[] AIGet;//save the right number
    private Integer[] temp;
    private Integer[] temp1;
    private Integer[] temp2;
    private Integer[] user1;
    private Integer[] user2;
    private int[] feedbackArray;
    private int[] feedbackArray2;
    private int number; // the number of digits
    private String name;
    private boolean flag;//win or fail
    private int black;//black means all right  if black = 4 win!
    private int white;//white means wrong color right position 
    private int score;
    private int chance;
    private int chance1;
    private int hints;
    private int totallChance;
    private int tempNum;
    private int hintTemp;
    private int index;
    private int choose;
    private boolean state;
    private int timeCount;
    public String input;
    long sumA, sumB;
    long starTimeA, starTimeB;
    long endTimeA, endTimeB;
    long timeA, timeB;

    Random random = new Random();
    Scanner sca = new Scanner(System.in);

    public Mastermind(int number, String name) {//initialize
//        System.out.print("How many color code would you like to guess锛�(2-8) ");
//        number = sca.nextInt();
//        while(number!=2 || number!=3 || number!=4 || number!=5 || number!=6 || number!=7 || number!=8){
//            System.out.print("\n llegal input. Please enter again 锛� ");
//            number = sca.nextInt();
//        }
        this.name = name;
        this.number = number;
        guess = new Integer[number];
        answer = new Integer[number];
        AIGet = new Integer[number];
        temp = new Integer[number];
        temp1 = new Integer[number];
        temp2 = new Integer[10];
        user1 = new Integer[number];
        user2 = new Integer[number];
        feedbackArray = new int[10];
        feedbackArray2 = new int[10];
        black = 0;
        white = 0;
        score = 0;
        chance = 12;//can be modified
        chance1 = 0;
        timeCount = 0;
        totallChance = 12;
        hints = number / 2;//can be modified
        hintTemp = 0;
        tempNum = 0;
        choose = 2;
        index = 0;
        state = true;
        reset();
    }

    public Mastermind(int number) {//initialize
//        System.out.print("How many color code would you like to guess锛�(2-8) ");
//        number = sca.nextInt();
//        while(number!=2 || number!=3 || number!=4 || number!=5 || number!=6 || number!=7 || number!=8){
//            System.out.print("\n llegal input. Please enter again 锛� ");
//            number = sca.nextInt();
//        }
        this.number = number;
        guess = new Integer[number];
        answer = new Integer[number];
        AIGet = new Integer[number];
        temp = new Integer[number];
        temp1 = new Integer[number];
        temp2 = new Integer[10];
        user1 = new Integer[number];
        user2 = new Integer[number];
        feedbackArray = new int[10];
        feedbackArray2 = new int[10];
        black = 0;
        white = 0;
        score = 0;
        chance = 6;//can be modified
        chance1 = 0;
        timeCount = 0;
        totallChance = 6;
        hints = number / 2;//can be modified
        hintTemp = 0;
        tempNum = 0;
        choose = 2;
        index = 0;
        state = true;
        reset();
    }

    public void reset() {
        Arrays.fill(guess, 0);
        Arrays.fill(answer, 0);
        Arrays.fill(AIGet, 0);
        Arrays.fill(temp, 0);
        Arrays.fill(temp1, 0);
        Arrays.fill(user1, 0);
        Arrays.fill(user2, 0);
        flag = false;
        black = 0;
        white = 0;
        score = 0;
        chance = 10;
        chance1 = 0;
        hints = number / 2;//can be modified
        hintTemp = 0;
        tempNum = 0;
        choose = 2;
        state = true;
    }

    @Override
    public void user_generate() {//int number
        refresh();
        reset();//ensure the data is initialized
        for (int i = 0; i < answer.length; i++) {
            answer[i] = random.nextInt(8) + 1;//only 4 colors. that is answer
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("                Enter 'quit' to end the game.            ");
        System.out.println("                Enter 'hint' to get prompt.              ");
        System.out.println("---------------------------------------------------------");

    }

    @Override
    public void user_process() {
        for (int i = 0; i < ballColors.length; i++) {
            System.out.println(ballColors[i] + "\t=  " + (i + 1));//display the number of color
        }
        hintTemp = hints;
        //processing
        System.out.println("\n      ** Please enter your guess colour in numbers. **\n");
        System.out.println("\tGUESS\t\t" + "FEEDBACK" + "\t\tCHANCE LEFT");//title
        while (chance > 0) {
            System.out.print("        ");
            this.input = sca.next();//user input
            while (input.equals("hint")) {
                while (input.equals("hint") && hintTemp > 0) {
                    hint();
                    System.out.print("        ");
                    input = sca.next();
                }
                if (input.equals("hint") && hintTemp <= 0) {
                    System.out.println("      ** You've used up your hints. Please enter again : **");
                    System.out.print("        ");
                    input = sca.next();
                } else {
                    break;
                }
            }

            if (this.input.equals("quit")) {
                break;
            }
            
            if(this.input.equals("restart")){
                user_generate();
                user_process();
            }

            if (transform(input)) {// if the number is suitable
                System.out.print("\t" + input + "\t\t" + user_feedback(guess));
                refresh();//refresh the number of black and white and guess array
                if (flag == true) {
                    System.out.println("\n\n\t Congratulations, you guessed it correctly!\n");
                    //System.out.print("The answer is ");
                    //user_print(answer);
                    break;
                } else {
                    chance--;
                    System.out.print("\t\t\t" + chance + "\n");
                }
            }
        }
        if (flag == false) {//
            System.out.println("\n\n\t   ------------> GAME OVER <------------\n");
            System.out.print("The correct answer is ");
            user_print(answer);
        }
    }

    @Override
    public String user_feedback(Integer[] guess) {//by JIAYUE
        for (int i = 0; i < number; i++) {
            temp[i] = answer[i];
        }
        for (int i = 0; i < number; i++) {
            if (guess[i] == temp[i]) {
                guess[i] = temp[i] = 0;
                black++;
            }
        }

        for (int i = 0; i < number; i++) {
            for (int k = 0; k < number; k++) {
                if (guess[k] != 0 && guess[k] == temp[i]) {
                    guess[k] = temp[i] = 0;
                    white++;
                }
            }
        }
        if (black == number) {
            flag = true;
        }
        feedbackArray[timeCount] = black;
        feedbackArray2[timeCount] = white;
        return black + "x" + white + "o";
    }

    @Override
    public void hint() {
//        while (true) {
//            if (this.input.equals("hint")) {
        index = random.nextInt(number) + 1;
        System.out.print("\t***** Digit " + index + " is " + answer[index - 1] + " *****\n");
//                this.input = sca.next();
        hintTemp--;
//               if(hintTemp<=0)
//                    break;
//            }else{
//                state = true;
//                break;
//            }
    }

    public int user_score(long time, int count, int[] scoreArray) {//BY BAOGANG
        if (!input.equals("quit") && chance>0) {
            if (hints == hintTemp) {
                chance1 = totallChance - chance;
                score = (int) (1.0 / (1.0 + (chance1 * time)) * 100000 * number);
                scoreArray[count] = score;
                return score;
            } else {
                hints = hints - hintTemp;
                double decrease = Math.pow(0.8, hints);
                score = (int) (1.0 / (1.0 + (chance * time)) * 100000 * number * decrease);
                scoreArray[count] = score;
                return score;
            }
        } else {
            return score = 0;
        }
    }

    @Override
    public void show_score(int count, int[] scoreArray) {
        int temNum;

        //System.out.print(name+"'s score is " + scoreArray[count] + "\n");
        for (int i = 0; i <= count; i++) {
            temp2[i] = scoreArray[i];
        }
        for (int t = 0; t < count; t++) {
            for (int j = 0; j < count; j++) {
                if (temp2[j] < temp2[j + 1]) {
                    temNum = temp2[j];
                    temp2[j] = temp2[j + 1];
                    temp2[j + 1] = temNum;
                }
            }
        }
        System.out.print("\n");
        for (int i = 0; i <= count; i++) {
            switch (i) {
            //System.out.print("\t\t     " + (i + 1) + "st  "+mainProcessing.playersList.get(i).getName()+"\t" + mainProcessing.playersList.get(i).getScore() + "\n");
                case 0:
                    System.out.printf("\t     %dst %10s  %8s     Codelength:%d\n", (i + 1), mainProcessing.playersList.get(i).getName(), Integer.toString(mainProcessing.playersList.get(i).getScore()), mainProcessing.playersList.get(i).getLevel());
                    break;
                case 1:
                    System.out.printf("\t     %dnd %10s  %8s     Codelength:%d\n", (i + 1), mainProcessing.playersList.get(i).getName(), Integer.toString(mainProcessing.playersList.get(i).getScore()), mainProcessing.playersList.get(i).getLevel());
                    break;
                case 2:
                    System.out.printf("\t     %drd %10s  %8s     Codelength:%d\n", (i + 1), mainProcessing.playersList.get(i).getName(), Integer.toString(mainProcessing.playersList.get(i).getScore()), mainProcessing.playersList.get(i).getLevel());
                    break;
                default:
                    System.out.printf("\t     %dth %10s  %8s     Codelength:%d\n", (i + 1), mainProcessing.playersList.get(i).getName(), Integer.toString(mainProcessing.playersList.get(i).getScore()), mainProcessing.playersList.get(i).getLevel());
                    break;
            }
        }
        System.out.print("\n");
    }

    @Override
    public void AI_process() {
        int count = 0;
        for (int i = 1; i <= 8; i++) {
            Arrays.fill(guess, i);//the value of every member is i in guess array
            AI_print(guess);
            System.out.print(guess[number - 1] + "\t\t" + AI_feedback1() + "\n");//that can use another method to simplify(need improvment)

            if (flag == true) {
                break;
            }
            Arrays.fill(AIGet, count, count + black, i);// ensure the value of members is i from count to count+black
            Arrays.fill(AIGet, count + black, count + black + white, i);//ensure the value of members is i from (count+black) to (count+black+white)
            count += (black + white);
            refresh();
            if (count == number) {//means all numbers are right but position is wrong
                break;
            }
        }
        AI_anotherProcess();

    }

    @Override
    public void AI_anotherProcess() {
        while (flag == false) {
            if (flag == false) {
                for (int i = 0; i < AIGet.length - 1; i++) {
                    AI_swap(AIGet, i, i + 1);
                    AI_print(AIGet);
                    System.out.print(AIGet[number - 1] + "\t\t" + AI_feedback2() + "\n");
                    refresh();
                    if (flag == true) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void AI_swap(Integer[] AIGet, int a, int b) {
        int temp;
        temp = AIGet[a];
        AIGet[a] = AIGet[b];
        AIGet[b] = temp;
    }

    @Override
    public String AI_feedback1() {//by jia qin
        for (int i = 0; i < answer.length; i++) {
            temp[i] = answer[i];
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == guess[i]) {
                temp[i] = guess[i] = 0;
                black++;
            }
        }

        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < guess.length; j++) {
                if (temp[i] == guess[j] && guess[j] != 0) {
                    guess[j] = temp[i] = 0;
                    white++;
                }
            }
        }

        if (black == number) {
            flag = true;
        }

        return black + "X" + white + "O";

    }

    @Override
    public boolean transform(String input) {
//        if (!input.equals("hint") && hintTemp>0) {
        if (input.length() != number) {
            System.out.println("\t***** Illegal input *****");
            return false;
        }

        for (int i = 0; i < this.guess.length; i++) {
            if (input.charAt(i) - 48 > 8 || input.charAt(i) - 48 < 1) {
                System.out.println("illegal input");
                return false;
            } else {
                this.guess[i] = input.charAt(i) - 48;//change String [] int into Integer[]
            }
        }
//        }else
        return true;
//        return true;
    }

    @Override
    public void refresh() {
        this.black = 0;
        this.white = 0;
        Arrays.fill(guess, 0);
        Arrays.fill(temp, 0);
        Arrays.fill(temp1, 0);
    }

    @Override
    public String AI_feedback2() {
        refresh();
        for (int i = 0; i < AIGet.length; i++) {
            temp[i] = AIGet[i];
        }

        for (int i = 0; i < answer.length; i++) {
            temp1[i] = answer[i];
        }

        for (int i = 0; i < temp1.length; i++) {
            if (temp[i] == temp1[i]) {
                temp[i] = temp1[i] = 0;
                black++;
            }
        }

        for (int j = 0; j < temp1.length; j++) {
            for (int k = 0; k < temp.length; k++) {
                if (temp[k] != 0 && temp[k] == temp1[j]) {
                    temp[k] = temp1[j] = 0;
                    white++;
                }
            }
        }

        if (black == number) {
            flag = true;
        }

        return black + "X" + white + "O";
    }

    @Override
    public void user_print(Integer[] arr) {
        for (int i = 0; i < number; i++) {
            System.out.print(arr[i]);
        }
        System.out.println(".");
    }

    @Override
    public void AI_print(Integer[] arr) {
        for (int i = 0; i < number - 1; i++) {
            System.out.print(arr[i]);
        }
    }

    @Override
    public void Multi_usersGenerate() {
        reset();
        refresh();
        for (int i = 0; i < number; i++) {
            answer[i] = random.nextInt(8) + 1;
        }
        System.out.println("\n                    Let the game begins !\n");
        System.out.println("\t\t***** Enter 'quit' to Esc *****");

    }

    @Override
    public void Multi_usersProcessing() {
        for (int i = 0; i < ballColors.length; i++) {
            System.out.println(ballColors[i] + "\t=  " + (i + 1));//display the number of color
        }

        //processing
        System.out.println("\n\t\tGUESS\t\t" + "FEEDBACK");//title

        while (true) {
            System.out.print("\t\t");
            String input1 = sca.next();
            if (input1.equals("quit")) {
                System.out.println("Player 1 gives up. Player 2 wins the game.");
                break;
            }

            System.out.print("\t\t");
            String input2 = sca.next();

            if (input2.equals("quit")) {
                System.out.println("Player 2 gives up. Player 1 wins the game.");
                break;
            }

            if (Multi_tansform(input1, input2)) {
                System.out.println("\t\tPL1:" + input1 + "\t\t" + user_feedback(user1));
                refresh();
                timeCount++;
                if (flag == true) {
                    System.out.println("\nPlayer 1 wins the game.");
                    break;
                }
                System.out.println("\t\tPL2:" + input2 + "\t\t" + user_feedback(user2));
                refresh();
                if (flag == true) {
                    System.out.println("\nPayer 2 wins the game.");
                    break;
                }
            }
        }
        System.out.print("The correct answer is ");
        user_print(answer);
    }

    @Override
    public boolean Multi_tansform(String input1, String input2) {
        if (input1.length() != number && input2.length() == number) {
            System.out.println("\t\t***** Player1 illegal input. *****");
            return false;
        } else if (input2.length() != number && input1.length() == number) {
            System.out.println("\t\t***** Player2 illegal input. *****");
            return false;
        } else if (input2.length() != number && input2.length() != number) {
            System.out.println("\t\t***** Both illegal input. *****");
            return false;
        }

        for (int i = 0; i < number; i++) {
            if (input1.charAt(i) - 48 > 8 || input1.charAt(i) - 48 < 1) {
                System.out.println("Player1 illegal input");
                return false;
            } else if (input2.charAt(i) - 48 > 8 || input1.charAt(i) - 48 < 1) {
                System.out.println("Player2 illegal input");
                return false;
            } else if ((input1.charAt(i) - 48 > 8 || input1.charAt(i) - 48 < 1) && (input2.charAt(i) - 48 > 8 || input1.charAt(i) - 48 < 1)) {
                System.out.println("Both illegal input");
                return false;
            } else {
                user1[i] = input1.charAt(i) - 48;
                user2[i] = input2.charAt(i) - 48;
            }
        }
        return true;
    }

    @Override
    public int calcuMulScore(long time) {
        int sumBlack = 0;
        int sumWhite = 0;
        for (int i = 0; i < timeCount; i++) {
            sumBlack += feedbackArray[i];
        }
        for (int k = 0; k < timeCount; k++) {
            sumWhite += feedbackArray2[k];
        }
        score = (int) (1.0 / (1.0 + (timeCount * time)) * (0.7 * 100000 * sumBlack + 0.3 * 100000 * sumWhite));
        return score;
    }

//    public void printMultScore(int count, int[] userAscore, int[] userBscore) {
//        for (int k = 0; k <= count; k++) {
//            System.out.print("the player A get score is: " + userAscore[k] + "\n");
//        }
//        System.out.print("-------------------------------------------------");
//
//        for (int n = 0; n <= count; n++) {
//            System.out.print("the player B get score is: " + userAscore[n] + "\n");
//        }
//        System.out.print("\n");
//    }
    @Override
    public String getName() {
        return this.name;
    }

    public int level() {
        return this.number;
    }

}

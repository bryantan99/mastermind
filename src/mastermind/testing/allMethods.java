
package fop_assignment;


public interface allMethods {
    //that is an interface file,which consists of all the methods we'll use in the game
    void reset();//make all the virable back to initial condition
    void user_generate();//this method is for user to guess.(generate random numbers)
    void user_process();//this method is the process for user to guess
    String user_feedback(Integer [] guess);//this method is for showing the result to users
    void refresh();//refresh the number of black and white to ensure correct results
    void AI_process();//this method for AI to process the game
    String AI_feedback2();
    void AI_anotherProcess();//used for AI to get right answer 
    void AI_swap(Integer [] AIGet, int a, int b);//change position of number
    String  AI_feedback1();
    boolean transform(String guess);//change String [] into Integer[]
    void user_print(Integer arr[]);
    void AI_print(Integer arr[]);
    void Multi_usersGenerate();//generate random answer
    void Multi_usersProcessing();//processing
    boolean Multi_tansform(String input1 , String input2);
    int calcuMulScore(long time);// ignore
    String getName();//get the player's name
    void show_score(int count, int[] scoreArray) ;// for displaying the user'score
    void hint();//
    int level();// =number
}

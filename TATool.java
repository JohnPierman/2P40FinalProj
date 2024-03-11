import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TATool {

    /*
    * algos[0] = new personality(new alternate(), "alternate");
      algos[1] = new personality(new alwaysC(), "alwaysC");
      algos[2] = new personality(new alwaysD(), "alwaysD");
      algos[3] = new personality(new opposite(), "opposite");
      algos[4] = new personality(new titfortat(), "tit4tat");
      algos[5] = new personality(new titfor2tat(), "tit42tats");
      algos[6] = new personality(new twobackoneforward(), "twobackoneforward");
      algos[7] = new personality(new twoforwardoneback(), "twoforwardoneback");
    * */
    /*
    * Default Values:
    * initPops = {10,10,10,10,10,10,10,10}; //in general keep all values in range [10,1000]
    * rounds = 1000;
    * iterations = 100;
    * popSize = 100; //Interesting to play with but best for consistency at 100
    * These values gave me a runtime of about 37 seconds. I will look to optimize it. Probably won't be able to without
    * significant changes to the program. System logs show that majority of time is just dominated by the battler.
    * We could reduce number of game rounds per interaction between cells but may affect simulations.
    * */

    public static void AlwaysTA(){
        //int[] initPops = {0,90,10,0,0,0,0,0}; //Change these numbers to change initial populations.
        int rounds = 1000; //Change this number to increase/decrease simulation length (Numbers of years program will run)
        int iterations = 100; //Change this number to increase/decrease the iterations of the simulation. Higher number = more statistical precision but longer runtime
        int popSize = 100; //Change this number to change growth factor. Affects max size of population.
        int[][] finalPops = new int[8][iterations];
        int[][] results = new int[2][101];
        for(int n = 100; n >= 0; n--){
            int[] initPops = {0, n, (100 - n), 0, 0, 0, 0, 0};
            for (int i = 0; i < iterations; i++) {
                personality[] finalPersonalities = envHandler.runEnv(popSize, rounds, initPops);
                for (int j = 0; j < 8; j++) {
                    finalPops[j][i] = finalPersonalities[j].population;
                }
            }
            int p = 0;
            for(int k = 0; k < iterations; k++){
                if (finalPops[2][k] == 0){
                    p++;
                }
            }
            System.out.println(p);
            results[0][n] = n;
            results[1][n] = p;
            System.out.println((100 * (float) (100-n) / iterations) + "% Completed");
        }
        try {
            PrintWriter pw = new PrintWriter("TAData.txt");

            for (int i = 0; i < results.length; i++) { // Assuming array.length == 2
                for (int j = 0; j < results[i].length; j++) { // Assuming array[i].length == 100
                    pw.print(results[i][j]);
                    if (j < results[i].length - 1) {
                        pw.print(", "); // Separate elements in the same row with comma
                    }
                }
                pw.println(); // Move to the next line after finishing each row
            }

            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //AlwaysTA();
        int[] initPops = {100, 100, 100, 0, 100, 0, 100, 0}; //Change these numbers to change initial populations.
        int rounds = 1000; //Change this number to increase/decrease simulation length (Numbers of years program will run)
        int iterations = 10; //Change this number to increase/decrease the iterations of the simulation. Higher number = more statistical precision but longer runtime
        double popSize = 1000; //Change this number to change growth factor. Affects max size of population. //Keep 100 best number
        int[][] finalPops = new int[8][iterations];
        float[] meanPops = new float[8];
        for (int i = 0; i < iterations; i++) {
            personality[] finalPersonalities = envHandler.runEnv(popSize, rounds, initPops);
            for (int j = 0; j < 8; j++) {
                finalPops[j][i] = finalPersonalities[j].population;
            }
            System.out.println((100 * (float) (i+1) / iterations) + "% Completed");
        }
        //The Array finalPops contains the data for all finalPopulations. If analysis is wanted for % of finals states
        //manipulation of this array may be useful. This example checks how many times (and percent) the second algorithm goes to 0:
        int p = 0;
        for(int k = 0; k < iterations; k++){
            if (finalPops[2][k] == 0){
                p++;
            }
        }
        System.out.println(p);
        System.out.println(100 * (float)p/iterations);

        float totalMeanPops = 0;
        for (int j = 0; j < 8; j++) {
            float sum = 0;
            for (int i = 0; i < iterations; i++) {
                sum += finalPops[j][i];
            }
            meanPops[j] = sum / iterations;
            totalMeanPops+=meanPops[j];
        }
        for (int j = 0; j < 8; j++) {
            System.out.println("Algo " + j + " Mean Final Population %: " + 100*(meanPops[j]/totalMeanPops));
        }
        System.out.println("Final (Mean) Array: " + Arrays.toString(meanPops));
    }



}

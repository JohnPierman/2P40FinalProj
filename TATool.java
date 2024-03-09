import java.util.Arrays;

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
    * initPops = {10,10,10,10,10,10,10,10};
    * rounds = 1000;
    * iterations = 100;
    * popSize = 1000;
    * These values gave me a runtime of about 37 seconds. I will look to optimize it. Probably won't be able to without
    * significant changes to the program. System logs show that majority of time is just dominated by the battler.
    * We could reduce number of game rounds per interaction between cells but may affect simulations.
    * */

    public static void main(String[] args) {
        int[] initPops = {10,10,10,10,10,10,10,10}; //Change these numbers to change initial populations.
        int rounds = 1000; //Change this number to increase/decrease simulation length (Numbers of years program will run)
        int iterations = 100; //Change this number to increase/decrease the iterations of the simulation. Higher number = more statistical precision but longer runtime
        int popSize = 1000; //Change this number to change growth factor. Affects max size of population.
        int[][] finalPops = new int[8][iterations];
        float[] meanPops = new float[8];
        for (int i = 0; i < iterations; i++) { //Increasing number increases runtime but also increases precision
            personality[] finalPersonalities = envHandler.runEnv(popSize, rounds, initPops);
            for (int j = 0; j < 8; j++) {
                finalPops[j][i] = finalPersonalities[j].population;
            }
            System.out.println((100 * (float) i / iterations) + "% Completed");
        }
        //The Array finalPops contains the data for all finalPopulations. If analysis is wanted for % of finals states
        //manipulation of this array may be useful. This example checks how many times (and percent) the second algorithm goes to 0:
        /*int p = 0;
        for(int k = 0; k < iterations; k++){
            if (finalPops[2][k] == 0){
                p++;
            }
        }
        System.out.println(p);
        System.out.println(100 * (float)p/iterations);
        
         */
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

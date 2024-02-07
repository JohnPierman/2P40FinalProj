import java.lang.Math;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import algorithms.*;

public class envHandler {

    /*carrying capacity function f(x) = 500(1-e^(kx/1000), k is a constant around 1
     * This comes from the equation f(x) = c + ae^(-kx), where c, a and k are constants
     * c represents the maximum points obtainable in one round
     * Then we use the point f(0) = 0 to start correctly mapping the function. which gives us a = 500
     * Finally for ease on typing when we type {-} or {+} we are referring to the limit as you approach
     * from the negative or positive side respectively
     * We start with, 500{-} = 500(1-e^(k*1000) because we want the maximal point total necessary at the carrying capacity
     * 500{-}/500 = 1- e^(k*1000)
     * 1{-} - 1 = -e^(k*1000)
     * So ln(0{+})/1000 = k.
     * Experimentally I found the nicest graphs for curves existed around 0.005-0.007 instead of 0{+}
     * This is because there will still be decently steep growth for a sufficient time before it cuts off,
     * but doesn't flatten out as rapidly.
     */
    public static int carryingCapacity(int p, int size) {
        double k = 1;
        return (int) Math.ceil((size * (1 - Math.exp(5 * (k / 10000) * -p))));
    }

    public static int totalPopulation(int[] arr) {
        int j = 0;
        for (int i : arr) {
            j += i;
        }
        return j;
    }

    public static int[] createTempPopArr(personality[] arr) {
        int[] tempPop = new int[8];
        for (int j = 0; j < 8; j++) {
            tempPop[j] = arr[j].population;
        }
        return tempPop;
    }

    public static int createChildren(personality p, int pop, int size) {
        return (int) Math.floor((double) p.pointTally / carryingCapacity(pop, size));
    }

    public static void outputTable(personality[] algos, int round, int maxRounds, int size) {
        // Define the file path
        String filePath = "dataToVisualize.txt";
        // Use try-with-resources to ensure the writer is closed properly after use
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) { // true for append mode, but file is deleted each time method is called
            int n = totalPopulation(createTempPopArr(algos));
            out.printf("Round: %d, Total Population: %d, Points Needed To Reproduce: %d\n", round, n, carryingCapacity(n, size));
            for (personality p : algos) {
                out.printf("Name: %s, Population: %d, Point Tally: %d%n", p.name, p.population, p.pointTally);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println((100 * (float) round / maxRounds) + "% Processed");
    }

    public static String runEnv(int size, int rounds, int[] initPops) {
        String filePath = "dataToVisualize.txt";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        personality[] algos = new personality[8];
        algos[0] = new personality(new alternate(), "alternate");
        algos[1] = new personality(new alwaysC(), "alwaysC");
        algos[2] = new personality(new alwaysD(), "alwaysD");
        algos[3] = new personality(new opposite(), "opposite");
        algos[4] = new personality(new titfortat(), "tit4tat");
        algos[5] = new personality(new titfor2tat(), "tit42tats");
        algos[6] = new personality(new twobackoneforward(), "twobackoneforward");
        algos[7] = new personality(new twoforwardoneback(), "twoforwardoneback");
        //modify IVS
        //algos[0].setPopulation(-5);
        //Naughty Algos
        algos[2].setPopulation(initPops[2]);
        algos[6].setPopulation(initPops[6]);
        //Nice Algos
        algos[1].setPopulation(initPops[1]);
        algos[7].setPopulation(initPops[7]);
        //responsive algos
        algos[0].setPopulation(initPops[0]);
        algos[3].setPopulation(initPops[3]);
        algos[4].setPopulation(initPops[4]);
        algos[5].setPopulation(initPops[5]);
        battler.retValues retValues;
        int j;
        int[][] populationDecay = new int[8][5];
        for (j = 0; j < 8; j++) {
            populationDecay[j][0] = algos[j].population;
        }
        for (int i = 0; i < rounds; i++) {
            for (j = 0; j < 8; j++) {
                algos[j].setPointTally(0);
            }
            outputTable(algos, i + 1, rounds, size);
            int[] tempPop = createTempPopArr(algos);
            for (j = 0; j < 8; j++) {
                if (tempPop[j] > 0) {
                    while (totalPopulation(tempPop) > 1) {
                        int a = (int) Math.floor(8 * Math.random());
                        int b = (int) Math.floor(8 * Math.random());
                        if (tempPop[a] > 0 && tempPop[b] > 0) {
                            tempPop[a]--;
                            tempPop[b]--;
                            retValues = battler.simulate(algos[a].Algo, algos[b].Algo);
                            algos[a].setPointTally(algos[a].pointTally + retValues.algoApoints);
                            algos[b].setPointTally(algos[b].pointTally + retValues.algoBpoints);
                        }
                    }
                }
            }
            for (j = 0; j < 8; j++) {
                for (int k = 4; k > 0; k--) {
                    populationDecay[j][k] = populationDecay[j][k - 1];
                }
                int newChildren = createChildren(algos[j], totalPopulation(createTempPopArr(algos)), size);
                //System.out.println("Population: "+ algos[j].population+"Net Children: "+ (newChildren - populationDecay[j][4]));
                algos[j].setPopulation(newChildren - populationDecay[j][4]);
                populationDecay[j][0] = newChildren;
            }
        }
        return filePath;
    }

    public static void main(String[] args) {
        runEnv(2000,1000,new int[]{-10,0,0,-10,-10,-10,-10,-10});
    }
}


import java.lang.Math;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import algorithms.*;

public class envHandler {

    /*carrying capacity function f(x) = 500(1-e^(kx/1000), k is a constant around 1
     * This comes from the equation f(x) = c + ae^(-kx), where c, a and k are constants     *
     *
     * Notes:
     *
     * I ran 100000 simulations of just c=15, d=5, and got that it converges to d=0 78009 out of 100000 times
     * or almost exactly 78%
     */
    public static int carryingCapacity(int p, double size) {
        double k = 1;
        return (int) Math.ceil((size * (1 - Math.exp((k / 1000) * (-p)))));
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

    public static int createChildren(personality p, int pop, double size) {
        return (int) Math.floor((double) p.pointTally / carryingCapacity(pop, size));
    }

    public static void outputTable(personality[] algos, int round, int maxRounds, double size) {
        String filePath = "dataToVisualize.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) { // true for append mode, but file is deleted each time method is called
            int n = totalPopulation(createTempPopArr(algos));
            out.printf("Round: %d, Total Population: %d, Points Needed To Reproduce: %d\n", round, n, carryingCapacity(n, size));
            for (personality p : algos) {
                out.printf("Name: %s, Population: %d, Point Tally: %d%n", p.name, p.population, p.pointTally);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static personality[] runEnv(double size, int rounds, int[] initPops) {
        boolean aCheck = false;
        boolean bCheck = false;
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
            outputTable(algos, i + 1, rounds, size); //UNCOMMENT THIS LINE TO OUTPUT TO DATA TO VISUALIZE
            int[] tempPop = createTempPopArr(algos);
            while (totalPopulation(tempPop) > 1) {
                aCheck = false;
                bCheck = false;
                int a = 0;
                while (!aCheck) {
                    a = (int) Math.floor(8 * Math.random());
                    if (tempPop[a] > 0) {
                        tempPop[a]--;
                        aCheck = true;
                    }
                }
                int b = 0;
                while (!bCheck) {
                    b = (int) Math.floor(8 * Math.random());
                    if (tempPop[b] > 0) {
                        tempPop[b]--;
                        bCheck = true;
                    }
                }
                retValues = battler.simulate(algos[a].Algo, algos[b].Algo);

                algos[a].setPointTally(algos[a].pointTally + retValues.algoApoints);
                algos[b].setPointTally(algos[b].pointTally + retValues.algoBpoints);
            }

            for (j = 0; j < 8; j++) {
                for (int k = 4; k > 0; k--) {
                    populationDecay[j][k] = populationDecay[j][k - 1];
                }
                int newChildren = createChildren(algos[j], totalPopulation(createTempPopArr(algos)), size);
                algos[j].setPopulation(newChildren - populationDecay[j][4]);
                populationDecay[j][0] = newChildren;
            }
        }
        return algos;
    }
}


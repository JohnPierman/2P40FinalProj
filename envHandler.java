import java.lang.Math;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;

import algorithms.*;

public class envHandler {

    //Simple Method that helps calculate how many points are required to create new cells.
    //In general, a higher return value means it is "more difficult" for new cells to be created.
    public static int carryingCapacity(int p, double size) {
        double k = 1;
        return (int) Math.ceil((size * (1 - Math.exp((k / 1000) * (-p)))));
    }

    public static int totalPopulation(int[] arr) { //Sum an integer array
        int j = 0;
        for (int i : arr) {
            j += i;
        }
        return j;
    }

    public static int[] createTempPopArr(personality[] arr) { //Creates a temporary int array with the same values as the personality array's populations.
        int[] tempPop = new int[8];
        for (int j = 0; j < 8; j++) {
            tempPop[j] = arr[j].population;
        }
        return tempPop;
    }

    public static int createChildren(personality p, int pop, double size) { //Create children using the carrying capacity method and the personality's point tally.
        return (int) Math.floor((double) p.pointTally / carryingCapacity(pop, size));
    }

    //Method that outputs data to the "dataToVisualize.txt" file. This file allows visualizeData.py to create a historic picture of the populations as in Example.png
    public static void outputTable(personality[] algos, int round, double size) {
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

    public static personality[] runEnv(double size, int rounds, int[] initPops) { //The main method that runs the simulation
        boolean aCheck = false;
        boolean bCheck = false;
        String filePath = "dataToVisualize.txt";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        personality[] algos = new personality[8]; //Define our 8 personalities
        algos[0] = new personality(new alternate(), "alternate");
        algos[1] = new personality(new alwaysC(), "alwaysC");
        algos[2] = new personality(new alwaysD(), "alwaysD");
        algos[3] = new personality(new opposite(), "opposite");
        algos[4] = new personality(new titfortat(), "tit4tat");
        algos[5] = new personality(new titfor2tat(), "tit42tats");
        algos[6] = new personality(new twobackoneforward(), "twobackoneforward");
        algos[7] = new personality(new twoforwardoneback(), "twoforwardoneback");
        //modify IVS -> Note: this following part could be simplified by overloading the creation of the personalities.
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
        int[][] populationDecay = new int[8][5]; //Initialize our population decay array.
        for (j = 0; j < 8; j++) { // Set the first element in each personalities array as their initial population
            populationDecay[j][0] = algos[j].population;
        }
        for (int i = 0; i < rounds; i++) {
            for (j = 0; j < 8; j++) {
                algos[j].setPointTally(0); //Resets all personalities point tally to 0.
            }
            //outputTable(algos, i + 1, size); //UNCOMMENT THIS LINE TO OUTPUT TO DATA TO VISUALIZE -> Slows program when using many iterations.
            int[] tempPop = createTempPopArr(algos); //Create an integer array that represents population
            // The following loop is best explained as a whole than line by line.
            // The idea behind this loop is that it matches each cell from a personality to another random cell in the ecosystem,
            // one by one until there are no (or one because it wouldn't have a match) cells left.
            // There are checks to make sure it doesn't match to a personality with 0 cells remaining.
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
                retValues = battler.simulate(algos[a].Algo, algos[b].Algo); //Run the battle on each of the random cell matches

                algos[a].setPointTally(algos[a].pointTally + retValues.algoApoints); //Update the point totals of each personality
                algos[b].setPointTally(algos[b].pointTally + retValues.algoBpoints);
            }
            //Apply the population decay: after 5 iterations cells "die", and are subtracted from their point total.
            for (j = 0; j < 8; j++) {
                for (int k = 4; k > 0; k--) {
                    populationDecay[j][k] = populationDecay[j][k - 1];
                }
                int newChildren = createChildren(algos[j], totalPopulation(createTempPopArr(algos)), size); //Create new cells depending on the total population size, point total, and size environment argument
                algos[j].setPopulation(newChildren - populationDecay[j][4]); //Update populations of personalities
                populationDecay[j][0] = newChildren;
            }
        }
        return algos;
    }
}


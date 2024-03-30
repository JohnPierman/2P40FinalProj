import java.util.Arrays;

public class TATool {

    /*
    0: Alternate
    1: Always Cooperate
    2: Always Defect
    3: Opposite
    4: Tit For Tat
    5: Tit For 2 Tats
    6: Two Back One Forward
    7: Two Forward One Back
    */
    public static int findIndexOfMax(int[] array) { //Simple helper method for finding Dominant Algorithm
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static int[] SurvivabilityTool() {
        int[] resultsArr = new int[256];
        for (int i = 1; i < 256; i++) {
            int rounds = 1000;
            double popSize = 1500;
            String binaryIndex = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0'); //convert number to binary string
            int[] initPops = new int[8];
            for (int j = 0; j < 8; j++) {
                if (binaryIndex.charAt(j) == '0') {
                    initPops[j] = 0;
                } else {
                    initPops[j] = 100;
                }
            }
            personality[] finalPersonalities = envHandler.runEnv(popSize, rounds, initPops);
            StringBuilder resultsString = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                if (finalPersonalities[j].population == 0) {
                    resultsString.append('0');
                } else {
                    resultsString.append('1');
                }
            }
            resultsArr[i] = Integer.parseInt(resultsString.toString(), 2);
            System.out.println("Done " + (i + 1) + "of 256");
        }
        return resultsArr;
    }

    public static int[] DominanceTool() {
        int[] resultsArr = new int[256];
        for (int i = 1; i < 256; i++) {
            int rounds = 1000;
            double popSize = 1500;
            String binaryIndex = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            int[] initPops = new int[8];
            for (int j = 0; j < 8; j++) {
                if (binaryIndex.charAt(j) == '0') {
                    initPops[j] = 0;
                } else {
                    initPops[j] = 100;
                }
            }
            personality[] finalPersonalities = envHandler.runEnv(popSize, rounds, initPops);
            int[] finalPops = new int[8];
            for (int j = 0; j < 8; j++) {
                finalPops[j] = finalPersonalities[j].population;
            }
            resultsArr[i - 1] = findIndexOfMax(finalPops);
            System.out.println(Arrays.toString(finalPops));
            System.out.println(resultsArr[i - 1]);
            System.out.println("Done " + (i) + "of 255");
        }
        return resultsArr;
    }

    public static int[] CustomTool() { //Tool that allows to freely customize values. Tips: Keep popSize [1000,2000].
        int rounds = 1000;
        double popSize = 1500;
        int[] initPops = {100, 100, 100, 100, 100, 100, 100, 100};
        personality[] finalPersonalities = envHandler.runEnv(popSize,rounds,initPops);
        int[] finalPops = new int[8];
        for (int j = 0; j < 8; j++) {
            finalPops[j] = finalPersonalities[j].population;
        }
        return finalPops;
    }

    public static void main(String[] args) { //Only have one tool uncommented at a time.
        //int[] results = SurvivabilityTool();
        int[] results = CustomTool();
        //int[] results = DominanceTool();
        for (int i = 0; i < results.length; i++) {
            System.out.println("Index " + i + ": " + results[i]);

        }
    }
}



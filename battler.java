import algorithms.*;
public class battler {

    static class retValues{
        public int algoApoints;
        public int algoBpoints;
    }

    public static retValues simulate(algo algoA, algo algoB){
        retValues returnValues = new retValues();
        boolean algoADecision, algoBDecision = false;
        for(int roundCount = 0; roundCount<50; roundCount++){
            algoADecision = algoA.postMove();
            algoBDecision = algoB.postMove();
            if (algoADecision == algoBDecision && algoADecision) { //Both cooperate
                returnValues.algoApoints += 3;
                returnValues.algoBpoints += 3;
            } else if (algoADecision == algoBDecision) { //Both defect
                returnValues.algoApoints += 1;
                returnValues.algoBpoints += 1;
            } else if (algoADecision) { // A cooperate, B defect
                returnValues.algoBpoints += 5;
            }
            else { //A defect B cooperate
                returnValues.algoApoints += 5;
            }
            algoA.getMove(algoBDecision);
            algoB.getMove(algoADecision);
        }
        //System.out.println("A: " + returnValues.algoApoints);
        //System.out.println("B: " + returnValues.algoBpoints);
        return returnValues;
    }


}

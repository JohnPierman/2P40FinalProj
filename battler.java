import algorithms.*;
public class battler {

    static class retValues{
        public int algoApoints;
        public int algoBpoints;
    }

    static retValues simulate(algo algoA, algo algoB){
        retValues returnValues = new retValues();
        boolean algoADecision, algoBDecision = false;
        for(int roundCount = 0; roundCount<100; roundCount++){ // Run for 100 Rounds
            algoADecision = algoA.postMove(); // Get each algorithm's decision
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
            else { //A defect, B cooperate
                returnValues.algoApoints += 5;
            }
            algoA.getMove(algoBDecision); //Inform each algorithm on the other's decision
            algoB.getMove(algoADecision);
        }
        return returnValues;
    }


}

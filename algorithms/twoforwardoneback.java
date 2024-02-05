package algorithms;
public class twoforwardoneback implements algo{

    boolean SecondDecision = true;
    boolean FirstDecision = true;
    @Override
    public boolean postMove() {
        if (!SecondDecision){
            FirstDecision = SecondDecision;
            SecondDecision = true;
            return true;
        } else if (FirstDecision) {
            FirstDecision = SecondDecision;
            return true;
        } else {
            SecondDecision = false;
            return false;
        }
    }

    @Override
    public void getMove(boolean OppDecision) {

    }
}

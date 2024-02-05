package algorithms;
public class twobackoneforward implements algo{

    boolean SecondDecision = true;
    boolean FirstDecision = true;
    @Override
    public boolean postMove() {
        if (SecondDecision){
            FirstDecision = SecondDecision;
            SecondDecision = false;
            return false;
        } else if (FirstDecision) {
            FirstDecision = SecondDecision;
            return false;
        } else {
            SecondDecision = true;
            return true;
        }
    }

    @Override
    public void getMove(boolean OppDecision) {

    }
}

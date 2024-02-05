package algorithms;
public class alternate implements algo{

    private boolean lastDecision = false;
    @Override
    public boolean postMove() {
        if (!lastDecision){
            lastDecision = true;
            return true;
        } else {
            lastDecision = false;
            return false;
        }
    }

    @Override
    public void getMove(boolean OppDecision) {
    }

}

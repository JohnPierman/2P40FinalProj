package algorithms;
public class opposite implements algo{

    boolean oppLastDecision = false;
    @Override
    public boolean postMove() {
        return !oppLastDecision;
    }

    @Override
    public void getMove(boolean OppDecision) {
        oppLastDecision = OppDecision;
    }
}

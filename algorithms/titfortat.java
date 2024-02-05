package algorithms;
public class titfortat implements algo{

    boolean oppLastDecision = true;
    @Override
    public boolean postMove() {
        return oppLastDecision;
    }

    @Override
    public void getMove(boolean OppDecision) {
        oppLastDecision = OppDecision;
    }
}

package algorithms;
public class titfor2tat implements algo{

    boolean oppSecondDecision = true;
    boolean oppFirstDecision = true;
    @Override
    public boolean postMove() {
        return oppSecondDecision != oppFirstDecision || oppSecondDecision;
    }

    @Override
    public void getMove(boolean OppDecision) {
        oppFirstDecision = oppSecondDecision;
        oppSecondDecision = OppDecision;
    }
}

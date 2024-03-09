public class TATool {

    /*
    * algos[0] = new personality(new alternate(), "alternate");
      algos[1] = new personality(new alwaysC(), "alwaysC");
      algos[2] = new personality(new alwaysD(), "alwaysD");
      algos[3] = new personality(new opposite(), "opposite");
      algos[4] = new personality(new titfortat(), "tit4tat");
      algos[5] = new personality(new titfor2tat(), "tit42tats");
      algos[6] = new personality(new twobackoneforward(), "twobackoneforward");
      algos[7] = new personality(new twoforwardoneback(), "twoforwardoneback");
    * */
    public void TechAnalysis(){
        int[] initPops = {0,75,25,0,0,0,0,0};
        envHandler.runEnv(2000,1500,initPops);

    }

}

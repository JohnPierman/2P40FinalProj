import algorithms.algo;

public class personality {
    public algo Algo;
    public int pointTally = 0;

    public String name;
    public int population;

    personality(algo a, String s){
        setAlgo(a);
        setPopulation(10);
        setName(s);
    }
    public void setAlgo(algo a){
        this.Algo = a;
    }
    public void setPointTally(int newPoints){
        this.pointTally = newPoints;
    }

    public void setPopulation(int population) {
        this.population += population;
    }
    public void setName(String name){
        this.name = name;
    }
}
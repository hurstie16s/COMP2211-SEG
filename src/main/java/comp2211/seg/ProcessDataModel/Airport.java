package comp2211.seg.ProcessDataModel;

import java.util.ArrayList;

public class Airport {
    private ArrayList<Runway> runways;
    public String name;
    public Airport(String name){
        runways = new ArrayList<>();
        this.name = name;
        makeRunway();
    }
    public void makeRunway(){
        runways.add(new Runway());
    }

    public ArrayList<Runway> getRunways() {
        return runways;
    }
}

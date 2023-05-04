package comp2211.seg.Controller.Interfaces;

import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.Runway;

import java.util.ArrayList;

/**
 * The type Airports data.
 */
public class AirportsData {
    /**
     * Gets airports.
     *
     * @return the airports
     */
    public static ArrayList<Airport> getAirports() {
        ArrayList<Airport> airports = new ArrayList<>();
        Airport apLHR = new Airport("Heathrow (LHR)", "09L/27R",3902,3902,3660,3902,3902,3902,3660,3902);
        apLHR.addRunway(new Runway("09R/27L",3884,3884,3660,3884,3884,3884,3660,3884));
        airports.add(apLHR);
        Airport apLGW = new Airport("Gatwick (LGW)","08L/26R",2866,3479,2661,2866,2866,3479,2661,2866);
        apLGW.addRunway(new Runway("08R/26L",2866,3479,2661,2866,2866,3479,2661,2866));
        airports.add(apLGW);
        Airport apMAN = new Airport("Manchester (MAN)","05L/23R",3048,3128,2908,3048,3048,3128,3048,3048);
        apMAN.addRunway(new Runway("05R/23L",3048,3128,2908,3048,3048,3128,3048,3048));
        airports.add(apMAN);
        Airport apDEMO = (new Airport("Demo Airport", "09L/27R", 4000, 5000, 3500, 4500, 4000, 5000, 3500, 4500));
        apDEMO.addRunway(new Runway("08/26", 4000, 5000, 3500, 4500, 4000, 5000, 3500, 4500));
        apDEMO.addRunway(new Runway("07", 4000, 5000, 3500, 4500));

        airports.add(apDEMO);
        //        airports.add(new Airport("Stansted (STN)","04/22",3048,3048,2908,3048,3048,3128,3048,3048));
//        airports.add(new Airport("Luton (LTN)","08/26",2164,2164,2744,3048,3048,3048,3048,3048));
//        airports.add(new Airport("Edinburgh (EDI)","06/24",2011,2111,1939,2164,2164,2164,1939,2164));
//        airports.add(new Airport("Birmingham (BHX)","15/33",2728,2996,1893,2011,2011,2111,2011,2011));
//        airports.add(new Airport("Glasgow (GLA)","05/23",2217,2217,2551,2728,2728,2996,2728,2728));
//        airports.add(new Airport("Bristol (BRS)","09/27",2010,2010,2083,2217,2217,2217,2217,2217));
//        airports.add(new Airport("Newcastle (NCL)","07/25",1962,2127,1829,2010,2010,2010,1900,2010));
//        airports.add(new Airport("East Midlands (EMA)","09/27",2926,2926,1829,1962,1962,2127,1962,1962));
//        airports.add(new Airport("Liverpool John Lennon (LPL)","09/27",2134,2310,2761,2926,2926,2926,2761,2926));
//        airports.add(new Airport("London City (LCY)","09/27",1509,1509,2134,2134,2134,2310,2134,2134));
//        airports.add(new Airport("Leeds Bradford (LBA)","14/32",1723,1723,1509,1509,1509,1509,1509,1509));
//        airports.add(new Airport("Belfast International (BFS)","07/25",2933,2933,1607,1723,1723,1723,1723,1723));
//        airports.add(new Airport("Aberdeen International (ABZ)","16/34",1951,2106,2755,2933,2933,2933,2755,2933));
//        airports.add(new Airport("Southampton (SOU)","02/20",1725,1725,1951,1951,1951,1951,1834,1951));
//        airports.add(new Airport("Cardiff (CWL)","12/30",2145,2145,1725,1725,1725,1725,1725,1725));
//        airports.add(new Airport("Glasgow Prestwick (PIK)","03/21",2099,2099,2145,2145,2145,2145,2145,2145));
//        airports.add(new Airport("London Southend (SEN)","06/24",1791,1791,2099,2099,2099,2099,2099,2099));

        return airports;
    }
}

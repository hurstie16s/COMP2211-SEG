package comp2211.seg.Controller.Interfaces;

import comp2211.seg.ProcessDataModel.Airport;

import java.util.ArrayList;

public class AirportsData {
    String[] names = {
            "Heathrow (LHR)",
            "Gatwick (LGW)",
            "Manchester (MAN)",
            "Stansted (STN)",
            "Luton (LTN)",
            "Edinburgh (EDI)",
            "Birmingham (BHX)",
            "Glasgow (GLA)",
            "Bristol (BRS)",
            "Newcastle (NCL)",
            "East Midlands (EMA)",
            "Liverpool John Lennon (LPL)",
            "London City (LCY)",
            "Leeds Bradford (LBA)",
            "Belfast International (BFS)",
            "Aberdeen International (ABZ)",
            "Southampton (SOU)",
            "Cardiff (CWL)",
            "Glasgow Prestwick (PIK)",
            "London Southend (SEN)"
    };
    String[][] runways = {
            // Heathrow Airport
            {"09L/27R", "09R/27L"},
            // Gatwick Airport
            {"08L/26R", "08R/26L"},
            // Manchester Airport
            {"05L/23R", "05R/23L"},
            // Stansted Airport
            {"04/22"},
            // Luton Airport
            {"08/26"},
            // Edinburgh Airport
            {"06/24"},
            // Birmingham Airport
            {"15/33"},
            // Glasgow Airport
            {"05/23"},
            // Bristol Airport
            {"09/27"},
            // Newcastle Airport
            {"07/25"},
            // East Midlands Airport
            {"09/27"},
            // Liverpool John Lennon Airport
            {"09/27"},
            // London City Airport
            {"09/27"},
            // Leeds Bradford Airport
            {"14/32"},
            // Belfast International Airport
            {"07/25"},
            // Aberdeen International Airport
            {"16/34"},
            // Southampton Airport
            {"02/20"},
            // Cardiff Airport
            {"12/30"},
            // Glasgow Prestwick Airport
            {"03/21"},
            // London Southend Airport
            {"06/24"}
    };

    public ArrayList<Airport> getAirports() {
        ArrayList<Airport> airports = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Airport airport1 = new Airport(names[i]);

        }



        return airports;
    }


    // London Heathrow Airport
    public static String LHR_RUNWAY_09L_27R = "09L/27R";
    public static int LHR_TORA_09L = 3902; // meters
    public static int LHR_TODA_09L = 3902; // meters
    public static int LHR_LDA_09L = 3660; // meters
    public static int LHR_ASDA_09L = 3902; // meters
    public static int LHR_TORA_27R = 3902; // meters
    public static int LHR_TODA_27R = 3902; // meters
    public static int LHR_LDA_27R = 3660; // meters
    public static int LHR_ASDA_27R = 3902; // meters
    public static String LHR_RUNWAY_09R_27L = "09R/27L";
    public static int LHR_TORA_09R = 3884; // meters
    public static int LHR_TODA_09R = 3884; // meters
    public static int LHR_LDA_09R = 3660; // meters
    public static int LHR_ASDA_09R = 3884; // meters
    public static int LHR_TORA_27L = 3884; // meters
    public static int LHR_TODA_27L = 3884; // meters
    public static int LHR_LDA_27L = 3660; // meters
    public static int LHR_ASDA_27L = 3884; // meters
    // London Gatwick Airport
    public static String LGW_RUNWAY_08L_26R = "08L/26R";
    public static int LGW_TORA_08L = 3034; // meters
    public static int LGW_TODA_08L = 3479; // meters
    public static int LGW_LDA_08L = 2738; // meters
    public static int LGW_ASDA_08L = 3034; // meters
    public static int LGW_TORA_26R = 3034; // meters
    public static int LGW_TODA_26R = 3479; // meters
    public static int LGW_LDA_26R = 3034; // meters
    public static int LGW_ASDA_26R = 3479; // meters
    // String LGW_RUNWAY_08R_26L = "08R/26L";
    public static int LGW_TORA_08R = 2866; // meters
    public static int LGW_TODA_08R = 3479; // meters
    public static int LGW_LDA_08R = 2661; // meters
    public static int LGW_ASDA_08R = 2866; // meters
    public static int LGW_TORA_26L = 2866; // meters
    public static int LGW_TODA_26L = 3479; // meters
    public static int LGW_LDA_26L = 2661; // meters
    public static int LGW_ASDA_26L = 2866; // meters
    // Manchester Airport
    public static String MAN_RUNWAY_05L_23R = "05L/23R";
    public static int MAN_TORA_05L = 3048;
    public static int MAN_TODA_05L = 3128;
    public static int MAN_LDA_05L = 2908;
    public static int MAN_ASDA_05L = 3048;
    public static int MAN_TORA_23R = 3048;
    public static int MAN_TODA_23R = 3128;
    public static int MAN_LDA_23R = 3048;
    public static int MAN_ASDA_23R = 3048;

    public static String MAN_RUNWAY_05R_23L = "05R/23L";
    public static int MAN_TORA_05R = 3048;
    public static int MAN_TODA_05R = 3128;
    public static int MAN_LDA_05R = 2908;
    public static int MAN_ASDA_05R = 3048;
    public static int MAN_TORA_23L = 3048;
    public static int MAN_TODA_23L = 3128;
    public static int MAN_LDA_23L = 3048;
    public static int MAN_ASDA_23L = 3048;
    // London Stansted Airport
    public static String STN_RUNWAY_04_22 = "04/22";
    public static int STN_TORA_04 = 3048;
    public static int STN_TODA_04 = 3048;
    public static int STN_LDA_04 = 2744;
    public static int STN_ASDA_04 = 3048;
    public static int STN_TORA_22 = 3048;
    public static int STN_TODA_22 = 3048;
    public static int STN_LDA_22 = 3048;
    public static int STN_ASDA_22 = 3048;
    // London Luton Airport
    public static String LTN_RUNWAY_08_26 = "08/26";
    public static int LTN_TORA_08 = 2164;
    public static int LTN_TODA_08 = 2164;
    public static int LTN_LDA_08 = 1939;
    public static int LTN_ASDA_08 = 2164;
    public static int LTN_TORA_26 = 2164;
    public static int LTN_TODA_26 = 2164;
    public static int LTN_LDA_26 = 1939;
    public static int LTN_ASDA_26 = 2164;
    // Edinburgh Airport
    public static String EDI_RUNWAY_06_24 = "06/24";
    public static int EDI_TORA_06 = 2011;
    public static int EDI_TODA_06 = 2111;
    public static int EDI_LDA_06 = 1893;
    public static int EDI_ASDA_06 = 2011;
    public static int EDI_TORA_24 = 2011;
    public static int EDI_TODA_24 = 2111;
    public static int EDI_LDA_24 = 2011;
    public static int EDI_ASDA_24 = 2011;
    // Birmingham Airport
    public static String BHX_RUNWAY_15_33 = "15/33";
    public static int BHX_TORA_15 = 2728;
    public static int BHX_TODA_15 = 2996;
    public static int BHX_LDA_15 = 2551;
    public static int BHX_ASDA_15 = 2728;
    public static int BHX_TORA_33 = 2728;
    public static int BHX_TODA_33 = 2996;
    public static int BHX_LDA_33 = 2728;
    public static int BHX_ASDA_33 = 2728;
    // Glasgow Airport
    public static String GLA_RUNWAY_05_23 = "05/23";
    public static int GLA_TORA_05 = 2217;
    public static int GLA_TODA_05 = 2217;
    public static int GLA_LDA_05 = 2083;
    public static int GLA_ASDA_05 = 2217;
    public static int GLA_TORA_23 = 2217;
    public static int GLA_TODA_23 = 2217;
    public static int GLA_LDA_23 = 2217;
    public static int GLA_ASDA_23 = 2217;
    // Bristol Airport
    public static String BRS_RUNWAY_09_27 = "09/27";
    public static int BRS_TORA_09 = 2010;
    public static int BRS_TODA_09 = 2010;
    public static int BRS_LDA_09 = 1829;
    public static int BRS_ASDA_09 = 2010;
    public static int BRS_TORA_27 = 2010;
    public static int BRS_TODA_27 = 2010;
    public static int BRS_LDA_27 = 1900;
    public static int BRS_ASDA_27 = 2010;
    // Newcastle Airport
    public static String NCL_RUNWAY_07_25 = "07/25";
    public static int NCL_TORA_07 = 1962;
    public static int NCL_TODA_07 = 2127;
    public static int NCL_LDA_07 = 1829;
    public static int NCL_ASDA_07 = 1962;
    public static int NCL_TORA_25 = 1962;
    public static int NCL_TODA_25 = 2127;
    public static int NCL_LDA_25 = 1962;
    public static int NCL_ASDA_25 = 1962;
    // East Midlands Airport
    public static String EMA_RUNWAY_09_27 = "09/27";
    public static int EMA_TORA_09 = 2926;
    public static int EMA_TODA_09 = 2926;
    public static int EMA_LDA_09 = 2761;
    public static int EMA_ASDA_09 = 2926;
    public static int EMA_TORA_27 = 2926;
    public static int EMA_TODA_27 = 2926;
    public static int EMA_LDA_27 = 2761;
    public static int EMA_ASDA_27 = 2926;
    // Liverpool John Lennon Airport
    public static String LPL_RUNWAY_09_27 = "09/27";
    public static int LPL_TORA_09 = 2134;
    public static int LPL_TODA_09 = 2310;
    public static int LPL_LDA_09 = 2134;
    public static int LPL_ASDA_09 = 2134;
    public static int LPL_TORA_27 = 2134;
    public static int LPL_TODA_27 = 2310;
    public static int LPL_LDA_27 = 2134;
    public static int LPL_ASDA_27 = 2134;
    // London City Airport
    public static String LCY_RUNWAY_09_27 = "09/27";
    public static int LCY_TORA_09 = 1509;
    public static int LCY_TODA_09 = 1509;
    public static int LCY_LDA_09 = 1509;
    public static int LCY_ASDA_09 = 1509;
    public static int LCY_TORA_27 = 1509;
    public static int LCY_TODA_27 = 1509;
    public static int LCY_LDA_27 = 1509;
    public static int LCY_ASDA_27 = 1509;
    // Leeds Bradford Airport
    public static String LBA_RUNWAY_14_32 = "14/32";
    public static int LBA_TORA_14 = 1723;
    public static int LBA_TODA_14 = 1723;
    public static int LBA_LDA_14 = 1607;
    public static int LBA_ASDA_14 = 1723;
    public static int LBA_TORA_32 = 1723;
    public static int LBA_TODA_32 = 1723;
    public static int LBA_LDA_32 = 1723;
    public static int LBA_ASDA_32 = 1723;
    // Belfast International Airport
    public static String BFS_RUNWAY_07_25 = "07/25";
    public static int BFS_TORA_07 = 2933;
    public static int BFS_TODA_07 = 2933;
    public static int BFS_LDA_07 = 2755;
    public static int BFS_ASDA_07 = 2933;
    public static int BFS_TORA_25 = 2933;
    public static int BFS_TODA_25 = 2933;
    public static int BFS_LDA_25 = 2755;
    public static int BFS_ASDA_25 = 2933;
    // Aberdeen International Airport
    public static String ABZ_RUNWAY_16_34 = "16/34";
    public static int ABZ_TORA_16 = 1951;
    public static int ABZ_TODA_16 = 2106;
    public static int ABZ_LDA_16 = 1951;
    public static int ABZ_ASDA_16 = 1951;
    public static int ABZ_TORA_34 = 1951;
    public static int ABZ_TODA_34 = 1951;
    public static int ABZ_LDA_34 = 1834;
    public static int ABZ_ASDA_34 = 1951;
    // Southampton Airport
    public static String SOU_RUNWAY_02_20 = "02/20";
    public static int SOU_TORA_02 = 1725;
    public static int SOU_TODA_02 = 1725;
    public static int SOU_LDA_02 = 1725;
    public static int SOU_ASDA_02 = 1725;
    public static int SOU_TORA_20 = 1725;
    public static int SOU_TODA_20 = 1725;
    public static int SOU_LDA_20 = 1725;
    public static int SOU_ASDA_20 = 1725;
    // Cardiff Airport
    public static String CWL_RUNWAY_12_30 = "12/30";
    public static int CWL_TORA_12 = 2145;
    public static int CWL_TODA_12 = 2145;
    public static int CWL_LDA_12 = 2145;
    public static int CWL_ASDA_12 = 2145;
    public static int CWL_TORA_30 = 2145;
    public static int CWL_TODA_30 = 2145;
    public static int CWL_LDA_30 = 2145;
    public static int CWL_ASDA_30 = 2145;
    // Glasgow Prestwick Airport
    public static String PIK_RUNWAY_03_21 = "03/21";
    public static int PIK_TORA_03 = 2099;
    public static int PIK_TODA_03 = 2099;
    public static int PIK_LDA_03 = 2099;
    public static int PIK_ASDA_03 = 2099;
    public static int PIK_TORA_21 = 2099;
    public static int PIK_TODA_21 = 2099;
    public static int PIK_LDA_21 = 2099;
    public static int PIK_ASDA_21 = 2099;

    // London Southend Airport
    public static String SEN_RUNWAY_06_24 = "06/24";
    public static int SEN_TORA_06 = 1791;
    public static int SEN_TODA_06 = 1791;
    public static int SEN_LDA_06 = 1791;
    public static int SEN_ASDA_06 = 1791;
    public static int SEN_TORA_24 = 1791;
    public static int SEN_TODA_24 = 1791;
    public static int SEN_LDA_24 = 1791;
    public static int SEN_ASDA_24 = 1791;

}

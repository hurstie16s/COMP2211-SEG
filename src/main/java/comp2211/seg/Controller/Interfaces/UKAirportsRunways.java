package comp2211.seg.Controller.Interfaces;

import comp2211.seg.ProcessDataModel.Airport;

/**
 * UK Airport Runways data 2021
 */
public interface UKAirportsRunways {
    /**
     * The constant AIRPORT_NAMES.
     */
    String[] AIRPORT_NAMES = {
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
    /**
     * The constant AIRPORT_RUNWAYS.
     */
    String[][] AIRPORT_RUNWAYS = {
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

    /**
     * The constant LHR_RUNWAY_09L_27R.
     */
// London Heathrow Airport
  String LHR_RUNWAY_09L_27R = "09L/27R";
    /**
     * The constant LHR_TORA_09L.
     */
    int LHR_TORA_09L = 3902; // meters
    /**
     * The constant LHR_TODA_09L.
     */
    int LHR_TODA_09L = 3902; // meters
    /**
     * The constant LHR_LDA_09L.
     */
    int LHR_LDA_09L = 3660; // meters
    /**
     * The constant LHR_ASDA_09L.
     */
    int LHR_ASDA_09L = 3902; // meters
    /**
     * The constant LHR_TORA_27R.
     */
    int LHR_TORA_27R = 3902; // meters
    /**
     * The constant LHR_TODA_27R.
     */
    int LHR_TODA_27R = 3902; // meters
    /**
     * The constant LHR_LDA_27R.
     */
    int LHR_LDA_27R = 3660; // meters
    /**
     * The constant LHR_ASDA_27R.
     */
    int LHR_ASDA_27R = 3902; // meters
    /**
     * The constant LHR_RUNWAY_09R_27L.
     */
    String LHR_RUNWAY_09R_27L = "09R/27L";
    /**
     * The constant LHR_TORA_09R.
     */
    int LHR_TORA_09R = 3884; // meters
    /**
     * The constant LHR_TODA_09R.
     */
    int LHR_TODA_09R = 3884; // meters
    /**
     * The constant LHR_LDA_09R.
     */
    int LHR_LDA_09R = 3660; // meters
    /**
     * The constant LHR_ASDA_09R.
     */
    int LHR_ASDA_09R = 3884; // meters
    /**
     * The constant LHR_TORA_27L.
     */
    int LHR_TORA_27L = 3884; // meters
    /**
     * The constant LHR_TODA_27L.
     */
    int LHR_TODA_27L = 3884; // meters
    /**
     * The constant LHR_LDA_27L.
     */
    int LHR_LDA_27L = 3660; // meters
    /**
     * The constant LHR_ASDA_27L.
     */
    int LHR_ASDA_27L = 3884; // meters

    /**
     * The constant LGW_RUNWAY_08L_26R.
     */
// London Gatwick Airport
  String LGW_RUNWAY_08L_26R = "08L/26R";
    /**
     * The constant LGW_TORA_08L.
     */
    int LGW_TORA_08L = 3034; // meters
    /**
     * The constant LGW_TODA_08L.
     */
    int LGW_TODA_08L = 3479; // meters
    /**
     * The constant LGW_LDA_08L.
     */
    int LGW_LDA_08L = 2738; // meters
    /**
     * The constant LGW_ASDA_08L.
     */
    int LGW_ASDA_08L = 3034; // meters
    /**
     * The constant LGW_TORA_26R.
     */
    int LGW_TORA_26R = 3034; // meters
    /**
     * The constant LGW_TODA_26R.
     */
    int LGW_TODA_26R = 3479; // meters
    /**
     * The constant LGW_LDA_26R.
     */
    int LGW_LDA_26R = 3034; // meters
    /**
     * The constant LGW_ASDA_26R.
     */
    int LGW_ASDA_26R = 3479; // meters

    /**
     * The constant LGW_RUNWAY_08R_26L.
     */
    String LGW_RUNWAY_08R_26L = "08R/26L";
    /**
     * The constant LGW_TORA_08R.
     */
    int LGW_TORA_08R = 2866; // meters
    /**
     * The constant LGW_TODA_08R.
     */
    int LGW_TODA_08R = 3479; // meters
    /**
     * The constant LGW_LDA_08R.
     */
    int LGW_LDA_08R = 2661; // meters
    /**
     * The constant LGW_ASDA_08R.
     */
    int LGW_ASDA_08R = 2866; // meters
    /**
     * The constant LGW_TORA_26L.
     */
    int LGW_TORA_26L = 2866; // meters
    /**
     * The constant LGW_TODA_26L.
     */
    int LGW_TODA_26L = 3479; // meters
    /**
     * The constant LGW_LDA_26L.
     */
    int LGW_LDA_26L = 2661; // meters
    /**
     * The constant LGW_ASDA_26L.
     */
    int LGW_ASDA_26L = 2866; // meters

    /**
     * The constant MAN_RUNWAY_05L_23R.
     */
// Manchester Airport
  String MAN_RUNWAY_05L_23R = "05L/23R";
    /**
     * The constant MAN_TORA_05L.
     */
    int MAN_TORA_05L = 3048;
    /**
     * The constant MAN_TODA_05L.
     */
    int MAN_TODA_05L = 3128;
    /**
     * The constant MAN_LDA_05L.
     */
    int MAN_LDA_05L = 2908;
    /**
     * The constant MAN_ASDA_05L.
     */
    int MAN_ASDA_05L = 3048;
    /**
     * The constant MAN_TORA_23R.
     */
    int MAN_TORA_23R = 3048;
    /**
     * The constant MAN_TODA_23R.
     */
    int MAN_TODA_23R = 3128;
    /**
     * The constant MAN_LDA_23R.
     */
    int MAN_LDA_23R = 3048;
    /**
     * The constant MAN_ASDA_23R.
     */
    int MAN_ASDA_23R = 3048;
    /**
     * The constant MAN_RUNWAY_05R_23L.
     */
    String MAN_RUNWAY_05R_23L = "05R/23L";
    /**
     * The constant MAN_TORA_05R.
     */
    int MAN_TORA_05R = 3048;
    /**
     * The constant MAN_TODA_05R.
     */
    int MAN_TODA_05R = 3128;
    /**
     * The constant MAN_LDA_05R.
     */
    int MAN_LDA_05R = 2908;
    /**
     * The constant MAN_ASDA_05R.
     */
    int MAN_ASDA_05R = 3048;
    /**
     * The constant MAN_TORA_23L.
     */
    int MAN_TORA_23L = 3048;
    /**
     * The constant MAN_TODA_23L.
     */
    int MAN_TODA_23L = 3128;
    /**
     * The constant MAN_LDA_23L.
     */
    int MAN_LDA_23L = 3048;
    /**
     * The constant MAN_ASDA_23L.
     */
    int MAN_ASDA_23L = 3048;

    /**
     * The constant STN_RUNWAY_04_22.
     */
// London Stansted Airport
  String STN_RUNWAY_04_22 = "04/22";
    /**
     * The constant STN_TORA_04.
     */
    int STN_TORA_04 = 3048;
    /**
     * The constant STN_TODA_04.
     */
    int STN_TODA_04 = 3048;
    /**
     * The constant STN_LDA_04.
     */
    int STN_LDA_04 = 2744;
    /**
     * The constant STN_ASDA_04.
     */
    int STN_ASDA_04 = 3048;
    /**
     * The constant STN_TORA_22.
     */
    int STN_TORA_22 = 3048;
    /**
     * The constant STN_TODA_22.
     */
    int STN_TODA_22 = 3048;
    /**
     * The constant STN_LDA_22.
     */
    int STN_LDA_22 = 3048;
    /**
     * The constant STN_ASDA_22.
     */
    int STN_ASDA_22 = 3048;

    /**
     * The constant LTN_RUNWAY_08_26.
     */
// London Luton Airport
  String LTN_RUNWAY_08_26 = "08/26";
    /**
     * The constant LTN_TORA_08.
     */
    int LTN_TORA_08 = 2164;
    /**
     * The constant LTN_TODA_08.
     */
    int LTN_TODA_08 = 2164;
    /**
     * The constant LTN_LDA_08.
     */
    int LTN_LDA_08 = 1939;
    /**
     * The constant LTN_ASDA_08.
     */
    int LTN_ASDA_08 = 2164;
    /**
     * The constant LTN_TORA_26.
     */
    int LTN_TORA_26 = 2164;
    /**
     * The constant LTN_TODA_26.
     */
    int LTN_TODA_26 = 2164;
    /**
     * The constant LTN_LDA_26.
     */
    int LTN_LDA_26 = 1939;
    /**
     * The constant LTN_ASDA_26.
     */
    int LTN_ASDA_26 = 2164;

    /**
     * The constant EDI_RUNWAY_06_24.
     */
// Edinburgh Airport
  String EDI_RUNWAY_06_24 = "06/24";
    /**
     * The constant EDI_TORA_06.
     */
    int EDI_TORA_06 = 2011;
    /**
     * The constant EDI_TODA_06.
     */
    int EDI_TODA_06 = 2111;
    /**
     * The constant EDI_LDA_06.
     */
    int EDI_LDA_06 = 1893;
    /**
     * The constant EDI_ASDA_06.
     */
    int EDI_ASDA_06 = 2011;
    /**
     * The constant EDI_TORA_24.
     */
    int EDI_TORA_24 = 2011;
    /**
     * The constant EDI_TODA_24.
     */
    int EDI_TODA_24 = 2111;
    /**
     * The constant EDI_LDA_24.
     */
    int EDI_LDA_24 = 2011;
    /**
     * The constant EDI_ASDA_24.
     */
    int EDI_ASDA_24 = 2011;

    /**
     * The constant BHX_RUNWAY_15_33.
     */
// Birmingham Airport
  String BHX_RUNWAY_15_33 = "15/33";
    /**
     * The constant BHX_TORA_15.
     */
    int BHX_TORA_15 = 2728;
    /**
     * The constant BHX_TODA_15.
     */
    int BHX_TODA_15 = 2996;
    /**
     * The constant BHX_LDA_15.
     */
    int BHX_LDA_15 = 2551;
    /**
     * The constant BHX_ASDA_15.
     */
    int BHX_ASDA_15 = 2728;
    /**
     * The constant BHX_TORA_33.
     */
    int BHX_TORA_33 = 2728;
    /**
     * The constant BHX_TODA_33.
     */
    int BHX_TODA_33 = 2996;
    /**
     * The constant BHX_LDA_33.
     */
    int BHX_LDA_33 = 2728;
    /**
     * The constant BHX_ASDA_33.
     */
    int BHX_ASDA_33 = 2728;


    /**
     * The constant GLA_RUNWAY_05_23.
     */
// Glasgow Airport
  String GLA_RUNWAY_05_23 = "05/23";
    /**
     * The constant GLA_TORA_05.
     */
    int GLA_TORA_05 = 2217;
    /**
     * The constant GLA_TODA_05.
     */
    int GLA_TODA_05 = 2217;
    /**
     * The constant GLA_LDA_05.
     */
    int GLA_LDA_05 = 2083;
    /**
     * The constant GLA_ASDA_05.
     */
    int GLA_ASDA_05 = 2217;
    /**
     * The constant GLA_TORA_23.
     */
    int GLA_TORA_23 = 2217;
    /**
     * The constant GLA_TODA_23.
     */
    int GLA_TODA_23 = 2217;
    /**
     * The constant GLA_LDA_23.
     */
    int GLA_LDA_23 = 2217;
    /**
     * The constant GLA_ASDA_23.
     */
    int GLA_ASDA_23 = 2217;

    /**
     * The constant BRS_RUNWAY_09_27.
     */
// Bristol Airport
  String BRS_RUNWAY_09_27 = "09/27";
    /**
     * The constant BRS_TORA_09.
     */
    int BRS_TORA_09 = 2010;
    /**
     * The constant BRS_TODA_09.
     */
    int BRS_TODA_09 = 2010;
    /**
     * The constant BRS_LDA_09.
     */
    int BRS_LDA_09 = 1829;
    /**
     * The constant BRS_ASDA_09.
     */
    int BRS_ASDA_09 = 2010;
    /**
     * The constant BRS_TORA_27.
     */
    int BRS_TORA_27 = 2010;
    /**
     * The constant BRS_TODA_27.
     */
    int BRS_TODA_27 = 2010;
    /**
     * The constant BRS_LDA_27.
     */
    int BRS_LDA_27 = 1900;
    /**
     * The constant BRS_ASDA_27.
     */
    int BRS_ASDA_27 = 2010;

    /**
     * The constant NCL_RUNWAY_07_25.
     */
// Newcastle Airport
  String NCL_RUNWAY_07_25 = "07/25";
    /**
     * The constant NCL_TORA_07.
     */
    int NCL_TORA_07 = 1962;
    /**
     * The constant NCL_TODA_07.
     */
    int NCL_TODA_07 = 2127;
    /**
     * The constant NCL_LDA_07.
     */
    int NCL_LDA_07 = 1829;
    /**
     * The constant NCL_ASDA_07.
     */
    int NCL_ASDA_07 = 1962;
    /**
     * The constant NCL_TORA_25.
     */
    int NCL_TORA_25 = 1962;
    /**
     * The constant NCL_TODA_25.
     */
    int NCL_TODA_25 = 2127;
    /**
     * The constant NCL_LDA_25.
     */
    int NCL_LDA_25 = 1962;
    /**
     * The constant NCL_ASDA_25.
     */
    int NCL_ASDA_25 = 1962;

    /**
     * The constant EMA_RUNWAY_09_27.
     */
// East Midlands Airport
  String EMA_RUNWAY_09_27 = "09/27";
    /**
     * The constant EMA_TORA_09.
     */
    int EMA_TORA_09 = 2926;
    /**
     * The constant EMA_TODA_09.
     */
    int EMA_TODA_09 = 2926;
    /**
     * The constant EMA_LDA_09.
     */
    int EMA_LDA_09 = 2761;
    /**
     * The constant EMA_ASDA_09.
     */
    int EMA_ASDA_09 = 2926;
    /**
     * The constant EMA_TORA_27.
     */
    int EMA_TORA_27 = 2926;
    /**
     * The constant EMA_TODA_27.
     */
    int EMA_TODA_27 = 2926;
    /**
     * The constant EMA_LDA_27.
     */
    int EMA_LDA_27 = 2761;
    /**
     * The constant EMA_ASDA_27.
     */
    int EMA_ASDA_27 = 2926;

    /**
     * The constant LPL_RUNWAY_09_27.
     */
// Liverpool John Lennon Airport
  String LPL_RUNWAY_09_27 = "09/27";
    /**
     * The constant LPL_TORA_09.
     */
    int LPL_TORA_09 = 2134;
    /**
     * The constant LPL_TODA_09.
     */
    int LPL_TODA_09 = 2310;
    /**
     * The constant LPL_LDA_09.
     */
    int LPL_LDA_09 = 2134;
    /**
     * The constant LPL_ASDA_09.
     */
    int LPL_ASDA_09 = 2134;
    /**
     * The constant LPL_TORA_27.
     */
    int LPL_TORA_27 = 2134;
    /**
     * The constant LPL_TODA_27.
     */
    int LPL_TODA_27 = 2310;
    /**
     * The constant LPL_LDA_27.
     */
    int LPL_LDA_27 = 2134;
    /**
     * The constant LPL_ASDA_27.
     */
    int LPL_ASDA_27 = 2134;

    /**
     * The constant LCY_RUNWAY_09_27.
     */
// London City Airport
  String LCY_RUNWAY_09_27 = "09/27";
    /**
     * The constant LCY_TORA_09.
     */
    int LCY_TORA_09 = 1509;
    /**
     * The constant LCY_TODA_09.
     */
    int LCY_TODA_09 = 1509;
    /**
     * The constant LCY_LDA_09.
     */
    int LCY_LDA_09 = 1509;
    /**
     * The constant LCY_ASDA_09.
     */
    int LCY_ASDA_09 = 1509;
    /**
     * The constant LCY_TORA_27.
     */
    int LCY_TORA_27 = 1509;
    /**
     * The constant LCY_TODA_27.
     */
    int LCY_TODA_27 = 1509;
    /**
     * The constant LCY_LDA_27.
     */
    int LCY_LDA_27 = 1509;
    /**
     * The constant LCY_ASDA_27.
     */
    int LCY_ASDA_27 = 1509;

    /**
     * The constant LBA_RUNWAY_14_32.
     */
// Leeds Bradford Airport
  String LBA_RUNWAY_14_32 = "14/32";
    /**
     * The constant LBA_TORA_14.
     */
    int LBA_TORA_14 = 1723;
    /**
     * The constant LBA_TODA_14.
     */
    int LBA_TODA_14 = 1723;
    /**
     * The constant LBA_LDA_14.
     */
    int LBA_LDA_14 = 1607;
    /**
     * The constant LBA_ASDA_14.
     */
    int LBA_ASDA_14 = 1723;
    /**
     * The constant LBA_TORA_32.
     */
    int LBA_TORA_32 = 1723;
    /**
     * The constant LBA_TODA_32.
     */
    int LBA_TODA_32 = 1723;
    /**
     * The constant LBA_LDA_32.
     */
    int LBA_LDA_32 = 1723;
    /**
     * The constant LBA_ASDA_32.
     */
    int LBA_ASDA_32 = 1723;


    /**
     * The constant BFS_RUNWAY_07_25.
     */
// Belfast International Airport
  String BFS_RUNWAY_07_25 = "07/25";
    /**
     * The constant BFS_TORA_07.
     */
    int BFS_TORA_07 = 2933;
    /**
     * The constant BFS_TODA_07.
     */
    int BFS_TODA_07 = 2933;
    /**
     * The constant BFS_LDA_07.
     */
    int BFS_LDA_07 = 2755;
    /**
     * The constant BFS_ASDA_07.
     */
    int BFS_ASDA_07 = 2933;
    /**
     * The constant BFS_TORA_25.
     */
    int BFS_TORA_25 = 2933;
    /**
     * The constant BFS_TODA_25.
     */
    int BFS_TODA_25 = 2933;
    /**
     * The constant BFS_LDA_25.
     */
    int BFS_LDA_25 = 2755;
    /**
     * The constant BFS_ASDA_25.
     */
    int BFS_ASDA_25 = 2933;

    /**
     * The constant ABZ_RUNWAY_16_34.
     */
// Aberdeen International Airport
  String ABZ_RUNWAY_16_34 = "16/34";
    /**
     * The constant ABZ_TORA_16.
     */
    int ABZ_TORA_16 = 1951;
    /**
     * The constant ABZ_TODA_16.
     */
    int ABZ_TODA_16 = 2106;
    /**
     * The constant ABZ_LDA_16.
     */
    int ABZ_LDA_16 = 1951;
    /**
     * The constant ABZ_ASDA_16.
     */
    int ABZ_ASDA_16 = 1951;
    /**
     * The constant ABZ_TORA_34.
     */
    int ABZ_TORA_34 = 1951;
    /**
     * The constant ABZ_TODA_34.
     */
    int ABZ_TODA_34 = 1951;
    /**
     * The constant ABZ_LDA_34.
     */
    int ABZ_LDA_34 = 1834;
    /**
     * The constant ABZ_ASDA_34.
     */
    int ABZ_ASDA_34 = 1951;

    /**
     * The constant SOU_RUNWAY_02_20.
     */
// Southampton Airport
  String SOU_RUNWAY_02_20 = "02/20";
    /**
     * The constant SOU_TORA_02.
     */
    int SOU_TORA_02 = 1725;
    /**
     * The constant SOU_TODA_02.
     */
    int SOU_TODA_02 = 1725;
    /**
     * The constant SOU_LDA_02.
     */
    int SOU_LDA_02 = 1725;
    /**
     * The constant SOU_ASDA_02.
     */
    int SOU_ASDA_02 = 1725;
    /**
     * The constant SOU_TORA_20.
     */
    int SOU_TORA_20 = 1725;
    /**
     * The constant SOU_TODA_20.
     */
    int SOU_TODA_20 = 1725;
    /**
     * The constant SOU_LDA_20.
     */
    int SOU_LDA_20 = 1725;
    /**
     * The constant SOU_ASDA_20.
     */
    int SOU_ASDA_20 = 1725;

    /**
     * The constant CWL_RUNWAY_12_30.
     */
// Cardiff Airport
  String CWL_RUNWAY_12_30 = "12/30";
    /**
     * The constant CWL_TORA_12.
     */
    int CWL_TORA_12 = 2145;
    /**
     * The constant CWL_TODA_12.
     */
    int CWL_TODA_12 = 2145;
    /**
     * The constant CWL_LDA_12.
     */
    int CWL_LDA_12 = 2145;
    /**
     * The constant CWL_ASDA_12.
     */
    int CWL_ASDA_12 = 2145;
    /**
     * The constant CWL_TORA_30.
     */
    int CWL_TORA_30 = 2145;
    /**
     * The constant CWL_TODA_30.
     */
    int CWL_TODA_30 = 2145;
    /**
     * The constant CWL_LDA_30.
     */
    int CWL_LDA_30 = 2145;
    /**
     * The constant CWL_ASDA_30.
     */
    int CWL_ASDA_30 = 2145;

    /**
     * The constant PIK_RUNWAY_03_21.
     */
// Glasgow Prestwick Airport
  String PIK_RUNWAY_03_21 = "03/21";
    /**
     * The constant PIK_TORA_03.
     */
    int PIK_TORA_03 = 2099;
    /**
     * The constant PIK_TODA_03.
     */
    int PIK_TODA_03 = 2099;
    /**
     * The constant PIK_LDA_03.
     */
    int PIK_LDA_03 = 2099;
    /**
     * The constant PIK_ASDA_03.
     */
    int PIK_ASDA_03 = 2099;
    /**
     * The constant PIK_TORA_21.
     */
    int PIK_TORA_21 = 2099;
    /**
     * The constant PIK_TODA_21.
     */
    int PIK_TODA_21 = 2099;
    /**
     * The constant PIK_LDA_21.
     */
    int PIK_LDA_21 = 2099;
    /**
     * The constant PIK_ASDA_21.
     */
    int PIK_ASDA_21 = 2099;

    /**
     * The constant SEN_RUNWAY_06_24.
     */
// London Southend Airport
  String SEN_RUNWAY_06_24 = "06/24";
    /**
     * The constant SEN_TORA_06.
     */
    int SEN_TORA_06 = 1791;
    /**
     * The constant SEN_TODA_06.
     */
    int SEN_TODA_06 = 1791;
    /**
     * The constant SEN_LDA_06.
     */
    int SEN_LDA_06 = 1791;
    /**
     * The constant SEN_ASDA_06.
     */
    int SEN_ASDA_06 = 1791;
    /**
     * The constant SEN_TORA_24.
     */
    int SEN_TORA_24 = 1791;
    /**
     * The constant SEN_TODA_24.
     */
    int SEN_TODA_24 = 1791;
    /**
     * The constant SEN_LDA_24.
     */
    int SEN_LDA_24 = 1791;
    /**
     * The constant SEN_ASDA_24.
     */
    int SEN_ASDA_24 = 1791;

}



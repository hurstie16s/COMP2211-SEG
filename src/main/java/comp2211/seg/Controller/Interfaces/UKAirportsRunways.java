package comp2211.seg.Controller.Interfaces;

/**
 * UK Airport Runways data 2021
 */
public interface UKAirportsRunways {
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
  // London Heathrow Airport
  String LHR_RUNWAY_09L_27R = "09L/27R";
  int LHR_TORA_09L = 3902; // meters
  int LHR_TODA_09L = 3902; // meters
  int LHR_LDA_09L = 3660; // meters
  int LHR_ASDA_09L = 3902; // meters
  int LHR_TORA_27R = 3902; // meters
  int LHR_TODA_27R = 3902; // meters
  int LHR_LDA_27R = 3660; // meters
  int LHR_ASDA_27R = 3902; // meters
  String LHR_RUNWAY_09R_27L = "09R/27L";
  int LHR_TORA_09R = 3884; // meters
  int LHR_TODA_09R = 3884; // meters
  int LHR_LDA_09R = 3660; // meters
  int LHR_ASDA_09R = 3884; // meters
  int LHR_TORA_27L = 3884; // meters
  int LHR_TODA_27L = 3884; // meters
  int LHR_LDA_27L = 3660; // meters
  int LHR_ASDA_27L = 3884; // meters

  // London Gatwick Airport
  String LGW_RUNWAY_08L_26R = "08L/26R";
  int LGW_TORA_08L = 3034; // meters
  int LGW_TODA_08L = 3479; // meters
  int LGW_LDA_08L = 2738; // meters
  int LGW_ASDA_08L = 3034; // meters
  int LGW_TORA_26R = 3034; // meters
  int LGW_TODA_26R = 3479; // meters
  int LGW_LDA_26R = 3034; // meters
  int LGW_ASDA_26R = 3479; // meters

  String LGW_RUNWAY_08R_26L = "08R/26L";
  int LGW_TORA_08R = 2866; // meters
  int LGW_TODA_08R = 3479; // meters
  int LGW_LDA_08R = 2661; // meters
  int LGW_ASDA_08R = 2866; // meters
  int LGW_TORA_26L = 2866; // meters
  int LGW_TODA_26L = 3479; // meters
  int LGW_LDA_26L = 2661; // meters
  int LGW_ASDA_26L = 2866; // meters

  // Manchester Airport
  String MAN_RUNWAY_05L_23R = "05L/23R";
  int MAN_TORA_05L = 3048;
  int MAN_TODA_05L = 3128;
  int MAN_LDA_05L = 2908;
  int MAN_ASDA_05L = 3048;
  int MAN_TORA_23R = 3048;
  int MAN_TODA_23R = 3128;
  int MAN_LDA_23R = 3048;
  int MAN_ASDA_23R = 3048;
  String MAN_RUNWAY_05R_23L = "05R/23L";
  int MAN_TORA_05R = 3048;
  int MAN_TODA_05R = 3128;
  int MAN_LDA_05R = 2908;
  int MAN_ASDA_05R = 3048;
  int MAN_TORA_23L = 3048;
  int MAN_TODA_23L = 3128;
  int MAN_LDA_23L = 3048;
  int MAN_ASDA_23L = 3048;

  // London Stansted Airport
  String STN_RUNWAY_04_22 = "04/22";
  int STN_TORA_04 = 3048;
  int STN_TODA_04 = 3048;
  int STN_LDA_04 = 2744;
  int STN_ASDA_04 = 3048;
  int STN_TORA_22 = 3048;
  int STN_TODA_22 = 3048;
  int STN_LDA_22 = 3048;
  int STN_ASDA_22 = 3048;

  // London Luton Airport
  String LTN_RUNWAY_08_26 = "08/26";
  int LTN_TORA_08 = 2164;
  int LTN_TODA_08 = 2164;
  int LTN_LDA_08 = 1939;
  int LTN_ASDA_08 = 2164;
  int LTN_TORA_26 = 2164;
  int LTN_TODA_26 = 2164;
  int LTN_LDA_26 = 1939;
  int LTN_ASDA_26 = 2164;

  // Edinburgh Airport
  String EDI_RUNWAY_06_24 = "06/24";
  int EDI_TORA_06 = 2011;
  int EDI_TODA_06 = 2111;
  int EDI_LDA_06 = 1893;
  int EDI_ASDA_06 = 2011;
  int EDI_TORA_24 = 2011;
  int EDI_TODA_24 = 2111;
  int EDI_LDA_24 = 2011;
  int EDI_ASDA_24 = 2011;

  // Birmingham Airport
  String BHX_RUNWAY_15_33 = "15/33";
  int BHX_TORA_15 = 2728;
  int BHX_TODA_15 = 2996;
  int BHX_LDA_15 = 2551;
  int BHX_ASDA_15 = 2728;
  int BHX_TORA_33 = 2728;
  int BHX_TODA_33 = 2996;
  int BHX_LDA_33 = 2728;
  int BHX_ASDA_33 = 2728;


  // Glasgow Airport
  String GLA_RUNWAY_05_23 = "05/23";
  int GLA_TORA_05 = 2217;
  int GLA_TODA_05 = 2217;
  int GLA_LDA_05 = 2083;
  int GLA_ASDA_05 = 2217;
  int GLA_TORA_23 = 2217;
  int GLA_TODA_23 = 2217;
  int GLA_LDA_23 = 2217;
  int GLA_ASDA_23 = 2217;

  // Bristol Airport
  String BRS_RUNWAY_09_27 = "09/27";
  int BRS_TORA_09 = 2010;
  int BRS_TODA_09 = 2010;
  int BRS_LDA_09 = 1829;
  int BRS_ASDA_09 = 2010;
  int BRS_TORA_27 = 2010;
  int BRS_TODA_27 = 2010;
  int BRS_LDA_27 = 1900;
  int BRS_ASDA_27 = 2010;

  // Newcastle Airport
  String NCL_RUNWAY_07_25 = "07/25";
  int NCL_TORA_07 = 1962;
  int NCL_TODA_07 = 2127;
  int NCL_LDA_07 = 1829;
  int NCL_ASDA_07 = 1962;
  int NCL_TORA_25 = 1962;
  int NCL_TODA_25 = 2127;
  int NCL_LDA_25 = 1962;
  int NCL_ASDA_25 = 1962;

  // East Midlands Airport
  String EMA_RUNWAY_09_27 = "09/27";
  int EMA_TORA_09 = 2926;
  int EMA_TODA_09 = 2926;
  int EMA_LDA_09 = 2761;
  int EMA_ASDA_09 = 2926;
  int EMA_TORA_27 = 2926;
  int EMA_TODA_27 = 2926;
  int EMA_LDA_27 = 2761;
  int EMA_ASDA_27 = 2926;

  // Liverpool John Lennon Airport
  String LPL_RUNWAY_09_27 = "09/27";
  int LPL_TORA_09 = 2134;
  int LPL_TODA_09 = 2310;
  int LPL_LDA_09 = 2134;
  int LPL_ASDA_09 = 2134;
  int LPL_TORA_27 = 2134;
  int LPL_TODA_27 = 2310;
  int LPL_LDA_27 = 2134;
  int LPL_ASDA_27 = 2134;

  // London City Airport
  String LCY_RUNWAY_09_27 = "09/27";
  int LCY_TORA_09 = 1509;
  int LCY_TODA_09 = 1509;
  int LCY_LDA_09 = 1509;
  int LCY_ASDA_09 = 1509;
  int LCY_TORA_27 = 1509;
  int LCY_TODA_27 = 1509;
  int LCY_LDA_27 = 1509;
  int LCY_ASDA_27 = 1509;

  // Leeds Bradford Airport
  String LBA_RUNWAY_14_32 = "14/32";
  int LBA_TORA_14 = 1723;
  int LBA_TODA_14 = 1723;
  int LBA_LDA_14 = 1607;
  int LBA_ASDA_14 = 1723;
  int LBA_TORA_32 = 1723;
  int LBA_TODA_32 = 1723;
  int LBA_LDA_32 = 1723;
  int LBA_ASDA_32 = 1723;


  // Belfast International Airport
  String BFS_RUNWAY_07_25 = "07/25";
  int BFS_TORA_07 = 2933;
  int BFS_TODA_07 = 2933;
  int BFS_LDA_07 = 2755;
  int BFS_ASDA_07 = 2933;
  int BFS_TORA_25 = 2933;
  int BFS_TODA_25 = 2933;
  int BFS_LDA_25 = 2755;
  int BFS_ASDA_25 = 2933;

  // Aberdeen International Airport
  String ABZ_RUNWAY_16_34 = "16/34";
  int ABZ_TORA_16 = 1951;
  int ABZ_TODA_16 = 2106;
  int ABZ_LDA_16 = 1951;
  int ABZ_ASDA_16 = 1951;
  int ABZ_TORA_34 = 1951;
  int ABZ_TODA_34 = 1951;
  int ABZ_LDA_34 = 1834;
  int ABZ_ASDA_34 = 1951;

  // Southampton Airport
  String SOU_RUNWAY_02_20 = "02/20";
  int SOU_TORA_02 = 1725;
  int SOU_TODA_02 = 1725;
  int SOU_LDA_02 = 1725;
  int SOU_ASDA_02 = 1725;
  int SOU_TORA_20 = 1725;
  int SOU_TODA_20 = 1725;
  int SOU_LDA_20 = 1725;
  int SOU_ASDA_20 = 1725;

  // Cardiff Airport
  String CWL_RUNWAY_12_30 = "12/30";
  int CWL_TORA_12 = 2145;
  int CWL_TODA_12 = 2145;
  int CWL_LDA_12 = 2145;
  int CWL_ASDA_12 = 2145;
  int CWL_TORA_30 = 2145;
  int CWL_TODA_30 = 2145;
  int CWL_LDA_30 = 2145;
  int CWL_ASDA_30 = 2145;

  // Glasgow Prestwick Airport
  String PIK_RUNWAY_03_21 = "03/21";
  int PIK_TORA_03 = 2099;
  int PIK_TODA_03 = 2099;
  int PIK_LDA_03 = 2099;
  int PIK_ASDA_03 = 2099;
  int PIK_TORA_21 = 2099;
  int PIK_TODA_21 = 2099;
  int PIK_LDA_21 = 2099;
  int PIK_ASDA_21 = 2099;

  // London Southend Airport
  String SEN_RUNWAY_06_24 = "06/24";
  int SEN_TORA_06 = 1791;
  int SEN_TODA_06 = 1791;
  int SEN_LDA_06 = 1791;
  int SEN_ASDA_06 = 1791;
  int SEN_TORA_24 = 1791;
  int SEN_TODA_24 = 1791;
  int SEN_LDA_24 = 1791;
  int SEN_ASDA_24 = 1791;

}



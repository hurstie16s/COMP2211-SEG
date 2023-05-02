
//testing changing airports and scroll bar in base scene
function Test1()
{
 TestedApps.run1.Run(1, true);
  let java = Aliases.java;
  let stage = java.stageHeathrowLhr;
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  stage.basescene.Click(368, 132);
  let comboBoxPopupControl_3 = java.comboboxpopupcontrol3;
  comboBoxPopupControl_3.Drag(309, 60, -4, 14);
  comboBoxPopupControl_3.scene21.list_view.comboboxlistviewskin21EdinburghE.Click(186, 12);
  stage.Close();
  
}

// Testing changing airport and runway in base scene
function Test2()
{
 // OCR.Recognize(Aliases.explorer.wndShell_TrayWnd.ReBarWindow32.MSTaskSwWClass.MSTaskListWClass).BlockByText("lila").Click();
  TestedApps.run1.Run(1, true);
  let java = Aliases.java;
  let stage = java.stageHeathrowLhr;
  stage.homescene.Click(849, 567);
  stage.basescene.Click(387, 147);
  let comboBoxPopupControl_3 = java.comboboxpopupcontrol3;
  comboBoxPopupControl_3.scene21.list_view.comboboxlistviewskin21StanstedSt.Click(201, 9);
  java.stageGatwickLgw.basescene.Click(285, 210);
  OCR.Recognize(comboBoxPopupControl_3).BlockByText("26L").Click();
  stage.Close();
}

// testing all exporting UI
function Test3()
{
  TestedApps.run1.Run(1, true);
  let java = Aliases.java;
  let stage = java.stageHeathrowLhr;
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  let baseScene = stage.basescene;
  OCR.Recognize(baseScene.tabbuttonAirportConfiguration).BlockByText("File").Click();
  baseScene.menubar.JavaFXMenu.Click("File|Export to XML|Export Airport...");
  stage.Close();
  
  TestedApps.run.Run(1, true);
  stage.Activate();
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  baseScene.tabbuttonAirportConfiguration.Click(69, 13);
  baseScene.menubar.JavaFXMenu.Click("File|Export to XML|Export Airport...");
  stage.Close();
  
  TestedApps.run.Run(1, true);
  stage.Activate();
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  baseScene.tabbuttonAirportConfiguration.Click(69, 13);
  baseScene.menubar.JavaFXMenu.Click("File|Export to XML|Export Airport & Obstacle...");
  stage.Close();
}

// Testing changing runways and airports on home page. 
function Test4()
{
  TestedApps.run.Run(1, true);
  let java = Aliases.java;
  let stage = java.stageHeathrowLhr;
  let homeScene = stage.homescene;
  homeScene.Click(864, 485);
  let comboBoxPopupControl_3 = java.comboboxpopupcontrol3;
  OCR.Recognize(comboBoxPopupControl_3).BlockByText("LGW").Click();
  let homeScene2 = java.stageGatwickLgw.homescene;
  OCR.Recognize(homeScene2).BlockByText("LGW").Click();
  OCR.Recognize(comboBoxPopupControl_3).BlockByText("Edinburgh (EDI)").Click();
  OCR.Recognize(homeScene2).BlockByText("Edinburgh").Click();
  OCR.Recognize(comboBoxPopupControl_3).BlockByText("LHR").Click();
  OCR.Recognize(homeScene).BlockByText("27R").Click();
  comboBoxPopupControl_3.Click(77, 39);
  stage.Close();
}

// Testing all import UI options
function Test5()
{ 
    //Testing drop down menu import UI
  TestedApps.run.Run(1, true);
  Aliases.explorer.wndShell_TrayWnd.ReBarWindow32.MSTaskSwWClass.MSTaskListWClass.Click(413, 21);
  let java = Aliases.java;
  let stage = java.stageHeathrowLhr;
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  let baseScene = stage.basescene;
  OCR.Recognize(baseScene.tabbuttonAirportConfiguration).BlockByText("File").Click();
  baseScene.menubar.JavaFXMenu.Click("File|Import from XML|Import Obstacle...");
  stage.Close();
   
  TestedApps.run.Run(1, true);
  Aliases.explorer.wndShell_TrayWnd.ReBarWindow32.MSTaskSwWClass.MSTaskListWClass.Click(413, 21);
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  OCR.Recognize(baseScene.tabbuttonAirportConfiguration).BlockByText("File").Click();
  baseScene.menubar.JavaFXMenu.Click("File|Import from XML|Import Airport & Obstacle...");
  OCR.Recognize(baseScene.tabbuttonAirportConfiguration).BlockByText("File").Click();
  stage.Close();
  // Testing the home page import UI
  TestedApps.run.Run(1, true);
  OCR.Recognize(stage.homescene).BlockByText("Airport", spRightMost).Click();
  stage.Close();
}

// Testing Obstacle configuration and changing values
function Test6()
{
  TestedApps.run.Run(1, true);
  let stage = Aliases.java.stageHeathrowLhr;
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  let baseScene = stage.basescene;
  OCR.Recognize(baseScene).BlockByText("Configuration", spNearestToCenter).Click();
  OCR.Recognize(baseScene.labelDesignator).BlockByText("Ye").Click();
  let vlabel = baseScene.labelTopLandingTakeOff;
  vlabel.Click(4, 16);
  vlabel.Click(8, 14);
  vlabel.Click(8, 14);
  vlabel.Click(8, 14);
  vlabel.Click(9, 14);
  baseScene.Drag(257, 235, -88, -2);
  baseScene.spinner.Keys("40");
  baseScene.Click(263, 286);
  baseScene.spinner2.Keys("[BS][BS]35");
  baseScene.togglebuttonTowards.Click(4, 7);
  baseScene.Click(727, 234);
  baseScene.Drag(634, 142, 41, 0);
  stage.Close();
}

// Testing dragging the obstacle configuration boxes
function Test7()
{
  let stage = Aliases.java.stageHeathrowLhr;
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  let baseScene = stage.basescene;
  OCR.Recognize(baseScene).BlockByText("Configuration", spNearestToCenter).Click();
  baseScene.Drag(253, 661, 13, -282);
  baseScene.Drag(254, 665, 80, -285);
  baseScene.Drag(187, 384, 595, 107);
  baseScene.Drag(237, 92, 55, 273);
  OCR.Recognize(baseScene).BlockByText("History").Click();
  OCR.Recognize(baseScene).BlockByText("Maths", spNearestToCenter).Click();
  baseScene.Drag(237, 663, 476, 14);
  baseScene.Drag(503, 660, -327, 0);
  let scrollPane = baseScene.scrollpane;
  scrollPane.Drag(228, 76, 45, 281);
  scrollPane.Drag(130, 75, 250, 274);
  baseScene.Drag(512, 382, -291, -5);
  scrollPane.Click(256, 72);
  stage.Close();
}

//Testing the ESC key on each scene and exiting app via ESC
function Test8()
{
  let stage = Aliases.java.stageHeathrowLhr;
  let homeScene = stage.homescene;
  OCR.Recognize(homeScene).BlockByText("Application").Click();
  let baseScene = stage.basescene;
  baseScene.combobox.Keys("[Esc]");
  OCR.Recognize(homeScene).BlockByText("Application").Click();
  OCR.Recognize(baseScene).BlockByText("Configuration", spNearestToCenter).Click();
  baseScene.Click(989, 244);
  let button = stage.runwaysceneloader.buttonAlign;
  button.Keys("[Esc]");
  baseScene.Click(1016, 568);
  button.Keys("[Esc]");
  baseScene.togglebuttonTowards.Keys("[Esc]");
  homeScene.combobox.Keys("[Esc]");
}


 
// Testing TopView 3D model manipulation
function Test9()
{
  let stage = Aliases.java.stageHeathrowLhr;
  let homeScene = stage.homescene;
  OCR.Recognize(homeScene).BlockByText("Application").Click();
  let baseScene = stage.basescene;
  OCR.Recognize(baseScene).BlockByText("Configuration", spNearestToCenter).Click();
  OCR.Recognize(baseScene).BlockByText("Yes").Click();
  baseScene.Click(1059, 276);
  let runwaySceneLoader = stage.runwaysceneloader;
  runwaySceneLoader.Drag(824, 273, -184, 82);
  runwaySceneLoader.MouseWheel(-18);
  stage.Drag(1263, 586, -122, -22);
  runwaySceneLoader.MouseWheel(21);
  runwaySceneLoader.buttonAlign.Keys("[Esc]");
  baseScene.togglebuttonTowards.Keys("[Esc]");
  homeScene.combobox.Keys("[Esc]");
}

// Testing SideView 3D model manipulation 
function Test10()
{
  let stage = Aliases.java.stageHeathrowLhr;
  OCR.Recognize(stage.homescene).BlockByText("Start Application").Click();
  let baseScene = stage.basescene;
  OCR.Recognize(baseScene).BlockByText("Configuration", spNearestToCenter).Click();
  baseScene.Click(215, 340);
  baseScene.Click(1056, 652);
  let runwaySceneLoader = stage.runwaysceneloader;
  runwaySceneLoader.Drag(897, 512, -190, 0);
  runwaySceneLoader.Drag(693, 529, 365, 27);
  runwaySceneLoader.Drag(878, 331, 8, 240);
  runwaySceneLoader.Drag(820, 667, 31, 29);
  runwaySceneLoader.MouseWheel(-20);
  runwaySceneLoader.Drag(835, 487, 4, -88);
  runwaySceneLoader.MouseWheel(-34);
  runwaySceneLoader.Drag(1265, 362, -57, 94);
  runwaySceneLoader.MouseWheel(79);
  OCR.Recognize(runwaySceneLoader).BlockByText("Align").Click();
  stage.Close();
}

//Testing 3D modelling re-Allignment
function Test11()
{
  let stage = Aliases.java.stageHeathrowLhr;
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  let baseScene = stage.basescene;
  OCR.Recognize(baseScene).BlockByText("Configuration", spNearestToCenter).Click();
  baseScene.Click(1131, 332);
  let runwaySceneLoader = stage.runwaysceneloader;
  runwaySceneLoader.Drag(993, 326, -40, 244);
  runwaySceneLoader.Drag(1145, 315, -357, 39);
  runwaySceneLoader.MouseWheel(-45);
  runwaySceneLoader.Click(74, 56);
  let button = runwaySceneLoader.buttonAlign;
  button.Keys("[Esc]");
  baseScene.Click(972, 622);
  runwaySceneLoader.Drag(889, 337, 154, 435);
  runwaySceneLoader.Drag(905, 445, -242, 162);
  runwaySceneLoader.MouseWheel(-32);
  OCR.Recognize(runwaySceneLoader).BlockByText("Align").Click();
  button.Keys("[Esc]");
  OCR.Recognize(baseScene).BlockByText("Both Views Side-On Views Top-Down Views").Click();
  baseScene.Click(1125, 350);
  runwaySceneLoader.Drag(841, 198, -6, 85);
  stage.Close();
}

// Testing help menu toggle
function Test12()
{
  let java = Aliases.java;
  let stage = java.stageHeathrowLhr;
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  let baseScene = stage.basescene;
  baseScene.menubar.Click(197, 25);
  OCR.Recognize(java.contextmenu3).BlockByText("fi Help menu").Click();
  baseScene.combobox.Keys("hhh");
  stage.Close();
}

// Testing changing the theme
function Test13()
{
  let java = Aliases.java;
  let stage = java.stageHeathrowLhr;
  OCR.Recognize(stage.homescene).BlockByText("Application").Click();
  let baseScene = stage.basescene;
  OCR.Recognize(baseScene.tabbuttonAirportConfiguration).BlockByText("Options").Click();
//  OCR.Recognize(java.contextmenu3.scene21).BlockByText("Colour").Click();
  baseScene.menubar.JavaFXMenu.Click("Options|Colour Schemes|Light Theme");
  OCR.Recognize(baseScene.tabbuttonAirportConfiguration).BlockByText("Options").Click();
  stage.Close();
}




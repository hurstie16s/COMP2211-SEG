package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Settings;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.FileHandler;
import comp2211.seg.ProcessDataModel.Obstacle;
import comp2211.seg.ProcessDataModel.Runway;
import comp2211.seg.UiView.Scene.RunwayComponents.Sub;
import comp2211.seg.UiView.Scene.SceneComponents.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


/**
 * The type Base scene.
 */
public class BaseScene extends SceneAbstract implements GlobalVariables{

    //logger for BaseScene
    private static final Logger logger = LogManager.getLogger(BaseScene.class);
    private static TabLayout tabLayout = null;
    public static ArrayList<TabLayout> tabs = new ArrayList<>();
    private boolean overlay = false;

    public VBox topView;
    public Scene scene;

    private ComboBox airports;
    private ComboBox runways;


    /**
     * Constructor to create a BaseScene object.
     *
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     * @param width     the width
     * @param height    the height
     */
    public BaseScene(Pane root, AppWindow appWindow, SimpleDoubleProperty width, SimpleDoubleProperty height) {
        super(root, appWindow, width, height);
        this.scene = root.getScene();
    }

    @Override
    public void initialise() {

        setOnKeyPressed((keyEvent -> {
            switch (keyEvent.getCode()){
                case H:
                    help.toggleHelp(this.getClass().getCanonicalName());
                    break;
                case ESCAPE:
                    Platform.runLater(appWindow::startHomeScene);
            }
        }));
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (mouseEvent.getPickResult().getIntersectedNode().getParent() instanceof TabButton){
                        TabLayout.oldTabButton = (TabButton) mouseEvent.getPickResult().getIntersectedNode().getParent();
                        overlay = false;
                    } else {
                        TabLayout.oldTabButton = null;
                    }
                } catch (Exception e) {
                    TabLayout.oldTabButton = null;
                }
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (TabLayout.oldTabButton != null && !overlay) {
                    overlay = true;
                    for (TabLayout tab:tabs) {
                        tab.renderOverlay();
                    }
                }
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (TabLayout.oldTabButton != null) {

                    for (TabLayout tab:tabs) {
                        tab.clearOverlay();
                    }
                    try {
                        Node p = mouseEvent.getPickResult().getIntersectedNode();
                        while ((p.getParent() != null) && !((p instanceof TabLayout) || (p instanceof TabButton))) {
                            p = p.getParent();
                            logger.info(p.getClass().getName());
                        }
                        if (p instanceof TabLayout) {
                            Parent gp = p.getParent();
                            while (gp != null) {
                                if (gp.equals(TabLayout.oldTabButton.tab.getValue())) {
                                    throw new RuntimeException("Attempted place tab inside itself");
                                }
                                gp = gp.getParent();
                            }
                            if (TabLayout.oldTabButton != null) {
                                if ((mouseEvent.getSceneX() - ((TabLayout) p).contents.localToScene(((TabLayout) p).contents.getLayoutBounds()).getMinX()) / ((TabLayout) p).contents.getBoundsInLocal().getWidth() < (2/3.0) &&
                                        (mouseEvent.getSceneY() - ((TabLayout) p).contents.localToScene(((TabLayout) p).contents.getLayoutBounds()).getMinY()) / ((TabLayout) p).contents.getBoundsInLocal().getHeight() < (2/3.0)) {
                                    ((TabLayout) p).makeTab(TabLayout.oldTabButton.tab);
                                } else if ((mouseEvent.getSceneX() - ((TabLayout) p).contents.localToScene(((TabLayout) p).contents.getLayoutBounds()).getMinX()) / ((TabLayout) p).contents.getBoundsInLocal().getWidth() <
                                        (mouseEvent.getSceneY() - ((TabLayout) p).contents.localToScene(((TabLayout) p).contents.getLayoutBounds()).getMinY()) / ((TabLayout) p).contents.getBoundsInLocal().getHeight()){
                                    // go Down
                                    Pane pp = ((Pane) p.getParent());
                                    int index = pp.getChildren().indexOf(p);
                                    TabsPaneVertical tabsPane = new TabsPaneVertical((TabLayout) p,TabLayout.oldTabButton);
                                    if (pp instanceof TabsPaneVertical){
                                        ((TabsPaneVertical) pp).replace(index, tabsPane);
                                    } else if (pp instanceof TabsPaneHorizontal) {
                                        ((TabsPaneHorizontal) pp).replace(index, tabsPane);
                                    }else {
                                        pp.getChildren().add(tabsPane);

                                    }


                                } else {
                                    // go Right
                                    Pane pp = ((Pane) p.getParent());
                                    int index = pp.getChildren().indexOf(p);
                                    TabsPaneHorizontal tabsPane = new TabsPaneHorizontal((TabLayout) p,TabLayout.oldTabButton);
                                    if (pp instanceof TabsPaneVertical){
                                        ((TabsPaneVertical) pp).replace(index, tabsPane);
                                    } else if (pp instanceof TabsPaneHorizontal) {
                                        ((TabsPaneHorizontal) pp).replace(index, tabsPane);
                                    }else {
                                        pp.getChildren().add(tabsPane);
                                    }
                                }


                                TabLayout.oldTabButton.tabLayout.removeTab(TabLayout.oldTabButton);
                            }
                        } else if (p instanceof TabButton) {
                            if (TabLayout.oldTabButton.equals(p)) {
                                ((TabButton) p).run();
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
                TabLayout.oldTabButton = null;
            }
        });
    }

    public void build()  {
        super.build();
        logger.info("building");
        //root.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG,null,null)));
        root.getStyleClass().add("unfocusedBG");
        mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        if (tabLayout == null || AppWindow.currentScene instanceof HomeScene) {
            ArrayList<Pair<String, Pane>> tabs = new ArrayList<>();
            tabs.add(new Pair<>("Airport Configuration", makeAirportConfig()));

            tabs.add(new Pair<>("Obstacle Configuration", makeObstacleConfig()));
            //tabLayout = new TabLayout(tabs,Theme.unfocusedBG,Theme.focusedBG);
            tabLayout = new TabLayout(tabs, "unfocusedBG", "focusedBG");


            //appWindow.startBaseScene();
            //tabLayout.tabButtons.get(0).run();
            //((TabLayout) ((TabsPaneVertical) ((TabsPaneHorizontal) tabLayout.contents.getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).tabButtons.get(1).run();
        }
        for (TabLayout tab: tabs) {
            tab.clearOverlay();
        }

        mainPane.maxHeightProperty().bind(root.heightProperty().subtract(topMenu.heightProperty()));
        mainPane.minHeightProperty().bind(root.heightProperty().subtract(topMenu.heightProperty()));
        mainPane.maxWidthProperty().bind(root.widthProperty());
        mainPane.minWidthProperty().bind(root.widthProperty());
        TabsPaneVertical tabsPaneVertical = new TabsPaneVertical();
        tabsPaneVertical.add(tabLayout);

        tabsPaneVertical.maxHeightProperty().bind(mainPane.heightProperty());
        tabsPaneVertical.minHeightProperty().bind(mainPane.heightProperty());
        tabsPaneVertical.maxWidthProperty().bind(mainPane.widthProperty());
        tabsPaneVertical.minWidthProperty().bind(mainPane.widthProperty());
        mainPane.getChildren().add(tabsPaneVertical);
        reset();
        refresh();

    }


    private Scene getScene() {
        return this;
    }

    /**
     * Make airport config pane.
     *
     * @return the pane
     */
    public Pane makeAirportConfig() {
        //Aleks do stuff here
        logger.info("makes airport config");
        //left menu
        //top
        airports = new ComboBox(FXCollections.observableArrayList(appWindow.getAirports()));
        airports.getStyleClass().add("veryfocusedBG");
        airports.getStyleClass().add("font");
// Add a listener to update the runways ComboBox when a new airport is selected
        airports.valueProperty().addListener((observableValue, oldAirport, newAirport) -> {
            appWindow.setAirport((Airport) newAirport);

            // Update the options of the runways ComboBox with the runways for the selected airport
            runways.setItems(FXCollections.observableArrayList(((Airport)newAirport).getRunways()));
            // Select the first runway in the new list, if it exists
            if (!((Airport)newAirport).getRunways().isEmpty()) {
                runways.setValue(((Airport)newAirport).getRunways().get(0));
            }
        });
        // Create the runways ComboBox
        runways = new ComboBox(FXCollections.observableArrayList(appWindow.airport.getRunways()));
        runways.getStyleClass().add("veryfocusedBG");
        runways.getStyleClass().add("font");
// Add a listener to update the selected runway in the appWindow when a new runway is selected
        runways.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                logger.info("changed runway: "+ t1);
                appWindow.setRunway((Runway) t1);
                if (! (o == null)) {
                    if (!o.equals(t1)) {
                        tabLayout = null;
                        appWindow.startBaseScene();
                    }
                }
            }
        });
        // Set the initial value of the airports ComboBox
        //airports.setValue(appWindow.getAirports().get(0));
        airports.setValue(appWindow.airport);
// Set the initial value of the runways ComboBox
        runways.setValue(appWindow.runway);

        //main vBox
        VBox vBoxAirportLayout = new VBox();
        //table
        VBox vBoxTable = new VBox();
        //VBox.setMargin(vBoxTable,new Insets(150,20,100,20));//Top/Right/Bottom/Left
        vBoxTable.setAlignment(Pos.CENTER);
        //vBoxTable.getChildren().add(makeRunwayGridTable());
        vBoxTable.getChildren().add(buildTableView());

        Label airportsLabel = makeLabel("Airport");
        airportsLabel.getStyleClass().add("font");
        airportsLabel.setPrefWidth(80);
        airportsLabel.setAlignment(Pos.CENTER_RIGHT);
        HBox hBoxAirports = new HBox();
        hBoxAirports.setAlignment(Pos.CENTER_LEFT);
        hBoxAirports.getChildren().addAll(airportsLabel,airports);
        HBox.setMargin(airports,new Insets(0,0,0,10));

        Label runwaysLabel = makeLabel("Runway");
        runwaysLabel.getStyleClass().add("font");
        runwaysLabel.setPrefWidth(80);
        runwaysLabel.setAlignment(Pos.CENTER_RIGHT);
        HBox hBoxRunways = new HBox();
        hBoxRunways.setAlignment(Pos.CENTER_LEFT);
        hBoxRunways.getChildren().addAll(runwaysLabel,runways);
        HBox.setMargin(runways,new Insets(0,0,0,10));
        //left menu children
        VBox leftMenu = new VBox(hBoxAirports,hBoxRunways);
        VBox.setMargin(hBoxAirports,new Insets(30,20,20,20));
        VBox.setMargin(hBoxRunways,new Insets(10,20,20,20));

        //right menu
        // Create image views for the icons
        ImageView exportIcon1 = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/exportT.png")).toExternalForm()));
        ImageView importIcon1 = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/importT.png")).toExternalForm()));
        ImageView exportIcon2 = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/exportT.png")).toExternalForm()));
        ImageView importIcon2 = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/importT.png")).toExternalForm()));

        // Create the buttons and set their graphics
        Button exportAirObsButton = new Button("Export Airport & Obstacle", exportIcon1);
        Button importAirObsButton = new Button("Import Airport & Obstacle", importIcon1);
        Button exportObstacle = new Button("Export Obstacle", exportIcon2);
        Button importObstacle = new Button("Import Obstacle", importIcon2);


        //Button events
        exportAirObsButton.setOnAction(e -> exportAirportButtonEvent());

        exportObstacle.setOnAction(e -> exportObstacleButtonEvent());

        importAirObsButton.setOnAction(e -> importAirportWithObstacleButtonEvent());

        importObstacle.setOnAction(e -> importObstacleButtonEvent());



        // Set the size of the icon
        exportIcon1.setFitHeight(16);
        exportIcon1.setFitWidth(16);
        importIcon1.setFitHeight(16);
        importIcon1.setFitWidth(16);
        exportIcon2.setFitHeight(16);
        exportIcon2.setFitWidth(16);
        importIcon2.setFitHeight(16);
        importIcon2.setFitWidth(16);


        VBox rightMenuTop = new VBox(exportObstacle, importObstacle);
        VBox.setMargin(exportObstacle,new Insets(30,20,20,20));
        VBox.setMargin(importObstacle,new Insets(10,20,20,20));
      VBox rightMenuBottom = new VBox(exportAirObsButton, importAirObsButton);
      VBox.setMargin(exportAirObsButton,new Insets(30,20,20,20));
      VBox.setMargin(importAirObsButton,new Insets(10,20,20,20));
      HBox rightMenu = new HBox(rightMenuTop,rightMenuBottom);
        //Dark buttons style from css, can be done globally
        List<Button> darkButtons = new ArrayList<>();
        darkButtons.add(exportObstacle);
        darkButtons.add(importObstacle);
      darkButtons.add(exportAirObsButton);
      darkButtons.add(importAirObsButton);


        for (Button button : darkButtons) {
            //button.setBackground(new Background(new BackgroundFill(Theme.focusedBG,null,null)));
            button.getStyleClass().add("focusedBG");
            button.getStyleClass().add("fg");
            button.setCursor(Cursor.HAND);
        }

        HBox hBoxMenuButtons = new HBox();
        Region region = new Region();
        HBox.setHgrow(region,Priority.ALWAYS);
//        hBoxMenuButtons.getChildren().addAll(leftMenu,region,rightMenu);
        hBoxMenuButtons.getChildren().addAll(leftMenu);

        vBoxAirportLayout.getChildren().addAll(hBoxMenuButtons,vBoxTable);
        //return vBox
        return vBoxAirportLayout;
    }

    //exporting the Airport with runways and no objects
    public void exportAirportNoObsButtonEvent() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to export");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML format(*.xml)","*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Airport");
        File file = fileChooser.showSaveDialog(new Stage());

        try {
            if (file == null) {
                return;
            }

            if (!file.getName().contains(".xml")) {
                logger.info("reached");
                file = new File(file.getAbsolutePath() + ".xml");
                logger.info(file.getAbsolutePath());
            }

            if (FileHandler.exportAirport(file, appWindow.airport)) {
                FileHandler.exportAirport(file, appWindow.airport);
                logger.info("Exporting Successful");
            } else {
                logger.info("Exporting Airport failed");
            }
        } catch (NullPointerException nullPointerException) {
            logger.info("No airport initiated, hence: " +
                "Exception in thread \"JavaFX Application Thread\" java.lang.NullPointerException: " +
                "Cannot invoke \"comp2211.seg.ProcessDataModel.Airport.toString()\" because \"airport\" is null");
        }
    }

    public void exportAirportButtonEvent() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to export");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML format(*.xml)","*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("AirportWithObstacle");
        File file = fileChooser.showSaveDialog(new Stage());

        try {
            if (file == null) {
                return;
            }

            if (!file.getName().contains(".xml")) {
                logger.info("reached");
                file = new File(file.getAbsolutePath() + ".xml");
                logger.info(file.getAbsolutePath());
            }

            if (FileHandler.exportAirportAndOb(file, appWindow.airport, appWindow.runway.runwayObstacle)) {
                FileHandler.exportAirportAndOb(file, appWindow.airport,appWindow.runway.runwayObstacle);
                logger.info("Exporting Successful");
            } else {
                logger.info("Exporting Airport failed");
            }
        } catch (NullPointerException nullPointerException) {
            logger.info("No airport initiated, hence: " +
                    "Exception in thread \"JavaFX Application Thread\" java.lang.NullPointerException: " +
                    "Cannot invoke \"comp2211.seg.ProcessDataModel.Airport.toString()\" because \"airport\" is null");
        }
    }

    public void exportObstacleButtonEvent() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to export");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML format(*.xml)","*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Obstacle");
        File file = fileChooser.showSaveDialog(new Stage());

        try {
            if (file == null) {
                return;
            }

            if (!file.getName().contains(".xml")) {
                logger.info("reached");
                file = new File(file.getAbsolutePath() + ".xml");
                logger.info(file.getAbsolutePath());
            }

            if (FileHandler.exportObstacle(file, appWindow.runway.runwayObstacle)) {
                FileHandler.exportObstacle(file, appWindow.runway.runwayObstacle);
                logger.info("Exporting Successful");
            } else {
                logger.info("Exporting Obstacle failed");
            }
        } catch (NullPointerException nullPointerException) {
            logger.info("No Obstacle initiated, hence: " +
                    "Exception in thread \"JavaFX Application Thread\" java.lang.NullPointerException: " +
                    "Cannot invoke \"comp2211.seg.ProcessDataModel.Airport.toString()\" because \"airport\" is null");
        }
    }


    /**
     * Build table view grid pane.
     *
     * @return the grid pane
     */
    public GridPane buildTableView() {

        GridPane airportData = new GridPane();

        for (int i = 0; i < 10; i++) {
            ColumnConstraints ccx = new ColumnConstraints();
            ccx.setPercentWidth(10);
            airportData.getColumnConstraints().add(ccx);
        }

        String[] titles = new String[] {"Runway (RWY)","Stopway (SWY)","Clearway (CWY)","RESA","Threshold\nDisplacement","Strip End","Blast\nProtection"};
        for (int i = 0; i < 7; i++) {

            Label data = makeLabel(titles[i]);
            data.getStyleClass().add("tableH1");
            data.setAlignment(Pos.CENTER);
            data.setTextAlignment(TextAlignment.CENTER);
            data.setPadding(new Insets(10, 0, 10, 0));
            if (i==0){
                GridPane.setColumnSpan(data,2);
                airportData.add(data,1,0);
            }else if (i==3){
                GridPane.setColumnSpan(data,2);
                airportData.add(data,5,0);
            }else if (i<3&&i>0){
                //data.setFont(new Font("Calibri",17));
                airportData.add(data,i+2,0);
            }else{
                GridPane.setRowSpan(data,2);
                //data.setFont(new Font("Calibri",17));
                airportData.add(data,i+3,0);
            }

        }
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            if (i==0 || i==3){
                var lengthLabel = makeLabel("Length");
                var widthLabel = makeLabel("Width");

                //lengthLabel.setFont(Theme.fontsmall);
                lengthLabel.getStyleClass().add("tableH2");
                lengthLabel.setPadding(new Insets(8, 0, 8 ,0));
                //widthLabel.setFont(Theme.fontsmall);
                widthLabel.getStyleClass().add("tableH2");
                widthLabel.setPadding(new Insets(8, 0, 8 ,0));

                counter += 1;
                airportData.add(lengthLabel,counter,1);
                counter += 1;
                airportData.add(widthLabel,counter,1);
            }else{
                var lengthLabel = makeLabel("Length");

                //lengthLabel.setFont(Theme.fontsmall);
                lengthLabel.getStyleClass().add("tableH2");
                lengthLabel.setPadding(new Insets(8, 0, 8 ,0));

                counter += 1;
                airportData.add(lengthLabel,counter,1);
            }

        }
        Label desl = makeLabel(appWindow.runway.getRunwayDesignatorLeft());
        desl.setAlignment(Pos.CENTER);
        airportData.add(desl,0,2);
        airportData.add(makeTableCell(appWindow.runway.runwayLengthProperty()),1,2);
        airportData.add(makeTableCell(appWindow.runway.runwayWidthProperty()),2,2);
        airportData.add(makeTableCell(appWindow.runway.stopwayRightProperty()),3,2);
        airportData.add(makeTableCell(appWindow.runway.clearwayRightProperty()),4,2);
        airportData.add(makeTableCell(appWindow.runway.RESAWidthProperty()),5,2);
        airportData.add(makeTableCell(appWindow.runway.RESAHeightProperty()),6,2);
        airportData.add(makeTableCell(appWindow.runway.dispThresholdLeftProperty()),7,2);
        airportData.add(makeTableCell(appWindow.runway.stripEndProperty()),8,2);
        airportData.add(makeTableCell(new SimpleDoubleProperty(500)),9,2);

        Label desr = makeLabel(appWindow.runway.getRunwayDesignatorRight());
        desr.setAlignment(Pos.CENTER);
        airportData.add(desr,0,3);
        airportData.add(makeTableCell(appWindow.runway.runwayLengthProperty()),1,3);
        airportData.add(makeTableCell(appWindow.runway.runwayWidthProperty()),2,3);
        airportData.add(makeTableCell(appWindow.runway.stopwayLeftProperty()),3,3);
        airportData.add(makeTableCell(appWindow.runway.clearwayLeftProperty()),4,3);
        airportData.add(makeTableCell(appWindow.runway.RESAWidthProperty()),5,3);
        airportData.add(makeTableCell(appWindow.runway.RESAHeightProperty()),6,3);
        airportData.add(makeTableCell(appWindow.runway.dispThresholdRightProperty()),7,3);
        airportData.add(makeTableCell(appWindow.runway.stripEndProperty()),8,3);
        airportData.add(makeTableCell(new SimpleDoubleProperty(500)),9,3);
        for (Node node:airportData.getChildren()) {
            if (node instanceof Control control) {
                control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                //control.setBorder(new Border(new BorderStroke(Theme.fg,BorderStrokeStyle.SOLID,null,new BorderWidths(1))));
                control.getStyleClass().add("fgBorder");
            }
            if (node instanceof Pane pane) {
                pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                //pane.setBorder(new Border(new BorderStroke(Theme.fg,BorderStrokeStyle.SOLID,null,new BorderWidths(1))));
                pane.getStyleClass().add("fgBorder");
            }
            if (node instanceof TextField){
                ((TextField) node).setAlignment(Pos.CENTER);
            }
            if (node instanceof Label){
                ((Label) node).setAlignment(Pos.CENTER);
            }
        }

        airportData.setAlignment(Pos.CENTER);
        airportData.setSnapToPixel(false);



        return airportData;
    }

    /**
     * Build table view 2 table view.
     *
     * @param container the container
     * @return the table view
     */
    public TableView<RunwayData> buildTableView2(VBox container) {
        TableView<RunwayData> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //table.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG,null,null)));
        //final Label label = new Label("Runway Data");
        //label.setFont(Theme.font);

        //columns
        TableColumn<RunwayData, String> col1 = createColumn("Runway\nDesignators");
        col1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        TableColumn<RunwayData, String> col2 = createColumn("Runway(RWY)");
        TableColumn<RunwayData, String> col21 = createColumn("Length");
        col21.setCellValueFactory(new PropertyValueFactory<>("column21"));
        TableColumn<RunwayData, String> col22 = createColumn("Width");
        col22.setCellValueFactory(new PropertyValueFactory<>("column22"));
        TableColumn<RunwayData, String> col3 = createColumn("Runway Strip");
        TableColumn<RunwayData, String> col31 = createColumn("Length");
        col31.setCellValueFactory(new PropertyValueFactory<>("column31"));
        TableColumn<RunwayData, String> col32 = createColumn("Width");
        col32.setCellValueFactory(new PropertyValueFactory<>("column32"));
        TableColumn<RunwayData, String> col4 = createColumn("Stopway(SWY)");
        TableColumn<RunwayData, String> col41 = createColumn("Length");
        col41.setCellValueFactory(new PropertyValueFactory<>("column41"));
        TableColumn<RunwayData, String> col42 = createColumn("Width");
        col42.setCellValueFactory(new PropertyValueFactory<>("column42"));
        TableColumn<RunwayData, String> col5 = createColumn("Clearway(CWY)");
        TableColumn<RunwayData, String> col51 = createColumn("Length");
        col51.setCellValueFactory(new PropertyValueFactory<>("column51"));
        TableColumn<RunwayData, String> col52 = createColumn("Width");
        col52.setCellValueFactory(new PropertyValueFactory<>("column52"));
        TableColumn<RunwayData, String> col6 = createColumn("RESA");
        TableColumn<RunwayData, String> col61 = createColumn("Length");
        col61.setCellValueFactory(new PropertyValueFactory<>("column61"));
        TableColumn<RunwayData, String> col62 = createColumn("Width");
        col62.setCellValueFactory(new PropertyValueFactory<>("column62"));
        TableColumn<RunwayData, String> col7 = createColumn("Threshold\nDisplacement");
        col7.setCellValueFactory(new PropertyValueFactory<>("column7"));
        TableColumn<RunwayData, String> col8 = createColumn("Strip End");
        col8.setCellValueFactory(new PropertyValueFactory<>("column8"));
        TableColumn<RunwayData, String> col9 = createColumn("Blast\nProtection");
        col9.setCellValueFactory(new PropertyValueFactory<>("column9"));

        List<TableColumn<RunwayData, ?>> col2Columns = new ArrayList<>();
        col2Columns.add(col21);
        col2Columns.add(col22);
        col2.getColumns().addAll(col2Columns);
        List<TableColumn<RunwayData, ?>> col3Columns = new ArrayList<>();
        col3Columns.add(col31);
        col3Columns.add(col32);
        col3.getColumns().addAll(col3Columns);
        List<TableColumn<RunwayData, ?>> col4Columns = new ArrayList<>();
        col4Columns.add(col41);
        col4Columns.add(col42);
        col4.getColumns().addAll(col4Columns);
        List<TableColumn<RunwayData, ?>> col5Columns = new ArrayList<>();
        col5Columns.add(col51);
        col5Columns.add(col52);
        col5.getColumns().addAll(col5Columns);
        List<TableColumn<RunwayData, ?>> col6Columns = new ArrayList<>();
        col6Columns.add(col61);
        col6Columns.add(col62);
        col6.getColumns().addAll(col6Columns);
        List<TableColumn<RunwayData, ?>> columns = new ArrayList<>();
        columns.add(col1);
        columns.add(col2);
        columns.add(col3);
        columns.add(col4);
        columns.add(col5);
        columns.add(col6);
        columns.add(col7);
        columns.add(col8);
        columns.add(col9);
        // create an ObservableList of RunwayData objects
        ObservableList<RunwayData> runway1Data = FXCollections.observableArrayList();
        RunwayData leftData = new RunwayData(
            makeTableCell(appWindow.runway.runwayDesignatorLeftProperty()),
            makeTableCell(appWindow.runway.runwayLengthProperty()),
            makeTableCell(appWindow.runway.runwayWidthProperty()),
            new TextField(""),
            new TextField(""),
            makeTableCell(appWindow.runway.stopwayLeftProperty()),
            new TextField(""),
            makeTableCell(appWindow.runway.clearwayLeftProperty()),
            new TextField(""),
            makeTableCell(appWindow.runway.RESAWidthProperty()),
            makeTableCell(appWindow.runway.RESAHeightProperty()),
            makeTableCell(appWindow.runway.dispThresholdLeftProperty()),
            makeTableCell(appWindow.runway.stripEndProperty()),
            new TextField("500")
        );

        RunwayData rightData = new RunwayData(
            makeTableCell(appWindow.runway.runwayDesignatorRightProperty()),
            makeTableCell(appWindow.runway.runwayLengthProperty()),
            makeTableCell(appWindow.runway.runwayWidthProperty()),
            new TextField(""),
            new TextField(""),
            makeTableCell(appWindow.runway.stopwayRightProperty()),
            new TextField(""),
            makeTableCell(appWindow.runway.clearwayRightProperty()),
            new TextField(""),
            makeTableCell(appWindow.runway.RESAWidthProperty()),
            makeTableCell(appWindow.runway.RESAHeightProperty()),
            makeTableCell(appWindow.runway.dispThresholdRightProperty()),
            makeTableCell(appWindow.runway.stripEndProperty()),
            new TextField("500")
        );
        /*RunwayData data1 = new RunwayData(
            "09L",
            "xxxxm", "xxxxm",
            "xxxxm", "xxxxm",
            "xxxxm", "xxxxm",
            "xxxxm", "xxxxm",
            "xxxxm", "xxxxm",
            "xxxxm",
            "60m",
            "300m");
        RunwayData data2 = new RunwayData(
            "27L",
            "xxxxm", "xxxxm",
            "xxxxm", "xxxxm",
            "xxxxm", "xxxxm",
            "xxxxm", "xxxxm",
            "xxxxm", "xxxxm",
            "xxxxm",
            "60m",
            "300m");*/
        runway1Data.add(leftData);
        runway1Data.add(rightData);


        table.setItems(runway1Data);

        table.fixedCellSizeProperty().bind(container.heightProperty()
            .subtract(55).divide(2));//table.getItems().size()));
        table.maxWidthProperty().bind(container.widthProperty());
        table.minWidthProperty().bind(container.widthProperty());
        table.getColumns().addAll(columns);
        for (TableColumn<RunwayData, ?> tableColumn : table.getColumns()) {
            //logger.info("for");
            if (tableColumn.getText().matches(".*\\d{2}.*")) {
                //logger.info("if");
                tableColumn.maxWidthProperty().bind(container.widthProperty().divide(18));
                tableColumn.minWidthProperty().bind(container.widthProperty().divide(18));
            } else {
                //logger.info("else");
                tableColumn.maxWidthProperty().bind(container.widthProperty().divide(9));
                tableColumn.minWidthProperty().bind(container.widthProperty().divide(9));
            }
        }
        table.setPlaceholder(null);
        return table;
    }
    private <T> TableColumn<RunwayData, T> createColumn(String columnName) {
        TableColumn<RunwayData, T> tableColumn = new TableColumn<>(columnName);
        //tableColumn.setCellFactory(column -> {
        //    TableCell<RunwayData, T> cell = new TableCell<RunwayData, T>() {
        //        @Override
        //        protected void updateItem(T item, boolean empty) {
        //            super.updateItem(item, empty);
        //            if (empty || item == null) {
        //                setText(null);
        //            } else {
        //                setText(item.toString());
        //            }
        //        }
        //    };
        //    return cell;
        //});
        return tableColumn;
    }

    /**
     * Make obstacle config pane.
     *
     * @return the pane
     */
    public Pane makeObstacleConfig(){
        TabsPaneHorizontal obstacleLayout = new TabsPaneHorizontal();
        TabsPaneVertical leftPane = new TabsPaneVertical();
        makeObstaclePane(leftPane);
        TabsPaneVertical rightPane = new TabsPaneVertical();
        rightPane.getChildren().add(makeRunwayTabs());


        obstacleLayout.getChildren().add(leftPane);
        new Divider(obstacleLayout);
        obstacleLayout.getChildren().add(rightPane);
        return obstacleLayout;
    }

    /**
     * Make obstacle pane.
     *
     * @param obstaclePane the obstacle pane
     */
    public void makeObstaclePane(TabsPaneVertical obstaclePane){

        TabLayout obstacleOptionsPane = makeObstacleOptionsPane();
        TabLayout declaredDistancesPane = makeDistancesPane();
        TabLayout breakDownPane = makeBreakDownPane();


        obstaclePane.getChildren().add(obstacleOptionsPane);
        new Divider(obstaclePane);
        obstaclePane.getChildren().add(declaredDistancesPane);
        new Divider(obstaclePane);
        obstaclePane.getChildren().add(breakDownPane);
        obstaclePane.rebalance();
    }
    private TabLayout makeObstacleOptionsPane() {
        // Obstacle preset ComboBox
        ComboBox obstacleComboBox = new ComboBox(FXCollections.observableArrayList(appWindow.obstaclePresets));
        //obstacleComboBox.setBackground(new Background(new BackgroundFill(Theme.veryfocusedBG,null,null)));
        obstacleComboBox.getStyleClass().addAll("veryfocusedBG", "font");
        obstacleComboBox.setOnAction(event -> {
            Object selectedObstacle = obstacleComboBox.getSelectionModel().getSelectedItem();
            appWindow.runway.addObstacle((Obstacle) selectedObstacle);
        });

        obstacleComboBox.valueProperty().set("Default");

        ArrayList<Pair<String, Pane>> obstacleOptions = new ArrayList<>();
        GridPane obstacleData = new GridPane();

        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(17);

        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(20);
        ColumnConstraints cc3 = new ColumnConstraints();
        cc3.setPercentWidth(33);
        ColumnConstraints cc4 = new ColumnConstraints();
        cc4.setPercentWidth(30);
        obstacleData.getColumnConstraints().addAll(cc1,cc2,cc3,cc4);
        obstacleData.setAlignment(Pos.CENTER_LEFT);
        obstacleData.setHgap(10);

        ArrayList<RowConstraints> rc = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            RowConstraints rcx = new RowConstraints();
            rcx.setPercentHeight(20);
            rc.add(rcx);
        }
        obstacleData.getRowConstraints().addAll(rc);

        Label pos = makeLabel("Position");
//        GridPane.setRowSpan(pos,2);
//        pos.setAlignment(Pos.TOP_CENTER);

        obstacleData.addColumn(0,makeLabel("Preset"),makeLabel("Height (m)"),makeLabel("Length (m)"),makeLabel("Width (m)"),makeLabel("Active?"));

        obstacleData.add(pos, 2,0);
        obstacleData.add(makeLabel("Top Landing/Take off"), 2,2);
        Label bottom = makeLabel("Bottom Landing/Take off");
        bottom.visibleProperty().bind(appWindow.runway.dualDirectionRunway);
        obstacleData.add(bottom, 2,3);
        obstacleData.add(makeButton(appWindow.runway.directionLeftProperty(),"Towards","Away"),3,2);
        Node rightDirection = makeButton(appWindow.runway.directionRightProperty(),"Away","Towards");
        rightDirection.visibleProperty().bind(appWindow.runway.dualDirectionRunway);
        obstacleData.add(rightDirection,3,3);

        // Obstacle preset dropdown selector
        obstacleData.add(obstacleComboBox,1,0);

        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().heightProperty()),1,1);
        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().lengthProperty()),1,2);
        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().widthProperty()),1,3);
        obstacleData.add(makeButton(appWindow.runway.hasRunwayObstacleProperty(),"Yes","No"),1,4);
        Slider slider = new Slider();
        slider.minProperty().bind(appWindow.runway.runwayObstacle.lengthProperty().divide(-2));
        slider.maxProperty().bind(appWindow.runway.runwayLengthProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)));
        slider.valueProperty().bindBidirectional(appWindow.runway.runwayObstacle.distFromThresholdProperty());
        Button leftButton = new Button("<");
        leftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {

                    slider.valueProperty().set(slider.valueProperty().get()-1);
                } catch (Exception e){}
            }
        });
        Button rightButton = new Button(">");
        rightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {

                    slider.valueProperty().set(slider.valueProperty().get()+1);
                } catch (Exception e){}
            }
        });
        leftButton.setPadding(new Insets(3));
        rightButton.setPadding(new Insets(3));


        HBox posSlider = new HBox(leftButton,slider,rightButton);
        posSlider.setAlignment(Pos.CENTER);

        Node l = makeOutputLabel(appWindow.runway.runwayObstacle.distFromThresholdProperty(),appWindow.runway.dispThresholdLeftProperty().multiply(-1),new SimpleBooleanProperty(true),5);
        Node r = makeOutputLabel(appWindow.runway.runwayObstacle.distFromOtherThresholdProperty(),new SimpleBooleanProperty(true),5);

        VBox posLeft = new VBox(makeOutputLabel(new SimpleStringProperty("Left"),new SimpleBooleanProperty(true),18),l);
        VBox posRight = new VBox(makeOutputLabel(new SimpleStringProperty("Right"),new SimpleBooleanProperty(true),18),r);
        posLeft.setAlignment(Pos.CENTER);
        posLeft.setMinWidth(50);
        posRight.setAlignment(Pos.CENTER);
        posRight.setMinWidth(50);

        BorderPane posvals = new BorderPane();
        posvals.setLeft(posLeft);
        posvals.setRight(posRight);
        posvals.setPadding(new Insets(5));
        obstacleData.add(posSlider,3,0);
        obstacleData.add(posvals,3,1);
        obstacleData.getChildren().forEach(new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                GridPane.setHalignment(node, HPos.RIGHT);
            }
        });


        ListView<String> historyListView = new ListView<>();
        historyListView.getStyleClass().add("veryfocusedBG");
        historyListView.getStyleClass().add("fg");
        historyListView.setPadding(new Insets(10));
        historyListView.setFocusTraversable(false);

        historyListView.itemsProperty().bind(appWindow.runway.getChangeHistoryProperty());

        ScrollPane history = new ScrollPane(historyListView);
        history.setFitToWidth(true);
        history.setPadding(new Insets(16));
        history.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        history.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        obstacleOptions.add(new Pair<>("Obstacle", obstacleData));
        obstacleOptions.add(new Pair<>("Change History", new BorderPane(history)));
        TabLayout obstacleOptionsPane = new TabLayout(obstacleOptions,"focusedBG","veryfocusedBG");
        return obstacleOptionsPane;
    }



    private Node makeOutputLabel(SimpleStringProperty string, SimpleBooleanProperty visibility, int i) {
        Label data = new Label();

        data.getStyleClass().addAll("fontsmall", "bold"); //Hardcoding this annoying use was easier lol
        //data.setTextFill(Theme.fg);
        data.getStyleClass().add("fg");
        data.setText(String.valueOf(string.getValue()));
        data.textProperty().bind(Bindings.when(visibility).then(string).otherwise(new
                SimpleStringProperty("Error")));
        return data;
    }

    private Node makeOutputLabel(SimpleDoubleProperty property, SimpleBooleanProperty visibility, int i) {
        Label data = new Label();
        //data.setFont(Theme.fontsmall);
        data.getStyleClass().add("fontsmall");
        //data.setTextFill(Theme.fg);
        data.getStyleClass().add("fg");
        data.setText(String.valueOf(property.getValue()));
        property.addListener((observableValue, number, t1) -> {
            if (!t1.equals(number)){
                if (visibility.get()){
                    data.textProperty().set(Math.round(property.get())+appWindow.runway.unitsProperty().get());
                }else {
                    data.textProperty().set("Error");
                }
                logger.info(data.textProperty().get());
            }
        });
        data.textProperty().set(Bindings.when(visibility).then(new SimpleStringProperty(Long.toString(Math.round(property.get()))).concat(appWindow.runway.unitsProperty())).otherwise(new
                SimpleStringProperty("Error")).get());
        data.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logger.info("clicked " + data.textProperty().get());
            }
        });
        return data;

    }
    private Node makeOutputLabel(SimpleDoubleProperty property,DoubleBinding propertyoffset, SimpleBooleanProperty visibility, int i) {
        Label data = new Label();
        //data.setFont(Theme.fontsmall);
        data.getStyleClass().add("fontsmall");
        //data.setTextFill(Theme.fg);
        data.getStyleClass().add("fg");
        data.setText(String.valueOf(property.getValue()));
        property.addListener((observableValue, number, t1) -> {
            if (!t1.equals(number)){
                if (visibility.get()){
                    data.textProperty().set(Math.round(property.get()+propertyoffset.get())+appWindow.runway.unitsProperty().get());
                }else {
                    data.textProperty().set("Error");
                }
                logger.info(data.textProperty().get());
            }
        });
        propertyoffset.addListener((observableValue, number, t1) -> {
            if (!t1.equals(number)){
                if (visibility.get()){
                    data.textProperty().set(Math.round(property.get()+propertyoffset.get())+appWindow.runway.unitsProperty().get());
                }else {
                    data.textProperty().set("Error");
                }
                logger.info(data.textProperty().get());
            }
        });
        data.textProperty().set(Bindings.when(visibility).then(new SimpleStringProperty(Long.toString(Math.round(property.get()+propertyoffset.get()))).concat(appWindow.runway.unitsProperty())).otherwise(new
                SimpleStringProperty("Error")).get());
        data.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logger.info("clicked " + data.textProperty().get());
            }
        });
        return data;

    }

    private TabLayout makeDistancesPane() {
        GridPane distancesGrid = new GridPane();
        distancesGrid.add(makeLabel("Designator"),1,0);
        distancesGrid.add(makeLabel("TORA"),2,0);
        distancesGrid.add(makeLabel("TODA"),3,0);
        distancesGrid.add(makeLabel("ASDA"),4,0);
        distancesGrid.add(makeLabel("LDA"),5,0);
        Label label1 = makeLabel("Original");
        Label label2 = makeLabel("Recalculated");
        GridPane.setRowSpan(label1,2);
        GridPane.setRowSpan(label2,2);
        distancesGrid.add(label1,0,1);
        distancesGrid.add(label2,0,3);
        ArrayList<ColumnConstraints> cc = new ArrayList<>();
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(120/6.2);
        cc.add(cc1);
        for (int i = 0; i < 5; i++) {

            ColumnConstraints ccx = new ColumnConstraints();
            ccx.setPercentWidth(100/6.2);
            cc.add(ccx);
        }
        distancesGrid.getColumnConstraints().addAll(cc);

        ArrayList<RowConstraints> rc = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            RowConstraints rcx = new RowConstraints();
            rcx.setPercentHeight(100/5);
            rc.add(rcx);
        }
        distancesGrid.getRowConstraints().addAll(rc);


        distancesGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorLeftProperty(),new SimpleBooleanProperty(true)),1,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputLeftToraProperty(),new SimpleBooleanProperty(true)),2,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputLeftTodaProperty(),new SimpleBooleanProperty(true)),3,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputLeftAsdaProperty(),new SimpleBooleanProperty(true)),4,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputLeftLdaProperty(),new SimpleBooleanProperty(true)),5,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorRightProperty(),new SimpleBooleanProperty(true)),1,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputRightToraProperty(),new SimpleBooleanProperty(true)),2,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputRightTodaProperty(),new SimpleBooleanProperty(true)),3,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputRightAsdaProperty(),new SimpleBooleanProperty(true)),4,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputRightLdaProperty(),new SimpleBooleanProperty(true)),5,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorLeftProperty(),new SimpleBooleanProperty(true)),1,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.leftToraProperty(),new SimpleBooleanProperty(true)),2,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.leftTodaProperty(),new SimpleBooleanProperty(true)),3,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.leftAsdaProperty(),new SimpleBooleanProperty(true)),4,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.leftLdaProperty(),new SimpleBooleanProperty(true)),5,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorRightProperty(),new SimpleBooleanProperty(true)),1,4);
        distancesGrid.add(makeOutputLabel(appWindow.runway.rightToraProperty(),new SimpleBooleanProperty(true)),2,4);
        distancesGrid.add(makeOutputLabel(appWindow.runway.rightTodaProperty(),new SimpleBooleanProperty(true)),3,4);
        distancesGrid.add(makeOutputLabel(appWindow.runway.rightAsdaProperty(),new SimpleBooleanProperty(true)),4,4);
        distancesGrid.add(makeOutputLabel(appWindow.runway.rightLdaProperty(),new SimpleBooleanProperty(true)),5,4);

        ArrayList<Pair<String, Pane>> declaredDistances = new ArrayList<>();
        declaredDistances.add(new Pair<>("Declared Distances", distancesGrid));
        TabLayout declaredDistancesPane = new TabLayout(declaredDistances,"focusedBG","veryfocusedBG");
        distancesGrid.getChildren().forEach(new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                GridPane.setHalignment(node, HPos.CENTER);
            }
        });
        return declaredDistancesPane;
    }

    private Node makeOutputLabel(SimpleStringProperty runwayDesignatorProperty, SimpleBooleanProperty visibility) {
        Label data = new Label();
        //data.setFont(Theme.font);
        data.getStyleClass().add("font");
        //data.setTextFill(Theme.fg);
        data.getStyleClass().add("fg");
        data.setText(String.valueOf(runwayDesignatorProperty.getValue()));
        data.textProperty().bind(Bindings.when(visibility).then(runwayDesignatorProperty).otherwise(new
                SimpleStringProperty("Error")));
        return data;

    }
    private Pane makeOutputLabel(SimpleStringProperty prop1header,SimpleStringProperty prop1,SimpleStringProperty prop2header,SimpleStringProperty prop2) {

        Label dataheader = new Label();
        //dataheader.setFont(Theme.font);
        dataheader.getStyleClass().add("font");
        //dataheader.setTextFill(Theme.fg);
        dataheader.getStyleClass().add("fg");
        dataheader.textProperty().bind(prop1header);
        dataheader.setAlignment(Pos.CENTER_LEFT);

        Label data = new Label();
        //data.setFont(Theme.font);
        data.getStyleClass().add("font");
        //data.setTextFill(Theme.fg);
        data.getStyleClass().add("fg");
        data.textProperty().bind(prop1);
        data.setAlignment(Pos.CENTER_LEFT);

        Label data2header = new Label();
        //data2header.setFont(Theme.font);
        data2header.getStyleClass().add("font");
        //data2header.setTextFill(Theme.fg);
        data2header.getStyleClass().add("fg");
        data2header.textProperty().bind(prop2header);
        data2header.setAlignment(Pos.CENTER_LEFT);

        Label data2 = new Label();
        //data2.setFont(Theme.font);
        data2.getStyleClass().add("font");
        //data2.setTextFill(Theme.fg);
        data2.getStyleClass().add("fg");
        data2.textProperty().bind(prop2);
        data2.setAlignment(Pos.CENTER_LEFT);

        ScrollPane scrollPane = new ScrollPane();

        VBox box = new VBox(dataheader,data,data2header,data2);
        VBox.setVgrow(data, Priority.ALWAYS);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(0,5,0,5));

        scrollPane.setContent(box); // TODO: Finish
        Pane pane = new Pane(scrollPane);
        scrollPane.maxHeightProperty().bind(pane.heightProperty());
        scrollPane.minHeightProperty().bind(pane.heightProperty());
        scrollPane.maxWidthProperty().bind(pane.widthProperty());
        scrollPane.minWidthProperty().bind(pane.widthProperty());
        return pane;

    }

    /**
     * Make label label.
     *
     * @param string the string
     * @return the label
     */
    public Label makeLabel(String string){
        Label label = new Label(string);
        //label.setFont(Theme.font);
        label.getStyleClass().addAll("font", "bold");
        //label.setTextFill(Theme.fg);
        label.getStyleClass().add("fg");
        return label;
    }

    /**
     * Make output label label.
     *
     * @param property   the property
     * @param visibility the visibility
     * @return the label
     */
    public Label makeOutputLabel(SimpleDoubleProperty property, SimpleBooleanProperty visibility) {
        Label data = new Label();
        //data.setFont(Theme.font);
        data.getStyleClass().add("font");
        //data.setTextFill(Theme.fg);
        data.getStyleClass().add("fg");
        data.setText(String.valueOf(property.getValue()));
        property.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (!t1.equals(number)){
                    data.textProperty().bind(Bindings.when(visibility).then(new SimpleStringProperty(Long.toString(Math.round(property.get()))).concat(appWindow.runway.unitsProperty())).otherwise(new
                            SimpleStringProperty("Error")));
                }
            }
        });
        data.textProperty().bind(Bindings.when(visibility).then(new SimpleStringProperty(Long.toString(Math.round(property.get()))).concat(appWindow.runway.unitsProperty())).otherwise(new
                SimpleStringProperty("Error")));
        return data;
    }

    private TabLayout makeBreakDownPane() {
        ArrayList<Pair<String, Pane>> breakDown = new ArrayList<>();
        breakDown.add(
                new Pair<>(
                        "TORA Maths",
                        makeOutputLabel(
                                appWindow.runway.leftToraBreakdownHeaderProperty(),
                                appWindow.runway.leftToraBreakdownProperty(),
                                appWindow.runway.rightToraBreakdownHeaderProperty(),
                                appWindow.runway.rightToraBreakdownProperty()
                        )
                )
        );
        breakDown.add(
                new Pair<>(
                        "TODA Maths",
                        makeOutputLabel(
                                appWindow.runway.leftTodaBreakdownHeaderProperty(),
                                appWindow.runway.leftTodaBreakdownProperty(),
                                appWindow.runway.rightTodaBreakdownHeaderProperty(),
                                appWindow.runway.rightTodaBreakdownProperty()
                        )
                )
        );
        breakDown.add(
                new Pair<>(
                        "ASDA Maths",
                        makeOutputLabel(
                                appWindow.runway.leftAsdaBreakdownHeaderProperty(),
                                appWindow.runway.leftAsdaBreakdownProperty(),
                                appWindow.runway.rightAsdaBreakdownHeaderProperty(),
                                appWindow.runway.rightAsdaBreakdownProperty()
                        )
                )
        );
        breakDown.add(
                new Pair<>(
                        "LDA Maths",
                        makeOutputLabel(
                                appWindow.runway.leftLdaBreakdownHeaderProperty(),
                                appWindow.runway.leftLdaBreakdownProperty(),
                                appWindow.runway.rightLdaBreakdownHeaderProperty(),
                                appWindow.runway.rightLdaBreakdownProperty()
                        )
                )
        );
        TabLayout breakDownPane = new TabLayout(breakDown,"focusedBG","veryfocusedBG");
        // TODO: scrollable
        return breakDownPane;
    }
    private TextField makeTableCell(SimpleDoubleProperty property){
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        //textField.setBorder(new Border(new BorderStroke(Theme.fg,BorderStrokeStyle.SOLID,null,new BorderWidths(1))));

        textField.getStyleClass().addAll("fgBorder", "font");
        textField.textProperty().bind(property.asString().concat(Runway.units));
        textField.editableProperty().set(false);
        return textField;
    }

    private TextField makeTableCell(SimpleStringProperty property){
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        //textField.setBorder(new Border(new BorderStroke(Theme.fg,BorderStrokeStyle.SOLID,null,new BorderWidths(1))));
        textField.getStyleClass().add("fgBorder");
        textField.textProperty().set(property.get());
        textField.editableProperty().set(false);
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!s.equals(t1)){
                    if (Objects.equals(t1, "")) {
                        property.set("");
                    } else {
                        try {
                            property.set(t1);
                        } catch (Exception e) {
                            displayErrorMessage("Invalid Entry", t1 + " must be a number");
                            textField.setText(s);
                        }
                    }
                }
            }
        });
        return textField;
    }

    /**
     * Make runway tabs pane.
     *
     * @return the pane
     */
    public Pane makeRunwayTabs(){
        ArrayList<Pair<String, Pane>> viewTabs = new ArrayList<>();

        RunwaySceneLoader runwayScene1 = new RunwaySceneLoader(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
        RunwaySceneLoader runwayScene2 = new RunwaySceneLoader(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
        runwayScene1.buildmenulessalt();
        runwayScene2.buildmenulessalt();
        runwayScene2.scene.toggleView();



        VBox dualView = new VBox(runwayScene1.getRoot(),runwayScene2.getRoot());
        for (RunwaySceneLoader scene: new RunwaySceneLoader[] {runwayScene1,runwayScene2}) {
            scene.root.maxWidthProperty().bind(dualView.widthProperty());
            scene.root.minWidthProperty().bind(dualView.widthProperty());
            scene.root.maxHeightProperty().bind(dualView.heightProperty().divide(2));
            scene.root.minHeightProperty().bind(dualView.heightProperty().divide(2));

        }

        RunwaySceneLoader runwayScene3 = new RunwaySceneLoader(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
        runwayScene3.buildmenulessalt();
        if (Settings.portrait.get()) {
            runwayScene3.scene.angleXProperty().set(180);
            runwayScene3.scene.angleYProperty().set(0);
            runwayScene3.scene.angleZProperty().set(-90);
            runwayScene3.scene.portrait.set(true);
        }
        topView = new VBox(runwayScene3.getRoot());

        runwayScene3.root.maxWidthProperty().bind(topView.widthProperty());
        runwayScene3.root.minWidthProperty().bind(topView.widthProperty());
        runwayScene3.root.maxHeightProperty().bind(topView.heightProperty());
        runwayScene3.root.minHeightProperty().bind(topView.heightProperty());

        RunwaySceneLoader runwayScene4 = new RunwaySceneLoader(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
        runwayScene4.buildmenulessalt();
        if (Settings.portrait.get()) {
            runwayScene4.scene.angleYProperty().set(90);
            runwayScene4.scene.angleXProperty().set(90);
            runwayScene4.scene.portrait.set(true);
        }else {
            runwayScene4.scene.toggleView();
        }
        VBox sideView = new VBox(runwayScene4.getRoot());

        runwayScene4.root.maxWidthProperty().bind(sideView.widthProperty());
        runwayScene4.root.minWidthProperty().bind(sideView.widthProperty());
        runwayScene4.root.maxHeightProperty().bind(sideView.heightProperty());
        runwayScene4.root.minHeightProperty().bind(sideView.heightProperty());

        runwayScene1.getRoot().setOnMousePressed((e) -> appWindow.startRunwayScene());
        runwayScene2.getRoot().setOnMousePressed((e) -> appWindow.startRunwaySceneRotated());
        topView.setOnMousePressed((e) -> appWindow.startRunwayScene());
        sideView.setOnMousePressed((e) -> appWindow.startRunwaySceneRotated());


        refreshables.add(runwayScene1);
        refreshables.add(runwayScene2);
        refreshables.add(runwayScene3);
        refreshables.add(runwayScene4);

        viewTabs.add(new Pair<>("Both Views", dualView));
        viewTabs.add(new Pair<>("Side-On Views", sideView));
        viewTabs.add(new Pair<>("Top-Down Views", topView));
        TabLayout viewPane = new TabLayout(viewTabs,"focusedBG","veryfocusedBG");
        return viewPane;
    }

    /**
     * Make spinner spinner.
     *
     * @param binding the binding
     * @return the spinner
     */
    public Spinner makeSpinner(SimpleDoubleProperty binding){
        Spinner spinner = new Spinner();
        SpinnerValueFactory<Double> svf = new SpinnerValueFactory.DoubleSpinnerValueFactory(0,999999999,binding.get());
        spinner.setValueFactory(svf);
        spinner.editableProperty().set(true);
        spinner.getStyleClass().add("font");
        spinner.getEditor().setStyle("-fx-padding: 4px 10px 4px 10px;");


        spinner.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (Objects.equals(t1, "")) {
                    binding.set(0);
                } else {
                    try {
                        binding.set(Double.parseDouble(t1));
                    } catch (Exception e) {
                        displayErrorMessage("Invalid Entry", t1 + " must be a number");
                        spinner.getEditor().setText(s);
                    }
                }
            }
        });
        binding.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (Double.parseDouble(spinner.getEditor().textProperty().get()) != t1.doubleValue()) {
                    spinner.getEditor().setText(Double.toString(t1.doubleValue()));
                }
            }
        });

        return spinner;
    }

    /**
     * Displays an error message dialog box with the specified title and message.
     *
     * @param title   the title of the dialog box
     * @param message the message to display in the dialog box
     */
    private void displayErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Creates a new Button with the specified label and adds it to the given parent Pane.
     *
     * @param property the property
     * @param label1   the label to use for the first Button.
     * @param label2   the label to use for the second Button.
     * @return the created Button Node.
     */
    public Node makeButton(SimpleBooleanProperty property, String label1, String label2) {
        HBox segment = new HBox();
        ToggleButton button = new ToggleButton(label1);
        ToggleButton button2 = new ToggleButton(label2);
        button.setPadding(new Insets(0));
        button2.setPadding(new Insets(0));

        button.getStyleClass().add("font");
        button.getStyleClass().add("toggleButtonNotFocused");
        button2.getStyleClass().add("font");
        button2.getStyleClass().add("toggleButtonFocused");
        segment.getChildren().addAll(button,button2);

        button.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (button2.selectedProperty().get() == t1){
                    button2.selectedProperty().set(!t1);
                }
                if (t1) {
                    property.set(true);

                    button.getStyleClass().remove("toggleButtonNotFocused");
                    button.getStyleClass().add("toggleButtonFocused");

                    button2.getStyleClass().remove("toggleButtonFocused");
                    button2.getStyleClass().add("toggleButtonNotFocused");
                } else {
                    button.getStyleClass().remove("toggleButtonFocused");
                    button.getStyleClass().add("toggleButtonNotFocused");

                    button2.getStyleClass().remove("toggleButtonNotFocused");
                    button2.getStyleClass().add("toggleButtonFocused");
                }
            }
        });
        button2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (button.selectedProperty().get() == t1){
                    button.selectedProperty().set(!t1);
                }
                if (t1) {
                    property.set(false);

                    button.getStyleClass().remove("toggleButtonFocused");
                    button.getStyleClass().add("toggleButtonNotFocused");

                    button2.getStyleClass().remove("toggleButtonNotFocused");
                    button2.getStyleClass().add("toggleButtonFocused");

                } else {
                    button.getStyleClass().remove("toggleButtonNotFocused");
                    button.getStyleClass().add("toggleButtonFocused");

                    button2.getStyleClass().remove("toggleButtonFocused");
                    button2.getStyleClass().add("toggleButtonNotFocused");
                }
            }
        });
        button.minWidthProperty().bind(segment.widthProperty().divide(2).subtract(8));
        button.maxWidthProperty().bind(segment.widthProperty().divide(2).subtract(8));
        button2.minWidthProperty().bind(segment.widthProperty().divide(2).subtract(8));
        button2.maxWidthProperty().bind(segment.widthProperty().divide(2).subtract(8));
        button.minHeightProperty().bind(segment.heightProperty().subtract(12));
        button.maxHeightProperty().bind(segment.heightProperty().subtract(12));
        button2.minHeightProperty().bind(segment.heightProperty().subtract(12));
        button2.maxHeightProperty().bind(segment.heightProperty().subtract(12));

        button.selectedProperty().set(property.get());
        button2.selectedProperty().set(!property.get());
        segment.setPadding(new Insets(4,0,4,0));
        return segment;
    }


}

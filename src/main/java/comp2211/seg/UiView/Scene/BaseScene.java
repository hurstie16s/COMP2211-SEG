package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.ProcessDataModel.Airport;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class BaseScene extends SceneAbstract implements GlobalVariables{
    private Button airportButton;
    private Button obstacleButton;
    private HBox topbar;
    private VBox layout;

    private static final Logger logger = LogManager.getLogger(BaseScene.class);


    /**
     * Constructor to create a SceneAbstract object.
     *
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     * @param width
     * @param height
     */
    public BaseScene(Pane root, AppWindow appWindow, double width, double height) {
        super(root, appWindow, width, height);
    }

    @Override
    public void initialise() {

    }

    public void build()  {
        super.build();
        root.setBackground(new Background(new BackgroundFill(GlobalVariables.unfocusedBG,null,null)));
        mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

        ArrayList<Pair<String, Pane>> tabs = new ArrayList<>();
        tabs.add(new Pair<>("Airport Configuration", makeAirportConfig()));
        tabs.add(new Pair<>("Obstacle Configuration", makeObstacleConfig()));
        TabLayout tabLayout = new TabLayout(tabs,GlobalVariables.unfocusedBG,GlobalVariables.focusedBG);
        mainPane.maxHeightProperty().bind(root.heightProperty());
        mainPane.minHeightProperty().bind(root.heightProperty());
        mainPane.maxWidthProperty().bind(root.widthProperty());
        mainPane.minWidthProperty().bind(root.widthProperty());
        mainPane.getChildren().add(tabLayout);
    }
    public Pane makeAirportConfig() {
        //Aleks do stuff here

        //main vBox
        VBox vBoxAirportLayout = new VBox();
        //table
        VBox vBoxTable = new VBox();
        VBox.setMargin(vBoxTable,new Insets(150,20,20,20));//Top/Right/Bottom/Left
        vBoxTable.getChildren().add(makeRunwayGridTable());

        //left menu
        Button airportsTempButton = new Button("Airports");
        Button runwaysTempButton = new Button("Runways");
        VBox leftMenu = new VBox(airportsTempButton,runwaysTempButton);
        VBox.setMargin(airportsTempButton,new Insets(30,20,20,20));
        VBox.setMargin(runwaysTempButton,new Insets(10,20,20,20));

        //right menu

        // Create image views for the icons
        ImageView exportIcon = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/export.png")).toExternalForm()));
        ImageView importIcon = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/import.png")).toExternalForm()));


        // Create the buttons and set their graphics
        Button exportButton = new Button("Export Airport", exportIcon);
        Button importButton = new Button("Import Airport", importIcon);

        // Set the size of the icon
        exportIcon.setFitHeight(16);
        exportIcon.setFitWidth(16);
        importIcon.setFitHeight(16);
        importIcon.setFitWidth(16);

        VBox rightMenu = new VBox(exportButton, importButton);
        VBox.setMargin(exportButton,new Insets(30,20,20,20));
        VBox.setMargin(importButton,new Insets(10,20,20,20));
        //Dark buttons style from css, can be done globally
        List<Button> darkButtons = new ArrayList<>();
        darkButtons.add(airportsTempButton);
        darkButtons.add(runwaysTempButton);
        darkButtons.add(exportButton);
        darkButtons.add(importButton);

        for (Button button : darkButtons) {
            button.getStyleClass().add("button-dark");
        }



        HBox hBoxMenuButtons = new HBox();
        Region region = new Region();
        HBox.setHgrow(region,Priority.ALWAYS);
        hBoxMenuButtons.getChildren().addAll(leftMenu,region,rightMenu);

        vBoxAirportLayout.getChildren().addAll(hBoxMenuButtons,vBoxTable);
        //return vBox
        return vBoxAirportLayout;
    }

    private Pane makeRunwayGridTable() {

        GridPane runwayGrid = new GridPane();

        runwayGrid.setVgap(20);
        runwayGrid.setGridLinesVisible(true);

        Label label1 = makeLabel("Runway(RWY)");
        Label label2 = makeLabel("Runway Strip");
        Label label3 = makeLabel("Stopway(SWY)");
        Label label4 = makeLabel("Clearway(CWY)");
        Label label5 = makeLabel("RESA");
        Label label6 = makeLabel("Threshold\nDisplacement");
        Label label7 = makeLabel("Strip End");
        Label label8 = makeLabel("Blast\nProtection");
        Label label9 = makeLabel("60m");
        Label label10 = makeLabel("300m");

        GridPane.setColumnSpan(label1,2);
        GridPane.setColumnSpan(label2,2);
        GridPane.setColumnSpan(label3,2);
        GridPane.setColumnSpan(label4,2);
        GridPane.setColumnSpan(label5,2);
        GridPane.setColumnSpan(label6,2);
        GridPane.setRowSpan(label6,2);
        GridPane.setRowSpan(label7,2);
        GridPane.setRowSpan(label8,2);
        GridPane.setRowSpan(label9,2);
        GridPane.setRowSpan(label10,2);

        runwayGrid.add(label1,1,0);
        runwayGrid.add(label2,3,0);
        runwayGrid.add(label3,5,0);
        runwayGrid.add(label4,7,0);
        runwayGrid.add(label5,9,0);
        runwayGrid.add(label6,11,0);
        runwayGrid.add(label7,13,0);
        runwayGrid.add(label8,14,0);
        runwayGrid.add(label9,13,2);
        runwayGrid.add(label10,14,2);

        runwayGrid.add(makeLabel("Length"),1,1);
        runwayGrid.add(makeLabel("Length"),3,1);
        runwayGrid.add(makeLabel("Length"),5,1);
        runwayGrid.add(makeLabel("Length"),7,1);
        runwayGrid.add(makeLabel("Length"),9,1);

        runwayGrid.add(makeLabel("Width"),2,1);
        runwayGrid.add(makeLabel("Width"),4,1);
        runwayGrid.add(makeLabel("Width"),6,1);
        runwayGrid.add(makeLabel("Width"),8,1);
        runwayGrid.add(makeLabel("Width"),10,1);


        runwayGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorProperty(),new SimpleBooleanProperty(true)),0,2);
        runwayGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorProperty(),new SimpleBooleanProperty(true)),0,3);

        ArrayList<ColumnConstraints> cc = new ArrayList<>();
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(120/6.2);
        //cc1.setPercentWidth(60/6.2);
        cc.add(cc1);
        for (int i = 0; i < 14; i++) {
            ColumnConstraints ccx = new ColumnConstraints();
            if(i>11) {
             ccx.setPercentWidth(100/5.2);
             logger.info("constrains1:" + ccx + "/i: " + i);
            } else {
                //ccx.setPercentWidth(100/6.2);
                ccx.setPercentWidth(100 / 6.2);
                logger.info("constrains2:" + ccx + "/i: " + i);
            }
            cc.add(ccx);

        }
        runwayGrid.getColumnConstraints().addAll(cc);

        /*ArrayList<RowConstraints> rc = new ArrayList<>();
        for (int i = 0; i < 14; i++) {

            RowConstraints rcx = new RowConstraints();
            rcx.setPercentHeight(100/5);
            logger.info("constrains3:" + rcx + "/i: " + i);
            rc.add(rcx);
        }
        runwayGrid.getRowConstraints().addAll(rc);

        /*ArrayList<Pair<String, Pane>> runwayTable = new ArrayList<>();
        runwayTable.add(new Pair<>("Runway Table", runwayGrid));
        Pane runwayGridPane = new TabLayout(runwayTable,GlobalVariables.focusedBG,GlobalVariables.veryfocusedBG);
        */

        runwayGrid.getChildren().forEach(new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                GridPane.setHalignment(node, HPos.CENTER);
            }
        });
        return runwayGrid;
    }

    public Pane makeObstacleConfig(){
        HBox obstacleLayout = new HBox();
        VBox leftPane = new VBox();
        makeObstaclePane(leftPane);
        VBox rightPane = new VBox(makeRunwayTabs());

        leftPane.maxWidthProperty().bind(obstacleLayout.widthProperty().subtract(10).divide(2));
        leftPane.minWidthProperty().bind(obstacleLayout.widthProperty().subtract(10).divide(2));
        rightPane.maxWidthProperty().bind(obstacleLayout.widthProperty().subtract(10).divide(2));
        rightPane.minWidthProperty().bind(obstacleLayout.widthProperty().subtract(10).divide(2));
        leftPane.maxHeightProperty().bind(obstacleLayout.heightProperty().subtract(10));
        leftPane.minHeightProperty().bind(obstacleLayout.heightProperty().subtract(10));
        rightPane.maxHeightProperty().bind(obstacleLayout.heightProperty().subtract(10));
        rightPane.minHeightProperty().bind(obstacleLayout.heightProperty().subtract(10));


        obstacleLayout.getChildren().addAll(leftPane,rightPane);
        return obstacleLayout;
    }
    public void makeObstaclePane(VBox obstaclePane){
        HBox topHalf = new HBox();
        topHalf.maxWidthProperty().bind(obstaclePane.widthProperty());
        topHalf.minWidthProperty().bind(obstaclePane.widthProperty());
        topHalf.maxHeightProperty().bind(obstaclePane.heightProperty().divide(2));
        topHalf.minHeightProperty().bind(obstaclePane.heightProperty().divide(2));


        Pane obstacleOptionsPane = new Pane(makeObstacleOptionsPane());
        obstacleOptionsPane.maxWidthProperty().bind(topHalf.widthProperty().divide(1.5));
        obstacleOptionsPane.minWidthProperty().bind(topHalf.widthProperty().divide(1.5));
        obstacleOptionsPane.maxHeightProperty().bind(topHalf.heightProperty());
        obstacleOptionsPane.minHeightProperty().bind(topHalf.heightProperty());

        Pane changeHistoryPane = new Pane(makeChangeHistoryPane());
        changeHistoryPane.maxWidthProperty().bind(topHalf.widthProperty().divide(3));
        changeHistoryPane.minWidthProperty().bind(topHalf.widthProperty().divide(3));
        changeHistoryPane.maxHeightProperty().bind(topHalf.heightProperty());
        changeHistoryPane.minHeightProperty().bind(topHalf.heightProperty());

        topHalf.getChildren().addAll(obstacleOptionsPane, changeHistoryPane);

        VBox bottomHalf = new VBox();
        bottomHalf.maxWidthProperty().bind(obstaclePane.widthProperty());
        bottomHalf.minWidthProperty().bind(obstaclePane.widthProperty());
        bottomHalf.maxHeightProperty().bind(obstaclePane.heightProperty().divide(2));
        bottomHalf.minHeightProperty().bind(obstaclePane.heightProperty().divide(2));

        Pane declaredDistancesPane = new Pane(makeDistancesPane());
        declaredDistancesPane.maxWidthProperty().bind(bottomHalf.widthProperty());
        declaredDistancesPane.minWidthProperty().bind(bottomHalf.widthProperty());
        declaredDistancesPane.maxHeightProperty().bind(bottomHalf.heightProperty().divide(2));
        declaredDistancesPane.minHeightProperty().bind(bottomHalf.heightProperty().divide(2));

        Pane breakDownPane = new Pane(makeBreakDownPane());
        breakDownPane.maxWidthProperty().bind(bottomHalf.widthProperty());
        breakDownPane.minWidthProperty().bind(bottomHalf.widthProperty());
        breakDownPane.maxHeightProperty().bind(bottomHalf.heightProperty().divide(2));
        breakDownPane.minHeightProperty().bind(bottomHalf.heightProperty().divide(2));

        bottomHalf.getChildren().addAll(declaredDistancesPane, breakDownPane);

        obstaclePane.getChildren().addAll(topHalf,bottomHalf);

    }

    private Pane makeChangeHistoryPane() {
        ArrayList<Pair<String, Pane>> changeHistory = new ArrayList<>();
        ScrollPane history = new ScrollPane(appWindow.runway.changesHistory);
        history.setFitToWidth(true);
        history.setPadding(new Insets(16));
        changeHistory.add(new Pair<>("Change History", new BorderPane(history)));
        Pane changeHistoryPane = new TabLayout(changeHistory,GlobalVariables.focusedBG,GlobalVariables.veryfocusedBG);
        return changeHistoryPane;
    }

    private Pane makeObstacleOptionsPane() {
        ArrayList<Pair<String, Pane>> obstacleOptions = new ArrayList<>();
        GridPane obstacleData = new GridPane();

        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(60);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(40);
        obstacleData.getColumnConstraints().addAll(cc1,cc2);

        ArrayList<RowConstraints> rc = new ArrayList<>();
        for (int i = 0; i < 7; i++) {

            RowConstraints rcx = new RowConstraints();
            rcx.setPercentHeight(100/7);
            rc.add(rcx);
        }
        obstacleData.getRowConstraints().addAll(rc);

        obstacleData.addColumn(0,makeLabel("Preset"),makeLabel("Height"),makeLabel("Length"),makeLabel("Width"),makeLabel("Left Displacement"),makeLabel("Right Displacement"),makeLabel("Currently Active?"));
        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().heightProperty()),1,1);
        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().lengthProperty()),1,2);
        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().widthProperty()),1,3);
        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().distFromThresholdProperty()),1,4);
        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().distFromOtherThresholdProperty()),1,5);
        obstacleData.getChildren().forEach(new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                GridPane.setHalignment(node, HPos.RIGHT);
            }
        });


        obstacleOptions.add(new Pair<>("Obstacle", obstacleData));
        Pane obstacleOptionsPane = new TabLayout(obstacleOptions,GlobalVariables.focusedBG,GlobalVariables.veryfocusedBG);
        return obstacleOptionsPane;
    }
    private Pane makeDistancesPane() {
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


        distancesGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorProperty(),new SimpleBooleanProperty(true)),1,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputLeftToraProperty(),new SimpleBooleanProperty(true)),2,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputLeftTodaProperty(),new SimpleBooleanProperty(true)),3,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputLeftLdaProperty(),new SimpleBooleanProperty(true)),4,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputLeftAsdaProperty(),new SimpleBooleanProperty(true)),5,1);
        distancesGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorProperty(),new SimpleBooleanProperty(true)),1,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputRightToraProperty(),new SimpleBooleanProperty(true)),2,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputRightTodaProperty(),new SimpleBooleanProperty(true)),3,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputRightLdaProperty(),new SimpleBooleanProperty(true)),4,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.inputRightAsdaProperty(),new SimpleBooleanProperty(true)),5,2);
        distancesGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorProperty(),new SimpleBooleanProperty(true)),1,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.leftToraProperty(),new SimpleBooleanProperty(true)),2,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.leftTodaProperty(),new SimpleBooleanProperty(true)),3,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.leftLdaProperty(),new SimpleBooleanProperty(true)),4,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.leftAsdaProperty(),new SimpleBooleanProperty(true)),5,3);
        distancesGrid.add(makeOutputLabel(appWindow.runway.runwayDesignatorProperty(),new SimpleBooleanProperty(true)),1,4);
        distancesGrid.add(makeOutputLabel(appWindow.runway.rightToraProperty(),new SimpleBooleanProperty(true)),2,4);
        distancesGrid.add(makeOutputLabel(appWindow.runway.rightTodaProperty(),new SimpleBooleanProperty(true)),3,4);
        distancesGrid.add(makeOutputLabel(appWindow.runway.rightLdaProperty(),new SimpleBooleanProperty(true)),4,4);
        distancesGrid.add(makeOutputLabel(appWindow.runway.rightAsdaProperty(),new SimpleBooleanProperty(true)),5,4);

        ArrayList<Pair<String, Pane>> declaredDistances = new ArrayList<>();
        declaredDistances.add(new Pair<>("Declared Distances", distancesGrid));
        Pane declaredDistancesPane = new TabLayout(declaredDistances,GlobalVariables.focusedBG,GlobalVariables.veryfocusedBG);
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
        data.setFont(GlobalVariables.font);
        data.setTextFill(GlobalVariables.fg);
        data.setText(String.valueOf(runwayDesignatorProperty.getValue()));
        data.textProperty().bind(Bindings.when(visibility).then(runwayDesignatorProperty).otherwise(new
                SimpleStringProperty("Error")));
        return data;

    }

    public Label makeLabel(String string){
        Label label = new Label(string);
        label.setFont(GlobalVariables.font);
        label.setTextFill(GlobalVariables.fg);
        return label;
    }
    public Label makeOutputLabel(SimpleDoubleProperty property, SimpleBooleanProperty visibility) {
        Label data = new Label();
        data.setFont(GlobalVariables.font);
        data.setTextFill(GlobalVariables.fg);
        data.setText(String.valueOf(property.getValue()));
        data.textProperty().bind(Bindings.when(visibility).then(property.asString().concat(appWindow.runway.unitsProperty())).otherwise(new
                SimpleStringProperty("Error")));
        return data;
    }

    private Pane makeBreakDownPane() {
        ArrayList<Pair<String, Pane>> breakDown = new ArrayList<>();
        breakDown.add(new Pair<>("TORA Breakdown", new VBox()));
        breakDown.add(new Pair<>("TODA Breakdown", new VBox()));
        breakDown.add(new Pair<>("ASDA Breakdown", new VBox()));
        breakDown.add(new Pair<>("LDA Breakdown", new VBox()));
        Pane breakDownPane = new TabLayout(breakDown,GlobalVariables.focusedBG,GlobalVariables.veryfocusedBG);
        return breakDownPane;
    }

    public Pane makeRunwayTabs(){
        ArrayList<Pair<String, Pane>> viewTabs = new ArrayList<>();
        RunwayScene runwayScene1 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/4.0,appWindow.getHeight()/4.0,false);
        RunwayScene runwayScene2 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/4.0,appWindow.getHeight()/4.0,false);
        runwayScene1.build();
        runwayScene2.build();
        runwayScene2.toggleView();
        VBox dualView = new VBox(runwayScene1.getRoot(),runwayScene2.getRoot());
        for (RunwayScene scene: new RunwayScene[] {runwayScene1,runwayScene2}) {
            scene.root.maxWidthProperty().bind(dualView.widthProperty());
            scene.root.minWidthProperty().bind(dualView.widthProperty());
            scene.root.maxHeightProperty().bind(dualView.heightProperty().divide(2));
            scene.root.minHeightProperty().bind(dualView.heightProperty().divide(2));

        }

        RunwayScene runwayScene3 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/4.0,appWindow.getHeight()/4.0,false);
        runwayScene3.build();
        runwayScene3.angleXProperty().set(180);
        runwayScene3.angleYProperty().set(0);
        runwayScene3.angleZProperty().set(-90);
        VBox topView = new VBox(runwayScene3.getRoot());
        runwayScene3.portrait.set(true);

        runwayScene3.root.maxWidthProperty().bind(topView.widthProperty());
        runwayScene3.root.minWidthProperty().bind(topView.widthProperty());
        runwayScene3.root.maxHeightProperty().bind(topView.heightProperty());
        runwayScene3.root.minHeightProperty().bind(topView.heightProperty());

        RunwayScene runwayScene4 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/4.0,appWindow.getHeight()/4.0,false);
        runwayScene4.build();
        runwayScene4.angleYProperty().set(90);
        runwayScene4.angleXProperty().set(90);
        VBox sideView = new VBox(runwayScene4.getRoot());
        runwayScene4.portrait.set(true);

        runwayScene4.root.maxWidthProperty().bind(sideView.widthProperty());
        runwayScene4.root.minWidthProperty().bind(sideView.widthProperty());
        runwayScene4.root.maxHeightProperty().bind(sideView.heightProperty());
        runwayScene4.root.minHeightProperty().bind(sideView.heightProperty());

        dualView.setOnMousePressed((e) -> appWindow.startRunwayScene());
        topView.setOnMousePressed((e) -> appWindow.startRunwayScene());
        sideView.setOnMousePressed((e) -> appWindow.startRunwayScene());



        viewTabs.add(new Pair<>("Both Views", dualView));
        viewTabs.add(new Pair<>("Side-On Views", sideView));
        viewTabs.add(new Pair<>("Top-Down Views", topView));
        TabLayout viewPane = new TabLayout(viewTabs,GlobalVariables.focusedBG,GlobalVariables.veryfocusedBG);
        return viewPane;
    }

    public Spinner makeSpinner(SimpleDoubleProperty binding){
        Spinner spinner = new Spinner();
        binding.bind(spinner.valueProperty());

        return spinner;
    }


}

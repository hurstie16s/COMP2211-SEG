package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.FileHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

public class BaseScene extends SceneAbstract implements GlobalVariables{
    private Button airportButton;
    private Button obstacleButton;
    private HBox topbar;
    private VBox layout;


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

    public void build(){
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
    public Pane makeAirportConfig(){
        //Aleks do stuff here
        VBox airportLayout = new VBox();

        ComboBox airports = new ComboBox(FXCollections.observableArrayList(appWindow.getAirports()));
        airports.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                appWindow.setAirport((Airport) t1);
            }
        });
        airports.valueProperty().set(appWindow.airport);
        ComboBox runways = new ComboBox<>();

        GridPane tablePane = new GridPane();
        makeTablePane(tablePane);

        Button exportButton = new Button("Export Airport");

        exportButton.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose file to export");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("XML format(*.xml)","*.xml"));
            File file = fileChooser.showSaveDialog(new Stage());

            if (file == null)
                return;

            if(!file.getName().contains(".xml"))
                file = new File(file.getAbsolutePath()+".xml");



        });

        Button importButton = new Button("Import Airport");

        VBox leftMenu = new VBox(airports,runways);
        VBox rightMenu = new VBox(exportButton, importButton);
        Region region = new Region();
        HBox.setHgrow(region,Priority.ALWAYS);
        HBox menuPane = new HBox(leftMenu,region,rightMenu);
        airportLayout.getChildren().addAll(menuPane,tablePane);
        return airportLayout;





    }




    private void makeTablePane(GridPane table) {
        //Aleks: To do
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

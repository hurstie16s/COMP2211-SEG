package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Interfaces.UKAirportsRunways;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Settings;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.FileHandler;
import comp2211.seg.Controller.Stage.Theme;
import comp2211.seg.ProcessDataModel.Obstacle;
import comp2211.seg.ProcessDataModel.Runway;
import javafx.application.Platform;
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
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private TabLayout tabLayout;


    /**
     * Constructor to create a SceneAbstract object.
     *
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     * @param width     the width
     * @param height    the height
     */
    public BaseScene(Pane root, AppWindow appWindow, double width, double height) {
        super(root, appWindow, width, height);
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
    }

    /**
     * Select obstacle menu.
     */
    public void selectObstacleMenu(){
        tabLayout.tabButtons.get(1).fire();
    }

    public void build()  {
        super.build();
        root.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG,null,null)));
        mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

        ArrayList<Pair<String, Pane>> tabs = new ArrayList<>();
        tabs.add(new Pair<>("Airport Configuration", makeAirportConfig()));


        tabs.add(new Pair<>("Obstacle Configuration", makeObstacleConfig()));
        tabLayout = new TabLayout(tabs,Theme.unfocusedBG,Theme.focusedBG);
        mainPane.maxHeightProperty().bind(root.heightProperty());
        mainPane.minHeightProperty().bind(root.heightProperty());
        mainPane.maxWidthProperty().bind(root.widthProperty());
        mainPane.minWidthProperty().bind(root.widthProperty());
        mainPane.getChildren().add(tabLayout);
    }

    /**
     * Make airport config pane.
     *
     * @return the pane
     */
    public Pane makeAirportConfig() {
        //Aleks do stuff here

        //main vBox
        VBox vBoxAirportLayout = new VBox();
        //table
        VBox vBoxTable = new VBox();
        VBox.setMargin(vBoxTable,new Insets(150,20,100,20));//Top/Right/Bottom/Left
        //vBoxTable.getChildren().add(makeRunwayGridTable());
        vBoxTable.getChildren().add(buildTableView());
        vBoxTable.maxWidthProperty().bind(vBoxAirportLayout.widthProperty().subtract(40));
        vBoxTable.minWidthProperty().bind(vBoxAirportLayout.widthProperty().subtract(40));

        //left menu
        //top
        ComboBox airportsCombo = new ComboBox(FXCollections.observableArrayList(appWindow.getAirports()));
        airportsCombo.setBackground(new Background(new BackgroundFill(Theme.veryfocusedBG,null,null)));
        airportsCombo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                appWindow.setAirport((Airport) t1);
                if (! (o == null)) {
                    if (!o.equals(t1)) {
                        appWindow.startBaseScene();
                    }
                }
            }
        });
        airportsCombo.valueProperty().set(appWindow.airport);
        Label airportsLabel = makeLabel("Airport");
        HBox hBoxAirports = new HBox();
        hBoxAirports.getChildren().addAll(airportsLabel,airportsCombo);
        HBox.setMargin(airportsCombo,new Insets(0,0,0,10));

        //bottom

        ComboBox runwaysCombo = new ComboBox(FXCollections.observableArrayList(appWindow.airport.getRunways())); //FXCollections.observableArrayList(appWindow.airport.getRunways()));
        runwaysCombo.setBackground(new Background(new BackgroundFill(Theme.veryfocusedBG,null,null)));
        runwaysCombo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                appWindow.setRunway((Runway) t1);
                if (! (o == null)) {
                    if (!o.equals(t1)) {
                        appWindow.startBaseScene();
                    }
                }
            }
        });
        runwaysCombo.valueProperty().set(appWindow.runway);
        Label runwaysLabel = makeLabel("Runway");
        HBox hBoxRunways = new HBox();
        hBoxRunways.getChildren().addAll(runwaysLabel,runwaysCombo);
        HBox.setMargin(runwaysCombo,new Insets(0,0,0,10));
        //left menu children
        VBox leftMenu = new VBox(hBoxAirports,hBoxRunways);
        VBox.setMargin(hBoxAirports,new Insets(30,20,20,20));
        VBox.setMargin(hBoxRunways,new Insets(10,20,20,20));

        //right menu
        // Create image views for the icons
        ImageView exportIcon1 = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/export.png")).toExternalForm()));
        ImageView importIcon1 = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/import.png")).toExternalForm()));
        ImageView exportIcon2 = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/export.png")).toExternalForm()));
        ImageView importIcon2 = new ImageView(new Image(Objects.requireNonNull(getClass()
            .getResource("/images/import.png")).toExternalForm()));

        // Create the buttons and set their graphics
        Button exportAirObsButton = new Button("Export Airport & Obstacle", exportIcon1);
        Button importAirObsButton = new Button("Import Airport & Obstacle", importIcon1);
        Button exportObstacle = new Button("Export Obstacle", exportIcon2);
        Button importObstacle = new Button("Import Obstacle", importIcon2);


        //Button events
        exportAirObsButton.setOnAction(e -> exportAirportButtonEvent());

        exportObstacle.setOnAction(e -> exportObstacleButtonEvent());

        importAirObsButton.setOnAction(e -> importAirportButtonEvent());

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
            button.setBackground(new Background(new BackgroundFill(Theme.focusedBG,null,null)));
            button.setTextFill(Theme.fg);
            button.setCursor(Cursor.HAND);
        }

        HBox hBoxMenuButtons = new HBox();
        Region region = new Region();
        HBox.setHgrow(region,Priority.ALWAYS);
        hBoxMenuButtons.getChildren().addAll(leftMenu,region,rightMenu);

        vBoxAirportLayout.getChildren().addAll(hBoxMenuButtons,vBoxTable);
        //return vBox
        return vBoxAirportLayout;
    }

    //exporting the Airport with runways and objects
    public void exportAirportButtonEvent() {
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

            if (FileHandler.exportAirport(file, appWindow.airport, appWindow.runway.runwayObstacle)) {
                FileHandler.exportAirport(file, appWindow.airport,appWindow.runway.runwayObstacle);
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

        for (int i = 0; i < 11; i++) {
            ColumnConstraints ccx = new ColumnConstraints();
            ccx.setPercentWidth(100/17);
            airportData.getColumnConstraints().add(ccx);
        }
        for (int i = 0; i < 3; i++) {

            ColumnConstraints ccx = new ColumnConstraints();
            ccx.setPercentWidth(200/17);
            airportData.getColumnConstraints().add(ccx);
        }

        ArrayList<RowConstraints> rc = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            RowConstraints rcx = new RowConstraints();
            rcx.setPercentHeight(100/4);
            rc.add(rcx);
        }
        String[] titles = new String[] {"Runway(RWY)","Runway Strip","Stopway(SWY)","Clearway(CWY)","RESA","Threshold\nDisplacement","Strip End","Blast\nProtection"};
        for (int i = 0; i < 8; i++) {

            Label data = makeLabel(titles[i]);
            data.setAlignment(Pos.CENTER);
            data.setTextAlignment(TextAlignment.CENTER);
            data.setFont(Theme.fontsmall);
            if (i>=5){
                GridPane.setRowSpan(data,2);
                airportData.add(data,6+i,0);
            }else{
                GridPane.setColumnSpan(data,2);
                //data.setFont(new Font("Calibri",17));
                airportData.add(data,1+i*2,0);
            }

        }
        for (int i = 0; i < 5; i++) {

            var lengthLabel = makeLabel("Length");
            var widthLabel = makeLabel("Width");

            lengthLabel.setFont(Theme.fontsmall);
            widthLabel.setFont(Theme.fontsmall);

            airportData.add(lengthLabel,1+i*2,1);
            airportData.add(widthLabel,2+i*2,1);
        }
        Label desl = makeLabel(appWindow.runway.getRunwayDesignatorLeft());
        desl.setAlignment(Pos.CENTER);
        airportData.add(desl,0,2);
        airportData.add(makeTableCell(appWindow.runway.runwayLengthProperty()),1,2);
        airportData.add(makeTableCell(appWindow.runway.runwayWidthProperty()),2,2);
        airportData.add(new TextField("-"),3,2);
        airportData.add(new TextField("-"),4,2);
        airportData.add(makeTableCell(appWindow.runway.stopwayLeftProperty()),5,2);
        airportData.add(new TextField("-"),6,2);
        airportData.add(makeTableCell(appWindow.runway.clearwayLeftProperty()),7,2);
        airportData.add(new TextField("-"),8,2);
        airportData.add(makeTableCell(appWindow.runway.RESAWidthProperty()),9,2);
        airportData.add(makeTableCell(appWindow.runway.RESAHeightProperty()),10,2);
        airportData.add(makeTableCell(appWindow.runway.dispThresholdLeftProperty()),11,2);
        airportData.add(makeTableCell(appWindow.runway.stripEndProperty()),12,2);
        airportData.add(new TextField("500m"),13,2);

        Label desr = makeLabel(appWindow.runway.getRunwayDesignatorRight());
        desr.setAlignment(Pos.CENTER);
        airportData.add(desr,0,3);
        airportData.add(makeTableCell(appWindow.runway.runwayLengthProperty()),1,3);
        airportData.add(makeTableCell(appWindow.runway.runwayWidthProperty()),2,3);
        airportData.add(new TextField("-"),3,3);
        airportData.add(new TextField("-"),4,3);
        airportData.add(makeTableCell(appWindow.runway.stopwayRightProperty()),5,3);
        airportData.add(new TextField("-"),6,3);
        airportData.add(makeTableCell(appWindow.runway.clearwayRightProperty()),7,3);
        airportData.add(new TextField("-"),8,3);
        airportData.add(makeTableCell(appWindow.runway.RESAWidthProperty()),9,3);
        airportData.add(makeTableCell(appWindow.runway.RESAHeightProperty()),10,3);
        airportData.add(makeTableCell(appWindow.runway.dispThresholdRightProperty()),11,3);
        airportData.add(makeTableCell(appWindow.runway.stripEndProperty()),12,3);
        airportData.add(new TextField("500m"),13,3);
        for (Node node:airportData.getChildren()) {
            if (node instanceof Control) {
                Control control = (Control) node;
                control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                control.setBorder(new Border(new BorderStroke(Theme.fg,BorderStrokeStyle.SOLID,null,new BorderWidths(1))));
            }
            if (node instanceof Pane) {
                Pane pane = (Pane) node;
                pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pane.setBorder(new Border(new BorderStroke(Theme.fg,BorderStrokeStyle.SOLID,null,new BorderWidths(1))));
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

    /**
     * Make obstacle pane.
     *
     * @param obstaclePane the obstacle pane
     */
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
        Pane changeHistoryPane = new TabLayout(changeHistory,Theme.focusedBG,Theme.veryfocusedBG);
        return changeHistoryPane;
    }

    private Pane makeObstacleOptionsPane() {
        // Obstacle preset ComboBox
        ComboBox obstacleComboBox = new ComboBox(FXCollections.observableArrayList(appWindow.obstaclePresets));
        obstacleComboBox.setBackground(new Background(new BackgroundFill(Theme.veryfocusedBG,null,null)));

        obstacleComboBox.setOnAction(event -> {
            Object selectedObstacle = obstacleComboBox.getSelectionModel().getSelectedItem();
            appWindow.runway.addObstacle((Obstacle) selectedObstacle);
        });

        obstacleComboBox.valueProperty().set("None");

        ArrayList<Pair<String, Pane>> obstacleOptions = new ArrayList<>();
        GridPane obstacleData = new GridPane();

        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(60);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(40);
        obstacleData.getColumnConstraints().addAll(cc1,cc2);

        ArrayList<RowConstraints> rc = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            RowConstraints rcx = new RowConstraints();
            rcx.setPercentHeight(100/7);
            rc.add(rcx);
        }
        RowConstraints rcx = new RowConstraints();
        rcx.setPercentHeight(200/7);
        rc.add(rcx);
        obstacleData.getRowConstraints().addAll(rc);

        obstacleData.addColumn(0,makeLabel("Preset"),makeLabel("Height (m)"),makeLabel("Width (m)"),makeLabel("Length (m)"),makeLabel("Currently Active?"),makeLabel("Position"));

        // Obstacle preset dropdown selector
        obstacleData.add(obstacleComboBox,1,0);

        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().heightProperty()),1,1);
        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().widthProperty()),1,2);
        obstacleData.add(makeSpinner(appWindow.runway.getRunwayObstacle().lengthProperty()),1,3);
        obstacleData.add(makeButton(appWindow.runway.hasRunwayObstacleProperty(),"No","Yes"),1,4);
        Slider posSlider = new Slider();
        posSlider.minProperty().bind(appWindow.runway.runwayObstacle.lengthProperty().divide(-2));
        posSlider.maxProperty().bind(appWindow.runway.runwayLengthProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)));
        posSlider.valueProperty().bindBidirectional(appWindow.runway.runwayObstacle.distFromThresholdProperty());
        VBox posLeft = new VBox(makeOutputLabel(new SimpleStringProperty("Left"),new SimpleBooleanProperty(true),18),makeOutputLabel(appWindow.runway.runwayObstacle.distFromThresholdProperty(),new SimpleBooleanProperty(true),5));
        VBox posRight = new VBox(makeOutputLabel(new SimpleStringProperty("Right"),new SimpleBooleanProperty(true),18),makeOutputLabel(appWindow.runway.runwayObstacle.distFromOtherThresholdProperty(),new SimpleBooleanProperty(true),5));
        posRight.setAlignment(Pos.CENTER_RIGHT);

        BorderPane posvals = new BorderPane();
        posvals.setLeft(posLeft);
        posvals.setRight(posRight);
        VBox position = new VBox(posSlider,posvals);
        position.setAlignment(Pos.CENTER);
        posvals.maxWidthProperty().bind(position.widthProperty().subtract(10));
        posvals.minWidthProperty().bind(position.widthProperty().subtract(10));
        posvals.setPadding(new Insets(5));
        obstacleData.add(position,1,5);
        obstacleData.getChildren().forEach(new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                GridPane.setHalignment(node, HPos.RIGHT);
            }
        });


        obstacleOptions.add(new Pair<>("Obstacle", obstacleData));
        Pane obstacleOptionsPane = new TabLayout(obstacleOptions,Theme.focusedBG,Theme.veryfocusedBG);
        return obstacleOptionsPane;
    }

    private Node makeOutputLabel(SimpleStringProperty string, SimpleBooleanProperty visibility, int i) {
        Label data = new Label();
        data.setFont(new Font(Theme.font.getName(),i));
        data.setTextFill(Theme.fg);
        data.setText(String.valueOf(string.getValue()));
        data.textProperty().bind(Bindings.when(visibility).then(string).otherwise(new
                SimpleStringProperty("Error")));
        return data;
    }

    private Node makeOutputLabel(SimpleDoubleProperty property, SimpleBooleanProperty visibility, int i) {
        Label data = new Label();
        data.setFont(Theme.fontsmall);
        data.setTextFill(Theme.fg);
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
        Pane declaredDistancesPane = new TabLayout(declaredDistances,Theme.focusedBG,Theme.veryfocusedBG);
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
        data.setFont(Theme.font);
        data.setTextFill(Theme.fg);
        data.setText(String.valueOf(runwayDesignatorProperty.getValue()));
        data.textProperty().bind(Bindings.when(visibility).then(runwayDesignatorProperty).otherwise(new
                SimpleStringProperty("Error")));
        return data;

    }
    private Pane makeOutputLabel(SimpleStringProperty prop1header,SimpleStringProperty prop1,SimpleStringProperty prop2header,SimpleStringProperty prop2) {

        Label dataheader = new Label();
        dataheader.setFont(Theme.font);
        dataheader.setTextFill(Theme.fg);
        dataheader.setText(String.valueOf(prop1header.getValue()));

        Label data = new Label();
        data.setFont(Theme.font);
        data.setTextFill(Theme.fg);
        data.setText(String.valueOf(prop1.getValue()));

        Label data2header = new Label();
        data2header.setFont(Theme.font);
        data2header.setTextFill(Theme.fg);
        data2header.setText(String.valueOf(prop2header.getValue()));

        Label data2 = new Label();
        data2.setFont(Theme.font);
        data2.setTextFill(Theme.fg);
        data2.setText(String.valueOf(prop2.getValue()));

        VBox box = new VBox(dataheader,data,data2header,data2);
        VBox.setVgrow(data, Priority.ALWAYS);
        box.setAlignment(Pos.CENTER);

        return box;

    }

    /**
     * Make label label.
     *
     * @param string the string
     * @return the label
     */
    public Label makeLabel(String string){
        Label label = new Label(string);
        label.setFont(Theme.font);
        label.setTextFill(Theme.fg);
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
        data.setFont(Theme.font);
        data.setTextFill(Theme.fg);
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

    private Pane makeBreakDownPane() {
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
        Pane breakDownPane = new TabLayout(breakDown,Theme.focusedBG,Theme.veryfocusedBG);
        return breakDownPane;
    }
    private TextField makeTableCell(SimpleDoubleProperty property){
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setBorder(new Border(new BorderStroke(Theme.fg,BorderStrokeStyle.SOLID,null,new BorderWidths(1))));
        textField.textProperty().set(property.asString().get());
        textField.editableProperty().set(false);
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!s.equals(t1)){
                    if (Objects.equals(t1, "")) {
                        property.set(0);
                    } else {
                        try {
                            property.set(Double.parseDouble(t1));
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

    private TextField makeTableCell(SimpleStringProperty property){
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setBorder(new Border(new BorderStroke(Theme.fg,BorderStrokeStyle.SOLID,null,new BorderWidths(1))));
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
        RunwayScene runwayScene1 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/4.0,appWindow.getHeight()/4.0,false);
        RunwayScene runwayScene2 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/4.0,appWindow.getHeight()/4.0,false);
        runwayScene1.buildmenulessalt();
        runwayScene2.buildmenulessalt();
        runwayScene2.toggleView();
        VBox dualView = new VBox(runwayScene1.getRoot(),runwayScene2.getRoot());
        for (RunwayScene scene: new RunwayScene[] {runwayScene1,runwayScene2}) {
            scene.root.maxWidthProperty().bind(dualView.widthProperty());
            scene.root.minWidthProperty().bind(dualView.widthProperty());
            scene.root.maxHeightProperty().bind(dualView.heightProperty().divide(2));
            scene.root.minHeightProperty().bind(dualView.heightProperty().divide(2));

        }

        RunwayScene runwayScene3 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/4.0,appWindow.getHeight()/4.0,false);
        runwayScene3.buildmenulessalt();
        if (Settings.portrait.get()) {
            runwayScene3.angleXProperty().set(180);
            runwayScene3.angleYProperty().set(0);
            runwayScene3.angleZProperty().set(-90);
            runwayScene3.portrait.set(true);
        }
        VBox topView = new VBox(runwayScene3.getRoot());

        runwayScene3.root.maxWidthProperty().bind(topView.widthProperty());
        runwayScene3.root.minWidthProperty().bind(topView.widthProperty());
        runwayScene3.root.maxHeightProperty().bind(topView.heightProperty());
        runwayScene3.root.minHeightProperty().bind(topView.heightProperty());

        RunwayScene runwayScene4 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/4.0,appWindow.getHeight()/4.0,false);
        runwayScene4.buildmenulessalt();
        if (Settings.portrait.get()) {
            runwayScene4.angleYProperty().set(90);
            runwayScene4.angleXProperty().set(90);
            runwayScene4.portrait.set(true);
        }else {
            runwayScene4.toggleView();
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



        viewTabs.add(new Pair<>("Both Views", dualView));
        viewTabs.add(new Pair<>("Side-On Views", sideView));
        viewTabs.add(new Pair<>("Top-Down Views", topView));
        TabLayout viewPane = new TabLayout(viewTabs,Theme.focusedBG,Theme.veryfocusedBG);
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
                spinner.getEditor().setText(Double.toString(t1.doubleValue()));
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
        button.setFont(Theme.font);
        button.setTextFill(Theme.fg);
        button .setBackground(new Background(new BackgroundFill(Theme.focusedBG,null,null)));
        button2.setFont(Theme.font);
        button2.setTextFill(Theme.fg);
        button2.setBackground(new Background(new BackgroundFill(Theme.veryfocusedBG,null,null)));
        segment.getChildren().addAll(button,button2);
        button.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (button2.selectedProperty().get() == t1){
                    button2.selectedProperty().set(!t1);
                }
                if (t1) {
                    property.set(true);
                    button2.setBackground(new Background(new BackgroundFill(Theme.extremelyfocusedBG,null,null)));
                    button2.setTextFill(Theme.fg);
                    button.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG,null,null)));
                    button.setTextFill(Theme.extremelyfocusedBG);
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
                    button2.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG,null,null)));
                    button2.setTextFill(Theme.extremelyfocusedBG);
                    button.setBackground(new Background(new BackgroundFill(Theme.extremelyfocusedBG,null,null)));
                    button.setTextFill(Theme.fg);
                }
            }
        });
        segment.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                button.setMinWidth(0);
                button2.setMinWidth(0);
                button.setMinWidth(t1.doubleValue() /2-10);
                button2.setMinWidth(t1.doubleValue() /2-10);
                button.setMaxWidth(t1.doubleValue() /2);
                button2.setMaxWidth(t1.doubleValue() /2);
            }
        });
        segment.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                button.setMinHeight(0);
                button2.setMinHeight(0);
                button.setMinHeight(t1.doubleValue()-10);
                button2.setMinHeight(t1.doubleValue()-10);
                button.setMaxHeight(t1.doubleValue());
                button2.setMaxHeight(t1.doubleValue());
            }
        });

        button.selectedProperty().set(property.get());
        button2.selectedProperty().set(!property.get());
        segment.setPadding(new Insets(0,0,0,10));
        return segment;
    }

}

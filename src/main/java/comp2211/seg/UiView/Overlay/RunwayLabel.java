package comp2211.seg.UiView.Overlay;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Theme;
import comp2211.seg.UiView.Scene.RunwayScene;
import comp2211.seg.UiView.Scene.RunwaySceneLoader;
import comp2211.seg.UiView.Scene.Utilities.CssColorParser;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

/**
 * The RunwayLabel class represents a label for a runway that includes the runway name and a line indicating its location
 * and direction. The label is rendered as a Group that can be added to a JavaFX scene.
 */
public class RunwayLabel extends Group {
    private final RunwayScene scene;
    private boolean direction;
    private String name;
    private final Rotate xRotate;
    private final Rotate yRotate;
    private final Rotate zRotate;
    private final Text label;

    /**
     * Constructs a new RunwayLabel with the specified name, color, x-offset, y-offset, length, scene, and direction.
     *
     * @param name       The name of the runway.
     * @param color      The color of the label and line.
     * @param xOffset    The x-offset of the label.
     * @param yOffset    The y-offset of the label.
     * @param length     The length of the line.
     * @param scene      The RunwayScene to which the label belongs.
     * @param direction  The direction of the runway.
     * @param visibility the visibility
     */
    public RunwayLabel(String name, Color color, DoubleBinding xOffset, double yOffset, DoubleBinding length, RunwayScene scene, boolean direction, SimpleBooleanProperty visibility) {
        this.scene = scene;
        this.direction = direction;
        this.name = name;
        this.visibleProperty().bind(visibility.and(Bindings.when(new SimpleBooleanProperty(direction)).then(Bindings.lessThan(length,0)).otherwise(Bindings.greaterThan(length,0))));
        /**
        visibleProperty().bind
                (Bindings.and(
                        Bindings.when(new SimpleBooleanProperty(direction))
                        .then(Bindings.greaterThanOrEqual(0, length))
                        .otherwise(Bindings.lessThanOrEqual(0, length)),
                        visibility));
         */

        Group labelRotateGroup = new Group();
        label = new Text(name);
        length.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                label.textProperty().bind((new SimpleStringProperty(name)).concat(" (").concat(Bindings.when(Bindings.lessThanOrEqual(0, length)).then(Long.toString(Math.round(length.get()))).otherwise(Long.toString(Math.round(length.get()*-1)))).concat(scene.appWindow.runway.unitsProperty()).concat(")"));
            }
        });
        label.textProperty().bind((new SimpleStringProperty(name)).concat(" (").concat(Bindings.when(Bindings.lessThanOrEqual(0, length)).then(Long.toString(Math.round(length.get()))).otherwise(Long.toString(Math.round(length.get()*-1)))).concat(scene.appWindow.runway.unitsProperty()).concat(")"));
        //label.setFill(Theme.getLabelFg);
        label.getStyleClass().clear();
        label.getStyleClass().add("runwaylabelfg");
        label.setFill(Theme.getLabelFg());
        //label.setFont(Theme.font);
        scene.root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                label.setFont(new Font(t1.doubleValue()/25));
                if (direction) {
                    label.yProperty().set((t1.doubleValue()/30) / 2 + 8);
                    label.xProperty().set(-label.getBoundsInLocal().getWidth() * ((t1.doubleValue()/25) / label.getBoundsInLocal().getHeight()) / 2);
                } else {
                    label.yProperty().set(-5);
                    label.xProperty().set(-label.getBoundsInLocal().getWidth() * ((t1.doubleValue()/25) / label.getBoundsInLocal().getHeight()) / 2 - 10);
                }

            }
        });
        label.setFont(new Font(scene.root.heightProperty().doubleValue()/25));
        if (direction) {
            label.yProperty().set((scene.root.heightProperty().doubleValue()/25) / 2 + 8);
            label.xProperty().set(-label.getBoundsInLocal().getWidth() * ((scene.root.heightProperty().doubleValue()/25) / label.getBoundsInLocal().getHeight()) / 2);
        } else {
            label.yProperty().set(-5);
            label.xProperty().set(-label.getBoundsInLocal().getWidth() * ((scene.root.heightProperty().doubleValue()/25) / label.getBoundsInLocal().getHeight()) / 2 - 10);
        }

        labelRotateGroup.getTransforms().addAll(
            xRotate = new Rotate(0, Rotate.X_AXIS),
            yRotate = new Rotate(0, Rotate.Y_AXIS),
            zRotate = new Rotate(0, Rotate.Z_AXIS)
        );
        scene.angleXProperty().addListener((observableValue, number, t1) -> {setAngle();});
        scene.angleYProperty().addListener((observableValue, number, t1) -> {setAngle();});
        scene.angleZProperty().addListener((observableValue, number, t1) -> {setAngle();});
        scene.portrait.addListener((observableValue, number, t1) -> {setAngle();});

        labelRotateGroup.getChildren().add(label);
        if (direction) {
            labelRotateGroup.translateXProperty().bind(xOffset.multiply(scene.scaleFactorProperty()).add(label.getBoundsInLocal().getWidth() / 2));
        } else {
            labelRotateGroup.translateXProperty().bind(xOffset.multiply(scene.scaleFactorProperty()).subtract(label.getBoundsInLocal().getWidth() / 2));
        }
        labelRotateGroup.translateYProperty().bind(Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(-0.5 * yOffset));
        labelRotateGroup.translateZProperty().bind(Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(-0.5 * yOffset));

        Node leftHorizontal = makeArrow(
            xOffset,
            length.divide(1),
                (DoubleBinding) Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(0.5 * yOffset).multiply(-1),
            2,
            color
        );

        Group leftVertical = makeLineVertical(
                xOffset,
                (DoubleBinding) Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(0.5 * yOffset).multiply(-1),
                1,
                Theme.getLabelFg()
        );

        Group rightVertical = makeLineVertical(
                xOffset.subtract(length),
                (DoubleBinding) Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(0.5 * yOffset).multiply(-1),
                1,
                Theme.getLabelFg()

        );

        leftHorizontal.getTransforms().add(xRotate);
        getChildren().addAll(labelRotateGroup, leftHorizontal, leftVertical, rightVertical);





        /*
        Node leftHorizontal = makeLineHorizontal(
                xOffset,
                length.divide(2).subtract(new SimpleDoubleProperty(label.getBoundsInLocal().getWidth()/2).divide(scene.scaleFactorProperty())),
                scene.heightProperty().multiply(0.5*yOffset).multiply(-1),
                2,
                color
        );

        Node rightHorizontal = makeLineHorizontal(
                xOffset.add(length.divide(2).add(new SimpleDoubleProperty(label.getBoundsInLocal().getWidth()/2).divide(scene.scaleFactorProperty()))),
                length.divide(2).subtract(new SimpleDoubleProperty(label.getBoundsInLocal().getWidth()/2).divide(scene.scaleFactorProperty())),
                scene.heightProperty().multiply(0.5*yOffset).multiply(-1),
                2,
                color


        rightHorizontal.getTransforms().add(xRotate);
        getChildren().addAll(labelRotateGroup,leftHorizontal,rightHorizontal,leftVertical,rightVertical);
        );
         */
    }    /**
     * Constructs a new RunwayLabel with the specified name, color, x-offset, y-offset, length, scene, and direction.
     *
     * @param color      The color of the label and line.
     * @param xOffset    The x-offset of the label.
     * @param yOffset    The y-offset of the label.
     * @param length     The length of the line.
     * @param scene      The RunwayScene to which the label belongs.
     * @param visibility the visibility
     */
    public RunwayLabel(Color color, DoubleBinding xOffset, double yOffset, DoubleBinding length, RunwayScene scene, BooleanBinding visibility) {
        this.scene = scene;
        this.visibleProperty().bind(visibility);
        /**
        visibleProperty().bind
                (Bindings.and(
                        Bindings.when(new SimpleBooleanProperty(direction))
                        .then(Bindings.greaterThanOrEqual(0, length))
                        .otherwise(Bindings.lessThanOrEqual(0, length)),
                        visibility));
         */

        Group labelRotateGroup = new Group();
        label = new Text();
        label.autosize();


        length.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                label.textProperty().bind(Bindings.when(Bindings.lessThanOrEqual(0, length)).then(Long.toString(Math.round(length.get()))).otherwise(Long.toString(Math.round(length.get()*-1))).concat(scene.appWindow.runway.unitsProperty()));
            }
        });
        label.textProperty().bind(Bindings.when(Bindings.lessThanOrEqual(0, length)).then(Long.toString(Math.round(length.get()))).otherwise(Long.toString(Math.round(length.get()*-1))).concat(scene.appWindow.runway.unitsProperty()));
        //label.setFill(Theme.getLabelFg());
        label.getStyleClass().clear();
        label.getStyleClass().add("runwaylabelfg");
        label.setFill(Theme.getLabelFg());
        //label.setFont(Theme.font);
        scene.root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                label.setFont(new Font(t1.doubleValue()/35));
                if (direction) {
                    label.yProperty().set((t1.doubleValue()/35) / 2 + 8);
                    label.xProperty().set(-label.getBoundsInLocal().getWidth() * ((t1.doubleValue()/35) / label.getBoundsInLocal().getHeight()) / 2);
                } else {
                    label.yProperty().set(-5);
                    label.xProperty().set(-label.getBoundsInLocal().getWidth() * ((t1.doubleValue()/35) / label.getBoundsInLocal().getHeight()) / 2 - 10);
                }

            }
        });
        label.setFont(new Font(scene.root.heightProperty().doubleValue()/35));
        if (direction) {
            label.yProperty().set((scene.root.heightProperty().doubleValue()/35) / 2 + 8);
            label.xProperty().set(-label.getBoundsInLocal().getWidth() * ((scene.root.heightProperty().doubleValue()/35) / label.getBoundsInLocal().getHeight()) / 2);
        } else {
            label.yProperty().set(-5);
            label.xProperty().set(-label.getBoundsInLocal().getWidth() * ((scene.root.heightProperty().doubleValue()/35) / label.getBoundsInLocal().getHeight()) / 2 - 10);
        }

        labelRotateGroup.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS),
                zRotate = new Rotate(0, Rotate.Z_AXIS)
        );
        scene.angleXProperty().addListener((observableValue, number, t1) -> {setAngle();});
        scene.angleYProperty().addListener((observableValue, number, t1) -> {setAngle();});
        scene.angleZProperty().addListener((observableValue, number, t1) -> {setAngle();});
        scene.portrait.addListener((observableValue, number, t1) -> {setAngle();});

        labelRotateGroup.getChildren().add(label);
        if (direction) {
            labelRotateGroup.translateXProperty().bind(xOffset.multiply(scene.scaleFactorProperty()).add(label.getBoundsInLocal().getWidth() / 2));
        } else {
            labelRotateGroup.translateXProperty().bind(xOffset.multiply(scene.scaleFactorProperty()).subtract(label.getBoundsInLocal().getWidth() / 2));
        }
        labelRotateGroup.translateYProperty().bind(Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(-0.5 * yOffset));
        labelRotateGroup.translateZProperty().bind(Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(-0.5 * yOffset));


        Node leftHorizontal = makeLineHorizontal(
            xOffset,
            length.divide(1),
                (DoubleBinding) Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(0.5 * yOffset).multiply(-1),
            2,
            color
        );



        Group leftVertical = makeLineVertical(
                xOffset,
                (DoubleBinding) Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(0.5 * yOffset).multiply(-1),
                1,
                Theme.getLabelFg()

        );

        Group rightVertical = makeLineVertical(
                xOffset.subtract(length),
                (DoubleBinding) Bindings.when(scene.portrait).then(scene.mainPane.widthProperty()).otherwise(scene.mainPane.heightProperty()).multiply(0.5 * yOffset).multiply(-1),
                1,
                Theme.getLabelFg()

        );

        leftHorizontal.getTransforms().add(xRotate);
        getChildren().addAll(labelRotateGroup, leftHorizontal, leftVertical, rightVertical);





        /*
        Node leftHorizontal = makeLineHorizontal(
                xOffset,
                length.divide(2).subtract(new SimpleDoubleProperty(label.getBoundsInLocal().getWidth()/2).divide(scene.scaleFactorProperty())),
                scene.heightProperty().multiply(0.5*yOffset).multiply(-1),
                2,
                color
        );

        Node rightHorizontal = makeLineHorizontal(
                xOffset.add(length.divide(2).add(new SimpleDoubleProperty(label.getBoundsInLocal().getWidth()/2).divide(scene.scaleFactorProperty()))),
                length.divide(2).subtract(new SimpleDoubleProperty(label.getBoundsInLocal().getWidth()/2).divide(scene.scaleFactorProperty())),
                scene.heightProperty().multiply(0.5*yOffset).multiply(-1),
                2,
                color


        rightHorizontal.getTransforms().add(xRotate);
        getChildren().addAll(labelRotateGroup,leftHorizontal,rightHorizontal,leftVertical,rightVertical);
        );
         */
    }

    /**
     * Set angle.
     */
    public void setAngle(){


        xRotate.angleProperty().set(-scene.getAngleXProperty()*(Math.cos(Math.toRadians(scene.getAngleZProperty()))));
        yRotate.angleProperty().set(scene.getAngleXProperty()*(Math.sin(Math.toRadians(scene.getAngleZProperty()))));
        zRotate.angleProperty().set(-scene.getAngleZProperty());
        if (scene.portrait.get()){
            zRotate.setAngle(zRotate.getAngle() + 90 - scene.angleYProperty().get());
            if (direction) {
                label.yProperty().set(-5);
            } else {
                label.yProperty().set(label.getBoundsInLocal().getHeight() / 2 + 8);
            }

        }
    }

    /**
     * Creates a horizontal line in a 3D space, represented by a Box object.
     *
     * @param start     The binding for the starting position of the line on the x-axis.
     * @param length    The binding for the length of the line on the x-axis.
     * @param height    The binding for the height of the line on the y and z-axis.
     * @param thickness The thickness of the line on the y and z-axis.
     * @param color     The color of the line.
     * @return A Box object representing the horizontal line.
     */
    public Node makeArrow(DoubleBinding start, DoubleBinding length, DoubleBinding height, double thickness, Color color){
        RunwayArrow arrow = new RunwayArrow(color,scene.scaleFactorProperty(), length,direction);
        arrow.translateXProperty().bind(start.subtract(length).multiply(scene.scaleFactorProperty()));

        //arrow.labelText= name;
        //arrow.buildArrowLabel();
        arrow.translateYProperty().bind(height);
        arrow.translateZProperty().bind(height);
        return arrow;
    }
    /**
     * Creates a horizontal line in a 3D space, represented by a Box object.
     *
     * @param start     The binding for the starting position of the line on the x-axis.
     * @param length    The binding for the length of the line on the x-axis.
     * @param height    The binding for the height of the line on the y and z-axis.
     * @param thickness The thickness of the line on the y and z-axis.
     * @param color     The color of the line.
     * @return A Box object representing the horizontal line.
     */
    public Node makeLineHorizontal(DoubleBinding start, DoubleBinding length, DoubleBinding height, double thickness, Color color){
        Line line = new Line();
        line.startXProperty().set(0);
        line.setStrokeWidth(3);
        line.endXProperty().bind(length.multiply(scene.scaleFactorProperty()));
        line.setFill(color);
        line.setStroke(color);

        line.translateXProperty().bind(start.subtract(length).multiply(scene.scaleFactorProperty()));

        //arrow.labelText= name;
        //arrow.buildArrowLabel();
        line.translateYProperty().bind(height);
        line.translateZProperty().bind(height);
        return line;
    }

    /**
     * Creates a vertical line in a 3D space, represented by a Group object containing a rotated Box.
     *
     * @param start     The binding for the starting position of the line on the x-axis.
     * @param height    The binding for the height of the line on the y-axis.
     * @param thickness The thickness of the line on the x and y-axis.
     * @param color     The color of the line.
     * @return A Group object containing a rotated Box representing the vertical line.
     */
    public Group makeLineVertical(DoubleBinding start, DoubleBinding height, double thickness, Color color){
        Group boxRotateGroup = new Group();
        PhongMaterial material = new PhongMaterial();

        Box box = new Box(thickness,thickness,100);
        box.translateXProperty().bind(start.multiply(scene.scaleFactorProperty()));
        box.translateZProperty().bind(box.depthProperty().divide(-2));
        scene.mainPane.heightProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                box.depthProperty().set(Math.sqrt(height.multiply(height).multiply(2).get()));
                if (height.get() > 0) {
                    box.translateZProperty().bind(box.depthProperty().divide(2));
                } else {
                    box.translateZProperty().bind(box.depthProperty().divide(-2));
                }
            }
        });scene.mainPane.widthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                box.depthProperty().set(Math.sqrt(height.multiply(height).multiply(2).get()));
                if (height.get() > 0) {
                    box.translateZProperty().bind(box.depthProperty().divide(2));
                } else {
                    box.translateZProperty().bind(box.depthProperty().divide(-2));
                }
            }
        });
        box.depthProperty().set(Math.sqrt(height.multiply(height).multiply(2).get()));
        if (height.get() > 0) {
            box.translateZProperty().bind(box.depthProperty().divide(2));
        } else {
            box.translateZProperty().bind(box.depthProperty().divide(-2));
        }
        material.setDiffuseColor(color);
        box.setMaterial(material);
        boxRotateGroup.getChildren().add(box);
        boxRotateGroup.getTransforms().add(new Rotate(-45,Rotate.X_AXIS));
        return boxRotateGroup;
    }



}

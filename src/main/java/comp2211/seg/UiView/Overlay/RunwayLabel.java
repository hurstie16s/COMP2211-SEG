package comp2211.seg.UiView.Overlay;

import comp2211.seg.UiView.Scene.RunwayScene;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class RunwayLabel extends Group{
    private final RunwayScene scene;
    private final boolean direction;
    private final String name;

    public RunwayLabel(String name, Color color, DoubleBinding xOffset, double yOffset, DoubleBinding length, RunwayScene scene, boolean direction){
        this.scene = scene;
        this.direction = direction;
        this.name = name;
        Group labelRotateGroup = new Group();
        Text label = new Text(name);
        label.setFill(color);
        label.setFont(Font.font("Calibri",18));
        label.xProperty().set(-label.getBoundsInLocal().getWidth()/2);
        //label.yProperty().set(label.getBoundsInLocal().getHeight()/4);
        label.yProperty().set(-5);


        Rotate xRotate;
        Rotate yRotate;
        Rotate zRotate;
        labelRotateGroup.getTransforms().addAll(
                xRotate = new Rotate(0,Rotate.X_AXIS),
                yRotate = new Rotate(0,Rotate.Y_AXIS),
                zRotate = new Rotate(0,Rotate.Z_AXIS)
        );

        xRotate.angleProperty().bind(scene.angleXProperty().multiply(-1));
        yRotate.angleProperty().bind(scene.angleZProperty().multiply(-1));
        zRotate.angleProperty().bind(scene.angleYProperty().multiply(-1));

        labelRotateGroup.getChildren().add(label);
        if (direction) {
            labelRotateGroup.translateXProperty().bind(xOffset.multiply(scene.scaleFactorProperty()).add(label.getBoundsInLocal().getWidth() / 2));
        }else {
            labelRotateGroup.translateXProperty().bind(xOffset.add(length).multiply(scene.scaleFactorProperty()).subtract(label.getBoundsInLocal().getWidth() / 2));

        }
        labelRotateGroup.translateYProperty().bind(scene.heightProperty().multiply(-0.5 * yOffset));
        labelRotateGroup.translateZProperty().bind(scene.heightProperty().multiply(-0.5 * yOffset));

        Node leftHorizontal = makeLineHorizontal(
                xOffset,
                length.divide(1),
                scene.heightProperty().multiply(0.5*yOffset).multiply(-1),
                2,
                color
        );


        Group leftVertical = makeLineVertical(
                xOffset,
                scene.heightProperty().multiply(0.5*yOffset).multiply(-1),
                1,
                color
        );

        Group rightVertical = makeLineVertical(
                xOffset.subtract(length),
                scene.heightProperty().multiply(0.5*yOffset).multiply(-1),
                1,
                color
        );

        leftHorizontal.getTransforms().add(xRotate);
        getChildren().addAll(labelRotateGroup,leftHorizontal,leftVertical,rightVertical);


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
     * Creates a horizontal line in a 3D space, represented by a Box object.
     *
     * @param start The binding for the starting position of the line on the x-axis.
     * @param length The binding for the length of the line on the x-axis.
     * @param height The binding for the height of the line on the y and z-axis.
     * @param thickness The thickness of the line on the y and z-axis.
     * @param color The color of the line.
     * @return A Box object representing the horizontal line.
     */
    public Node makeLineHorizontal(DoubleBinding start, DoubleBinding length, DoubleBinding height, double thickness, Color color){

        RunwayArrow arrow = new RunwayArrow(color,scene.scaleFactorProperty(), length,direction);
        //arrow.labelText= name;
        //arrow.buildArrowLabel();
        arrow.translateXProperty().bind(start.subtract(length).multiply(scene.scaleFactorProperty()));
        arrow.translateYProperty().bind(height);
        arrow.translateZProperty().bind(height);
        return arrow;
    }

    /**
     * Creates a vertical line in a 3D space, represented by a Group object containing a rotated Box.
     *
     * @param start The binding for the starting position of the line on the x-axis.
     * @param height The binding for the height of the line on the y-axis.
     * @param thickness The thickness of the line on the x and y-axis.
     * @param color The color of the line.
     * @return A Group object containing a rotated Box representing the vertical line.
     */
    public Group makeLineVertical(DoubleBinding start, DoubleBinding height, double thickness, Color color){
        Group boxRotateGroup = new Group();
        PhongMaterial material = new PhongMaterial();

        Box box = new Box(thickness,thickness,100);
        box.translateXProperty().bind(start.multiply(scene.scaleFactorProperty()));
        box.translateZProperty().bind(box.depthProperty().divide(-2));
        if (height.get() > 0) {
            box.depthProperty().set(Math.sqrt(Math.pow(height.get(), 2) * 2));
            box.translateZProperty().bind(box.depthProperty().divide(2));
        } else {
            box.depthProperty().set(Math.sqrt(Math.pow(height.get(), 2) * 2));
            box.translateZProperty().bind(box.depthProperty().divide(-2));
        }
        material.setDiffuseColor(color);
        box.setMaterial(material);
        boxRotateGroup.getChildren().add(box);
        boxRotateGroup.getTransforms().add(new Rotate(-45,Rotate.X_AXIS));
        return boxRotateGroup;
    }



}

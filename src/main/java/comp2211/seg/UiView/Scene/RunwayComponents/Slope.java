package comp2211.seg.UiView.Scene.RunwayComponents;

import comp2211.seg.ProcessDataModel.Obstacle;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 * A custom JavaFX MeshView representing a triangular prism object representing
 * a slope obstacle in a runway scene.
 * The class inherits from MeshView and adds properties
 * and methods specific to Slope objects.
 */
public class Slope extends MeshView {

    /** The direction of the ramp. */
    private final SimpleBooleanProperty direction;

    /** The distance from the centre of the runway (to the left side) of the ramp. */
    private final SimpleFloatProperty front = new SimpleFloatProperty();

    /** The distance from the centre of the runway (to the right side) of the ramp. */
    private final SimpleFloatProperty back = new SimpleFloatProperty();

    /** The distance of the bottom of the ramp from the ground. */
    private final SimpleFloatProperty bottom = new SimpleFloatProperty();

    /** The distance of the top of the ramp from the ground. */
    private final SimpleFloatProperty top = new SimpleFloatProperty();

    /** The scaling factor of the ramp. */
    private final SimpleDoubleProperty scaleFactor;

    /** The obstacle causing the ramp. */
    private final Obstacle obstacle;

    /**
     * Adds a triangular prism object to the runway scene.
     *
     * @param obstacle  the obstacle causing the issue
     * @param y         the height above the runway
     * @param w         the width of the runway
     * @param h         the height of the object
     * @param color     the colour of the object
     * @param direction the direction the ramp is facing
     */
    public Slope(Obstacle obstacle, DoubleBinding y, DoubleBinding z, DoubleBinding w, DoubleBinding h, Color color, SimpleBooleanProperty direction, SimpleDoubleProperty scaleFactor, SimpleDoubleProperty scaleFactorHeight, SimpleDoubleProperty scaleFactorDepth){
        this.scaleFactor = scaleFactor;
        this.obstacle = obstacle;
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        setMaterial(material);
        this.direction = direction;

        DoubleBinding x = obstacle.distFromThresholdProperty().multiply(1).subtract(obstacle.widthProperty().divide(2));
        SimpleFloatProperty end = new SimpleFloatProperty();
        SimpleFloatProperty start = new SimpleFloatProperty();
        front.bind(y.multiply(scaleFactorHeight).add(w.multiply(scaleFactorHeight).divide(2)));
        back.bind(y.multiply(scaleFactorHeight).subtract(w.multiply(scaleFactorHeight).divide(2)));
        start.bind(x.multiply(scaleFactor));
        end.bind(x.multiply(scaleFactor).subtract(h.multiply(scaleFactor).multiply(49)));
        bottom.bind(z.multiply(scaleFactorDepth).multiply(-1));
        top.bind(h.add(z).multiply(scaleFactorDepth).multiply(-1));
        for (Property prop: new Property[] {
                front,
                back,
                end,
                start,
                bottom,
                top,
                direction,
                scaleFactor
        }) {
            prop.addListener((observableValue, o, t1) -> redraw());
        }
        redraw();
    }

    /**
     * Creates a prism MeshView object with the specified coordinates, faces, and color.
     *
     * @param coords the coordinates of the vertices of the prism
     * @param faces  the indices of the vertices used to construct each face of the prism
     */
    public void makePrism(float [] coords, int[] faces){
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(coords);
        mesh.getFaces().addAll(faces);
        mesh.getTexCoords().addAll(1,1);
        setMesh(mesh);
    }

    /**
     * Redraws the slope by calculating the start and end points
     * of the ramp and updating the mesh with the new vertices.
     * The method uses the current values of the properties
     * to compute the start and end points, and then creates a new
     * triangular prism MeshView object with the updated vertices and faces.
     */
    public void redraw(){
        float tempStart;
        float tempEnd;
        if (direction.get()) {
            tempStart = (float) (obstacle.distFromThresholdProperty().get() - (obstacle.widthProperty().divide(2)).get());
            tempEnd = (float) (tempStart - obstacle.getHeight()*49);
        } else {
            tempStart = (float) (obstacle.distFromThresholdProperty().get() + (obstacle.widthProperty().divide(2)).get());
            tempEnd = (float) (tempStart + obstacle.getHeight()*49);
        }

        float difference = tempStart-tempEnd;
        if (-240 < difference && difference < 240) {
            if (tempStart < tempEnd){
                tempEnd = tempStart + 240;
            }else{
                tempEnd = tempStart - 240;
            }
        }
        tempStart = (float) (tempStart * scaleFactor.get());
        tempEnd = (float) (tempEnd * scaleFactor.get());
        makePrism(new float[]{
                tempStart, front.get(), bottom.get(),
                tempEnd, front.get(), bottom.get(),
                tempStart, front.get(), top.get(),
                tempStart, back.get(), bottom.get(),
                tempEnd, back.get(), bottom.get(),
                tempStart, back.get(), top.get()
        }, new int[]{
                0, 0, 2, 0, 1, 0,
                3, 0, 4, 0, 5, 0,
                0, 0, 4, 0, 3, 0,
                0, 0, 1, 0, 4, 0,
                0, 0, 3, 0, 5, 0,
                0, 0, 5, 0, 2, 0,
                1, 0, 5, 0, 4, 0,
                1, 0, 2, 0, 5, 0,
        });
    }
}

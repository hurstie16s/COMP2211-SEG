package comp2211.seg.UiView.Scene.RunwayComponents;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.ProcessDataModel.Obstacle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A custom JavaFX MeshView representing a triangular prism object representing
 * a slope obstacle in a runway scene.
 * The class inherits from MeshView and adds properties
 * and methods specific to Slope objects.
 */
public class Slope extends MeshView {

    private static final Logger logger = LogManager.getLogger(Slope.class);
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
    private final SimpleFloatProperty start = new SimpleFloatProperty();
    private final SimpleFloatProperty end = new SimpleFloatProperty();

    /** The scaling factor of the ramp. */
    private final SimpleDoubleProperty scaleFactor;

    /** The obstacle causing the ramp. */
    private final Obstacle obstacle;
    private final AppWindow appWindow;

    /**
     * Adds a triangular prism object to the runway scene.
     *
     * @param appWindow         the application window
     * @param x                 the x
     * @param y                 the height above the runway
     * @param z                 the z
     * @param w                 the width of the runway
     * @param h                 the height of the object
     * @param color             the colour of the object
     * @param direction         the direction the ramp is facing
     * @param scaleFactor       the scale factor
     * @param scaleFactorHeight the scale factor height
     * @param scaleFactorDepth  the scale factor depth
     */
    public Slope(AppWindow appWindow, DoubleBinding x, DoubleBinding y, DoubleBinding z, DoubleBinding w, DoubleBinding h, Color color, SimpleBooleanProperty direction, SimpleDoubleProperty scaleFactor, SimpleDoubleProperty scaleFactorHeight, SimpleDoubleProperty scaleFactorDepth){
        this.scaleFactor = scaleFactor;
        this.obstacle = appWindow.runway.getRunwayObstacle();
        this.appWindow = appWindow;
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        setMaterial(material);
        this.direction = direction;
        this.visibleProperty().bind(appWindow.runway.hasRunwayObstacleProperty());

        front.bind(y.multiply(scaleFactorHeight).add(w.multiply(scaleFactorHeight).divide(2)));
        back.bind(y.multiply(scaleFactorHeight).subtract(w.multiply(scaleFactorHeight).divide(2)));
        start.bind((Bindings.when(direction).then(x.add(obstacle.distFromThresholdProperty()).subtract(obstacle.lengthProperty().divide(2))).otherwise(x.add(obstacle.distFromThresholdProperty()).add(obstacle.lengthProperty().divide(2))).multiply(scaleFactor)));
        NumberBinding difference = Bindings.when(direction).then(obstacle.heightProperty().multiply(-50).add(obstacle.lengthProperty().divide(2))).otherwise(obstacle.heightProperty().multiply(50).subtract(obstacle.lengthProperty().divide(2)));
        NumberBinding change = start.add(Bindings.when(direction.not()).then(Bindings.when(Bindings.lessThan(240, difference)).then(difference).otherwise(240)).otherwise(Bindings.when(Bindings.greaterThan(-240, difference)).then(difference).otherwise(-240)).multiply(scaleFactor));
        end.bind(Bindings.when(Bindings.lessThan(appWindow.runway.runwayLengthProperty().divide(2).multiply(scaleFactor),change)).then(start).otherwise(Bindings.when(Bindings.greaterThan(appWindow.runway.runwayLengthProperty().divide(-2).multiply(scaleFactor),change)).then(start).otherwise(change)));
        difference.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                logger.info(t1);
            }
        });
        bottom.bind(z.multiply(scaleFactorDepth).multiply(-1).subtract(0.1));
        top.bind(h.add(z).multiply(scaleFactorDepth).multiply(-1).subtract(0.1));
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
        int[] faceSmoothingGroups = {
                0, 0, 0, 0, 0, 0, 0, 0
        };
        mesh.getFaceSmoothingGroups().addAll(faceSmoothingGroups);
        mesh.getFaces().addAll(faces);
        mesh.getTexCoords().setAll(1,1,
                1,1,
                1,1,
                1,1);
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
        if (!direction.get()) {
            makePrism(new float[]{
                    start.get(), front.get(), bottom.get(),
                    end.get(), front.get(), bottom.get(),
                    start.get(), front.get(), top.get(),
                    start.get(), back.get(), bottom.get(),
                    end.get(), back.get(), bottom.get(),
                    start.get(), back.get(), top.get()
            }, new int[]{
                    2, 0, 0, 0, 1, 0,
                    4, 0, 3, 0, 5, 0,
                    4, 0, 0, 0, 3, 0,
                    1, 0, 0, 0, 4, 0,
                    3, 0, 0, 0, 5, 0,
                    5, 0, 0, 0, 2, 0,
                    5, 0, 1, 0, 4, 0,
                    2, 0, 1, 0, 5, 0,
            });
        }else {
            makePrism(new float[]{
                    start.get(), front.get(), bottom.get(),
                    end.get(), front.get(), bottom.get(),
                    start.get(), front.get(), top.get(),
                    start.get(), back.get(), bottom.get(),
                    end.get(), back.get(), bottom.get(),
                    start.get(), back.get(), top.get()
            }, new int[]{
                    2, 0, 1, 0, 0, 0,
                    4, 0, 5, 0, 3, 0,
                    4, 0, 3, 0, 0, 0,
                    1, 0, 4, 0, 0, 0,
                    3, 0, 5, 0, 0, 0,
                    5, 0, 2, 0, 0, 0,
                    5, 0, 4, 0, 1, 0,
                    2, 0, 5, 0, 1, 0,
            });
        }
    }
}

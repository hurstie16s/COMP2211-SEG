package comp2211.seg.Controller.Stage;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class represents a custom JavaFX StackPane that provides scaling and positioning functionality. It includes a logger,
 * width and height values for the pane, a scalar value for scaling, and a boolean for auto-scaling. The HandlerPane class
 * overrides the layoutChildren method to calculate the scale factor, scale the pane, and translate it into position. It also
 * provides methods to get the parent width and height.
 */
public class HandlerPane extends StackPane {

    private static final Logger logger = LogManager.getLogger(HandlerPane.class);
    private final double width;
    private final double height;
    private double scalar = 1;
    private final boolean autoScale = true;

    /**
     * Constructor for the HandlerPane class. It sets the maximum height and width of the pane, as well as its alignment.
     *
     * @param width  The maximum width of the pane.
     * @param height The maximum height of the pane.
     */
    public HandlerPane(double width, double height) {
        super();
        this.width = width;
        this.height = height;
        setMaxHeight(height);
        setMaxWidth(width);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Sets the scalar value of the pane, which determines how much the pane is scaled.
     *
     * @param scalar The new scalar value.
     */
    protected void setScalar(double scalar) {
        this.scalar = scalar;
    }

    /**
     * Overrides the layoutChildren method of the StackPane class. It calculates the scale factor of the pane, sets the scalar
     * value, sets up the scale, calculates the padding needed for positioning, and applies the transformation.
     */
    @Override
    public void layoutChildren() {
        super.layoutChildren();

        if (!autoScale) {
            return;
        }

        // Work out the scale factor height and width
        var scaleFactorHeight = getHeight() / height;
        var scaleFactorWidth = getWidth() / width;

        // Work out whether to scale by width or height
        if (scaleFactorHeight > scaleFactorWidth) {
            setScalar(scaleFactorWidth);
        } else {
            setScalar(scaleFactorHeight);
        }

        // Set up the scale
        Scale scale = new Scale(scalar, scalar);

        // Get the parent width and height
        var parentWidth = getWidth();
        var parentHeight = getHeight();

        // Get the padding needed on the top and left
        var paddingLeft = (parentWidth - (width * scalar)) / 2.0;
        var paddingTop = (parentHeight - (height * scalar)) / 2.0;

        // Perform the transformation
        Translate translate = new Translate(paddingLeft, paddingTop);
        scale.setPivotX(0);
        scale.setPivotY(0);
        getTransforms().setAll(translate, scale);
    }

    /**
     * Returns the maximum width of the pane.
     *
     * @return The maximum width of the pane.
     */
    public double getParentWidth(){
        return width;
    }

    /**
     * Returns the maximum height of the pane.
     *
     * @return The maximum height of the pane.
     */
    public double getParentHeight(){
        return height;
    }
}

package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;

public class subsceneAbstract extends SceneAbstract{
    private final DoubleBinding widthProperty;
    private final DoubleBinding heightProperty;

    /**
     * Constructor to create a SceneAbstract object.
     *
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     * @param width
     * @param height
     */
    public subsceneAbstract(Pane root, AppWindow appWindow, DoubleBinding width, DoubleBinding height) {
        super(root, appWindow, width.get(), height.get());
        widthProperty = width;
        heightProperty = height;
    }

    @Override
    public void initialise() {

    }
    public void build(){
        super.build();
        root.maxWidthProperty().bind(widthProperty);
        root.minWidthProperty().bind(widthProperty);
        root.maxHeightProperty().bind(widthProperty);
        root.minHeightProperty().bind(widthProperty);
        root.setBackground(new Background(new BackgroundFill(GlobalVariables.focusedBG,null,null)));
    }

}

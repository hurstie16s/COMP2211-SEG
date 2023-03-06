package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelpScene extends SceneAbstract{

    private static final Logger logger = LogManager.getLogger(SceneAbstract.class);

    /**
     * Constructor to create a SceneAbstract object.
     *
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     */
    public HelpScene(HandlerPane root, AppWindow appWindow) {
        super(root, appWindow);
    }

    @Override
    public void initialise() {

    }
    public void toggleHelp(String className){
        logger.info(className);
    }
}

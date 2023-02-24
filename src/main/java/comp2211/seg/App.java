package comp2211.seg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
    private static App instance;
    private Stage stage;
    private static final Logger logger = LogManager.getLogger(App.class);
    public static void main(String[] args) {
        logger.info("Starting");
        launch();
    }

    @Override
    public void start(Stage stage) {

        logger.info("say hi");
        instance = this;
        this.stage = stage;
        //Do stuff
    }
}
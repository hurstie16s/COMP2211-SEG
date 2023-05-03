package comp2211.seg.ProcessDataModel;
import comp2211.seg.Controller.Stage.AppWindow;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import comp2211.seg.UiView.Scene.SceneAbstract;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTestIgnore {

    @Test
    void exportAirportAndOb() {
    }

    @Test
    void exportAirport() {
    }

    @Test
    void exportObstacle() {
    }

    @DisplayName("Airbus A320-200 Import Test")
    @Test
    void importAirbusA320ObstacleTest() {
        var obstacleTest = new Obstacle("Airbus A320-200", 11.76, 0, 37.57, 35.8);
        File testFile = new File("src/test/resources/AirbusA320-200.xml");
        Obstacle importedObstacle = null;
        try {
            importedObstacle = FileHandler.importObstacle(testFile);
        } catch (Exception ignored) {}
        assertEquals(obstacleTest, importedObstacle, "Obstacle Import not correct");
    }

    @DisplayName("Boeing 737-800 Import Test")
    @Test
    void importBoeing737ObstacleTest() {
        var obstacleTest = new Obstacle("Boeing 737-800", 12.6, 0, 34.32, 39.5);
        File testFile = new File("src/test/resources/Boeing737-800.xml");
        Obstacle importedObstacle = null;
        try {
            importedObstacle = FileHandler.importObstacle(testFile);
        } catch (Exception ignored) {}
        assertEquals(obstacleTest, importedObstacle, "Obstacle Import not correct");
    }

    @Test
    void importAirport() {
    }

    @Test
    public void testExportImage(@TempDir Path tempDir) throws IOException {
        // Create an instance of the SceneAbstract class
        Platform.startup(() -> {});

        Platform.runLater(() -> {
            Pane root = new Pane();
            Stage stage = new Stage();
            SimpleDoubleProperty widthP = new SimpleDoubleProperty(1280);
            SimpleDoubleProperty heightP = new SimpleDoubleProperty(720);
            AppWindow appWindow = new AppWindow(stage, 1280, 720);
            SceneAbstract scene = new SceneAbstract(root, appWindow, widthP, heightP) {
                @Override
                public void initialise() {
                    // Implementation of to initialise() method
                }
            };
            // Create a test image to export
            int width = 100;
            int height = 100;
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);

            // Set the export format and file path
            String format = "png";
            Path filePath = tempDir.resolve("test." + format);

            // Export the image
            scene.exportImage(format, image);

            // Check if the exported file exists
            assertTrue(Files.exists(filePath), "Exported file does not exist");

            // Check if the exported file has the correct format
            String fileType = null;
            try {
                fileType = Files.probeContentType(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            assertNotNull(fileType, "Exported file type is null");
            assertEquals("image/png", fileType, "Exported file type is not correct");

            // Check if the exported file has the correct dimensions
            BufferedImage exportedImage = null;
            try {
                exportedImage = ImageIO.read(filePath.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            assertEquals(width, exportedImage.getWidth(), "Exported image width is incorrect");
            assertEquals(height, exportedImage.getHeight(), "Exported image height is incorrect");
        });
    }

}
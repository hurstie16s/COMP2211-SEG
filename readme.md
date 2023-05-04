## RunwayTool

RunwayTool is an application that allows you to modify the parameters of a runway and add/remove objects to the scene.

### Authors
- Josh Douglas (jod1n21) 
- Lam Giang (lg1n20) 
- Samuel Hurst (shjh1g21)
- David Kuc (drk1g21)
- Aleksander Pilski (ajp1e19)
- Josh Willson (jjrw1g21)



### Running the Application

To run the application, you will need to have Java 17 or later installed on your computer. If you do not have Java 17 or later installed, you can download it from the [official Java website](https://www.oracle.com/java/technologies/downloads/).

If you are using the provided "fat" jar file to run the application, please note that it will load all required dependencies. However, if you are using the source code in an IDE, it is recommended to use Maven to manage dependencies. Please check the dependencies section in this README.md file to ensure compatibility.

To run the application:

 - Double-click on the provided `runwaytool.jar` file.

Alternatively, you can run the JAR file from the command line. Follow these steps:
1. Open the command line.
2. Navigate to the directory where you saved `runwaytool.jar`.
3. Type `java -jar runwaytool.jar` and press Enter.

The "fat" jar provided will load all the required dependencies. However, if the source code is used in an IDE, it is recommended to use Maven. Please check the dependencies section to ensure compatibility.

### Dependencies

The RunwayTool application uses the following dependencies:

- Java SE Development Kit 17 or later
- JavaFX
- Log4j
- Java XML
- CSS Parser
- SAC

### Usage

Upon starting the application, you have access to the main scene. On this screen, you can modify the runway parameters and add/remove objects. If the result shows a valid runway, the resulting runway will be displayed at the bottom of the screen.

Should you wish to change the values for the runway, simply click on the value you wish to change and type in a new one. If you enter a non-numeric value, the program will flag that issue and prevent you from continuing. Should the values you enter not form a valid runway, an error will show up at the bottom listing all problems.

If you want to enlarge the runways you can expand the view, or you can click on either of the runways at the bottom. This will open up a large view of just the runway in question, and you can press T to toggle top/side view.

On all scenes, you have access to a help menu which can be toggled with the H key. If you only want the values, those are available on the top half of the main screen, and are automatically updated.

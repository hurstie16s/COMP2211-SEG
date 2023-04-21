package comp2211.seg.UiView.Scene.SceneComponents;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TabsPaneVertical extends VBox {
    private Pane pane;
    public TabsPaneVertical(){
        super();
    }
    public void remove(Node c){
        pane.getChildren().remove(c);
        for (Node child: pane.getChildren()) {
            if (child instanceof Divider){
                if (pane.getChildren().get(pane.getChildren().indexOf(child)+1) instanceof Divider){
                    pane.getChildren().remove(child);
                }
            }
        }
        double totalHeight = 0;
        double maxHeight = pane.heightProperty().get() - ((pane.getChildren().size()-1) / 2.0 * 5);
        for (Node child: pane.getChildren()) {
            if (child instanceof TabLayout){
                totalHeight += ((TabLayout) child).getHeight();
            }
        }
        for (Node child: pane.getChildren()) {
            System.out.println(child.getClass().getName());
            if (child instanceof TabLayout){
                ((TabLayout) child).setMaxHeight(((TabLayout) child).heightProperty().get() * maxHeight / totalHeight );
                ((TabLayout) child).setMinHeight(((TabLayout) child).heightProperty().get() * maxHeight / totalHeight );
            }
        }
    }
}

package comp2211.seg.UiView.Scene;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class RunwayArrow extends Line {

    private double startX;
    private double endX;
    private String text;
    private double arrowWidth = 5;
    private double arrowHeight = 5;
    private Color arrowCol = Color.BEIGE;


    public RunwayArrow(double startX, double endX, String text) {
        this.startX = startX;
        this.endX = endX;
        this.text = text;

        Line arrowLine = new Line(startX, arrowHeight, endX, arrowHeight);
        arrowLine.setStrokeWidth(arrowWidth);
        arrowLine.setStroke(arrowCol);

        Polygon arrowHead = new Polygon();
        arrowHead.getPoints().addAll(new Double[]{
           endX, (arrowHeight - 2),
           endX + 2, arrowHeight,
           endX, (arrowHeight + 2)
        });



    }

}

/*
 * Programmer: Dan Hopp
 * Date: 25-APR-2020
 * Description: This class creates an explosion sprite out of a polygon based on
    the object's body size. Player explosions and npc explosions have differentiating
    colors.
 */
package playingfield;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Explosion {
    
    Polygon polygon;
    
    //default
    Explosion(){
        
    }
    
    //Class to auto-create a polygon "explosion" based on the dimensions of
    //Tank object's body. Polygon should have 18 points
    Explosion(Tank tank, Double x, Double y){
        
        polygon = new Polygon();
        ObservableList<Double> pointList = polygon.getPoints();
        
        //variables for common ratios
        double width = tank.body.getWidth();
        double height = tank.body.getHeight();
        double widthOneEighth = (width * 0.125);
        double widthThreeFourths = (width * 0.75);
        double widthOneHalf = (width * 0.5);
        double heightOneHalf = (height * 0.5);
        

        //First point
        pointList.add(x);
        pointList.add(y);
        //2nd
        pointList.add(x + widthOneEighth);
        pointList.add(y - widthThreeFourths);
        //3rd
        pointList.add(x + widthOneHalf);
        pointList.add(y);
        //4
        pointList.add(x + widthThreeFourths);
        pointList.add(y - widthThreeFourths);
        //5
        pointList.add(x + width);
        pointList.add(y);
        //6
        pointList.add(x + width + widthThreeFourths);
        pointList.add(y - widthThreeFourths);
        //7
        pointList.add(x + width);
        pointList.add(y + (height * 0.125));
        //8
        pointList.add(x + width + widthThreeFourths);
        pointList.add(y + heightOneHalf);
        //9
        pointList.add(x + width);
        pointList.add(y + (height * 0.75));
        //10
        pointList.add(x + width + widthOneHalf);
        pointList.add(y + height + (height * 0.25));
        //11
        pointList.add(x + widthThreeFourths);
        pointList.add(y + height);
        //12
        pointList.add(x + widthOneHalf);
        pointList.add(y + height + widthThreeFourths);
        //13
        pointList.add(x + widthOneEighth);
        pointList.add(y + height);
        //14
        pointList.add(x - (width * 0.25));
        pointList.add(y + height + widthOneHalf);
        //15
        pointList.add(x);
        pointList.add(y + height);
        //16
        pointList.add(x - widthOneHalf);
        pointList.add(y + (height * 0.75));
        //17
        pointList.add(x);
        pointList.add(y + heightOneHalf);
        //18
        pointList.add(x - widthThreeFourths);
        pointList.add(y + (height * 0.125));
        
        polygon.setStrokeWidth(widthOneEighth);
        
        //if player = 0, color yellow center with red line
        if (tank.player == 0){
            polygon.setFill(Color.YELLOW);
            polygon.setStroke(Color.RED);
        }
        //else color orange center with yellow line
        else {
            polygon.setFill(Color.RED);
            polygon.setStroke(Color.YELLOW);
        }
    }
}

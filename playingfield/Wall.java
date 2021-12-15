/*
 * Programmer: Dan Hopp
 * Date: 25-APR-2020
 * Description: Class to greate a wall rectangle. The borders have a different 
color so the player can better distinguish the wall sections.
 */
package playingfield;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall {
    
    Rectangle rectangle;
    
    //Default
    public Wall(){
        
    }
    
    public Wall(Double startx, Double startY, Double width, Double height){
        
        rectangle = new Rectangle(startx, startY, width, height);
        rectangle.setFill(Color.RED);
        rectangle.setStroke(Color.FIREBRICK);
        
    }
    
    //Getter
    public Rectangle getWallRectangle(){
        return rectangle;
    }
}

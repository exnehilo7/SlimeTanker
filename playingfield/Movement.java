/*
 * Programmer: Dan Hopp
 * Date: 25-APR-2020
 * Description: This class handles the movement of the player's sprite. Uses
A S W D for movement, with optional arrow keys in case the player prefers them.
Since the gun on the tank is a seperate rectangle, there are also functions to 
correctly align it and move with the tank.
 */
package playingfield;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;


public class Movement {
    
    /*Tank movement. If movment were to go beyond the screen borders, don't
continue in that diraction. Adjust gun's position so it stays with the tank.*/
    public static double moveTank(KeyCode code, Rectangle body, Rectangle gun, 
            Double speed, Double currentDirectionOfTank){
        
        //clear explosions from pane
        SlimeTanker.removeExplosionsFromPane();
        
        switch (code) {
        case W: 
            if (body.getY() > 0){
                currentDirectionOfTank = tankMovementUp(body, gun, speed);
            }
            break;
        case S:
            if (body.getY() < 730){
                currentDirectionOfTank = tankMovementDown(body, gun, speed);
            }
            break;
        case A:
            if (body.getX() > 0){
                currentDirectionOfTank = tankMovementLeft(body, gun, speed);
            }
            break;
        case D:
            if (body.getX() < 1140){
                currentDirectionOfTank = tankMovementRight(body, gun, speed);
            }
            break;
        //optional arrow keys    
        case UP:
            if (body.getY() > 0){
                currentDirectionOfTank = tankMovementUp(body, gun, speed);
            }
            break;
        case DOWN:
            if (body.getY() < 730){
                currentDirectionOfTank = tankMovementDown(body, gun, speed);
            }
            break;
        case LEFT:
            if (body.getX() > 0){
                currentDirectionOfTank = tankMovementLeft(body, gun, speed);
            }
            break;
        case RIGHT:
            if (body.getX() < 1140){
                currentDirectionOfTank = tankMovementRight(body, gun, speed);
            }
        }
            
        return currentDirectionOfTank;
    }
    
    
    //Move player tank and gun up
    private static double tankMovementUp(Rectangle body, Rectangle gun, 
            Double speed){

        //Alter -Y
        double currentDirectionOfTank = rotateBodyUp(body);
        body.setY(body.getY() - speed);  //movement speed
        
        //gun
        rotateGunUp(currentDirectionOfTank, body, gun);
        
        return currentDirectionOfTank;
        
    }
    
    //Move player tank and gun down
    private static double tankMovementDown(Rectangle body, Rectangle gun, 
            Double speed){

        //Alter +Y
        double currentDirectionOfTank = rotateBodyDown(body);
        body.setY(body.getY() + speed); //movement speed
        
        //gun
        rotateGunDown(currentDirectionOfTank, body, gun);
        
        return currentDirectionOfTank;
        
    }    
    
    //Move player tank and gun left
    private static double tankMovementLeft(Rectangle body, Rectangle gun, 
            Double speed){
       
        //Alter -X
        double currentDirectionOfTank = rotateBodyLeft(body);
        body.setX(body.getX() - speed);
        
        //gun
        rotateGunLeft(currentDirectionOfTank, body, gun);
        
        return currentDirectionOfTank;
    }
    
    //Move player tank and gun right
    private static double tankMovementRight(Rectangle body, Rectangle gun, 
            Double speed){

        //Alter +X
        double currentDirectionOfTank = rotateBodyRight(body);
        body.setX(body.getX() + speed);
        
        //gun
        rotateGunRight(currentDirectionOfTank, body, gun);
        
        return currentDirectionOfTank;
    }
    
    //Functions to rotate the body and assign and return the degrees of direction facing
    public static Double rotateBodyUp(Rectangle body){
        
        double currentDirectionOfTank = 0;
        body.setRotate(currentDirectionOfTank);
        return currentDirectionOfTank;
    }
    public static Double rotateBodyDown(Rectangle body){
        
        double currentDirectionOfTank = 180;
        body.setRotate(currentDirectionOfTank);
        return currentDirectionOfTank;
    }
    public static Double rotateBodyLeft(Rectangle body){
        
        double currentDirectionOfTank = 270;
        body.setRotate(currentDirectionOfTank);
        return currentDirectionOfTank;
    }    
    public static Double rotateBodyRight(Rectangle body){
        
        double currentDirectionOfTank = 90;
        body.setRotate(currentDirectionOfTank);
        return currentDirectionOfTank;
    }
        
    //Functions to rotate the gun
    public static void rotateGunUp(Double currDirOfTank, Rectangle body,
            Rectangle gun){
        
        gun.setRotate(currDirOfTank);
        gun.setX(body.getX() + ((body.getWidth() / 2) - (gun.getWidth() / 2)));
        gun.setY(body.getY() - (body.getHeight() / 1.5));

    }
    public static void rotateGunDown(Double currDirOfTank, Rectangle body,
            Rectangle gun){
        
        gun.setRotate(currDirOfTank);
        gun.setX(body.getX() + ((body.getWidth() / 2) - (gun.getWidth() / 2)));
        gun.setY(body.getY() + (body.getHeight() / 2));

    }
    public static void rotateGunLeft(Double currDirOfTank, Rectangle body,
            Rectangle gun){
        
        gun.setRotate(currDirOfTank);
        gun.setY(body.getY() + ((body.getHeight() / 2) - (gun.getHeight() / 2)));
        gun.setX(body.getX() - (gun.getHeight() / 2) + gun.getWidth());

    }
    public static void rotateGunRight(Double currDirOfTank, Rectangle body,
            Rectangle gun){
        
        gun.setRotate(currDirOfTank);
        gun.setY(body.getY() + ((body.getHeight() / 2) - (gun.getHeight() / 2)));
        gun.setX(body.getX() + (gun.getHeight() / 2) + gun.getWidth());

    }
}

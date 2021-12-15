/*
 * Programmer: Dan Hopp
 * Date: 25-APR-2020
 * Description: This class is for the player and npc objects. Since an npc is
essentially a tank, their gun rectangles are set to transparent.

Within the tank object are the various paths that the Levels class can assign to
the object.

This also contains a subclass for the shell that is fired from the tank, as well
its pathing.
 */
package playingfield;

import javafx.animation.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import static playingfield.SlimeTanker.*;


public class Tank {
    
    int player; //0 < NPC < 999     Player1 = 0 
    
    Rectangle body;
    Rectangle gun;
    
    double centerOfTankX;
    double centerOfTankY;
    double startingX;
    double startingY;
    double currentDirectionOfTank;
    double tankSpeed;
    double shellSpeed; //in ms
    PathTransition ptTank;
    int health;
    int ammo;
    int gear;
    int pathPattern;
    
    Shell shell;
    
    //Default
    public Tank(){
        body = new Rectangle(50, 100);
    }
    
    /*Create a tank. Specify its dimentions, coloring, which player, its speed 
    and starting position */
    public Tank(Double width, Double length, Color fillColorBody, 
            Color strokeColorBody, Color fillColorGun, Color strokeColorGun,
            Integer player, Double tankSpeed, Double shellSpeed, Integer playerLives,
            Integer ammo, Double startingX, Double startingY, Integer pathPattern,
            Pane pane){
        
        //set player
        this.player = player;
        this.pathPattern = pathPattern;
        
        health = playerLives;
        this.ammo = ammo;
        
        //create body
        body = createRectangle(width, length);
        //Add gun to tank
        gun = createRectangle((width / 3.33), length + (body.getHeight()* 0.25));
        
        //set tank speed
        this.tankSpeed = tankSpeed;
        gear = 1;
        
        //set its shell velocity
        this.shellSpeed = shellSpeed;
        
        //set colors
        setColors(fillColorBody, strokeColorBody, fillColorGun, strokeColorGun);
        
        //set Start Position and place the gun on the body
        setStartingPosition(startingX, startingY);
        
        //Add body and gun to pane
        pane.getChildren().addAll(body, gun);
        
        //if the tank is not an NPC, assign action and focus
        if (player == 0){
            setEvents(pane, fillColorGun);
            body.requestFocus(); 
           
            //listener for wall collisions
            body.boundsInParentProperty().addListener((observable, oldValue, newValue) ->  
                    SlimeTanker.checkWallIntersection(body)
            );
        }
        else {
            //call some pre-setup movement pathing functions
            createPatrolPath(pathPattern);
            
            //assign listener to the NPC body for hit detection
            body.boundsInParentProperty().addListener((observable, oldValue, newValue) ->    
                    SlimeTanker.checkBodyIntersection()
            ); 
        }   
    }
    
    //if tank is player's tank, register key commands and events asscoiated with the tank
    private void setEvents(Pane pane, Color fillColorGun){
        
        if (player == 0) { 
            body.setOnKeyPressed(e -> {    
                
                
                if (!enableLudicrousMode) {
                    //Change speed
                    switch (e.getCode()){
                        case DIGIT1:
                            tankSpeed = 2;
                            gear = 1;
                            break;
                        case DIGIT2:
                            tankSpeed = 5;
                            gear = 2;
                            break;
                        case DIGIT3:
                            tankSpeed = 8;
                            gear = 3;
                    }
                }
                //Ludicrous knows not this thing called "gear speeds"
                else {
                    tankSpeed = 100;
                    gear = 999999;
                }
                
                //Movement
                //return new current direction of the tank
                currentDirectionOfTank = Movement.moveTank(e.getCode(), body, gun, 
                        tankSpeed, currentDirectionOfTank);              

                //Fire shell, if there's ammo
                if (ammo > 0) {
                    if (e.getCode() == KeyCode.SPACE){
                        //create and animate shell
                        fireShell(pane, fillColorGun); 
                        //retuce ammo by 1
                        setReduceAmmo();                  
                    }
                } 
                
                //reset level if needed
                if (new KeyCodeCombination(KeyCode.R, 
                        KeyCombination.CONTROL_DOWN).match(e)){
                    
                    if (enableAbusiveMode){
                        SlimeTanker.getRandomMessage();
                    }
                    
                    SlimeTanker.resetLevel();
                }
                
                //Update info for player
                SlimeTanker.updateLabels(); 
            }); 
        }
    }
    
    //Function for shell creation and animation
    private void fireShell(Pane pane, Color fillColorGun){
        
        setCenterOfTanksPosition();
        //create shell
        shell = new Shell(player, gun.getWidth(), shellSpeed, fillColorGun, 
                centerOfTankX, centerOfTankY, currentDirectionOfTank);
        //Add shell to pane
        pane.getChildren().add(shell.getShell());
        //add shell to listener for collision detection
        shell.getShell().boundsInParentProperty().addListener((observable, oldValue, newValue) ->    
            SlimeTanker.checkShellIntersection(shell.getPathTransition(), shell.getShell(),
                    shell.getShellID())
        );

        //play animation
        shell.pt.play();
    }
    
    //create a new rectangle
    private Rectangle createRectangle(Double width, Double length){
        return new Rectangle(width, length);
    }
    
    private void setColors(Color fillColorBody, Color strokeColorBody,
            Color fillColorGun, Color strokeColorGun){
        
        body.setFill(fillColorBody);
        body.setStroke(strokeColorBody);
        gun.setFill(fillColorGun);
        gun.setStroke(strokeColorGun);        
        
    }
    
    //Set inital starting position of the tank, facing north
    //This is also used for respawn
    public void setStartingPosition(Double x, Double y){
        
        //Set main body
        body.setX(x);
        body.setY(y);
        
        startingX = x;
        startingY = y;
        
        //set gun on body
        setGunPosition();
        
        //set tank diraction default value
        setDirectionOfTank(0.0);
    }
    
    public void setDirectionOfTank(Double direction){
        currentDirectionOfTank = direction;
    }
    
    public void setGunPosition(){
        gun.setX(body.getX() + ((body.getWidth() / 2) - (gun.getWidth() / 2))); //adjust for X
        gun.setY(body.getY() - (body.getHeight() / 1.5));  //adjust for Y 
    }
    
    //Function to get current location of the center of the tank
    private void setCenterOfTanksPosition(){
        centerOfTankX = body.getX() + ((body.getWidth() / 2));
        centerOfTankY = body.getY() + (body.getHeight() / 2);
    }
    
    public void setHealthOnHit(Integer damageAmt){
        health = health - damageAmt;
    }
    
    public void addPlayerLives(Integer numOfHealth){
        health = health + numOfHealth;
    }
    
    public void setAmmo(Integer ammoAmount){
        ammo = ammoAmount;
    }
    
    public void setReduceAmmo(){
        ammo--;
    }
    
    
    //set the Path Transition Parameters
    private void setPathTransitionParameters(Integer ms, Shape shape, 
            double rate, Boolean isInterpolatorLinear){
        
        ptTank.setDuration(Duration.millis(ms)); //lower number = faster
        ptTank.setRate(rate); //Rate 1.0 is normal play, 2.0 is 2 time normal, -1.0 is backwards, etc...
        ptTank.setPath(shape);
        if (isInterpolatorLinear){
            ptTank.setInterpolator(Interpolator.LINEAR);
        }
        ptTank.setNode(body);
        ptTank.setCycleCount(Timeline.INDEFINITE);
        
    }
    
    /* Function to create patrol path for NPC tanks. Particular level setups will
    pass coordinates for the path shapes.
    */
    private void createPatrolPath(Integer pathType){
        
        
        switch (pathType){
            //0 = no path

            //level 3, normal mode
            case 1:
                ptTank = new PathTransition();
                /*(Integer: ms, Shape: shape, Integer: rate, Boolean: isInterpolatorLinear)*/
                setPathTransitionParameters(7000, new Rectangle(360, 670, 770, 70),
                        1, false);
                ptTank.play();
                break;
            case 2:
                ptTank = new PathTransition();
                setPathTransitionParameters(6000, new Rectangle(65, 80, 800, 145),
                        -1, false);
                ptTank.play();
                break;
                
            //level 4, normal mode
            case 3:
                Path nm3Path1 = new Path();
                nm3Path1.getElements().add(new MoveTo(1140, 630));
                nm3Path1.getElements().add(new VLineTo(750));
                nm3Path1.getElements().add(new HLineTo(770));
                nm3Path1.getElements().add(new VLineTo(445));
                nm3Path1.getElements().add(new HLineTo(545));                
                nm3Path1.getElements().add(new VLineTo(360));
                nm3Path1.getElements().add(new HLineTo(880));
                nm3Path1.getElements().add(new VLineTo(630));

                nm3Path1.getElements().add(new ClosePath());
                
                ptTank = new PathTransition();
                setPathTransitionParameters(4500, nm3Path1, 1, false);
                ptTank.play();

                break;
            case 4:
                Path nm3Path2 = new Path();
                nm3Path2.getElements().add(new MoveTo(430, 40));
                nm3Path2.getElements().add(new HLineTo(495));
                nm3Path2.getElements().add(new VLineTo(155));
                nm3Path2.getElements().add(new HLineTo(675));
                nm3Path2.getElements().add(new VLineTo(25));
                nm3Path2.getElements().add(new HLineTo(880));
                nm3Path2.getElements().add(new VLineTo(50));
                nm3Path2.getElements().add(new HLineTo(720));
                nm3Path2.getElements().add(new VLineTo(180));
                nm3Path2.getElements().add(new HLineTo(430));
                nm3Path2.getElements().add(new ClosePath());
                
                ptTank = new PathTransition();
                setPathTransitionParameters(3500, nm3Path2, -1, true);
                ptTank.play();
                
                break;
            case 5:
                ptTank = new PathTransition();
                setPathTransitionParameters(4000, new Circle(200, 590, 150), 1,
                        true);
                ptTank.play();
                break;
            
            //level 5, normal mode
            case 6:
                Path nm4Path1 = new Path();
                nm4Path1.getElements().add(new MoveTo(1120, 280));
                nm4Path1.getElements().add(new VLineTo(340));
                nm4Path1.getElements().add(new HLineTo(45));
                nm4Path1.getElements().add(new VLineTo(280));
                nm4Path1.getElements().add(new ClosePath());
                
                ptTank = new PathTransition();
                setPathTransitionParameters(3000, nm4Path1, -1, true);
                ptTank.play();
                break;
            case 7:
                Path nm4Path2 = new Path();
                nm4Path2.getElements().add(new MoveTo(45, 340));
                nm4Path2.getElements().add(new VLineTo(280));
                nm4Path2.getElements().add(new HLineTo(1120));
                nm4Path2.getElements().add(new VLineTo(340));
                nm4Path2.getElements().add(new ClosePath());
                
                ptTank = new PathTransition();
                setPathTransitionParameters(3500, nm4Path2, 1, true);
                ptTank.play();  
                break;
            case 8:
                Path nm4Path3 = new Path();
                nm4Path3.getElements().add(new MoveTo(840, 740));
                nm4Path3.getElements().add(new HLineTo(1080));
                nm4Path3.getElements().add(new LineTo(840, 500));
                nm4Path3.getElements().add(new ClosePath());
                
                ptTank = new PathTransition();
                setPathTransitionParameters(2800, nm4Path3, 2, true);
                ptTank.play();  
                break;
            case 9:
                ptTank = new PathTransition();
                setPathTransitionParameters(3800, new Rectangle(410, 500, 240, 240),
                        2, true);
                ptTank.setAutoReverse(true);
                ptTank.play();  
                break;    
            case 10:
                Path nm4Path5 = new Path();
                nm4Path5.getElements().add(new MoveTo(290, 740));
                nm4Path5.getElements().add(new HLineTo(50));
                nm4Path5.getElements().add(new LineTo(290, 500));
                nm4Path5.getElements().add(new ClosePath());
                
                ptTank = new PathTransition();
                setPathTransitionParameters(3500, nm4Path5, -2, true);
                ptTank.play();  
                break;
                
            //level 6, normal mode
            case 11:
                ptTank = new PathTransition();
                setPathTransitionParameters(2000, new Ellipse(600, 400, 300, 200),
                        -1.5, true);
                ptTank.play();  
                break;
            case 12:
                ptTank = new PathTransition();
                Ellipse nm5Path2 = new Ellipse(600, 400, 300, 200);
                nm5Path2.setRotate(225);                
                setPathTransitionParameters(2000, nm5Path2, -1.5, true);
                ptTank.play();  
                break;
            case 13:
                ptTank = new PathTransition();
                Ellipse nm5Path3 = new Ellipse(600, 400, 300, 200);
                nm5Path3.setRotate(315);
                setPathTransitionParameters(2000, nm5Path3, 1.5, true);
                ptTank.play();  
                break;
            
                
                
            //Ludicrous level
            case 999:
                //random parameters for ludicrous mode
                double randomX = (Math.random() * 1200) + 1;
                double randomY = (Math.random() * 550) + 1;

                //adjust so a X=1200 and Y=630 doesnt use a shape that goes past the screen
                double randomWidth = (Math.random() * (1200 - randomX));
                double randomHeight = (Math.random() * (550 - randomY));
                
                double direction = 0;
                ptTank = new PathTransition();
                Shape shape = getRandomShape(randomX, randomY, randomWidth, 
                        randomHeight);
                shape.setRotate(Math.random() + 359);
                
                //randomize direction
                if ((int)(Math.random() * 2) == 0){
                    direction = -1;
                }
                else {
                    direction = 1;
                }     
                
                setPathTransitionParameters((int)(Math.random() * 800) + 1200, 
                        shape, direction, true);
                
                //randomize reversal
                if ((int)(Math.random() * 2) == 0){
                    ptTank.setAutoReverse(false);
                }
                else {
                    ptTank.setAutoReverse(true);
                }
                
                ptTank.play(); 
                
        }
    }
    
    private Shape getRandomShape(double randomX, double randomY,
            double randomWidth, double randomHeight){
        
        //3 possibilities
        int rando = (int)(Math.random() * 3);
        Shape shape = new Rectangle(5, 5,
                        5, 5);
        
        switch (rando){
                //rectangle
                case 0:
                    shape = new Rectangle(randomX, randomY,
                        randomWidth, randomHeight);
                    break;
                //circle
                case 1:
                    shape = new Circle(randomX, randomY, randomHeight / 2);
                    break;
                //elipse
                case 2:
                    shape = new Ellipse(randomX, randomY, randomWidth / 2,
                    randomHeight / 2);
            }
        return shape;
    }
    
    //Getters
    public Rectangle getBody(){
        return body;
    }
    
    public Integer getPlayer(){
        return player;
    }
    
    public Integer getHealth(){
        return health;
    }
    
    public Double getStartingX(){
        return startingX;
    }
    
    public Double getStartingY(){
        return startingY;
    }
}

//Shell bullet
class Shell {
    
    Circle circle;
    Line shellLine;
    double shellSpeed;
    final int SHELL_DAMAGE = 1;
    PathTransition pt;
    int shellID;  //to keep track of who's shell it is
    
    //default
    public Shell(){
        circle = createCircle(2.0);
    }
    
    //pass the gun's width, shell color, starting pos
    public Shell(Integer player, Double gunWidth, Double shellSpeed, Color fillColor, 
            Double tankCenterX, Double tankCenterY, Double tanksDirection){
        
        shellID = player;
        
        circle = createCircle(gunWidth);
        //this.shellSpeed = shellSpeed;
        setShellColor(fillColor);
        
        //create shell's animation path
        //set up an animation path and speed
        setShellPath(tankCenterX, tankCenterY, tanksDirection);


        pt = new PathTransition();
        pt.setDuration(Duration.millis(shellSpeed));
        pt.setPath(shellLine);
        pt.setNode(circle);
        pt.setCycleCount(1);
        pt.setOnFinished(e -> {
            circle.setVisible(false);
        });
    }
    
    //shell's firing path
    private void setShellPath(Double tankCenterX, 
            Double tankCenterY, Double tanksDirection){
        shellLine = new Line();
        shellLine.setStartX(tankCenterX);
        shellLine.setStartY(tankCenterY);
        //end direction should be based on the direction of the tank's front
        adjustLineForTanksDirection(tankCenterX, tankCenterY, tanksDirection);
    }
    
    //adjust for which way the tank is facing
    private void adjustLineForTanksDirection(Double tankCenterX, 
            Double tankCenterY, Double tanksDirection){
        
        //If up, x=tank's center  y=min  
        if (tanksDirection == 0) {
            shellLine.setEndX(tankCenterX);
            shellLine.setEndY((SlimeTanker.SCENE_SIZE_Y - 
                SlimeTanker.SCENE_SIZE_Y));
        }
        //down,  x=tank's center  y=max
        else if (tanksDirection == 180) {
            shellLine.setEndX(tankCenterX);
            shellLine.setEndY((SlimeTanker.SCENE_SIZE_Y));
        }
        //left, x=min, y=tank's center
        else if (tanksDirection == 270) {
            shellLine.setEndX((SlimeTanker.SCENE_SIZE_X - 
                SlimeTanker.SCENE_SIZE_X));
            shellLine.setEndY((tankCenterY));
        }
        //right x=max, y=tank's center
        else if (tanksDirection == 90) {
            shellLine.setEndX((SlimeTanker.SCENE_SIZE_X));
            shellLine.setEndY((tankCenterY));
        }

    }
    
    //Getters
    public Circle getShell(){
        return circle;
    }
    public Integer getShellDamage(){
        return SHELL_DAMAGE;
    }
    public PathTransition getPathTransition(){
        return pt;
    }
    public Integer getShellID(){
        return shellID;
    }
    
    //Create a new circle. adjust its size based on the gun's width
    private Circle createCircle(Double gunWidth){
        return new Circle(gunWidth / 2);
    }
    
    //set shell's fill color. if invisible mode, make it black
    private void setShellColor(Color fillColor){
        if (enableInvisibleMode){
            circle.setFill(Color.BLACK);
        }
        else{
            circle.setFill(fillColor);
        }
    }
   
    Object boundsInParentProperty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
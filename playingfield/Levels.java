/*
 * Programmer: Dan Hopp
 * Date: 25-APR-2020
 * Description: This class contains the level layouts. Each layout will create
and place player, npc, and wall objects, as well update the labels on the pane 
which contain information such as health, current gear speed, ammo, and current
level.

        Tank object parameters:
            (Double width, Double length, Color fillColorBody, 
            Color strokeColorBody, Color fillColorGun, Color strokeColorGun,
            Integer player, Double tankSpeed, Double shellSpeed, Integer playerHealth,
            Integer ammo, Double startingX, Double startingY, Integer pathPattern,
            Pane pane)
        Adding the tank to the pane is located within the Tank class.
        
        Wall parameters:
            (Double startx, Double startY, Double width, Double height)
        
        Label function:
            (String playerHealth, String numOfShots, String currentLevel, String message)
 */
package playingfield;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static playingfield.SlimeTanker.defaultTankSpeed;
import static playingfield.SlimeTanker.currentLevel;
import static playingfield.SlimeTanker.enableInvisibleMode;
import static playingfield.SlimeTanker.enableLudicrousMode;


public class Levels {
    
    /*Function that contains the setup information for each level:
        Player spawn, wall locations.
        NPC spawn and pathing option.
    */
    public static void gameLevels(Integer current_stage, Pane pane){
        
        //Reset pane. Clear all sprites and empty arrays
        SlimeTanker.clearPaneAndArrays();
        
        if(enableLudicrousMode){
            ludicrousGame(current_stage, pane);
        }
        else{
            normalGame(current_stage, pane);
        }
    }
    
    //perameters for a normal game
    private static void normalGame(Integer current_stage, Pane pane){
        
        int playerHealth = 0;
        int numOfShots = 0;
        int gear = 1;
        
        switch(current_stage){
            //stage 0 is a testing stage
            case 0:
                SlimeTanker.addWallToWallArray(new Wall(500.0, 200.0, 200.0, 20.0)); //1st wall
                SlimeTanker.addWallToWallArray(new Wall(50.0, 200.0, 250.0, 20.0)); //2nd wall         
                SlimeTanker.addWallsToPane();
                
                //player 1
                playerHealth = 4;
                numOfShots = 5;
                SlimeTanker.addTankToTankArray(new Tank(40.0, 80.0, Color.OLIVE, Color.BLUE, Color.GREEN, 
                        Color.GREEN, 0, defaultTankSpeed, 500.00, playerHealth, numOfShots, 
                        20.0, 400.0, 0, pane));
                //NPC red
                SlimeTanker.addTankToTankArray(new Tank(60.0, 70.0, Color.RED, Color.RED, Color.TRANSPARENT, 
                        Color.TRANSPARENT, 1, 2.0, 250.00, 1, 0, 400.0, 100.0, 1, pane));
                //NPC purple
                SlimeTanker.addTankToTankArray(new Tank(20.0, 40.0, Color.PURPLE, Color.PURPLE, Color.TRANSPARENT, 
                        Color.TRANSPARENT, 1, 2.0, 250.00, 1, 0, 400.0, 400.0, 2, pane));
                
                SlimeTanker.createLabels("Tank Health: " + playerHealth, "Gear: " + gear,
                        "Shots: " + numOfShots, "Level " + currentLevel, "");
                
                break;
            //Level 1, normal mode
            case 1:
                //player
                /*(Double: width, Double: length,
                Color: fillColorBody, Color: strokeColorBody, Color: fillColorGun, Color: strokeColorGun,
                Integer: player, Double: tankSpeed, Double: shellSpeed, 
                Integer: playerHealth, Integer: ammo, 
                Double: startingX, Double: startingY,
                Integer: pathPattern, Pane: pane)*/
                if (enableInvisibleMode){
                    playerHealth = 2;
                    numOfShots = 8;
                    SlimeTanker.addTankToTankArray(new Tank(80.0, 125.0, 
                        Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, 
                        0, defaultTankSpeed, 500.00, 
                        playerHealth, numOfShots, 
                        530.0, 590.0, 
                        0, pane));   
                }
                else {
                    playerHealth = 2;
                    numOfShots = 5;
                    SlimeTanker.addTankToTankArray(new Tank(80.0, 125.0, 
                        Color.GREEN, Color.YELLOWGREEN, Color.YELLOWGREEN, Color.GREEN, 
                        0, defaultTankSpeed, 500.00, 
                        playerHealth, numOfShots, 
                        530.0, 590.0, 
                        0, pane));   
                }
                
                
                //walls
                //(Double startx, Double startY, Double width, Double height)
                SlimeTanker.addWallToWallArray(new Wall(430.0, 300.0, 280.0, 40.0));
                SlimeTanker.addWallsToPane();
                
                //NPCs
                SlimeTanker.addTankToTankArray(new Tank(110.0, 110.0, 
                        Color.DODGERBLUE, Color.DARKBLUE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        780.0, 270.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(110.0, 110.0, 
                        Color.MEDIUMPURPLE, Color.DARKMAGENTA, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        515.0, 130.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(110.0, 110.0, 
                        Color.YELLOW, Color.GOLD, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        240.0, 270.0, 
                        0, pane));
                
                
                //Labels
                SlimeTanker.createLabels("Tank Health: " + playerHealth, "Gear: " + gear,
                        "Shots: " + numOfShots, "Level " + currentLevel, "");
                break;
                
            //Level 2, normal mode
            case 2:
                //player
                if (enableInvisibleMode){
                    playerHealth = 6;
                    numOfShots = 10;
                    SlimeTanker.addTankToTankArray(new Tank(80.0, 80.0, 
                        Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, 
                        0, defaultTankSpeed, 500.00, 
                        playerHealth, numOfShots, 
                        545.0, 700.0, 
                        0, pane));   
                }
                else {
                    playerHealth = 6;
                    numOfShots = 5;    
                    SlimeTanker.addTankToTankArray(new Tank(80.0, 80.0, 
                            Color.DARKMAGENTA, Color.MEDIUMPURPLE, Color.MEDIUMPURPLE, Color.DARKMAGENTA, 
                            0, defaultTankSpeed, 500.00, 
                            playerHealth, numOfShots, 
                            545.0, 700.0, 
                            0, pane));
                }

                //walls
                SlimeTanker.addWallToWallArray(new Wall(455.0, 580.0, 35.0, 220.0));
                SlimeTanker.addWallToWallArray(new Wall(490.0, 580.0, 195.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(685.0, 580.0, 35.0, 220.0));
                
                //upper left box
                SlimeTanker.addWallToWallArray(new Wall(55.0, 60.0, 235.0, 40.0)); //top of box
                SlimeTanker.addWallToWallArray(new Wall(55.0, 250.0, 235.0, 40.0)); //bottom
                SlimeTanker.addWallToWallArray(new Wall(55.0, 100.0, 35.0, 150.0)); //left side
                SlimeTanker.addWallToWallArray(new Wall(255.0, 100.0, 35.0, 150.0)); //right side
                
                //lower left box
                SlimeTanker.addWallToWallArray(new Wall(55.0, 500.0, 235.0, 40.0)); //top of box
                SlimeTanker.addWallToWallArray(new Wall(55.0, 690.0, 235.0, 40.0)); //bottom
                SlimeTanker.addWallToWallArray(new Wall(55.0, 540.0, 35.0, 150.0)); //left side
                SlimeTanker.addWallToWallArray(new Wall(255.0, 540.0, 35.0, 150.0)); //right side
                
                //upper right box
                SlimeTanker.addWallToWallArray(new Wall(850.0, 60.0, 235.0, 40.0)); //top of box
                SlimeTanker.addWallToWallArray(new Wall(850.0, 250.0, 235.0, 40.0)); //bottom
                SlimeTanker.addWallToWallArray(new Wall(850.0, 100.0, 35.0, 150.0)); //left side
                SlimeTanker.addWallToWallArray(new Wall(1050.0, 100.0, 35.0, 150.0)); //right side
                
                //lower right box
                SlimeTanker.addWallToWallArray(new Wall(850.0, 500.0, 235.0, 40.0)); //top of box
                SlimeTanker.addWallToWallArray(new Wall(850.0, 690.0, 235.0, 40.0)); //bottom
                SlimeTanker.addWallToWallArray(new Wall(850.0, 540.0, 35.0, 150.0)); //left side
                SlimeTanker.addWallToWallArray(new Wall(1050.0, 540.0, 35.0, 150.0)); //right side
                
                SlimeTanker.addWallsToPane();

                //npcs
                SlimeTanker.addTankToTankArray(new Tank(85.0, 85.0, 
                        Color.ORANGE, Color.CHOCOLATE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        130.0, 130.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(85.0, 85.0, 
                        Color.BLUE, Color.DARKBLUE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        130.0, 573.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(85.0, 85.0, 
                        Color.GREEN, Color.DARKGREEN, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        925.0, 130.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(85.0, 85.0, 
                        Color.YELLOW, Color.GOLD, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        925.0, 573.0, 
                        0, pane));
                
                //Labels
                SlimeTanker.createLabels("Tank Health: " + playerHealth, "Gear: " + gear,
                        "Shots: " + numOfShots, "Level " + currentLevel, "");
                break;
                
            //Level 3, normal mode
            case 3:
                //player
                if (enableInvisibleMode){
                    playerHealth = 2;
                    numOfShots = 10;
                    SlimeTanker.addTankToTankArray(new Tank(80.0, 125.0, 
                        Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, 
                        0, defaultTankSpeed, 500.00, 
                        playerHealth, numOfShots, 
                        60.0, 670.0, 
                        0, pane));   
                }
                else {
                    playerHealth = 2;
                    numOfShots = 5;
                    SlimeTanker.addTankToTankArray(new Tank(80.0, 125.0, 
                            Color.GREEN, Color.YELLOWGREEN, Color.YELLOWGREEN, Color.GREEN, 
                            0, defaultTankSpeed, 500.00, 
                            playerHealth, numOfShots, 
                            60.0, 670.0, 
                            0, pane));
                }
                
                //walls
                //(Double startx, Double startY, Double width, Double height)
                SlimeTanker.addWallToWallArray(new Wall(200.0, 500.0, 55.0, 300.0));
                
                SlimeTanker.addWallToWallArray(new Wall(0.0, 300.0, 200.0, 50.0));
                SlimeTanker.addWallToWallArray(new Wall(200.0, 300.0, 200.0, 50.0));
                SlimeTanker.addWallToWallArray(new Wall(400.0, 300.0, 180.0, 50.0));
                
                SlimeTanker.addWallToWallArray(new Wall(525.0, 300.0, 55.0, 225.0));
                
                SlimeTanker.addWallToWallArray(new Wall(525.0, 525.0, 240.0, 50.0));
                SlimeTanker.addWallToWallArray(new Wall(765.0, 525.0, 240.0, 50.0));
                
                SlimeTanker.addWallToWallArray(new Wall(950.0, 370.0, 55.0, 155.0));
                SlimeTanker.addWallToWallArray(new Wall(950.0, 160.0, 55.0, 210.0));
                
                SlimeTanker.addWallsToPane();
                
                //NPCs
                SlimeTanker.addTankToTankArray(new Tank(90.0, 90.0, 
                        Color.DODGERBLUE, Color.DARKBLUE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        60.0, 400.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(90.0, 90.0, 
                        Color.LAWNGREEN, Color.CHARTREUSE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        550.0, 610.0, 
                        1, pane));
                SlimeTanker.addTankToTankArray(new Tank(105.0, 105.0, 
                        Color.MEDIUMPURPLE, Color.DARKMAGENTA, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        715.0, 350.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(90.0, 90.0, 
                        Color.YELLOW, Color.GOLD, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        325.0, 30.0, 
                        2, pane));
                
                
                //Labels
                SlimeTanker.createLabels("Tank Health: " + playerHealth, "Gear: " + gear,
                        "Shots: " + numOfShots, "Level " + currentLevel, "");
                break;
                
            //Level 4, normal mode
            case 4:
                //player
                if (enableInvisibleMode){
                    playerHealth = 2;
                    numOfShots = 10;
                    SlimeTanker.addTankToTankArray(new Tank(60.0, 60.0, 
                        Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, 
                        0, defaultTankSpeed, 500.00, 
                        playerHealth, numOfShots, 
                        530.0, 700.0, 
                        0, pane));   
                }
                else {
                    playerHealth = 1;
                    numOfShots = 6;
                    /*(Double: width, Double: length,
                    Color: fillColorBody, Color: strokeColorBody, Color: fillColorGun, Color: strokeColorGun,
                    Integer: player, Double: tankSpeed, Double: shellSpeed, 
                    Integer: playerHealth, Integer: ammo, 
                    Double: startingX, Double: startingY,
                    Integer: pathPattern, Pane: pane)*/
                    SlimeTanker.addTankToTankArray(new Tank(60.0, 60.0, 
                            Color.DARKBLUE, Color.DARKBLUE, Color.DODGERBLUE, Color.DARKBLUE, 
                            0, defaultTankSpeed, 500.00, 
                            playerHealth, numOfShots, 
                            530.0, 700.0, 
                            0, pane));
                }
                
                //walls
                //(Double startx, Double startY, Double width, Double height)
                SlimeTanker.addWallToWallArray(new Wall(650.0, 570.0, 50.0, 230.0));
                SlimeTanker.addWallToWallArray(new Wall(560.0, 570.0, 90.0, 45.0));
                
                SlimeTanker.addWallToWallArray(new Wall(415.0, 625.0, 50.0, 175.0));
                SlimeTanker.addWallToWallArray(new Wall(415.0, 425.0, 50.0, 200.0));
                SlimeTanker.addWallToWallArray(new Wall(415.0, 235.0, 50.0, 190.0));
                
                SlimeTanker.addWallToWallArray(new Wall(415.0, 210.0, 260.0, 45.0));
                SlimeTanker.addWallToWallArray(new Wall(675.0, 210.0, 260.0, 45.0));
                
                SlimeTanker.addWallToWallArray(new Wall(935.0, 210.0, 50.0, 165.0));
                SlimeTanker.addWallToWallArray(new Wall(935.0, 375.0, 50.0, 185.0));
                
                SlimeTanker.addWallToWallArray(new Wall(985.0, 515.0, 115.0, 45.0));
                
                SlimeTanker.addWallToWallArray(new Wall(845.0, 85.0, 50.0, 125.0));
                SlimeTanker.addWallToWallArray(new Wall(560.0, 0.0, 50.0, 125.0));
                
                SlimeTanker.addWallToWallArray(new Wall(275.0, 315.0, 140.0, 45.0));
                SlimeTanker.addWallToWallArray(new Wall(0.0, 315.0, 140.0, 45.0));

                SlimeTanker.addWallsToPane();
                
                //NPCs
                SlimeTanker.addTankToTankArray(new Tank(70.0, 70.0, 
                        Color.CRIMSON, Color.DARKRED, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        60.0, 400.0, 
                        3, pane));
                SlimeTanker.addTankToTankArray(new Tank(40.0, 40.0, 
                        Color.ORANGE, Color.ORANGERED, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        550.0, 610.0, 
                        4, pane));
                SlimeTanker.addTankToTankArray(new Tank(85.0, 85.0, 
                        Color.MEDIUMPURPLE, Color.DARKMAGENTA, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        0.0, 0.0, 
                        5, pane));
                SlimeTanker.addTankToTankArray(new Tank(85.0, 85.0, 
                        Color.DODGERBLUE, Color.DARKBLUE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        170.0, 260.0, 
                        0, pane));
                
                //Labels
                SlimeTanker.createLabels("Tank Health: " + playerHealth, "Gear: " + gear,
                        "Shots: " + numOfShots, "Level " + currentLevel, "");
                break;
                
            //Level 5, normal mode
            case 5:
                //player
                if (enableInvisibleMode){
                    playerHealth = 1;
                    numOfShots = 13;
                    SlimeTanker.addTankToTankArray(new Tank(60.0, 60.0, 
                        Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, 
                        0, defaultTankSpeed, 500.00, 
                        playerHealth, numOfShots, 
                        1100.0, 65.0, 
                        0, pane));   
                }
                else {
                    playerHealth = 2;
                    numOfShots = 11;

                    SlimeTanker.addTankToTankArray(new Tank(60.0, 60.0, 
                            Color.DARKBLUE, Color.DARKBLUE, Color.DODGERBLUE, Color.DARKBLUE, 
                            0, defaultTankSpeed, 500.00, 
                            playerHealth, numOfShots, 
                            1100.0, 65.0, 
                            0, pane));
                }
                
                //walls
                //(Double startx, Double startY, Double width, Double height)
                SlimeTanker.addWallToWallArray(new Wall(125.0, 175.0, 200.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(325.0, 175.0, 200.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(525.0, 175.0, 200.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(725.0, 175.0, 200.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(925.0, 175.0, 200.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(1125.0, 175.0, 75.0, 40.0));
                
                SlimeTanker.addWallToWallArray(new Wall(0.0, 400.0, 200.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(200.0, 400.0, 200.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(400.0, 400.0, 200.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(600.0, 400.0, 200.0, 40.0));
                SlimeTanker.addWallToWallArray(new Wall(800.0, 400.0, 250.0, 40.0));
                
                SlimeTanker.addWallsToPane();
                
                //npcs
                SlimeTanker.addTankToTankArray(new Tank(102.0, 102.0, 
                        Color.MEDIUMPURPLE, Color.DARKMAGENTA, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        920.0, 40.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(102.0, 102.0, 
                        Color.DARKBLUE, Color.DODGERBLUE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        740.0, 40.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(102.0, 102.0, 
                        Color.LIMEGREEN, Color.GREEN, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        560.0, 40.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(102.0, 102.0, 
                        Color.YELLOW, Color.GOLD, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        380.0, 40.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(102.0, 102.0, 
                        Color.ORANGE, Color.ORANGERED, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        200.0, 40.0, 
                        0, pane));
                SlimeTanker.addTankToTankArray(new Tank(102.0, 102.0, 
                        Color.RED, Color.FIREBRICK, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        30.0, 40.0, 
                        0, pane));
                
                SlimeTanker.addTankToTankArray(new Tank(50.0, 50.0, 
                        Color.YELLOW, Color.GOLD, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        0.0, 0.0, 
                        6, pane));
                SlimeTanker.addTankToTankArray(new Tank(50.0, 50.0, 
                        Color.DODGERBLUE, Color.DARKBLUE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        0.0, 0.0, 
                        7, pane));
                
                SlimeTanker.addTankToTankArray(new Tank(75.0, 75.0, 
                        Color.CORNFLOWERBLUE, Color.SKYBLUE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        0.0, 0.0, 
                        8, pane));
                SlimeTanker.addTankToTankArray(new Tank(75.0, 75.0, 
                        Color.LIGHTPINK, Color.HOTPINK, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        0.0, 0.0, 
                        9, pane));
                SlimeTanker.addTankToTankArray(new Tank(75.0, 75.0, 
                        Color.DARKSLATEBLUE, Color.DARKBLUE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        0.0, 0.0, 
                        10, pane));
                
                //Labels
                SlimeTanker.createLabels("Tank Health: " + playerHealth, "Gear: " + gear,
                        "Shots: " + numOfShots, "Level " + currentLevel, "");
                
                break;
                
            //level 6, normal mode
            case 6:
                //player
                if (enableInvisibleMode){
                    playerHealth = 1;
                    numOfShots = 4;
                    SlimeTanker.addTankToTankArray(new Tank(80.0, 125.0, 
                        Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, 
                        0, defaultTankSpeed, 500.00, 
                        playerHealth, numOfShots, 
                        560.0, 320.0, 
                        0, pane));   
                }
                else {
                    playerHealth = 1;
                    numOfShots = 4;
                    /*(Double: width, Double: length,
                    Color: fillColorBody, Color: strokeColorBody, Color: fillColorGun, Color: strokeColorGun,
                    Integer: player, Double: tankSpeed, Double: shellSpeed, 
                    Integer: playerHealth, Integer: ammo, 
                    Double: startingX, Double: startingY,
                    Integer: pathPattern, Pane: pane)*/
                    SlimeTanker.addTankToTankArray(new Tank(80.0, 125.0, 
                            Color.GREEN, Color.YELLOWGREEN, Color.YELLOWGREEN, Color.GREEN, 
                            0, defaultTankSpeed, 500.00, 
                            playerHealth, numOfShots, 
                            560.0, 320.0, 
                            0, pane));
                }

                //npcs
                SlimeTanker.addTankToTankArray(new Tank(100.0, 100.0, 
                        Color.DARKSLATEBLUE, Color.DARKBLUE, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        0.0, 0.0, 
                        11, pane));
                SlimeTanker.addTankToTankArray(new Tank(100.0, 100.0, 
                        Color.YELLOW, Color.GOLD, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        0.0, 0.0, 
                        12, pane));
                SlimeTanker.addTankToTankArray(new Tank(100.0, 100.0, 
                        Color.RED, Color.DARKRED, Color.TRANSPARENT, Color.TRANSPARENT, 
                        1, 2.0, 250.00, 
                        1, 0, 
                        0.0, 0.0, 
                        13, pane));

                //Labels
                SlimeTanker.createLabels("Tank Health: " + playerHealth, "Gear: " + gear,
                        "Shots: " + numOfShots, "Level " + currentLevel, "");
                break;
            //level 7
            case 7:
                SlimeTanker.showPlayerWinScreen();
                
        }
    }
    
    //perameters for a ludicrous game
    private static void ludicrousGame(Integer current_stage, Pane pane){
        
        int playerHealth = 1;
        int numOfShots = 0;
        int gear = 9999999;
        
        
        switch(current_stage){
            case 1:
                //Player
                /*(Double: width, Double: length,
                Color: fillColorBody, Color: strokeColorBody, Color: fillColorGun, Color: strokeColorGun,
                Integer: player, Double: tankSpeed, Double: shellSpeed, 
                Integer: playerHealth, Integer: ammo, 
                Double: startingX, Double: startingY,
                Integer: pathPattern, Pane: pane)*/
                SlimeTanker.addTankToTankArray(new Tank(80.0, 125.0, 
                        Color.BLACK, Color.RED, Color.RED, Color.BLACK, 
                        0, defaultTankSpeed, 500.00, 
                        playerHealth, numOfShots, 
                        530.0, 670.0, 
                        0, pane));
                
                //NPCs
                //creation loop
                for (int i = 0; i < 275; i++){
                    
                    //random size
                    double squareSize = (Math.random() * 90) + 20;
                    
                    //Random colors
                    Color color1 = new Color(Math.random(), Math.random(), 
                    Math.random(), 1);
                    Color color2 = new Color(Math.random(), Math.random(), 
                    Math.random(), 1);
                    
                    SlimeTanker.addTankToTankArray(new Tank(squareSize, squareSize, 
                            color1, color2, Color.TRANSPARENT, Color.TRANSPARENT, 
                            1, 2.0, 250.00, 
                            1, 0, 
                            0.0, 0.0, 
                            999, pane));
                }        
                        
                SlimeTanker.createLabels("Tank Health: " + playerHealth, "Gear: " + gear,
                        "Shots: " + numOfShots, "Level " + (currentLevel - 1), "");
        }
    }
}

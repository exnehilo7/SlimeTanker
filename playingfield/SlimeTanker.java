/*
 * Programmer: Dan Hopp
 * Date: 25-APR-2020
 * Description: This is a top-down shooting game. The player has limited health
and ammo. They can adjust their speed, and reset the level if needed. It offers 
several modes of play, for fun and for difficulty.

Walls and npcs will damage the tank. Walls are immune to shots.

This class contains the title, mission breifing, post-level advancement, win, and
game over screens, the hit detection for the player, walls, and npcs, and essential
variables and arrays to keep the game operating.
 */
package playingfield;

import java.util.ArrayList;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class SlimeTanker extends Application{
    
    //Arrays for the objects and shapes
    public static ArrayList<Tank> tankArray = new ArrayList<>();
    public static ArrayList<Shell> shellArray = new ArrayList<>();
    public static ArrayList<Wall> wallArray = new ArrayList<>();
    public static ArrayList<Explosion> kaboomArray = new ArrayList<>();
    
    //Window size
    public static final double SCENE_SIZE_X = 1200;
    public static final double SCENE_SIZE_Y = 800;
    static Pane pane;
    
    //Game mode toggles
    static boolean enableNormalMode = false;
    static boolean enableInvisibleMode = false;
    static boolean enableAbusiveMode = false;
    static boolean enableLudicrousMode = false;
    
    //Current level & default tank speed
    static int previousLevel = 1;
    static int currentLevel = 1;
    static double defaultTankSpeed = 2.0;
    
    //Labels for screen info
    static Label lblPlayerHealth;
    static Label lblGear;
    static Label lblNumOfShots;
    static Label lblCurrentLevel;
    static Label lblMessage;
    static String strAbusiveMessage = "";
    
    //Here we go!
    public static void main(String[] args){
        Application.launch();
    }
    
    /* Set the stage and show the title screen */
    @Override
    public void start(Stage PrimaryStage){
       
        pane = new Pane();
        pane.setStyle("-fx-background-color: white;");

        //activate pane and main stage
        setAndShowStage(PrimaryStage);

        //Title screen
        showTitleScreen();
        
        //For testing/////////////////////////////////
        //currentLevel = 3;
        //enableNormalMode = true;
        //enableAbusiveMode = true;
        //enableLudicrousMode = true;
        //enableInvisibleMode = true;
        //Levels.gameLevels(currentLevel, pane);
        //showGameOverScreen();
        //showPlayerWinScreen();
        //showLevelCompleteScene();
        //////////////////////////////////////////
    }
    
    //Set and show main stage
    private void setAndShowStage(Stage stage){
        Scene scene = new Scene(pane, SCENE_SIZE_X, SCENE_SIZE_Y);
        stage.setTitle("Slime Tanker");
        stage.setScene(scene);
        stage.show();         
    }
    
    //function to reset the current level
    static void resetLevel(){
        Levels.gameLevels(currentLevel, pane);
    }
    
    //function to reset pane for the next level
    static void clearPaneAndArrays(){
        //clear all children
        pane.getChildren().clear();
        //clear wall array
        wallArray.clear();
        //clear tank array
        tankArray.clear();
        //clear shell array
        shellArray.clear();
        //clear explosion array
        kaboomArray.clear();
    }
    
    //add walls to the pane
    static void addWallsToPane(){
        for (int i = 0; i < wallArray.size(); i++){
            pane.getChildren().add(wallArray.get(i).rectangle);
        }
    }
    
    //add objects/shapes to arrays
    static void addTankToTankArray(Tank tank){
        tankArray.add(tank);
    }
    static void addWallToWallArray(Wall wall){
        wallArray.add(wall);
    }
    
    //Labels for pane. Pass player health, stage number, number of shots
    static void createLabels(String playerHealth, String gear, String numOfShots,
            String currentLevel, String message){
        
        lblPlayerHealth = new Label(playerHealth);
        lblGear = new Label(gear); 
        lblNumOfShots = new Label(numOfShots);
        lblCurrentLevel = new Label(currentLevel);
        lblMessage = new Label(message);
        
        //Format
        lblPlayerHealth.setFont(Font.font("Stencil", 18));
        lblGear.setFont(Font.font("Stencil", 18));
        lblNumOfShots.setFont(Font.font("Stencil", 18));
        lblCurrentLevel.setFont(Font.font("Stencil",  18));
        
        lblMessage.setFont(Font.font("OCR A Extended", FontWeight.BOLD, 40));
        
        addLabelsToPane();
    }
    
    //Add labels
    private static void addLabelsToPane(){
        
        //Label Pos
        lblPlayerHealth.relocate(0, 0); //Top left
        lblGear.relocate(0, 17); //Just below top left
        lblNumOfShots.relocate(1100, 0); //Top right
        lblCurrentLevel.relocate(540, 0); //Top center
        lblMessage.relocate(500, 400);  //center center

        pane.getChildren().addAll(lblPlayerHealth, lblGear, lblNumOfShots, 
                lblCurrentLevel, lblMessage);
    }
    
    //Function to update the label text on screen
    public static void updateLabels(){
        if (tankArray.size() > 0){
            lblPlayerHealth.setText("Tank Health: " + tankArray.get(0).getHealth());
            lblGear.setText("Gear: " + tankArray.get(0).gear);
            lblNumOfShots.setText("Shots: " + tankArray.get(0).ammo);
        }
        lblCurrentLevel.setText("Level " + currentLevel);

        if (enableAbusiveMode){
            lblMessage.setText(strAbusiveMessage);
        }
        else {
            lblMessage.setText("");
        }

    }
    
    //check if the tank's shell hit an object(wall or tank body)
    public static void checkShellIntersection(PathTransition pt, Circle circle,
            Integer shellID) {
        
        //did shell hit wall? if so, stop
        for (int i = 0; i < wallArray.size(); i++){
            if (circle.getBoundsInParent().
                    intersects(wallArray.get(i).rectangle.getBoundsInParent())){
                
                //stop the shell
                pt.stop();
                
                //remove shell from pane
                pane.getChildren().remove(circle);
                
            }            
        }
        
        //Did shell hit NPC?  if so, npc takes hit
        for(int i = 0; i < tankArray.size(); i++){
            //ignore the tank the shell came from
            if (tankArray.get(i).player != shellID){
                                                     
                //check for hit
                if (circle.getBoundsInParent().intersects(
                        tankArray.get(i).body.getBoundsInParent())){
                
                    //stop the shell
                    pt.stop();

                    //circle.setVisible(false);
                    pane.getChildren().remove(circle);

                    //adjust NPC life count
                    tankArray.get(i).setHealthOnHit(
                            tankArray.get(0).shell.getShellDamage());

                    npcHitResolution(tankArray.get(i));

                    placeExplosionsOnPane();
                        
                }  
            }
        }
    }
    
    //check if the NPCs hit the player
    public static void checkBodyIntersection() {

        for(int i = 0; i < tankArray.size(); i++){
            //NPCs can only collide with the player
            if (i > 0){
                //check for hit
                if (tankArray.get(i).body.getBoundsInParent().intersects(
                 tankArray.get(0).body.getBoundsInParent())){
                
                    //avoid those pesky out of bounds exceptions
                    if (tankArray.get(0).health == 1){
                        showGameOverScreen();
                        break;
                    }
                    else {
                        //pause animation
                        if(tankArray.get(i).pathPattern > 0){ //avoid calls to null
                            tankArray.get(i).ptTank.pause();
                        }

                        //player health goes down by 1
                        tankArray.get(0).setHealthOnHit(1); 
                        playerHitNPCResolution(tankArray.get(0));

                        //create npc explosion animation and remove npc from pane
                        npcHitResolution(tankArray.get(i));                    

                        //add explosions to pane
                        placeExplosionsOnPane();

                        //set so blank message is only called once
                        previousLevel = currentLevel;
                        
                        //update info for player
                        updateLabels();
                    }
                }  
            }
        }
    }
    
    //check if player hit a wall
    public static void checkWallIntersection(Rectangle rectangle) {

        for(int i = 0; i < wallArray.size(); i++){
        
              if (rectangle.getBoundsInParent().
                    intersects(wallArray.get(i).rectangle.getBoundsInParent())){
       
                //avoid those pesky out of bounds exceptions
                if (tankArray.get(0).health == 1){
                    showGameOverScreen();
                    break;
                }
                else {
                    //player health goes down by 1
                    tankArray.get(0).setHealthOnHit(1); 
                    playerHitWallResolution(tankArray.get(0), wallArray.get(i).rectangle);
                    wallArray.remove(i);  

                    //add explosions to pane
                    placeExplosionsOnPane();

                    //set so blank message is only called once
                    previousLevel = currentLevel;
                    
                    updateLabels();
                }
            }            
        }
    }
    
    
    //remove NPC from the pane and create an explosion sprite based on its last 
    //coordinates
    private static void npcHitResolution(Tank npc) {
        
        //reduce health by 1
        npc.setHealthOnHit(1);

        //get NPC's current x and Y. Account for active pathing
        double npcX = npc.body.getTranslateX() + npc.body.getX();
        double npcY = npc.body.getTranslateY() + + npc.body.getY();

        //make explosion sprite for NPC
        createExplosionSprite(npc, npcX, npcY);

        //remove NPC from pane
        pane.getChildren().remove(npc.body);
        pane.getChildren().remove(npc.gun);
        
        
        tankArray.remove(npc); 
        
        //if tankArray size = 1, only the player should be left. call next level
        if (tankArray.size() == 1){
            
            previousLevel = currentLevel;
            
            currentLevel++;
            
            //display level complete message and call next level
            showLevelCompleteScene();
        } 
    }
    
    
    //function to resolve player hitting a wall. reduce health by 1, trigger 
    //game over if needed
    private static void playerHitWallResolution(Tank tank, Rectangle rectangle){
        
        //get Tank's x and Y
        double npcX = tank.body.getX();
        double npcY = tank.body.getY();     
        
        //clear explosions
        removeExplosionsFromPane();
        
        //make explosion sprite for player
        createExplosionSprite(tank, npcX, npcY);
        
        
        //if health = 0, game over. Else remove the wall
        if (tankArray.get(0).getHealth() > 0){
        
            //remove the wall
            pane.getChildren().remove(rectangle);
            
            if (enableAbusiveMode){
                //Talk smack
                getRandomMessage();
            }

        }
        else {
            showGameOverScreen();
        }
    }

    //function to resolve npc hitting player. reduce health by 1, 
    //trigger game over if needed
    private static void playerHitNPCResolution(Tank tank){
        
        //get Tank's x and Y
        double npcX = tank.body.getX();
        double npcY = tank.body.getY();    
        
        //clear explosions
        removeExplosionsFromPane();
        
        //make explosion sprite for player
        createExplosionSprite(tank, npcX, npcY);
        
        //if health = 0, game over
        if (tankArray.get(0).getHealth() > 0){
            if (enableAbusiveMode){
                //Talk smack
                getRandomMessage();
            }
        }
        else {
            showGameOverScreen();
        }
    }    
    
    //Create explosion and add to array
    public static void createExplosionSprite(Tank tank, Double npcX, 
            Double npcY){
        kaboomArray.add(new Explosion(tank, npcX, npcY));
    }
    
    //place all explosion sprites in the array onto the pane
    public static void placeExplosionsOnPane(){
        for(Explosion e : kaboomArray){
            pane.getChildren().add(e.polygon);
        }
    }
    
    //clear all explosions for the pane and its array
    public static void removeExplosionsFromPane(){
        for(Explosion e : kaboomArray){
            pane.getChildren().remove(e.polygon);
        }

        kaboomArray.removeAll(kaboomArray);
    }
    
    //Reset level parameters
    private static void resetGameMode(){
        
        enableNormalMode = false;
        enableInvisibleMode = false;
        enableAbusiveMode = false;
        enableLudicrousMode = false;
        
        //reset levels
        previousLevel = 1;
        currentLevel = 1;
        
    }
    
    //Title Screen
    private static void showTitleScreen(){
        
        //clear pane
        clearPaneAndArrays();
        
        resetGameMode();
        
        VBox vBoxMain = new VBox();
        VBox vBoxOptionGroup01 = new VBox();
        VBox vBoxOptionGroup02 = new VBox();
        
        //picture in top
        Image image = new Image("TitleScreen.bmp");
        vBoxMain.getChildren().add(new ImageView(image));
        
        //options on the bottom
        RadioButton rbNormal = new RadioButton("Normal");
        RadioButton rbInvisibleMode = new RadioButton("Invisible Mode");
        RadioButton rbAbusiveMode = new RadioButton("Abusive Mode");
        RadioButton rbLudicrousMode = new RadioButton("Ludicrous Mode");
        //option font
        rbNormal.setFont(Font.font("System Regular", 18));
        rbInvisibleMode.setFont(Font.font("System Regular", 18));
        rbAbusiveMode.setFont(Font.font("System Regular", 18));
        rbLudicrousMode.setFont(Font.font("System Regular", 18));
        
        
        //Set tooltips
        //how-to learned from https://docs.oracle.com/javafx/2/ui_controls/tooltip.htm
        rbNormal.setTooltip(new Tooltip("A standard game."));
        rbInvisibleMode.setTooltip(new Tooltip("The standard game, but with a twist."));
        rbAbusiveMode.setTooltip(new Tooltip("For those who want an authentic" 
            + " online playing experience."));
        
        //group toggles
        ToggleGroup group = new ToggleGroup();
        rbNormal.setToggleGroup(group);
        rbInvisibleMode.setToggleGroup(group);
        rbAbusiveMode.setToggleGroup(group);
        rbLudicrousMode.setToggleGroup(group);
        
        vBoxOptionGroup01.setPadding(new Insets(8, 8, 8, 8));
        vBoxOptionGroup02.setPadding(new Insets(8, 8, 8, 8));
        vBoxOptionGroup01.getChildren().addAll(rbNormal, rbAbusiveMode); 
        vBoxOptionGroup02.getChildren().addAll(rbInvisibleMode, rbLudicrousMode);
        
        //label for option groups
        Label lblSelection = new Label("Select Game");
        lblSelection.setFont(Font.font("System Regular", 25));
        vBoxMain.getChildren().add(lblSelection);
        
        //vertical boxes in a grid pane, on the bottom main Vbox
        GridPane gridPane = new GridPane();
        gridPane.add(vBoxOptionGroup01, 0, 1); //col, row
        gridPane.add(vBoxOptionGroup02, 1, 1);
        gridPane.setAlignment(Pos.CENTER);
        vBoxMain.getChildren().add(gridPane);
        
        //Start button
        Button button = new Button("Start!");
        button.setPadding(new Insets(8, 20, 8, 20)); 
        vBoxMain.getChildren().add(new Label());
        vBoxMain.getChildren().add(button);
        
        vBoxMain.setAlignment(Pos.CENTER);
        pane.getChildren().add(vBoxMain);
        
        //button action
        button.setOnAction(e -> {
            if (rbNormal.isSelected()){
                //Start normal game. call instruction screen
                enableNormalMode = true;
                showInstructionsScreen();
            }
            else if (rbInvisibleMode.isSelected()){
                enableInvisibleMode = true;
                //call instruction screen
                showInstructionsScreen();
            }
            else if (rbAbusiveMode.isSelected()){
                //jump straight into the game
                enableAbusiveMode = true;
                Levels.gameLevels(currentLevel, pane);
            }
            else if (rbLudicrousMode.isSelected()){
                enableLudicrousMode = true;
                //call instruction screen
                showInstructionsScreen();
            }
        });
        
    }
    
    //mission start screen. give rundown of controls and flavor text
    private static void showInstructionsScreen(){
        
        //clear pane
        clearPaneAndArrays();
        
        VBox vBoxMain = new VBox();
        
        //Standard text
        String strFlavorText = "An army of slimes have invaded the planet! You "
                 + "have been tasked with stopping the jiggling menace. "
                + "Some slimes can damage the tank, especially the moving ones! "
                + "(Try not to bang up the tank too much. It is government "
                + "property, after all.) To victory!";
        String strControlsText = "Controls: W -- Move up; S -- Move "
                + "down; A -- Move left; D -- Move right; Space -- Fire; "
                + "1 - 3 -- Change Gears. (Ctrl+R will reset the level)";
        
        if (enableInvisibleMode){
            strFlavorText = "Uh oh! Looks like someone accidentally used the "
                    + "same paint on your tank that was used for Wonder Woman's "
                    + "jet. Ah well. (Note - shells are fired from the breech "
                    + "of a gun.)";          
        }
        else if (enableLudicrousMode){
            strFlavorText = "You know what to do.";
            strControlsText = "Controls: W -- Move forward";
        }
        
        //rundown of scenario on top
        TextArea flavorText = new TextArea();
        flavorText.setWrapText(true);
        flavorText.setEditable(false);
        flavorText.setFont(Font.font("Consolas", 25));
        flavorText.setText(strFlavorText);
        
        //controls on the bottom
        TextArea controlsText = new TextArea(strControlsText);
        controlsText.setWrapText(true);
        controlsText.setEditable(false);
        controlsText.setFont(Font.font("Courier New", 20));
        
        //Buttons
        Button buttonOk = new Button("Ok!");
        buttonOk.setPadding(new Insets(8, 20, 8, 20));
        Button buttonYeahYeah = new Button("Yeah, yeah...");
        buttonYeahYeah.setPadding(new Insets(8, 20, 8, 20));
        Button buttonBack = new Button("< Back");
        buttonBack.setPadding(new Insets(8, 20, 8, 20));
        
        //buttons to gridpane
        GridPane gridPaneButtons = new GridPane();
        gridPaneButtons.add(buttonBack, 0, 0); //col, row
        gridPaneButtons.add(new Label("   "), 1, 0);
        gridPaneButtons.add(new Label("   "), 2, 0);
        gridPaneButtons.add(new Label("   "), 3, 0);
        gridPaneButtons.add(buttonOk, 4, 0); //col, row
        if (enableInvisibleMode || enableLudicrousMode){
            gridPaneButtons.add(new Label("   "), 5, 0);
            gridPaneButtons.add(new Label("   "), 6, 0);
            gridPaneButtons.add(new Label("   "), 7, 0);
            gridPaneButtons.add(buttonYeahYeah, 8, 0);
        }
        
        GridPane gridPaneFlavor = new GridPane();
        GridPane gridPaneControls = new GridPane();
        gridPaneFlavor.add(flavorText, 0, 0);
        gridPaneControls.add(controlsText, 0, 0);
        
        gridPaneButtons.setAlignment(Pos.CENTER);
        gridPaneFlavor.setAlignment(Pos.CENTER);
        gridPaneControls.setAlignment(Pos.CENTER);
        
        //Blank banner on top to center the other panes
        Image image = new Image("BlankHeader.png");
        
        vBoxMain.getChildren().addAll(new ImageView(image), gridPaneFlavor, 
                gridPaneControls, gridPaneButtons);
        
        
        pane.setStyle("-fx-background-color: white;");
        pane.getChildren().add(vBoxMain);
        
        //Button actions
        buttonOk.setOnAction(e -> {
            Levels.gameLevels(currentLevel, pane);
        });
        buttonYeahYeah.setOnAction(e -> {
            Levels.gameLevels(currentLevel, pane);
        });
        buttonBack.setOnAction(e -> {
            showTitleScreen();
        });
        
    }
    
    //Player win screens
    static void showPlayerWinScreen(){
        
        //clear pane
        clearPaneAndArrays();
        
        StackPane stackPane = new StackPane();
        
        if (enableAbusiveMode){
            Image image = new Image("AbusiveModeWin.bmp");
            pane.setStyle("-fx-background-color: white;");
            stackPane.getChildren().add(new ImageView(image));
        }
        else{
            Image image = new Image("PlayerWin.bmp");
            pane.setStyle("-fx-background-color: white;");
            stackPane.getChildren().add(new ImageView(image));
        }
       
        pane.getChildren().add((stackPane));
        
        pane.getChildren().add(((addButtonForMainTitleScreen(500, 740)))); //Bottom center
        
    }
    
    //Game over screens
    private static void showGameOverScreen(){
        
        Image image = new Image("NormalGameOver.png");
        
        //clear pane
        clearPaneAndArrays();
        
        VBox vBoxMain = new VBox();
        //StackPane stackPane = new StackPane();
        
        pane.setStyle("-fx-background-color: white;");
        
        //picture in top
        if (enableAbusiveMode){
            vBoxMain.setAlignment(Pos.CENTER);
            vBoxMain.getChildren().add(new ImageView(new Image("AbusiveModeGameOver_02.jpg")));
            vBoxMain.getChildren().add(new ImageView(new Image("BlankHeaderSmall.png")));
            pane.getChildren().add(vBoxMain);
            pane.getChildren().add((addButtonForRetryLevel(400, 755)));//Bottom right
            pane.getChildren().add((addButtonForMainTitleScreen(580, 755)));//Bottom right
            
        }
        else if (enableLudicrousMode){
            image = new Image("LudicrousModeGameOver.png");
            //stackPane.getChildren().add(new ImageView(image));
            pane.getChildren().add(new ImageView(image));
        }
        else{
            pane.getChildren().add(new ImageView(image));
            pane.getChildren().add((addButtonForRetryLevel(400, 740)));//Bottom right
            pane.getChildren().add((addButtonForMainTitleScreen(580, 740)));//Bottom middle
        } 
    }
    
    //button to go back to the main title screen
    private static Button addButtonForMainTitleScreen(double x, double y){
        
        Button buttonMainScreen = new Button("Back to Title Screen");
        buttonMainScreen.relocate(x, y);  
        buttonMainScreen.setPadding(new Insets(8, 20, 8, 20));
        
        buttonMainScreen.setOnAction(e -> {
            showTitleScreen();
        });
        
        return buttonMainScreen;
    }
    
    //button to retry the level
    private static Button addButtonForRetryLevel(double x, double y){
        
        Button buttonMainScreen = new Button("Retry?");
        buttonMainScreen.relocate(x, y);  
        buttonMainScreen.setPadding(new Insets(8, 20, 8, 20));
        
        buttonMainScreen.setOnAction(e -> {
            Levels.gameLevels(currentLevel, pane);
        });
        
        return buttonMainScreen;
    }
    
    //level complete screen
    private static void showLevelCompleteScene(){
        
        //Clear pane
        clearPaneAndArrays();
        
        Label lblMessage = new Label(); 
        String strLblMessage = "Level Complete!";
        String strBttnMessage = "Forward!";
        
        StackPane spMessage = new StackPane();
        Button button = new Button();
        
        StackPane spButton = new StackPane(button);
        
        lblMessage.setFont(Font.font("Stencil", 140));
        
        if (enableAbusiveMode){
            String[] randoMessage = {"Pathetic.",
                                    "Useless.",
                                    "Worthless."};
            strLblMessage = randoMessage[(int)(Math.random() * randoMessage.length)];
            
            strBttnMessage = "   .....   ";
            
            lblMessage.setFont(Font.font("OCR A Extended", 170));
        }
        
        lblMessage.setText(strLblMessage);
        button.setText(strBttnMessage);
        
        VBox vbox = new VBox();
        
        Insets insets = new Insets(30, 30, 30, 30); //T, R, B, L
        button.setPadding(insets);
        
        //Add message
        spMessage.getChildren().add(lblMessage);
        
        vbox.getChildren().addAll(spMessage, new Label(), new Label(), 
                new Label(), new Label(), spButton);

        pane.getChildren().add(vbox);

        button.setOnAction(e -> {
            //call next level
            Levels.gameLevels(currentLevel, pane);
        });
        
    }
    
    //Encourage the player.
    static void getRandomMessage(){
        
        //String array of text
        String[] abusiveMessagees = {"plz uninstall",
            "you suck",
            "omg",
            "get gud",
            "lol"};

        //generate random number with range of 0 to array size
        strAbusiveMessage = abusiveMessagees[(int)(Math.random() * abusiveMessagees.length)];
        
        updateLabels();
    }
}



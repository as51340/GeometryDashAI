package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.image.Image;

public abstract class Obstacle {

    private int height;
    private int width;
    private int positionX;
    private int positionY;
    private int velocityX;
    private Image icon;

    /**
     * Moves obstacles to the left at a steady pace
     * Should be called at each update
     */
    public void calculateNewPosition(){
        this.positionX= (int) (positionX - velocityX * GameConstants.timeBetweenUpdates);
    }

    /**
     * Draws the position of the obstacle
     */
    public abstract void drawTheirPosition();

    public boolean checkCollisions(){
        //TODO ovaj method
        //daj sve tocke koje taj obstacle ima
        //pogledaj dal se poklapaju s nekim od playera
        //ako da return true
        //inace reutrn false
        return false;
    }

    //TODO ovu klasu
    // public abstract ________ getAllPoints
    //trebala bi vracati sve točke koje taj obstacle zauzima



}

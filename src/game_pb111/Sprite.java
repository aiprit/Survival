package game_pb111;



import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;


/**
 * Class to store mob units
 * 
 * @author Parit Burintrathikul
 */

public class Sprite {
	
  
	public Circle myCircle;
    public Node node;
    public double speedX = 0;
    public double speedY = 0;
    public boolean isDead = false;
    public Circle collisionBounds;
    
    /**constructor
     * 
     * @param size
     * @param circlecolor
     */
    public Sprite(double size, Color circlecolor){
    	myCircle = new Circle(size,circlecolor);

	}
    /**update circle position
     * 
     */
    public void update() {
    	 myCircle.setTranslateX(myCircle.getTranslateX() + speedX);
    	 myCircle.setTranslateY(myCircle.getTranslateY() + speedY);
  
    }

    /**collision wall check for circles
     * 
     * @param myScene
     */
    public void collisionwall(Scene myScene) {
    	if(myCircle.getTranslateX() > myScene.getWidth()- myCircle.getBoundsInLocal().getWidth()/2)
			speedX = -1*speedX;
		if(myCircle.getTranslateX() < myCircle.getBoundsInLocal().getWidth()/2)
			speedX = -1*speedX;
		if(myCircle.getTranslateY()> myScene.getHeight()- myCircle.getBoundsInLocal().getHeight()/2)
			speedY = -1*speedY;
		if(myCircle.getTranslateY() < myCircle.getBoundsInLocal().getHeight()/2)
			speedY = -1*speedY;
    }
    
    /**getter
     * 
     * @return
     */
    public Circle getCircle(){
    	return myCircle;
    }

    /**setter
     * 
     * @param life
     */
	public void setlife(boolean life) {
		isDead = life;
	}
	
	/**getter
	 * 
	 * @return
	 */
	public boolean getlife() {
		return isDead;
	}
}

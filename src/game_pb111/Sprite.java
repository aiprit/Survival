package game_pb111;



import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;


public class Sprite {
	
  
	public Circle myCircle;
	
	
    public Node node;

    public double speedX = 0;

    public double speedY = 0;

    public boolean isDead = false;

    public Circle collisionBounds;
    
    public Sprite(double size, Color circlecolor){
    	myCircle = new Circle(size,circlecolor);

	}
 
    public void update() {
    	 myCircle.setTranslateX(myCircle.getTranslateX() + speedX);
    	 myCircle.setTranslateY(myCircle.getTranslateY() + speedY);
  
    }

    /**
     * Did this sprite collide into the other sprite?
     *
     * @param other - The other sprite.
     * @return boolean - Whether this or the other sprite collided, otherwise false.
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
    public Circle getCircle(){
    	return myCircle;
    }
//    public void handleDeath(RunGame RunGame) {
//        RunGame.getSpriteManager().addSpritesToBeRemoved(this);
//    }

	


	public void setlife(boolean life) {
		isDead = life;
	}

	public boolean getlife() {
		// TODO Auto-generated method stub
		return isDead;
	}
}

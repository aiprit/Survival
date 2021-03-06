// This entire file is part of my masterpiece.
// Parit Burintrathikul

package game_pb111;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for Boss character, similar to Player but with its own set of mechanics
 * 
 * @author Parit Burintrathikul
 */


public class Boss {

	private ImageView myBoss;
	private double BossspeedY;;
	private double BossspeedX;
	private boolean BossisDead = false;
	private int mysize = 0;
	
	/**
	 * constructor
	 * @param root
	 * @param width
	 * @param height
	 * @param size
	 */
	public void generateboss(Group root,int width, int height, int size){
		myBoss = new ImageView( new Image(getClass().getClassLoader().getResourceAsStream("gooboss.png")));
		mysize = size;
		myBoss.setFitHeight(size);
		myBoss.setPreserveRatio(true);		
		myBoss.setX(width  - myBoss.getBoundsInParent().getWidth() / 2);
		myBoss.setY(height/2  - myBoss.getBoundsInParent().getHeight() / 2);
		root.getChildren().add(myBoss);
	}
	/**
	 * Boss movement
	 * @param myPlayer
	 */
	public void Bossmove(Player myPlayer){
		BossspeedX = ((myPlayer.getPlayerImg().getX()-myBoss.getX()>0)? 0.5:-0.5) ;
		BossspeedY = ((myPlayer.getPlayerImg().getY()-myBoss.getY()>0)? 0.5:-0.5) ;
		myBoss.setX(myBoss.getX() + BossspeedX);
		myBoss.setY(myBoss.getY() + BossspeedY);
		
	}
	/**
	 * Boss collision vs player
	 * @param myPlayer
	 */
	public void collisionBoss(Player myPlayer){
		if (myPlayer.getPlayerImg().getBoundsInParent().intersects(myBoss.getBoundsInParent())) {
			if(myBoss.getFitHeight() > myPlayer.getPlayerImg().getBoundsInParent().getHeight()*1.2){
				myPlayer.makedead(true); 
			}
			else{
				if(!BossisDead){
				BossisDead = true;
				myPlayer.getPlayerImg().setFitHeight(myPlayer.getPlayerImg().getFitHeight()+myBoss.getFitHeight()/4);
				myPlayer.getPlayerImg().setPreserveRatio(true);
				myPlayer.getPlayerImg().setSmooth(true);
				
				}
			}
		}
	}
	
	/**death Boss animation
	 * 
	 * @param frame
	 */
	public void deathboss(int frame, Group root) {
		ImageView deadframe1 = new ImageView( new Image(getClass().getClassLoader().getResourceAsStream("Deathboss"+frame/8+".png")));
		root.getChildren().remove(myBoss);
		double X = myBoss.getX();
		double Y = myBoss.getY();
		myBoss = deadframe1;
		myBoss.setFitHeight(mysize-frame*3);
		myBoss.setPreserveRatio(true);
		myBoss.setX(X);
		myBoss.setY(Y);
		root.getChildren().add(myBoss);

	}
	
	/**getter
	 * 
	 * @return
	 */
	public double getY() {
		return myBoss.getY();
	}
	
	/**getter
	 * 
	 * @return
	 */
	public double getX() {
		return myBoss.getX();
	}
	
	/**getter
	 * 
	 * @return
	 */
	public boolean Bossdead(){
		return BossisDead;
	}
	
	/**getter
	 * 
	 * @return
	 */
	public ImageView getBoss(){
		return myBoss;
	}
	

}

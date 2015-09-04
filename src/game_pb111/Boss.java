package game_pb111;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boss {

	private ImageView myBoss;
	private double BossspeedY;;
	private double BossspeedX;
	private boolean BossisDead = false;
	private double BossY;
	private double BossX;
	public void generateboss(Group root,int width, int height, int size){
		myBoss = new ImageView( new Image(getClass().getClassLoader().getResourceAsStream("gooboss.png")));
		myBoss.setFitHeight(size);
		myBoss.setPreserveRatio(true);		
		myBoss.setX(width  - myBoss.getBoundsInParent().getWidth() / 2);
		myBoss.setY(height/2  - myBoss.getBoundsInParent().getHeight() / 2);
		root.getChildren().add(myBoss);
	}
	public void Bossmove(Player myPlayer){
		BossspeedX = ((myPlayer.getPlayerImg().getX()-myBoss.getX()>0)? 0.5:-0.5) ;
		BossspeedY = ((myPlayer.getPlayerImg().getY()-myBoss.getY()>0)? 0.5:-0.5) ;
		myBoss.setX(myBoss.getX() + BossspeedX);
		myBoss.setY(myBoss.getY() + BossspeedY);
		
	}
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
	public double getY() {
		return myBoss.getY();
	}
	public double getX() {
		return myBoss.getX();
	}
	public boolean Bossdead(){
		return BossisDead;
	}
	public void deathanimation(Group root) {
		root.getChildren().remove(myBoss);
		
	}
}

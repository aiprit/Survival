package game_pb111;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/**
 * Player with collision/movement and details on the unit
 * 
 * @author Parit Burintrathikul
 */
public class Player {
	ImageView myPlayerimg;
	private double myPlayerspeedY = 0;
	private double myPlayerspeedX = 0;
	private boolean isDead;

	/**constructor
	 * 
	 * @param size
	 * @param width
	 * @param height
	 */
	public Player(double size, int width, int height){
		Image image1 = new Image(getClass().getClassLoader().getResourceAsStream("gooplayer.gif"));
		isDead = false;
		myPlayerimg = new ImageView(image1);
		myPlayerimg.setFitHeight(size);
		myPlayerimg.setPreserveRatio(true);
		myPlayerimg.setX(20);
		myPlayerimg.setY(20);
	}


	/**
	 * getter
	 * @return
	 */
	public ImageView getPlayerImg(){
		return myPlayerimg;

	}

	/**
	 * getter
	 * @return
	 */
	public boolean checkDead(){
		return isDead;
	}
	
	/**setter
	 * 
	 * @param bol
	 */
	public void makedead(boolean bol){
		isDead = bol;
	}
	
	/**getter
	 * 
	 * @return
	 */
	public double PlayergetY(){
		return myPlayerspeedY;
	}
	/**getter
	 * 
	 * @return
	 */
	public double PlayergetX(){
		return myPlayerspeedX;
	}
	/**
	 * Player check collision on wall
	 * @param myScene
	 */
	public void Playercollisionwall(Scene myScene){
		if(myPlayerimg.getX() > myScene.getWidth()- myPlayerimg.getBoundsInLocal().getWidth())
			myPlayerspeedX = -1*myPlayerspeedX;
		if(myPlayerimg.getX() < 0)
			myPlayerspeedX = -1*myPlayerspeedX;
		if(myPlayerimg.getY()> myScene.getHeight()- myPlayerimg.getBoundsInLocal().getHeight())
			myPlayerspeedY = -1*myPlayerspeedY;
		if(myPlayerimg.getY() < 0)
			myPlayerspeedY = -1*myPlayerspeedY;
	}

	/**
	 * What to do each time a key is pressed
	 * @param code
	 * @param value speed control
	 * @param myScene
	 */
	public void handleKeyInput (KeyCode code, double value,Scene myScene) {
		switch (code) {
		case RIGHT:
			myPlayerspeedX=myPlayerspeedX + value;
			if(myPlayerspeedY<0)
				myPlayerspeedY=myPlayerspeedY + value/2;
			else if (myPlayerspeedY>0)
				myPlayerspeedY=myPlayerspeedY - value/2;
			break;

		case LEFT:
			myPlayerspeedX=myPlayerspeedX - value ;
			if(myPlayerspeedY<0)
				myPlayerspeedY=myPlayerspeedY + value/2;
			else if (myPlayerspeedY>0)
				myPlayerspeedY=myPlayerspeedY - value/2;
			break;
		case UP:
			myPlayerspeedY=myPlayerspeedY - value;
			if(myPlayerspeedX<0)
				myPlayerspeedX=myPlayerspeedX + value/2;
			else if (myPlayerspeedX>0)
				myPlayerspeedX=myPlayerspeedX - value/2 ;
			break;
		case DOWN:
			myPlayerspeedY=myPlayerspeedY + value;
			if(myPlayerspeedX<0)
				myPlayerspeedX=myPlayerspeedX + value/2;
			else if (myPlayerspeedX>0)
				myPlayerspeedX=myPlayerspeedX - value/2 ;
			break;
		case EQUALS:
			myPlayerimg.setFitHeight(myPlayerimg.getFitHeight()+10);
			myPlayerimg.setPreserveRatio(true);
			myPlayerimg.setSmooth(true);
			checkoverwallexpand(myScene);
			break;
		case MINUS:
			myPlayerimg.setFitHeight(myPlayerimg.getFitHeight()-10);
			myPlayerimg.setPreserveRatio(true);
			myPlayerimg.setSmooth(true);
			break;

		default:
			// do nothing
		}
	}


	/**
	 * player movement with step
	 * @param myScene
	 */
	public void playermovement(Scene myScene) {
		myPlayerimg.setX(myPlayerimg.getX() + myPlayerspeedX);
		myPlayerimg.setY(myPlayerimg.getY() + myPlayerspeedY);
		Playercollisionwall(myScene);
	}
	/**collision vs sprites
	 * 
	 * @param sprite
	 * @param myScene
	 */
	public void collisionPlayer(Sprite sprite,Scene myScene) {
		if (sprite.getCircle().getBoundsInParent().intersects(myPlayerimg.getBoundsInParent())) {
			if(myPlayerimg.getFitHeight() > sprite.getCircle().getBoundsInLocal().getHeight()*0.8){
				myPlayerimg.setFitHeight(myPlayerimg.getFitHeight()+sprite.getCircle().getBoundsInLocal().getHeight()/4);
				myPlayerimg.setPreserveRatio(true);
				myPlayerimg.setSmooth(true);
				checkoverwallexpand(myScene);
				sprite.setlife(true); 
			}
			else{
				isDead = true;

			}
		}
	}
	/**check wall clipping
	 * 
	 * @param myScene
	 */
	private void checkoverwallexpand(Scene myScene) {
		if( myPlayerimg.getX()+myPlayerimg.getBoundsInLocal().getWidth()>myScene.getWidth())
			myPlayerimg.setX(myScene.getWidth()-myPlayerimg.getBoundsInLocal().getWidth());
		if( myPlayerimg.getY()+myPlayerimg.getBoundsInLocal().getHeight()>myScene.getHeight())
			myPlayerimg.setY(myScene.getHeight()-myPlayerimg.getBoundsInLocal().getHeight());
		if( myPlayerimg.getX()+myPlayerimg.getBoundsInLocal().getWidth()<0)
			myPlayerimg.setX(0);
		if( myPlayerimg.getY()+myPlayerimg.getBoundsInLocal().getHeight()<0)
			myPlayerimg.setY(0);
	}
	
	/**death player animation
	 * 
	 * @param frame
	 */
	public void deathanimation(int frame,Group root) {
		ImageView deadframe = new ImageView( new Image(getClass().getClassLoader().getResourceAsStream("Deathanimation"+frame/8+".png")));
		root.getChildren().remove(myPlayerimg);
		double X = myPlayerimg.getX();
		double Y = myPlayerimg.getY();
		myPlayerimg = deadframe;
		myPlayerimg.setX(X);
		myPlayerimg.setY(Y);
		root.getChildren().add(myPlayerimg);

	}


}

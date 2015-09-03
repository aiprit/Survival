package game_pb111;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Player {
	ImageView myPlayerimg;
	private double myPlayerspeedY = 0;
	private double myPlayerspeedX = 0;
	private boolean isDead = false;
	private double myY;
	private double myX;
	
	
	public Player(double size, int width, int height){
		Image image1 = new Image(getClass().getClassLoader().getResourceAsStream("gooplayer.gif"));
		myPlayerimg = new ImageView(image1);
		myPlayerimg.setFitHeight(size);
		myPlayerimg.setPreserveRatio(true);
		myPlayerimg.setSmooth(true);
		myPlayerimg.setX(width / 2 - myPlayerimg.getBoundsInLocal().getWidth() / 2);
		myPlayerimg.setY(height / 2 - myPlayerimg.getBoundsInLocal().getHeight() / 2);
	}
	
	public void setImage(ImageView newImage){
		myPlayerimg = newImage;
		myPlayerimg.setX(myX);
		myPlayerimg.setY(myY);
	}
	public ImageView getPlayerImg(){
		return myPlayerimg;
		
	}
	public boolean checkDead(){
		return isDead;
	}
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

	//What to do each time a key is pressed
	public void handleKeyInput (KeyCode code, double value) {
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
		default:
			// do nothing
		}
	}
	public double PlayergetY(){
		return myPlayerspeedY;
	}
	public double PlayergetX(){
		return myPlayerspeedX;
	}

	public void setY(double Y) {
		 myPlayerimg.setY(Y);
		 myY = Y;
	}

	public void setX(double X) {
		myPlayerimg.setX(X);
		myX = X;
	}
	public void collisionPlayer(Sprite sprite) {
		if (sprite.getCircle().getBoundsInParent().intersects(myPlayerimg.getBoundsInParent())) {
			if(myPlayerimg.getFitHeight() > sprite.getCircle().getBoundsInLocal().getHeight()){
				myPlayerimg.setFitHeight(myPlayerimg.getFitHeight()+sprite.getCircle().getBoundsInLocal().getHeight()/4);
				myPlayerimg.setPreserveRatio(true);
				myPlayerimg.setSmooth(true);
				sprite.setlife(true); 
			}
			else{
				isDead = true;
			}


		}
	}


	
}

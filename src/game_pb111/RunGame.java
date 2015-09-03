package game_pb111;
import java.util.List;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import game_pb111.Sprite;
import game_pb111.SpriteMain;



class RunGame {
	public  final String TITLE = "Survival";


	private Group root = new Group();
	private Scene myScene;
	private Player myPlayer;
	private int myframe = 1 ;
	private SpriteMain manager = new SpriteMain();


	/**
	 * Returns name of the game.
	 */
	public String getTitle () {
		return TITLE;
	}

	/**
	 * Create the game's scene
	 */
	public Scene init (int width, int height) {

		myScene = new Scene(root, width, height, Color.BLACK);

		myPlayer = new Player(30,width,height);

		root.getChildren().add(myPlayer.getPlayerImg());
		generatemob(30,1,root);
		// Respond to input
		 myScene.setOnKeyPressed(e -> myPlayer.handleKeyInput(e.getCode(),0.5));
		return myScene;
	}




	/**
	 * Change properties of shapes to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes,
	 * but these simple ways work too.
	 */
	public void step (double elapsedTime) {


		myPlayer.setX(myPlayer.getPlayerImg().getX() + myPlayer.PlayergetX());
		myPlayer.setY(myPlayer.getPlayerImg().getY() + myPlayer.PlayergetY());
		myPlayer.Playercollisionwall(myScene);
		updateSprites(elapsedTime);
		handledeath();
		gamecondition();


	}
	private void gameover() {
	

	}
	private void gamecondition(){
		if(myPlayer.checkDead()){
			if(myframe == 70)
				gameover();
			else if(myframe%10 ==0)
				deathanimation(myframe);
			else
				myframe++;
		}
		if(manager.getAllSprites().isEmpty())
			wincondition();
		
	}
	private void wincondition() {
		// TODO Auto-generated method stub
		
	}

	private void handledeath() {
		for (Sprite sprite:manager.getAllSprites()){
			if(sprite.getlife()){
				manager.addSpritesToBeRemoved(sprite);
				root.getChildren().remove(sprite.getCircle());
			}
		}
		manager.cleanupSprites();

	}


	private void deathanimation(int frame) {
		ImageView deadframe = new ImageView( new Image(getClass().getClassLoader().getResourceAsStream("Deathanimation"+frame/10+".png")));
		root.getChildren().remove(myPlayer.getPlayerImg());
		myPlayer.setImage(deadframe);
		root.getChildren().add(myPlayer.getPlayerImg());
		myframe++;

	}

	private void updateSprites(double time) {
		for (Sprite sprite:manager.getAllSprites()){
			sprite.update();
			sprite.collisionwall( myScene);

			myPlayer.collisionPlayer(sprite);

		}
	}


	private void generatemob(int nummob, int level,Group root){
		Random rnd = new Random();
		int S = 10;
		int M = 30;
		int L = 50;
		int XL = 70;
		int XXL = 100;
		formspheres(nummob/2, root, rnd,S);
//		formspheres(nummob/4, root, rnd,M);
//		formspheres(nummob/6, root, rnd,L);
//		formspheres(nummob/8, root, rnd,XL);
		formspheres(1, root, rnd,XXL);


	}

	private void formspheres(int nummob, Group root, Random rnd, int size) {
		for (int i=0; i<nummob; i++) {
			Color c = Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));

			Sprite b = new Sprite(size, c);
			Circle circle = b.getCircle();
			// random 0 to 2 + (.0 to 1) * random (1 or -1)
			b.speedX = (rnd.nextInt(1)* rnd.nextInt(1) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1);
			b.speedY = (rnd.nextInt(1)* rnd.nextInt(1) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1);

			// random x between 0 to width of scene
			double newX = rnd.nextInt((int) myScene.getWidth());

			// check for the right of the width newX is greater than width 
			// minus radius times 2(width of sprite)
			if (newX > (myScene.getWidth() - (circle.getRadius() * 2))) {
				newX = myScene.getWidth() - (circle.getRadius()  * 2);
			}

			// check for the bottom of screen the height newY is greater than height 
			// minus radius times 2(height of sprite)
			double newY = rnd.nextInt((int) myScene.getHeight());
			if (newY > (myScene.getHeight() - (circle.getRadius() * 2))) {
				newY = myScene.getHeight() - (circle.getRadius() * 2);
			}

			circle.setTranslateX(newX);
			circle.setTranslateY(newY);
			circle.setVisible(true);
			circle.setId(b.toString());
			manager.addSprites(b);
			root.getChildren().add(circle);
		}
	}

}

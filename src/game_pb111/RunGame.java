package game_pb111;

import java.util.Random;


import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import game_pb111.Sprite;
import game_pb111.SpriteMain;

/**
 * Main Engine with all the level info and buildings as well as its initialization
 * 
 * @author Parit Burintrathikul
 */

class RunGame {
	public  final String TITLE = "Survival";
	private Group root = new Group();
	private Scene myScene;
	private Player myPlayer;
	private int myframe = 1 ;
	private SpriteMain manager = new SpriteMain();
	private Stage myStage;
	private int level = 1;
	private boolean freeze = false;
	private Boss myBoss;
	private int time =0;
	private double speed = 1.0;
	
	//Title
	public String getTitle () {

		return TITLE;
	}
	//Constructor to pass on Stage
	public RunGame(Stage s){
		myStage = s;
	}

/**
 * startmenu starts the splash menu and init the enter button for new game
 */
	public Scene startmenu(int width,int height){
		myScene = new Scene(root, width, height, Color.BLACK);
		Settitle("Title",2,3);
		Settitle("start",2,1.5);
		myScene.setOnKeyPressed(e -> newgame(e.getCode(),level));
		freeze = true;
		return myScene;

	}
/**
 * init starts new game on the first level, create mobs,player and set controls
  * 
  * @param width size of screen set from main
  * @param height size of screen set from main
  * @return
  */
	public Scene init (int width, int height) {
		freeze = false;
		myScene = new Scene(root, width, height, Color.BLACK);
		myPlayer = new Player(30,width,height);
		root.getChildren().add(myPlayer.getPlayerImg());
		generatemob(30,1,root);
		myScene.setOnKeyPressed(e -> myPlayer.handleKeyInput(e.getCode(),0.5,myScene));
		return myScene;
	}

/**starts level 2, similar to init creates boss, player and boss shooter, set controls
 * 
 * @param width size of screen set from main
 * @param height size of screen set from main
 * @return
 */
	public Scene initboss (int width, int height) {
		freeze = false;
		root = new Group();
		myScene = new Scene(root, width, height, Color.BLACK);
		myPlayer = new Player(30,width,height);
		root.getChildren().add(myPlayer.getPlayerImg());
		myBoss = new Boss();
		myBoss.generateboss(root,width,height, 150);
		myScene.setOnKeyPressed(e -> myPlayer.handleKeyInput(e.getCode(),0.5,myScene));
		return myScene;
	}

/**steps through timeline, freezes on player death
 * 
 * @param elapsedTime
 */
	public void step (double elapsedTime) {
		time ++;

		if (!freeze){
			myPlayer.playermovement(myScene);
			updateSprites();
			handledeath();
			gamecondition();
			if(level!=1){
				if(!myBoss.Bossdead()){
					myBoss.Bossmove(myPlayer);
					myBoss.collisionBoss(myPlayer);
					if (time%100 == 0){
						speed += 0.2;
						generateshots(root, new Random(),speed);

					}
				}
			}
		}
	}

	/**generate shots that the boss shoots at you 50-50 big/small shots, the speed increases over time
	 * 
	 * @param root Group
	 * @param rnd Random number
	 * @param speed of the shots
	 */
	private void generateshots(Group root, Random rnd,double speed ) {
		Color c = Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
		Sprite b = new Sprite((rnd.nextBoolean() ? 70 : 10), c);
		Circle circle = b.getCircle();
		int valX = ((myPlayer.getPlayerImg().getX()-myBoss.getX()>0)? 2:-2);
		int valY = ((myPlayer.getPlayerImg().getY()-myBoss.getY()>0)? 2:-2);
		double ratioX = ((myPlayer.getPlayerImg().getX()-myBoss.getX())/(myPlayer.getPlayerImg().getY()-myBoss.getY()));
		double ratioY = 1;
		if(ratioX>1){
			ratioY = (1/ratioX);
			ratioX = 1;
		}
		if(ratioX<1){
			ratioY = -(1/ratioX);
			ratioX = 1;
		}
		b.speedX = ratioX*valX ;
		b.speedY = ratioY*valY ;
		circle.setTranslateX(myBoss.getX());
		circle.setTranslateY(myBoss.getY());
		circle.setVisible(true);
		circle.setId(b.toString());
		manager.addSprites(b);
		root.getChildren().add(circle);
	}

	/**checks win/lose/level conditions and do death animations
	 * 
	 */
	private void gamecondition(){

		if(myPlayer.checkDead()){
			if(myframe == 60){
				gameover();
			}
			else if(myframe%8 ==0)
				deathanimation(myframe);
			else
				myframe++;
		}
		if( level==1){
			if(manager.getAllSprites().isEmpty())
				nextlevelblock();
		}
		if(level!=1){
			if(myBoss.Bossdead()){
				if(myframe == 60){
					wincondition();
				}
				else if(myframe%8 ==0)
					deathboss(myframe);
				else
					myframe++;
			
					
			}
		}

	}
	
	/**Key set for level 1 game or level 2 game
	 * 
	 * @param code
	 * @param part
	 */
	private void newgame(KeyCode code,int part) {
		switch (code) {
		case ENTER:
			if(part <3){
			spritecleanall();
			root = new Group();
			level=1;
			Scene scene = init(1028,720);
			myframe = 1;
			myStage.setScene(scene);
			myStage.show();
			
			}
			break;
		case SPACE:
			if (part>1){
			spritecleanall();
			root = new Group();
			level = 2;
			Scene scene2 = initboss(1028,720); 
			myframe = 1;
			myStage.setScene(scene2);
			myStage.show();
			
			}
			break;
		default:
			break;

		}
	}

	/**set any splashes
	 * 
	 * @param name name of file (assume is png currently)
	 * @param xval position in scene
	 * @param yval ""
	 */
	private void Settitle(String name,double xval, double yval){
		ImageView title = new ImageView( new Image(getClass().getClassLoader().getResourceAsStream(name+".png")));
		title.setX(myScene.getWidth() / xval - title.getBoundsInLocal().getWidth() / 2);
		title.setY(myScene.getHeight() / yval - title.getBoundsInLocal().getHeight() / 2);
		root.getChildren().add(title);
	}

	/**set splashes + key on win level 2
	 * 
	 */
	private void wincondition() {
		Settitle("wingame",2,3);
		Settitle("playagain",2,1.5);
		Settitle("spacetorestart",2,1.2);
		myScene.setOnKeyPressed(e -> newgame(e.getCode(),2));
	}
	
	/**set splashes + key on win level 1
	 * 
	 */
	private void nextlevelblock() {
		freeze = true;
		Settitle("completed1",2,3);
		Settitle("continue",2,1.5);
		Settitle("entertorestart",2,1.2);
		myScene.setOnKeyPressed(e -> newgame(e.getCode(),2));

	}
	
	/**set splashes + key on lose both levels
	 * 
	 */
	private void gameover() {
		freeze = true;
		Settitle("gameover",2,3);
		Settitle("entertorestart",2,1.5);
		if (level >1)
			Settitle("spacetorestart",2,1.2);
		myScene.setOnKeyPressed(e -> newgame(e.getCode(),level));

	}
	
	/**Sprite wipe with each scene
	 * 
	 */
	private void spritecleanall(){
		for (Sprite sprite:manager.getAllSprites()){
			manager.addSpritesToBeRemoved(sprite);
			root.getChildren().remove(sprite.getCircle());
		}
		manager.cleanupSprites();

	}
	
	/**handle Sprite death on collisions
	 * 
	 */
	private void handledeath() {
		for (Sprite sprite:manager.getAllSprites()){
			if(sprite.getlife()){
				manager.addSpritesToBeRemoved(sprite);
				root.getChildren().remove(sprite.getCircle());
			}
		}
		manager.cleanupSprites();

	}

	/**death player animation
	 * 
	 * @param frame
	 */
	private void deathanimation(int frame) {
		ImageView deadframe = new ImageView( new Image(getClass().getClassLoader().getResourceAsStream("Deathanimation"+frame/8+".png")));
		root.getChildren().remove(myPlayer.getPlayerImg());
		myPlayer.setImage(deadframe);
		root.getChildren().add(myPlayer.getPlayerImg());
		myframe++;

	}
	
	/**death Boss animation
	 * 
	 * @param frame
	 */
	private void deathboss(int frame) {
		ImageView deadframe1 = new ImageView( new Image(getClass().getClassLoader().getResourceAsStream("Deathboss"+frame/8+".png")));
		root.getChildren().remove(myBoss.getBoss());
		myBoss.setBoss(deadframe1,150-frame*3);
		root.getChildren().add(myBoss.getBoss());
		myframe++;

	}
	
	/**update collision sprite vs player/wall
	 * 
	 */
	private void updateSprites() {
		for (Sprite sprite:manager.getAllSprites()){
			sprite.update();
			if(level ==1){
				sprite.collisionwall( myScene);
			}
			myPlayer.collisionPlayer(sprite,myScene);

		}
	}

	/**generate level 1 mobs can be used to build further levels if want to with ratio size difficulty
	 * 
	 * @param nummob number to spawn
	 * @param level	difficulty (not implemented)
	 * @param root
	 */
	 
	private void generatemob(int nummob, int level,Group root){
		Random rnd = new Random();
		int S = 10;
		int M = 30;
		int L = 50;
		int XL = 70;
		int XXL = 100;
		formspheres(nummob/2, root, rnd,S);
		formspheres(nummob/4, root, rnd,M);
		formspheres(nummob/6, root, rnd,L);
		formspheres(nummob/8, root, rnd,XL);
		formspheres(1, root, rnd,XXL);


	}

	/**helper function to create circles
	 * 
	 * @param nummob
	 * @param root
	 * @param rnd
	 * @param size
	 */
	private void formspheres(int nummob, Group root, Random rnd, int size) {
		for (int i=0; i<nummob; i++) {
			Color c = Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));

			Sprite b = new Sprite(size, c);
			Circle circle = b.getCircle();
			// random 0 to 2 + (.0 to 1) * random (1 or -1)
			b.speedX = (rnd.nextInt(1)* rnd.nextInt(1) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1);
			b.speedY = (rnd.nextInt(1)* rnd.nextInt(1) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1);
			double newX = rnd.nextInt((int) myScene.getWidth());
			if (newX > (myScene.getWidth() - (circle.getRadius() * 2))) {
				newX = myScene.getWidth() - (circle.getRadius()  * 2);
			}
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

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



class RunGame {
	public  final String TITLE = "Survival";
	private Group root = new Group();
	private Scene myScene;
	private Player myPlayer;
	private int myframe = 1 ;
	private SpriteMain manager = new SpriteMain();
	private Stage myStage;
	private boolean level1 = true;
	private boolean freeze = false;
	private Boss myBoss = new Boss();
	private int time =0;
	private double speed = 1.0;
	public String getTitle () {

		return TITLE;
	}

	public RunGame(Stage s){
		myStage = s;
	}


	public Scene startmenu(int width,int height){
		myScene = new Scene(root, width, height, Color.BLACK);
		Settitle("Title",2,3);
		Settitle("start",2,1.5);
		myScene.setOnKeyPressed(e -> newgame(e.getCode(),0.5));
		freeze = true;
		return myScene;

	}
	public Scene init (int width, int height) {
		freeze = false;
		myScene = new Scene(root, width, height, Color.BLACK);
		myPlayer = new Player(30,width,height);
		root.getChildren().add(myPlayer.getPlayerImg());
		generatemob(30,1,root);
		myScene.setOnKeyPressed(e -> myPlayer.handleKeyInput(e.getCode(),0.5,myScene));
		return myScene;
	}


	public Scene initboss (int width, int height) {
		freeze = false;
		root = new Group();
		myScene = new Scene(root, width, height, Color.BLACK);
		myPlayer = new Player(30,width,height);
		root.getChildren().add(myPlayer.getPlayerImg());
		myBoss.generateboss(root,width,height, 150);
		myScene.setOnKeyPressed(e -> myPlayer.handleKeyInput(e.getCode(),0.5,myScene));
		return myScene;
	}








	public void step (double elapsedTime) {
		time ++;

		if (!freeze){
			playermovement();
			updateSprites();
			handledeath();
			gamecondition();
			if(!level1){
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

	private void playermovement() {
		myPlayer.setX(myPlayer.getPlayerImg().getX() + myPlayer.PlayergetX());
		myPlayer.setY(myPlayer.getPlayerImg().getY() + myPlayer.PlayergetY());
		myPlayer.Playercollisionwall(myScene);
	}

	private void gamecondition(){

		if(myPlayer.checkDead()){
			if(myframe == 70){
				gameover();
			}
			else if(myframe%10 ==0)
				deathanimation(myframe);
			else
				myframe++;
		}
		if( level1){
			if(manager.getAllSprites().isEmpty())
				nextlevelblock();
		}
		if(!level1){
			if(myBoss.Bossdead()){
				myBoss.deathanimation(root);
				wincondition();
			}
		}

	}
	private void nextlevelblock() {
		freeze = true;
		Settitle("completed1",2,3);
		Settitle("continue",2,1.5);
		myScene.setOnKeyPressed(e -> nextlevel(e.getCode(),0.5));

	}

	private void nextlevel(KeyCode code, double d) {
		switch (code) {
		case SPACE:
			spritecleanall();
			level1 = false;
			root = new Group();
			Scene scene2 = initboss(1028,720); 
			myStage.setScene(scene2);
			myStage.show();
			break;
		default:
			break;
		}
	}
	private void gameover() {
		freeze = true;
	
		Settitle("gameover",2,3);
		Settitle("tryagain",2,1.5);
		myScene.setOnKeyPressed(e -> newgame(e.getCode(),0.5));

	}
	private void newgame(KeyCode code, double d) {
		switch (code) {
		case ENTER:
			spritecleanall();
			root = new Group();
			level1 = true;
			Scene scene = init(1028,720);
			myframe = 1;
			myStage.setScene(scene);
			myStage.show();
			break;
		default:
			break;

		}
	}

	private void Settitle(String name,double xval, double yval){
		ImageView title = new ImageView( new Image(getClass().getClassLoader().getResourceAsStream(name+".png")));
		title.setX(myScene.getWidth() / xval - title.getBoundsInLocal().getWidth() / 2);
		title.setY(myScene.getHeight() / yval - title.getBoundsInLocal().getHeight() / 2);
		root.getChildren().add(title);
	}

	private void wincondition() {
		Settitle("wingame",2,3);
		Settitle("playagain",2,1.5);
	}
	private void spritecleanall(){
		for (Sprite sprite:manager.getAllSprites()){
			manager.addSpritesToBeRemoved(sprite);
			root.getChildren().remove(sprite.getCircle());
		}
		manager.cleanupSprites();

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

	private void updateSprites() {
		for (Sprite sprite:manager.getAllSprites()){
			sprite.update();
			if(level1){
				sprite.collisionwall( myScene);
			}
			myPlayer.collisionPlayer(sprite,myScene);

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
		formspheres(nummob/4, root, rnd,M);
		formspheres(nummob/6, root, rnd,L);
		formspheres(nummob/8, root, rnd,XL);
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

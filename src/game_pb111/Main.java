package game_pb111;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main class to run and initialize application
 * 
 * @author Parit Burintrathikul
 */

public class Main extends Application {

    private int fps = 60;
    private RunGame myGame;

    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
        // create your own game here
 
        myGame = new RunGame(s);
        s.setTitle(myGame.getTitle());
        int MILLISECOND_DELAY = 1000 / fps;
        double SECOND_DELAY = 1.0 / fps;
        // attach game to the stage and display it
        Scene scene = myGame.startmenu(1028,720);
        s.setScene(scene);
        s.show();
        
        // sets the game's loop
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> myGame.step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
       
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

	
}

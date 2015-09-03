package game_pb111;

import java.util.*;

public class SpriteMain {
	   /** All the sprite objects currently in play */
    private  List<Sprite> GAME_ACTORS = new ArrayList<>();
    
    /** A global single threaded set used to cleanup or remove sprite objects
     * in play.
     */
    private  Set<Sprite> CLEAN_UP_SPRITES = new HashSet<>();
    
    /** */
    public  List<Sprite> getAllSprites() {
        return GAME_ACTORS;
    }
     
    /**
     * VarArgs of sprite objects to be added to the game.
     * @param sprites 
     */
    public void addSprites(Sprite... sprites) {       
        GAME_ACTORS.addAll(Arrays.asList(sprites));
    }
    
    /**
     * VarArgs of sprite objects to be removed from the game.
     * @param sprites 
     */
    public void removeSprites(Sprite... sprites) {       
        GAME_ACTORS.removeAll(Arrays.asList(sprites));
    }
    
    /** Returns a set of sprite objects to be removed from the GAME_ACTORS. 
     * @return CLEAN_UP_SPRITES
     */    
    public Set<Sprite> getSpritesToBeRemoved() {
        return CLEAN_UP_SPRITES;
    }
    
 /**
     * Adds sprite objects to be removed
     * @param sprites varargs of sprite objects.
     */
    public void addSpritesToBeRemoved(Sprite... sprites) {
        if (sprites.length > 1) {
            CLEAN_UP_SPRITES.addAll(Arrays.asList((Sprite[]) sprites));
        } else {
            CLEAN_UP_SPRITES.add(sprites[0]);
        }
    }

    /**
     * Returns a list of sprite objects to assist in collision checks.
     * This is a temporary and is a copy of all current sprite objects
     * (copy of GAME_ACTORS).
     * @return CHECK_COLLISION_LIST
     */


    /**
     * Removes sprite objects and nodes from all
     * temporary collections such as:
     * CLEAN_UP_SPRITES.
     * The sprite to be removed will also be removed from the
     * list of all sprite objects called (GAME_ACTORS).
     */
    public void cleanupSprites() {

        // remove from actors list
        GAME_ACTORS.removeAll(CLEAN_UP_SPRITES);

        // reset the clean up sprites
        CLEAN_UP_SPRITES.clear();
    }
}


package game_pb111;

import java.util.*;

public class SpriteMain {
	   /** All the sprite objects currently in play */
    private  List<Sprite> AllSprites = new ArrayList<>();
 
    private  Set<Sprite> Spritestokill = new HashSet<>();
    
    /** */
    public  List<Sprite> getAllSprites() {
        return AllSprites;
    }
     
    /**
     * VarArgs of sprite objects to be added to the game.
     * @param sprites 
     */
    public void addSprites(Sprite... sprites) {       
    	AllSprites.addAll(Arrays.asList(sprites));
    }
    
    /**
     * VarArgs of sprite objects to be removed from the game.
     * @param sprites 
     */
 
    
    /** Returns a set of sprite objects to be removed from the GAME_ACTORS. 
     * @return CLEAN_UP_SPRITES
     */    
    public Set<Sprite> getSpritesToBeRemoved() {
        return Spritestokill;
    }
    
 /**
     * Adds sprite objects to be removed
     * @param sprites varargs of sprite objects.
     */
    public void addSpritesToBeRemoved(Sprite... sprites) {
        if (sprites.length > 1) {
        	Spritestokill.addAll(Arrays.asList((Sprite[]) sprites));
        } else {
        	Spritestokill.add(sprites[0]);
        }
    }
  

    public void cleanupSprites() {

        // remove from actors list
    	AllSprites.removeAll(Spritestokill);

        // reset the clean up sprites
        Spritestokill.clear();
    }
}


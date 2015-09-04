package game_pb111;

import java.util.*;


/**
 * Manager class for the sprite circles. Deal with add and removing and keeping 
 * them in order
 * 
 * @author Parit Burintrathikul
 */
public class SpriteMain {

	private  List<Sprite> AllSprites = new ArrayList<>();
	private  Set<Sprite> Spritestokill = new HashSet<>();

	/** as named
	 * 
	 * @return
	 */
	public  List<Sprite> getAllSprites() {
		return AllSprites;
	}

	/**add circles
	 * 
	 * @param sprites
	 */
	public void addSprites(Sprite... sprites) {       
		AllSprites.addAll(Arrays.asList(sprites));
	}

	/**get list to remove
	 * 
	 * @return
	 */
	public Set<Sprite> getSpritesToBeRemoved() {
		return Spritestokill;
	}

	/**add to list to remove
	 * 
	 * @param sprites
	 */
	public void addSpritesToBeRemoved(Sprite... sprites) {
		if (sprites.length > 1) {
			Spritestokill.addAll(Arrays.asList((Sprite[]) sprites));
		} else {
			Spritestokill.add(sprites[0]);
		}
	}

	/**remove circles as in list
	 * 
	 */
	public void cleanupSprites() {
		AllSprites.removeAll(Spritestokill);
		Spritestokill.clear();
	}
}


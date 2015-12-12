package sound;

import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioManager {

	private static Random random = new Random();
	
	public AudioManager() {}

	public static void playClip(String filename) {
		try {
			Sound sound = new Sound("res/" + filename);
			sound.play();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void playClipRandomPitch(String filename) {
		try {
			Sound sound = new Sound("res/" + filename);
			float pitch = 1.0f;
			sound.play(pitch, 0.8f);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void playLoop(String filename) {
		try {
			Sound sound = new Sound("res/" + filename);
			sound.loop();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}

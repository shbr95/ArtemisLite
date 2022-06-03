package artemislite;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * @author curti
 *
 */
public class Sound {

	private boolean startSound = true;

	/**
	 * This version works best, can simply call method and it works
	 */
	public void playLevelUpSound() {

		File file = new File("Level Up Sound Effect.wav");

		if (file.exists()) {

			AudioInputStream audioStream;
			Clip clip;
			try {
				audioStream = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(audioStream);

				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				// gainControl.setValue(-150.0f); // Reduce sound by 10 decibels
				float range = gainControl.getMaximum() - gainControl.getMinimum();
				float gain = (range * 0.8f) + gainControl.getMinimum(); // 80% volume
				gainControl.setValue(gain);

				clip.start();

				// clip.loop(Clip.LOOP_CONTINUOUSLY);
				// Thread.sleep(1550); // 1.55 seconds, works for short audio clip, don't need
				// to put thread to sleep!
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}

		} else {
			System.out.println("Cant find audio file");
		}

	}

	public void playDiceRollSound() {

		File file = new File("diceRoll.wav");

		if (file.exists()) {

			AudioInputStream audioStream;
			Clip clip;
			try {
				audioStream = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(audioStream);
				/*
				 * FloatControl gainControl = (FloatControl)
				 * clip.getControl(FloatControl.Type.MASTER_GAIN);
				 * //gainControl.setValue(-150.0f); // Reduce sound by 10 decibels float range =
				 * gainControl.getMaximum() - gainControl.getMinimum(); float gain = (range *
				 * 0.8f) + gainControl.getMinimum(); // 80% volume gainControl.setValue(gain);
				 */
				clip.start();

				// clip.loop(Clip.LOOP_CONTINUOUSLY);
				// Thread.sleep(1550); // 1.55 seconds, works for short audio clip, don't need
				// to put thread to sleep!
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}

		} else {
			System.out.println("Cant find audio file");
		}

	}

	public void playGameOverSound() {

		File file = new File("gameOver.wav");

		if (file.exists()) {

			AudioInputStream audioStream;
			Clip clip;
			try {
				audioStream = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(audioStream);
				/*
				 * FloatControl gainControl = (FloatControl)
				 * clip.getControl(FloatControl.Type.MASTER_GAIN);
				 * //gainControl.setValue(-150.0f); // Reduce sound by 10 decibels float range =
				 * gainControl.getMaximum() - gainControl.getMinimum(); float gain = (range *
				 * 0.8f) + gainControl.getMinimum(); // 80% volume gainControl.setValue(gain);
				 */
				clip.start();

				// clip.loop(Clip.LOOP_CONTINUOUSLY);
				Thread.sleep(2500); // enough time to hear audio
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e1) {
				e1.printStackTrace();
			}

		} else {
			System.out.println("Cant find audio file");
		}

	}

	public void playDeductResourceSound() {

		File file = new File("deductResourceWAV.wav");

		if (file.exists()) {

			AudioInputStream audioStream;
			Clip clip;
			try {
				audioStream = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(audioStream);
				/*
				 * FloatControl gainControl = (FloatControl)
				 * clip.getControl(FloatControl.Type.MASTER_GAIN);
				 * //gainControl.setValue(-150.0f); // Reduce sound by 10 decibels float range =
				 * gainControl.getMaximum() - gainControl.getMinimum(); float gain = (range *
				 * 0.8f) + gainControl.getMinimum(); // 80% volume gainControl.setValue(gain);
				 */
				clip.start();

				// clip.loop(Clip.LOOP_CONTINUOUSLY);
				Thread.sleep(2500); // enough time to hear audio
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e1) {
				e1.printStackTrace();
			}

		} else {
			System.out.println("Cant find audio file");
		}

	}

	/**
	 * This method works, but will put thread to sleep, not allowing anything to be
	 * done while audio is playing
	 */
	public void playBackgroundMusic() {

		File file = new File("background.wav");

		if (file.exists()) {

			AudioInputStream audioStream;
			Clip clip;
			try {
				audioStream = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				Thread.sleep((long) 1.5e+6); // 25 minutes
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e1) {
				e1.printStackTrace();
			}

		} else {
			System.out.println("Cant find audio file");
		}

	}

	/**
	 * THIS ONE This does not use thread.sleep as unable to do anything else, this
	 * may be able to be used in conjunction with playGame, but unsure
	 */
	public void playBackgroundMusicNoSleep() {

		File file = new File("backgroundMusicWAV.wav");

		if (file.exists()) {

			AudioInputStream audioStream;
			Clip clip;

			

				try {
					audioStream = AudioSystem.getAudioInputStream(file);
					clip = AudioSystem.getClip();
					if(startSound) {
						
						clip.open(audioStream);
						FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
						// gainControl.setValue(-150.0f); // Reduce sound by 10 decibels
						float range = gainControl.getMaximum() - gainControl.getMinimum();
						float gain = (range * 0.6f) + gainControl.getMinimum(); // 60% volume
						gainControl.setValue(gain);
						clip.start();
						clip.loop(Clip.LOOP_CONTINUOUSLY);
						
					} else {
						clip.close();
					}
					
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}

			

		} else {
			System.out.println("Cant find audio file");
		}

	}

	/**
	 * This method allows the user to pause, resume, restart and stop audio This
	 * could simply be turned into a switch statement to allow for different user
	 * input Could be given as an option after a while of playing the game
	 */
	public void playBackgroundMusicPauseResumeRestartStop() {

		File file = new File("backgroundMusic.mp3");

		if (file.exists()) {

			AudioInputStream audioStream;
			Clip clip;
			try {
				audioStream = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY); // loops audio

				JOptionPane.showMessageDialog(null, "Press OK to pause");
				long clipCurrentTime = clip.getMicrosecondPosition();
				clip.stop();

				JOptionPane.showMessageDialog(null, "Press OK to resume");
				clip.setMicrosecondPosition(clipCurrentTime);
				clip.start();

				JOptionPane.showMessageDialog(null, "Press OK to restart");
				clip.setMicrosecondPosition(0);
				clip.start();

				JOptionPane.showMessageDialog(null, "Press OK to stop playing");

			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}

		} else {
			System.out.println("Cant find audio file");
		}

	}

	// other method

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException      this also works but needs multiple
	 *                                       try/catches in code where method is
	 *                                       called
	 */

	public void levelUpSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		File file = new File("Level Up Sound Effect.wav");

		if (file.exists()) {

			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);

			clip.start();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Cant find audio file");
		}
	}

	/**
	 * @param startSound the startSound to set
	 */
	public void setStartSound(boolean startSound) {
		this.startSound = startSound;
	}
}

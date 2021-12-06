package uet.oop.bomberman.sound;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    long currentFrame = 0;

    private Clip clip;

    public Sound(File path) {
        try {
            AudioInputStream ais;
            ais = AudioSystem.getAudioInputStream(path);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            this.currentFrame = this.clip.getMicrosecondPosition();
        }
    }

    public void resume() {
        if (clip != null) {
            stop();
            clip.setMicrosecondPosition(currentFrame);
            clip.start();
        }
    }

    public void play() {
        if (clip != null) {
            stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void playBackground() {
        if (clip != null) {
            stop();
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
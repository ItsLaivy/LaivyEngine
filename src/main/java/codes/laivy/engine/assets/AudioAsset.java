package codes.laivy.engine.assets;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AudioAsset extends Asset {

    private final @NotNull Clip clip;

    public AudioAsset(@NotNull URL url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);

        clip = AudioSystem.getClip();
        clip.open(audioStream);

        audioStream.close();
    }
    public AudioAsset(@NotNull ResourceFile file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

        clip = AudioSystem.getClip();
        clip.open(audioStream);

        audioStream.close();
    }
    public AudioAsset(@NotNull InputStream stream) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(stream);

        clip = AudioSystem.getClip();
        clip.open(audioStream);

        audioStream.close();
        stream.close();
    }
    public AudioAsset(@NotNull Clip clip) {
        this.clip = clip;
    }

    @Contract(pure = true)
    public @NotNull Clip getClip() {
        return clip;
    }

    public void loop(@Range(from = 1, to = Integer.MAX_VALUE) int count) {
        clip.loop(count - 1);
    }
    public void play() {
        clip.start();
    }
    public void stop() {
        clip.stop();
    }
    public boolean isPlaying() {
        return clip.isRunning();
    }

    @Override
    public void dispose() {
        stop();

        super.dispose();
        clip.close();
    }
}

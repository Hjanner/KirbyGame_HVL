package KirbyGame_HVL.git.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {

    // Atributos
    private static Music soundTrack;

    // Constructor
    public MusicManager () {
    }

    // Iniciamos la musica
    public static void play () {
        if (soundTrack == null) {
            soundTrack = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/starforge-saga-281379.mp3"));
            soundTrack.setVolume(0.3f);
            soundTrack.setLooping(true);
            soundTrack.play();
        }
    }

    // Detenemos la musica
    public static void stop () {
        soundTrack.stop();
        soundTrack.dispose();
        soundTrack = null;
    }
}

package cuadrado.villar.hadrian.arkanoid.CControl;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import cuadrado.villar.hadrian.arkanoid.R;

/**
 * Clase donde se cargan los distintos archivos de música y asi podemos tratar con esta.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Audio {
    /**
     * Efectos sonoros.
     */
    SoundPool efectos;
    /**
     * Sonidos (Canciones, etc).
     */
    public MediaPlayer mediaPlayer;
    /**
     * Controlador del audio.
     */
    AudioManager audioManager;
    /**
     * Numero máxmio de sonidos simultaneos.
     */
    int maxSonidosSimultaneos;


    /**
     * Constructor de clase.
     * @param contexto Contexto de la aplicación.
     * @param maxSonidosSimultaneos Especifica el número máximo de sonidos emitiendose en el mismo momento.
     */

    //TODO: Implementar esta clase para mayor modularidad

    public Audio(Context contexto, int maxSonidosSimultaneos) {
        this.maxSonidosSimultaneos = maxSonidosSimultaneos;
        audioManager = (AudioManager) contexto.getSystemService(Context.AUDIO_SERVICE);

        //Musica de fondo (canción)
        mediaPlayer = MediaPlayer.create(contexto, R.raw.cancionmenu);
        mediaPlayer = MediaPlayer.create(contexto, R.raw.canciongameplay);


        int v = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(v / 2, v / 2);
        mediaPlayer.setLooping(true);

        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSonidosSimultaneos);
            this.efectos = spb.build();
        } else {
            this.efectos = new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }

        //Quito efectos, porque me fallan en la app.
    }
}

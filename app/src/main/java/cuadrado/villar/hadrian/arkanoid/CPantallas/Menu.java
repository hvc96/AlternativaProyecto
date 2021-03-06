package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;


import cuadrado.villar.hadrian.arkanoid.CControl.Escena;
import cuadrado.villar.hadrian.arkanoid.R;

/**
 * Pantalla Menú, se gestiona el control de escenas de la aplicación.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Menu extends Escena {
    /**
     * Imagen de fondo.
     */
    Bitmap fondo;
    /**
     * Rectangulos donde escribimos las distintas opciones.
     */
    Rect ayuda, opciones, juego, records, creditos;
    /**
     * Fragmento del alto total de la pantalla.
     */
    int alto;
    /**
     * Fragmento del ancho total de la pantalla.
     */
    int ancho;
    /**
     * Textos almacenados para el uso de cambio de idiomas.
     */
    String jugartxt, ayudatxt, opcionestxt, recordstxt, creditostxt;
    /**
     * Fuente para escribir los textos.
     */
    Paint p;
    /**
     * Canción de menu.
     */
    public MediaPlayer mediaPlayer;

    /**
     * Constructor de clase.
     * @param context Contexto de la aplicación.
     * @param idEscena Define la pantalla en la que estamos.
     * @param anchoPantalla Ancho de la pantalla.
     * @param altoPantalla Alto de la pantalla.
     */
    public Menu(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        fondo = getBitmapFromAssets("Fondos/fondo.png");
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);

        alto = altoPantalla / 15;
        ancho = anchoPantalla / 10;

        jugartxt = context.getString(R.string.jugar);
        ayudatxt = context.getString(R.string.ayuda);
        opcionestxt = context.getString(R.string.opciones);
        recordstxt = context.getString(R.string.records);
        creditostxt = context.getString(R.string.creditos);

        juego = new Rect(ancho * 3, alto * 2, ancho * 7, alto * 4);
        ayuda = new Rect(ancho * 1, alto * 7, ancho * 9, alto * 8);
        opciones = new Rect(ancho * 1, alto * 9, ancho * 9, alto * 10);
        records = new Rect(ancho * 1, alto * 11, ancho * 9, alto * 12);
        creditos = new Rect(ancho * 1, alto * 13, ancho * 9, alto * 14);


        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(context, R.raw.cancionmenu);
        int v = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(v / 2 * 2, v / 2 * 2);
        mediaPlayer.setLooping(true);

        if (prefs.getBoolean("musica", true)) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }

        Typeface faw = Typeface.createFromAsset(context.getAssets(), "Fuentes/welton.TTF");
        p = new Paint();
        p.setTypeface(faw);
        p.setTextSize(getDp(70));
        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Método para la gestión de la pulsación en la pantalla.
     * @param event Evento ocasionado al tocar la pantalla.
     * @return Devuelve la escena en la que se encuentra.
     */
    public int onTouchEvent(MotionEvent event) {

        int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación
        switch (accion) {
            case MotionEvent.ACTION_DOWN:           // Primer dedo toca
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                if (pulsa(juego, event)) {
                    mediaPlayer.stop();
                    return 100;             //Juego         ->100
                } else if (pulsa(ayuda, event)) {
                    mediaPlayer.stop();
                    return 10;              //Ayuda         ->10
                } else if (pulsa(creditos, event)) {
                    mediaPlayer.stop();
                    return 20;              //Creditos      ->20
                }
                else if (pulsa(opciones, event)){
                    mediaPlayer.stop();
                    return 30;              //Opciones      ->30
                }
                else if (pulsa(records, event)) {
                    mediaPlayer.stop();
                    return 40;      //Records       ->40
                }
                break;

            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
        }
        return idEscena;
    }

    /**
     * Actualiza las físicas.
     */
    // Actualizamos la física de los elementos en pantalla
    public void actualizarFisica() {

    }

    /**
     * Método para el dibujado del jugador.
     *
     * @param c Objeto Canvas para utilizar los métodos útiles para el dibujo.
     */
    // Rutina de dibujo en el lienzo. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.LTGRAY);
            c.drawBitmap(fondo, 0, 0, null);
            super.dibujar(c);

            c.drawRect(juego, pMenu);
            c.drawCircle(ancho * 5, alto * 3, ancho * 3, pMenu);
            c.drawText(jugartxt, ancho * 5, juego.centerY() + (alto / 6), p);

            c.drawText(ayudatxt, ayuda.exactCenterX(), ayuda.centerY() + (alto / 2), p);

            c.drawText(opcionestxt, opciones.exactCenterX(), opciones.centerY() + (alto / 2), p);

            c.drawText(recordstxt, records.exactCenterX(), records.centerY() + (alto / 2), p);

            c.drawText(creditostxt, creditos.exactCenterX(), creditos.centerY() + (alto / 2), p);

        } catch (Exception e) {
        }
    }
}

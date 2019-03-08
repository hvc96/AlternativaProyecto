package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import cuadrado.villar.hadrian.arkanoid.CControl.Audio;
import cuadrado.villar.hadrian.arkanoid.CControl.BaseDatos;
import cuadrado.villar.hadrian.arkanoid.CControl.Escena;
import cuadrado.villar.hadrian.arkanoid.CJuego.Bola;
import cuadrado.villar.hadrian.arkanoid.CJuego.Jugador;
import cuadrado.villar.hadrian.arkanoid.CJuego.Ladrillo;
import cuadrado.villar.hadrian.arkanoid.R;

import static android.content.Context.SENSOR_SERVICE;

/**
 * <h1>Juego</h1>
 * Pantalla Juego, donde se produce el gameplay de la aplicación.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Juego extends Escena {
    float dedoCoordX, dedoCoordY;
    int movimiento, tiempoVibracion, vidas, pts, indice;
    boolean pulsandoIzquierda, pulsandoDerecha, moverGiroscopio, reseteado = false, perder = false, pplay, juegoPausado = false, ganar = false, vibracion;
    RectF izquierda, derecha;
    Rect botonPlayPause;
    Bola bola;
    long tiempo;
    Jugador jugador;
    Bitmap jugadorImagen1, bolaImagen, ladrilloImagenAmarillo, ladrilloImagenAmarilloRompiendo, vidaImagen, pauseImagen, playImagen;
    float velocidadJugador = 20, velocidadBolaX = 25, velocidadBolaY = 15;
    Paint pTextoblanco, pBarra, p;
    ArrayList<Ladrillo> ladrillos;
    Vibrator vibrador = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
    Sensor giroscopio;
    SensorManager sm;
    String perdertxt, ganartxt, query;
    Bitmap[] imagenes;
    Cursor c;
    MediaPlayer mediaPlayer;
    Audio audio;

    // Create a listener
    SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
        /**
         * Método que se ejecuta cuando el sensor detecta una rotación sobre el eje x (sensorEvent.values[0]).
         * @param sensorEvent Evento del sensor.
         */
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (moverGiroscopio) {
                float posXGiro = anchoPantalla / 2 - sensorEvent.values[0] * 100;
                if (posXGiro  >= 0 +jugador.imagen.getWidth()/2 && posXGiro <= anchoPantalla-jugador.imagen.getWidth()/2) {
                    jugador.moverJugadorGiroscopio(posXGiro);
                    jugador.actualizarRects();
                }
            }
        }

        /**
         * Método ejecutado cuando se cambia la precisión del sensor.
         * @param sensor Sensor.
         * @param i Precisión del sensor.
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    /**
     * Constructor de clase.
     * @param context Contexto de la aplicación.
     * @param idEscena Define la pantalla en la que estamos.
     * @param anchoPantalla Ancho de la pantalla.
     * @param altoPantalla Alto de la pantalla.
     */
    public Juego(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        imagenes = new Bitmap[2];

        Random rand = new Random();
        int n = rand.nextInt(8); // Dara un int aleatorio entre 0 y 8 incluidos

        indice = 0;
        pts = 0;
        perdertxt = context.getString(R.string.perder);
        ganartxt = context.getString(R.string.ganar);

        sm = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        giroscopio = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);

        // Register the listener
        sm.registerListener(gyroscopeSensorListener,
                giroscopio, SensorManager.SENSOR_DELAY_NORMAL);

        pTextoblanco = new Paint();
        pTextoblanco.setColor(Color.WHITE);
        pTextoblanco.setTextSize(getDp(40));
        pTextoblanco.setTextAlign(Paint.Align.CENTER);

        pBarra = new Paint();
        pBarra.setColor(Color.LTGRAY);

        vidas = 2;

        izquierda = new RectF(0, 0, anchoPantalla / 2, altoPantalla);
        derecha = new RectF(anchoPantalla / 2, 0, anchoPantalla, altoPantalla);

        jugadorImagen1 = getBitmapFromAssets("Jugador/jugador_moviendose_1.png");
        jugadorImagen1 = Bitmap.createScaledBitmap(jugadorImagen1, getDp(100), getDp(30), false);

        jugador = new Jugador(jugadorImagen1, anchoPantalla / 2 - jugadorImagen1.getWidth() / 2, altoPantalla - getDp(30), velocidadJugador, anchoPantalla);

        bolaImagen = getBitmapFromAssets("Bola/bola.png");
        bolaImagen = Bitmap.createScaledBitmap(bolaImagen, getDp(15), getDp(15), false);
        bola = new Bola(bolaImagen, anchoPantalla / 2, altoPantalla - getDp(55), velocidadBolaX, velocidadBolaY, altoPantalla);

        if (prefs.getBoolean("play", true)) {
            pplay = true;
        } else {
            pplay = false;
        }

        if (prefs.getBoolean("giroscopio", true)) {
            moverGiroscopio = true;
        } else {
            moverGiroscopio = false;
        }

        //Ladrillos normales

        ladrilloImagenAmarillo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_amarillo.png");
        ladrilloImagenAmarillo = Bitmap.createScaledBitmap(ladrilloImagenAmarillo, anchoPantalla / 5, altoPantalla / 30, false);

        //Ladrillos rompiendose
        ladrilloImagenAmarilloRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_amarillo_rompiendo.png");
        ladrilloImagenAmarilloRompiendo = Bitmap.createScaledBitmap(ladrilloImagenAmarilloRompiendo, anchoPantalla / 5, altoPantalla / 30, false);

        //Vida
        vidaImagen = getBitmapFromAssets("Jugador/Vida/vida.png");
        vidaImagen = Bitmap.createScaledBitmap(vidaImagen, altoPantalla / 20, altoPantalla / 20, false);

        //Play/Pause
        playImagen = getBitmapFromAssets("Botones/play.png");
        playImagen = Bitmap.createScaledBitmap(playImagen, altoPantalla / 16, altoPantalla / 16, false);
        pauseImagen = getBitmapFromAssets("Botones/pause.png");
        pauseImagen = Bitmap.createScaledBitmap(pauseImagen, altoPantalla / 16, altoPantalla / 16, false);
        botonPlayPause = new Rect(0, 0, playImagen.getWidth(), playImagen.getWidth());

        ladrillos = creaLadrillos(25);

        tiempo = System.currentTimeMillis();

        Typeface faw = Typeface.createFromAsset(context.getAssets(), "Fuentes/PoiretOne-Regular.ttf");
        p = new Paint();
        p.setTypeface(faw);
        p.setTextSize(getDp(60));
        p.setColor(Color.RED);
        p.setTextAlign(Paint.Align.CENTER);

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(context, R.raw.canciongameplay);
        int v = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(v / 2 * 2, v / 2 * 2);
        mediaPlayer.setLooping(true);

        if (prefs.getBoolean("musica", true)) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }

        if (prefs.getBoolean("vibracion", true)) {
            vibracion = true;
        } else {
            vibracion = false;
        }

    }

    /**
     * Actualiza las físicas.
     */
    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {
        if (!juegoPausado) {
            jugarOtraVez();

            for (int i = ladrillos.size() - 1; i >= 0; i--) {
                if (ladrillos.get(i).colisionaLadrillos(bola, ladrilloImagenAmarilloRompiendo)) {
//                bola.reverseXVelocity();
                    bola.reverseYVelocity();  //Ahora solo rebota hacia abajo, no tiene colisiones laterales
                    if (ladrillos.get(i).getNumImpactos() <= 0) {
                        ladrillos.remove(i);
                        pts++;
                    }
                    if (ladrillos.isEmpty()) {
                        audio.getEfectos().play(audio.sVictoria, 1, 1, 1, 0, 1);
                    }
                }
            }
            if (!moverGiroscopio) {
                switch (movimiento) {
                    case 1:
                        if (pulsandoIzquierda) {
                            jugador.moverJugador(1);
                            pulsandoDerecha = false;
                        }
                        break;
                    case 2:
                        if (pulsandoDerecha) {
                            jugador.moverJugador(2);
                            pulsandoIzquierda = false;
                        }
                        break;
                }
            }
                jugador.actualizarRects();

            bola.actualizarFisica();
            bola.limites(anchoPantalla);

            if (bola.getContenedor().top > altoPantalla / 2) bola.setRestaChoque(true);

            if (bola.getContenedor().intersect(jugador.ei)) {
                bola.setRestaChoque(true);
                Log.i("velei", " " + bola.getVelocidadX());
                if (bola.getVelocidadX() > 0) {
                    bola.reverseYVelocity();
                    bola.setVelocidadY(velocidadBolaY + 1);
                } else {
                    bola.reverseYVelocity();
                    bola.reverseXVelocity();
                }
            } else if (bola.getContenedor().intersect(jugador.ci)) {
                Log.i("velci", " " + bola.getVelocidadX());
                bola.reverseYVelocity();

            } else if (bola.getContenedor().intersect(jugador.centro)) {
                Log.i("velcentro", " " + bola.getVelocidadX());
                bola.reverseYVelocity();

            } else if (bola.getContenedor().intersect(jugador.cd)) {
                Log.i("velcd", " " + bola.getVelocidadX());
                bola.reverseYVelocity();

            } else if (bola.getContenedor().intersect(jugador.ed)) {
                Log.i("veled", " " + bola.getVelocidadX());
                if (bola.getVelocidadX() > 0) {
                    bola.reverseYVelocity();
                    bola.reverseXVelocity();
                } else {
                    bola.reverseYVelocity();
                    bola.setVelocidadY(velocidadBolaY + 1);
                }
            }

            if (vidas == 0) {
                perder = true;
                //audio.getEfectos().play(audio.sDerrota,1,1,1,0,1);
            }

            if (bola.getContenedor().bottom > altoPantalla) {
                perderVida();
                resetPosicion();
                reseteado = true;
                if (ganar||perder) {
//                sqldb.execSQL("INSERT INTO puntos(pts) VALUES()");

                }
            }
        }
    }

    /**
     * Método para el dibujado del jugador.
     *
     * @param c Objeto Canvas para utilizar los métodos útiles para el dibujo.
     */
    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLACK); // Establecemos un color de fondo. En este caso negro
            c.drawRect(0, 0, anchoPantalla, altoPantalla / 20 + getDp(20), pBarra);

            //Vidas
            c.drawBitmap(vidaImagen, anchoPantalla - vidaImagen.getWidth() - getDp(5), getDp(5), null);
            if (pplay) {
                c.drawBitmap(playImagen, getDp(5), getDp(5), null);
            } else {
                c.drawBitmap(pauseImagen, getDp(5), getDp(5), null);
            }
            c.drawText(vidas + "", anchoPantalla - vidaImagen.getWidth() * 2, altoPantalla / 20, pTextoblanco);

            jugador.dibujar(c);
            bola.dibujar(c);
            for (Ladrillo l : ladrillos) l.dibujar(c);

            if (!juegoPausado) {

                if (perder) {
                    c.drawColor(Color.BLACK);
                    c.drawText(perdertxt, anchoPantalla / 2, altoPantalla / 2, p);
                    c.drawText(pts + " pts", anchoPantalla / 2, altoPantalla / 2 + getDp(100), pTextoblanco);
                }

                if (ganar) {
                    c.drawColor(Color.BLACK);
                    c.drawText(ganartxt, anchoPantalla / 2, altoPantalla / 2, p);
                }
            }
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Método para la gestión de la pulsación en la pantalla.
     * @param event Evento ocasionado al tocar la pantalla.
     * @return Devuelve la escena en la que se encuentra.
     */
    public int onTouchEvent(MotionEvent event) {

        int accion = event.getActionMasked();   //Obtenemos el tipo de pulsación

        switch (accion) {
            case MotionEvent.ACTION_DOWN:           // Primer dedo toca
                if (!juegoPausado) {
                    dedoCoordX = event.getX();
                    dedoCoordY = event.getY();
                    if (izquierda.contains(dedoCoordX, dedoCoordY)) {
                        pulsandoIzquierda = true;
                        movimiento = 1;// Mover izquierda
                        jugador.moverJugador(1);
                    } else if (derecha.contains(dedoCoordX, dedoCoordY)) {
                        pulsandoDerecha = true;
                        movimiento = 2;// Mover derecha
                        jugador.moverJugador(2);
                    }
                }

                if (pulsa(botonPlayPause, event)) {
                    if (prefs.getBoolean("musica", true)) {
                        if (pplay) {
                            juegoPausado = true;
                            mediaPlayer.pause();
                        } else {
                            juegoPausado = false;
                            mediaPlayer.start();

                        }
                    }
                    if (perder || ganar) {
                        if (mediaPlayer.isPlaying() || mediaPlayer.isLooping())
                            mediaPlayer.stop();
                    }

                    pplay = !pplay;
                    editor.putBoolean("play", pplay);
                    editor.commit();
                }

                if (reseteado) {
                    resetVelocidad();
                    reseteado = false;
                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
                if (perder||ganar) return 0; //Vuelve al menu
                pulsandoIzquierda = false;
                pulsandoDerecha = false;
                break;
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                break;
            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        return -123;//No cambia porque no existe este caso
    }

    /**
     * Método para restar una vida.
     */
    public void perderVida() {
        if (vidas != 0) {
            vidas--;
            vibrar();
            perder = false;
        } else {
            perder = true;
            mediaPlayer.stop();
        }
    }

    /**
     * Método para el reset de la posicón del jugador y de la bola.
     */
    public void resetPosicion() {
        bola.posicion.x = anchoPantalla / 2;
        bola.posicion.y = altoPantalla - getDp(55);
        jugador.posicion.x = anchoPantalla / 2 - jugadorImagen1.getWidth() / 2;
        jugador.posicion.y = altoPantalla - getDp(30);
        jugador.actualizarRects();
        bola.setVelocidadX(0);
        bola.setVelocidadY(0);
        bola.actualizarRect();
    }

    /**
     * Método que te devuelve a la pantalla menú.
     * @return Devuelve el id de pantalla.
     */
    public int jugarOtraVez() {

        if (vidas < 1) {
            return 0; //Menu
        }
        return 100; //Pantalla juego
    }

    /**
     * Método para resetear la velocidad de la bola una vez reseteada la posición.
     */
    public void resetVelocidad() {
        if (vidas > 0) {
            bola.setVelocidadX(25);
            bola.setVelocidadY(15);
        }
    }

    /**
     * Método para la gestión de la vibración del dispositivo.
     */
    public void vibrar() {
        if (!vibracion) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrador.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrador.vibrate(tiempoVibracion);
            }
        }
    }

    /**
     * Método para la creación de los objetos ladrillo.
     * @param nMax Número máximo de ladrillos a crear.
     * @return Devuelve el ArrayList de Ladrillos.
     */
    public ArrayList<Ladrillo> creaLadrillos(int nMax) {
        ArrayList<Ladrillo> alLadrillos = new ArrayList<>();
        int cont = 0;
        int y = altoPantalla / 20, x = 0;
        for (int i = 0; i < nMax; i++) {
            if (cont % 5 == 0) {
                y += ladrilloImagenAmarillo.getHeight();
                x = 0;
            }
            Ladrillo ladrillo = new Ladrillo(x, y, ladrilloImagenAmarillo);
            alLadrillos.add(ladrillo);
            x += ladrilloImagenAmarillo.getWidth();
            cont++;
        }

        return alLadrillos;
    }

    /**
     * Método que se ejecuta para introducir la puntuación sobre la base de datos.
     */
    public void insertPuntuacion() {
        int maxId = 0;
        bd = new BaseDatos(getContext(), "puntos", null, 1);
        sqldb = bd.getWritableDatabase();
        query = "SELECT max(id )FROM scores";
        c = sqldb.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                maxId = c.getInt(0);
            } while (c.moveToNext());
        }
        ContentValues fila = new ContentValues();
        fila.put("id", maxId + 1);
        sqldb.insert("puntos", null, fila);
        c.close();
        sqldb.close();
    }

//    TODO: Imagenes aleatorias.
//    public void colocarImagenes(int n){
//        Ladrillo ladrillo= new Ladrillo(x, y, ladrilloImagenAmarillo);
//        Bitmap[] fotos= new Bitmap[2];
//        imagenes=ladrillo.coloresRandom(n);
//        fotos[0]=ladrillo.imagen;
//        fotos[1]=ladrillo.imagen;
//
//        imagenes[0]=Bitmap.createScaledBitmap(ladrillo.coloresRandom(n),anchoPantalla/5,altoPantalla/30,false);
//        imagenes[1]=Bitmap.createScaledBitmap(ladrillo.coloresRandom(n),anchoPantalla/5,altoPantalla/30,false);
//
//    }
}

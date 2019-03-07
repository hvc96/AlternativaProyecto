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
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collection;

import cuadrado.villar.hadrian.arkanoid.CControl.BaseDatos;
import cuadrado.villar.hadrian.arkanoid.CControl.Escena;
import cuadrado.villar.hadrian.arkanoid.CJuego.Bola;
import cuadrado.villar.hadrian.arkanoid.CJuego.Jugador;
import cuadrado.villar.hadrian.arkanoid.CJuego.Ladrillo;
import cuadrado.villar.hadrian.arkanoid.R;

import static android.content.Context.SENSOR_SERVICE;

public class Juego extends Escena {
    float dedoCoordX, dedoCoordY;
    int movimiento, tiempoVibracion, vidas, idEscenaJuego, pts, indice;
    boolean pulsandoIzquierda, pulsandoDerecha, moverDerechaGiroscopio, moverIzquierdaGiroscopio, reseteado = false, perder = false, pplay;
    RectF izquierda, derecha;
    Rect botonPlayPause;
    Bola bola;
    long tiempo;
    Jugador jugador;
    Bitmap jugadorImagen1, jugadorImagen2, jugadorImagen3, bolaImagen, ladrilloImagenAmarillo, ladrilloImagenAmarilloRompiendo, vidaImagen, pauseImagen, playImagen, imagenJugadorDinamica;
    float velocidadJugador = 10, velocidadBolaX = 25, velocidadBolaY = 15;
    Paint pTextoblanco, pBarra, p;
    ArrayList<Ladrillo> ladrillos;
    Collection<Bitmap> imagenesJugador;
    Vibrator vibrador = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
    Sensor giroscopio;
    SensorManager sm;
    String perdertxt, query;
    BaseDatos bd;
    SQLiteDatabase sqldb;
    Cursor c;

    // Create a listener
    SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            // More code goes here
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };


    public Juego(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        indice = 0;
        pts = 0;
        perdertxt = context.getString(R.string.perder);

        sm = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        giroscopio = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Register the listener
        sm.registerListener(gyroscopeSensorListener,
                giroscopio, SensorManager.SENSOR_DELAY_NORMAL);

        pTextoblanco = new Paint();
        pTextoblanco.setColor(Color.WHITE);
        pTextoblanco.setTextSize(getDp(40));

        pBarra = new Paint();
        pBarra.setColor(Color.LTGRAY);

        vidas = 2;

        izquierda = new RectF(0, 0, anchoPantalla / 2, altoPantalla);
        derecha = new RectF(anchoPantalla / 2, 0, anchoPantalla, altoPantalla);

        jugadorImagen1 = getBitmapFromAssets("Jugador/jugador_moviendose_1.png");
        jugadorImagen1 = Bitmap.createScaledBitmap(jugadorImagen1, getDp(100), getDp(30), false);
//        jugadorImagen2 = getBitmapFromAssets("Jugador/jugador_moviendose_2.png");
//        jugadorImagen2 = Bitmap.createScaledBitmap(jugadorImagen2, getDp(100), getDp(30), false);
//        jugadorImagen3 = getBitmapFromAssets("Jugador/jugador_moviendose_3.png");
//        jugadorImagen3 = Bitmap.createScaledBitmap(jugadorImagen3, getDp(100), getDp(30), false);

        jugador = new Jugador(jugadorImagen1, anchoPantalla / 2 - jugadorImagen1.getWidth() / 2, altoPantalla - getDp(30), velocidadJugador, anchoPantalla);

        bolaImagen = getBitmapFromAssets("Bola/bola.png");
        bolaImagen = Bitmap.createScaledBitmap(bolaImagen, getDp(15), getDp(15), false);
        bola = new Bola(bolaImagen, anchoPantalla / 2, altoPantalla - getDp(55), velocidadBolaX, velocidadBolaY, altoPantalla);

        if (prefs.getBoolean("play", true) == true) {
            pplay = true;
        } else {
            pplay = false;
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

        //imagenesJugador.addAll(getFrames(3,"Jugador","jugador_moviendose_",jugadorImagen1.getWidth()));
        //imagenJugadorDinamica=cambiaFrame(3,tiempo,100,indice);
    }


    public ArrayList<Ladrillo> creaLadrillos(int nMax) {
        ArrayList<Ladrillo> alLadrillos = new ArrayList<>();
        int cont = 0;
        // Empieza en 40 porque son para botones vidas etc
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

    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {
        jugarOtraVez();

        Log.i("giroscopio", "   izquierda " + moverIzquierdaGiroscopio + "   ----------    derecha " + moverDerechaGiroscopio);
        Log.i("tiempito", "es divisble?   " + (System.currentTimeMillis() - tiempo % 2 == 0) + "     " + System.currentTimeMillis() + "        " + tiempo + "          " + "wtf  " + (System.currentTimeMillis() - tiempo));
//        if (System.currentTimeMillis() - tiempo % 2 > 0) {
//            jugador.imagen = jugadorImagen1;
//            Log.i("entre1", "                   " + jugador.imagen);
//        } else if (System.currentTimeMillis() - tiempo % 5 > 0) {
//            jugador.imagen = jugadorImagen2;
//            Log.i("entre2", "        !!" + jugador.imagen);
//        } else {
//            jugador.imagen = jugadorImagen3;
//            Log.i("entre3", " " + jugador.imagen);
//        }


        for (int i = ladrillos.size() - 1; i >= 0; i--) {
            if (ladrillos.get(i).colisionaLadrillos(bola, ladrilloImagenAmarilloRompiendo)) {
//                bola.reverseXVelocity();
                bola.reverseYVelocity();  //Ahora solo rebota hacia abajo, no tiene colisiones laterales
                if (ladrillos.get(i).getNumImpactos() <= 0) {
                    ladrillos.remove(i);
                    pts++;
                }
            }
        }
        switch (movimiento) {
            case 1:
                if (pulsandoIzquierda || moverIzquierdaGiroscopio) {
                    jugador.moverJugador(1);
                    pulsandoDerecha = false;
//                    moverIzquierdaGiroscopio=false;
                }
                break;
            case 2:
                if (pulsandoDerecha || moverDerechaGiroscopio) {
                    jugador.moverJugador(2);
                    pulsandoIzquierda = false;
//                    moverDerechaGiroscopio=false;
                }
                break;
        }
        bola.actualizarFisica();
        bola.limites(anchoPantalla);

        if (jugador.ed.contains(bola.contenedor) && bola.contenedor.top == anchoPantalla) {
            bola.setVelocidadX(-15);
        }

        if (jugador.ei.contains(bola.contenedor) && bola.contenedor.left == 0) {
            bola.setVelocidadX(15);
        }

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
        }

        if (bola.getContenedor().bottom > altoPantalla) {
            perderVida();
            resetPosicion();
            reseteado = true;
            if (perder) {
//                sqldb.execSQL("INSERT INTO puntos(pts) VALUES()");
                idEscenaJuego = 0;
            } else {
                idEscenaJuego = 100;
            }
        }
    }

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
//            jugador.imagen.

            bola.dibujar(c);
            for (Ladrillo l : ladrillos) l.dibujar(c);

            if (perder) {
                c.drawColor(Color.BLACK);
                c.drawText(perdertxt, anchoPantalla / 2, altoPantalla / 2, p);
            }

        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    public int onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();        //Obtenemos el índice de la acción
        int pointerID = event.getPointerId(pointerIndex); //Obtenemos el Id del pointer asociado a la acción
        int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación
        //detectRotation(event);

        switch (accion) {
            case MotionEvent.ACTION_DOWN:           // Primer dedo toca
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

                if (pulsa(botonPlayPause, event)) {
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
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                pulsandoIzquierda = false;
                pulsandoDerecha = false;
                if (perder)
                    return 0; //Vuelve al menu
                break;
            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        return -123;//No cambia porque no existe este caso
    }


    public void perderVida() {
        if (vidas != 0) {
            vidas--;
            vibrar();
            perder = false;
        } else {
            perder = true;
        }
    }

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

    public int jugarOtraVez() {

        if (vidas < 1) {
            return 0; //Menu
        }
        return 100; //Pantalla juego
    }

    public void resetVelocidad() {
        if (vidas > 0) {
            bola.setVelocidadX(25);
            bola.setVelocidadY(15);
        }
    }

    public void vibrar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrador.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrador.vibrate(tiempoVibracion);
        }
    }

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

    public void detectRotation(SensorEvent event) {
        if (event.values[2] > 0.5f) { // anticlockwise
            //Mover izquierda
            moverIzquierdaGiroscopio = true;
            moverDerechaGiroscopio = false;
        } else if (event.values[2] < -0.5f) { // clockwise
            //Mover derecha
            moverDerechaGiroscopio = true;
            moverIzquierdaGiroscopio = false;
        }
    }
}

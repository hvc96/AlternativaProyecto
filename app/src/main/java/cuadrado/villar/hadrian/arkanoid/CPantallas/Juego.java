package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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

import cuadrado.villar.hadrian.arkanoid.CControl.Escena;
import cuadrado.villar.hadrian.arkanoid.CJuego.Bola;
import cuadrado.villar.hadrian.arkanoid.CJuego.Jugador;
import cuadrado.villar.hadrian.arkanoid.CJuego.Ladrillo;
import cuadrado.villar.hadrian.arkanoid.R;

import static android.content.Context.SENSOR_SERVICE;

public class Juego extends Escena {
    float dedoCoordX, dedoCoordY;
    int movimiento, tiempoVibracion, vidas, idEscenaJuego;
    boolean pulsandoIzquierda, pulsandoDerecha,moverDerechaGiroscopio,moverIzquierdaGiroscopio, reseteado = false, perder = false;
    RectF izquierda, derecha;
    Bola bola;
    long tiempo;
    Jugador jugador;
    Bitmap jugadorImagen1, jugadorImagen2, jugadorImagen3, bolaImagen, ladrilloImagenAmarillo, ladrilloImagenAzulOscuro, ladrilloImagenMarron, ladrilloImagenAzul, ladrilloImagenNaranja, ladrilloImagenOscuro, ladrilloImagenRojo, ladrilloImagenVerde, ladrilloImagenVerdeLima, ladrilloImagenVioleta, ladrilloImagenAmarilloRompiendo, ladrilloImagenAzulRompiendo, ladrilloImagenAzulOscuroRompiendo, ladrilloImagenMarronRompiendo, ladrilloImagenNaranjaRompiendo, ladrilloImagenOscuroRompiendo, ladrilloImagenRojoRompiendo, ladrilloImagenVerdeRompiendo, ladrilloImagenVerdeLimaRompiendo, ladrilloImagenVioletaRompiendo, vidaImagen;
    float velocidadJugador = 10, velocidadBolaX = 25, velocidadBolaY = 15;
    Paint pTextoblanco, pBarra;
    ArrayList<Ladrillo> ladrillos;
    Vibrator vibrador = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
    Sensor giroscopio;
    SensorManager sm;
    String perdertxt;

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

        perdertxt= context.getString(R.string.perder);

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
        jugadorImagen2 = getBitmapFromAssets("Jugador/jugador_moviendose_2.png");
        jugadorImagen2 = Bitmap.createScaledBitmap(jugadorImagen2, getDp(100), getDp(30), false);
        jugadorImagen3 = getBitmapFromAssets("Jugador/jugador_moviendose_3.png");
        jugadorImagen3 = Bitmap.createScaledBitmap(jugadorImagen3, getDp(100), getDp(30), false);

        jugador = new Jugador(jugadorImagen1, anchoPantalla / 2 - jugadorImagen1.getWidth() / 2, altoPantalla - getDp(30), velocidadJugador, anchoPantalla);

        bolaImagen = getBitmapFromAssets("Bola/bola.png");
        bolaImagen = Bitmap.createScaledBitmap(bolaImagen, getDp(15), getDp(15), false);
        bola = new Bola(bolaImagen, anchoPantalla / 2, altoPantalla - getDp(55), velocidadBolaX, velocidadBolaY, altoPantalla);

        //Ladrillos normales

        ladrilloImagenAmarillo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_amarillo.png");
        ladrilloImagenAmarillo = Bitmap.createScaledBitmap(ladrilloImagenAmarillo, anchoPantalla / 5, altoPantalla / 30, false);
        ladrilloImagenAzul = getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul.png");
        ladrilloImagenAzul = Bitmap.createScaledBitmap(ladrilloImagenAzul, getDp(80), getDp(20), false);
        ladrilloImagenAzulOscuro = getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul_oscuro.png");
        ladrilloImagenAzulOscuro = Bitmap.createScaledBitmap(ladrilloImagenAzulOscuro, getDp(80), getDp(20), false);
        ladrilloImagenMarron = getBitmapFromAssets("Ladrillos/Normales/ladrillo_marron.png");
        ladrilloImagenMarron = Bitmap.createScaledBitmap(ladrilloImagenMarron, getDp(80), getDp(20), false);
        ladrilloImagenNaranja = getBitmapFromAssets("Ladrillos/Normales/ladrillo_naranja.png");
        ladrilloImagenNaranja = Bitmap.createScaledBitmap(ladrilloImagenNaranja, getDp(80), getDp(20), false);
        ladrilloImagenOscuro = getBitmapFromAssets("Ladrillos/Normales/ladrillo_oscuro.png");
        ladrilloImagenOscuro = Bitmap.createScaledBitmap(ladrilloImagenOscuro, getDp(80), getDp(20), false);
        ladrilloImagenRojo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_rojo.png");
        ladrilloImagenRojo = Bitmap.createScaledBitmap(ladrilloImagenRojo, getDp(80), getDp(20), false);
        ladrilloImagenVerde = getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde.png");
        ladrilloImagenVerde = Bitmap.createScaledBitmap(ladrilloImagenVerde, getDp(80), getDp(20), false);
        ladrilloImagenVerdeLima = getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde_lima.png");
        ladrilloImagenVerdeLima = Bitmap.createScaledBitmap(ladrilloImagenVerdeLima, getDp(80), getDp(20), false);
        ladrilloImagenVioleta = getBitmapFromAssets("Ladrillos/Normales/ladrillo_violeta.png");
        ladrilloImagenVioleta = Bitmap.createScaledBitmap(ladrilloImagenVioleta, getDp(80), getDp(20), false);

        //Ladrillos rompiendose
        ladrilloImagenAmarilloRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_amarillo_rompiendo.png");
        ladrilloImagenAmarilloRompiendo = Bitmap.createScaledBitmap(ladrilloImagenAmarilloRompiendo, anchoPantalla / 5, altoPantalla / 30, false);
        ladrilloImagenAzulRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_azul_rompiendo.png");
        ladrilloImagenAzulRompiendo = Bitmap.createScaledBitmap(ladrilloImagenAzulRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenAzulOscuroRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_azul_oscuro_rompiendo.png");
        ladrilloImagenAzulOscuroRompiendo = Bitmap.createScaledBitmap(ladrilloImagenAzulOscuroRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenMarronRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_marron_rompiendo.png");
        ladrilloImagenMarronRompiendo = Bitmap.createScaledBitmap(ladrilloImagenMarronRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenNaranjaRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_naranja_rompiendo.png");
        ladrilloImagenNaranjaRompiendo = Bitmap.createScaledBitmap(ladrilloImagenNaranjaRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenOscuroRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_oscuro_rompiendo.png");
        ladrilloImagenOscuroRompiendo = Bitmap.createScaledBitmap(ladrilloImagenOscuroRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenRojoRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_rojo_rompiendo.png");
        ladrilloImagenRojoRompiendo = Bitmap.createScaledBitmap(ladrilloImagenRojoRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenVerdeRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_verde_rompiendo.png");
        ladrilloImagenVerdeRompiendo = Bitmap.createScaledBitmap(ladrilloImagenVerdeRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenVerdeLimaRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_verde_lima_rompiendo.png");
        ladrilloImagenVerdeLimaRompiendo = Bitmap.createScaledBitmap(ladrilloImagenVerdeLimaRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenVioletaRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_violeta_rompiendo.png");
        ladrilloImagenVioletaRompiendo = Bitmap.createScaledBitmap(ladrilloImagenVioletaRompiendo, getDp(80), getDp(20), false);

        //Vida
        vidaImagen = getBitmapFromAssets("Jugador/Vida/vida.png");
        vidaImagen = Bitmap.createScaledBitmap(vidaImagen, altoPantalla / 20, altoPantalla / 20, false);
        ladrillos = creaLadrillos(25);

        tiempo = System.currentTimeMillis();
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

        Log.i("giroscopio","   izquierda "+moverIzquierdaGiroscopio+"   ----------    derecha "+moverDerechaGiroscopio);

        Log.i("tiempito", "es divisble?   " + (System.currentTimeMillis() - tiempo % 2 == 0) +"     "+System.currentTimeMillis()+"        "+tiempo+"          "+"wtf  "+ (System.currentTimeMillis() - tiempo));
        if (System.currentTimeMillis() - tiempo % 2 > 0) {
            jugador.imagen = jugadorImagen1;
            Log.i("entre1","                   "+jugador.imagen);
        } else if (System.currentTimeMillis() - tiempo % 5 > 0) {
            jugador.imagen = jugadorImagen2;
            Log.i("entre2","        !!"+jugador.imagen);
        } else {
            jugador.imagen = jugadorImagen3;
            Log.i("entre3"," "+jugador.imagen);
        }


        for (int i = ladrillos.size() - 1; i >= 0; i--) {
            if (ladrillos.get(i).colisionaLadrillos(bola, ladrilloImagenAmarilloRompiendo)) {
//                bola.reverseXVelocity();
                bola.reverseYVelocity();  //Ahora solo rebota hacia abajo, no tiene colisiones laterales
                if (ladrillos.get(i).getNumImpactos() <= 0) ladrillos.remove(i);
            }
        }
        switch (movimiento) {
            case 1:
                if (pulsandoIzquierda||moverIzquierdaGiroscopio) {
                    jugador.moverJugador(1);
                    pulsandoDerecha = false;
//                    moverIzquierdaGiroscopio=false;
                }
                break;
            case 2:
                if (pulsandoDerecha||moverDerechaGiroscopio) {
                    jugador.moverJugador(2);
                    pulsandoIzquierda = false;
//                    moverDerechaGiroscopio=false;
                }
                break;
        }
        bola.actualizarFisica();
        bola.limites(anchoPantalla);

        if (jugador.ed.contains(bola.contenedor)&&bola.contenedor.top==anchoPantalla){
            bola.setVelocidadX(-15);
        }

        if (jugador.ei.contains(bola.contenedor)&&bola.contenedor.left==0){
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
            c.drawText(vidas + "", anchoPantalla - vidaImagen.getWidth() * 2, altoPantalla / 20, pTextoblanco);

            jugador.dibujar(c);
            bola.dibujar(c);
            for (Ladrillo l : ladrillos) l.dibujar(c);

            if (perder){
                c.drawText(perdertxt);
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

//        int idPadre = super.onTouchEvent(event);
//        if (idPadre != idEscena) return idPadre;

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


    public void detectRotation(SensorEvent event) {
        if (event.values[2] > 0.5f) { // anticlockwise
            //Mover izquierda
            moverIzquierdaGiroscopio=true;
            moverDerechaGiroscopio=false;
        } else if (event.values[2] < -0.5f) { // clockwise
            //Mover derecha
            moverDerechaGiroscopio=true;
            moverIzquierdaGiroscopio=false;
        }
    }
}

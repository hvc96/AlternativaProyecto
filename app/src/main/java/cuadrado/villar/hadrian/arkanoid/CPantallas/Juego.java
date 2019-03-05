package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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

public class Juego extends Escena {
    float dedoCoordX, dedoCoordY;
    int movimiento, tiempoVibracion, vidas, idEscenaJuego;
    ;
    boolean pulsandoIzquierda, pulsandoDerecha, reseteado = false, perder = false;
    RectF izquierda, derecha;
    Bola bola;
    Jugador jugador;
    Bitmap jugadorImagen, bolaImagen, ladrilloImagenAmarillo, ladrilloImagenAzulOscuro, ladrilloImagenMarron, ladrilloImagenAzul, ladrilloImagenNaranja, ladrilloImagenOscuro, ladrilloImagenRojo, ladrilloImagenVerde, ladrilloImagenVerdeLima, ladrilloImagenVioleta, ladrilloImagenAmarilloRompiendo, ladrilloImagenAzulRompiendo, ladrilloImagenAzulOscuroRompiendo, ladrilloImagenMarronRompiendo, ladrilloImagenNaranjaRompiendo, ladrilloImagenOscuroRompiendo, ladrilloImagenRojoRompiendo, ladrilloImagenVerdeRompiendo, ladrilloImagenVerdeLimaRompiendo, ladrilloImagenVioletaRompiendo, vidaImagen;
    float velocidadJugador = 10, velocidadBolaX = 25, velocidadBolaY = 15;
    Paint pTextoblanco, pBarra;
    ArrayList<Ladrillo> ladrillos;
    Vibrator vibrador = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);


    public Juego(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        pTextoblanco = new Paint();
        pTextoblanco.setColor(Color.WHITE);
        pTextoblanco.setTextSize(getDp(40));

        pBarra = new Paint();
        pBarra.setColor(Color.LTGRAY);

        vidas = 2;

        izquierda = new RectF(0, 0, anchoPantalla / 2, altoPantalla);
        derecha = new RectF(anchoPantalla / 2, 0, anchoPantalla, altoPantalla);

        jugadorImagen = getBitmapFromAssets("Jugador/jugador_moviendose_1.png");
        jugadorImagen = Bitmap.createScaledBitmap(jugadorImagen, getDp(100), getDp(30), false);
        jugador = new Jugador(jugadorImagen, anchoPantalla / 2-jugadorImagen.getWidth()/2, altoPantalla - getDp(30), velocidadJugador, anchoPantalla);

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

        for (int i = ladrillos.size() - 1; i >= 0; i--) {
            if (ladrillos.get(i).colisionaLadrillos(bola, ladrilloImagenAmarilloRompiendo)) {
//                bola.reverseXVelocity();
                bola.reverseYVelocity();
                if (ladrillos.get(i).getNumImpactos() <= 0) ladrillos.remove(i);
            }
        }
        switch (movimiento) {
            case 1:
                if (pulsandoIzquierda) jugador.moverJugador(1);
                pulsandoDerecha = false;
                break;
            case 2:
                if (pulsandoDerecha) jugador.moverJugador(2);
                pulsandoIzquierda = false;
                break;
        }
        bola.actualizarFisica(60);
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

        if (vidas==0){
            perder=true;
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

        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    public int onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();        //Obtenemos el índice de la acción
        int pointerID = event.getPointerId(pointerIndex); //Obtenemos el Id del pointer asociado a la acción
        int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación
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
            perder = false;
        } else {
            perder = true;
        }
    }

    public void resetPosicion() {
        bola.posicion.x = anchoPantalla / 2;
        bola.posicion.y = altoPantalla - getDp(55);
        jugador.posicion.x = anchoPantalla / 2-jugadorImagen.getWidth()/2;
        jugador.posicion.y = altoPantalla -getDp(30);
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
            vibrador.vibrate(VibrationEffect.createOneShot(750, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrador.vibrate(tiempoVibracion);
        }
    }

}

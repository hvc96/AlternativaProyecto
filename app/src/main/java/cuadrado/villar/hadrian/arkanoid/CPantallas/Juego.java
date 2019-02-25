package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import cuadrado.villar.hadrian.arkanoid.CControl.Escena;
import cuadrado.villar.hadrian.arkanoid.CJuego.Bola;
import cuadrado.villar.hadrian.arkanoid.CJuego.Jugador;
import cuadrado.villar.hadrian.arkanoid.CJuego.Ladrillo;

public class Juego extends Escena {
    float dedoCoordX, dedoCoordY;
    int movimiento, columna = 6, fila = 5;
    boolean pulsandoIzquierda, pulsandoDerecha, reseteado = false;
    RectF izquierda, derecha;
    Bola bola;
    Jugador jugador;
    Bitmap jugadorImagen, bolaImagen, ladrilloImagenAmarillo, ladrilloImagenAzulOscuro, ladrilloImagenMarron, ladrilloImagenAzul, ladrilloImagenNaranja, ladrilloImagenOscuro, ladrilloImagenRojo, ladrilloImagenVerde, ladrilloImagenVerdeLima, ladrilloImagenVioleta, ladrilloImagenAmarilloRompiendo, ladrilloImagenAzulRompiendo, ladrilloImagenAzulOscuroRompiendo, ladrilloImagenMarronRompiendo, ladrilloImagenNaranjaRompiendo, ladrilloImagenOscuroRompiendo, ladrilloImagenRojoRompiendo, ladrilloImagenVerdeRompiendo, ladrilloImagenVerdeLimaRompiendo, ladrilloImagenVioletaRompiendo;
    float velocidadJugador = 10, velocidadBolaX = 25, velocidadBolaY = 15;
    Ladrillo Ladrillos[][];

    public Juego(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        izquierda = new RectF(0, 0, anchoPantalla / 2, altoPantalla);
        derecha = new RectF(anchoPantalla / 2, 0, anchoPantalla, altoPantalla);

        jugadorImagen = getBitmapFromAssets("Jugador/jugador_moviendose_1.png");
        jugadorImagen = Bitmap.createScaledBitmap(jugadorImagen, getDp(100), getDp(30), false);
        jugador = new Jugador(jugadorImagen, anchoPantalla / 2, altoPantalla - getDp(30), velocidadJugador, anchoPantalla);

        bolaImagen = getBitmapFromAssets("Bola/bola.png");
        bolaImagen = Bitmap.createScaledBitmap(bolaImagen, getDp(15), getDp(15), false);
        bola = new Bola(bolaImagen, anchoPantalla / 2, altoPantalla - getDp(55), velocidadBolaX, velocidadBolaY);

        Ladrillos = new Ladrillo[fila][columna];

        //Ladrillos normales
        ladrilloImagenAmarillo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_amarillo.png");
        ladrilloImagenAzul = getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul.png");
        ladrilloImagenAzulOscuro = getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul_oscuro.png");
        ladrilloImagenMarron = getBitmapFromAssets("Ladrillos/Normales/ladrillo_marron.png");
        ladrilloImagenNaranja = getBitmapFromAssets("Ladrillos/Normales/ladrillo_naranja.png");
        ladrilloImagenOscuro = getBitmapFromAssets("Ladrillos/Normales/ladrillo_oscuro.png");
        ladrilloImagenRojo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_rojo.png");
        ladrilloImagenVerde = getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde.png");
        ladrilloImagenVerdeLima = getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde_lima.png");
        ladrilloImagenVioleta = getBitmapFromAssets("Ladrillos/Normales/ladrillo:violeta.png");

        //Ladrillos rompiendose
        ladrilloImagenAmarilloRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_amarillo_rompiendo.png");
        ladrilloImagenAzulRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul_rompiendo.png");
        ladrilloImagenAzulOscuroRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul_oscuro_rompiendo.png");
        ladrilloImagenMarronRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_marron_rompiendo.png");
        ladrilloImagenNaranjaRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_naranja_rompiendo.png");
        ladrilloImagenOscuroRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_oscuro_rompiendo.png");
        ladrilloImagenRojoRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_rojo_rompiendo.png");
        ladrilloImagenVerdeRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde_rompiendo.png");
        ladrilloImagenVerdeLimaRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde_lima_rompiendo.png");
        ladrilloImagenVioletaRompiendo = getBitmapFromAssets("Ladrillos/Normales/ladrillo:violeta_rompiendo.png");
    }


    public void construirLadrillos() {
        //dependiendo del nivel tienen 1 o 2 hits
    }


    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {
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
        bola.actualizarFisica(30);
        bola.limites(anchoPantalla);
        if (bola.preguntaPerdio(altoPantalla)) {
            resetPosicion();
        }
        if (reseteado) {
            bola.setVelocidadX(velocidadBolaX);
            bola.setVelocidadY(velocidadBolaY);
        }

        if (bola.getContenedor().intersect(jugador.getRect())) {
            bola.reverseYVelocity();
            bola.reverseXVelocity();
        }
    }

    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLACK); // Establecemos un color de fondo. En este caso negro
            jugador.dibujar(c);
            bola.dibujar(c);
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
                break;
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                pulsandoIzquierda = false;
                pulsandoDerecha = false;
                break;

            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) return idPadre;

        return idEscena;
    }

    public void resetPosicion() {
        bola.posicion.x = anchoPantalla / 2;
        bola.posicion.y = altoPantalla - getDp(55);
        bola.setVelocidadX(0);
        bola.setVelocidadY(0);
        reseteado = true;
    }

}

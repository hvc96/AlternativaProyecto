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

public class Juego extends Escena {
    float dedoCoordX,dedoCoordY;
    int movimiento;
    boolean pulsandoIzquierda, pulsandoDerecha;
    RectF izquierda,derecha;
    Bola bola;
    Jugador jugador;
    Bitmap jugadorImagen, bolaImagen;
    float velocidadJugador=10, velocidadBolaX=50,velocidadBolaY=75;

    public Juego(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        izquierda = new RectF(0, 0, anchoPantalla/2, altoPantalla);
        derecha = new RectF(anchoPantalla/2, 0, anchoPantalla, altoPantalla);

        jugadorImagen = getBitmapFromAssets("Jugador/jugador_moviendose_1.png");
//        jugadorImagen = getBitmapFromAssets(context,"\\Jugador\\jugador_moviendose_1.png");
        jugadorImagen= Bitmap.createScaledBitmap(jugadorImagen,getDp(100),getDp(30),false);
        jugador =new Jugador(jugadorImagen,anchoPantalla/2,altoPantalla-getDp(30),velocidadJugador,anchoPantalla);

        bolaImagen = getBitmapFromAssets("Bola/bola.png");
//        bolaImagen = getBitmapFromAssets(context,"\\Bola\\bola.png");
        bolaImagen= Bitmap.createScaledBitmap(bolaImagen,getDp(25),getDp(25),false);
        bola =new Bola(bolaImagen,anchoPantalla/2,altoPantalla-getDp(55),velocidadBolaX,velocidadBolaY);

    }


    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {
       switch (movimiento){
           case 1:
        jugador.moverJugador(1);
               break;
           case 2:
        jugador.moverJugador(2);
               break;
       }
    }

    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLACK); // Establecemos un color de fondo. En este caso negro
            bola.dibujar(c);
            jugador.dibujar(c);
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
                dedoCoordX = event.getX(pointerIndex);
                dedoCoordY = event.getY(pointerIndex);
                if (izquierda.contains(dedoCoordX,dedoCoordY)) {
                    movimiento=1;// Mover izquierda
                } else {
                    movimiento=2;// Mover derecha
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
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


}

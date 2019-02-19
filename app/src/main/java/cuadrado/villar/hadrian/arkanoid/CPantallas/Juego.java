package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import cuadrado.villar.hadrian.arkanoid.CControl.Escena;
import cuadrado.villar.hadrian.arkanoid.CJuego.Bola;
import cuadrado.villar.hadrian.arkanoid.CJuego.Jugador;

public class Juego extends Escena {
    float posX;
    int alto, ancho;
    boolean pulsandoIzquierda, pulsandoDerecha;
    RectF izquierda,derecha;
    Paint paintJugador;
    Bola bola;
    Jugador jugador;

    public Juego(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        alto = altoPantalla;
        ancho = anchoPantalla;

        izquierda = new RectF(0, 0, anchoPantalla/2, altoPantalla);
        derecha = new RectF(anchoPantalla/2, 0, anchoPantalla, altoPantalla);

        paintJugador= new Paint(Color.GREEN);
        jugador =new Jugador(anchoPantalla,altoPantalla);
        bola = new Bola(screenX, screenY);
//        fondo = getBitmapFromAssets("fondoJuego.jpg");
//        fondo= Bitmap.createScaledBitmap(fondo,anchoPantalla,altoPantalla,false);
    }


    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {

    }

    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLUE); // Establecemos un color de fondo. En este caso azul
            super.dibujar(c);

            //c.drawBitmap(fondo,0,0,null);
            super.dibujar(c);
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
                posX = event.getX(pointerIndex);
                if (posX < ancho / 2) {      //Izquierda
                    //Mueve jugador izquierda
                } else {                     //Derecha

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

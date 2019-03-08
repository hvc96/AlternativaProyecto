package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import cuadrado.villar.hadrian.arkanoid.CControl.BaseDatos;
import cuadrado.villar.hadrian.arkanoid.CControl.Escena;

/**
 * Pantalla Records, utilizado en el juego para mostrar las mejores puntuaciones obtenidas.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Records extends Escena {

    public Paint paint, pBronce, pPlata, pOro;
    Rect bronce, plata, oro;
    BaseDatos bd;
    ArrayList<Integer> alRecords;
    int ancho, alto, primero = 0, segundo = 0, tercero = 0;

    /**
     * Constructor de clase.
     *
     * @param context       Contexto de la aplicación.
     * @param idEscena      Define la pantalla en la que estamos.
     * @param anchoPantalla Ancho de la pantalla.
     * @param altoPantalla  Alto de la pantalla.
     */
    public Records(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        bd = new BaseDatos(context, "puntos", null, 1);

        ancho = anchoPantalla / 10;
        alto = altoPantalla / 10;

        pBronce = new Paint();
        pBronce.setARGB(255, 150, 90, 56);
        bronce = new Rect(ancho * 6, alto * 5, ancho * 8, alto * 9);

        pOro = new Paint();
        pOro.setARGB(255, 217, 164, 65);
        oro = new Rect(ancho * 4, alto * 3, ancho * 6, alto * 9);

        pPlata = new Paint();
        pPlata.setARGB(255, 204, 194, 194);
        plata = new Rect(ancho * 2, alto * 4, ancho * 4, alto * 9);

        bd = new BaseDatos(context, "puntos", null, 1);
        alRecords = bd.mostrarRecords(bd.getWritableDatabase());

        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);

//        if (alRecords.size() > 0 && alRecords != null) {
//            primero = alRecords.get(0);
//            segundo = alRecords.get(1);
//            tercero = alRecords.get(2);
//        }
    }

    /**
     * Actualiza las físicas.
     */
    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {

    }

    /**
     * Método para el dibujado del jugador.
     *
     * @param c Objeto Canvas para utilizar los métodos útiles para el dibujo.
     */
    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.WHITE);
            c.drawRect(bronce, pBronce);
            c.drawRect(plata, pPlata);
            c.drawRect(oro, pOro);

            if (alRecords.size() > 0) {
                c.drawText(alRecords.get(1) + "", ancho * 2 + ancho / 2, alto * 4, paint);
            }
            if (alRecords.size() > 1) {
                c.drawText(alRecords.get(0)+"", ancho * 4 + ancho / 2, alto * 3, paint);
            }
            if (alRecords.size() > 2) {
                c.drawText(alRecords.get(2)+"", ancho * 6 + ancho / 2, alto * 5, paint);
            }
            super.dibujar(c);
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Método para la gestión de la pulsación en la pantalla.
     *
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
                if (pulsa(volverAtras, event)) {
                    return 0;
                }
                break;
        }
        return idEscena;
    }

}


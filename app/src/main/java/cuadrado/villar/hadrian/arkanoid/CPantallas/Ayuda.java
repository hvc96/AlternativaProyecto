package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import cuadrado.villar.hadrian.arkanoid.CControl.Escena;

/**
 * Pantalla Ayuda, utilizado en el juego.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Ayuda extends Escena {

    /**
     * Array de imagenes ayuda.
     */
    Bitmap[] imagenes;
    /**
     * Contador del array.
     */
    int cont;
    /**
     * Rectangulo donde detectamos la pulsación en la pantalla.
     */
    Rect pantalla;

    /**
     * Constructor de clase.
     * @param context Contexto de la aplicación.
     * @param idEscena Define la pantalla en la que estamos.
     * @param anchoPantalla Ancho de la pantalla.
     * @param altoPantalla Alto de la pantalla.
     */
    public Ayuda(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        imagenes=new Bitmap[7];
        imagenes[0]=Bitmap.createScaledBitmap(getBitmapFromAssets("Ayuda/inicio_texto.png"),anchoPantalla,altoPantalla,false);
        imagenes[1]=Bitmap.createScaledBitmap(getBitmapFromAssets("Ayuda/juego_texto.png"),anchoPantalla,altoPantalla,false);
        imagenes[2]=Bitmap.createScaledBitmap(getBitmapFromAssets("Ayuda/juego_perder_texto.png"),anchoPantalla,altoPantalla,false);
        imagenes[3]=Bitmap.createScaledBitmap(getBitmapFromAssets("Ayuda/opciones_volumen_texto.png"),anchoPantalla,altoPantalla,false);
        imagenes[4]=Bitmap.createScaledBitmap(getBitmapFromAssets("Ayuda/opciones_sensores_texto.png"),anchoPantalla,altoPantalla,false);
        imagenes[5]=Bitmap.createScaledBitmap(getBitmapFromAssets("Ayuda/opciones_idioma_texto.png"),anchoPantalla,altoPantalla,false);
        imagenes[6]=Bitmap.createScaledBitmap(getBitmapFromAssets("Ayuda/records_texto.png"),anchoPantalla,altoPantalla,false);
        cont=0;

        pantalla=new Rect(0,0,anchoPantalla,altoPantalla);
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
            c.drawBitmap(imagenes[cont],0,0,null);


            super.dibujar(c);
        } catch (Exception e) {
        }
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
                if (pulsa(pantalla,event)){
                    cont++;
                }
                if (cont>=imagenes.length)cont=0;
                break;
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

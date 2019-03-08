package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;


import java.util.ArrayList;

import cuadrado.villar.hadrian.arkanoid.CControl.Escena;
import cuadrado.villar.hadrian.arkanoid.R;

/**
 * Pantalla Créditos, utilizado en el juego para mostrar la información referente a los agradecimientos imagenes y demás.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Creditos extends Escena {

    String tt0,t1,t2,tt3,t4,tt5,t6,t7,t8,tt9,t10,t22;
    Paint pNormal,pTitulo;
    int alto;

    /**
     * Constructor de clase.
     * @param context Contexto de la aplicación.
     * @param idEscena Define la pantalla en la que estamos.
     * @param anchoPantalla Ancho de la pantalla.
     * @param altoPantalla Alto de la pantalla.
     */
    public Creditos(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        alto=altoPantalla/21;

        tt0 = context.getString(R.string.licenciastitulo);

        t1 = context.getString(R.string.licencias);
        t2 = context.getString(R.string.licenciasAgradecer);
        t22=context.getString(R.string.licenciasAgr2);

        tt3 = context.getString(R.string.programador);

        t4 = context.getString(R.string.yo);

        tt5 = context.getString(R.string.agradecimientos);
        t6 = context.getString(R.string.genteAgradecimientoProfesores);
        t7 = context.getString(R.string.genteAgradecimientosAmigos1);
        t8 = context.getString(R.string.genteAgradecimientosAmigos2);

        tt9 = context.getString(R.string.programadoelaborado);
        t10 = context.getString(R.string.yo);


        Typeface faw1 = Typeface.createFromAsset(context.getAssets(), "Fuentes/PoiretOne-Regular.ttf");
        Typeface faw2 = Typeface.createFromAsset(context.getAssets(), "Fuentes/WELTRON2.TTF");

        pTitulo= new Paint();
        pTitulo.setTypeface(faw2);
        pTitulo.setTextSize(getDp(30));
        pTitulo.setColor(Color.BLACK);
        pTitulo.setTextAlign(Paint.Align.CENTER);

        pNormal = new Paint();
        pNormal.setTypeface(faw1);
        pNormal.setTextSize(getDp(20));
        pNormal.setColor(Color.BLACK);
        pNormal.setStrokeWidth(10);
        pNormal.setTextAlign(Paint.Align.CENTER);
    }


    /**
     * Actualiza las físicas.
     */
    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica(){

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
            c.drawText(tt0,anchoPantalla/2,alto*2,pTitulo);
            c.drawText(t1,anchoPantalla/2,alto*4,pNormal);
            c.drawText(t2,anchoPantalla/2,alto*5,pNormal);
            c.drawText(t22,anchoPantalla/2,alto*6,pNormal);
            c.drawText(tt3,anchoPantalla/2,alto*8,pTitulo);
            c.drawText(t4,anchoPantalla/2,alto*10,pNormal);
            c.drawText(tt5,anchoPantalla/2,alto*12,pTitulo);
            c.drawText(t6,anchoPantalla/2,alto*14,pNormal);
            c.drawText(t7,anchoPantalla/2,alto*15,pNormal);
            c.drawText(t8,anchoPantalla/2,alto*16,pNormal);
            c.drawText(tt9,anchoPantalla/2,alto*18,pTitulo);
            c.drawText(t10,anchoPantalla/2,alto*20,pNormal);

            super.dibujar(c);
        } catch (Exception e) {
            Log.i("Error al dibujar",e.getLocalizedMessage());
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


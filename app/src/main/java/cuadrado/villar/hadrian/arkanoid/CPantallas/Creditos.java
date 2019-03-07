package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;


import cuadrado.villar.hadrian.arkanoid.CControl.Escena;

public class Creditos extends Escena {

    public Creditos(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
//        fondo = getBitmapFromAssets("fondoCreditos.jpg");
//        fondo= Bitmap.createScaledBitmap(fondo,anchoPantalla,altoPantalla,false);
    }



    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica(){

    }

    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.WHITE);
//            c.drawBitmap(fondo,0,0,null);
            super.dibujar(c);
        } catch (Exception e) {
            Log.i("Error al dibujar",e.getLocalizedMessage());
        }
    }



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


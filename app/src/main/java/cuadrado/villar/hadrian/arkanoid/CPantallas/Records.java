package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import cuadrado.villar.hadrian.arkanoid.CControl.BaseDatos;
import cuadrado.villar.hadrian.arkanoid.CControl.Escena;

public class Records extends Escena {

    public Paint paint, pBronce,pPlata,pOro;
    Rect bronce, plata, oro;
    BaseDatos bd;

    public Records(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        int ancho= anchoPantalla/10;
        int alto=altoPantalla/10;

        pBronce= new Paint();
        pBronce.setARGB(255,150,90,56);
        bronce=new Rect(ancho*6,alto*5,ancho*8,alto*9);

        pOro= new Paint();
        pOro.setARGB(255,217,164,65);
        oro=new Rect(ancho*4,alto*3,ancho*6,alto*9);

        pPlata= new Paint();
        pPlata.setARGB(255,204,194,194);
        plata=new Rect(ancho*2,alto*4,ancho*4,alto*9);

        bd = new BaseDatos(context,"puntos",null,1);
//        puntuaciones= bd.mostrarRecords();
    }

    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica(){

    }

    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.WHITE);
            c.drawRect(bronce,pBronce);
            c.drawRect(plata,pPlata);
            c.drawRect(oro,pOro);
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


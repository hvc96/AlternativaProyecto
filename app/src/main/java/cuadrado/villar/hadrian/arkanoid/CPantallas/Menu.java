package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;


import cuadrado.villar.hadrian.arkanoid.CControl.Escena;

public class Menu extends Escena {

    Rect ayuda, opciones, juego, records, creditos;
    int alto, ancho;

    public Menu(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        fondo = getBitmapFromAssets("a7.png");
        fondo= Bitmap.createScaledBitmap(fondo,anchoPantalla,altoPantalla,false);
        alto=altoPantalla/15;
        ancho= anchoPantalla/10;

        juego= new Rect(ancho*3, alto*2,ancho*7,alto*4);
        ayuda=new Rect(ancho*1,alto*7, ancho*9,alto*8);
        opciones=new Rect(ancho*1,alto*9, ancho*9,alto*10);
        records=new Rect(ancho*1,alto*11, ancho*9,alto*12);
        creditos=new Rect(ancho*1,alto*13,ancho*9,alto*14);
    }


    public int onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();        //Obtenemos el índice de la acción
        int pointerID = event.getPointerId(pointerIndex); //Obtenemos el Id del pointer asociado a la acción
        int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación
        switch (accion) {
            case MotionEvent.ACTION_DOWN:           // Primer dedo toca
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                if (pulsa(juego,event)) return 100;             //Juego         ->100
                else  if (pulsa(ayuda,event)) return 10;        //Ayuda         ->10
                else  if (pulsa(creditos,event)) return 20;     //Creditos      ->20
                else  if (pulsa(opciones,event)) return 30;     //Opciones      ->30
                else  if (pulsa(records,event)) return 40;      //Records       ->40
                break;

            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }
        return idEscena;
    }





    // Actualizamos la física de los elementos en pantalla
    public void actualizarFisica(){

    }

    // Rutina de dibujo en el lienzo. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo,0,0,null);
            super.dibujar(c);
            c.drawRect(juego,pBoton2);
            c.drawCircle(ancho*5,alto*3,ancho*3,pBoton2);
            c.drawText("START",juego.centerX(),juego.centerY()+(alto/6),pTexto);

            c.drawRect(ayuda,pBoton);
            c.drawText("AYUDA",ayuda.centerX(),ayuda.centerY()+(alto/2),pTexto);

            c.drawRect(opciones,pBoton);
            c.drawText("OPTIONS",opciones.centerX(),opciones.centerY()+(alto/2),pTexto);

            c.drawRect(records,pBoton);
            c.drawText("RECORDS",records.centerX(),records.centerY()+(alto/2),pTexto);

            c.drawRect(creditos,pBoton);
            c.drawText("CREDITS",creditos.centerX(),creditos.centerY()+(alto/2),pTexto);

        } catch (Exception e) {
            Log.i("Error al dibujar",e.getLocalizedMessage());
        }
    }
}

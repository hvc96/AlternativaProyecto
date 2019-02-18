package cuadrado.villar.hadrian.arkanoid.CControl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Escena {
    Context context;
    public int idEscena;
    int anchoPantalla, altoPantalla;
    public Bitmap fondo, botonRetroceder;
    public Paint pTexto,pTexto2, pBoton, pBoton2;
    Rect backToMenu;

    private static final int MIN_DXDY = 2;
    final private static Map<Integer, PointF> posiciones = new HashMap<>();


    public Escena(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.idEscena = idEscena;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        pTexto=new Paint();
        pTexto2=new Paint();

        pTexto.setColor(Color.YELLOW);
        pTexto.setTextAlign(Paint.Align.CENTER);
        pTexto.setTextSize((int)(altoPantalla/12));

        pTexto2.setColor(Color.RED);
        pTexto2.setTextAlign(Paint.Align.CENTER);
        pTexto2.setTextSize(altoPantalla/5);

        pBoton= new Paint();
        pBoton.setColor(Color.RED);

        pBoton2= new Paint();
        pBoton2.setColor(Color.BLACK);

        botonRetroceder = getBitmapFromAssets("atras256.png");
        backToMenu=new Rect(anchoPantalla-2*(anchoPantalla/9),altoPantalla-altoPantalla/9,anchoPantalla,altoPantalla);
    }


    public int onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();        //Obtenemos el índice de la acción
        int pointerID = event.getPointerId(pointerIndex); //Obtenemos el Id del pointer asociado a la acción
        int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación
        switch (accion) {

            case MotionEvent.ACTION_POINTER_DOWN:
                PointF posicion = new PointF(event.getX(pointerIndex), event.getY(pointerIndex));
                posiciones.put(pointerID, posicion);
                break;
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                if (pulsa(backToMenu,event) && idEscena!=0 && idEscena!=100)return 0;
                break;
            case MotionEvent.ACTION_MOVE:


                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }
        return idEscena;
    }

    public boolean pulsa(Rect boton, MotionEvent event){
        if (boton.contains((int)event.getX(),(int)event.getY())) return true;
        else return false;
    }



    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica(){

    }

    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            if (idEscena!=0&&idEscena!=100)c.drawBitmap(botonRetroceder,anchoPantalla-2*(anchoPantalla/8),altoPantalla-altoPantalla/8,null);

        } catch (Exception e) {
            Log.i("Error al dibujar",e.getLocalizedMessage());
        }
    }



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getIdEscena() {
        return idEscena;
    }

    public void setIdEscena(int idEscena) {
        this.idEscena = idEscena;
    }

    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    public void setAnchoPantalla(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    public void setAltoPantalla(int altoPantalla) {
        this.altoPantalla = altoPantalla;
    }

    public Bitmap getFondo() {
        return fondo;
    }

    public void setFondo(Bitmap fondo) {
        this.fondo = fondo;
    }

    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is=context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public int getDp(int pixels){
        /**
         * Calculo de las coordenada y en relacion al una pantalla de 1208x775
         * @param pixels coordenada en pixses en realcion a una pantalla de 1208x775
         * @return coordenada adaptada a la resolucion del dispositivo
         */
        return (int)((pixels/7.75)*altoPantalla)/100;
    }

}

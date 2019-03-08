package cuadrado.villar.hadrian.arkanoid.CControl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
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

/**
 * <h1>Escena</h1>
 * Clase de la cual extienden todas las pantallas.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Escena {

    Context context;
    public int idEscena;
    public int anchoPantalla, altoPantalla;
    public Bitmap fondo, botonRetroceder;
    public Paint pTexto, pTexto2, pBoton, pBoton2, paint;
    public Rect volverAtras;

    public static BaseDatos bd;
    public static SQLiteDatabase sqldb;
    public static SharedPreferences prefs;
    public static SharedPreferences.Editor editor;

    /**
     * Constructor de clase.
     * @param context Contexto de la aplicación.
     * @param idEscena Número que retornará en el menú para el manejo de escenas.
     * @param anchoPantalla Ancho del dispositivo en el que se ejecuta la apliación.
     * @param altoPantalla Alto del dispositivo en el que se ejecuta la aplicación.
     */
    public Escena(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.idEscena = idEscena;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        pTexto = new Paint();
        pTexto2 = new Paint();

        pTexto.setColor(Color.YELLOW);
        pTexto.setTextAlign(Paint.Align.CENTER);
        pTexto.setTextSize((int) (altoPantalla / 12));

        pTexto2.setColor(Color.RED);
        pTexto2.setTextAlign(Paint.Align.CENTER);
        pTexto2.setTextSize(altoPantalla / 5);

        pBoton = new Paint();
        pBoton.setColor(Color.RED);

        pBoton2 = new Paint();
        pBoton2.setColor(Color.BLACK);

        botonRetroceder = getBitmapFromAssets("Botones/atras.png");
        botonRetroceder = Bitmap.createScaledBitmap(botonRetroceder, getDp(50), getDp(60), false);

        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);

        prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        editor = prefs.edit();

        volverAtras = new Rect(0, 0, getDp(50), getDp(60));

        prefs.getBoolean("musica", true);
        prefs.getBoolean("vibracion", true);
        prefs.getBoolean("giroscopio", true);
        prefs.getBoolean("castellano", true);
        prefs.getBoolean("ingles", true);
    }

    /**
     * Método para la gestión de la pulsación en la pantalla.
     * @param event Evento ocasionado al tocar la pantalla.
     * @return Devuelve la escena en la que se encuentra.
     */
    public int onTouchEvent(MotionEvent event) {
        return idEscena;
    }

    /**
     * Método para la gestión de la pulsación sobre un botón, el cual genera un evento.
     * @param boton Botón de forma rectangular soobre el que se ejecuta la pulsación.
     * @param event Evento ocasionado por la pulsación de la pantalla para saber donde pulsa el individuo.
     * @return Devuelve true si presiona un botón, false si es en otro lado.
     */
    public boolean pulsa(Rect boton, MotionEvent event) {
        if (boton.contains((int) event.getX(), (int) event.getY())) return true;
        else return false;
    }

    /**
     * Método para la actualización de las físicas.
     */
    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {

    }

    /**
     * Método para dibujar sobre el lienzo del dispositivo.
     * @param c Objeto Canvas para utilizar los métodos útiles para el dibujo.
     */
    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            if (idEscena != 0 && idEscena != 100) {
                c.drawBitmap(botonRetroceder, 0, 0, null);
                c.drawRect(volverAtras, paint);
            }

        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Getter del contexto
     * @return Devuelve el contexto
     */
    public Context getContext() {
        return context;
    }

    /**
     * Setter del contexto
     * @param context Contexto de la aplicación
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Getter de idEscena.
     * @return Devuelve una referencia de la escena en la que te encuentras.
     */
    public int getIdEscena() {
        return idEscena;
    }


    /**
     * Método para obtener una imagen a partir de un fichero.
     * @param fichero Fichero donde se encuentra.
     * @return Devuelve un bitmap localizado en dicho fichero.
     */
    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is = context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }


        /**
         * Calculo de las coordenada y en relacion al una pantalla de 1208x775.
         * @param pixels coordenada en pixeles en relacion a una pantalla de 1208x775.
         * @return coordenada adaptada a la resolucion del dispositivo.
         */
    public int getDp(int pixels) {
        return (int) ((pixels / 7.75) * altoPantalla) / 100;
    }


//TODO No se usan, implementar en un futuro.

//    public void cambiaFrame(int numImg,long tFrameAuxm, long tiempoFrame, int indice){
//        if (System.currentTimeMillis()-tFrameAuxm>tiempoFrame) {
//            indice++;
//            if (indice>= numImg)indice=0;
//            tFrameAuxm=System.currentTimeMillis();
//        }
//    }
//    public Bitmap[] getFrames(int numImg, String dir, String tag, int width){
//        Bitmap[] aux=new Bitmap[numImg];
//        for (int i=0;i<numImg;i++) aux[i]=escalaAnchura(dir+"/"+tag+" ("+(i+1)+").png",width);
//        return aux;
//    }
//    public  Bitmap escalaAnchura(String fichero, int nuevoAncho) {
//        Bitmap bitmapAux=getBitmapFromAssets(fichero);
//        if (nuevoAncho==bitmapAux.getWidth()) return bitmapAux;
//        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
//    }
}

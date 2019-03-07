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


    public int onTouchEvent(MotionEvent event) {
        return idEscena;
    }

    public boolean pulsa(Rect boton, MotionEvent event) {
        if (boton.contains((int) event.getX(), (int) event.getY())) return true;
        else return false;
    }


    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {

    }

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
            InputStream is = context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    public static Bitmap getBitmapFromAssets(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
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

    public int getDp(int pixels) {
        /**
         * Calculo de las coordenada y en relacion al una pantalla de 1208x775
         * @param pixels coordenada en pixses en realcion a una pantalla de 1208x775
         * @return coordenada adaptada a la resolucion del dispositivo
         */
        return (int) ((pixels / 7.75) * altoPantalla) / 100;
    }

    public Bitmap[] getFrames(int numImg, String dir, String tag, int width){
        Bitmap[] aux=new Bitmap[numImg];
        for (int i=0;i<numImg;i++) aux[i]=escalaAnchura(dir+"/"+tag+" ("+(i+1)+").png",width);
        return aux;
    }

    public  Bitmap escalaAnchura(String fichero, int nuevoAncho) {
        Bitmap bitmapAux=getBitmapFromAssets(fichero);
        if (nuevoAncho==bitmapAux.getWidth()) return bitmapAux;
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    public void cambiaFrame(int numImg,long tFrameAuxm, long tiempoFrame, int indice){
        if (System.currentTimeMillis()-tFrameAuxm>tiempoFrame) {
            indice++;
            if (indice>= numImg)indice=0;
            tFrameAuxm=System.currentTimeMillis();
        }
    }
}

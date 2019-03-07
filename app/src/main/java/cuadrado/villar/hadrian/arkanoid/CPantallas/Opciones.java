package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Locale;

import cuadrado.villar.hadrian.arkanoid.CControl.Escena;
import cuadrado.villar.hadrian.arkanoid.CJuego.Bola;

public class Opciones extends Escena {

    String idioma = Locale.getDefault().getLanguage().toLowerCase().trim();
    Bitmap castellanoONImagen, castellanoOFFImagen, inglesONImagen, inglesOFFImagen, vibracionONImagen, vibracionOFFImagen, sonidoONImagen, sonidoOFFImagen, giroscopioONImagen, giroscopioOFFImagen;
    Rect castellano, ingles, vibracion, giroscopio, sonido;
    boolean mmusica, vvibracion, inggles, ggiroscopio, ccastellano;
    int alto, ancho;

    public Opciones(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        ancho = anchoPantalla / 6;
        alto = altoPantalla / 10;

        sonidoONImagen = getBitmapFromAssets("Opciones/Sonido/sonido.png");
        sonidoONImagen = Bitmap.createScaledBitmap(sonidoONImagen, getDp(100), getDp(100), false);
        sonidoOFFImagen = getBitmapFromAssets("Opciones/Sonido/silencio.png");
        sonidoOFFImagen = Bitmap.createScaledBitmap(sonidoOFFImagen, getDp(100), getDp(100), false);

        sonido = new Rect(anchoPantalla / 2 - sonidoONImagen.getWidth() / 2, alto, anchoPantalla / 2 + sonidoONImagen.getWidth() / 2, alto + getDp(100));

        vibracionONImagen = getBitmapFromAssets("Opciones/Vibracion/vibracionON.png");
        vibracionONImagen = Bitmap.createScaledBitmap(vibracionONImagen, getDp(100), getDp(100), false);
        vibracionOFFImagen = getBitmapFromAssets("Opciones/Vibracion/vibracionOFF.png");
        vibracionOFFImagen = Bitmap.createScaledBitmap(vibracionOFFImagen, getDp(100), getDp(100), false);

        vibracion = new Rect(ancho * 2 - vibracionONImagen.getWidth() / 2, alto * 4, ancho * 2 + vibracionONImagen.getWidth() / 2, alto * 4 + getDp(100));

        giroscopioONImagen = getBitmapFromAssets("Opciones/Giroscopio/giroscopioON.png");
        giroscopioONImagen = Bitmap.createScaledBitmap(giroscopioONImagen, getDp(100), getDp(100), false);
        giroscopioOFFImagen = getBitmapFromAssets("Opciones/Giroscopio/giroscopioOFF.png");
        giroscopioOFFImagen = Bitmap.createScaledBitmap(giroscopioOFFImagen, getDp(100), getDp(100), false);

        giroscopio = new Rect(ancho * 4 - giroscopioONImagen.getWidth() / 2, alto * 4, ancho * 4 + giroscopioONImagen.getWidth() / 2, alto * 4 + getDp(100));

        castellanoONImagen = getBitmapFromAssets("Opciones/Idioma/castellanoON.png");
        castellanoONImagen = Bitmap.createScaledBitmap(castellanoONImagen, getDp(100), getDp(100), false);
        castellanoOFFImagen = getBitmapFromAssets("Opciones/Idioma/castellanoOFF.png");
        castellanoOFFImagen = Bitmap.createScaledBitmap(castellanoOFFImagen, getDp(100), getDp(100), false);

        castellano = new Rect(ancho * 2 - castellanoOFFImagen.getWidth() / 2, alto * 8, ancho * 2 + castellanoOFFImagen.getWidth() / 2, alto * 8 + getDp(100));

        inglesONImagen = getBitmapFromAssets("Opciones/Idioma/inglesON.png");
        inglesONImagen = Bitmap.createScaledBitmap(inglesONImagen, getDp(100), getDp(100), false);
        inglesOFFImagen = getBitmapFromAssets("Opciones/Idioma/inglesOFF.png");
        inglesOFFImagen = Bitmap.createScaledBitmap(inglesOFFImagen, getDp(100), getDp(100), false);

        ingles = new Rect(ancho * 4 - inglesONImagen.getWidth() / 2, alto * 8, ancho * 4 + inglesONImagen.getWidth() / 2, alto * 8 + getDp(100));

        if (prefs.getBoolean("musica", true) == true) {
            mmusica = true;
        } else {
            mmusica = false;
        }

        if (prefs.getBoolean("vibracion", true) == true) {
            vvibracion = true;
        } else {
            vvibracion = false;
        }

        if (prefs.getBoolean("giroscopio", true) == true) {
            ggiroscopio = true;
        } else {
            ggiroscopio = false;
        }

        if (prefs.getBoolean("castellano", true) == true) {
            ccastellano = true;
        } else {
            ccastellano = false;
        }

        if (prefs.getBoolean("ingles", true) == true) {
            inggles = true;
        } else {
            inggles = false;
        }
    }


    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {

    }

    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.WHITE);
//            c.drawBitmap(volverAtras, 0, 0, null);

            if (mmusica) {
                c.drawBitmap(sonidoONImagen, anchoPantalla / 2 - sonidoONImagen.getWidth() / 2, alto, null);
            } else {
                c.drawBitmap(sonidoOFFImagen, anchoPantalla / 2 - sonidoOFFImagen.getWidth() / 2, alto, null);
            }

            if (vvibracion) {
                c.drawBitmap(vibracionONImagen, ancho * 2 - vibracionONImagen.getWidth() / 2, alto * 4, null);
            } else {
                c.drawBitmap(vibracionOFFImagen, ancho * 2 - vibracionONImagen.getWidth() / 2, alto * 4, null);
            }

            if (ggiroscopio) {
                c.drawBitmap(giroscopioONImagen, ancho * 4 - giroscopioONImagen.getWidth() / 2, alto * 4, null);
            } else {
                c.drawBitmap(giroscopioOFFImagen, ancho * 4 - giroscopioOFFImagen.getWidth() / 2, alto * 4, null);
            }

            if (ccastellano) {
                c.drawBitmap(castellanoONImagen, ancho * 2 - castellanoONImagen.getWidth() / 2, alto * 8, null);
                c.drawBitmap(inglesOFFImagen, ancho * 4 - inglesOFFImagen.getWidth() / 2, alto * 8, null);
            }

            if (inggles) {
                c.drawBitmap(inglesONImagen, ancho * 4 - inglesONImagen.getWidth() / 2, alto * 8, null);
                c.drawBitmap(castellanoOFFImagen, ancho * 2 - castellanoOFFImagen.getWidth() / 2, alto * 8, null);
            }
            super.dibujar(c);
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
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
                if (pulsa(volverAtras, event)) return 0;
                else if (pulsa(sonido, event)) {
                    mmusica = !mmusica;
                    editor.putBoolean("musica",mmusica);
                } else if (pulsa(vibracion, event)) {
                    vvibracion = !vvibracion;
                    editor.putBoolean("vibracion",vvibracion);
                } else if (pulsa(giroscopio, event)) {
                    ggiroscopio = !ggiroscopio;
                    editor.putBoolean("giroscopio",ggiroscopio);
                } else if (pulsa(castellano, event)) {
                    ccastellano = true;
                    inggles = false;
                    editor.putBoolean("castellano",ccastellano);
                    editor.putBoolean("ingles",inggles);
                } else if (pulsa(ingles, event)) {
                    inggles = true;
                    ccastellano = false;
                    editor.putBoolean("castellano",ccastellano);
                    editor.putBoolean("ingles",inggles);
                }
                editor.commit();
                break;
        }
        return idEscena;
    }

    /*
     * Establece el idioma del sistema
     * @param languageCode Codigo del nuevo lenguaje: es, en, gl, ....
     */
    public void changeLanguage(String languageCode) {
        Resources res = getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(languageCode.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

}

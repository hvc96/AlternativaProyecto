package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;


import java.util.ArrayList;

import cuadrado.villar.hadrian.arkanoid.CControl.Escena;

/**
 * Objeto Ladrillo, utilizado en el juego.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Ladrillo {

    Escena a;
    RectF contenedor;
    float x, y;
    public Bitmap imagen;
    public PointF posicion;
    public Paint paint;
    public int cont2 = 0;
    int numImpactos = 2;
    Bitmap ladrilloImagenAmarillo, ladrilloImagenAzul, ladrilloImagenAzulOscuro, ladrilloImagenMarron, ladrilloImagenNaranja, ladrilloImagenRojo, ladrilloImagenVerde, ladrilloImagenVerdeLima, ladrilloImagenVioleta, ladrilloImagenAmarilloRompiendo, ladrilloImagenAzulRompiendo, ladrilloImagenAzulOscuroRompiendo, ladrilloImagenMarronRompiendo, ladrilloImagenNaranjaRompiendo, ladrilloImagenRojoRompiendo, ladrilloImagenVerdeRompiendo, ladrilloImagenVerdeLimaRompiendo, ladrilloImagenVioletaRompiendo;

    /**
     * Constructor de clase
     * @param x Coordenada en x.
     * @param y Coordenada en y.
     * @param imagen Imagen del ladrillo.
     */
    public Ladrillo(float x, float y, Bitmap imagen) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        this.posicion = new PointF(x, y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());
    }

    /**
     * Método para el dibujado del ladrillo.
     *
     * @param c Objeto Canvas para utilizar los métodos útiles para el dibujo.
     */
    public void dibujar(Canvas c) {
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
//        c.drawRect(contenedor, paint);
    }

    /**
     * Devuelve el numero de impactos que le faltan al ladrillo para romperse.
     * @return Numero de impactos que le queda por recibir.
     */
    public int getNumImpactos() {
        return numImpactos;
    }

    /**
     * Método para la colisión entre la bola y un ladrillo.
     * @param bola Objeto bola.
     * @param imagen Imagen actualizada del ladrillo dependiendo de los impactos.
     * @return Si hay colision devuelve true si no false.
     */
    public boolean colisionaLadrillos(Bola bola, Bitmap imagen) {
        if (bola.getContenedor().intersect(contenedor) && bola.isRestaChoque()) {
            bola.setRestaChoque(false);
            numImpactos--;
            this.imagen = imagen;
            return true;
        }

        return false;
    }
//  TODO: Generar los ladrillos aleatoriamente dependiendo de una variable
//    public Bitmap[] coloresRandom(int rand) {
//        Bitmap[] bitmaps = new Bitmap[2];
//        switch (rand) {
//            case 0:
//                bitmaps[0] = ladrilloImagenAmarillo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_amarillo.png");
//                bitmaps[1] = ladrilloImagenAmarilloRompiendo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_amarillo_rompiendo.png");
//                break;
//            case 1:
//                bitmaps[0] = ladrilloImagenAzul = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul.png");
//                bitmaps[1] = ladrilloImagenAzulRompiendo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul_rompiendo.png");
//                break;
//            case 2:
//                bitmaps[0] = ladrilloImagenAzulOscuro = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul_oscuro.png");
//                bitmaps[1] = ladrilloImagenAzulOscuroRompiendo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul_oscuro_rompiendo.png");
//                break;
//            case 3:
//                bitmaps[0] = ladrilloImagenMarron = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_marron.png");
//                bitmaps[1] = ladrilloImagenMarronRompiendo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_marron_rompiendo.png");
//                break;
//            case 4:
//                bitmaps[0] = ladrilloImagenNaranja = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_naranja.png");
//                bitmaps[1] = ladrilloImagenNaranjaRompiendo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_naranja_rompiendo.png");
//                break;
//            case 5:
//
//                bitmaps[0] = ladrilloImagenRojo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_rojo.png");
//                bitmaps[1] = ladrilloImagenRojoRompiendo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_rojo_rompiendo.png");
//                break;
//            case 6:
//                bitmaps[0] = ladrilloImagenVerde = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde.png");
//                bitmaps[1] = ladrilloImagenVerdeRompiendo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde_rompiendo.png");
//                break;
//            case 7:
//                bitmaps[0] = ladrilloImagenVerdeLima = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde_lima.png");
//                bitmaps[1] = ladrilloImagenVerdeLimaRompiendo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde_lima_rompiendo.png");
//                break;
//            case 8:
//                bitmaps[0] = ladrilloImagenVioleta = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo:violeta.png");
//                bitmaps[1] = ladrilloImagenVioletaRompiendo = a.getBitmapFromAssets("Ladrillos/Normales/ladrillo:violeta_rompiendo.png");
//                break;
//        }
//
//return bitmaps;
//    }
}
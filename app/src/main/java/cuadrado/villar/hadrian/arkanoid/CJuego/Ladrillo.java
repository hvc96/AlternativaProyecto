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

public class Ladrillo {

    RectF contenedor;
    float x, y;
    public Bitmap imagen;
    public PointF posicion;
    public Paint paint;
    public int cont2 = 0;
    int numImpactos=2;
    //public int columna, fila;
    //public Ladrillo[][] nivel = new Ladrillo[fila][columna];


    public Ladrillo(float x, float y, Bitmap imagen) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        this.posicion = new PointF(x, y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());

        int padding = 2;

//        contenedor = new RectF(
//                x * imagen.getWidth() + padding,
//                y * imagen.getHeight() + padding,
//                x * imagen.getWidth() + imagen.getWidth() - padding,
//                y * imagen.getHeight() + imagen.getHeight() - padding
//        );

        // https://code.tutsplus.com/es/tutorials/android-sensors-in-depth-proximity-and-gyroscope--cms-28084
        // Sensor movimiento (Giroscopio)
    }

    public void actualizarFisica(int nMax) {

    }

    public RectF getRect() {
        return this.contenedor;
    }

    public void dibujar(Canvas c) {
//        Log.i("ladri",contenedor.left+" "+ contenedor.top+" "+  contenedor.right+" "+  contenedor.bottom);
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
        c.drawRect(contenedor, paint);


    }

    public int getNumImpactos() {
        return numImpactos;
    }

    public boolean colisionaLadrillos(Bola bola, Bitmap imagen) {
//        for (Ladrillo l : alLadrillos) {

            if (bola.getContenedor().intersect(contenedor) && bola.isRestaChoque()) {
                bola.setRestaChoque(false);
              numImpactos--;
              this.imagen=imagen;
                return true;
            }

        return false;
    }
}
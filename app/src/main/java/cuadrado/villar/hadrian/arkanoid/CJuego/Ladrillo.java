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



    public Ladrillo(float x, float y, Bitmap imagen) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        this.posicion = new PointF(x, y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());
    }

    public void actualizarFisica(int nMax) {

    }

    public RectF getRect() {
        return this.contenedor;
    }

    public void dibujar(Canvas c) {
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
        c.drawRect(contenedor, paint);


    }

    public int getNumImpactos() {
        return numImpactos;
    }

    public boolean colisionaLadrillos(Bola bola, Bitmap imagen) {
            if (bola.getContenedor().intersect(contenedor) && bola.isRestaChoque()) {
                bola.setRestaChoque(false);
              numImpactos--;
              this.imagen=imagen;
                return true;
            }

        return false;
    }
}
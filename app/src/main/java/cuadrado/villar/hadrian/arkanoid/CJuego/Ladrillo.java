package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Ladrillo {

    RectF contenedor;
    float velocidadX, velocidadY;
    public Bitmap imagen;
    public PointF posicion;
    public Paint paint;
    public int columna, fila;
    public Ladrillo[][] nivel=new Ladrillo[fila][columna];

    public Ladrillo(int fila, int columna, int altura, int ancho, Bitmap imagen) {

        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());

        int padding = 2;

        contenedor = new RectF(
                columna * imagen.getWidth() + padding,
                fila * imagen.getHeight() + padding,
                columna * imagen.getWidth() + imagen.getWidth() - padding,
                fila * imagen.getHeight() + imagen.getHeight() - padding
        );
    }

    public RectF getRect() {
        return this.contenedor;
    }

    public void construirLadrillos(int columnas, int filas) {

    }
}

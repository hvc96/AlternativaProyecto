package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

public class Ladrillo {
    ArrayList<Ladrillo> alLadrillos = new ArrayList<>();
    RectF contenedor;
    float x, y;
    public Bitmap imagen;
    public PointF posicion;
    public Paint paint;
    public int cont = 0, cont2 = 0;
    //public int columna, fila;
    //public Ladrillo[][] nivel = new Ladrillo[fila][columna];


    public Ladrillo(float x, float y, Bitmap imagen) {
        paint= new Paint();
        paint.setColor(Color.MAGENTA);
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
    }

    public void actualizarFisica(int nMax) {
        creaLadrillos(nMax);
    }

    public RectF getRect() {
        return this.contenedor;
    }

    public void dibujar(Canvas c) {
        for (Ladrillo l : alLadrillos) {
                c.drawRect(l.contenedor.left, l.contenedor.top, l.contenedor.right, l.contenedor.bottom, paint);
                c.drawBitmap(l.imagen, l.posicion.x, l.posicion.y, null);
        }
    }

    public void creaLadrillos(int nMax) {
        if (cont < nMax) {
            if (cont % 5 == 0) {
                posicion.y += imagen.getHeight();
                posicion.x = 0;
            }
            Ladrillo ladrillo = new Ladrillo(posicion.x, posicion.y, imagen);
            alLadrillos.add(ladrillo);
            posicion.x+=imagen.getWidth();
            cont++;
            Log.i("ladrillo", "" + alLadrillos.size() + "----------------------" + cont);
        }
    }
}




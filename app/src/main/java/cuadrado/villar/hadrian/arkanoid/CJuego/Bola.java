package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

public class Bola {

    RectF contenedor;
    float velocidadX, velocidadY, anchoBola = 10, altoBola = 10, x, y;
    public Bitmap imagen;
    public PointF posicion;

    public Bola(Bitmap imagen,float x,float y,float velocidadX,float velocidadY) {
        this.posicion=new PointF(x,y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getWidth());
        this.velocidadX=velocidadX;
        this.velocidadY=velocidadY;
    }

    public void dibujar(Canvas c){
        c.drawBitmap(imagen,posicion.x,posicion.y,null);
    }

    public RectF getContenedor() {
        return contenedor;
    }

    public void actualizarFisica(long fps) {
        contenedor.left = contenedor.left + (velocidadX / fps);
        contenedor.top = contenedor.top + (velocidadY / fps);
        contenedor.right = contenedor.left + anchoBola;
        contenedor.bottom = contenedor.top - altoBola;
    }

    public void reverseYVelocity() {
        velocidadY = -velocidadY;
    }


    public void reverseXVelocity() {
        velocidadX = -velocidadX;
    }

    public void setRandomXVelocity() {
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if (answer == 0) {
            reverseXVelocity();
        }
    }

//    public void clearObstacleY(float y) {
//        contenedor.bottom = y;
//        contenedor.top = y - altoBola;
//    }
//
//    public void clearObstacleX(float x) {
//        contenedor.left = x;
//        contenedor.right = x + anchoBola;
//    }
//
//    public void reset(int x, int y) {
//        contenedor.left = x / 2;
//        contenedor.top = y - 20;
//        contenedor.right = x / 2 + anchoBola;
//        contenedor.bottom = y - 20 - altoBola;
//    }

}

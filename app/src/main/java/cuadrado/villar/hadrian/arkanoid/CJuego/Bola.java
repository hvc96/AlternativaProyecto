package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

public class Bola {

    RectF contenedor;
    float velocidadX, velocidadY, anchoBola = 10, altoBola = 10, x, y;
    public float ancho, alto, x, y;
    public Bitmap imagen;
    public PointF posicion;

    public Bola(int anchoPantalla, int altoPantalla) {

        // Start the ball travelling straight up at 100 pixels per second
        velocidadX = 200;
        velocidadY = -400;


        // Inicializar
        x = anchoPantalla / 2;
        y = altoPantalla - 30;

        // Place the ball in the centre of the screen at the bottom
        // Make it a 10 pixel x 10 pixel square

        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getWidth());
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

    public void clearObstacleY(float y) {
        contenedor.bottom = y;
        contenedor.top = y - altoBola;
    }

    public void clearObstacleX(float x) {
        contenedor.left = x;
        contenedor.right = x + anchoBola;
    }

    public void reset(int x, int y) {
        contenedor.left = x / 2;
        contenedor.top = y - 20;
        contenedor.right = x / 2 + anchoBola;
        contenedor.bottom = y - 20 - altoBola;
    }

}

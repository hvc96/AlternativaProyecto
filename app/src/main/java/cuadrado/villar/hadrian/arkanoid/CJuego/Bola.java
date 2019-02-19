package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.RectF;

import java.util.Random;

public class Bola {

    RectF hitboxBola;
    float velocidadX, velocidadY, anchoBola = 10, altoBola = 10, x, y;


    public Bola(int anchoPantalla, int altoPantalla) {

        // Start the ball travelling straight up at 100 pixels per second
        velocidadX = 200;
        velocidadY = -400;


        // Inicializar
        x = anchoPantalla / 2;
        y = altoPantalla - 30;

        // Place the ball in the centre of the screen at the bottom
        // Make it a 10 pixel x 10 pixel square

        hitboxBola = new RectF(x*1, y*1, x + ancho, y + alto);
    }

    public RectF getHitboxBola() {
        return hitboxBola;
    }

    public void actualizarFisica(long fps) {
        hitboxBola.left = hitboxBola.left + (velocidadX / fps);
        hitboxBola.top = hitboxBola.top + (velocidadY / fps);
        hitboxBola.right = hitboxBola.left + anchoBola;
        hitboxBola.bottom = hitboxBola.top - altoBola;
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
        hitboxBola.bottom = y;
        hitboxBola.top = y - altoBola;
    }

    public void clearObstacleX(float x) {
        hitboxBola.left = x;
        hitboxBola.right = x + anchoBola;
    }

    public void reset(int x, int y) {
        hitboxBola.left = x / 2;
        hitboxBola.top = y - 20;
        hitboxBola.right = x / 2 + anchoBola;
        hitboxBola.bottom = y - 20 - altoBola;
    }

}

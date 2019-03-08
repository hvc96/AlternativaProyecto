package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Random;

import cuadrado.villar.hadrian.arkanoid.CPantallas.Juego;

/**
 * <h1>Bola</h1>
 * Objeto bola, utilizada en el juego.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Bola {

    public RectF contenedor;
    float velocidadX, velocidadY;

    public Bitmap imagen;
    public PointF posicion;
    public Paint paint;
    public boolean perdio = false, signo;
    public boolean restaChoque = true;
    public int altoPantalla;

    public boolean isRestaChoque() {
        return restaChoque;
    }

    public void setRestaChoque(boolean restaChoque) {
        this.restaChoque = restaChoque;
    }

    /**
     * Constructor de clase.
     *
     * @param imagen       Imagen de la bola.
     * @param x            Coordenada x.
     * @param y            Coordenada y.
     * @param velocidadX   Velocidad en el eje x.
     * @param velocidadY   Velocidad en el eje y.
     * @param altoPantalla Alto de la pantalla del dispositivo.
     */
    public Bola(Bitmap imagen, float x, float y, float velocidadX, float velocidadY, int altoPantalla) {
        this.posicion = new PointF(x, y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.altoPantalla = altoPantalla;
        //Para dibujar hitbox en caso de

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * Método para el dibujado de la bola.
     *
     * @param c Objeto Canvas para utilizar los métodos útiles para el dibujo.
     */
    public void dibujar(Canvas c) {
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
        //c.drawRect(contenedor, paint);
    }

    /**
     * Actualiza la posicion del rectángulo contenedor de la imagen para el uso de las colisiones.
     */
    public void actualizarRect() {
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());
    }

    public RectF getContenedor() {
        return contenedor;
    }

    /**
     * Actualiza las fisicas de la bola.
     */
    public void actualizarFisica() {
        this.posicion.x = posicion.x - velocidadX;
        this.posicion.y = posicion.y - velocidadY;
        Log.i("ladri", isRestaChoque() + "");
        actualizarRect();
    }

    /**
     * Invierte la velocidad en y.
     */
    public void reverseYVelocity() {
        velocidadY = -this.getVelocidadY();
    }

    /**
     * Invierte la velocidad en x.
     */
    public void reverseXVelocity() {
        velocidadX = -this.getVelocidadX();
    }

    /**
     * Método para el cálculo de los límites hacia los que se puede dirigir la bola.
     * @param anchoPantalla Ancho de la pantalla.
     */
    public void limites(int anchoPantalla) {
        if (contenedor.right > anchoPantalla || contenedor.left < 0) reverseXVelocity();
        if (contenedor.top < altoPantalla / 20 + getDp(20)) reverseYVelocity();
    }

    //Setters y getters
    public float getVelocidadX() {
        return velocidadX;
    }

    public float getVelocidadY() {
        return velocidadY;
    }

    public void setVelocidadX(float velocidadX) {
        this.velocidadX = velocidadX;
    }

    public void setVelocidadY(float velocidadY) {
        this.velocidadY = velocidadY;
    }


    /**
     * Calculo de las coordenada y en relacion al una pantalla de 1208x775
     *
     * @param pixels coordenada en pixses en realcion a una pantalla de 1208x775
     * @return coordenada adaptada a la resolucion del dispositivo
     */
    public int getDp(int pixels) {
        return (int) ((pixels / 7.75) * altoPantalla) / 100;
    }


//TODO implementar aleatoriedad en la bola.
//    public boolean random5050() {
//
//        Random rand = new Random();
//        int porcentaje = rand.nextInt(100);
//        if (porcentaje < 50) {
//            signo = true;
//        }else{
//            signo=false;
//        }
//        return signo;
//    }
}

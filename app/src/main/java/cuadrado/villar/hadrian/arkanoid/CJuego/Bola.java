package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.MediaPlayer;
import cuadrado.villar.hadrian.arkanoid.R;

/**
 * Objeto bola, utilizada en el juego.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Bola {

    /**
     * Rectangulo donde se dibuja el objeto.
     */
    public RectF contenedor;
    /**
     * Velocidad en el eje x del objeto.
     */
    float velocidadX;
    /**
     * Velocidad en el eje y del objeto.
     */
    float velocidadY;
    /**
     * Imagen del objeto.
     */
    public Bitmap imagen;
    /**
     * Vector de las dos coordenadas x,y;
     */
    public PointF posicion;
    /**
     * Propiedad que determina el cambio del objeto ladrillo (Cantidad de golpes que hay que darle), si esta a true, resta si esta a false, solo rebotaria en el sin eliminarlo.
     */
    public boolean restaChoque = true;
    /**
     * Alto de la pantalla
     */
    public int altoPantalla;
    /**
     * Sonido de colisión del objeto.
     */
    MediaPlayer mColision;


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
    public Bola(Context context,Bitmap imagen, float x, float y, float velocidadX, float velocidadY, int altoPantalla) {
        this.posicion = new PointF(x, y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.altoPantalla = altoPantalla;

        mColision = MediaPlayer.create(context, R.raw.colision);
        mColision.setLooping(false);
    }

    /**
     * Método para el dibujado de la bola.
     *
     * @param c Objeto Canvas para utilizar los métodos útiles para el dibujo.
     */
    public void dibujar(Canvas c) {
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
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
        actualizarRect();
    }

    /**
     * Invierte la velocidad en y.
     */
    public void reverseYVelocity() {
        velocidadY = -this.getVelocidadY();
        mColision.start();
    }

    /**
     * Invierte la velocidad en x.
     */
    public void reverseXVelocity() {
        velocidadX = -this.getVelocidadX();
        mColision.start();
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

    public boolean isRestaChoque() {
        return restaChoque;
    }

    public void setRestaChoque(boolean restaChoque) {
        this.restaChoque = restaChoque;
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

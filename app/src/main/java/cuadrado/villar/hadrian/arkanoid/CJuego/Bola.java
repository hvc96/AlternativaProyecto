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

public class Bola {

    public RectF contenedor;
    float velocidadX, velocidadY;

    public Bitmap imagen;
    public PointF posicion;
    public Paint paint;
    public boolean perdio = false, signo;
    public boolean restaChoque=true;
    public int altoPantalla;

    public boolean isRestaChoque() {
        return restaChoque;
    }

    public void setRestaChoque(boolean restaChoque) {
        this.restaChoque = restaChoque;
    }

    public Bola(Bitmap imagen, float x, float y, float velocidadX, float velocidadY, int altoPantalla) {
        this.posicion = new PointF(x, y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.altoPantalla=altoPantalla;
        //Para dibujar hitbox en caso de

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void dibujar(Canvas c) {
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
        //c.drawRect(contenedor, paint);
    }

    public void actualizarRect() {
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());
    }

    public RectF getContenedor() {
        return contenedor;
    }

    public void actualizarFisica() {
        this.posicion.x = posicion.x - velocidadX;
        this.posicion.y = posicion.y - velocidadY;
        Log.i("ladri", isRestaChoque()+"");
        actualizarRect();
    }

    public void reverseYVelocity() {
        velocidadY = -this.getVelocidadY();
    }

    public void reverseXVelocity() {
        velocidadX = -this.getVelocidadX();
    }


    public boolean random5050() {

        Random rand = new Random();
        int porcentaje = rand.nextInt(100);
        if (porcentaje < 50) {
            signo = true;
        }else{
            signo=false;
        }
        return signo;
    }

    public void limites(int anchoPantalla) {
        if (contenedor.right > anchoPantalla || contenedor.left < 0) reverseXVelocity();
        if (contenedor.top < altoPantalla / 20 + getDp(20)) reverseYVelocity();
    }

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

    public void setContenedor(RectF contenedor) {
        this.contenedor = contenedor;
    }

    public int getDp(int pixels) {
        /**
         * Calculo de las coordenada y en relacion al una pantalla de 1208x775
         * @param pixels coordenada en pixses en realcion a una pantalla de 1208x775
         * @return coordenada adaptada a la resolucion del dispositivo
         */
        return (int) ((pixels / 7.75) * altoPantalla) / 100;
    }
}

package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

public class Bola {

    RectF contenedor;
    float velocidadX, velocidadY;
    public Bitmap imagen;
    public PointF posicion;
    public  Paint paint;
    public boolean perdio=false;

    public Bola(Bitmap imagen,float x,float y,float velocidadX,float velocidadY) {
        this.posicion=new PointF(x,y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());
        this.velocidadX=velocidadX;
        this.velocidadY=velocidadY;

        //Para dibujar hitbox en caso de
        paint= new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void dibujar(Canvas c){
        c.drawBitmap(imagen,posicion.x,posicion.y,null);
        c.drawRect(contenedor, paint);
    }

    public void acutRect(){
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getHeight());
    }

    public RectF getContenedor() {
        return contenedor;
    }

    public void actualizarFisica(long fps) {
        this.posicion.x = posicion.x - velocidadX;
        this.posicion.y = posicion.y - velocidadY;

//        contenedor.left = contenedor.left + (velocidadX / fps);
//        contenedor.top = contenedor.top + (velocidadY / fps);
//        contenedor.right = contenedor.left + imagen.getWidth();
//        contenedor.bottom = contenedor.top - imagen.getHeight();
        acutRect();
    }

    public void reverseYVelocity() { velocidadY = -this.getVelocidadY(); }


    public void reverseXVelocity() { velocidadX = -this.getVelocidadX(); }

    public void setRandomXVelocity() {
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if (answer == 0) {
            reverseXVelocity();
        }
    }

    public void limites(int anchoPantalla){
        if (contenedor.right>anchoPantalla||contenedor.left<0)reverseXVelocity();
        if (contenedor.top<0)reverseYVelocity();
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

    public boolean preguntaPerdio(int altoPantalla){
        if (contenedor.bottom>altoPantalla) {
            return perdio=true;
        }
        return false;
    }



}

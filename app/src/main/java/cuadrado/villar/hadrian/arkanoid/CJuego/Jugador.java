package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Jugador {
    public RectF contenedor;
    public float ancho, alto, x, y, velocidadJugador;
    public Bitmap imagen;
    public PointF posicion;


    public Jugador(Bitmap imagen,float x,float y,float velocidadJugador) {
        this.posicion=new PointF(x,y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getWidth());
        this.velocidadJugador=velocidadJugador;
    }

    public void dibujar(Canvas c){
        c.drawBitmap(imagen,posicion.x,posicion.y,null);
    }

    public RectF getRect() {
        return contenedor;
    }

    public void moverNave(int direccion) {
        switch (direccion) {
            case 1:
                this.posicion.x = direccion - imagen.getWidth() / 2;
                contenedor.right = posicion.x + imagen.getWidth();
                contenedor.left = posicion.x;
                break;
            case 2:
                this.posicion.x = direccion - imagen.getWidth() / 2;
                contenedor.right = posicion.x + imagen.getWidth();
                contenedor.left = posicion.x;
                break;
            default:
                break;

        }


    }
}

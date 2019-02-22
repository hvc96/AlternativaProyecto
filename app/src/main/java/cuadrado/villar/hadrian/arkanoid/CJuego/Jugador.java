package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Jugador {
    public RectF contenedor;
    public float anchoPantalla, x, y, velocidadJugador;
    public Bitmap imagen;
    public PointF posicion;

    public Jugador(Bitmap imagen, float x, float y, float velocidadJugador, float anchoPantalla) {
        this.posicion = new PointF(x, y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getWidth());
        this.velocidadJugador = velocidadJugador;
        this.anchoPantalla = anchoPantalla;
    }

    public void dibujar(Canvas c) {
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
    }

    public RectF getRect() {
        return contenedor;
    }

    public void moverJugador(int direccion) {
        switch (direccion) {
            case 1: //Izquierda
                if (contenedor.left > 0){
                    this.posicion.x = posicion.x - velocidadJugador;
                    contenedor.right = posicion.x + imagen.getWidth();
                    contenedor.left = posicion.x;
                }
                break;
            case 2: //Derecha
                if (contenedor.right<anchoPantalla){
                    this.posicion.x = posicion.x + velocidadJugador;
                    contenedor.right = posicion.x + imagen.getWidth();
                    contenedor.left = posicion.x;
                }
                break;
            default:
                break;

        }


    }
}

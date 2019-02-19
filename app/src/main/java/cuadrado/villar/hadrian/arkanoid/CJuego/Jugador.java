package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

public class Jugador {
    public RectF contenedor;
    public float ancho, alto, x, y, velocidadJugador;
    public Bitmap imagen;
    public PointF posicion;

    public Jugador(int anchoPantalla, int altoPantalla) {


        // Inicializar
        x = anchoPantalla / 2;
        y = altoPantalla - 20;

        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getWidth());
        velocidadJugador = 350;
    }

    public RectF getRect() {
        return contenedor;
    }

//    // Cambiar el movim
//    public void setMovementState(int state){
//        jugadorMoviendo = state;
//    }
//
//    // Actualizacion del movimiento
//    public void actualizarFisica(long fps){
//        if(jugadorMoviendo == izquierda){
//            x = x - velocidadJugador / fps;
//        }
//        if(jugadorMoviendo == derecha){
//            x = x + velocidadJugador / fps;
//        }
//        contenedor.left = x;
//        contenedor.right = x + ancho;
//    }

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

package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.RectF;

public class Jugador {
    public RectF hitboxJugador;
    public float ancho, alto, x, y, velocidadJugador;
    public final int stop = 0;
    public final int izquierda = 1;
    public final int derecha = 2;
    public int jugadorMoviendo = stop;

    public Jugador(int anchoPantalla, int altoPantalla){
        // Dimensiones
        ancho = 130;
        alto = 20;

        // Inicializar
        x = anchoPantalla / 2;
        y = altoPantalla - 20;

        hitboxJugador = new RectF(x*1, y*1, x + ancho, y + alto);
        velocidadJugador = 350;
    }

    public RectF getRect(){
        return hitboxJugador;
    }

    // Cambiar el movim
    public void setMovementState(int state){
        jugadorMoviendo = state;
    }

    // Update del movimiento
    public void update(long fps){
        if(jugadorMoviendo == izquierda){
            x = x - velocidadJugador / fps;
        }
        if(jugadorMoviendo == derecha){
            x = x + velocidadJugador / fps;
        }
        hitboxJugador.left = x;
        hitboxJugador.right = x + ancho;
    }
}

package cuadrado.villar.hadrian.arkanoid.CJuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Objeto jugador, utilizado en el juego.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Jugador {
    /**
     * Rectangulo donde se dibuja el objeto.
     */
    public RectF contenedor;
    /**
     * Ancho de la pantalla.
     */
    public float anchoPantalla;
    /**
     * Coordenada en el eje y.
     */
    public float y;
    /**
     * Velocidad en el eje x.
     */
    public float velocidadJugador;
    /**
     * Imagen del objeto.
     */
    public Bitmap imagen;
    /**
     * Vector de coordenadas x,y;
     */
    public PointF posicion;
    /**
     * Rectangulo donde detecta la colision en el medio del objeto.
     */
    public RectF centro;
    /**
     * Rectangulo donde detecta la colision en el centro-izquierda del objeto.
     */
    public RectF ci;
    /**
     * Rectangulo donde detecta la colision en el centro-derecha del objeto.
     */
    public RectF cd;
    /**
     * Rectangulo donde detecta la colision en el extremo izquierdo del objeto.
     */
    public RectF ei;
    /**
     * Rectangulo donde detecta la colision en el extremo derecho del objeto.
     */
    public RectF ed;
    /**
     * Tamaño total del objeto.
     */
    float size;
    /**
     * Número de partes en el que se divide el objeto.
     */
    float partes;
    /**
     * Número de partes.
     */
    int numPartes = 5;

    /**
     * Constructor de clase.
     * @param imagen Imagen dl jugador.
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @param velocidadJugador Velocidad del jugador en la coordenada x.
     * @param anchoPantalla Ancho de la pantalla.
     */
    public Jugador(Bitmap imagen, float x, float y, float velocidadJugador, float anchoPantalla) {
        this.posicion = new PointF(x, y);
        this.imagen = imagen;
        contenedor = new RectF(posicion.x, posicion.y, posicion.x + imagen.getWidth(), posicion.y + imagen.getWidth());
        this.velocidadJugador = velocidadJugador;
        this.anchoPantalla = anchoPantalla;
        this.size = contenedor.right - contenedor.left;
        this.partes = this.size / numPartes;
        this.ei = new RectF(contenedor.left, contenedor.top, contenedor.left + partes, contenedor.bottom);
        this.ci = new RectF(contenedor.left + partes, contenedor.top, contenedor.left + partes * 2, contenedor.bottom);
        this.centro = new RectF(contenedor.left + partes * 2, contenedor.top, contenedor.left + partes * 3, contenedor.bottom);
        this.cd = new RectF(contenedor.left + partes * 3, contenedor.top, contenedor.left + partes * 4, contenedor.bottom);
        this.ed = new RectF(contenedor.left + partes * 4, contenedor.top, contenedor.right, contenedor.bottom);
    }

    /**
     * Método para el dibujado del jugador.
     *
     * @param c Objeto Canvas para utilizar los métodos útiles para el dibujo.
     */
    public void dibujar(Canvas c) {
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
    }

    /**
     * Método para mover al jugador en una dirección.
     * @param direccion si es 1 es izquierda, si es 2 derecha.
     */
    public void moverJugador(int direccion) {
        switch (direccion) {
            case 1: //Izquierda
                if (contenedor.left > 0) {
                    this.posicion.x = posicion.x - velocidadJugador;
                    contenedor.right = posicion.x + imagen.getWidth();
                    contenedor.left = posicion.x;
                }
                break;
            case 2: //Derecha
                if (contenedor.right < anchoPantalla) {
                    this.posicion.x = posicion.x + velocidadJugador;
                    contenedor.right = posicion.x + imagen.getWidth();
                    contenedor.left = posicion.x;
                }
                break;
            default:
                break;

        }

        actualizarRects();
    }

    /**
     * Funciona igual que un actualizar de un rectángulo habitual, pero lo descomponemos en varios segmentos.
     */
    public void actualizarRects(){
        //Actualizar contenedores de jugador
        this.ei = new RectF(contenedor.left, contenedor.top, contenedor.left + partes, contenedor.bottom);
        this.ci = new RectF(contenedor.left + partes, contenedor.top, contenedor.left + partes * 2, contenedor.bottom);
        this.centro = new RectF(contenedor.left + partes * 2, contenedor.top, contenedor.left + partes * 3, contenedor.bottom);
        this.cd = new RectF(contenedor.left + partes * 3, contenedor.top, contenedor.left + partes * 4, contenedor.bottom);
        this.ed = new RectF(contenedor.left + partes * 4, contenedor.top, contenedor.right, contenedor.bottom);
    }

    /**
     * Método para mover al jugador mediante el giroscopio.
     * @param nuevaPosX Coordenada en x proporcionada por el sensor.
     */
    public void moverJugadorGiroscopio(float nuevaPosX){
        this.posicion.x=nuevaPosX-imagen.getWidth()/2;
        this.actualizarRects(); // No se porque razon no me los actualiza si los llamo desde aqui
    }
}

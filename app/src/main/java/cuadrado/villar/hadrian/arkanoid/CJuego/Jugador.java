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
    public RectF centro, ci, cd, ei, ed;
    float size = 0;
    float partes;
    int numPartes = 5;

    Paint p1, p2, p3, p4, p5;

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
        p1 = new Paint();
        p1.setColor(Color.RED);
        p1.setStyle(Paint.Style.STROKE);
        p2 = new Paint();
        p2.setColor(Color.GREEN);
        p2.setStyle(Paint.Style.STROKE);
        p3 = new Paint();
        p3.setColor(Color.BLUE);
        p3.setStyle(Paint.Style.STROKE);
        p4 = new Paint();
        p4.setColor(Color.CYAN);
        p4.setStyle(Paint.Style.STROKE);
        p5 = new Paint();
        p5.setColor(Color.YELLOW);
        p5.setStyle(Paint.Style.STROKE);


    }


    public void dibujar(Canvas c) {
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
        c.drawRect(ei, p1);
        c.drawRect(ci, p2);
        c.drawRect(centro, p3);
        c.drawRect(cd, p4);
        c.drawRect(ed, p5);
    }

    public RectF getRect() {
        return contenedor;
    }

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
    public void actualizarRects(){
        //Actualizar contenedores de jugador
        this.ei = new RectF(contenedor.left, contenedor.top, contenedor.left + partes, contenedor.bottom);
        this.ci = new RectF(contenedor.left + partes, contenedor.top, contenedor.left + partes * 2, contenedor.bottom);
        this.centro = new RectF(contenedor.left + partes * 2, contenedor.top, contenedor.left + partes * 3, contenedor.bottom);
        this.cd = new RectF(contenedor.left + partes * 3, contenedor.top, contenedor.left + partes * 4, contenedor.bottom);
        this.ed = new RectF(contenedor.left + partes * 4, contenedor.top, contenedor.right, contenedor.bottom);
    }

    public void moverJugadorGiroscopio(float nuevaPosX){
        this.posicion.x=nuevaPosX-imagen.getWidth()/2;
        this.actualizarRects();
    }
}

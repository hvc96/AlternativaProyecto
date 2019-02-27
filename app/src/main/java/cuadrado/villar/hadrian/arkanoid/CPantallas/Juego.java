package cuadrado.villar.hadrian.arkanoid.CPantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import cuadrado.villar.hadrian.arkanoid.CControl.Escena;
import cuadrado.villar.hadrian.arkanoid.CJuego.Bola;
import cuadrado.villar.hadrian.arkanoid.CJuego.Jugador;
import cuadrado.villar.hadrian.arkanoid.CJuego.Ladrillo;

public class Juego extends Escena {
    float dedoCoordX, dedoCoordY;
    int movimiento;
    boolean pulsandoIzquierda, pulsandoDerecha, reseteado = false, perderVida = false;
    RectF izquierda, derecha;
    Bola bola;
    Jugador jugador;
    Bitmap jugadorImagen, bolaImagen, ladrilloImagenAmarillo, ladrilloImagenAzulOscuro, ladrilloImagenMarron, ladrilloImagenAzul, ladrilloImagenNaranja, ladrilloImagenOscuro, ladrilloImagenRojo, ladrilloImagenVerde, ladrilloImagenVerdeLima, ladrilloImagenVioleta, ladrilloImagenAmarilloRompiendo, ladrilloImagenAzulRompiendo, ladrilloImagenAzulOscuroRompiendo, ladrilloImagenMarronRompiendo, ladrilloImagenNaranjaRompiendo, ladrilloImagenOscuroRompiendo, ladrilloImagenRojoRompiendo, ladrilloImagenVerdeRompiendo, ladrilloImagenVerdeLimaRompiendo, ladrilloImagenVioletaRompiendo, vidaImagen;
    float velocidadJugador = 10, velocidadBolaX = 25, velocidadBolaY = 15;
//    Ladrillo ladrillo;
    Paint pTextoblanco;
    ArrayList<Ladrillo> ladrillos;

    public Juego(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        pTextoblanco= new Paint();
        pTextoblanco.setColor(Color.WHITE);
        pTextoblanco.setTextSize(getDp(40));

        izquierda = new RectF(0, 0, anchoPantalla / 2, altoPantalla);
        derecha = new RectF(anchoPantalla / 2, 0, anchoPantalla, altoPantalla);

        jugadorImagen = getBitmapFromAssets("Jugador/jugador_moviendose_1.png");
        jugadorImagen = Bitmap.createScaledBitmap(jugadorImagen, getDp(100), getDp(30), false);
        jugador = new Jugador(jugadorImagen, anchoPantalla / 2, altoPantalla - getDp(30), velocidadJugador, anchoPantalla);

        bolaImagen = getBitmapFromAssets("Bola/bola.png");
        bolaImagen = Bitmap.createScaledBitmap(bolaImagen, getDp(15), getDp(15), false);
        bola = new Bola(bolaImagen, anchoPantalla / 2, altoPantalla - getDp(55), velocidadBolaX, velocidadBolaY);

        //Ladrillos normales
        ladrilloImagenAmarillo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_amarillo.png");
        ladrilloImagenAmarillo = Bitmap.createScaledBitmap(ladrilloImagenAmarillo, getDp(80), getDp(20), false);
        ladrilloImagenAzul = getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul.png");
        ladrilloImagenAzul = Bitmap.createScaledBitmap(ladrilloImagenAzul, getDp(80), getDp(20), false);
        ladrilloImagenAzulOscuro = getBitmapFromAssets("Ladrillos/Normales/ladrillo_azul_oscuro.png");
        ladrilloImagenAzulOscuro = Bitmap.createScaledBitmap(ladrilloImagenAzulOscuro, getDp(80), getDp(20), false);
        ladrilloImagenMarron = getBitmapFromAssets("Ladrillos/Normales/ladrillo_marron.png");
        ladrilloImagenMarron = Bitmap.createScaledBitmap(ladrilloImagenMarron, getDp(80), getDp(20), false);
        ladrilloImagenNaranja = getBitmapFromAssets("Ladrillos/Normales/ladrillo_naranja.png");
        ladrilloImagenNaranja = Bitmap.createScaledBitmap(ladrilloImagenNaranja, getDp(80), getDp(20), false);
        ladrilloImagenOscuro = getBitmapFromAssets("Ladrillos/Normales/ladrillo_oscuro.png");
        ladrilloImagenOscuro = Bitmap.createScaledBitmap(ladrilloImagenOscuro, getDp(80), getDp(20), false);
        ladrilloImagenRojo = getBitmapFromAssets("Ladrillos/Normales/ladrillo_rojo.png");
        ladrilloImagenRojo = Bitmap.createScaledBitmap(ladrilloImagenRojo, getDp(80), getDp(20), false);
        ladrilloImagenVerde = getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde.png");
        ladrilloImagenVerde = Bitmap.createScaledBitmap(ladrilloImagenVerde, getDp(80), getDp(20), false);
        ladrilloImagenVerdeLima = getBitmapFromAssets("Ladrillos/Normales/ladrillo_verde_lima.png");
        ladrilloImagenVerdeLima = Bitmap.createScaledBitmap(ladrilloImagenVerdeLima, getDp(80), getDp(20), false);
        ladrilloImagenVioleta = getBitmapFromAssets("Ladrillos/Normales/ladrillo_violeta.png");
        ladrilloImagenVioleta = Bitmap.createScaledBitmap(ladrilloImagenVioleta, getDp(80), getDp(20), false);

        //Ladrillos rompiendose
        ladrilloImagenAmarilloRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_amarillo_rompiendo.png");
        ladrilloImagenAmarilloRompiendo = Bitmap.createScaledBitmap(ladrilloImagenAmarilloRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenAzulRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_azul_rompiendo.png");
        ladrilloImagenAzulRompiendo = Bitmap.createScaledBitmap(ladrilloImagenAzulRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenAzulOscuroRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_azul_oscuro_rompiendo.png");
        ladrilloImagenAzulOscuroRompiendo = Bitmap.createScaledBitmap(ladrilloImagenAzulOscuroRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenMarronRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_marron_rompiendo.png");
        ladrilloImagenMarronRompiendo = Bitmap.createScaledBitmap(ladrilloImagenMarronRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenNaranjaRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_naranja_rompiendo.png");
        ladrilloImagenNaranjaRompiendo = Bitmap.createScaledBitmap(ladrilloImagenNaranjaRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenOscuroRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_oscuro_rompiendo.png");
        ladrilloImagenOscuroRompiendo = Bitmap.createScaledBitmap(ladrilloImagenOscuroRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenRojoRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_rojo_rompiendo.png");
        ladrilloImagenRojoRompiendo = Bitmap.createScaledBitmap(ladrilloImagenRojoRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenVerdeRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_verde_rompiendo.png");
        ladrilloImagenVerdeRompiendo = Bitmap.createScaledBitmap(ladrilloImagenVerdeRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenVerdeLimaRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_verde_lima_rompiendo.png");
        ladrilloImagenVerdeLimaRompiendo = Bitmap.createScaledBitmap(ladrilloImagenVerdeLimaRompiendo, getDp(80), getDp(20), false);
        ladrilloImagenVioletaRompiendo = getBitmapFromAssets("Ladrillos/Rompiendo/ladrillo_violeta_rompiendo.png");
        ladrilloImagenVioletaRompiendo = Bitmap.createScaledBitmap(ladrilloImagenVioletaRompiendo, getDp(80), getDp(20), false);

//        ladrillo = new Ladrillo(0, 0, ladrilloImagenAmarillo);

        //Vida
        vidaImagen = getBitmapFromAssets("Jugador/Vida/vida.png");
        vidaImagen = Bitmap.createScaledBitmap(vidaImagen, getDp(100), getDp(100), false);
        ladrillos=creaLadrillos(25);
    }


    public  ArrayList<Ladrillo> creaLadrillos(int nMax) {
        ArrayList<Ladrillo> alLadrillos = new ArrayList<>();
        int cont = 0;

        int y=0,x=0;
        for (int i = 0; i < nMax; i++) {
            if (cont % 5 == 0) {
                y += ladrilloImagenAmarillo.getHeight();
                x = 0;
            }
            Ladrillo ladrillo = new Ladrillo(x,y, ladrilloImagenAmarillo);
            alLadrillos.add(ladrillo);
            x += ladrilloImagenAmarillo.getWidth();
            cont++;
        }

        return alLadrillos;
    }

    // Actualizamos la física de los elementos comunes en pantalla
    public void actualizarFisica() {
//        ladrillo.actualizarFisica(25);

        for (int i = ladrillos.size()-1; i >=0; i--) {
            if (ladrillos.get(i).colisionaLadrillos(bola, ladrilloImagenAmarilloRompiendo)) {
//                bola.reverseXVelocity();
            bola.reverseYVelocity();
                if (ladrillos.get(i).getNumImpactos()<=0) ladrillos.remove(i);
            }



//
        }
        switch (movimiento) {
            case 1:
                if (pulsandoIzquierda) jugador.moverJugador(1);
                pulsandoDerecha = false;
                break;
            case 2:
                if (pulsandoDerecha) jugador.moverJugador(2);
                pulsandoIzquierda = false;
                break;
        }
        bola.actualizarFisica(60);
        bola.limites(anchoPantalla);

        if (bola.getContenedor().bottom > altoPantalla) {
            perderVida = true;
        }

        resetPosicion(perderVida);

        if (bola.getContenedor().top>altoPantalla/2) bola.setRestaChoque(true);

        if (bola.getContenedor().intersect(jugador.ei)) {
            bola.setRestaChoque(true);
            Log.i("velei", " " + bola.getVelocidadX());
            if (bola.getVelocidadX() > 0) {
                bola.reverseYVelocity();
            } else {
                bola.reverseYVelocity();
                bola.reverseXVelocity();
            }
        } else if (bola.getContenedor().intersect(jugador.ci)) {
            Log.i("velci", " " + bola.getVelocidadX());
            bola.reverseYVelocity();

        } else if (bola.getContenedor().intersect(jugador.centro)) {
            Log.i("velcentro", " " + bola.getVelocidadX());
            bola.reverseYVelocity();

        } else if (bola.getContenedor().intersect(jugador.cd)) {
            Log.i("velcd", " " + bola.getVelocidadX());
            bola.reverseYVelocity();

        } else if (bola.getContenedor().intersect(jugador.ed)) {
            Log.i("veled", " " + bola.getVelocidadX());
            if (bola.getVelocidadX() > 0) {
                bola.reverseYVelocity();
                bola.reverseXVelocity();


            } else {
                bola.reverseYVelocity();
            }
        }
    }

    // Rutina de dibujo en el lienzo de los elementos comunes. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {

            c.drawColor(Color.BLACK); // Establecemos un color de fondo. En este caso negro
            jugador.dibujar(c);
            bola.dibujar(c);
           for (Ladrillo l:ladrillos) l.dibujar(c);
            if (reseteado) {
                c.drawBitmap(vidaImagen, anchoPantalla / 2-vidaImagen.getWidth()/2, altoPantalla / 2, null);
                c.drawText("-1",anchoPantalla / 2-pTextoblanco.getTextSize()/2, altoPantalla / 2+ vidaImagen.getHeight()/2+getDp(12),pTextoblanco);
            }
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    public int onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();        //Obtenemos el índice de la acción
        int pointerID = event.getPointerId(pointerIndex); //Obtenemos el Id del pointer asociado a la acción
        int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación
        switch (accion) {
            case MotionEvent.ACTION_DOWN:           // Primer dedo toca
                dedoCoordX = event.getX();
                dedoCoordY = event.getY();
                if (izquierda.contains(dedoCoordX, dedoCoordY)) {
                    pulsandoIzquierda = true;
                    movimiento = 1;// Mover izquierda
                    jugador.moverJugador(1);
                } else if (derecha.contains(dedoCoordX, dedoCoordY)) {
                    pulsandoDerecha = true;
                    movimiento = 2;// Mover derecha
                    jugador.moverJugador(2);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                pulsandoIzquierda = false;
                pulsandoDerecha = false;
                break;

            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos
                reseteado = false;
                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) return idPadre;

        return idEscena;
    }

    public void resetPosicion(boolean reset) {
        if (reset) {
            bola.posicion.x = anchoPantalla / 2;
            bola.posicion.y = altoPantalla - getDp(55);
            bola.actualizarRect();
            reseteado = true;
        }
    }

}

package cuadrado.villar.hadrian.arkanoid.CControl;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cuadrado.villar.hadrian.arkanoid.CPantallas.Ayuda;
import cuadrado.villar.hadrian.arkanoid.CPantallas.Creditos;
import cuadrado.villar.hadrian.arkanoid.CPantallas.Juego;
import cuadrado.villar.hadrian.arkanoid.CPantallas.Menu;
import cuadrado.villar.hadrian.arkanoid.CPantallas.Opciones;
import cuadrado.villar.hadrian.arkanoid.CPantallas.Records;

/**
 * Clase donde se controla el cambio entre pantallas.
 *
 * @author Hadrián Villar Cuadrado
 */
public class Manejador  extends SurfaceView implements SurfaceHolder.Callback{
    /**
     * Interfaz abstracta para manejar la superficie de dibujado.
     */
    private SurfaceHolder surfaceHolder;
    /**
     * Contexto de la aplicación.
     */
    private Context context;
    /**
     * Ancho de la pantalla, su valor se actualiza en el método surfaceChanged.
     */
    private int anchoPantalla=1;
    /**
     * Alto de la pantalla, su valor se actualiza en el método surfaceChanged.
     */
    private int altoPantalla=1;
    /**
     * Hilo encargado de dibujar y actualizar la física.
     */
    private Hilo hilo;
    /**
     * Control del hilo.
     */
    private boolean funcionando = false;
    /**
     * Escena actual.
     */
    Escena escenaActual;

    /**
     * Constructor de clase.
     * @param context Contexto de la aplicación.
     */
    public Manejador(Context context) {
        super(context);
        this.surfaceHolder = getHolder();       // Se obtiene el holder
        this.surfaceHolder.addCallback(this);   // Se indica donde van las funciones callback
        this.context = context;                 // Obtenemos el contexto
        hilo = new Hilo();                      // Inicializamos el hilo
        setFocusable(true);                     // Aseguramos que reciba eventos de toque
    }


    /**
     * Método para la gestión de la pulsación en la pantalla.
     * @param event Evento ocasionado al tocar la pantalla.
     * @return Devuelve la escena en la que se encuentra.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (surfaceHolder) {

            int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación

            int codEscena = escenaActual.onTouchEvent(event);

            if (codEscena != escenaActual.idEscena) {
                switch (accion) {
                    case MotionEvent.ACTION_DOWN:           // Primer dedo toca
                    case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                        break;

                    case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
                    case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                        switch (codEscena) {
                            case 0:
                                escenaActual = new Menu(context, 0, anchoPantalla, altoPantalla);
                                break;
                            case 10:
                                escenaActual = new Ayuda(context, 10, anchoPantalla, altoPantalla);
                                break;
                            case 20:
                                escenaActual = new Creditos(context, 20, anchoPantalla, altoPantalla);
                                break;
                            case 30:
                                escenaActual = new Opciones(context, 30, anchoPantalla, altoPantalla);
                                break;
                            case 40:
                                escenaActual = new Records(context, 40, anchoPantalla, altoPantalla);
                                break;
                            case 100:
                                escenaActual = new Juego(context, 100, anchoPantalla, altoPantalla);
                                break;
                        }
                        break;
                }
            }
        }
        return true;
    }

    /**
     * Una vez creado se lanza el hilo.
     * @param surfaceHolder Manejador de la pantalla.
     */
    // Callbacks del SurfaceHolder ///////////////////////////////////
    @Override // En cuanto se crea el SurfaceView lanzamos el hilo
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    /**
     * Se detiene el hilo una vez se termina el uso del surface.
     * @param surfaceHolder Manejador de la pantalla.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        hilo.setFuncionando(false);  // Se para el hilo
        try {
            hilo.join();   // Se espera a que finalize
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se ejecuta cuando cambiamos la pantalla.
     * @param holder  Manejador de pantalla.
     * @param format Formato de pantalla.
     * @param width Ancho de pantalla.
     * @param height Alto de pantalla.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        anchoPantalla = width;               // se establece el nuevo ancho de pantalla
        altoPantalla = height;               // se establece el nuevo alto de pantalla

        escenaActual=new Menu(context,0,anchoPantalla,altoPantalla);

        hilo.setSurfaceSize(width,height);   // se establece el nuevo ancho y alto de pantalla en el hilo
        hilo.setFuncionando(true); // Se le indica al hilo que puede arrancar
        if (hilo.getState() == Thread.State.NEW) hilo.start(); // si el hilo no ha sido creado se crea;
        if (hilo.getState() == Thread.State.TERMINATED) {      // si el hilo ha sido finalizado se crea de nuevo;
            hilo=new Hilo();
            hilo.start(); // se arranca el hilo
        }

    }

    /**
     * Hilo que permite lanzar los métodos dibujar y actualizar fisica en las distintas clases de la aplicación.
     */
    // Clase Hilo en la cual implementamos el método de dibujo (y física) para que se haga en paralelo con la gestión de la interfaz de usuario
    class Hilo extends Thread {
        public Hilo(){
        }

        @Override
        public void run() {
            while (funcionando) {
                Canvas c = null; //Necesario repintar xtodo el lienzo
                try {
                    if (!surfaceHolder.getSurface().isValid()) continue; // si la superficie no está preparada repetimos
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        c = surfaceHolder.lockHardwareCanvas(); // Obtenemos el lienzo con Aceleración Hw. Desde la API 26
                    }else c = surfaceHolder.lockCanvas(); // Obtenemos el lienzo con aceleración software

                    synchronized (surfaceHolder) {
                        if (c != null) {
                            escenaActual.dibujar(c);
                            escenaActual.actualizarFisica();  // Movimiento de los elementos
                        }
                    }
                } finally {  // Haya o no excepción, hay que liberar el lienzo
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        /**
         * Activa o desactiva el funcionamiento del hilo.
         * @param flag Si es true funciona, si es false no.
         */
        void setFuncionando(boolean flag) {
            funcionando = flag;
        }

        /**
         * Función que es llamada si cambia el tamaño de la pantalla o la orientación.
         * @param width Ancho de la pantalla.
         * @param height Alto de la pantalla.
         */
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {
            }
        }
    }
}

package cuadrado.villar.hadrian.arkanoid.CControl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cuadrado.villar.hadrian.arkanoid.CPantallas.Menu;

import static cuadrado.villar.hadrian.arkanoid.CControl.Escena.prefs;

/**
 * Clase donde se controla la creacíon, pausa, etc de la aplicación.
 *
 * @author Hadrián Villar Cuadrado
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Método que se ejecuta cuando se instala la aplicacion
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Manejador manejador = new Manejador(this);
        manejador.setKeepScreenOn(true);
        setContentView(manejador); // establecemos la vista del activity a la clase creada

        getSupportActionBar().hide();
    }

    /**
     * Método que se ejecuta cuando se sale de la aplicacion sin llegar a cerrarla, pausandola por ende.
     */
    @Override
    protected void onPause() {
        super.onPause();
//        if (prefs.getBoolean("play",true))
//        menu.mediaPlayer.pause();
    }

    /**
     * Se ejecuta cuando se vuelve del estado onpause.
     */
    @Override
    protected void onResume() {
        super.onResume();
//        if (prefs.getBoolean("play",true))
//            menu.mediaPlayer.start();
    }

}
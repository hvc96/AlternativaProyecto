package cuadrado.villar.hadrian.arkanoid.CControl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Clase donde se trata la base de datos con SQLite.
 *
 * @author Hadrián Villar Cuadrado
 */
public class BaseDatos extends SQLiteOpenHelper {
    String sqlCreateTable = " CREATE TABLE IF NOT EXISTS puntos ('numero' INT)";

    /**
     * Constructor de clase.
     *
     * @param context Contexto.
     * @param nombre  Nombre de la base de datos que se creará.
     * @param factory Se crea el objeto cursor.
     * @param version Versión de la base de datos utilizada.
     */
    public BaseDatos(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);

    }

    /**
     * Método para la creación de la base de datos.
     *
     * @param db Objeto SQLiteDatabase, nos permite trabajar con los datos de SQLite.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTable);
    }

    /**
     * Método para actualizar la base de datos.
     *
     * @param db         Objeto SQLiteDatabase, nos permite trabajar con los datos de SQLite.
     * @param oldVersion Versión antigua de la base de datos.
     * @param newVersion Versión nueva de la base de datos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Solución más simple. Borro la tabla y la creo con el nuevo formato.
        // Es recomentable migrar previamente los datos
        db.execSQL("DROP TABLE IF EXISTS puntos ");
        db.execSQL(sqlCreateTable);
    }

    /**
     * Método para el cálculo de las posiciones e inserción en la clase Records.
     *
     * @param db Objeto SQLiteDatabase, nos permite trabajar con los datos de SQLite.
     * @return Devuelve un AttayList de Integers con los datos de la consulta.
     */
    public ArrayList mostrarRecords(SQLiteDatabase db) {

        ArrayList<Integer> alRecords;
        alRecords= new ArrayList<>();

        String query = "SELECT * FROM puntos ORDER BY numero DESC";
        int cont = 0;
        int num =0;

        Cursor resultadoSql = db.rawQuery(query, null);
        if (resultadoSql != null) {
            if (resultadoSql.moveToFirst()) {
                do {
                    cont++;
                    num=resultadoSql.getInt(resultadoSql.getColumnIndex("numero"));
                    alRecords.add(num);
                    if (cont == 3) break;
                } while (resultadoSql.moveToNext());
            }
            resultadoSql.close();
        }
        return alRecords;
    }

    /**
     * Método para la introducción de datos en esta.
     *
     * @param db         Objeto SQLiteDatabase, nos permite trabajar con los datos de SQLite.
     * @param puntuacion Última puntuacón conseguida por el jugador.
     */
    public void introducirPuntuacion(SQLiteDatabase db, int puntuacion) {
        String query = "INSERT INTO puntos(numero) VALUES(" + puntuacion + ")";
        db.execSQL(query);
    }

}



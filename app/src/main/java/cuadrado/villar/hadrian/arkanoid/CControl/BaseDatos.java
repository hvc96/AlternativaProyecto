package cuadrado.villar.hadrian.arkanoid.CControl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * <h1>Base de Datos</h1>
 * Clase donde se trata la base de datos con SQLite.
 *
 * @author Hadrián Villar Cuadrado
 */
public class BaseDatos extends SQLiteOpenHelper {
    String sqlCreateTable = " CREATE TABLE IF NOT EXISTS puntos (numero INTEGER)";
    String sqlInsertPts = "INSERT INTO puntos (numero) VALUES" +
            "(0), " +
            "(0), " +
            "(0) ";
    ArrayList<Integer> alRecords;

    /**
     * Constructor de clase.
     * @param context Contexto.
     * @param nombre Nombre de la base de datos que se creará.
     * @param factory Se crea el objeto cursor.
     * @param versión Versión de la base de datos utilizada.
     */
    public BaseDatos(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int versión) {
        super(context, nombre, factory, versión);

    }

    /**
     * Método para la creación de la base de datos.
     * @param db Objeto SQLiteDatabase, nos permite trabajar con los datos de SQLite.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTable);
        db.execSQL(sqlInsertPts);
    }

    /**
     * Método para actualizar la base de datos.
     * @param db Objeto SQLiteDatabase, nos permite trabajar con los datos de SQLite.
     * @param oldVersion Versión antigua de la base de datos.
     * @param newVersion Versión nueva de la base de datos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Solución más simple. Borro la tabla y la creo con el nuevo formato.
        // Es recomentable migrar previamente los datos
        db.execSQL("DROP TABLE IF EXISTS puntos ");
        db.execSQL(sqlCreateTable);
        db.execSQL(sqlInsertPts);
    }

    /**
     * Método para el cálculo de las posiciones e inserción en la clase Records.
     * @param db Objeto SQLiteDatabase, nos permite trabajar con los datos de SQLite.
     * @return Devuelve un AttayList de Integers con los datos de la consulta.
     */
    public ArrayList mostrarRecords(SQLiteDatabase db){

        String query="SELECT * FROM puntos ORDER BY numero ASC LIMIT 3";
        Cursor resultadoSql = db.rawQuery(query, null);
        if (resultadoSql != null) {
            if (resultadoSql.moveToFirst()) {
                do {
                    alRecords.add(resultadoSql.getInt(resultadoSql.getColumnIndex("numero")));
                } while (resultadoSql.moveToNext());
            }
            resultadoSql.close();
        }
        return alRecords;
    }

    /**
     * Método para la introducción de datos en esta.
     * @param db Objeto SQLiteDatabase, nos permite trabajar con los datos de SQLite.
     * @param puntuacion Última puntuacón conseguida por el jugador.
     */
    public void introducirPuntuacion(SQLiteDatabase db, int puntuacion){
        String query="INSERT INTO puntos(numero) VALUES("+puntuacion+")";
        db.execSQL(query);
    }

}
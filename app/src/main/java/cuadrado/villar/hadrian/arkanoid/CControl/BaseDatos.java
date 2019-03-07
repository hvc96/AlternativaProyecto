package cuadrado.villar.hadrian.arkanoid.CControl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BaseDatos extends SQLiteOpenHelper {
    String sqlCreateTable = " CREATE TABLE IF NOT EXISTS puntos (numero INTEGER)";
    String sqlInsertPts = "INSERT INTO puntos (numero) VALUES" +
            "(0), " +
            "(0), " +
            "(0) ";
    int[] arrayPuntos;

    public BaseDatos(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int versión) {
        super(context, nombre, factory, versión);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTable);
        db.execSQL(sqlInsertPts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Solución más simpl   e. Borro la tabla y la creo con el nuevo formato.
        // Es recomentable migrar previamente los datos
        db.execSQL("DROP TABLE IF EXISTS puntos ");
        db.execSQL(sqlCreateTable);
        db.execSQL(sqlInsertPts);
    }

    public void mostrarRecords(SQLiteDatabase db){
        String query="SELECT * FROM puntos ORDER BY numero ASC LIMIT 3";
        Cursor resultadoSql = db.rawQuery(query, null);
        if (resultadoSql != null) {
            if (resultadoSql.moveToFirst()) {
                do {
                    arrayPuntos[0]=resultadoSql.getInt(resultadoSql.getColumnIndex("numero"));
                } while (resultadoSql.moveToNext());
            }
            resultadoSql.close();
        }
    }

    public void introducirPuntuacion(SQLiteDatabase db, int puntuacion){
        String query="INSERT INTO puntos(numero) VALUES("+puntuacion+")";
        db.execSQL(query);
    }

}
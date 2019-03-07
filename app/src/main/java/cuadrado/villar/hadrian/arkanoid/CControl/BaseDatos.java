package cuadrado.villar.hadrian.arkanoid.CControl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
    String sqlCreateTable = " CREATE TABLE IF NOT EXISTS puntos (id INTEGER PRIMARY KEY, numero INTEGER)";

    public BaseDatos(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int versión) {
        super(context, nombre, factory, versión);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTable);
        db.execSQL(
                "INSERT INTO puntos (id,numero) VALUES" +
                        "(1, 0), " +
                        "(2, 0), " +
                        "(3, 0) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Solución más simpl   e. Borro la tabla y la creo con el nuevo formato.
        // Es recomentable migrar previamente los datos
        db.execSQL("DROP TABLE IF EXISTS puntos ");
        db.execSQL(sqlCreateTable);
    }
}
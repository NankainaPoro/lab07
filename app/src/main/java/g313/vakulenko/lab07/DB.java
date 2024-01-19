package g313.vakulenko.lab07;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import androidx.annotation.Nullable;
public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context,@Nullable String name,@Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE my_test (my_key TEXT, my_value TEXT);";
        db.execSQL(sql);

    }

    public void do_insert(String key, String value)
    {
        String sql = "INSERT INTO my_test VALUES('" + key + "', '" + value + "');";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public String do_select(String key)
    {
        String sql = "SELECT my_value FROM my_test WHERE my_key = '" + key + "';";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery(sql, null);

        if (cur.moveToFirst() == true)
            return cur.getString(0);

        return "(!) not found";
    }

    public void do_update(String key, String newValue) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = "UPDATE my_test SET my_value = '" + newValue + "' WHERE my_key = '" + key + "';";

        try {
            db.execSQL(sql);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void do_delete(String key) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM my_test WHERE my_key = '" + key + "';";
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


package reagodjj.example.com.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        Log.e("RealgodJJ", "调用Insert方法");
        return null;
    }

    SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(getContext(), "student.db",
                null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String sql = "create table student_tb (_id integer primary key autoincrement, " +
                        "name varchar(20), age integer, gender varchar(2))";

                sqLiteDatabase.execSQL(sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

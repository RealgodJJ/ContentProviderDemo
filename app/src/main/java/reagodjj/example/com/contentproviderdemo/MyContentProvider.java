package reagodjj.example.com.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    //URI解析
    //1.UriMatcher:在contentProvider创建时，制定好匹配规则，当调用了ContentProvider中的操作方法时，
    // 利用匹配类去匹配传的uri，根据不同的uri给出不同的处理
    //2.Uri自带解析方法
    private UriMatcher uriMatcher;
    private SQLiteDatabase sqLiteDatabase;

    public MyContentProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        int result = 0;
        switch (code) {
            case 1000:
                Log.e("RealgodJJ", "匹配到的路径是helloworld");
                break;

            case 1001:
                Log.e("RealgodJJ", "匹配到的路径是helloworld/abc");
                break;

            case 1002:
                Log.e("RealgodJJ", "匹配到路径为helloworld/任意数字的内容");
                break;

            case 1003:
                Log.e("RealgodJJ", "匹配到路径为nihaoshijie/任意字符的内容");
                break;

            default:
                Log.e("RealgodJJ", "执行删除数据库内容的方法");
                result = sqLiteDatabase.delete("student_tb", selection, selectionArgs);
                break;
        }

        return result;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Log.e("RealgodJJ", "调用Insert方法");
        long id = sqLiteDatabase.insert("student_tb", null, values);
        ContentUris.withAppendedId(uri, id);
        //将id追加到Uri后面
        return null;
    }

    @Override
    public boolean onCreate() {
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(getContext(), "student.db",
                null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String sql = "create table student_tb (_id integer primary key autoincrement, " +
                        "name varchar(20), age integer, gender varchar(2))";

                db.execSQL(sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

        //参数代表无法匹配
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.myprovider", "helloworld", 1000);
        uriMatcher.addURI("com.example.myprovider", "helloworld/abc", 1001);
        uriMatcher.addURI("com.example.myprovider", "helloworld/#", 1002);
        uriMatcher.addURI("com.example.myprovider", "nihaoshijie/*", 1003);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return sqLiteDatabase.query("student_tb", projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return sqLiteDatabase.update("student_tb", values, selection, selectionArgs);
    }
}

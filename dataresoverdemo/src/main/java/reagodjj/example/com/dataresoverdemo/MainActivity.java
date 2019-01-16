package reagodjj.example.com.dataresoverdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ContentResolver contentResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取ContentResolver对象
        contentResolver = getContentResolver();

    }

    public void operate(View view) {
        switch (view.getId()) {
            case R.id.bt_insert:
                //参数1：URI（同一资源定位符）对象，content://authorities[/path]

                ContentValues contentValues = new ContentValues();
                contentResolver.insert(Uri.parse("content://com.example.myprovider"), contentValues);
                break;
        }
    }
}

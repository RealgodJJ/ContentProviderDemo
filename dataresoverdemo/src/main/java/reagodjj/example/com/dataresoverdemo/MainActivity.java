package reagodjj.example.com.dataresoverdemo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etAge;
    private RadioGroup rgGender;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private EditText etNumber;
    private ListView lvSelectItem;

    private String gender = "男";

    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //获取ContentResolver对象
        resolver = getContentResolver();

    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        rgGender = findViewById(R.id.rg_gender);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        etNumber = findViewById(R.id.et_number);
        lvSelectItem = findViewById(R.id.lv_select_item);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_male) {
                    gender = rbMale.getText().toString();
                } else {
                    gender = rbFemale.getText().toString();
                }
            }
        });
    }

    public void operate(View view) {
        Uri uri = Uri.parse("content://com.example.myprovider");
        switch (view.getId()) {
            case R.id.bt_insert:
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", etName.getText().toString());
                contentValues.put("age", String.valueOf(etAge.getText().toString()));
                contentValues.put("gender", gender);
                //参数1：URI（同一资源定位符）对象，content://authorities[/path]
                //参数2：contentvalues分享的数据
                Uri uriInsert = resolver.insert(uri, contentValues);
                long id = ContentUris.parseId(uriInsert);
                Toast.makeText(this, String.format(getResources().getString(R.string.add_student_success), id), Toast.LENGTH_SHORT).show();
                break;

            case R.id.bt_select:
                //参数2为空，几位查询表中所有列
                Cursor cursor = resolver.query(uri, null, null, null, null);
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor,
                        new String[]{"_id", "name", "age", "gender"},
                        new int[]{R.id.tv_id, R.id.tv_name, R.id.tv_age, R.id.tv_gender},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                lvSelectItem.setAdapter(adapter);
                break;

            case R.id.bt_delete:
                int result = resolver.delete(uri, "_id = ?", new String[]{etNumber.getText().toString()});
                if (result > 0) {
                    Toast.makeText(MainActivity.this, R.string.delete_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, R.string.delete_fail, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bt_update:
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("name", etName.getText().toString());
                contentValues1.put("age", etAge.getText().toString());
                contentValues1.put("gender", gender);
                int result1 = resolver.update(uri, contentValues1, "_id = ?", new String[]{etNumber.getText().toString()});
                if (result1 > 0) {
                    Toast.makeText(MainActivity.this, R.string.update_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, R.string.update_fail, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bt_analysis:
                resolver.delete(Uri.parse("content://com.example.myprovider/helloworld"),
                        null, null);
                resolver.delete(Uri.parse("content://com.example.myprovider/helloworld/abc"),
                        null, null);
                resolver.delete(Uri.parse("content://com.example.myprovider/helloworld/123"),
                        null, null);
                resolver.delete(Uri.parse("content://com.example.myprovider/helloworld/090"),
                        null, null);
                resolver.delete(Uri.parse("content://com.example.myprovider/helloworld/89ii"),
                        null, null);
                resolver.delete(Uri.parse("content://com.example.myprovider/nihaoshijie/ab90"),
                        null, null);
                break;

            case R.id.bt_uri_analysis:
                Uri uri1 = resolver.insert(Uri.parse("content://com.example.myprovider/whatever?name=张三&age=23&gender=男"),
                        new ContentValues());
                long id1 = ContentUris.parseId(uri1);
                Toast.makeText(MainActivity.this, String.format(getResources()
                        .getString(R.string.add_student_success), id1), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

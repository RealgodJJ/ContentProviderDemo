package reagodjj.example.com.dataresoverdemo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //获取ContentResolver对象
        contentResolver = getContentResolver();

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
        switch (view.getId()) {
            case R.id.bt_insert:
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", etName.getText().toString());
                contentValues.put("age", String.valueOf(etAge.getText().toString()));
                contentValues.put("gender", gender);
                //参数1：URI（同一资源定位符）对象，content://authorities[/path]
                //参数2：contentvalues分享的数据
                Uri uriInsert = contentResolver.insert(Uri.parse("content://com.example.myprovider"), contentValues);
                long id = ContentUris.parseId(uriInsert);
                Toast.makeText(this, String.format(getResources().getString(R.string.add_student_success), id), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

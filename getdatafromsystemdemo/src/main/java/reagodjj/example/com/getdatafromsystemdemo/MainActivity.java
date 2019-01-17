package reagodjj.example.com.getdatafromsystemdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSION_REQUEST_CODE = 13;
    private Button btGetMessage;
    private ListView lvMessage;
    private boolean isAllGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btGetMessage = findViewById(R.id.bt_get_message);
        lvMessage = findViewById(R.id.lv_message);
        isAllGranted = checkPermissionAllGranted(new String[]{Manifest.permission.READ_SMS});

        if (isAllGranted) {
            doBack();
            return;
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},
                MY_PERMISSION_REQUEST_CODE);

        btGetMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://sms");
//                Uri uri = Uri.parse("content://sms/inbox");
//                Uri uri = Uri.parse("content://sms/sent");
//                Uri uri = Uri.parse("content://sms/draft");
                Cursor cursor = getContentResolver().query(uri, null, null,
                        null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        //                    String address = cursor.getString(cursor.getColumnIndex("address"));
                        //                    String body = cursor.getString(cursor.getColumnIndex("body"));

                        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                                MainActivity.this, R.layout.list_item, cursor,
                                new String[]{"address", "body"}, new int[]{R.id.tv_address, R.id.tv_body},
                                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

                        lvMessage.setAdapter(simpleCursorAdapter);
                    }
                }
            }
        });
    }

    /**
     * 检查是否拥有全部权限
     *
     * @param permissions 所有权限
     * @return true or false
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对权限申请结果的处理
     *
     * @param requestCode 请求代码
     * @param permissions 所有申请权限
     * @param grantResults 权限申请状态
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;
            //判断是否所有的权限都已授予
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                //如果权限被授予，则执行后续步骤
                doBack();
            } else {
                //弹出对话框告诉用户需要权限的原因，并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    private void doBack() {
        Toast.makeText(MainActivity.this, getString(R.string.all_permission_opened), Toast.LENGTH_SHORT).show();
    }


    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning).setMessage(R.string.get_permission)
                .setPositiveButton(getString(R.string.manual_authorization), new DialogInterface.OnClickListener() {
                    @SuppressLint("InlinedApi")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                .addCategory(Intent.CATEGORY_DEFAULT)
                                .setData(Uri.parse("package:" + getPackageName()))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                .addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);
                    }
                }).setNegativeButton(getString(R.string.cancel), null).show();
    }
}

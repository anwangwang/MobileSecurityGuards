package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.awwhome.mobilesecurityguards.R;

/**
 * 联系人列表Activity
 * Created by awwho on 2017/4/13.
 */
public class ContactListActivity extends Activity {

    private static final String TAG = "ContactListActivity";

    private ListView lv_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_list);

        initView();
        initData();
    }

    /**
     * 初始化数据
     * 获取系统的联系人信息
     */
    private void initData() {
        lv_contacts.setAdapter(new MyAdapter());

        // 访问数据库，耗时操作，开启子线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                String contactsUri = "content://com.android.contacts/raw_contacts";
                String dataUri = "content://com.android.contacts/data";

                // 1.获取内容解析对象
                ContentResolver contentResolver = getContentResolver();
                // 2.根据解析对象进行查询联系人
                Cursor cursor = contentResolver.query(Uri.parse(contactsUri), new String[]{"contact_id"}, null, null, null);
                // 3.遍历查询结果
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    Log.d(TAG, "run: 查询ID:" + id);
                    // 4.根据联系人查询数据
                    Cursor cursor1 = contentResolver.query(Uri.parse(dataUri),
                            new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{id}, null);
                    // 5.循环获取联系人的电话号码姓名和数据类型
                    while (cursor1.moveToNext()) {
                        String data = cursor1.getString(0);
                        String type = cursor1.getString(1);
                        Log.d(TAG, "run: phone:" + data);
                        Log.d(TAG, "run: name:" + type);
                    }
                    cursor1.close();
                }

                cursor.close();
            }
        }.start();


    }

    /**
     * 初始化控件
     */
    private void initView() {

        lv_contacts = (ListView) findViewById(R.id.lv_contacts);
    }

    /**
     * 自定义适配器
     */
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}

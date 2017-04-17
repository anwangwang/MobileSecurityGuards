package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 联系人列表Activity
 * Created by awwho on 2017/4/13.
 */
public class ContactListActivity extends Activity {

    private static final String TAG = "ContactListActivity";

    private ListView lv_contacts;
    private ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyAdapter myAdapter = new MyAdapter();
            lv_contacts.setAdapter(myAdapter);
        }
    };

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

                // 先清空，防止数据重复
                contactList.clear();
                // 3.遍历查询结果
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    Log.d(TAG, "run: 查询ID:" + id);
                    // 4.根据联系人查询数据
                    Cursor cursor1 = contentResolver.query(Uri.parse(dataUri),
                            new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{id}, null);

                    HashMap<String, String> hashMap = new HashMap();

                    // 5.循环获取联系人的电话号码姓名和数据类型
                    while (cursor1.moveToNext()) {
                        String data = cursor1.getString(0);
                        String type = cursor1.getString(1);
                        Log.d(TAG, "run: phone:" + data);
                        Log.d(TAG, "run: name:" + type);
                        // 6.区分类型填充数据
                        if ("vnd.android.cursor.item/phone_v2".equals(type)) {
                            // 电话号码
                            if (!TextUtils.isEmpty(data)) {
                                hashMap.put("phone", data);
                            }
                        } else if ("vnd.android.cursor.item/name".equals(type)) {
                            // 联系人姓名
                            if (!TextUtils.isEmpty(data)) {
                                hashMap.put("name", data);
                            }
                        }
                    }
                    contactList.add(hashMap);
                    cursor1.close();
                }
                cursor.close();
                // 7.消息机制（数据适配器也属于UI）
                // 发送一条空的消息，告诉主线程数据已经准备就绪
                mHandler.sendEmptyMessage(0);
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
            return contactList.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(getApplicationContext(), R.layout.contact_item, null);

            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

            tv_name.setText(getItem(position).get("name"));
            tv_phone.setText(getItem(position).get("phone"));

            return view;
        }
    }
}

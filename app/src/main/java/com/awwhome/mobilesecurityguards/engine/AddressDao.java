package com.awwhome.mobilesecurityguards.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 归属地数据库操作
 * Created by awwho on 2017/4/20.
 */
public class AddressDao {

    private static final String TAG = "AddressDao";

    // 数据库访问路径
    private static String path = "data/data/com.awwhome.mobilesecurityguards/files/naddress.db";

    // 用户输入一个电话号码，返回号码的归属地
    public static String getAddress(String phone) {
        if (phone.length() > 7) {
            phone = phone.substring(0, 7);
            SQLiteDatabase sd = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = sd.query("numinfo", new String[]{"outkey"}, "mobileprefix = ?", new String[]{phone}, null, null, null);
            while (cursor.moveToNext()) {
                String outkey = cursor.getString(0);
                Log.d(TAG, "getAddress: outkey = " + outkey);
                // 通过表1查询出来的结果，作为外键查询表2
                Cursor cursor1 = sd.query("address_tb", new String[]{"cardtype"}, "_id = ?", new String[]{outkey}, null, null, null);
                while (cursor1.moveToNext()) {
                    String address = cursor1.getString(0);
                    Log.d(TAG, "getAddress: address = " + address);
                    return address;
                }
            }
            return "未知号码";
        } else {
            return "未知号码";
        }
    }
}

package com.awwhome.mobilesecurityguards.service;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;

import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;

/**
 * 位置服务
 * Created by awwho on 2017/4/19.
 */
public class LocationService extends Service {

    private static final String TAG = "LocationService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 获取手机的经纬度坐标
        // 1.获取位置管理者对象
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        // 允许花费
        criteria.setCostAllowed(true);
        // 指定获取经纬度的精确度
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        // 2.以最优的方式获取手机的经纬度（网络定位，基站定位，GPS定位）
        String bestProvider = locationManager.getBestProvider(criteria, true);
        // 3.在一定时间间隔，移动一定距离后获取经纬度坐标
        locationManager.requestLocationUpdates(bestProvider, 0, 0, new MyLocationListener());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 自定义位置监听器
     */
    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // 获取经纬度坐标
            // 纬度
            double latitude = location.getLatitude();
            // 经度
            double longitude = location.getLongitude();

            String contact_phone = SpUtil.getString(getApplicationContext(), ConstantValue.CONTACT_PHONE, "");
            // 4.发送短信
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(contact_phone, null, "位置在：经度=" + longitude + ",纬度=" + latitude, null, null);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            // 提供定位的状态发生改变时
        }

        @Override
        public void onProviderEnabled(String provider) {

            // 提供定位可用时
        }

        @Override
        public void onProviderDisabled(String provider) {

            // 提供定位不可用时
        }
    }
}

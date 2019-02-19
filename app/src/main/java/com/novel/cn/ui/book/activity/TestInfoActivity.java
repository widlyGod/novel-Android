package com.novel.cn.ui.book.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.novel.cn.R;
import com.novel.cn.util.SetIpManager;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackieli on 2019/1/14.
 */

public class TestInfoActivity extends AutoLayoutActivity {


    @Bind(R.id.button)
    Button button;
    @Bind(R.id.tv_xx)
    TextView tvXx;


    private WifiInfo wifiInfo;
    private SetIpManager IpConfig;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();

        IpConfig = new SetIpManager(TestInfoActivity.this);//封装，获取ip设置对象
        String ipText="113.91.150.150";
        String gateWayText="113.91.150.150";
        //IP 网络前缀长度24 DNS1域名1 网关
        Boolean isSetSuccess = IpConfig.setIpWithTfiStaticIp(false, ipText, 24, "255.255.255.0", gateWayText);
//        Toast.makeText(TestInfoActivity.this, "ip设置:" + isSetSuccess, Toast.LENGTH_SHORT).show();


    }



    @OnClick(R.id.button)
    public void onViewClicked() {
        String kuan = this.getResources().getDisplayMetrics().widthPixels + "";
        String gao = this.getResources().getDisplayMetrics().heightPixels + "";
        String imei = getIMEI(this);//IMEI
        String changshang = Build.MANUFACTURER;//厂商
        String product = Build.PRODUCT;//产品名
        String brand = Build.BRAND;//手机品牌
        String model = Build.MODEL;//手机型号
        String board = Build.BOARD;//手机主板名
        String device = Build.DEVICE;//手机设备名
        String SERIAL = Build.SERIAL;//硬件序列号
        String sdk_int = Build.VERSION.SDK_INT + "";//SDK版本
        String release = Build.VERSION.RELEASE + "";//Android版本
        String te1 = getPHOne(this);//获取本机号码
        String sim = getSimSerialNumber(this);//获得SIM卡的序号
        String result = wifiInfo.getMacAddress();//MAC地址


        tvXx.setText("手机宽="+kuan+"\n  手机高="+gao+"\n  IMEI="+imei+"\n  厂商="+changshang+"\n  产品名="+product
                +"\n  手机品牌="+brand+"\n   手机型号="+model+"\n  手机主板名="+board+"\n  手机设备名="+device
                +"\n  硬件序列号="+SERIAL+"\n   SDK版本="+sdk_int+"\n  Android版本="+release+"\n  获取本机号码="+te1
                +"\n  获得SIM卡的序号="+sim+"\n   MAC地址="+result
                +"\n   ");

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取外网ip
                final String ip = getOutNetIP(TestInfoActivity.this,0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvXx.setText(tvXx.getText().toString()+"\n ip = "+ip);
                    }
                });
            }
        }).start();
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "Unkonw";
        } else {
            return deviceId;
        }

    }

    @SuppressLint("MissingPermission")
    public static String getPHOne(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phone = tm.getLine1Number();
        if (phone == null) {
            return "Unkonw";
        } else {
            return phone;
        }

    }

    @SuppressLint("MissingPermission")
    public static String getSimSerialNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phone = tm.getSimSerialNumber();
        if (phone == null) {
            return "Unkonw";
        } else {
            return phone;
        }

    }

    /**
     * 获取外网的IP(必须放到子线程里处理)
     */
    private static String[] platforms = {
            "http://pv.sohu.com/cityjson",
            "http://pv.sohu.com/cityjson?ie=utf-8",
            "http://ip.chinaz.com/getip.aspx"
    };
    public static String getOutNetIP(Context context, int index) {
        if (index < platforms.length) {
            BufferedReader buff = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(platforms[index]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);//读取超时
                urlConnection.setConnectTimeout(5000);//连接超时
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);
                urlConnection.setRequestProperty("X-Forwarded-For", "113.91.150.150");
                urlConnection.setRequestProperty("x-remote-IP", "113.91.150.150");
                urlConnection.setRequestProperty("x-remote-ip", "113.91.150.150");
                urlConnection.setRequestProperty("x-client-ip", "113.91.150.150");
                urlConnection.setRequestProperty("x-client-IP", "113.91.150.150");
                urlConnection.setRequestProperty("X-Real-IP", "113.91.150.150");
                urlConnection.setRequestProperty("Proxy-Client-IP", "113.91.150.150");
                urlConnection.setRequestProperty("WL-Proxy-Client-IP", "113.91.150.150");
                urlConnection.setRequestProperty("HTTP_CLIENT_IP", "113.91.150.150");

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {//找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                    InputStream is = urlConnection.getInputStream();
                    buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = buff.readLine()) != null) {
                        builder.append(line);
                    }

                    buff.close();//内部会关闭 InputStream
                    urlConnection.disconnect();

                    Log.e("xiaoman", builder.toString());
                    if (index == 0 || index == 1) {
                        //截取字符串
                        int satrtIndex = builder.indexOf("{");//包含[
                        int endIndex = builder.indexOf("}");//包含]
                        String json = builder.substring(satrtIndex, endIndex + 1);//包含[satrtIndex,endIndex)
                        JSONObject jo = new JSONObject(json);
                        String ip = jo.getString("cip");

                        return ip;
                    } else if (index == 2) {
                        JSONObject jo = new JSONObject(builder.toString());
                        return jo.getString("ip");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "";
        }
        return getOutNetIP(context, ++index);
    }

}

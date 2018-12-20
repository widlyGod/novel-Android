package com.novel.cn.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络监察工具类
 * 
 * Created by wangtao on 4/28/2016.
 * 
 */
public class NetworkUtil {
	public static final String NET_TYPE_WIFI = "WIFI";
	public static final String NET_TYPE_MOBILE = "MOBILE";
	public static final String NET_TYPE_NO_NETWORK = "no_network";

	private Context mContext = null;

	public NetworkUtil(Context pContext) {
		this.mContext = pContext;
	}

	public static final String IP_DEFAULT = "0.0.0.0";

	/**
	 * 网络是否可用
	 * 
	 * @param pContext
	 * @return
	 */
	public static boolean isConnectInternet(final Context pContext) {
		final ConnectivityManager conManager = (ConnectivityManager) pContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}

		return false;
	}

	/**
	 * 检查是否wifi链接
	 * 
	 * @param pContext
	 * @return
	 */
	public static boolean isConnectWifi(final Context pContext) {
		ConnectivityManager mConnectivity = (ConnectivityManager) pContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		// 判断网络连接类型，只有在wifi里进行一些数据更新。
		int netType = -1;
		if (info != null) {
			netType = info.getType();
		}
		if (netType == ConnectivityManager.TYPE_WIFI) {
			return info.isConnected();
		} else {
			return false;
		}
	}

	/**
	 * 得到当前网络链接的具体类型
	 * 
	 * @param pNetType
	 * @return
	 */
	public static String getNetTypeName(final int pNetType) {
		switch (pNetType) {
		case 0:
			return "unknown";
		case 1:
			return "GPRS";
		case 2:
			return "EDGE";
		case 3:
			return "UMTS";
		case 4:
			return "CDMA: Either IS95A or IS95B";
		case 5:
			return "EVDO revision 0";
		case 6:
			return "EVDO revision A";
		case 7:
			return "1xRTT";
		case 8:
			return "HSDPA";
		case 9:
			return "HSUPA";
		case 10:
			return "HSPA";
		case 11:
			return "iDen";
		case 12:
			return "EVDO revision B";
		case 13:
			return "LTE";
		case 14:
			return "eHRPD";
		case 15:
			return "HSPA+";
		default:
			return "unknown";
		}
	}

	/**
	 * 得到当前链接的IP地址
	 * 
	 * @return
	 */
	public static String getIPAddress() {
		try {
			final Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface
					.getNetworkInterfaces();

			while (networkInterfaceEnumeration.hasMoreElements()) {
				final NetworkInterface networkInterface = networkInterfaceEnumeration
						.nextElement();

				final Enumeration<InetAddress> inetAddressEnumeration = networkInterface
						.getInetAddresses();

				while (inetAddressEnumeration.hasMoreElements()) {
					final InetAddress inetAddress = inetAddressEnumeration
							.nextElement();

					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}

			return NetworkUtil.IP_DEFAULT;
		} catch (final SocketException e) {
			return NetworkUtil.IP_DEFAULT;
		}
	}

	/**
	 * 得到当前网络链接类型
	 * 
	 * @return
	 */
	public String getConnTypeName() {
		ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return NET_TYPE_NO_NETWORK;
		} else {
			return networkInfo.getTypeName();
		}
	}
}

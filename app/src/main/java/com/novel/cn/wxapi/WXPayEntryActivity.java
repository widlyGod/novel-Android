package com.novel.cn.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.novel.cn.R;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;

    private TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);

		textView=findViewById(R.id.tv_zfjg);

		LogUtil.e("WXPayEntryActivity界面启动");
		//必须写
    	api = WXAPIFactory.createWXAPI(this, WxPayConfig.APP_ID);
        api.handleIntent(getIntent(), this);

    }


	//必须写
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}


//	参照微信SDK Sample，在net.sourceforge.simcpux.wxapi包路径中实现WXPayEntryActivity类(包名或类名不一致会造成无法回调)，
//	在WXPayEntryActivity类中实现onResp函数，支付完成后，微信APP会返回到商户APP并回调onResp函数，开发者需要在该函数中接收通知，
//	判断返回错误码，如果支付成功则去后台查询支付结果再展示用户实际支付结果。注意一定不能以客户端返回作为用户支付的结果，
//	应以服务器端的接收的支付通知或查询API返回的结果为准。代码示例如下：

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			int code = resp.errCode;
			switch (code) {
				case 0:
					ToastUtils.showShortToast("支付成功");
					break;
				case -1:
//					finish();
					// 支付失败 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
					textView.setText("支付失败errCode="+resp.errCode+",errStr="+resp.errStr+",openId="+resp.openId+",transaction="+resp.transaction+",checkArgs="+resp.checkArgs());
					ToastUtils.showShortToast("支付失败"+resp.errCode+resp.errStr);
					break;
				case -2:
					finish();
					ToastUtils.showShortToast("支付取消");
					break;
			}
		}
	}


}
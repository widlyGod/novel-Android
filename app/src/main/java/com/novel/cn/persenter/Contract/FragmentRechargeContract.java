package com.novel.cn.persenter.Contract;

import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.AplipayOrderBean;
import com.novel.cn.model.entity.QueryUpayBean;
import com.novel.cn.model.entity.WxOrderBean;

/**充值的contract
 * Created by jackieli on 2019/1/2.
 */

public interface FragmentRechargeContract extends BaseComtract {

    interface View extends BaseView {
        void querySuccess(QueryUpayBean queryUpayBean);
        void getAplipayDataSuccess(AplipayOrderBean data);
        void getWxDataSuccess(WxOrderBean data);
        void fail(String message);
        void noConnectInternet();
    }

    interface Presenter extends BasePresenter<FragmentRechargeContract.View> {
        void getPayInfo(String rechargeCode,String requestCode,double orderAmount);

        void quePayInfo();
    }


}

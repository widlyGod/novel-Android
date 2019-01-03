package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/2.
 */

public class AplipayOrderBean {


    /**
     * basePage : null
     * code : 1
     * data : {"tradeNumber":"20190102172658000000292","payCode":"wx681bb7c8ec4c6754149830186239d0fed5a9ac41fa833775c9965adfc9http://www.levis.kim:8083/weixinPayCenter/notifyPay20190102172658000000292MD5172.16.200.141100APP"}
     * message : 微信支付app下单成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static AplipayOrderBean objectFromData(String str) {

        return new Gson().fromJson(str, AplipayOrderBean.class);
    }

    public static List<AplipayOrderBean> arrayAplipayBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AplipayOrderBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public Object getBasePage() {
        return basePage;
    }

    public void setBasePage(Object basePage) {
        this.basePage = basePage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * tradeNumber : 20190102172658000000292
         * payCode : wx681bb7c8ec4c6754149830186239d0fed5a9ac41fa833775c9965adfc9http://www.levis.kim:8083/weixinPayCenter/notifyPay20190102172658000000292MD5172.16.200.141100APP
         */

        private String tradeNumber;
        private String payCode;

        public static DataBean objectFromData(String str) {
            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getTradeNumber() {
            return tradeNumber;
        }

        public void setTradeNumber(String tradeNumber) {
            this.tradeNumber = tradeNumber;
        }

        public String getPayCode() {
            return payCode;
        }

        public void setPayCode(String payCode) {
            this.payCode = payCode;
        }
    }
}

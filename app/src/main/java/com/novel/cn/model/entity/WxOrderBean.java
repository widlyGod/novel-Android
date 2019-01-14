package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/2.
 */

public class WxOrderBean {
    /**
     * basePage : null
     * code : 1
     * data : {"tradeNumber":"20190114101345000000234","payCode":{"package":"Sign=WXPay","appid":"wx87550c9d5f515694","sign":"F8F8684647A7A26367BBD6658C02C4E8","partnerid":"1521730251","prepayid":"wx14101344512993caa9a684db3792856336","noncestr":"2RiZVZn2IgSt0BUo","timestamp":"1547432025572"}}
     * message : 微信支付app下单成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static WxOrderBean objectFromData(String str) {

        return new Gson().fromJson(str, WxOrderBean.class);
    }

    public static List<WxOrderBean> arrayWxOrderBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<WxOrderBean>>() {
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
         * tradeNumber : 20190114101345000000234
         * payCode : {"package":"Sign=WXPay","appid":"wx87550c9d5f515694","sign":"F8F8684647A7A26367BBD6658C02C4E8","partnerid":"1521730251","prepayid":"wx14101344512993caa9a684db3792856336","noncestr":"2RiZVZn2IgSt0BUo","timestamp":"1547432025572"}
         */

        private String tradeNumber;
        private PayCodeBean payCode;

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

        public PayCodeBean getPayCode() {
            return payCode;
        }

        public void setPayCode(PayCodeBean payCode) {
            this.payCode = payCode;
        }

        public static class PayCodeBean {
            /**
             * package : Sign=WXPay
             * appid : wx87550c9d5f515694
             * sign : F8F8684647A7A26367BBD6658C02C4E8
             * partnerid : 1521730251
             * prepayid : wx14101344512993caa9a684db3792856336
             * noncestr : 2RiZVZn2IgSt0BUo
             * timestamp : 1547432025572
             */

            @SerializedName("package")
            private String packageX;
            private String appid;
            private String sign;
            private String partnerid;
            private String prepayid;
            private String noncestr;
            private String timestamp;

            public static PayCodeBean objectFromData(String str) {

                return new Gson().fromJson(str, PayCodeBean.class);
            }

            public static List<PayCodeBean> arrayPayCodeBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<PayCodeBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }


//    /**
//     * basePage : null
//     * code : 1
//     * data : {"tradeNumber":"20190103174229000000308","payCode":{"timeStamp":"1546508549851","nonce_str":"f5iO9za2DlCyRzw9","appid":"wx87550c9d5f515694","sign":"A2A095414B20E3FCF905872E5972B12E","trade_type":"APP","return_msg":"OK","result_code":"SUCCESS","mch_id":"1521730251","return_code":"SUCCESS","prepay_id":"wx03174229819493c217c28fce0487759103"}}
//     * message : 微信支付app下单成功
//     * success : true
//     */
//
//    private Object basePage;
//    private String code;
//    private DataBean data;
//    private String message;
//    private boolean success;
//
//    public static WxOrderBean objectFromData(String str) {
//
//        return new Gson().fromJson(str, WxOrderBean.class);
//    }
//
//    public static List<WxOrderBean> arrayWxOrderBeanFromData(String str) {
//
//        Type listType = new TypeToken<ArrayList<WxOrderBean>>() {
//        }.getType();
//
//        return new Gson().fromJson(str, listType);
//    }
//
//    public Object getBasePage() {
//        return basePage;
//    }
//
//    public void setBasePage(Object basePage) {
//        this.basePage = basePage;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public static class DataBean {
//        /**
//         * tradeNumber : 20190103174229000000308
//         * payCode : {"timeStamp":"1546508549851","nonce_str":"f5iO9za2DlCyRzw9","appid":"wx87550c9d5f515694","sign":"A2A095414B20E3FCF905872E5972B12E","trade_type":"APP","return_msg":"OK","result_code":"SUCCESS","mch_id":"1521730251","return_code":"SUCCESS","prepay_id":"wx03174229819493c217c28fce0487759103"}
//         */
//
//        private String tradeNumber;
//        private PayCodeBean payCode;
//
//        public static DataBean objectFromData(String str) {
//
//            return new Gson().fromJson(str, DataBean.class);
//        }
//
//        public static List<DataBean> arrayDataBeanFromData(String str) {
//
//            Type listType = new TypeToken<ArrayList<DataBean>>() {
//            }.getType();
//
//            return new Gson().fromJson(str, listType);
//        }
//
//        public String getTradeNumber() {
//            return tradeNumber;
//        }
//
//        public void setTradeNumber(String tradeNumber) {
//            this.tradeNumber = tradeNumber;
//        }
//
//        public PayCodeBean getPayCode() {
//            return payCode;
//        }
//
//        public void setPayCode(PayCodeBean payCode) {
//            this.payCode = payCode;
//        }
//
//        public static class PayCodeBean {
//            /**
//             * timeStamp : 1546508549851
//             * nonce_str : f5iO9za2DlCyRzw9
//             * appid : wx87550c9d5f515694
//             * sign : A2A095414B20E3FCF905872E5972B12E
//             * trade_type : APP
//             * return_msg : OK
//             * result_code : SUCCESS
//             * mch_id : 1521730251
//             * return_code : SUCCESS
//             * prepay_id : wx03174229819493c217c28fce0487759103
//             */
//
//            private String timeStamp;
//            private String nonce_str;
//            private String appid;
//            private String sign;
//            private String trade_type;
//            private String return_msg;
//            private String result_code;
//            private String mch_id;
//            private String return_code;
//            private String prepay_id;
//
//            public static PayCodeBean objectFromData(String str) {
//
//                return new Gson().fromJson(str, PayCodeBean.class);
//            }
//
//            public static List<PayCodeBean> arrayPayCodeBeanFromData(String str) {
//
//                Type listType = new TypeToken<ArrayList<PayCodeBean>>() {
//                }.getType();
//
//                return new Gson().fromJson(str, listType);
//            }
//
//            public String getTimeStamp() {
//                return timeStamp;
//            }
//
//            public void setTimeStamp(String timeStamp) {
//                this.timeStamp = timeStamp;
//            }
//
//            public String getNonce_str() {
//                return nonce_str;
//            }
//
//            public void setNonce_str(String nonce_str) {
//                this.nonce_str = nonce_str;
//            }
//
//            public String getAppid() {
//                return appid;
//            }
//
//            public void setAppid(String appid) {
//                this.appid = appid;
//            }
//
//            public String getSign() {
//                return sign;
//            }
//
//            public void setSign(String sign) {
//                this.sign = sign;
//            }
//
//            public String getTrade_type() {
//                return trade_type;
//            }
//
//            public void setTrade_type(String trade_type) {
//                this.trade_type = trade_type;
//            }
//
//            public String getReturn_msg() {
//                return return_msg;
//            }
//
//            public void setReturn_msg(String return_msg) {
//                this.return_msg = return_msg;
//            }
//
//            public String getResult_code() {
//                return result_code;
//            }
//
//            public void setResult_code(String result_code) {
//                this.result_code = result_code;
//            }
//
//            public String getMch_id() {
//                return mch_id;
//            }
//
//            public void setMch_id(String mch_id) {
//                this.mch_id = mch_id;
//            }
//
//            public String getReturn_code() {
//                return return_code;
//            }
//
//            public void setReturn_code(String return_code) {
//                this.return_code = return_code;
//            }
//
//            public String getPrepay_id() {
//                return prepay_id;
//            }
//
//            public void setPrepay_id(String prepay_id) {
//                this.prepay_id = prepay_id;
//            }
//        }
//    }
}

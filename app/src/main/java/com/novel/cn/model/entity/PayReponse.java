package com.novel.cn.model.entity;

/**
 * Created by jackieli on 2019/1/2.
 */

public class PayReponse {

    /**
     * appid : wxa694a8105b02619a
     * code : RE1513750758639793
     * noncestr : NONCE1513750758880-1-1-1-1
     * package : Sign=WXPay
     * partnerid : 1494228292
     * prepayid : wx2017122014192124c6b22af30750915188
     * sign : 4A320D2B2EBFCD7BCF95698BB5D7C192
     * timestamp : 1513750758
     */

    private String appid;
    private String code;
    private String noncestr;
    private String packagex;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackagex() {
        return packagex;
    }

    public void setPackagex(String packagex) {
        this.packagex = packagex;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

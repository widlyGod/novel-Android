package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2018/5/3.
 */

public class BaseBean {


    /**
     * success : true
     * code : 1
     * message : 发送成功
     * data : null
     * roleName : null
     */

    private boolean success;
    private String code;
    private String message;
    private Object data;
    private Object roleName;

    public static BaseBean objectFromData(String str) {

        return new Gson().fromJson(str, BaseBean.class);
    }

    public static List<BaseBean> arrayBaseBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<BaseBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public BaseBean(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getRoleName() {
        return roleName;
    }

    public void setRoleName(Object roleName) {
        this.roleName = roleName;
    }


    @Override
    public String toString() {
        return "BaseBean{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", roleName=" + roleName +
                '}';
    }
}

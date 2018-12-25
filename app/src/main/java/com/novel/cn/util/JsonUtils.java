package com.novel.cn.util;

import android.text.TextUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangtao
 * 构建Http请求的参数
 */
public class JsonUtils {

    public JsonUtils() {

    }

    //jsonobject对象
    private JSONObject retObj = new JSONObject();

    private JSONObject paramObj;


    public JSONObject getRetObj(){
        return retObj;
    }

    /**
     * 返回Http请求参数的 JsonObject
     *
     * @param cmd
     * @param token
     * @return
     */
    public JsonUtils setCmdAndToken(String cmd, String token) {
        try {
            retObj.put("cmd", cmd);
            if (!TextUtils.isEmpty(token)) {
                retObj.put("userToken", token);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonUtils addField(String key, Object value) {
        try {
            if (null != key && null != value)
                retObj.put(key, value);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 构建parameters 的JsonObject
     *
     * @param key
     * @param value
     */
    public JsonUtils addParams(String key, Object value) {
        if (null == paramObj) {
            paramObj = new JSONObject();
        }
        try {
            paramObj.put(key, value);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    public JsonUtils addMyParameters(String parameters_name, Object myParamObj){
        try {
            if (null != myParamObj) {
                retObj.put(parameters_name, myParamObj);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public JSONObject build() {
        try {
            if (null != paramObj) {
                retObj.put("parameters", paramObj);
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return retObj;
    }




    //测试加数组
    public JsonUtils arrayAddParams(String key, Object array) {
        if (null == paramObj) {
            paramObj = new JSONObject();
        }
        try {
//            retObj.element("jsonArray", jsonArray);
            retObj.put(key, array);
            //集合转换为jsonarray
//            JSONArray jsonArray = JSONArray.fromObject(list);
            //将j集合转化未json
//            gson.toJson(labelLists)
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    public static Gson getSkipIdGson() {
        Gson gson = new GsonBuilder().setExclusionStrategies(
                new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        //过滤掉字段名包含"id","address"的字段
                        return f.getName().equals("id");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 过滤掉 类名包含 Bean的类
                        return false;
                    }
                }).create();
        return gson;
    }

//该方法正确
    //            JSONObject jsonObject = new JSONObject();
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put("时间但是都撒");
//            jsonArray.put("是撒旦撒旦撒");
//            jsonObject.put("listLabel", jsonArray);
}

package com.ygw.library.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ygw.library.json.serializerAdapter.BoolDefault0Adapter;
import com.ygw.library.json.serializerAdapter.DoubleDefault0Adapter;
import com.ygw.library.json.serializerAdapter.IntegerDefault0Adapter;
import com.ygw.library.json.serializerAdapter.LongDefault0Adapter;
import com.ygw.library.json.serializerAdapter.StringDefaultAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by sunsh on 2017/6/7.
 */
public class JSONUtils {
    private JSONUtils() {
        throw new UnsupportedOperationException("this method disallow to use");
    }

    private static Gson gson = buildGson();
    private static JSONObject object = new JSONObject();

    public static JSONObject toJson(String json) throws JSONException {
        return object.getJSONObject(json);
    }

    /**
     * 对象装JSON字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * json字符串转对象
     *
     * @param json
     * @param tClass
     * @return
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    /**
     * json字符串转List
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type type) {
        Map<String,Object> o = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        return gson.fromJson(json, type);
    }

    public static Gson buildGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .registerTypeAdapter(Boolean.class, new BoolDefault0Adapter())
                .registerTypeAdapter(boolean.class, new BoolDefault0Adapter())
                .registerTypeAdapter(String.class, new StringDefaultAdapter())
                .disableHtmlEscaping().create();
        return gson;
    }
}

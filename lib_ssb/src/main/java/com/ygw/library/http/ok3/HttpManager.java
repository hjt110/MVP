package com.ygw.library.http.ok3;



import com.ygw.library.http.ok3.entity.BasicRequest;
import com.ygw.library.http.ok3.entity.HttpCallBack;
import com.ygw.library.http.ok3.utils.L;
import com.ygw.library.json.JSONUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunsh on 2018/5/31.
 */
public class HttpManager {

    private static HashMap<String, List<HttpCallBack>> callbacks = new HashMap<>();

    //接口请求mediatype
    public static final String HTTP_MEDIA_TYPE = "application/json ; charset=utf-8";

    /**
     * 组装header
     *
     * @return
     */
    public static HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Platform", "mobile");
        headers.put("Authorization", " ");
        headers.put("SZ-Version", "");
        return headers;
    }


    /**
     * 对象转为map
     *
     * @param requestModel
     * @return
     */
    private static HashMap<String, String> modelToMap(Object requestModel) {
        HashMap<String, String> params = new HashMap<>();
        Field[] fields = requestModel.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String value = null;
            try {
                if (field.get(requestModel) != null) {
                    if (field.get(requestModel) instanceof List) {
                        List list = (List) field.get(requestModel);
                        for (int i = 0; i < list.size(); i++) {
                            params.put(field.getName() + "[" + i + "]", list.get(i).toString());
                        }
                    } else {
                        value = field.get(requestModel).toString();
                        if (!value.equals("null")) {
                            params.put(field.getName(), value);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params;
    }

    private static String getRequestUrl(BasicRequest requestModel) {
        String url = requestModel.getHttpRequestPath();
//        return url.toLowerCase();
        return url;
    }

    /**
     * get请求
     *
     * @param requestModel 对象
     * @throws Exception
     */
    public static void get(BasicRequest requestModel, HttpCallBack callBack) {
        try {
           OkHttpUtils.get().url(getRequestUrl(requestModel)).headers(getHeaders()).params(modelToMap(requestModel)).build().execute(callBack);
        } catch (Exception e) {
            callBack.onError(null, e, 0);
        }
    }

    public static <T> T get(BasicRequest request, Class<T> tClass) throws Exception {
        T data = null;
        okhttp3.Response response =OkHttpUtils.get().url(getRequestUrl(request)).headers(getHeaders()).params(modelToMap(request)).build().execute();
        if (response.code() != 200) {
            throw new Exception("请检查您的网络连接是否正常");
        }
        data = JSONUtils.fromJson(response.body().string(), tClass);
        return data;
    }

    /**
     * post请求
     *
     * @param requestModel
     * @param callBack
     * @throws Exception
     */
    public static void postString(BasicRequest requestModel, HttpCallBack callBack) {
        try {
            OkHttpUtils.postString().mediaType(okhttp3.MediaType.parse(HTTP_MEDIA_TYPE)).url(getRequestUrl(requestModel)).headers(getHeaders()).content(JSONUtils.toJson(requestModel)).build().execute(callBack);
        } catch (Exception e) {
            callBack.onError(null, e, 0);
        }
    }

    /**
     * post请求
     *
     * @param requestModel
     * @param callBack
     * @throws Exception
     */
    public static void post(BasicRequest requestModel, HttpCallBack callBack) {
        try {
            OkHttpUtils.postString().mediaType(okhttp3.MediaType.parse(HTTP_MEDIA_TYPE)).url(getRequestUrl(requestModel)).headers(getHeaders()).content(requestModel.getObjectString()).build().execute(callBack);
        } catch (Exception e) {
            callBack.onError(null, e, 0);
        }
    }

    /**
     * 文件post请求
     *
     * @param url
     * @param file
     * @param callBack
     * @throws Exception
     */
    public static void postFile(String url, File file, HttpCallBack callBack) {
        try {
          OkHttpUtils.postFile().url(url).headers(getHeaders()).file(file).build().execute(callBack);
        } catch (Exception e) {
            callBack.onError(null, e, 0);
        }
    }

    /**
     * put请求
     *
     * @param request
     * @param callBack
     */
    public static void put(BasicRequest request, HttpCallBack callBack) {
        try {
            okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse(HTTP_MEDIA_TYPE), JSONUtils.toJson(request));
           OkHttpUtils.put().url(getRequestUrl(request)).headers(getHeaders()).requestBody(requestBody).build().execute(callBack);
        } catch (Exception e) {
            callBack.onError(null, e, 0);
        }
    }

    /**
     * put请求
     *
     * @param request
     * @param callBack
     */
    public static void putMap(BasicRequest request, HttpCallBack callBack) {
        try {
            okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse(HTTP_MEDIA_TYPE), JSONUtils.toJson(request.getObject()));
           OkHttpUtils.put().url(getRequestUrl(request)).headers(getHeaders()).requestBody(requestBody).build().execute(callBack);
        } catch (Exception e) {
            callBack.onError(null, e, 0);
        }
    }

    /**
     * delete请求
     *
     * @param request
     * @param callBack
     */
    public static void delete(BasicRequest request, HttpCallBack callBack) {
        try {
            okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse(HTTP_MEDIA_TYPE), JSONUtils.toJson(request));
           OkHttpUtils.delete().url(getRequestUrl(request)).headers(getHeaders()).requestBody(requestBody).build().execute(callBack);
//            OkHttpUtils.delete().url(request.getHttpRequestPath()).headers(getHeaders()).requestBody(JSONUtils.toJson(request)).build().execute(callBack);
        } catch (Exception e) {
            callBack.onError(null, e, 0);
        }
    }

    /**
     * 根据tag取消请求
     *
     * @param url
     * @param tag
     * @使用方法 OkHttpUtils.cancelTag(this);//取消以Activity.this作为tag的请求
     */
    public static void cancelRequestByTag(String url, Object tag) {
        try {
           OkHttpUtils.get().url(url).tag(tag).build();
        } catch (Exception e) {
            L.e("okhttp cancel:" + e.getMessage());
        }
    }

    /**
     * 取消所有的okhttp请求
     */
    public static void cancelAll() {
        try {
          OkHttpUtils.getInstance().getOkHttpClient().dispatcher().cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

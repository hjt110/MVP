package com.ygw.library.http.ok3.entity;



import com.ygw.library.json.JSONUtils;

import java.io.Serializable;
/**
 * Created by sunsh on 18/5/30.
 */
public abstract class BasicRequest implements Serializable {

    protected transient StringBuilder urlBuilder = new StringBuilder();

    public abstract String getHttpRequestPath();

    protected transient Object object;

    public String getObjectString(){
        return JSONUtils.toJson(getObject());
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    protected String jointStr(Object object){
        return object == null ? "" : "/" + object;
    }

    protected StringBuilder getUrlBuilder(){
        if (urlBuilder.length()>0){
            urlBuilder.delete(0,urlBuilder.length());
        }
        return urlBuilder;
    }
}

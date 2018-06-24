package com.tong.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Json
 * 
 * @author 
 * 
 */
public class JsonParse 
{

	public static <T> T getResult(String jsonStr, Class<T> c)
	{
		T result = null;
		Gson gson = new Gson();
		result = (T) gson.fromJson(jsonStr, c);
		return result;
	}

	public static <T> List<T> getResultList(String jsonStr, Class c)
	{
		List<T> list = null;

		Gson gson = new Gson();
		Type listType = new TypeToken<List<T>>() {
		}.getType();
		List<T> persons = gson.fromJson(jsonStr, listType);
		return list;
	}

	public static <T> List<T> getResultList(JsonArray array, Class c)
	{
		List<T> list = new ArrayList<T>();
		if (array != null) {
			int size = array.size();
			Gson gson = new Gson();
			for (int i = 0; i < size; i++)
			{
				list.add((T) gson.fromJson(array.get(i), c));
			}
		}

		return list;
	}

	public static String getOtherField(String otherStr, String field)
			throws JSONException
	{

		JSONObject jsonObject = new JSONObject(otherStr);
		Iterator keys = jsonObject.keys();
		Log.d("TAG", keys.toString());
		
		while (keys.hasNext())
		{
			String key = (String) keys.next();
			JSONObject object = (JSONObject) jsonObject.get(key);
			return object.getString(field);
		}
		
		return null;
	}

	public static JsonObject getJsonObj(JsonObject jsonObject)
	{
		JsonObject result = null;
		Iterator keys = jsonObject.entrySet().iterator();
		
		if (keys.hasNext())
		{
			Entry entry = (Entry) keys.next();
			result = (JsonObject) entry.getValue();
			return result;
		}
		return result;
	}
}

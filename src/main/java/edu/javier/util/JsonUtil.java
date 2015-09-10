package edu.javier.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

/**
 * Created by JavierAlonso on 08/09/2015.
 */
public class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static <T> T toObject(String jsonString, Class<T> clazz) {
        return new Gson().fromJson(jsonString, clazz);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}

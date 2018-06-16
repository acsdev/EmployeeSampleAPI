package br.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static String getListAsJson(List<? extends Serializable> serializable) {
        if (serializable == null) return "[]";

        String array = serializable.stream().map(Util::getAsJson).collect(Collectors.joining(","));
        return "[" + array + "]";
    }

    public static <T> List<T> getListAsJson(String json, Class<T> clazz) {
        if (json == null || "[]".equals(json)) return new ArrayList<>();

        Type listType = new TypeToken<List<T>>() {}.getType();
        return new Gson().fromJson(json, listType);
    }

    public static String getAsJson(Serializable serializable) {

        Gson gson = new GsonBuilder().create();

        return gson.toJson( serializable );

    }

    public static <T> T getAsObject(String json, Class<T> clazz) {

        Gson gson = new GsonBuilder().create();

        return gson.fromJson(json, clazz);

    }
}

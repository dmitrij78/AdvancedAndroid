package com.nitrosoft.ua.advancedandroid.testutils;

import com.nitrosoft.ua.advancedandroid.models.ApplicationJsonAdapterFactory;
import com.nitrosoft.ua.advancedandroid.models.ZoneDatetimeAdapter;
import com.squareup.moshi.Moshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class TestUtils {

    private static final TestUtils INSTANCE = new TestUtils();

    private static final Moshi TEST_MOSHI = initMoshi();

    private static Moshi initMoshi() {
        Moshi.Builder builder = new Moshi.Builder();
        builder.add(ApplicationJsonAdapterFactory.INSTANCE);
        builder.add(new ZoneDatetimeAdapter());
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    public static <T> T loadJson(String path, Type type) {
        try {
            String json = getFileString(path);
            return (T) TEST_MOSHI.adapter(type).fromJson(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize: " + path + " into type: " + type);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T loadJson(String path, Class clazz) {
        try {
            String json = getFileString(path);
            return (T) TEST_MOSHI.adapter(clazz).fromJson(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize: " + path + " into class: " + clazz);
        }
    }

    private static String getFileString(String path) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    INSTANCE.getClass().getClassLoader().getResourceAsStream(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read from resource " + path);
        }
    }
}

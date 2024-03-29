package com.nitrosoft.ua.advancedandroid.test;

import com.squareup.moshi.Moshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import javax.inject.Inject;

public class TestUtils {

    private Moshi moshi;

    @Inject
    TestUtils(Moshi moshi) {
        this.moshi = moshi;
    }

    @SuppressWarnings("unchecked")
    public <T> T loadJson(String path, Type type) {
        try {
            String json = getFileString(path);
            return (T) moshi.adapter(type).fromJson(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize: " + path + " into type: " + type);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T loadJson(String path, Class clazz) {
        try {
            String json = getFileString(path);
            return (T) moshi.adapter(clazz).fromJson(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize: " + path + " into class: " + clazz);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private String getFileString(String path) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException | NullPointerException e) {
            throw new IllegalArgumentException("Could not read from resource " + path);
        }
    }
}
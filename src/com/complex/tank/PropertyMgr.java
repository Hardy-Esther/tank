package com.complex.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key) {
        if (props == null) return null;
        return props.get(key);
    }

    public static int getInt(String key) {
        if (props == null) return 0;
        return Integer.parseInt(PropertyMgr.getString(key));
    }

    public static String getString(String key) {
        if (props == null) return "";
        return (String) PropertyMgr.get(key);
    }

}

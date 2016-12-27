package com.szzt.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by peterguo on 2016/12/26.
 */
public class FileUtils {


    /***
     * 获取运行时资源文件的路径
     * @return
     */
    public static String getResourceRealPath() {
        String realPath = FileUtils.class .getClassLoader().getResource("").getFile();
        File file = new File(realPath);
        realPath = file.getAbsolutePath();

        try {
            realPath = java.net.URLDecoder.decode (realPath, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return realPath + "/mbg_configuration_mysql.xml";
    }


    /**
     * 获取配置文件信息
     * @param key
     * @return
     */
    public static String getValue(String key) {
        Properties properties = new Properties();
        InputStream in = new FileUtils().getClass().getResourceAsStream(
                "/config.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) properties.get(key);
    }
}

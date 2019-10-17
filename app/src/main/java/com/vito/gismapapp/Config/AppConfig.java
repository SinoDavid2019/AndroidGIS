package com.vito.gismapapp.Config;

import android.content.Context;

import com.vito.gismapapp.Config.Entity.ConfigEntity;
import com.vito.gismapapp.Config.Xml.XmlParser;

/**
 * 兰州北科维拓
 * <p>
 * FileName: AppConfig
 * <p>
 * Author: 魏海龙
 * <p>
 * Date: 2019/10/16 16:00
 * <p>
 * Description:
 */
public class AppConfig {
    /**
     * 获取应用程序配置信息
     */
    public static ConfigEntity getConfig(Context context) {
        ConfigEntity config = null;
        try {
            config = XmlParser.getConfig(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }
}

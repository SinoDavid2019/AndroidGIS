package com.vito.gismapapp.Config.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 兰州北科维拓
 * <p>
 * FileName: ConfigEntity
 * <p>
 * Author: 魏海龙
 * <p>
 * Date: 2019/10/16 14:58
 * <p>
 * Description:
 */
public class ConfigEntity {
    private String runtimrKey = null;//许可信息
    private String workspacePath = null;//工作空间路径

    private List<WidgetEntity> mListWidget = new ArrayList<WidgetEntity>();

    public String getRuntimrKey() {
        return runtimrKey;
    }

    public void setRuntimrKey(String runtimrKey) {
        this.runtimrKey = runtimrKey;
    }

    public String getWorkspacePath() {
        return workspacePath;
    }

    public void setWorkspacePath(String workspacePath) {
        this.workspacePath = workspacePath;
    }

    public void setListWidget(List<WidgetEntity> list)
    {
        mListWidget = list;
    }
    public List<WidgetEntity> getListWidget()
    {
        return mListWidget;
    }
}

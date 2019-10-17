package com.vito.gismapapp.Config.Entity;

/**
 * 兰州北科维拓
 * <p>
 * FileName: WidgetEntity
 * <p>
 * Author: 魏海龙
 * <p>
 * Date: 2019/10/16 14:55
 * <p>
 * Description:
 */
public class WidgetEntity {
    private int mId=0;
    private String mClassname = "";
    private String mLabel = "";
    private String mGroup = "";
    private String mIcon = "";
    private String mSelectIcon = "";
    private String mConfig = "";
    private boolean isShowing = false;
    public void setGroup(String group){
        this.mGroup = group;
    }
    public String getGroup(){
        return mGroup;
    }

    public boolean getIsShowing() {
        return isShowing;
    }
    public void setStatus(boolean isShowing) {
        this.isShowing =  isShowing;
    }
    public void setId(int id)
    {
        mId = id;
    }
    public int getId()
    {
        return mId;
    }
    public void setClassname(String name)
    {
        mClassname = name;
    }
    public String getClassname()
    {
        return mClassname;
    }
    public void setConfig(String config)
    {
        mConfig = config;
    }
    public String getConfig()
    {
        return mConfig;
    }

    public void setSelectIconName(String mSelectIcon) {
        this.mSelectIcon = mSelectIcon;
    }

    public String getSelectIcon() {
        return mSelectIcon;
    }

    public void setIconName(String icon)
    {
        mIcon = icon;
    }
    public String getIconName()
    {
        return mIcon;
    }

    public void setLabel(String label)
    {
        mLabel = label;
    }
    public String getLabel()
    {
        return mLabel;
    }

}

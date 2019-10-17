package com.vito.gismapapp.Resource;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.vito.gismapapp.R;

/**
 * 兰州北科维拓
 * <p>
 * FileName: ResourceConfig
 * <p>
 * Author: 魏海龙
 * <p>
 * Date: 2019/10/16 14:25
 * <p>
 * Description:
 */
public class ResourceConfig {
    public Context context;
    private Activity activity;

    public ResourceConfig(Context context) {
        this.context = context;
        this.activity= (Activity) context;
        initConfig();
    }

    /**资源列表**/
    public MapView mapView = null;//地图控件
    public RelativeLayout compassView =null;//指北针控件
    public TextView txtMapScale = null;//比例尺
    public View baseWidgetView = null;//widget组件
    public ImageView imgCenterView = null;//中心十字叉
    public ToggleButton togbtnLocation=null;//定位按钮
    public TextView txtLocation = null;

    public LinearLayout baseWidgetToolsView;//widget组件工具列表



    private void initConfig() {

        this.mapView=activity.findViewById(R.id.activity_map_mapview);

        this.txtMapScale =  activity.findViewById(R.id.activity_map_mapview_scale);
        this.baseWidgetView = activity.findViewById(R.id.base_widget_view_baseview);
        this.togbtnLocation =  activity.findViewById(R.id.activity_map_togbtnLocation);
        this.txtLocation = activity.findViewById(R.id.activity_map_mapview_locationInfo);

        this.baseWidgetToolsView = activity.findViewById(R.id.base_widget_view_tools_linerview);

    }
}

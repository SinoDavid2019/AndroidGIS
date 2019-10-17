package com.vito.gismapapp.Map;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedEvent;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedListener;
import com.vito.arcgistools.utils.ToastUtils;
import com.vito.gismapapp.Config.Entity.ConfigEntity;
import com.vito.gismapapp.Map.Location.DMUserLocationManager;
import com.vito.gismapapp.Map.Running.MapConfigInfo;
import com.vito.gismapapp.R;
import com.vito.gismapapp.Resource.ResourceConfig;

import java.text.DecimalFormat;

/**
 * 兰州北科维拓
 * <p>
 * FileName: MapManager
 * <p>
 * Author: 魏海龙
 * <p>
 * Date: 2019/10/16 14:21
 * <p>
 * Description:
 */
public class MapManager {
    private static String TAG="MapManager";
    private ResourceConfig resourceConfig;
    private ConfigEntity configEntity;
    private Context context;
    private ArcGISMap map;//地图容器

    public MapManager(Context context,ResourceConfig resourceConfig,ConfigEntity ce) {
        this.context = context;
        this.configEntity = ce;
        this.resourceConfig=resourceConfig;
        this.map = new ArcGISMap();
        resourceConfig.mapView.setMap(map);
        initMapResource();//初始化地图配置
        
    }

    /**
     * 初始化地图资源
     */
    private void initMapResource() {

        try {
            /**设置许可**/
            ArcGISRuntimeEnvironment.setLicense(configEntity.getRuntimrKey());
            String version =ArcGISRuntimeEnvironment.getAPIVersion();
            String lic =ArcGISRuntimeEnvironment.getLicense().getLicenseLevel().name();
            ToastUtils.showShort(context,"ArcGIS Runtime版本:"+version +"; 许可信息:"+lic);
        }catch (Exception e){
            ToastUtils.showShort(context,"ArcGIS Runtime 许可设置异常:"+e.getMessage());
        }

        /***显示放大镜*/
        resourceConfig.mapView.setMagnifierEnabled(false);
        resourceConfig.mapView.setCanMagnifierPanMap(false);

//        /**最大最小比例尺设置*/
//        resourceConfig.mapView.getMap().setMinScale(999999999);//最小比例尺
//        resourceConfig.mapView.getMap().setMaxScale(1500);//最大比例尺

        /***不显示Esri LOGO*/
        resourceConfig.mapView.setAttributionTextVisible(false);



        /**显示比例尺级别*/
        final DecimalFormat df = new DecimalFormat("###");

        //设置默认比例尺级别
        String scale = df.format(resourceConfig.mapView.getMapScale());
        resourceConfig.txtMapScale.setText("比例尺 1:"+scale);

        //根据缩放设置比例尺级别
        resourceConfig.mapView.addMapScaleChangedListener(new MapScaleChangedListener() {
            @Override
            public void mapScaleChanged(MapScaleChangedEvent mapScaleChangedEvent) {
                String scale = df.format(resourceConfig.mapView.getMapScale());
                resourceConfig.txtMapScale.setText("比例尺 1:"+scale);
            }
        });

        /**
         * 设置定位相关事件
         */
        MapConfigInfo.dmUserLocationManager = new DMUserLocationManager(context,resourceConfig.mapView);
        resourceConfig.togbtnLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ToastUtils.showShort(context,"定位开");
                    resourceConfig.togbtnLocation.setBackgroundResource(R.drawable.ic_location_btn_on);
                    MapConfigInfo.dmUserLocationManager.start();
                }else{
                    ToastUtils.showShort(context,"定位关");
                    resourceConfig.togbtnLocation.setBackgroundResource(R.drawable.ic_location_btn_off);
                    MapConfigInfo.dmUserLocationManager.stop();
                    resourceConfig.txtLocation.setText(context.getString(R.string.txt_location_info));
                }
            }
        });


    }
}

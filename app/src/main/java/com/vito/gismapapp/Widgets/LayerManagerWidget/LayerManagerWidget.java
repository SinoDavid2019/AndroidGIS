package com.vito.gismapapp.Widgets.LayerManagerWidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.arcgisruntime.data.FeatureCollection;
import com.esri.arcgisruntime.data.FeatureCollectionTable;
import com.esri.arcgisruntime.data.GeoPackage;
import com.esri.arcgisruntime.data.GeoPackageFeatureTable;
import com.esri.arcgisruntime.data.ShapefileFeatureTable;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.vito.gismapapp.BaseWidget.BaseWidget;
import com.vito.gismapapp.R;
import com.vito.gismapapp.Resource.ResourceConfig;
import com.vito.gismapapp.Utils.FileUtils;
import com.vito.gismapapp.Widgets.LayerManagerWidget.Adapter.LayerListviewAdapter;
import com.vito.gismapapp.Widgets.LayerManagerWidget.Adapter.LegendListviewAdapter;

import java.io.File;
import java.util.List;

/**
 * 图层控制
 */
public class LayerManagerWidget extends BaseWidget {

    private static String TAG = "LayerManagerWidget";

    public View mWidgetView = null;//
    public ListView baseMapLayerListView = null;
    public ListView featureLayerListView = null;
    private ResourceConfig resourceConfig;

    private Context context;

    private LayerListviewAdapter featureLayerListviewAdapter = null;
    private LayerListviewAdapter basemapLayerListviewAdapter = null;
    private LegendListviewAdapter legendListviewAdapter = null;

    @Override
    public void active() {
        super.active();
        super.showWidget(mWidgetView);
//        super.showMessageBox(super.name);
    }

    @Override
    public void create() {

        context = super.context;
        resourceConfig = new ResourceConfig(context);

        initBaseMapResource();//初始化底图
        //initOperationalLayers();//初始化业务图层

        //initGeoPackageLayers();//初始化业务图层 gpkg

        //initJsonLayers();//初始化json图层

        initWidgetView();//初始化UI

    }

    /**
     * UI初始化
     */
    private void initWidgetView() {
        /**
         * **********************************************************************************
         * 布局容器
         */
        mWidgetView = LayoutInflater.from(super.context).inflate(R.layout.widget_view_layer_manager, null);
        TextView txtLayerListBtn = (TextView) mWidgetView.findViewById(R.id.widget_view_layer_manager_txtBtnLayerList);
        final View viewLayerListSelect = mWidgetView.findViewById(R.id.widget_view_layer_manager_viewLayerList);
        TextView txtLegendBtn = (TextView) mWidgetView.findViewById(R.id.widget_view_layer_manager_txtBtnLegend);
        final View viewLegendSelect = mWidgetView.findViewById(R.id.widget_view_layer_manager_viewLegend);
        final RelativeLayout viewContent = mWidgetView.findViewById(R.id.widget_view_layer_manager_contentView);//内容区域

        /**
         * **********************************************************************************
         * 图例
         */
        final View legendView = LayoutInflater.from(super.context).inflate(R.layout.widget_view_layer_manager_legend, null);
        ListView listViewLenged = legendView.findViewById(R.id.widget_view_layer_manager_legend_layerListview);
        legendListviewAdapter = new LegendListviewAdapter(context, super.mapView.getMap().getOperationalLayers());
        listViewLenged.setAdapter(legendListviewAdapter);
        TextView textViewNoLegend = (TextView) legendView.findViewById(R.id.widget_view_layer_manager_legend_txtNoLegend);

        /**
         * **********************************************************************************
         * 图层列表
         */
        final View layerManagerView = LayoutInflater.from(super.context).inflate(R.layout.widget_view_layer_manager_layers, null);
        this.baseMapLayerListView = (ListView) layerManagerView.findViewById(R.id.widget_view_layer_manager_layers_basemapLayerListview);
        this.featureLayerListView = (ListView) layerManagerView.findViewById(R.id.widget_view_layer_manager_layers_featureLayerListview);

        basemapLayerListviewAdapter = new LayerListviewAdapter(context, super.mapView.getMap().getBasemap().getBaseLayers());
        this.baseMapLayerListView.setAdapter(basemapLayerListviewAdapter);
        featureLayerListviewAdapter = new LayerListviewAdapter(context, super.mapView.getMap().getOperationalLayers());
        this.featureLayerListView.setAdapter(featureLayerListviewAdapter);

        //业务图层操作
        Button operationBtnMore = (Button) layerManagerView.findViewById(R.id.widget_view_layer_managet_layers_operationlayer_btnMore);
        operationBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pmp = new PopupMenu(context, v);
                pmp.getMenuInflater().inflate(R.menu.menu_layer_handle_tools, pmp.getMenu());
                pmp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_layer_handle_tools_openAllLayer:
                                for (int i = 0; i < mapView.getMap().getOperationalLayers().size(); i++) {
                                    Layer layer = mapView.getMap().getOperationalLayers().get(i);
                                    layer.setVisible(true);
                                }
                                featureLayerListviewAdapter.refreshData();
                                break;
                            case R.id.menu_layer_handle_tools_closedAllLayer:
                                for (int i = 0; i < mapView.getMap().getOperationalLayers().size(); i++) {
                                    Layer layer = mapView.getMap().getOperationalLayers().get(i);
                                    layer.setVisible(false);
                                }
                                featureLayerListviewAdapter.refreshData();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                pmp.show();
            }
        });

        //底图图层
        Button basemapnBtnMore = layerManagerView.findViewById(R.id.widget_view_layer_managet_layers_basemap_btnMore);
        basemapnBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pmp = new PopupMenu(context, v);
                pmp.getMenuInflater().inflate(R.menu.menu_layer_handle_tools, pmp.getMenu());
                pmp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_layer_handle_tools_openAllLayer:
                                for (int i = 0; i < mapView.getMap().getBasemap().getBaseLayers().size(); i++) {
                                    Layer layer = mapView.getMap().getBasemap().getBaseLayers().get(i);
                                    layer.setVisible(true);
                                }
                                basemapLayerListviewAdapter.refreshData();
                                break;
                            case R.id.menu_layer_handle_tools_closedAllLayer:
                                for (int i = 0; i < mapView.getMap().getBasemap().getBaseLayers().size(); i++) {
                                    Layer layer = mapView.getMap().getBasemap().getBaseLayers().get(i);
                                    layer.setVisible(false);
                                }
                                basemapLayerListviewAdapter.refreshData();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                pmp.show();
            }
        });

        /**
         * **********************************************************************************
         * 布局容器事件
         */
        viewContent.addView(layerManagerView);//默认显示图层列表
        txtLayerListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewContent.getChildAt(0) != layerManagerView) {
                    viewContent.removeAllViews();
                    viewContent.addView(layerManagerView);
//                    txtLayerListBtn.setTextColor();
                    viewLayerListSelect.setVisibility(View.VISIBLE);
                    viewLegendSelect.setVisibility(View.GONE);
                }
            }
        });
        txtLegendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewContent.getChildAt(0) != legendView) {
                    viewContent.removeAllViews();
                    viewContent.addView(legendView);
                    viewLayerListSelect.setVisibility(View.GONE);
                    viewLegendSelect.setVisibility(View.VISIBLE);

                    //打开窗口前先刷新数据面板
                    legendListviewAdapter.refreshData();
                }
            }
        });
    }

    @Override
    public void inactive() {
        super.inactive();
    }

    /**
     * 初始化业务图层-shapefile
     */
    private void initOperationalLayers() {
        String path = getOperationalLayersPath();
        List<FileUtils.FileInfo> fileInfos = FileUtils.getFileListInfo(path, "shp");
        if (fileInfos == null) return;
        for (int i = 0; i < fileInfos.size(); i++) {
            FileUtils.FileInfo fileInfo = fileInfos.get(i);

            final ShapefileFeatureTable shapefileFeatureTable = new ShapefileFeatureTable(fileInfo.FilePath);
            shapefileFeatureTable.loadAsync();//异步方式读取文件
            shapefileFeatureTable.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    //数据加载完毕后，添加到地图
                    FeatureLayer mainShapefileLayer = new FeatureLayer(shapefileFeatureTable);
                    mapView.getMap().getOperationalLayers().add(mainShapefileLayer);
                }
            });
        }
    }

    /**
     * 初始化GeoPackage图层
     */
    private void initGeoPackageLayers() {
        String path = getGeoPackagePath();
        List<FileUtils.FileInfo> fileInfos = FileUtils.getFileListInfo(path, "gpkg");
        if (fileInfos == null) return;
        for (int i = 0; i < fileInfos.size(); i++) {
            FileUtils.FileInfo fileInfo = fileInfos.get(i);
            final GeoPackage geoPackage = new GeoPackage(fileInfo.FilePath);
            geoPackage.loadAsync();
            geoPackage.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    List<GeoPackageFeatureTable> packageFeatureTables = geoPackage.getGeoPackageFeatureTables();
                    for (int j = 0; j < packageFeatureTables.size(); j++) {
                        GeoPackageFeatureTable table = packageFeatureTables.get(j);
                        FeatureLayer layer = new FeatureLayer(table);

//                         //        //标注测试
//                        StringBuilder labelDefinitionString = new StringBuilder();
//                        labelDefinitionString.append("{");
//                        labelDefinitionString.append("\"labelExpressionInfo\": {");
//                        labelDefinitionString.append("\"expression\": \"return $feature.fid;\"},");
//                        labelDefinitionString.append("\"labelPlacement\": \"esriServerPolygonPlacementAlwaysHorizontal\",");
//                        labelDefinitionString.append("\"minScale\":500000,");
//                        labelDefinitionString.append("\"symbol\": {");
//                        labelDefinitionString.append("\"color\": [0,255,50,255],");
//                        labelDefinitionString.append("\"font\": {\"size\": 14, \"weight\": \"bold\"},");
//                        labelDefinitionString.append("\"type\": \"esriTS\"}");
//                        labelDefinitionString.append("}");
//                         LabelDefinition labelDefinition = LabelDefinition.fromJson(String.valueOf(labelDefinitionString));
//                         layer.getLabelDefinitions().add(labelDefinition);
//                         layer.setLabelsEnabled(true);

//                layer.setName(table.getName()+"-gpkg");
                        mapView.getMap().getOperationalLayers().add(layer);
                    }
                }
            });

        }
    }

    /**
     * 初始化
     */
    private void initJsonLayers() {
        String path = getJSONPath();
        List<FileUtils.FileInfo> fileInfos = FileUtils.getFileListInfo(path, ".json");
        if (fileInfos == null) return;
        for (int i = 0; i < fileInfos.size(); i++) {
            FileUtils.FileInfo fileInfo = fileInfos.get(i);
            String json = FileUtils.openTxt(fileInfo.FilePath, "UTF-8");
            final FeatureCollection featureCollection = FeatureCollection.fromJson(json);
            featureCollection.loadAsync();
            featureCollection.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    List<FeatureCollectionTable> featureCollectionTable = featureCollection.getTables();
                    for (int j = 0; j < featureCollectionTable.size(); j++) {
                        FeatureCollectionTable features = featureCollectionTable.get(j);
                        FeatureLayer featureLayer = new FeatureLayer(features);
                        featureLayer.setName(features.getTableName() + "-json");
                        mapView.getMap().getOperationalLayers().add(featureLayer);
                    }
                }
            });

        }

    }

    /**
     * 初始化基础底图资源
     */
    private void initBaseMapResource() {
        String strMapUrl = "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer";
        ArcGISTiledLayer arcGISTiledMapServiceLayer = new ArcGISTiledLayer(strMapUrl);
        super.mapView.getMap().getBasemap().getBaseLayers().add(arcGISTiledMapServiceLayer);
    }

    /**
     * 获取基础底图路径
     *
     * @param path
     * @return
     */
    private String getBasemapPath(String path) {
        return projectPath + File.separator + "BaseMap" + File.separator + path;
    }

    /**
     * 获取业务图层路径
     *
     * @return
     */
    private String getOperationalLayersPath() {
        return projectPath + File.separator + "OperationalLayers" + File.separator;
    }

    /**
     * 获取Geopackage路径
     *
     * @return
     */
    private String getGeoPackagePath() {
        return projectPath + File.separator + "GeoPackage" + File.separator;
    }

    /**
     * 获取json路径信息
     *
     * @return
     */
    private String getJSONPath() {
        return projectPath + File.separator + "JSON" + File.separator;
    }

}

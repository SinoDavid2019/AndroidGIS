package com.vito.gismapapp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.vito.arcgistools.utils.SysUtils;
import com.vito.arcgistools.utils.ToastUtils;
import com.vito.gismapapp.Base.BaseActivity;
import com.vito.gismapapp.BaseWidget.BaseWidget;
import com.vito.gismapapp.BaseWidget.WidgetManager;
import com.vito.gismapapp.Config.AppConfig;
import com.vito.gismapapp.Config.Entity.ConfigEntity;
import com.vito.gismapapp.Config.Entity.WidgetEntity;
import com.vito.gismapapp.Map.MapManager;
import com.vito.gismapapp.Resource.ResourceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private Context context;
    private ResourceConfig resourceConfig;
    private ArcMenu arcMenu;
    private MapManager mMapManager;
    private Map<Integer,Object> mWidgetEntityMenu=new HashMap<>();
    private WidgetManager mWidgetManager;
    private ConfigEntity mConfigEntity;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        context=this;
        resourceConfig=new ResourceConfig(context);
        titleTextView.setText(R.string.app_name);
        init();
        initEvent();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){

        }else if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){

        }
    }

    private void init() {
        mConfigEntity= AppConfig.getConfig(context);
        mMapManager=new MapManager(context,resourceConfig,mConfigEntity);
        mWidgetManager=new WidgetManager(context,resourceConfig,mMapManager,mConfigEntity);

        mWidgetManager.instanceAllClass();
    }


    private void initEvent() {
       arcMenu=findViewById(R.id.arcmenu);
       arcMenu.setMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
           @Override
           public void onClick(View view, int pos) {

           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        super.onCreateOptionsMenu(menu);
        boolean ispad= SysUtils.isPad(context);
        if(ispad){
            if(mConfigEntity!=null){
                final List<WidgetEntity> mListWidget=mConfigEntity.getListWidget();
                for(int i=0;i<mListWidget.size();i++){
                    final WidgetEntity widgetEntity=mListWidget.get(i);
                    View view= LayoutInflater.from(context).inflate(R.layout.base_widget_view_tools_widget_btn,null);
                    final LinearLayout ltbtn=view.findViewById(R.id.base_widget_view_tools_widget_btn_lnbtnWidget);
                    TextView textViewName=view.findViewById(R.id.base_widget_view_tools_widget_btn_txtWidgetToolName);
                    ImageView imageView=view.findViewById(R.id.base_widget_view_tools_widget_btn_imgWidgetToolIcon);
                    mWidgetManager.setWidgetBtnView(widgetEntity.getId(),textViewName,imageView);
                    ltbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int id=widgetEntity.getId();
                            BaseWidget widget=mWidgetManager.getSelectWidget();
                            if(widget!=null){
                                if(id==widget.id){
                                    if(mWidgetManager.getSelectWidget().isActiveView()){
                                        mWidgetManager.startWidgetByID(id);
                                    }else{
                                        mWidgetManager.hideSelectWidget();
                                    }
                                }else{
                                    mWidgetManager.startWidgetByID(id);
                                }
                            }else{
                                mWidgetManager.startWidgetByID(id);
                            }
                        }
                    });
                    textViewName.setText(widgetEntity.getLabel());
                    try{
                        String name=widgetEntity.getIconName();
                        if(name!=null){
                            InputStream is=getAssets().open(name);
                            Bitmap bitmap= BitmapFactory.decodeStream(is);
                            imageView.setImageBitmap(bitmap);
                        }

                    }catch (IOException e){
                        e.printStackTrace();

                    }

                    resourceConfig.baseWidgetToolsView.addView(view);
                }

            }
        }else{
            if (mConfigEntity != null) {
                List<WidgetEntity> mListWidget = mConfigEntity.getListWidget();

                String widgetGroup = "";
                List<WidgetEntity> mGroupListWidget = new ArrayList<>();

                for (int i = 0; i < mListWidget.size(); i++) {
                    WidgetEntity widgetEntity = mListWidget.get(i);
                    if(widgetEntity.getGroup()==""){
                        MenuItem menuItem= menu.add(Menu.NONE, Menu.FIRST + i, 0, widgetEntity.getLabel());
                        //记录菜单ID和WidgetEntity间对应关系
                        mWidgetEntityMenu.put(menuItem.getItemId(),widgetEntity);
                        if (widgetEntity.getIsShowing()) {
                            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);//显示到状态栏
                        }
                    }else {
                        if (widgetGroup == "") {
                            widgetGroup = widgetEntity.getGroup();//设置组名称
                            //获取组名
                            MenuItem menuItem = menu.add(Menu.NONE, Menu.FIRST + i, 0, widgetGroup);
                            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);//显示到状态栏
                            //记录菜单ID和WidgetEntity间对应关系
                            mWidgetEntityMenu.put(menuItem.getItemId(),mGroupListWidget);
                        }
                        if (widgetEntity.getGroup().equals(widgetGroup)) {//同一个组
                            mGroupListWidget.add(widgetEntity);//记录widget组信息
                        } else {
                            //不同组
                        }
                    }

                }
            }

        }
        return true;
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onResume() {
        boolean ispad= SysUtils.isPad(context);
        if(ispad==false){
            if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
        super.onResume();

    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        getLayoutInflater().inflate(R.layout.activity_toobar_view,toolbar);
        titleTextView=toolbar.findViewById(R.id.activity_baseview_toobar_view_txtTitle);
        titleTextView.setTextSize(18);
        titleTextView.setPadding(0,0,0,0);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            //exitActivity();
            //如果这里不加上该句代码，一点击到后退键该界面就消失了，选择框就一闪掉了，选择不了
            return true;
        }

        super.onOptionsItemSelected(item);
        Object object =  mWidgetEntityMenu.get(item.getItemId());
        if (object!=null){
            if (object.getClass().equals(WidgetEntity.class)){
                WidgetEntity widgetEntity = (WidgetEntity)object;
                if (widgetEntity!=null){
                    mWidgetManager.startWidgetByID(widgetEntity.getId());//显示widget
                }else {
                    ToastUtils.showShort(context,"WidgetEntity为空");
                }
            }else if (object.getClass().equals(ArrayList.class)){
                final List<WidgetEntity> list = (List<WidgetEntity>)object;
                if (list!=null){
                    PopupMenu pmp = new PopupMenu(context,findViewById(item.getItemId()));
                    for (int i=0;i<list.size();i++){
                        WidgetEntity entity = list.get(i);
                        pmp.getMenu().add(entity.getLabel());
                    }
                    pmp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            //子菜单点击事件
                            for (int i=0;i<list.size();i++){
                                WidgetEntity entity = list.get(i);
                                if (entity.getLabel().equals(item.getTitle())){
                                    mWidgetManager.startWidgetByID(entity.getId());//显示widget
                                    break;
                                }
                            }
                            return true;
                        }
                    });
                    pmp.show();
                }
            }
        }

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //exitActivity();
        }
        return super.onKeyDown(keyCode, event);
    }


}

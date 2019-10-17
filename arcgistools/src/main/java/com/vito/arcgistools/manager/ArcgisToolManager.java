package com.vito.arcgistools.manager;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;

/**
 * 兰州北科维拓
 * <p>
 * FileName: ArcgisToolManager
 * <p>
 * Author: 魏海龙
 * <p>
 * Date: 2019/10/16 11:20
 * <p>
 * Description:
 */
public class ArcgisToolManager {
    private static ArcgisToolManager arcgisToolManager=null;
    private MapView mMapView;
    private Context context;
    private DefaultMapViewOnTouchListener mapListener;

    public ArcgisToolManager( Context context,MapView mMapView) {
        this.mMapView = mMapView;
        this.context = context;
        DefaultMapViewOnTouchListener listener=new DefaultMapViewOnTouchListener(context,mMapView){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if(mapListener!=null) {
                    mapListener.onScroll(e1, e2, distanceX, distanceY);
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onRotate(MotionEvent event, double rotationAngle) {
                return super.onRotate(event, rotationAngle);
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                return super.onScale(detector);
            }
        };
        mMapView.setOnTouchListener(listener);
    }

    public static ArcgisToolManager create(Context context, MapView mMapView){
        if(arcgisToolManager==null){
            arcgisToolManager=new ArcgisToolManager(context,mMapView);
        }
        return arcgisToolManager;
    }
    public ArcgisToolManager setMapClickCallBack(DefaultMapViewOnTouchListener mapListener){
        this.mapListener=mapListener;
        return arcgisToolManager;
    }
}

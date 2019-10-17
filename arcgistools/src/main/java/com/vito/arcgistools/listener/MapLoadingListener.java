package com.vito.arcgistools.listener;
import com.esri.arcgisruntime.loadable.LoadStatus;
/**
 * 兰州北科维拓
 * <p>
 * FileName: MapLoadingListener
 * <p>
 * Author: 魏海龙
 * <p>
 * Date: 2019/10/16 10:52
 * <p>
 * Description:
 */
public interface MapLoadingListener {
    void LoadingSuccess();
    void LoadingFailed(LoadStatus status);
}

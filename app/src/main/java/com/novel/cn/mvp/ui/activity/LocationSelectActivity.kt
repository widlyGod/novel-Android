package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.CircleCommentEvent
import com.novel.cn.R
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.clicks
import com.novel.cn.ext.toast
import com.novel.cn.mvp.presenter.NothingPresenter
import com.novel.cn.mvp.ui.adapter.LocationSelectAdapter
import kotlinx.android.synthetic.main.activity_location_select.*

class LocationSelectActivity : BaseActivity<NothingPresenter>(), PoiSearch.OnPoiSearchListener {
    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    private lateinit var locationClient: AMapLocationClient
    private lateinit var locationOption: AMapLocationClientOption
    private lateinit var poiSearch: PoiSearch
    private val mAdapter by lazy { LocationSelectAdapter() }
    private var query = ""

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_location_select

    override fun initData(savedInstanceState: Bundle?) {
        initLocation()
        recyclerView.adapter = mAdapter

        locationClient.startLocation()
        refreshLayout.setOnRefreshListener { locationClient.startLocation() }
        et_location_search.textChanges().subscribe {
            query = it.toString()
            locationClient.startLocation()
        }.bindToLifecycle(this)
        iv_search_clean.clicks().subscribe {
            et_location_search.setText("")
        }.bindToLifecycle(this)
        mAdapter.clicks().subscribe {
            EventBusManager.getInstance().post(mAdapter.data[it.second])
            finish()
        }.bindToLifecycle(this)
    }

    private fun initLocation() {
        //初始化client
        locationClient = AMapLocationClient(this.applicationContext)
        locationOption = getDefaultOption()
        //设置定位参数
        locationClient.setLocationOption(locationOption)
        // 设置定位监听
        locationClient.setLocationListener(locationListener)
    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

    override fun onPoiSearched(result: PoiResult?, rcode: Int) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.query != null) {// 搜索poi的结果
                var poiItems = result.pois
                refreshLayout.finishRefresh()
                mAdapter.setNewData(poiItems)
            } else {
                toast("对不起，没有搜索到相关数据")
            }
        } else {
            toast("对不起，没有搜索到相关数据")
        }
    }

    /**
     * 定位监听
     */
    private var locationListener: AMapLocationListener = AMapLocationListener { location ->
        if (null != location) {
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.errorCode == 0) {
                poiSearch = PoiSearch(this, PoiSearch.Query(query, "", location.cityCode))
                poiSearch.setOnPoiSearchListener(this)
                poiSearch.bound = PoiSearch.SearchBound(LatLonPoint(location.latitude,
                        location.longitude), 500)//设置周边搜索的中心点以及半径
                poiSearch.searchPOIAsyn()
            } else {
                toast("定位失败${location.errorCode}")
            }
        }
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     */
    private fun getDefaultOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.isGpsFirst = false//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.httpTimeOut = 30000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.interval = 2000//可选，设置定位间隔。默认为2秒
        mOption.isNeedAddress = true//可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = true//可选，设置是否单次定位。默认是false
        mOption.isOnceLocationLatest = false//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.isSensorEnable = false//可选，设置是否使用传感器。默认是false
        mOption.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
        mOption.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption
    }

}
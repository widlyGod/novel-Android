package com.novel.cn.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.ChapterBean;
import com.novel.cn.model.entity.VolumesBean;
import com.novel.cn.persenter.Contract.CataloglContract;
import com.novel.cn.persenter.PresenterClass.CatalogPresenter;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.adapter.BookShelfAdapter;
import com.novel.cn.view.adapter.CatalogAdapter;
import com.novel.cn.view.wight.CustomLoadMoreView;
import com.novel.cn.view.wight.StateButton;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackieli on 2019/1/21.
 */

public class CatalogActivity extends AutoLayoutActivity implements CataloglContract.View,
        BaseQuickAdapter.RequestLoadMoreListener, ItemClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.tv_mulutext)
    TextView tvMulutext;
    @Bind(R.id.iv_paixu)
    ImageView ivPaixu;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.sbtn_sy)
    StateButton sbtnSy;
    @Bind(R.id.tv_mulu)
    TextView tvMulu;
    @Bind(R.id.sbtn_xy)
    StateButton sbtnXy;
    private CatalogPresenter presenter;
    private CatalogAdapter adapter;
    private String novelId="";
    private int pageNum=1;
    private String sort="ASC";
    private String volume="1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);

        novelId=getIntent().getStringExtra("id");
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CatalogAdapter(R.layout.adapter_catalog, null, this,this);
        adapter.setOnLoadMoreListener(this, rv);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        rv.setAdapter(adapter);

        presenter=new CatalogPresenter();
        presenter.setMvpView(this,"");
        presenter.getVolumes(novelId);
        presenter.getChapters(novelId,"1","10",sort,volume,false);

        //Request URL: http://59.110.124.41:8000/novelapi/novelOAService/mobile/getVolumes?novelId=bc35cf06340b498cb1e638b551616fbe
        //Request URL: http://59.110.124.41:8000/novelapi/novelOAService/mobile/getChapters

    }


    @OnClick({R.id.iv_left, R.id.iv_paixu, R.id.sbtn_sy, R.id.sbtn_xy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:{
                finish();
            }break;
            case R.id.iv_paixu:{

            }break;
            case R.id.sbtn_sy:{

            }break;
            case R.id.sbtn_xy:{

            }break;
        }
    }


    List<VolumesBean.DataBean>list=new ArrayList<>();

    //获取卷信息成功
    @Override
    public void getVolumesSuccess(VolumesBean bean) {
        if(bean.isSuccess()){
            list=bean.getData();
            if(list.size()>0){
                VolumesBean.DataBean bean1=list.get(0);
                tvMulutext.setText("第"+bean1.getVolume()+"卷 "+bean1.getTitle()+"  |  共"+bean1.getChapterNum()+"章");
            }
        }else{
            LogUtil.e("getVolumesSuccess="+bean.getMessage());
        }
    }
    //获取章节信息成功
    @Override
    public void getChaptersSuccess(ChapterBean bean,boolean isLoadMore) {
        if (bean != null) {
            List<ChapterBean.DataBean.ListBean>listBeans=bean.getData().getList();
            if (isLoadMore) {
                adapter.addData(adapter.getData().size() == 0 ? 0 : adapter.getData().size(), listBeans);
            } else {
                adapter.setNewData(listBeans);
            }
            adapter.loadMoreComplete();
            if (listBeans.size() < 10) {
                LogUtil.e("getChaptersSuccess========<100");
                adapter.loadMoreEnd(isLoadMore == true ? false : true);
            }
        } else {
            LogUtil.e("getChaptersSuccess-null");
            adapter.loadMoreEnd(isLoadMore == true ? false : true);
        }
    }

    @Override
    public void fail(String message) {
        LogUtil.e("失败原因:"+message);
    }
    //无网络
    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        presenter.getChapters(novelId,pageNum+"","10",sort,volume,true);
    }

    @Override
    public void iteamClickCallback(int type, Object parameter1, Object parameter2) {
        //跳转阅读界面
    }
}

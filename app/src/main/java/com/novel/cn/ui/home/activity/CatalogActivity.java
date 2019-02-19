package com.novel.cn.ui.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.ChapterBean;
import com.novel.cn.model.entity.VolumesBean;
import com.novel.cn.persenter.Contract.CataloglContract;
import com.novel.cn.persenter.PresenterClass.CatalogPresenter;
import com.novel.cn.util.CalculationUtil;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.adapter.CatalogAdapter;
import com.novel.cn.view.adapter.PopCatalogAdapter;
import com.novel.cn.view.adapter.PopWindowAdapter;
import com.novel.cn.view.wight.CatalogPopwindow;
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
    private PopupWindow popupWindow;
    private PopWindowAdapter popAdapter;
    private CatalogPopwindow catalogPop;
    private int intentType=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);

        novelId=getIntent().getStringExtra("id");
//        novelId="fd1dc45252b6493bb9be18133be42164";
        intentType=getIntent().getIntExtra("type",0);


        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CatalogAdapter(R.layout.adapter_catalog, null, this,this);
        adapter.setOnLoadMoreListener(this, rv);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        rv.setAdapter(adapter);

        presenter=new CatalogPresenter();
        presenter.setMvpView(this,"");
        presenter.getVolumes(novelId);
        presenter.getChapters(novelId,1,"100",sort,volume,false);

        View view = LayoutInflater.from(CatalogActivity.this).inflate(R.layout.pop_catalogall, null);
        popupWindow= new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //一定要在代码中setBackgroundDrawable，不然点击外面popupWindow是不会dismiss的
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);  //原来true
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popupWindow.showAsDropDown(tvMulutext);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //弹窗消失让箭头换回来
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.pop_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        popAdapter = new PopWindowAdapter(CatalogActivity.this, new PopWindowAdapter.MyItemClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                VolumesBean.DataBean dataBean=list.get(tag);
                volume=dataBean.getVolume();
                tvMulutext.setText("第"+dataBean.getVolume()+"卷 "+dataBean.getTitle()+"  |  共"+dataBean.getChapterNum()+"章");
                presenter.getChapters(novelId,1,"100",sort,volume,false);
                popupWindow.dismiss();
            }
        }, list);
        recyclerView.setAdapter(popAdapter);


        catalogPop=new CatalogPopwindow(CatalogActivity.this);
        catalogPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bgAlpha(1f);
            }
        });
        catalogPop.popAdapter.setItemClickListener(new PopCatalogAdapter.MyItemClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                presenter.getChapters(novelId,tag+1,"100",sort,volume,false);
                catalogPop.dismiss();
            }
        });
    }





    @OnClick({R.id.iv_left, R.id.iv_paixu, R.id.sbtn_sy, R.id.sbtn_xy,R.id.tv_mulutext,R.id.tv_mulu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:{
                finish();
            }break;
            case R.id.iv_paixu:{
                if(sort.equals("ASC")){
                    sort="DESC";
                    ivPaixu.setImageDrawable(getResources().getDrawable(R.drawable.mulu_pais));
                }else{
                    sort="ASC";
                    ivPaixu.setImageDrawable(getResources().getDrawable(R.drawable.mulu_paij));
                }
                presenter.getChapters(novelId,1,"100",sort,volume,false);
            }break;
            case R.id.sbtn_sy:{
                if(pageNum>1){
                    pageNum=pageNum-1;
                    presenter.getChapters(novelId,pageNum,"100",sort,volume,false);
                }
            }break;
            case R.id.sbtn_xy:{
                if(isAllData==false){
                    pageNum=pageNum+1;
                    presenter.getChapters(novelId,pageNum,"100",sort,volume,false);
                }

            }break;
            case R.id.tv_mulutext:{
                popAdapter.notifyDataSetChanged();
                popupWindow.showAsDropDown(tvMulutext);
            }break;
            case R.id.tv_mulu:{
                bgAlpha(0.5f);
                catalogPop.showAtLocation(CatalogActivity.this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            }break;
        }
    }


    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }


    List<VolumesBean.DataBean>list=new ArrayList<>();
    List<String>chaptersList=new ArrayList<>();

    //获取卷信息成功
    @Override
    public void getVolumesSuccess(VolumesBean bean) {
        if(bean.isSuccess()){
            List<VolumesBean.DataBean> listx=bean.getData();
            list.clear();
            list.addAll(listx);
            if(list.size()>0){
                VolumesBean.DataBean bean1=list.get(0);
                tvMulutext.setText("第"+bean1.getVolume()+"卷 "+bean1.getTitle()+"  |  共"+bean1.getChapterNum()+"章");
            }
        }else{
            LogUtil.e("getVolumesSuccess="+bean.getMessage());
        }
    }
    //获取章节信息成功
    private boolean isAllData=false;
    @Override
    public void getChaptersSuccess(ChapterBean bean,int pageNumx,boolean isLoadMore) {
        pageNum=pageNumx;
        if (bean != null) {
            List<ChapterBean.DataBean.ListBean>listBeans=bean.getData().getList();
            if (isLoadMore) {
                adapter.addData(adapter.getData().size() == 0 ? 0 : adapter.getData().size(), listBeans);
            } else {
                if(listBeans.size()>0){
                    tvMulu.setText("第"+listBeans.get(0).getChapter()+"章 -第"+
                            listBeans.get(listBeans.size()-1).getChapter()+"章");
                }
                adapter.setNewData(listBeans);
                //计算弹出框章节，100章为一页
                if(pageNumx==1){
                    chaptersList=CalculationUtil.calChapters(bean);
                }
                catalogPop.setList(chaptersList);
            }

            adapter.loadMoreComplete();
            if (listBeans.size() < 100) {
                adapter.loadMoreEnd(true);
                isAllData=true;//当前页是最后一页才可以点击下一页
            }else{//证明
                isAllData=false;//当前页不是最后一页任然可点击下一页
            }
        } else {
            adapter.loadMoreEnd(true);
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
//        pageNum++;
//        presenter.getChapters(novelId,pageNum+"","100",sort,volume,true);
    }

    @Override
    public void iteamClickCallback(int type, Object parameter1, Object parameter2, Object parameter3) {
        //跳转阅读界面
        Intent intent = new Intent(CatalogActivity.this, ReadActivity.class);
        if(intentType==0){//从阅读界面进来
            intent.putExtra("id", (String) parameter1);
            intent.putExtra("chapterIndex", (int) parameter2);
            intent.putExtra("isCharge", (int) parameter3);
            setResult(0,intent);
            finish();
        }else if(intentType==1){//从书籍详情界面进来
            intent.putExtra("id", (String) parameter1);
            intent.putExtra("novelId", novelId);
            intent.putExtra("chapterIndex", (int) parameter2);
            intent.putExtra("isIntentMulu",true);
            intent.putExtra("isCharge", (int) parameter3);
            startActivity(intent);
            finish();
        }
    }

}

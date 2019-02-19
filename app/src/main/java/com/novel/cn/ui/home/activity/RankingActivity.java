package com.novel.cn.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.FragmentListener;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookShelfAllBean;
import com.novel.cn.model.entity.BookShowBean;
import com.novel.cn.model.entity.RankingBean;
import com.novel.cn.persenter.Contract.RankingorOtherContract;
import com.novel.cn.persenter.PresenterClass.RankingorOtherPresenter;
import com.novel.cn.ui.home.fragment.BookAllShowFragment;
import com.novel.cn.ui.home.fragment.BookShowFragment;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.adapter.RankingAdapter;
import com.novel.cn.view.adapter.RankingAllAdapter;
import com.novel.cn.view.adapter.RecentUpdatesAdapter;
import com.novel.cn.view.wight.CustomLoadMoreView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 排行，男生女生频道
 * Created by jackieli on 2019/1/15.
 */

public class RankingActivity extends AutoLayoutActivity implements RankingorOtherContract.View,
        ItemClickListener, BaseQuickAdapter.RequestLoadMoreListener,FragmentListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.toolbar)
    TextView toolbar;
    @Bind(R.id.appbar)
    RelativeLayout appbar;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.framelayout)
    FrameLayout framelayout;

    //0排行，1男生，2女生,3全部频道
    private int type = 0;
    private RankingorOtherPresenter presenter;
    private RankingAdapter adapter;
    private RankingAllAdapter adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(RankingActivity.this));

        presenter=new RankingorOtherPresenter();
        presenter.setMvpView(this,"");
        type = getIntent().getIntExtra("type", 0);
        if(type==0){
            toolbar.setText("排行");
            presenter.getList();
            adapter = new RankingAdapter(R.layout.adpter_ranking, null,
                    this, RankingActivity.this,type);
            adapter.setOnLoadMoreListener(this, rv);
            adapter.setLoadMoreView(new CustomLoadMoreView());
            rv.setAdapter(adapter);

            presenter.getList();
        }else{
            toolbar.setText("书库");
            presenter.getAllTypesMo();
            adapter2 = new RankingAllAdapter(R.layout.adpter_ranking, null,
                    this, RankingActivity.this,type);
            adapter2.setOnLoadMoreListener(this, rv);
            adapter2.setLoadMoreView(new CustomLoadMoreView());
            rv.setAdapter(adapter2);
            presenter.getAllTypesMo();
        }


    }


    @OnClick(R.id.iv_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getListSuccess(RankingBean bean) {

        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        BookShowFragment fragment=new BookShowFragment();

        if (bean != null) {
            List<RankingBean.DataBean>list= bean.getData();
            if(list.size()>=1){
                list.get(0).setClck(true);
                fragment.setFragmentCode(list.get(0).getCode());
            }
            adapter.setNewData(list);
            adapter.loadMoreComplete();
            adapter.loadMoreEnd(true);
        } else {
            LogUtil.e("getBookDataSuccess-null");
            fragment.setFragmentCode("");
            adapter.loadMoreEnd(true);
        }

        transaction1.add(R.id.framelayout, fragment);
        transaction1.show(fragment);
        transaction1.commit();
    }

    @Override
    public void getNovelListSuccess(BookShowBean baseBean, boolean isLoadMore) {

    }

    @Override
    public void addBookShelfSuccess(BaseBean bean) {

    }
    //获取男生女生列表成功
    @Override
    public void getAllTypesMoSuccess(BookShelfAllBean bean) {
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        BookAllShowFragment fragment=new BookAllShowFragment();

        if (bean != null) {
            List<BookShelfAllBean.DataBean>list= bean.getData();
            if(list.size()>=2){
                if(type==1){//男生
                    list.get(0).setClck(true);
                    fragment.setFragmentCode(type,list.get(0).getChildren());
                }else{//女生
                    list.get(1).setClck(true);
                    fragment.setFragmentCode(type,list.get(1).getChildren());
                }
            }
            adapter2.setNewData(list);
            adapter2.loadMoreComplete();
            adapter2.loadMoreEnd(true);
        } else {
            LogUtil.e("getBookDataSuccess-null");
            fragment.setFragmentCode(type,null);
            adapter2.loadMoreEnd(true);
        }

        transaction1.add(R.id.framelayout, fragment);
        transaction1.show(fragment);
        transaction1.commit();
    }

    @Override
    public void fail(String message) {
        LogUtil.e("getListSuccess message="+message);
    }

    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }

    @Override
    public void iteamClickCallback(int type, Object parameter1, Object parameter2, Object parameter3) {

        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        if(type==0){
            adapter.notifyDataSetChanged();
            BookShowFragment fragment=new BookShowFragment();
            fragment.setFragmentCode((String) parameter1);
            transaction1.replace(R.id.framelayout,fragment);
        }else{
            adapter2.notifyDataSetChanged();
            BookAllShowFragment fragment=new BookAllShowFragment();
            fragment.setFragmentCode(type,adapter2.getData().get((Integer) parameter1).getChildren());
            transaction1.replace(R.id.framelayout,fragment);
        }

        transaction1.commit();
    }

    @Override
    public void onLoadMoreRequested() {
            //切换fragment


    }


    @Override
    public void onBackFragment() {
    }
    @Override
    public void onClickFragment(Object o) {
    }
}

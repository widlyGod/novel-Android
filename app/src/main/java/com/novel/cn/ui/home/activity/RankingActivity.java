package com.novel.cn.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BookShowBean;
import com.novel.cn.model.entity.RankingBean;
import com.novel.cn.persenter.Contract.RankingorOtherContract;
import com.novel.cn.persenter.PresenterClass.RankingorOtherPresenter;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.adapter.RankingAdapter;
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

public class RankingActivity extends AutoLayoutActivity implements RankingorOtherContract.View, ItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(RankingActivity.this));
        adapter = new RankingAdapter(R.layout.adpter_ranking, null, this, RankingActivity.this);
        adapter.setOnLoadMoreListener(this, rv);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        rv.setAdapter(adapter);

        presenter=new RankingorOtherPresenter();
        presenter.setMvpView(this,"");
        type = getIntent().getIntExtra("type", 0);
        switch (type){
            case 0:
                toolbar.setText("排行");
                presenter.getList();
                break;
            case 1:
                toolbar.setText("书库");
                break;
            case 2:
                toolbar.setText("书库");
                break;
        }
    }


    @OnClick(R.id.iv_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getListSuccess(RankingBean bean) {
        if (bean != null) {
            List<RankingBean.DataBean>list= bean.getData();
            adapter.setNewData(list);
            adapter.loadMoreComplete();
            adapter.loadMoreEnd(true);
        } else {
            LogUtil.e("getBookDataSuccess-null");
            adapter.loadMoreEnd(true);
        }


    }

    @Override
    public void getNovelListSuccess(BookShowBean baseBean, boolean isLoadMore) {

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
    public void iteamClickCallback(int type, Object parameter1, Object parameter2) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreRequested() {
            //切换fragment
    }
}

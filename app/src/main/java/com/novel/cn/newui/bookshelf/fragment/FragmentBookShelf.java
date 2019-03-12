package com.novel.cn.newui.bookshelf.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fingdo.statelayout.StateLayout;
import com.novel.cn.R;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.persenter.Contract.FragmentBookShelfContract;
import com.novel.cn.persenter.PresenterClass.FragmentBookShelfPresenter;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.adapter.BookShelfAdapter;
import com.novel.cn.view.wight.CustomLoadMoreView;
import com.novel.cn.view.wight.StateButton;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 书架界面
 * Created by jackieli on 2019/3/6.
 */

public class FragmentBookShelf extends BaseFragment implements View.OnClickListener, FragmentBookShelfContract.View,
        ItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.tv_ydsc)
    TextView tvYdsc;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.sb_ljqd)
    StateButton sbLjqd;
//    @Bind(R.id.bs_top)
//    RelativeLayout bsTop;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.state_layout)
    StateLayout stateLayout;
    @Bind(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private PopupWindow popMore;
    private FragmentBookShelfPresenter presenter;
    private BookShelfAdapter adapter;
    private int pageNum=1;
    private ClassicsHeader mClassicsHeader;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bookshelf;
    }

    @Override
    public void initViews() {

        mClassicsHeader = (ClassicsHeader)refreshLayout.getRefreshHeader();
        //显示下拉刷新时间
//        mClassicsHeader.setEnableLastTime(false);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),3));
        adapter=new BookShelfAdapter(R.layout.adapter_bookshelf,null,this,getActivity());
        adapter.setOnLoadMoreListener(this, recyclerview);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerview.setAdapter(adapter);


        presenter=new FragmentBookShelfPresenter();
        presenter.setMvpView(this,"");
        presenter.getAllCollection(pageNum+"","10",false);

        inintPopWindow();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum=1;
                presenter.getAllCollection(pageNum+"","10",false);
                refreshLayout.finishRefresh();
            }
        });
    }


    //初始化弹出框
    private void inintPopWindow() {
        View viewx = LayoutInflater.from(getActivity()).inflate(R.layout.pop_bs, null, false);
        TextView pop_localimo = (TextView) viewx.findViewById(R.id.pop_localimo);
        TextView pop_readrec = (TextView) viewx.findViewById(R.id.pop_readrec);
        TextView pop_batchman = (TextView) viewx.findViewById(R.id.pop_batchman);
        TextView pop_downman = (TextView) viewx.findViewById(R.id.pop_downman);
        TextView pop_autosub = (TextView) viewx.findViewById(R.id.pop_autosub);
        pop_localimo.setOnClickListener(this);
        pop_readrec.setOnClickListener(this);
        pop_batchman.setOnClickListener(this);
        pop_downman.setOnClickListener(this);
        pop_autosub.setOnClickListener(this);

        popMore = new PopupWindow(viewx, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popMore.setTouchable(true);
        popMore.setFocusable(true);  //原来true
        popMore.setOutsideTouchable(true);
        popMore.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_localimo:
                ToastUtils.showShortToast("本地导入");
                popMore.dismiss();
                break;
            case R.id.pop_readrec:
                ToastUtils.showShortToast("阅读记录");
                popMore.dismiss();
                break;
            case R.id.pop_batchman:
                ToastUtils.showShortToast("批量管理");
                popMore.dismiss();
                break;
            case R.id.pop_downman:
                ToastUtils.showShortToast("下载管理");
                popMore.dismiss();
                break;
            case R.id.pop_autosub:
                ToastUtils.showShortToast("自动订阅");
                popMore.dismiss();
                break;
        }
    }

    @OnClick({R.id.iv_more, R.id.sb_ljqd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_more:{
                popMore.showAsDropDown(ivMore, 0, 0);
            }break;
            case R.id.sb_ljqd:{
                ToastUtils.showShortToast("签到");
            }break;
        }
    }

    @Override
    public void getAllCollectionSuccess(BookShelfBean bean,boolean isLoadMore) {
        List<BookShelfBean.DataBean.BookBean> dataBeans = bean.getData().getBook();
        if(bean.isSuccess() && dataBeans!=null){
            if (isLoadMore) {
                adapter.addData(adapter.getData().size() == 0 ? 0 : adapter.getData().size(), dataBeans);
            } else {
                adapter.setNewData(dataBeans);
            }

            adapter.loadMoreComplete();
            if (dataBeans.size() < 10) {
                LogUtil.e("========<10");
                adapter.loadMoreEnd(isLoadMore == true ? false : true);
            }
        }else{
            adapter.loadMoreEnd(isLoadMore == true ? false : true);

        }
    }

    @Override
    public void fail(String message) {
        LogUtil.e("错误信息:"+message);
    }

    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }

    @Override
    public void iteamClickCallback(int type, Object parameter1, Object parameter2, Object parameter3) {

    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        presenter.getAllCollection(pageNum+"","10",true);
        LogUtil.e("加载更多");

    }


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if(hidden==false) {
//            pageNum=1;
//            presenter.getAllCollection(pageNum+"","10",false);
//        }
//    }


}

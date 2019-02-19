package com.novel.cn.ui.book.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.novel.cn.R;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.PersonDataBean;
import com.novel.cn.persenter.Contract.FragmentBookContract;
import com.novel.cn.persenter.PresenterClass.FragmentBookPresenter;
import com.novel.cn.ui.home.activity.BookDetailsActivity;
import com.novel.cn.ui.home.activity.ReadActivity;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.adapter.BookShelfAdapter;
import com.novel.cn.view.adapter.RecentUpdatesAdapter;
import com.novel.cn.view.wight.CustomLoadMoreView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jackieli on 2018/12/26.
 */

public class FragmentBook extends BaseFragment implements FragmentBookContract.View, ItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.tv_collectionBook)
    TextView tvCollectionBook;
    @Bind(R.id.rv)
    RecyclerView rv;
    private FragmentBookPresenter presenter;
    private BookShelfAdapter adapter;
    private int pageNum=1;
    private int type=1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    public void initViews() {
        presenter=new FragmentBookPresenter();
        presenter.setMvpView(this,"");
        presenter.getBookData(type,false,"1","10");

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BookShelfAdapter(R.layout.adapter_bookshelf, null, this, getActivity());
        adapter.setOnLoadMoreListener(this, rv);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        rv.setAdapter(adapter);

        tablayout.addTab(tablayout.newTab().setText("我的收藏"));
        tablayout.addTab(tablayout.newTab().setText("我的订阅"));
        tablayout.addTab(tablayout.newTab().setText("阅读历史"));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选择了哪个书架模块
                type=tab.getPosition();
                adapter.setType(type);
                pageNum=1;
                presenter.getBookData(type,false,"1","10");
                //0我的收藏     1我的订阅   2阅读历史
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    @Override
    public void initData() {

    }


    @Override
    public void iteamClickCallback(int type, Object parameter1, Object parameter2, Object parameter3) {
        switch (type){
            case 0://继续阅读
                Intent intent=new Intent(getActivity(), ReadActivity.class);
                intent.putExtra("id", (String) parameter1);
                intent.putExtra("novelId", (String) parameter2);
                //接口需加个字段章节chapter
                intent.putExtra("isCharge", 0);
                startActivity(intent);
                break;
            case 1://取消操作
                presenter.cancelOper(type,(String) parameter1);
                break;
            case 2://书籍详情
                Intent intentx=new Intent(getActivity(), BookDetailsActivity.class);
                intentx.putExtra("id",(String) parameter1);
                startActivity(intentx);
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        presenter.getBookData(type,true,pageNum+"","10");
    }




    //获取书籍列表成功
    @Override
    public void getBookDataSuccess(BookShelfBean baseBean,boolean isLoadMore) {

        List<BookShelfBean.DataBean.BookBean> dataBeans = baseBean.getData().getBook();

        if (dataBeans != null) {
            if (isLoadMore) {
                adapter.addData(adapter.getData().size() == 0 ? 0 : adapter.getData().size(), dataBeans);
            } else {
                tvCollectionBook.setText("收藏书籍:"+baseBean.getData().getTotal()+"本");
                adapter.setNewData(dataBeans);
            }
            adapter.loadMoreComplete();
            if (dataBeans.size() < 10) {
                LogUtil.e("getBookDataSuccess========<100");
                adapter.loadMoreEnd(isLoadMore == true ? false : true);
            }
        } else {
            LogUtil.e("getBookDataSuccess-null");
//            adapter.loadMoreEnd(false);
            adapter.loadMoreEnd(isLoadMore == true ? false : true);
        }

    }

    //取消操作
    @Override
    public void cancelOperSuccess(BaseBean baseBean) {
        if(baseBean.isSuccess()){
            pageNum=1;
            presenter.getBookData(type,false,"1","10");
        }else{
            ToastUtils.showShortToast(baseBean.getMessage());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden==false) {
            pageNum=1;
            presenter.getBookData(type,false,"1","10");
        }
    }


    //失败操作
    @Override
    public void fail(String message) {
        LogUtil.e("我的书架错误信息:"+message);
    }

    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }



}

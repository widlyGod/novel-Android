package com.novel.cn.ui.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.novel.cn.R;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BookShelfAllBean;
import com.novel.cn.view.adapter.BookShowAdapter;
import com.novel.cn.view.adapter.BookShowAllAdapter;
import com.novel.cn.view.wight.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 书库展示
 * Created by jackieli on 2019/1/16.
 */

public class BookAllShowFragment extends BaseFragment implements ItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.rv)
    RecyclerView rv;
    List<BookShelfAllBean.DataBean.ChildrenBean> list=new ArrayList<>();
    int type=1;
    private BookShowAllAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_bookshow;
    }


    public void setFragmentCode(int typex,Object o){
        type=typex;
        list= (List<BookShelfAllBean.DataBean.ChildrenBean>) o;
    }


    @Override
    public void initViews() {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BookShowAllAdapter(R.layout.adapter_bookshowall, list, this, getActivity());
        adapter.setOnLoadMoreListener(this, rv);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        rv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        adapter.setNewData(list);
        adapter.loadMoreComplete();
        adapter.loadMoreEnd( true);
    }
    //单条点击
    @Override
    public void iteamClickCallback(int type, Object parameter1, Object parameter2, Object parameter3) {

    }

    //加载更多
    @Override
    public void onLoadMoreRequested() {

    }
}

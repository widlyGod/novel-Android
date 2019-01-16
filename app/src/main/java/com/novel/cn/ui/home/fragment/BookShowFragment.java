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
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookShelfAllBean;
import com.novel.cn.model.entity.BookShowBean;
import com.novel.cn.model.entity.RankingBean;
import com.novel.cn.persenter.Contract.RankingorOtherContract;
import com.novel.cn.persenter.PresenterClass.RankingorOtherPresenter;
import com.novel.cn.ui.home.activity.RankingActivity;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.adapter.BookShowAdapter;
import com.novel.cn.view.adapter.RankingAdapter;
import com.novel.cn.view.wight.CustomLoadMoreView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 书籍列表界面
 * Created by jackieli on 2019/1/16.
 */

public class BookShowFragment extends BaseFragment implements RankingorOtherContract.View, ItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.rv)
    RecyclerView rv;
    String code = "";

    private BookShowAdapter adapter;
    private RankingorOtherPresenter presenter;
    private int pageNum=1;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_bookshow;
    }


    public void setFragmentCode(String codex) {
        code = codex;
    }


    @Override
    public void initViews() {

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BookShowAdapter(R.layout.adapter_book, null, this, getActivity());
        adapter.setOnLoadMoreListener(this, rv);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        rv.setAdapter(adapter);
        presenter=new RankingorOtherPresenter();
        presenter.setMvpView(this,"");
        presenter.getNovelList(code,"1",pageNum+"",false);


        LogUtil.e("当前fragment code=" + code);
    }

    @Override
    public void initData() {

    }

    @Override
    public void getListSuccess(RankingBean bean) {



    }


    //获取列表成功
    @Override
    public void getNovelListSuccess(BookShowBean baseBean, boolean isLoadMore) {
        if (baseBean != null) {
            List<BookShowBean.DataBean.WEEKBean> list= baseBean.getData().getWEEK();
            if (isLoadMore) {
                adapter.addData(adapter.getData().size() == 0 ? 0 : adapter.getData().size(), list);
            } else {
                adapter.setNewData(list);
            }
            adapter.loadMoreComplete();
            if (list.size() < 10) {
                LogUtil.e("getNovelListSuccess========<100");
                adapter.loadMoreEnd(isLoadMore == true ? false : true);
            }
        } else {
            LogUtil.e("getNovelListSuccess-null");
            adapter.loadMoreEnd(isLoadMore == true ? false : true);
        }
    }

    @Override
    public void addBookShelfSuccess(BaseBean bean) {
        ToastUtils.showShortToast(bean.getMessage());
        presenter.getNovelList(code,"1",pageNum+"",false);
    }

    @Override
    public void getAllTypesMoSuccess(BookShelfAllBean bean) {

    }


    @Override
    public void fail(String message) {
        LogUtil.e("getListSuccess message="+message);
    }

    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }

    //部分点击
    @Override
    public void iteamClickCallback(int type, Object parameter1, Object parameter2) {
        switch (type){
            case 0:
                presenter.addBookShelf((String) parameter1);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    //加载更多
    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        presenter.getNovelList(code,"1",pageNum+"",true);
    }

}

package com.novel.cn.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.novel.cn.R;
import com.novel.cn.app.Constants;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BaseListObjectBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.persenter.Contract.FragmentHomeContract;
import com.novel.cn.persenter.PresenterClass.FragmentHomePresenter;
import com.novel.cn.ui.LoginActivity;
import com.novel.cn.ui.home.activity.BookDetailsActivity;
import com.novel.cn.ui.home.activity.RankingActivity;
import com.novel.cn.ui.home.activity.StackRoomActivity;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.SharePrefUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.adapter.RecentUpdatesAdapter;
import com.novel.cn.view.wight.CustomLoadMoreView;
import com.novel.cn.view.wight.GlideImageLoader;
import com.novel.cn.view.wight.HomeBookPanel;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackieli on 2018/12/26.
 */

public class FragmentHome extends BaseFragment implements OnBannerClickListener,ItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener,FragmentHomeContract.View {

    @Bind(R.id.iv_fragment_personal_face)
    SimpleDraweeView ivFragmentPersonalFace;
    @Bind(R.id.appbar)
    RelativeLayout appbar;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.home_ranking)
    RadioButton homeRanking;
    @Bind(R.id.home_book)
    RadioButton homeBook;
    @Bind(R.id.home_boy)
    RadioButton homeBoy;
    @Bind(R.id.home_girl)
    RadioButton homeGirl;
    @Bind(R.id.ll_book)
    LinearLayout ll_book;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.tv_hotsearch1)
    TextView tvHotsearch1;
    @Bind(R.id.tv_hotsearch2)
    TextView tvHotsearch2;
    @Bind(R.id.tv_hotsearch3)
    TextView tvHotsearch3;
    @Bind(R.id.rv)
    RecyclerView rv;
    private RecentUpdatesAdapter adapter;
    private int pageNo = 1;
    FragmentHomePresenter presenter;
    private Handler handler = null;
    private Runnable runnable = null;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }



    @Override
    public void initViews() {
        presenter=new FragmentHomePresenter();
        presenter.setMvpView(this,"");
        presenter.getHomePage();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecentUpdatesAdapter(R.layout.item_recentupdates, null, this, getActivity());
        adapter.setOnLoadMoreListener(this, rv);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        rv.setAdapter(adapter);

        banner.setOnBannerClickListener(this);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        inintHomeView();


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //要做的事情
                if(handler!=null){
                    pane2.changeFreeBook();
                    pane3.changeFreeBook();
                    handler.postDelayed(this, 5000);//600000
                }
            }
        };
        handler.postDelayed(runnable, 5000);//每10分钟执行一次runnable.  1000=1s  600000
    }


    private HomeBookPanel pane0;
    private HomeBookPanel pane1;
    private HomeBookPanel pane2;
    private HomeBookPanel pane3;

    private void inintHomeView() {
//        HomeReturnBean bean = HomeReturnBean.objectFromData(Constants.homeText);
        pane0 = new HomeBookPanel(getActivity());
        pane0.setType(0);
//        pane0.setRecommend(bean.getData());
        ll_book.addView(pane0);
        ll_book.addView(LayoutInflater.from(getActivity()).inflate(R.layout.whitebackground, null));

        pane1 = new HomeBookPanel(getActivity());
        pane1.setType(1);
//        pane1.setRecommend(bean.getData());
        ll_book.addView(pane1);
        ll_book.addView(LayoutInflater.from(getActivity()).inflate(R.layout.whitebackground, null));

        pane2 = new HomeBookPanel(getActivity());
        pane2.setType(2);
//        pane2.setRecommend(bean.getData());
        ll_book.addView(pane2);
        ll_book.addView(LayoutInflater.from(getActivity()).inflate(R.layout.whitebackground, null));

        pane3 = new HomeBookPanel(getActivity());
        pane3.setType(3);
//        pane3.setRecommend(bean.getData());
        ll_book.addView(pane3);
        ll_book.addView(LayoutInflater.from(getActivity()).inflate(R.layout.whitebackground, null));

    }

    /**
     * 轮播图
     */
    private void bannerImage(List<String> images) {
        //设置图片集合
//        images.add("http://59.110.124.41:80/novel_b/novel/ce006ab1e5e8423e8c9de91aa12c2d34/bef18de92c8c4d7e93a268a72c6e0fcf.png");
//        images.add("http://59.110.124.41:80/novel_b/novel/2f0b62a919f041bc862a7022f6161be0/0c1aafbb61e54fc499141a8388e352bd.png");
//        images.add("http://59.110.124.41:80/novel_a/novel/c3e68d9d8d9f467a892e4ea0b4e98ed6/d532b134d99a43b99bb7ea45328cc273.png");
//        images.add("http://59.110.124.41:80/novel_a/novel/2982353c29be41e19625ff2faf3a6728/12f3069ca152469e86394b5916b1c5d9.png");
//        images.add("http://59.110.124.41:80/novel_b/novel/7a553d07dbb1478c864507d349044f31/3f416d76834c474c810267aea982526b.png");
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.startAutoPlay();

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.iv_fragment_personal_face, R.id.home_ranking, R.id.home_book, R.id.home_boy, R.id.home_girl,
            R.id.tv_hotsearch1, R.id.tv_hotsearch2, R.id.tv_hotsearch3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_fragment_personal_face: {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }break;
            case R.id.home_ranking: {
                IntentActivity(0);
            }break;
            case R.id.home_book:
                Intent intent = new Intent(getActivity(), StackRoomActivity.class);
                startActivity(intent);
                break;
            case R.id.home_boy:
                IntentActivity(1);
                break;
            case R.id.home_girl:
                IntentActivity(2);
                break;
            case R.id.tv_hotsearch1:
                break;
            case R.id.tv_hotsearch2:
                break;
            case R.id.tv_hotsearch3:
                break;
        }
    }


    public void IntentActivity(int type){
        Intent intent=new Intent(getActivity(), RankingActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }

    //广告图的点击事件
    @Override
    public void OnBannerClick(int position) {

    }

    //单个最近更新点击
    @Override
    public void iteamClickCallback(int type, Object parameter1, Object parameter2, Object parameter3) {
        //跳转逻辑
        Intent intent=new Intent(getActivity(), BookDetailsActivity.class);
        intent.putExtra("id", (String) parameter1);
        startActivity(intent);
    }

    //底部加载更多
    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        presenter.getRecentUpdatedNovel(pageNo+"", "10");
    }

    //获取首页成功
    @Override
    public void getHomePageSuccess(HomeReturnBean data) {
        if(data!=null){
            if(data.isSuccess()){

                List<HomeReturnBean.DataBean.HotRecommendBean>hotList=data.getData().getHotRecommend();
                if(data.getData().getHotRecommend().size()>=3){
                    tvHotsearch1.setText(hotList.get(0).getNovelTitle());
                    tvHotsearch2.setText(hotList.get(1).getNovelTitle());
                    tvHotsearch3.setText(hotList.get(2).getNovelTitle());
                }

                List<String>list=new ArrayList<>();
                for(int i=0;i<data.getData().getHeadRecommend().size();i++){
                    list.add(data.getData().getHeadRecommend().get(i).getNovelPhoto());
                }
                bannerImage(list);
                pane0.setRecommend(data.getData());
                pane1.setRecommend(data.getData());
                pane2.setRecommend(data.getData());
                pane3.setRecommend(data.getData());
                adapter.setNewData(data.getData().getRecentUpdate());
                adapter.loadMoreComplete();
                if (data.getData().getRecentUpdate().size() < 10) {
                    adapter.loadMoreEnd(true);
                }
            }
        }
    }

    //获取最近更新成功
    @Override
    public void getRuSuccess(BaseListObjectBean<HomeReturnBean.DataBean.RecentUpdateBean> data) {
//        adapter.setNewData(bean.getData().getRecentUpdate());
        List<HomeReturnBean.DataBean.RecentUpdateBean> dataBeans = data.getData();
        if (dataBeans != null) {
            adapter.addData(adapter.getData().size() == 0 ? 0 : adapter.getData().size(), dataBeans);
            adapter.loadMoreComplete();
            if (dataBeans.size() < 10) {
                LogUtil.e("getRuSuccess========<100");
                adapter.loadMoreEnd(false);
            }
        } else {
            LogUtil.e("getRuSuccess-null");
            adapter.loadMoreEnd(false);
//            adapter.loadMoreEnd(isLoadMore == true ? false : true);
        }
    }


    //失败
    @Override
    public void fail(String message) {
        LogUtil.e("首页错误信息:"+message);
    }


    //无网络
    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }


    @Override
    public void onResume() {
        super.onResume();
        setTouXImage();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.e("fragmentMy 更新个人信息="+hidden);
        setTouXImage();
    }

    public void setTouXImage(){
        boolean isLogin = SharePrefUtil.getBoolean(getActivity(), "isLogin", false);
        if(isLogin){
            LogUtil.e("fragmentHome 更新个人信息");
            String userBean= SharePrefUtil.getString(getActivity(),"user","");
            if(!userBean.equals("")){
                UserBean userBean1=UserBean.objectFromData(userBean);
                if(userBean1.getUserPhoto()!=null){
                    ivFragmentPersonalFace.setImageURI(userBean1.getUserPhoto());
                }
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        banner.stopAutoPlay();
        ButterKnife.unbind(this);
    }

}

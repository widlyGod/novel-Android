package com.novel.cn.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.novel.cn.R;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.ui.LoginActivity;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.GlideImageLoader;
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

public class FragmentHome extends BaseFragment implements OnBannerClickListener {

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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    //水平3，8     垂直2，2，13
    @Override
    public void initViews() {
        bannerImage();


    }

    /**
     * 轮播图
     */
    private void bannerImage() {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.home_image4);
        images.add(R.drawable.home_image4);
        images.add(R.drawable.home_image4);
        images.add(R.drawable.home_image4);
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.startAutoPlay();

//        banner.stopAutoPlay();
        banner.setOnBannerClickListener(this);
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.iv_fragment_personal_face,R.id.home_ranking, R.id.home_book, R.id.home_boy, R.id.home_girl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_fragment_personal_face: {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.home_ranking:{
                ToastUtils.showShortToast("排行");
            }break;
            case R.id.home_book:
                ToastUtils.showShortToast("书籍");
                break;
            case R.id.home_boy:
                ToastUtils.showShortToast("男孩");
                break;
            case R.id.home_girl:
                ToastUtils.showShortToast("女孩");
                break;
        }
    }

    //广告图的点击事件
    @Override
    public void OnBannerClick(int position) {

    }



}

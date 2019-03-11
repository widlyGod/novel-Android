package com.novel.cn.newui.bookshelf.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fingdo.statelayout.StateLayout;
import com.novel.cn.R;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.StateButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 书架界面
 * Created by jackieli on 2019/3/6.
 */

public class FragmentBookShelf extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.tv_ydsc)
    TextView tvYdsc;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.sb_ljqd)
    StateButton sbLjqd;
    @Bind(R.id.bs_top)
    RelativeLayout bsTop;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.state_layout)
    StateLayout stateLayout;
    private PopupWindow popMore;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bookshelf;
    }

    @Override
    public void initViews() {

        inintPopWindow();

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

}

package com.novel.cn.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.novel.cn.interfaceFolder.FragmentListener;
import butterknife.ButterKnife;

/**fragment基类
 * Created by jackieli on 2018/forgetpstwo/9.
 */

public abstract class BaseFragment extends Fragment {

    protected FragmentListener mListener;
    public abstract int getLayoutId();
    public abstract void initViews();
    public abstract void initData();



    //activity接口
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentListener) {
            mListener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    //创建初始化布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflateView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, inflateView);

        initViews();
        initData();
        return inflateView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }




}

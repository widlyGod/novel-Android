package com.novel.cn.utils

import android.support.v4.app.Fragment

/**
 * Created by hy on 2018/11/15
 */
interface SwitchFragmentSupport {

    var lastSwitchShowFragment: Fragment?
    var parentSwitchFragmentSupport: SwitchFragmentSupport?
}
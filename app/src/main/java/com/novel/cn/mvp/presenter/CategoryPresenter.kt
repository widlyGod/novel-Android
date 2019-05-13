package com.novel.cn.mvp.presenter

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.mvp.contract.CategoryContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Category
import com.novel.cn.mvp.ui.adapter.CategoryAdapter
import com.novel.cn.view.MultiStateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


@FragmentScope
class CategoryPresenter
@Inject
constructor(model: CategoryContract.Model, rootView: CategoryContract.View) :
        BasePresenter<CategoryContract.Model, CategoryContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mAdapter: CategoryAdapter

    fun getCategory() {
        mModel.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<Category>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<Category>>) {
                        val data = t.data
                        if (data.isEmpty()) {
                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
                        } else {
                            mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)

                            val list = ArrayList<MultiItemEntity>()

                            //组装数据
                            data.forEach {
                                list.add(it)
                                it.children.forEach { child ->
                                    list.add(child)
                                }
                            }
                            mAdapter.setNewData(list)
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.showState(MultiStateView.VIEW_STATE_ERROR)
                    }
                })
    }
}

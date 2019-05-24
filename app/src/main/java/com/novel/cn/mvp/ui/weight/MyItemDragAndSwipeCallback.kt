package com.novel.cn.mvp.ui.weight

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by jmw on 2019/4/17
 */
open class MyItemDragAndSwipeCallback(adapter: BaseItemDraggableAdapter<*, *>, deleteView: View? = null) : ItemDragAndSwipeCallback(adapter) {

    // 是否于拖拽状态
    private val mDragStateSubject = BehaviorSubject.create<Boolean>()
    // 是否处于删除状态
    private val mDeleteStateSubject = BehaviorSubject.create<Boolean>()
    // 删除事件
    private val mOnDeletedEvent = BehaviorSubject.create<Unit>()

    private val mAdapter = adapter
    private val mDeleteView = deleteView
    var mTouchUp = false

    init {
        initData()
    }

    /**
     * 当长按选中item的时候（拖拽开始的时候）调用
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (ItemTouchHelper.ACTION_STATE_DRAG == actionState) {
            mDragStateSubject.onNext(true)
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    /**
     * 手指离开后ViewHolder的动画时间，在用户手指离开后调用
     */

    override fun getAnimationDuration(recyclerView: RecyclerView, animationType: Int, animateDx: Float, animateDy: Float): Long {
        mTouchUp = true
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy)
    }

    /**
     * 自定义拖动与滑动交互
     */
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (mDeleteView == null) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        val location = intArrayOf(0, 0)
        recyclerView.getLocationInWindow(location)
        val rvWindowY = location.last()
        mDeleteView.getLocationInWindow(location)
        val deleteViewY = location.last()

        if (dY + viewHolder.itemView.bottom + rvWindowY >= deleteViewY) {
            mDeleteStateSubject.onNext(true)
            if (mTouchUp) { // 在删除处松手
                viewHolder.itemView.visibility = View.INVISIBLE // 先设置不可见，如果不设置的话，会看到viewHolder返回到原位置时才消失，因为remove会在viewHolder动画执行完成后才将viewHolder删除
                mAdapter.data.removeAt(viewHolder.adapterPosition)
                mAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                initData()
                mOnDeletedEvent.onNext(Unit)
            }
        } else {
            if (View.INVISIBLE == viewHolder.itemView.visibility) {//如果viewHolder不可见，则表示用户放手，重置删除区域状态
                mDragStateSubject.onNext(false)
            }
            mDeleteStateSubject.onNext(false)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    /**
     * 当用户与item的交互结束并且item也完成了动画时调用
     */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        initData()
    }

    /**
     * 观察拖拽状态变化
     */
    fun observeDragState(): Observable<Boolean> {
        return mDragStateSubject.distinctUntilChanged()
    }

    /**
     * 观察删除状态变化
     */
    fun observeDeleteState(): Observable<Boolean> {
        return mDeleteStateSubject.distinctUntilChanged()
    }

    /**
     * 观察删除状态变化
     */
    fun observeDeletedEvent(): Observable<Unit> {
        return mOnDeletedEvent
    }

    /**
     * 重置
     */
    private fun initData() {
        mTouchUp = false
        mDragStateSubject.onNext(false)
        mDeleteStateSubject.onNext(false)
    }
}
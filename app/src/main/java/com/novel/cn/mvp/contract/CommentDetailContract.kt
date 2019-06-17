package com.novel.cn.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Reply
import io.reactivex.Observable


interface CommentDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun showState(state: Int)
        fun replySuccess(message: String)
        fun deleteCommentSuccess()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getReplyList(params: HashMap<String, Any>): Observable<BaseResponse<MutableList<Reply>>>
        fun reply(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
        fun deleteComment(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
    }

}

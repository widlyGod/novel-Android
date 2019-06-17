package com.novel.cn.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Comment
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/09/2019 09:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface CommentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun showCommentCount(count: Int)
        fun showState(state: Int)
        fun commentSuccess(message: String)
        fun agreeSuccess()
        fun replySuccess(message: String)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getCommentList(params: HashMap<String, String>): Observable<BaseResponse<MutableList<Comment>>>
        fun agree(commentId: String,type:Int): Observable<BaseResponse<Any>>
        fun comment(params: HashMap<String, String?>): Observable<BaseResponse<Any>>
        fun deleteComment(commentId: String): Observable<BaseResponse<Any>>
        fun reply(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
    }

}

package com.novel.cn.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.CircleCommentRaeplyAllBean
import com.novel.cn.mvp.model.entity.Content
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 14:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface CircleCommentReplyDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun agreeSuccess()
        fun chapterCommentSuccess()
        fun RefreshFinsh()
        fun getReplyDetaillSuccess(content: Content)
        fun showState(state: Int)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun agreeReply(momentId: String): Observable<BaseResponse<Any>>
        fun agreeReplyReply(replyId: String): Observable<BaseResponse<Any>>
        fun chapterCommentReply(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
        fun getReplyDetail(replyId: String): Observable<BaseResponse<Content>>
        fun getReplys(params: HashMap<String, String>): Observable<BaseResponse<CircleCommentRaeplyAllBean>>
        fun disAgree(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
        fun deleteCircleReply(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
    }

}

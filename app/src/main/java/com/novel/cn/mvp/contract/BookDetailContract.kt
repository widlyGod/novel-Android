package com.novel.cn.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.NovelInfoBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/01/2019 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface BookDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun showBookDetail(data: NovelInfoBean)
        fun conllectionSuccess()
        fun conllectionFail()
        fun commentSuccess(message: String)
        fun replySuccess(message: String)
        fun showState(state: Int)
        fun deleteCommentSuccess()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getBookDetail(bookId: String?): Observable<BaseResponse<NovelInfoBean>>
        fun addConllection(params: HashMap<String, Any>): Observable<BaseResponse<Any>>
        fun agree(commentId: String, type: Int): Observable<BaseResponse<Any>>
        fun deleteComment(commentId: String): Observable<BaseResponse<Any>>
        fun comment(params: HashMap<String, String?>): Observable<BaseResponse<Any>>
        fun reply(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
        fun getCommentList(params: HashMap<String, String>): Observable<BaseResponse<MutableList<Comment>>>
    }

}

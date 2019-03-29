package com.novel.cn.mvp.contract

import android.content.Context
import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.Pagination
import com.novel.cn.mvp.model.entity.SignIn
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface BookshelfContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun getContext(): Context
        fun complete(pullToRefresh: Boolean)
        fun showBookshelfList(pullToRefresh: Boolean, book: List<Book>)
        fun noMore()
        fun signInSuccess()
        fun changeSignInInfo(data: SignIn)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getBookshelf(pageIndex: Int):Observable<BaseResponse<Pagination<Book>>>
        fun signIn(params: HashMap<String, String>):Observable<BaseResponse<Any>>
        fun validateSignIn():Observable<BaseResponse<SignIn>>
    }

}

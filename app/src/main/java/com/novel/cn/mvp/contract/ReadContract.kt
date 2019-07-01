package com.novel.cn.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.view.readpage.TxtChapter
import com.zchu.rxcache.data.CacheResult
import io.reactivex.Observable
import okhttp3.ResponseBody


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/03/2019 15:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface ReadContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun showVolume(data: MutableList<Volume>?)
        fun showChapterList(volume: String?, data: ChapterBean)
        fun loadChapterSuccess()
        fun collectionSuccess()
        fun cancelCollection()
        fun showChapter(data: ChapterInfoBean, txtChapter: TxtChapter, mCurChapterPos: Int, charge: ChargeChapter)
        fun isChargeChapter(data: ChargeChapter)
        fun openBook(mCurChapterPos: Int, txtChapter: TxtChapter?)
        fun subscribeError()
        fun showCalalogueInfo(list: ArrayList<VolumeBean>)
        fun getUserAccountInfoSuccess(userAccountBean: UserAccountBean)
        fun reward(operation: String, number: Int)
        fun showUserInfo(data: User?)
//        fun subscribeSuccess()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getVolumeList(bookId: String?): Observable<BaseResponse<MutableList<Volume>?>>
        fun getChapterList(params: HashMap<String, Any?>): Observable<BaseResponse<ChapterBean>>
        fun readNovel(params: HashMap<String, Any?>): Observable<BaseResponse<ChapterInfoBean>>
        fun addConllection(params: HashMap<String, Any>): Observable<BaseResponse<Any>>
        fun cancelCollection(bookId: String): Observable<BaseResponse<Any>>
        fun subscribeBook(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
        fun isChargeChapter(param: HashMap<String, Any?>): Observable<BaseResponse<ChargeChapter>>
        fun getChapterInfo(link: String?): Observable<BaseResponse<ChapterInfoBean>>
        fun getCalalogue(novelId: String): Observable<CacheResult<CalalogueVo>>
        fun preDownload(url: String?): Observable<ResponseBody>
        fun updateRead(params: ArrayList<HashMap<String, Any>>): Observable<BaseResponse<Any>>
        fun getUserAccountInfo(): Observable<BaseResponse<UserAccountBean>>
        fun reward(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
        fun addAutoSubscribe(list: ArrayList<String>): Observable<BaseResponse<Any>>
        fun clickNum(params: HashMap<String, Any?>): Observable<BaseResponse<Any>>
        fun getUserInfo():Observable<BaseResponse<User>>
    }

}

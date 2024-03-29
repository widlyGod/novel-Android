package com.novel.cn.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.novel.cn.mvp.model.entity.*
import com.zchu.rxcache.data.CacheResult
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/30/2019 14:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface ContentsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun showVolume(data: MutableList<Volume>?)
        fun showChapterList(volume: String?, data: ChapterBean)
        fun showCalalogueInfo(list: ArrayList<VolumeBean>)
        fun selectPage(page: Int)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getVolumeList(bookId: String?): Observable<BaseResponse<MutableList<Volume>?>>
        fun getChapterList(params: HashMap<String, Any?>): Observable<BaseResponse<ChapterBean>>
        fun getCalalogue(novelId: String): Observable<CacheResult<CalalogueVo>>
    }


}

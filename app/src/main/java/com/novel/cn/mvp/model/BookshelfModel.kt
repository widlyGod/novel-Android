package com.novel.cn.mvp.model

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import javax.inject.Inject

import com.novel.cn.mvp.contract.BookshelfContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.*
import com.zchu.rxcache.data.CacheResult
import com.zchu.rxcache.kotlin.rxCache
import com.zchu.rxcache.stategy.CacheStrategy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


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
@FragmentScope
class BookshelfModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), BookshelfContract.Model {
    companion object {
        private val CACHE_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }

    val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
    override fun validateSignIn(): Observable<BaseResponse<SignIn>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).validateSignIn()
    }

    override fun signIn(params: HashMap<String, String>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).signIn(params)
    }

    override fun getBookshelf(pageIndex: Int): Observable<CacheResult<Pagination<Book>>> {

        val params = HashMap<String, String>()
        params.put("pageNum", pageIndex.toString())
        params.put("pageSize", Constant.PAGE_SIZE.toString())

        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getBookshelf(params)
                .map { it.data }
                .rxCache("bookshelf$pageIndex", CacheStrategy.firstRemoteSync())
    }

    override fun getReadTime(): Observable<BaseResponse<ReadTimeBean>> {

        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getReadTime(user!!.userId)
    }

    override fun getBookDetail(bookId: String?): Observable<BaseResponse<NovelInfoBean>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getBookDetail(bookId)
    }

    override fun getCalalogue(novelId: String): Observable<CacheResult<CalalogueVo>> {

        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getCalalogue(novelId)
                .map { it.data }
                .rxCache("catalogue$novelId", CacheStrategy.firstCacheTimeout(CACHE_TIMEOUT))
    }

}

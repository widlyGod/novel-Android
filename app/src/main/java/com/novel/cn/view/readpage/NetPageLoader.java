package com.novel.cn.view.readpage;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by newbiechen on 17-5-29.
 * 网络页面加载器
 */

public class NetPageLoader extends PageLoader {
    private static final String TAG = "PageFactory";

    public NetPageLoader(PageView pageView) {
        super(pageView);
    }

    private int size = 4;


    @Override
    public void setChapterList(List<TxtChapter> chapterList) {

        // 将 BookChapter 转换成当前可用的 Chapter
        mChapterList = chapterList;
        isChapterListPrepare = true;

        // 目录加载完成，执行回调操作。
        if (mPageChangeListener != null) {
            mPageChangeListener.onCategoryFinish(mChapterList);
        }

        // 如果章节未打开
        if (!isChapterOpen()) {
            // 打开章节
            openChapter();
        }
    }

    @Override
    protected BufferedReader getChapterReader(TxtChapter chapter) throws Exception {
        String json = "123保安队白长找到高经理把情况一说，高经理也犯愁，说：“他刚来第一天就辞退，恐怕不太好吧，再说这些刑满释放人员都不是好惹的，万一报复咱们怎么办？”\n白队长说：“可是让他在咱们这上班，迟早闹出乱子来，你是没看见他和胡警官说话那个态度，简直……”他愤愤不平的一拍桌子，好像刘子光欺负了他家女性亲属一样。\n高经理低头做沉思状，半晌才道：“这样吧，先观察一段时间，找点小毛病扣他的工资，扣到他自己辞职为止，这样不至于激化矛盾。”\n白队长赞道：“还是经理水平高。”\n……\n小区门口，王志军惋惜的说：“唉，以后抽不上你的烟了，这下高经理肯定得辞退你。”\n刘子光一瞪眼：“敢！”\n王志军凑过来问道：“哥们，你真是山上下来的？”\n刘子光装出一副人畜无害的表情道：“你看我象么？”\n“象！太象了，那个做派就和一般人不一样……”\n“好了，这会没啥事，我出去转转，你帮我顶着。”刘子光把剩下的半包中华扔给王志军，摸出马六的遥控钥匙按了一下，远处的轿车清脆的响应了一声，他连保安制服也不换，就这样开着车扬长而去，只留下王志军啧啧赞叹：“妈的，经理才开伊兰特，他开马六，这哥们真牛。”\n离开家乡太久，江北市有了翻天覆地的变化，高楼大厦拔地而起，马路宽阔干净，广场喷泉叮咚，绿树掩映，八年前刘子光推着小车卖烤肠的地段已经变成繁华的商业街，红男绿女匆匆而过，真让刘子光有恍如隔世之感。\n一晃八年过去了，自己依然是身无长物，如何让父母安度晚年，如何出人头地改善生活条件，成了目前最大的难题。\n千丝万缕，无从下手，焦躁不宁的刘子光驾着汽车在大街上左冲右突，路边一辆警用摩托发现了这辆严重超速并且违反交规的汽车，便拉响警笛追了上来。\n有警察追赶，刘子光反而更加兴奋起来，油门离合刹车档位不断变化，在车流中如同游鱼一般向前飞驰。\n不知不觉就甩掉了警用摩托，眼前是一条开阔的高速大路，刘子光蓦然猛醒，一踩刹车，汽车横在路上。\n与其挖空心思想怎么发达，不如从最点滴的事情做起，古语说得好，一屋不扫何以扫天下，如果连个保安都当不好，还谈什么出人头地！\n只有先融入这个社会，才能找到适合自己的道路，才能一展所长，崭露头角。\n……\n打定主意之后，刘子光将方向盘一打，回志诚花园上班去了。\n来到小区门口，就见堵了一长串的汽车，其中几辆车还在不耐烦的按着喇叭，刘子光将车停在路边，走到大门口一看，一辆黑色本田飞度正车头向外盘踞在大门里，车门落锁，司机不知去向。\n要知道这可是小区的入口，只进不出的，这辆飞度横在门口，外面十几辆车都进不来乐，又是下班高峰期，眼瞅着车辆还在增加，可把王志军给急坏了。\n“刘哥，你可来了，坏事了。”看见刘子光回来，满头大汗的王志军颠颠跑来向他诉苦。\n“咋回事？”刘子光问道。\n“本田车逆行要出门，正好碰到有车进来，双方都是硬茬子，不愿意退，就顶起来了，我劝了半天也没用，本田车主干脆下车走了，这下可糟了，咱俩的奖金泡汤了。”\n刘子光奇道：“逆行出门本来就不对，还敢玩这套，反了他了！报警拖他的车。”\n王志军道：“打过电话了，人家交警说小区内的道路不归他们管。”\n刘子光道：“那你报告经理了么？”\n王志军苦着脸\n说：“刚不说了么，闹到经理那里，咱俩奖金就完了。”\n“这样啊……”刘子光托着腮帮想了想，此时外面汽车堵的更多了，鸣笛此起彼伏，进进出出的居民也为之侧目，刘子光眉头一展，顺手抢过王志军手里的对讲机，按下通话键道：“车库的伙计，出口的伙计过来支援一下。”\n不一会儿，两个保安小跑着过来，见到这幅景象也是大吃一惊，刘子光道：“伙计们帮个忙，把这辆车抬到一边去。”\n王志军道：“开本田车的小子好像不太好惹，临走放话说谁敢动他的车就让谁难看。”\n刘子光嗤之以鼻：“鸟毛，违反社会公德还有礼了，抬！出了事算我的。”\n既然刘子光大包大揽，众人便合力将本田车抬了起来，得亏日本车减配的厉害，四个人轻轻松松就抬到了一边，外面堵成长串的车流缓缓地开进小区，每个经过保安们身旁的司机都鸣笛致意，四个保安互相对视一眼，一种职业荣誉感油然而生。\n正在此时，一声怒骂响起：“他妈了的13的，谁动老子的车？”一个穿着吊裆裤的红发小青年从小区外面气冲冲的走过来，直奔这几个保安而来。\n刘子光眉毛倒竖，这就要上去揍人，却被王志军一把拉住。\n“刘哥，别冲动，打了业主铁定下岗，让我来。”\n说着王志军便陪着笑脸迎上去，先敬礼，后道歉，慢声细语的解释，可是那红毛却更加嚣张起来，卷起袖子，露出刺龙画虎的细胳膊，一把掀掉王志军的大檐帽，又拽住他的领子叫骂：“不就是个看门狗么，敢动老子的车，打不死你的13养的。”\n高大健壮的王志军就这样被这个一米六高的小青年推搡谩骂着，憨厚的脸上赔着笑，连围观居民都看不下去了，但鉴于红毛身上的纹身，大家只敢小声嘀咕着。\n此时刘子光反倒不出手了，抱着膀子看热闹，他倒想看看王志军能忍到什么时候。\n都是二十郎当岁的青年人，谁也不是天生就该被欺负的，果不其然，王志军的耐性到了临界点，一把推开红毛，指着地上的东西厉喝道：“给我捡起来！”\n瘦小的红毛被推了个踉跄，差点栽倒，恼羞成怒之下，不但不捡，还狠狠踩向地上的东西，王志军一看，眼睛都红了，抓起红毛的胳膊，一个漂亮的擒拿动作就将其放倒在地，红毛被摔懵了，半天才爬起来，一瘸一拐的跑了。\n周围一阵噼里啪啦的掌声响起，居民们见没热闹看了，便四散而去，刘子光嘴角浮起一丝笑意，问道：“志军，你真是喂猪的兵么？”\n王志军从地上捡起一枚小小的徽章，认真的擦去上面的灰尘，骄傲的戴在左胸上道：“可不是么，喂了三年老母猪。”\n夕阳映照下，一枚金色伞翼徽章在他心口熠熠生辉。";
        CacheManager.getInstance().saveChapterInfo("abc12312", "重生1", json);

        File file = CacheManager.getInstance().getChapterFile("abc12312", "重生1");


        return new BufferedReader(new FileReader(file));
    }

    @Override
    protected boolean hasChapterData(TxtChapter chapter) {
        return true;
    }

    // 装载上一章节的内容
    @Override
    boolean parsePrevChapter() {
        boolean isRight = super.parsePrevChapter();

        if (mStatus == STATUS_FINISH) {
            loadPrevChapter();
        } else if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }
        return isRight;
    }

    // 装载当前章内容。
    @Override
    boolean parseCurChapter() {
        boolean isRight = super.parseCurChapter();

        if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }
        return isRight;
    }

    // 装载下一章节的内容
    @Override
    boolean parseNextChapter() {
        boolean isRight = super.parseNextChapter();

        if (mStatus == STATUS_FINISH) {
            loadNextChapter();
        } else if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }

        return isRight;
    }

    @Override
    public void skipToChapter(int pos) {
        super.skipToChapter(pos);
        if (mStatus == STATUS_FINISH) {
            loadNextChapter();
        } else if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }
    }

    /**
     * 加载当前页的前面两个章节
     */
    private void loadPrevChapter() {
        if (mPageChangeListener != null) {
            int end = mCurChapterPos;
            int begin = end - 2;
            if (begin < 0) {
                begin = 0;
            }

            requestChapters(begin, end);
        }
    }

    /**
     * 加载前一页，当前页，后一页。
     */
    private void loadCurrentChapter() {
        if (mPageChangeListener != null) {
            int begin = mCurChapterPos;
            int end = mCurChapterPos;

            // 是否当前不是最后一章
            if (end < mChapterList.size()) {
                end = end + size;
                if (end >= mChapterList.size()) {
                    end = mChapterList.size() - 1;
                }
            }

            // 如果当前不是第一章
            if (begin != 0) {
                begin = begin - 1;
                if (begin < 0) {
                    begin = 0;
                }
            }

            requestChapters(begin, end);
        }
    }

    /**
     * 加载当前页的后两个章节
     */
    private void loadNextChapter() {
        if (mPageChangeListener != null) {

            // 提示加载后两章
            int begin = mCurChapterPos + 1;
            int end = begin + size;

            // 判断是否大于最后一章
            if (begin >= mChapterList.size()) {
                // 如果下一章超出目录了，就没有必要加载了
                return;
            }

            if (end > mChapterList.size()) {
                end = mChapterList.size() - 1;
            }

            requestChapters(begin, end);
        }
    }

    private void requestChapters(int start, int end) {
        // 检验输入值
        if (start < 0) {
            start = 0;
        }

        if (end >= mChapterList.size()) {
            end = mChapterList.size() - 1;
        }


        List<TxtChapter> chapters = new ArrayList<>();

        // 过滤，哪些数据已经加载了
        for (int i = start; i <= end; ++i) {
            TxtChapter txtChapter = mChapterList.get(i);
            if (!hasChapterData(txtChapter)) {
                chapters.add(txtChapter);
            }
        }

        if (!chapters.isEmpty()) {
            mPageChangeListener.requestChapters(chapters);
        }
    }

    @Override
    public void saveRecord() {
        super.saveRecord();
//        if (mCollBook != null && isChapterListPrepare) {
//            //表示当前CollBook已经阅读
//           /* mCollBook.setIsUpdate(false);
//            mCollBook.setLastRead(StringUtils.
//                    dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
//            //直接更新
//            BookRepository.getInstance()
//                    .saveCollBook(mCollBook);*/
//
//           mCollBook.setLatestReadTimestamp(new Date());
//            DBManager.INSTANCE.saveBookcase(mCollBook);
//        }
    }
}


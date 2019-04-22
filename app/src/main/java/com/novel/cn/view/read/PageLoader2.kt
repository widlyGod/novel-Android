package com.novel.cn.view.read

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import com.jess.arms.utils.LogUtils
import com.novel.cn.ext.dp2px
import java.io.*
import java.util.ArrayList

class PageLoader2(private val pageView: PageView,
                  private val dispalyWidth: Int,
                  private val displayHeight: Int
                ) {

    private val mContext: Context;

    //书籍绘制区域的宽高
    private var mVisibleWidth: Int = 0
    private var mVisibleHeight: Int = 0

    private var marginLeft: Int = 0
    private var marginTop: Int = 0
    private var marginRight: Int = 0
    private var marginBottom: Int = 0

    //字体的颜色
    private var mTextColor: Int = 0
    //标题的大小
    private var mTitleSize: Int = 0
    //字体的大小
    private var mTextSize: Int = 0
    //行间距
    private var mTextInterval: Int = 0
    //标题的行间距
    private var mTitleInterval: Int = 0
    //段落距离(基于行间距的额外距离)
    private var mTextPara: Int = 0
    private var mTitlePara: Int = 0


    private var mStatus = Status.STATUS_FINISH

    private val mSmallTitlePaint = Paint()
    private val mTipPaint = TextPaint()

    private val mTextPaint = TextPaint()

    private val mTitlePaint = TextPaint()

    private var mCurPage = TxtPage()

    // 当前章节的页面列表
    private var mCurPageList: MutableList<TxtPage>? = null

    init {
        mContext = pageView.context

        marginTop = mContext.dp2px(23)
        marginLeft = mContext.dp2px(18)
        marginRight = marginLeft
        // 获取内容显示位置的大小
        mVisibleWidth = dispalyWidth - marginLeft - marginRight
        mVisibleHeight = displayHeight - marginTop - marginBottom

        setupTextSize(pageView.dp2px(15))

        initPaint()
    }

    private fun initPaint() {
        mSmallTitlePaint.apply {
            isAntiAlias = true
            textSize = pageView.dp2px(14).toFloat()
            textAlign = Paint.Align.LEFT
            isSubpixelText = true
        }
        mTipPaint.apply {
            isAntiAlias = true
            textSize = pageView.dp2px(25).toFloat()
        }
        mTextPaint.apply {
            isAntiAlias = true
            textSize = pageView.dp2px(14).toFloat()
        }
        mTitlePaint.apply {
            isAntiAlias = true
            textSize = pageView.dp2px(14).toFloat()
        }
    }

    private fun setupTextSize(textSize: Int) {
        mTextSize = textSize
        mTitleSize = textSize + pageView.dp2px(4)
        // 行间距(大小为字体的一半)
        mTextInterval = mTextSize / 2
        mTitleInterval = mTitleSize / 2
        // 段落间距(大小为字体的高度)
        mTextPara = mTextSize
        mTitlePara = mTitleSize
    }


    fun loadPage() {
        //生成的页面
        val pages = ArrayList<TxtPage>()
        val book2 = "1-14.王者归来\n保安队白长找到高经理把情况一说，高经理也犯愁，说：“他刚来第一天就辞退，恐怕不太好吧，再说这些刑满释放人员都不是好惹的，万一报复咱们怎么办？”\n白队长说：“可是让他在咱们这上班，迟早闹出乱子来，你是没看见他和胡警官说话那个态度，简直……”他愤愤不平的一拍桌子，好像刘子光欺负了他家女性亲属一样。\n高经理低头做沉思状，半晌才道：“这样吧，先观察一段时间，找点小毛病扣他的工资，扣到他自己辞职为止，这样不至于激化矛盾。”\n白队长赞道：“还是经理水平高。”\n……\n小区门口，王志军惋惜的说：“唉，以后抽不上你的烟了，这下高经理肯定得辞退你。”\n刘子光一瞪眼：“敢！”\n王志军凑过来问道：“哥们，你真是山上下来的？”\n刘子光装出一副人畜无害的表情道：“你看我象么？”\n“象！太象了，那个做派就和一般人不一样……”\n“好了，这会没啥事，我出去转转，你帮我顶着。”刘子光把剩下的半包中华扔给王志军，摸出马六的遥控钥匙按了一下，远处的轿车清脆的响应了一声，他连保安制服也不换，就这样开着车扬长而去，只留下王志军啧啧赞叹：“妈的，经理才开伊兰特，他开马六，这哥们真牛。”\n离开家乡太久，江北市有了翻天覆地的变化，高楼大厦拔地而起，马路宽阔干净，广场喷泉叮咚，绿树掩映，八年前刘子光推着小车卖烤肠的地段已经变成繁华的商业街，红男绿女匆匆而过，真让刘子光有恍如隔世之感。\n一晃八年过去了，自己依然是身无长物，如何让父母安度晚年，如何出人头地改善生活条件，成了目前最大的难题。\n千丝万缕，无从下手，焦躁不宁的刘子光驾着汽车在大街上左冲右突，路边一辆警用摩托发现了这辆严重超速并且违反交规的汽车，便拉响警笛追了上来。\n有警察追赶，刘子光反而更加兴奋起来，油门离合刹车档位不断变化，在车流中如同游鱼一般向前飞驰。\n不知不觉就甩掉了警用摩托，眼前是一条开阔的高速大路，刘子光蓦然猛醒，一踩刹车，汽车横在路上。\n与其挖空心思想怎么发达，不如从最点滴的事情做起，古语说得好，一屋不扫何以扫天下，如果连个保安都当不好，还谈什么出人头地！\n只有先融入这个社会，才能找到适合自己的道路，才能一展所长，崭露头角。\n……\n打定主意之后，刘子光将方向盘一打，回志诚花园上班去了。\n来到小区门口，就见堵了一长串的汽车，其中几辆车还在不耐烦的按着喇叭，刘子光将车停在路边，走到大门口一看，一辆黑色本田飞度正车头向外盘踞在大门里，车门落锁，司机不知去向。\n要知道这可是小区的入口，只进不出的，这辆飞度横在门口，外面十几辆车都进不来乐，又是下班高峰期，眼瞅着车辆还在增加，可把王志军给急坏了。\n“刘哥，你可来了，坏事了。”看见刘子光回来，满头大汗的王志军颠颠跑来向他诉苦。\n“咋回事？”刘子光问道。\n“本田车逆行要出门，正好碰到有车进来，双方都是硬茬子，不愿意退，就顶起来了，我劝了半天也没用，本田车主干脆下车走了，这下可糟了，咱俩的奖金泡汤了。”\n刘子光奇道：“逆行出门本来就不对，还敢玩这套，反了他了！报警拖他的车。”\n王志军道：“打过电话了，人家交警说小区内的道路不归他们管。”\n刘子光道：“那你报告经理了么？”\n王志军苦着脸\n说：“刚不说了么，闹到经理那里，咱俩奖金就完了。”\n“这样啊……”刘子光托着腮帮想了想，此时外面汽车堵的更多了，鸣笛此起彼伏，进进出出的居民也为之侧目，刘子光眉头一展，顺手抢过王志军手里的对讲机，按下通话键道：“车库的伙计，出口的伙计过来支援一下。”\n不一会儿，两个保安小跑着过来，见到这幅景象也是大吃一惊，刘子光道：“伙计们帮个忙，把这辆车抬到一边去。”\n王志军道：“开本田车的小子好像不太好惹，临走放话说谁敢动他的车就让谁难看。”\n刘子光嗤之以鼻：“鸟毛，违反社会公德还有礼了，抬！出了事算我的。”\n既然刘子光大包大揽，众人便合力将本田车抬了起来，得亏日本车减配的厉害，四个人轻轻松松就抬到了一边，外面堵成长串的车流缓缓地开进小区，每个经过保安们身旁的司机都鸣笛致意，四个保安互相对视一眼，一种职业荣誉感油然而生。\n正在此时，一声怒骂响起：“他妈了的13的，谁动老子的车？”一个穿着吊裆裤的红发小青年从小区外面气冲冲的走过来，直奔这几个保安而来。\n刘子光眉毛倒竖，这就要上去揍人，却被王志军一把拉住。\n“刘哥，别冲动，打了业主铁定下岗，让我来。”\n说着王志军便陪着笑脸迎上去，先敬礼，后道歉，慢声细语的解释，可是那红毛却更加嚣张起来，卷起袖子，露出刺龙画虎的细胳膊，一把掀掉王志军的大檐帽，又拽住他的领子叫骂：“不就是个看门狗么，敢动老子的车，打不死你的13养的。”\n高大健壮的王志军就这样被这个一米六高的小青年推搡谩骂着，憨厚的脸上赔着笑，连围观居民都看不下去了，但鉴于红毛身上的纹身，大家只敢小声嘀咕着。\n此时刘子光反倒不出手了，抱着膀子看热闹，他倒想看看王志军能忍到什么时候。\n都是二十郎当岁的青年人，谁也不是天生就该被欺负的，果不其然，王志军的耐性到了临界点，一把推开红毛，指着地上的东西厉喝道：“给我捡起来！”\n瘦小的红毛被推了个踉跄，差点栽倒，恼羞成怒之下，不但不捡，还狠狠踩向地上的东西，王志军一看，眼睛都红了，抓起红毛的胳膊，一个漂亮的擒拿动作就将其放倒在地，红毛被摔懵了，半天才爬起来，一瘸一拐的跑了。\n周围一阵噼里啪啦的掌声响起，居民们见没热闹看了，便四散而去，刘子光嘴角浮起一丝笑意，问道：“志军，你真是喂猪的兵么？”\n王志军从地上捡起一枚小小的徽章，认真的擦去上面的灰尘，骄傲的戴在左胸上道：“可不是么，喂了三年老母猪。”\n夕阳映照下，一枚金色伞翼徽章在他心口熠熠生辉。"
        val book = "雨夜，电闪雷鸣！\n路上稀少的行人与车辆匆匆而过\n如瓢泼一样的大雨，如末日那般的雷电肆虐着整个江城\n杂货铺里的一位老人眯着眼睛，抽了一口旱烟喃喃自语：“百年难遇的大雨与雷电今天全部聚在了一起到底预示着什么？”\n距离杂货铺附近不远，在一个最不起眼的胡同里\n当最后一缕雷电劈向那之后，似乎整个世界都安静了下来\n骤雨停息，雷电散去\n昏暗无光的胡同里，一个昏迷的少年犹如死尸躺在地上\n过了许久，他手指微微颤抖了一下，随后便猛然睁开了双眼\n少年漆黑的眼眸之中似乎有雷电闪烁，摄人心魄\n他艰难起身，打量了四周，一股陌生之感涌上心头：“这似乎并不是玄天大陆？”\n少年嗓音嘶哑自语疑问，感觉浑身剧痛\n他又疑惑看了看自己稍显稚嫩的双手与水洼里倒影出来的那年轻的容貌\n愣了许久之后，杨天这才不确定再次自语：“难道我成功了？”\n他仔细打量着四周，熟悉之感在脑海中闪过\n随后，直接放声狂笑热泪涌出：“哈哈哈，我成功了，本仙帝耗尽天命放弃所有逆转天地法则，终究是回来了”\n杨天笑着，笑的很悲凉逆转天地法则的代价极大他可是放弃了与天地同寿的机会\n但他却一点也不后悔\n逆转天地法则归来，是因这里有他要守护的人\n“父亲，母亲，姐姐如今你们可好？”\n杨天想到了自己的父母与姐姐，浑身颤抖一下，眼眶也通红起来\n亲情面前，他不是破天仙帝，而是一个普通人\n在玄天大陆活了三百年，上一世的刻骨的仇恨，他依旧是没敢忘记半分\n上一世，他原本有着幸福美满的家庭，但是却全被那些所谓血脉相连的亲人给破坏的支离破碎\n杨天的父母两家是京城的名门望族杨家和李家，两家本是门当户对的，但却由于上一代的恩怨成了仇敌\n杨天父母的结合自然不被允许，为此，杨家李家不敢撕破脸皮针锋相对，就处处折磨他们一家\n冷嘲热讽，无尽谩骂，鄙视，轻蔑！\n他永远不会忘记亲戚那丑陋的嘴脸\n在杨天一家陷入绝境的时候，非但不伸出援手，反而落井下石，把他们推进了更残酷的深渊\n他至今犹记得，上一世自己的母亲就是被他们这些人生生逼死的！\n“大舅，爷爷，大伯，三叔，亲戚？”\n杨天咬着牙冷笑：“京城杨家李家，豪门是吗？优越感十足是吗？权势滔天是吗？家财万贯是吗？”\n他眼眸泛着寒光自语：“上一世逼死了我父母还不算，还害死了我的姐姐认为我们一家是蝼蚁，妄想再次回到家族高攀你们？认为我们家妄想分割你们的财产？”\n杨天的拳头紧握，周身煞气密布，唇角咬破在丝丝溢血吼着：“你们以为我们家稀罕你们这群王八蛋的权势臭钱？”\n他额头青筋暴露咆哮：“你们根本不知道施加在我们家的痛苦到底有多深我父母姐姐全因为你们而死，三百年了，你们根本不知道我曾经历了什么，你们根本不知道我是怎么死里逃生苟且活下，为了逆转天地法则，我放弃了放弃了所有修为，放弃了所有仙帝地位，放弃了一切，为的就是归来找你们报仇”\n上一世他懦弱无能，没有帮到家里，反而因为自己的过错，拖累家里陷入更深的绝境\n上一世他母亲被李家逼迫到了无路可走，一头撞死在李家之人门前也没有一个人可怜\n上一世他父亲一生好强，承受了莫大的侮辱与委屈，最后对生活彻底失望，心灰意冷之后，那雄伟如泰山的身影轰然倒塌\n上一世他误会了自己的姐姐半辈子，知道了真相之后他姐姐已经离开了人世，这成了他一辈子的遗憾\n上一世他无能，懦夫，胆小怕事，逆来顺受\n这一世，他为仙\n三百年疯狂修炼，修成破天仙帝，逆转天地法则归来\n这一世，一切回到了起点，如今的他不再普通，而是拥有一个货真价实的仙帝灵魂\n那些轻辱他们一家的人，他一个都不会放过\n此刻，杨天冰冷如刀的眸子闪烁着寒芒：“京城杨家，李家！我回来了，等着我！终有一天我会杀向京城，我要让你们知道什么才是权贵豪门，我要让你们这群王八蛋知道到底是谁高攀不起谁？我要让你们付出最为惨痛的代价”\n过了许久，他才平静了下来，检查了一下自己的身体发现了很多淤青\n尘封的记忆再次被打开\n杨天想起了现在自己应该在高三学习，自己这身淤青是被学校里的一个富二代给打伤的\n如果他没记错，那个富二代的名字应该叫做周立，至于为什么打伤他，则是因为对方看中了他的女朋友\n威逼让杨天分手无果，最后则让几个手下，把他堵在这里死命的殴打了一顿\n如果按照上一世的发展，不出意外的话杨天那个所谓的女友沦陷在周立的金钱攻势下，成了对方的女朋友\n然后，他的女朋友会在明天跟他提出分手，把他无情抛弃\n杨天自此失魂落魄，原本他父亲把自己所有的期望全部寄托在了他的身上，希望他会扬眉吐气，让京城杨家李家看看他们当初是瞎了眼的\n但是，杨天却辜负了他的父亲最后的希望\n被女朋友无情抛弃之后，他的成绩一落千丈，高考失利，彻底成了废物\n最后一丝希望破灭的父亲心灰意冷\n杨天咬紧了牙关，指甲都深陷在肉里\n想想上一世他父亲临死前的眼神，他心痛如刀割\n他不仅恨别人，也恨自己无能，如今重活一世他绝不能让悲剧重演\n而那两个狗男女，也是最不可饶恕的\n“周立，郑丽丽，如今的我再次出现你们面前，你们会用怎样的方式来欢迎我呢？”\n杨天的声音十分寒彻\n曾经自己被他们踩在脚下，被当成笑话蝼蚁\n如今再世为人，他定要将那两个狗男女踩在脚下，下地狱去忏悔\n想到了这里，他不由想到了那个最让他感到愧疚的女子\n杨雪，他的姐姐！\n曾一度看不起他，嘲弄他是个废物不断羞辱他，因为他们父母把所有希望寄托在了他身上，而他却辜负了一家人的期望\n杨天曾对自己姐姐恨之入骨，但最终明白了一切之后感觉天地都失去了色彩\n他姐姐用心良苦，是想用这种方式让他拿出男子气概却被他误会了半辈子\n在杨天的父亲对他不抱什么希望的时候，这个坏姐姐还是没有放弃他\n最后，她在临死前的一刻，告诉他，让他不要放弃，要让京城的李家杨家看看他们一家的骨气，证明给对方看，他们一家永远不会服输\n她本可以活着的\n杨雪查出了自己得了重病，要是选择治疗是可以活着的\n但是她却想为他们家争一点骨气\n杨雪把自己所有的积蓄给了他，让杨天去拼搏为了超越京城杨家李家她放弃了自己的所有治疗\n但是悲剧再次重演，杨天那孤注一掷的公司被杨家李家无情打压，血本无归\n失去了一切东山再起的可能，杨雪死了，临死安慰他尽力就好！\n最后，杨天知道了这些真相，哭到最后血泪都流了下来\n每次想到这，他心如刀绞\n杨天眼眶通红，自语：“姐姐，上一世我对不起你，这一世，我绝不让你再次失望！”\n已经在原地停留了许久，杨天也整理了前世今生的所有思绪\n“救命！”\n他正要离开时突然听到从远方传来了一声无助的尖叫\n现在的雨已停杨天，顺着声音的方向走去，发现了一个熟悉绝色的少女身影\n这少女杨天认识，是他在高中时期所有男生心目中的女神——王静宸\n拥有倾国倾城般的容颜，出众的学习成绩，优越的家世\n可以说女神之名无可挑剔\n只不过她现在看起来十分不妙，因为此刻她竟然被一位黑衣男子拖进了一处幽暗的巷子中"

        LogUtils.warnInfo("==============+" + book2)
        CacheManager.getInstance().saveChapterInfo("abc12312", "重生1", book2)

        val file = CacheManager.getInstance().getChapterFile("abc12312", "重生1")

        val reader = FileReader(file)
        val br = BufferedReader(reader)

        val lines = ArrayList<String>()
        var rHeight = mVisibleHeight
        var titleLinesCount = 0
        var showTitle = true


        var paragraph: String? = "重生"//默认展示标题
        do {
            paragraph = br.readLine()
            if (!showTitle && paragraph == null) {
                break
            }

            // 重置段落
            if (!showTitle) {
                paragraph = paragraph.replace("\\s".toRegex(), "")
                // 如果只有换行符，那么就不执行
                if (paragraph == "") continue
                paragraph = halfToFull("  $paragraph\n")
            } else {
                //设置 title 的顶部间距
                rHeight -= mTitlePara
            }

            var wordCount = 0
            var subStr: String? = null

            while (paragraph!!.length > 0) {
                //当前空间，是否容得下一行文字
                if (showTitle) {
                    rHeight -= mTitlePaint.getTextSize().toInt()
                } else {
                    rHeight -= mTextPaint.textSize.toInt()
                }
                // 一页已经填充满了，创建 TextPage
                if (rHeight <= 0) {
                    // 创建Page
                    val page = TxtPage()
                    page.position = pages.size
                    page.title = "接口说的"
                    page.lines = ArrayList(lines)
                    page.titleLines = titleLinesCount
                    pages.add(page)
                    // 重置Lines
                    lines.clear()
                    rHeight = mVisibleHeight
                    titleLinesCount = 0

                    continue
                }
                //测量一行占用的字节数
                if (showTitle) {
                    wordCount = mTitlePaint.breakText(paragraph,
                            true, mVisibleWidth.toFloat(), null)
                } else {
                    wordCount = mTextPaint.breakText(paragraph,
                            true, mVisibleWidth.toFloat(), null)
                }
                subStr = paragraph!!.substring(0, wordCount)
                if (subStr != "\n") {
                    //将一行字节，存储到lines中
                    lines.add(subStr)

                    //设置段落间距
                    if (showTitle) {
                        titleLinesCount += 1
                        rHeight -= mTitleInterval
                    } else {
                        rHeight -= mTextInterval
                    }
                }
                //裁剪
                paragraph = paragraph.substring(wordCount)
            }
//-----------------------------------------------------------------
            //增加段落的间距
            if (!showTitle && lines.size != 0) {
                rHeight = rHeight - mTextPara + mTextInterval
            }

            if (showTitle) {
                rHeight = rHeight - mTitlePara + mTitleInterval
                showTitle = false
            }
        } while (true)

        if (lines.size != 0) {
            //创建Page
            val page = TxtPage()
            page.position = pages.size
            page.title = "接口说的"
            page.lines = ArrayList(lines)
            page.titleLines = titleLinesCount
            pages.add(page)
            //重置Lines
            lines.clear()
        }

        mCurPageList = pages
    }

    fun drawBackground(bitmap: Bitmap) {

        val canvas = Canvas(bitmap)
        canvas.drawColor(ContextCompat.getColor(mContext, PageStyle.BG_2.bgColor))
        if (mStatus == Status.STATUS_FINISH) {
            val fontMetrics = mSmallTitlePaint.fontMetrics
            val fontHeight = fontMetrics.descent - fontMetrics.ascent
            val baseLine = marginTop / 2 + fontHeight / 2 + fontMetrics.bottom
            canvas.drawText("重生1", marginLeft.toFloat(), baseLine, mSmallTitlePaint)
        }
    }


    fun drawPage(bitmap: Bitmap) {
        val canvas = Canvas(bitmap)
        canvas.drawColor(ContextCompat.getColor(mContext, PageStyle.BG_2.bgColor))
        val tip = "正在加载中"
        if (mStatus != Status.STATUS_FINISH) {
            val fontMetrics = mTipPaint.fontMetrics
            val textHeight = fontMetrics.top - fontMetrics.bottom
            val textWidth = mTipPaint.measureText(tip)
            val pivotX = dispalyWidth / 2 - textWidth / 2
            val pivotY = (displayHeight - textHeight) / 2 + marginBottom - marginTop
            canvas.drawText(tip, pivotX, pivotY, mTipPaint)
        } else {
            var top = -mTextPaint.fontMetrics.top

            //设置总距离
            val interval = mTextInterval + mTextPaint.textSize.toInt()
            val para = mTextPara + mTextPaint.textSize.toInt()
            val titleInterval = mTitleInterval + mTitlePaint.textSize.toInt()
            val titlePara = mTitlePara + mTextPaint.textSize.toInt()
            var str: String? = null

            mCurPage = mCurPageList!!.get(0)
            //对标题进行绘制
            for (i in 0 until mCurPage.titleLines) {
                str = mCurPage.lines[i]

                //设置顶部间距
                if (i == 0) {
                    top += mTitlePara.toFloat()
                }

                //计算文字显示的起始点
                val start = (dispalyWidth - mTitlePaint.measureText(str)).toInt() / 2
                //进行绘制
                canvas.drawText(str, start.toFloat(), top, mTitlePaint)

                //设置尾部间距
                if (i == mCurPage.titleLines - 1) {
                    top += titlePara.toFloat()
                } else {
                    //行间距
                    top += titleInterval.toFloat()
                }
            }
            LogUtils.warnInfo("======================>>>${mCurPage.lines.size}")
            //对内容进行绘制
            for (i in mCurPage.titleLines until mCurPage.lines.size) {
                str = mCurPage.lines[i]

                canvas.drawText(str, marginLeft.toFloat(), top, mTextPaint)
                if (str.endsWith("\n")) {
                    top += para.toFloat()
                } else {
                    top += interval.toFloat()
                }
            }
        }


    }


    enum class Status() {
        STATUS_LOADING,
        STATUS_FINISH
    }


    fun halfToFull(input: String): String {
        val c = input.toCharArray()
        for (i in c.indices) {
            if (c[i].toInt() == 32)
            //半角空格
            {
                c[i] = 12288.toChar()
                continue
            }
            //根据实际情况，过滤不需要转换的符号
            //if (c[i] == 46) //半角点号，不转换
            // continue;

            if (c[i].toInt() in 33..126)
            //其他符号都转换为全角
                c[i] = (c[i].toInt() + 65248).toChar()
        }
        return String(c)
    }
}



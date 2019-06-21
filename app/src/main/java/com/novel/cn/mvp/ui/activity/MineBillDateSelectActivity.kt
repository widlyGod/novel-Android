package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import cn.qqtheme.framework.picker.DatePicker
import cn.qqtheme.framework.picker.DateTimePicker
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.R
import com.novel.cn.app.visible
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.mvp.presenter.NothingPresenter
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_mine_bill_date_select.*
import java.text.SimpleDateFormat
import java.util.*


class MineBillDateSelectActivity : BaseActivity<NothingPresenter>() {

    val datePicker by lazy { DatePicker(this, DateTimePicker.YEAR_MONTH_DAY) }
    val monthPicker by lazy { DatePicker(this, DateTimePicker.YEAR_MONTH) }

    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_mine_bill_date_select

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, cl_title)
    }

    private var isStartDate = true
    private var startDate = ""
    private var endDate = ""
    private var isDay = true
    private var isSelectStartDate = false
    private var isSelectEndDate = false
    private var isSelectMonth = false
    private var selectMonth = ""

    override fun initData(savedInstanceState: Bundle?) {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val form = SimpleDateFormat("yyyy-MM")
        val calendar = Calendar.getInstance()
        val today = calendar.time
        startDate = sdf.format(today)
        endDate = sdf.format(today)
        selectMonth = form.format(today)
        datePicker.setOffset(5)

        val date = sdf.parse(startDate)
        datePicker.setUseWeight(true)
        datePicker.setDividerColor(-0x242425)
        datePicker.setTextColor(-0x1000000, -0x666667)
        datePicker.setTextSize(14)
        datePicker.setLabel("", "", "")
        datePicker.setOnWheelListener(object : DatePicker.OnWheelListener {
            override fun onYearWheeled(index: Int, year: String) {
                if (isStartDate) {
                    tv_start_date.text = String.format("%s-%s-%s", year, datePicker.selectedMonth, datePicker.selectedDay)
                    startDate = String.format("%s-%s-%s", year, datePicker.selectedMonth, datePicker.selectedDay)
                } else {
                    tv_end_date.text = String.format("%s-%s-%s", year, datePicker.selectedMonth, datePicker.selectedDay)
                    endDate = String.format("%s-%s-%s", year, datePicker.selectedMonth, datePicker.selectedDay)
                }

            }

            override fun onMonthWheeled(index: Int, month: String) {
                if (isStartDate) {
                    tv_start_date.text = String.format("%s-%s-%s", datePicker.selectedYear, month, datePicker.selectedDay)
                    startDate = String.format("%s-%s-%s", datePicker.selectedYear, month, datePicker.selectedDay)
                } else {
                    tv_end_date.text = String.format("%s-%s-%s", datePicker.selectedYear, month, datePicker.selectedDay)
                    endDate = String.format("%s-%s-%s", datePicker.selectedYear, month, datePicker.selectedDay)
                }

            }

            override fun onDayWheeled(index: Int, day: String) {
                if (isStartDate) {
                    tv_start_date.text = String.format("%s-%s-%s", datePicker.selectedYear, datePicker.selectedMonth, day)
                    startDate = String.format("%s-%s-%s", datePicker.selectedYear, datePicker.selectedMonth, day)
                } else {
                    tv_end_date.text = String.format("%s-%s-%s", datePicker.selectedYear, datePicker.selectedMonth, day)
                    endDate = String.format("%s-%s-%s", datePicker.selectedYear, datePicker.selectedMonth, day)
                }
            }
        })

        monthPicker.setOffset(5)
        monthPicker.setRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
        monthPicker.setUseWeight(true)
        monthPicker.setDividerColor(-0x242425)
        monthPicker.setTextColor(-0x1000000, -0x666667)
        monthPicker.setTextSize(14)
        monthPicker.setLabel("", "", "")
        monthPicker.setOnWheelListener(object : DatePicker.OnWheelListener {
            override fun onYearWheeled(index: Int, year: String) {
                tv_select_month.text = String.format("%s-%s", year, monthPicker.selectedMonth)
                selectMonth = String.format("%s-%s", year, monthPicker.selectedMonth)
            }

            override fun onMonthWheeled(index: Int, month: String) {
                tv_select_month.text = String.format("%s-%s", monthPicker.selectedYear, month)
                selectMonth = String.format("%s-%s", monthPicker.selectedYear, month)
            }

            override fun onDayWheeled(index: Int, day: String) {
            }
        })
        changeDayOrMonth(isDay)
        ll_start_date.clicks().subscribe {
            isSelectStartDate = true
            tv_start_date.text = startDate
            wheelview_container.removeAllViews()
            var startCalendar = Calendar.getInstance()
            var nowCalendar = Calendar.getInstance()
            startCalendar.time = sdf.parse(endDate)
            nowCalendar.time = sdf.parse(startDate)
            datePicker.setRangeStart(calendar.get(Calendar.YEAR) - 10, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.setRangeEnd(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH))
            datePicker.setSelectedItem(nowCalendar.get(Calendar.YEAR), nowCalendar.get(Calendar.MONTH) + 1, nowCalendar.get(Calendar.DAY_OF_MONTH))
            wheelview_container.addView(datePicker.contentView)
            isStartDate = true
            tv_start_date.setTextColor(Color.parseColor("#1b94f2"))
            line_start_date.setBackgroundColor(Color.parseColor("#1b94f2"))
            tv_end_date.setTextColor(Color.parseColor("#999999"))
            line_end_date.setBackgroundColor(Color.parseColor("#dbdbdb"))

        }.bindToLifecycle(this)
        ll_end_date.clicks().subscribe {
            isSelectEndDate = true
            tv_end_date.text = endDate
            wheelview_container.removeAllViews()
            var endCalendar = Calendar.getInstance()
            var nowCalendar = Calendar.getInstance()
            endCalendar.time = sdf.parse(startDate)
            nowCalendar.time = sdf.parse(endDate)
            datePicker.setRangeStart(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH) + 1, endCalendar.get(Calendar.DAY_OF_MONTH))
            datePicker.setRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.setSelectedItem(nowCalendar.get(Calendar.YEAR), nowCalendar.get(Calendar.MONTH) + 1, nowCalendar.get(Calendar.DAY_OF_MONTH))
            wheelview_container.addView(datePicker.contentView)
            isStartDate = false
            tv_end_date.setTextColor(Color.parseColor("#1b94f2"))
            line_end_date.setBackgroundColor(Color.parseColor("#1b94f2"))
            tv_start_date.setTextColor(Color.parseColor("#999999"))
            line_start_date.setBackgroundColor(Color.parseColor("#dbdbdb"))
        }.bindToLifecycle(this)
        ll_select_month.clicks().subscribe {
            var nowCalendar = Calendar.getInstance()
            nowCalendar.time = form.parse(selectMonth)
            isSelectMonth = true
            monthPicker.setSelectedItem(nowCalendar.get(Calendar.YEAR), nowCalendar.get(Calendar.MONTH) + 1)
            wheelview_container.removeAllViews()
            wheelview_container.addView(monthPicker.contentView)
            tv_select_month.setTextColor(Color.parseColor("#1b94f2"))
            line_star_date_month.setBackgroundColor(Color.parseColor("#1b94f2"))
        }.bindToLifecycle(this)
        tv_date_change.clicks().subscribe {
            isDay = if (isDay) {
                changeDayOrMonth(!isDay)
                false
            } else {
                changeDayOrMonth(!isDay)
                true
            }
            wheelview_container.removeAllViews()
        }.bindToLifecycle(this)
        iv_bin.clicks().subscribe {
            startDate = sdf.format(today)
            endDate = sdf.format(today)
            selectMonth = form.format(today)
            isSelectStartDate = false
            isSelectEndDate = false
            isSelectMonth = false
            tv_select_month.text = "选择月份"
            tv_start_date.text = "开始日期"
            tv_end_date.text = "结束日期"
            tv_start_date.setTextColor(Color.parseColor("#999999"))
            line_start_date.setBackgroundColor(Color.parseColor("#dbdbdb"))
            tv_end_date.setTextColor(Color.parseColor("#999999"))
            line_end_date.setBackgroundColor(Color.parseColor("#dbdbdb"))
            tv_select_month.setTextColor(Color.parseColor("#999999"))
            line_star_date_month.setBackgroundColor(Color.parseColor("#dbdbdb"))
            wheelview_container.removeAllViews()
        }.bindToLifecycle(this)
        tv_done.clicks().subscribe {
            if (isDay) {
                if (isSelectStartDate && isSelectEndDate) {
                    var intent = Intent()
                    intent.putExtra("startDate", startDate)
                    intent.putExtra("endDate", endDate)
                    intent.putExtra("type", 1)
                    setResult(0x01, intent)
                    finish()
                } else {
                    finish()
                }
            } else {
                if (isSelectMonth) {
                    var intent = Intent()
                    intent.putExtra("startDate", selectMonth)
                    intent.putExtra("endDate", "")
                    intent.putExtra("type", 0)
                    setResult(0x01, intent)
                    finish()
                } else {
                    finish()
                }
            }
        }.bindToLifecycle(this)
    }

    private fun changeDayOrMonth(isDay: Boolean) {
        if (isDay) {
            tv_date_change.text = "按日选择"
            ll_select_month.visible(false)
            ll_date.visible(true)
        } else {
            tv_date_change.text = "按月选择"
            ll_select_month.visible(true)
            ll_date.visible(false)
        }

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}
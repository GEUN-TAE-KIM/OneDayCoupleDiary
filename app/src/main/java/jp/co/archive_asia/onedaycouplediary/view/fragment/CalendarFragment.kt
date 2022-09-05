package jp.co.archive_asia.onedaycouplediary.view.fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentCalendarBinding
import jp.co.archive_asia.onedaycouplediary.view.adapter.CalendarAdapter
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {

    lateinit var selectedDate: LocalDate

    override fun initView() {
        super.initView()

        //現在日
        selectedDate = LocalDate.now()

        //先月、クリックイベント
        binding.preBtn.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }

        //来月、クリックイベント
        binding.nextBtn.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }

        setMonthView()

    }

    private fun setMonthView() {
        // 年月 textview
        binding.monthYearText.text = monthYearFromDate(selectedDate)

        // 日、生まれる
        val dayList = dayInMonthArray(selectedDate)

        val adapter = CalendarAdapter(dayList)

        val manager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)

        binding.recyclerView.layoutManager = manager

        binding.recyclerView.adapter = adapter
    }

    private fun monthYearFromDate(date: LocalDate): String {

        val formatter = DateTimeFormatter.ofPattern("yyyy年 MM月")

        //受け取った日付を該当formatに変更
        return date.format(formatter)
    }

    private fun dayInMonthArray(date: LocalDate): ArrayList<LocalDate?> {

        val dayList = ArrayList<LocalDate?>()

        val yearMonth = YearMonth.from(date)

        // 月の最後の日付を持ってくる。
        val lastDay = yearMonth.lengthOfMonth()

        // 月の最前の日付を持ってくる。
        val firstDay = selectedDate.withDayOfMonth(1)

        // 初日の日を持ってくる。
        val dayOfweek = firstDay.dayOfWeek.value

        for (i in 1..41) {
            if (i <= dayOfweek || i > (lastDay + dayOfweek)) {
                dayList.add(null)
            } else {
                // LocalDate
                dayList.add(
                    LocalDate.of(
                        selectedDate.year,
                        selectedDate.month, i - dayOfweek
                    )
                )
            }
        }
        return dayList
    }

}
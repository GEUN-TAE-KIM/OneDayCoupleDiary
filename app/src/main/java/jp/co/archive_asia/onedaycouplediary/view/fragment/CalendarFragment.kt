package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentCalendarBinding

import jp.co.archive_asia.onedaycouplediary.view.adapter.CalendarAdapter
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    lateinit var selectedDate: LocalDate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)

        selectedDate = LocalDate.now()

        setMonthView()

        binding.preBtn.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }

        binding.nextBtn.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }

        return binding.root
    }

    private fun setMonthView() {
        binding.monthYearText.text = monthYearFromDate(selectedDate)

        val dayList = dayInMonthArray(selectedDate)

        val adapter = CalendarAdapter(dayList)

        var manager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)

        binding.recyclerView.layoutManager = manager

        binding.recyclerView.adapter = adapter
    }

    private fun monthYearFromDate(data: LocalDate): String {

        var formatter = DateTimeFormatter.ofPattern("yyyy MM月")

        return data.format(formatter)
    }

    private fun dayInMonthArray(data: LocalDate): ArrayList<String> {

        var dayList = ArrayList<String>()

        var yearMonth = YearMonth.from(data)

        var lastDay = yearMonth.lengthOfMonth()

        var firstDay = selectedDate.withDayOfMonth(1)

        var dayOfWeek = firstDay.dayOfWeek.value

        for (i in 1..41) {
            if (i <= dayOfWeek || i > (lastDay + dayOfWeek)) {
                dayList.add("")
            } else {
                dayList.add((i - dayOfWeek).toString())
            }
        }
        return dayList
    }

}
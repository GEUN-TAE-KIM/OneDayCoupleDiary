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
import jp.co.archive_asia.onedaycouplediary.model.CalendarData

import jp.co.archive_asia.onedaycouplediary.view.adapter.CalendarAdapter
import java.time.LocalDate

import java.time.format.DateTimeFormatter
import java.util.*


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

        var manager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)

        binding.recyclerView.layoutManager = manager

        val adapter = CalendarAdapter()

        binding.recyclerView.adapter = adapter

        val calendar = Calendar.getInstance()

        calendar.timeInMillis = System.currentTimeMillis()

        calendar.set(
            Calendar.DAY_OF_MONTH, 1
        )

        val tmpCal = calendar.timeInMillis
        calendar.timeInMillis = tmpCal

        val lastDay = calendar.getActualMaximum(Calendar.DATE)

        val first = selectedDate.withDayOfMonth(1)

        val dayOfWeek = first.dayOfWeek.value

        val list = MutableList(dayOfWeek, init = { CalendarData() })

        for (i in 1..lastDay) {
            list.add(
                CalendarData(dayOfWeek, i)
            )
        }
        adapter.submitList(list)
    }

    private fun monthYearFromDate(data: LocalDate): String {

        var formatter = DateTimeFormatter.ofPattern("yyyy年 MM月")

        return data.format(formatter)
    }
}
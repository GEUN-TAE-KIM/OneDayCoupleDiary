package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentCalendarBinding
import java.time.LocalDate
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
    }

    private fun monthYearFromDate(data: LocalDate): String {

        var formatter = DateTimeFormatter.ofPattern("yyyy MM月")

        return data.format(formatter)
    }

}
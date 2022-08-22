package jp.co.archive_asia.onedaycouplediary.view.fragment

import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentCalendarBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {

    lateinit var selectedDate: LocalDate

    override fun initView() {
        super.initView()

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
    }

    private fun setMonthView() {
        binding.monthYearText.text = monthYearFromDate(selectedDate)
    }

    private fun monthYearFromDate(data: LocalDate): String {

        var formatter = DateTimeFormatter.ofPattern("yyyy MM月")

        return data.format(formatter)
    }

}
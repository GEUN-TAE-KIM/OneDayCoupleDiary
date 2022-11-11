package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.graphics.Color
import android.graphics.Paint
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentCalendarBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.adapter.CalendarAdapter
import jp.co.archive_asia.onedaycouplediary.view.adapter.WriteAdapter
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils.dayInMonthArray
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils.monthYearFromDate
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils.selectedDate
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar),
    CalendarAdapter.OnItemListener {

    private val adapter: WriteAdapter by lazy { WriteAdapter(calendarViewModel) }
    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory(
            requireActivity()
        )
    }

    private var dayList = dayInMonthArray(selectedDate)
    private val adapters = CalendarAdapter(dayList, this)

    var date = selectedDate.toString()

    override fun initView() {
        super.initView()

        //先月、クリックイベント
        binding.preBtn.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            val dayList = dayInMonthArray(selectedDate)
            adapters.update(dayList)
            binding.monthYearText.text = monthYearFromDate(selectedDate)
        }

        //来月、クリックイベント
        binding.nextBtn.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            val dayList = dayInMonthArray(selectedDate)
            adapters.update(dayList)
            binding.monthYearText.text = monthYearFromDate(selectedDate)
        }

        binding.dialogButton.setOnClickListener {
            findNavController().navigate(R.id.action_calendarFragment_to_writeFragment)

        }

        setMonthView()

    }

    override fun onItemClick(position: Int, dayText: LocalDate?) {

        if (dayText != null) {
            selectedDate = dayText

            val dayList = dayInMonthArray(selectedDate)
            adapters.update(dayList)

        }

        binding.textDate.text =
            selectedDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

        setEvent(selectedDate.toString())
    }

    private fun setMonthView() {

        // 年月 textview
        binding.monthYearText.text = monthYearFromDate(selectedDate)

        binding.recyclerView.layoutManager = GridLayoutManager(context, 7)

        binding.recyclerView.adapter = adapters

        setEvent(selectedDate.toString())

    }

    private fun setEvent(date: String) {

        binding.textRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.textRecyclerView.adapter = adapter

        getEvent(date)

    }

    private fun getEvent(date: String) {

        calendarViewModel.readDateData(date)

        calendarViewModel.currentData.observe(viewLifecycleOwner) { date ->
            adapter.setData(date)
        }
    }
}
package jp.co.archive_asia.onedaycouplediary.view.fragment

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
import jp.co.archive_asia.onedaycouplediary.view.util.addFinishAppBackButtonCallback
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar),
    CalendarAdapter.OnItemListener {

    private val adapter: WriteAdapter by lazy { WriteAdapter(calendarViewModel) }
    private val calendarViewModel: CalendarViewModel by viewModels()
    private var dayList = dayInMonthArray(selectedDate)
    private val adapters = CalendarAdapter(dayList, onItemListener = this)

    var date = selectedDate.toString()

    override fun initView() {
        super.initView()

        binding.textRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.textRecyclerView.adapter = adapter

        //先月、クリックイベント
        binding.preBtn.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            calendarViewModel.selectDateData(selectedDate)
            binding.monthYearText.text = monthYearFromDate(selectedDate)
        }

        //来月、クリックイベント
        binding.nextBtn.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            calendarViewModel.selectDateData(selectedDate)
            binding.monthYearText.text = monthYearFromDate(selectedDate)
        }

        binding.dialogButton.setOnClickListener {
            findNavController().navigate(R.id.action_calendarFragment_to_writeFragment)

        }

        binding.toolbarSetting.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_calendarFragment_to_settingFragment)
        }

        binding.textDate.text =
            selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        setMonthView()


        addFinishAppBackButtonCallback()

    }

    override fun initObservers() {
        calendarViewModel.currentData.observe(viewLifecycleOwner) { monthlyDiary ->
            val dayList = dayInMonthArray(selectedDate)
            adapters.update(dayList, monthlyDiary)
            adapter.setData(monthlyDiary)
        }
    }

    override fun onItemClick(position: Int, dayText: LocalDate?) {

        if (dayText != null) {
            selectedDate = dayText
            adapters.changeSelectedCell()

        }

        binding.textDate.text =
            selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        getEvent(selectedDate.toString())
    }

    private fun setMonthView() {
        // 年月 textview
        binding.monthYearText.text = monthYearFromDate(selectedDate)

        binding.recyclerView.layoutManager = GridLayoutManager(context, 7)

        binding.recyclerView.adapter = adapters
    }

    private fun getEvent(date: String) {
        adapter.update(date)

    }
}
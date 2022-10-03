package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentCalendarBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.adapter.CalendarAdapter
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.view.adapter.WriteAdapter
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils.dayInMonthArray
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils.monthYearFromDate
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils.selectedDate
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModelFactory

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar),
    CalendarAdapter.OnItemListener {

    private val adapter: WriteAdapter by lazy { WriteAdapter(calendarViewModel) }
    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory(
            requireActivity()
        )
    }

    private val dayList = dayInMonthArray(selectedDate)
    private val adapters = CalendarAdapter(dayList, this)

    var year: Int = selectedDate.year
    var month: Int = selectedDate.monthValue
    var day: Int = selectedDate.dayOfMonth

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

        binding.textRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.textRecyclerView.adapter = adapter

        /*calendarViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })*/

        binding.recyclerView.setOnClickListener {
            calendarViewModel.readDateData(year, month, day)
        }


        calendarViewModel.getAllData.observe(viewLifecycleOwner) {
            calendarViewModel.readDateData(year, month, day)
        }

        calendarViewModel.currentData.observe(viewLifecycleOwner) {
            adapter.setData(it)
            Log.d("select", "initView: $it")
        }

        setMonthView()

    }

    private fun setMonthView() {

        // 年月 textview
        binding.monthYearText.text = monthYearFromDate(selectedDate)

        binding.recyclerView.layoutManager = GridLayoutManager(context, 7)

        binding.recyclerView.adapter = adapters

    }

    override fun onItemClick(position: Int, dayText: LocalDate?) {
        if (dayText != null) {
            selectedDate = dayText

            val dayList = dayInMonthArray(selectedDate)
            adapters.update(dayList)

        }
    }

}
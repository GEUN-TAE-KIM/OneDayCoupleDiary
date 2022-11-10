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
        colorSelect()

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

        // 데이터가 있을때 색상을 표시하는 것 음
        // 레이아웃으로 30까지의 레이아웃을 만들고 데이터가 채워질때 레이아웃을 끌어와서 만든것을 표시하게 하는 것
        // 어떻게 끌어와서 표시?
        //
        // 아니면 캘린더 어댑터에 체크박스 표시되는 것 이거는 어댑터를 합쳐야 겠지?
        // 근데 어댑터를 합치는데 내가 쓰고 싶은 것만 쓸수가 있나? write어댑터에 뷰모델의 저장되어 있는 데이터를 끌어와서
        // 캘린더 어댑터에 뷰홀더에 색상 표시하고 여기 프래그먼트에 데이터를 나타내기

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

    private fun colorSelect() {

        val isSelected = binding.recyclerView.layoutManager
        val mPointPaint = Paint()
        if (isSelected == null) {
            mPointPaint.color = Color.BLACK
        } else {
            mPointPaint.color = Color.BLUE
        }
    }

}
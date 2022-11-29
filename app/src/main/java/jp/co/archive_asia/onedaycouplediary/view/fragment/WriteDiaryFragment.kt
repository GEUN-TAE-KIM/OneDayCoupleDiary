package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.MainActivity
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentWriteDiaryBinding
import jp.co.archive_asia.onedaycouplediary.model.ColorSpinner
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.adapter.SpinnerAdapter
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.view.util.DiaryUtils
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class WriteDiaryFragment : BaseFragment<FragmentWriteDiaryBinding>(R.layout.fragment_write_diary) {

    private lateinit var spinnerAdapterColor: SpinnerAdapter
    private val listOfYear = ArrayList<ColorSpinner>()

    lateinit var mainActivity: MainActivity

    private val diaryViewModel: DiaryViewModel by viewModels()

    override fun initView() {

        super.initView()

        binding.writeBtn.setOnClickListener {
            addDataWrite()
        }

        binding.textDate.text =
            CalendarUtils.selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

       //binding.selectColor.onItemSelectedListener = diaryViewModel.listener
        mainActivity = context as MainActivity
        setupSpinnerColor()

    }

    private fun setupSpinnerColor() {
        val colors = resources.getStringArray(R.array.color_select)

        val color = ColorSpinner(R.drawable.ic_baseline_fiber_manual_record_24, colors[0])
        listOfYear.add(color)

        val color1 = ColorSpinner(R.drawable.ic_baseline_fiber_manual_record_24_1, colors[1])
        listOfYear.add(color1)

        val color2 = ColorSpinner(R.drawable.ic_baseline_fiber_manual_record_24_2, colors[2])
        listOfYear.add(color2)

        val color3 = ColorSpinner(R.drawable.ic_baseline_fiber_manual_record_24_3, colors[3])
        listOfYear.add(color3)

        val color4 = ColorSpinner(R.drawable.ic_baseline_fiber_manual_record_24_4, colors[4])
        listOfYear.add(color4)


        spinnerAdapterColor = SpinnerAdapter(mainActivity, R.layout.item_spinner, listOfYear)
        binding.selectColor.adapter = spinnerAdapterColor

    }

    private fun addDataWrite() {

        val id = ""
        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()
        var dateString = CalendarFragment().date
        val selectedItem = binding.selectColor.selectedItem as ColorSpinner

        val validation = DiaryUtils.verifyData(title, content)

        if (validation) {

            // String -> Date -> Long
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString).time
            val newData = Diary(id, title, content, date, DiaryUtils.parsePriority(selectedItem.color_name))

            diaryViewModel.addData(newData)

            Toast.makeText(activity, "add", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()

        } else {
            Toast.makeText(activity, "add null", Toast.LENGTH_SHORT).show()
        }

    }

}
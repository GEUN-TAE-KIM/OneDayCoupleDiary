package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentWriteDiaryBinding
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.view.util.DiaryUtils
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModelFactory
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class WriteDiaryFragment : BaseFragment<FragmentWriteDiaryBinding>(R.layout.fragment_write_diary) {

    private val diaryViewModel: DiaryViewModel by viewModels {
        DiaryViewModelFactory(
            requireActivity()
        )
    }

    override fun initView() {

        super.initView()

        binding.writeBtn.setOnClickListener {
            addDataWrite()
        }

        binding.textDate.text =
            CalendarUtils.selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    }

    private fun addDataWrite() {

        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()
        var dateString = CalendarFragment().date

        val validation = DiaryUtils.verifyData(title, content)

        if (validation) {

            // String -> Date -> Long
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString).time
            val newData = Diary(0, title, content, date)

            diaryViewModel.addData(newData)

            Toast.makeText(activity, "add", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()

        } else {
            Toast.makeText(activity, "add null", Toast.LENGTH_SHORT).show()
        }

    }

}
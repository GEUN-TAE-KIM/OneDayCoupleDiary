package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentWriteBinding
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModelFactory
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModelFactory
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class DiaryFragment : BaseFragment<FragmentWriteBinding>(R.layout.fragment_write) {

    private val diaryViewModel: DiaryViewModel by viewModels {
        DiaryViewModelFactory(
            requireActivity()
        )
    }
    var year = CalendarFragment().year
    var month = CalendarFragment().month
    var day = CalendarFragment().day

    override fun initView() {

        super.initView()

        binding.writeBtn.setOnClickListener {
            addDataWrite()
        }

        binding.textDate.text =
            CalendarUtils.selectedDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

    }


    private fun addDataWrite() {
        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()

        val newData = Write(0, title, content, year, month, day)
        diaryViewModel.addData(newData)
        Log.d("add",newData.toString())
        Toast.makeText(activity, "add", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()


    }

}
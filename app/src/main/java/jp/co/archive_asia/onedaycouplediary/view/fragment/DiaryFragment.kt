package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentWriteBinding
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModelFactory
import java.time.format.DateTimeFormatter

class DiaryFragment : BaseFragment<FragmentWriteBinding>(R.layout.fragment_write) {

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
            CalendarUtils.selectedDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

    }

    private fun addDataWrite() {

        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()
        var date = CalendarFragment().date

        val newData = Write(0, title, content, date)
        diaryViewModel.addData(newData)
        Log.d("add", newData.toString())
        Toast.makeText(activity, "add", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()

    }

}
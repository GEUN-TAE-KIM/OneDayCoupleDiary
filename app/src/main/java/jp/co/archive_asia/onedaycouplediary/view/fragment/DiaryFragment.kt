package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentWriteBinding
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModelFactory

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

    }

    private fun addDataWrite() {
        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()

        val newData = Write(0, title, content)
        diaryViewModel.addData(newData)
        Toast.makeText(activity, "add", Toast.LENGTH_SHORT).show()
        Log.d("add",newData.toString())
        findNavController().navigate(R.id.action_writeFragment_to_calendarFragment)
    }

}
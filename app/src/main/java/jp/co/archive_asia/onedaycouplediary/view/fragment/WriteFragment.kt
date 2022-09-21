package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentWriteBinding
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.ViewModelFactory

class WriteFragment : BaseFragment<FragmentWriteBinding>(R.layout.fragment_write) {

    private val calendarViewModel: CalendarViewModel by viewModels {
        ViewModelFactory(
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
        calendarViewModel.addData(newData)
        Toast.makeText(activity, "add", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_writeFragment_to_calendarFragment)
    }

}
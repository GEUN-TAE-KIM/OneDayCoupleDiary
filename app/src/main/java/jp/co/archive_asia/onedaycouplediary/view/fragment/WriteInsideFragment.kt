package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentWriteInsideBinding
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.WriteUtils
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModelFactory

class WriteInsideFragment :
    BaseFragment<FragmentWriteInsideBinding>(R.layout.fragment_write_inside) {

    private val diaryViewModel: DiaryViewModel by viewModels {
        DiaryViewModelFactory(
            requireActivity()
        )
    }

    private val args by navArgs<WriteInsideFragmentArgs>()

    override fun initView() {

        super.initView()

        binding.titleArea.setText(args.currentItem.title)
        binding.contentArea.setText(args.currentItem.content)
        binding.textDate.text = args.currentItem.date

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.write_inside_frament_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_update -> {
                        updateItem()

                        return true
                    }
                    R.id.menu_delete -> {
                        return true
                    }
                }
                return true
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)

    }

    private fun updateItem() {

       val title = binding.titleArea.text.toString()
       val content = binding.contentArea.text.toString()
       val date = binding.textDate.text.toString()

       val validation = WriteUtils.verifyData(title, content)

       if (validation) {
           val updatedItem = Write(
               args.currentItem.id,
               title,
               content,
               date
           )
           diaryViewModel.updateData(updatedItem)
           Toast.makeText(requireContext(),"update",Toast.LENGTH_SHORT).show()
           findNavController().popBackStack()
       }else{
           Toast.makeText(requireContext(),"update null",Toast.LENGTH_SHORT).show()
       }
    }

}
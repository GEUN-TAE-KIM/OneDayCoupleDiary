package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.app.AlertDialog
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
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentDiaryBinding
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.DiaryUtils
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModel
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class DiaryFragment : BaseFragment<FragmentDiaryBinding>(R.layout.fragment_diary) {

    private val diaryViewModel: DiaryViewModel by viewModels {
        DiaryViewModelFactory(
            requireActivity()
        )
    }

    private val args by navArgs<DiaryFragmentArgs>()

    override fun initView() {

        super.initView()

        binding.titleArea.setText(args.currentItem.title)
        binding.contentArea.setText(args.currentItem.content)
        binding.selectColor.onItemSelectedListener = diaryViewModel.listener

        val pattern = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        binding.textDate.text = dateFormat.format(args.currentItem.date)

        val menuHost: MenuHost = requireActivity()

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

                        deleteItem()

                        return true
                    }
                    // Appbar back 設定
                    android.R.id.home -> {

                        requireActivity().onBackPressed()

                        return true
                    }
                }
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun updateItem() {

        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()
        val dateString = binding.textDate.text.toString()
        val colorSelect = binding.selectColor.selectedItem.toString()

        val pattern = "yyyy-MM-dd"
        val date = SimpleDateFormat(pattern, Locale.getDefault()).parse(dateString)

        val validation = DiaryUtils.verifyData(title, content)

        if (validation) {

            val updatedItem = Diary(
                args.currentItem.id,
                title,
                content,
                date!!.time,
                DiaryUtils.parsePriority(colorSelect)
            )

            diaryViewModel.updateData(updatedItem)

            Toast.makeText(requireContext(), "update", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()

        } else {
            Toast.makeText(requireContext(), "update null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteItem() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            diaryViewModel.deleteData(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Delete Success: ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("remove? '${args.currentItem.title}'?")
        builder.create().show()
    }

}
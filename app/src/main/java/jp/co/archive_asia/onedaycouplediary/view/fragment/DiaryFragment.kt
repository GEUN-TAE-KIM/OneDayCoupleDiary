package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.app.AlertDialog
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.co.archive_asia.onedaycouplediary.MainActivity
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentDiaryBinding
import jp.co.archive_asia.onedaycouplediary.model.ColorSpinner
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.adapter.SpinnerAdapter
import jp.co.archive_asia.onedaycouplediary.view.util.DiaryUtils
import jp.co.archive_asia.onedaycouplediary.view.util.showToast
import jp.co.archive_asia.onedaycouplediary.viewmodel.DiaryViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DiaryFragment : BaseFragment<FragmentDiaryBinding>(R.layout.fragment_diary) {

    private lateinit var spinnerAdapterColor: SpinnerAdapter
    private val listOfYear = ArrayList<ColorSpinner>()

    lateinit var mainActivity: MainActivity

    private val diaryViewModel: DiaryViewModel by viewModels()

    private val args by navArgs<DiaryFragmentArgs>()

    override fun initView() {

        super.initView()

        binding.titleArea.setText(args.currentItem.title)
        binding.contentArea.setText(args.currentItem.content)
        binding.selectColor.onItemSelectedListener



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

    private fun updateItem() {

        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()
        val dateString = binding.textDate.text.toString()
        val selectedItem = binding.selectColor.selectedItem as ColorSpinner

        val pattern = "yyyy-MM-dd"
        val date = SimpleDateFormat(pattern, Locale.getDefault()).parse(dateString)

        val validation = DiaryUtils.verifyData(title, content)

        if (validation) {

            val updatedItem = Diary(
                args.currentItem.user_id,
                title,
                content,
                date!!.time,
                DiaryUtils.parsePriority(selectedItem.color_name)
            )

            diaryViewModel.updateData(updatedItem)

            showToast("update")

            findNavController().popBackStack()

        } else {
            showToast("update null")
        }
    }

    private fun deleteItem() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            diaryViewModel.deleteData(args.currentItem)
            showToast("Delete Success: ${args.currentItem.title}")
            findNavController().popBackStack()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("remove? '${args.currentItem.title}'?")
        builder.create().show()
    }

}
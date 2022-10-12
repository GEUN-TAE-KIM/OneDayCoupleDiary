package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentWriteInsideBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment

class WriteInsideFragment :
    BaseFragment<FragmentWriteInsideBinding>(R.layout.fragment_write_inside) {

    private val args by navArgs<WriteInsideFragmentArgs>()

    override fun initView() {

        super.initView()

        binding.textDate.text = args.currentItem.date
        binding.titleArea.text = args.currentItem.title
        binding.contentArea.text = args.currentItem.content

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.write_inside_frament_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_save -> {
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

}
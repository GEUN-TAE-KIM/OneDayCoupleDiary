package jp.co.archive_asia.onedaycouplediary.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.MainActivity
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentSettingBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.showConfirmDialog
import jp.co.archive_asia.onedaycouplediary.viewmodel.SettingViewModel

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel: SettingViewModel by viewModels()
    lateinit var mainActivity: MainActivity

    override fun initView() {
        super.initView()
        binding.viewModel = viewModel

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonLogout.setOnClickListener {

            showConfirmDialog(
                title = R.string.logout,
                message = R.string.dialog_message_logout,
                positiveButtonText = R.string.dialog_ok_button,
                positiveButtonListener = { _, _ ->
                    signOut()
                },
                negativeButtonText = R.string.dialog_cancel_button
            )

        }

        binding.appVersion.setOnClickListener {
            showDialog()
        }

        mainActivity = context as MainActivity
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.logout.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.introFragment)
        }
    }

    private fun signOut() {
        viewModel.signOut()
    }

    private fun getVersion(context: Context): String {
        var versionName = ""
        try {
            val pInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = "App Version : " + pInfo.versionName
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(mainActivity)
        builder
            .setTitle("Version")
            .setMessage(getVersion(mainActivity))
            .setIcon(null)
            .setPositiveButton("YES") { dialog, which ->
                R.string.dialog_ok_button
            }
            .setNegativeButton("NO") { dialog, which ->
                R.string.dialog_cancel_button
            }
            .create()
            .show()
    }

}
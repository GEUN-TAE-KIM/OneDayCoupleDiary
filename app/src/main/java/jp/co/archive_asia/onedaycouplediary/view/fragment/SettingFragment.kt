package jp.co.archive_asia.onedaycouplediary.view.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentSettingBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.viewmodel.SettingViewModel

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel: SettingViewModel by viewModels()

    override fun initView() {
        super.initView()
        binding.viewModel = viewModel
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonLogout.setOnClickListener {
            signOut()
            findNavController().navigate(R.id.introFragment)
        }

    }

    private fun signOut() {
        viewModel.signOut()
    }

}
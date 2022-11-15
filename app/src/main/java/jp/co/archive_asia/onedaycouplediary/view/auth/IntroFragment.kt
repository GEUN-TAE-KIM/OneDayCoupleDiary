package jp.co.archive_asia.onedaycouplediary.view.auth

import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentIntroBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.addFinishAppBackButtonCallback

class IntroFragment : BaseFragment<FragmentIntroBinding>(R.layout.fragment_intro) {

    override fun initView() {
        super.initView()

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_loginFragment)
        }

        binding.joinBtn.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_joinFragment)
        }

        addFinishAppBackButtonCallback()
    }

}
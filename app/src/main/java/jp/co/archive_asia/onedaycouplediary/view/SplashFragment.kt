package jp.co.archive_asia.onedaycouplediary.view

import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun initView() {
        super.initView()
        findNavController().navigate(R.id.action_splashFragment_to_introFragment)
    }
}
package jp.co.archive_asia.onedaycouplediary.view

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentSplashBinding
import jp.co.archive_asia.onedaycouplediary.viewmodel.SplashViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel : SplashViewModel by viewModels()

    override fun initView() {
        super.initView()
        viewModel.checkLogin()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.isLogin.observe(viewLifecycleOwner) { isLogin ->
            val action = if (isLogin) {
                R.id.action_splashFragment_to_calendarFragment
            } else {
                R.id.action_splashFragment_to_introFragment
            }
            findNavController().navigate(action)
        }
    }
}
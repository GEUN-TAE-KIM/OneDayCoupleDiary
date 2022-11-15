package jp.co.archive_asia.onedaycouplediary.view

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun initView() {
        super.initView()
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(R.id.action_splashFragment_to_introFragment)
        }
    }
}
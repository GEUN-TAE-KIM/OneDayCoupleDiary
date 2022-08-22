package jp.co.archive_asia.onedaycouplediary.view.auth

import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentLoginBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun initView() {
        super.initView()
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_calendarFragment)
        }
    }

}
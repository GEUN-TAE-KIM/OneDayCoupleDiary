package jp.co.archive_asia.onedaycouplediary.view.auth

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentIntroBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.addFinishAppBackButtonCallback
import jp.co.archive_asia.onedaycouplediary.view.util.showConfirmDialog
import jp.co.archive_asia.onedaycouplediary.view.util.showToast
import jp.co.archive_asia.onedaycouplediary.viewmodel.IntroViewModel

class IntroFragment : BaseFragment<FragmentIntroBinding>(R.layout.fragment_intro) {

    private val viewModel : IntroViewModel by viewModels()

    override fun initView() {
        super.initView()

        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_loginFragment)
        }

        binding.buttonJoin.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_joinFragment)
        }

        binding.buttonLoginAnonymous.setOnClickListener {
            showConfirmDialog(
                title = R.string.dialog_confirm_title,
                message = R.string.dialog_confirm_message,
                positiveButtonText = R.string.dialog_ok_button,
                positiveButtonListener = { _, _ ->
                    viewModel.signInAnonymously()
                },
                negativeButtonText = R.string.dialog_cancel_button
            )
        }

        addFinishAppBackButtonCallback()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.loginAnonymously.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_introFragment_to_calendarFragment)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            showToast(errorMessage)
        }
    }

}
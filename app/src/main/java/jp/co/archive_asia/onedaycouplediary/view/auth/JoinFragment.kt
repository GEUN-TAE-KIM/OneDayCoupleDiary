package jp.co.archive_asia.onedaycouplediary.view.auth

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentJoinBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.showToast
import jp.co.archive_asia.onedaycouplediary.viewmodel.JoinViewModel

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val viewModel: JoinViewModel by viewModels()

    override fun initView() {
        super.initView()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnJoin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            createAccount(email, password)
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.result.observe(viewLifecycleOwner) {
            showToast("SingUp: Success")
            Log.d(SingUp, "Success")
            findNavController().navigate(R.id.action_joinFragment_to_calendarFragment)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Log.w(SingUp, "Failed")
            showToast("SingUp Failed: $errorMessage")
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }
        viewModel.createAccount(email, password)
    }

    // 앱에서 표시
    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.etEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            showToast("email入力")
            Log.d(TAG, "email入力 なし")
            binding.etEmail.error = "Required."
            valid = false
        } else {
            binding.etEmail.error = null
        }

        val password = binding.etPassword.text.toString()
        // TODO: 이메일 형식 체크
        val s = "^[a-zA-X0-9]@[a-zA-Z0-9].[a-zA-Z0-9]"

        if (email != s) {
            Toast.makeText(context, "email 入力", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "email 形式じゃない")
            binding.etEmail.error = "Required."
            valid = false
        }

        if (TextUtils.isEmpty(password)) {
            showToast("password 入力")
            Log.d(TAG, "password なし")
            binding.etPassword.error = "Required."
            valid = false
        } else {
            binding.etPassword.error = null
        }

        if (password.length < 6) {
            showToast("最低6文字以上入力")
            Log.d(TAG, "password 6文字以上ない:${password.length}文字")
            binding.etPassword.error = "Required."
            valid = false
        } else {
            binding.etPassword.error = null
        }

        return valid
    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val SingUp = "SingUp"
    }


}
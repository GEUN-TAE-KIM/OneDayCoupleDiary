package jp.co.archive_asia.onedaycouplediary.view.auth

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentJoinBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private lateinit var auth: FirebaseAuth

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

        auth = Firebase.auth
    }

    private fun createAccount(email: String, password: String) {

        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "성공")
                    auth.currentUser
                    findNavController().popBackStack()
                } else {
                    Log.w(TAG, "실패", task.exception)
                    Toast.makeText(context, "형식이 안맞음", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.etEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "이메일 입력", Toast.LENGTH_SHORT).show()
            binding.etEmail.error = "Required."
            valid = false
        } else {
            binding.etEmail.error = null
        }

        val password = binding.etPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "비번 입력", Toast.LENGTH_SHORT).show()
            binding.etPassword.error = "Required."
            valid = false
        } else {
            binding.etPassword.error = null
        }

        if (password.length < 6) {
            Toast.makeText(context, "최소 6자 이상 입력", Toast.LENGTH_SHORT).show()
            valid = false
        }

        return valid
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}
package jp.co.archive_asia.onedaycouplediary.view.auth

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthMultiFactorException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentLoginBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.fragment.CalendarFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth

    override fun initView() {

        super.initView()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            signIn(email, password)

        }

        auth = Firebase.auth

    }

    // 사용자가 있는지 없는지 확인
    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun signIn(email: String, password: String) {

        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "성공")
                    auth.currentUser
                    findNavController().navigate(R.id.action_loginFragment_to_calendarFragment)

                } else {
                    Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "실패")
                }

            }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                Toast.makeText(context, "Reload successful!", Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "reload", task.exception)
                Toast.makeText(context, "Failed to reload user.", Toast.LENGTH_SHORT).show()
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

        return valid
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}
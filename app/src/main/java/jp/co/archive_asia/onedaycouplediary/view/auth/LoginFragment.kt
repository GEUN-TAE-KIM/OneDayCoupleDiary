package jp.co.archive_asia.onedaycouplediary.view.auth

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentLoginBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment
import jp.co.archive_asia.onedaycouplediary.view.util.showToast

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
                    showToast("SignIn Success")
                    Log.d(SingIn, "Success")
                    auth.currentUser
                    findNavController().navigate(R.id.action_loginFragment_to_calendarFragment)

                } else {
                    showToast("Semailとか passwordが違う")
                    Log.d(SingIn, "Failed")
                }

            }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser
                showToast("Reload successful!")
            } else {
                Log.e(TAG, "reload", task.exception)
                showToast("Failed to reload user.")
            }

        }
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
        if (TextUtils.isEmpty(password)) {
            showToast("password 入力")
            Log.d(TAG, "password なし")
            binding.etPassword.error = "Required."
            valid = false
        } else {
            binding.etPassword.error = null
        }

        return valid
    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val SingIn = "SingIn"
    }

    fun getUid(): String {
        auth = FirebaseAuth.getInstance()

        return auth.currentUser?.uid.toString()
    }
}
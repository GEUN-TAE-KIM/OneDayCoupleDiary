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
                    Toast.makeText(context, "SingUp: Success", Toast.LENGTH_SHORT).show()
                    Log.d(SingUp, "Success")
                    auth.currentUser
                    findNavController().popBackStack()
                } else {
                    Log.w(SingUp, "Failed", task.exception)
                    Toast.makeText(context, "SingUp: Failed", Toast.LENGTH_SHORT).show()
                }
                if (email == email) {
                    Toast.makeText(context, "同じメールがあるとか形式が違うです。", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "同じメールがある")
                    binding.etEmail.error = "Required."

                } else {
                    binding.etEmail.error = null
                }

            }
    }

    // 앱에서 표시
    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.etEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "email入力", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "email入力 なし")
            binding.etEmail.error = "Required."
            valid = false
        } else {
            binding.etEmail.error = null
        }

        val password = binding.etPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "password 入力", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "password なし")
            binding.etPassword.error = "Required."
            valid = false
        } else {
            binding.etPassword.error = null
        }

        if (password.length < 6) {
            Toast.makeText(context, "最低6文字以上入力", Toast.LENGTH_SHORT).show()
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
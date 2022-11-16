package jp.co.archive_asia.onedaycouplediary.view.auth

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jp.co.archive_asia.onedaycouplediary.MainActivity
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

            findNavController().navigate(R.id.action_joinFragment_to_calendarFragment)
        }

        auth = Firebase.auth
    }


    // 사용자가 있는지 없는지 확인
    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    private fun createAccount(email: String, password: String) {

        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "성공")
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    Log.w(TAG, "실패", task.exception)
                    Toast.makeText(context, "올바르게 입력 해야 함", Toast.LENGTH_SHORT).show()
                    updateUI(null)
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
            binding.etPassword.error = "Required."
            valid = false
        } else {
            binding.etPassword.error = null
        }

        val password = binding.etPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "Required."
            valid = false
        } else {
            binding.etPassword.error = null
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {


            if (user.isEmailVerified) {

            } else {

            }
        } else {

        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}
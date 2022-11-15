package jp.co.archive_asia.onedaycouplediary.view.auth

import androidx.navigation.fragment.findNavController
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.FragmentJoinBinding
import jp.co.archive_asia.onedaycouplediary.view.BaseFragment

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    override fun initView() {
        super.initView()
        binding.btnJoin.setOnClickListener {
            findNavController().navigate(R.id.action_joinFragment_to_calendarFragment)
        }
    }
}
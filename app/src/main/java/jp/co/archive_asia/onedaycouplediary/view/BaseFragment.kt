package jp.co.archive_asia.onedaycouplediary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import jp.co.archive_asia.onedaycouplediary.view.adapter.WriteAdapter

open class BaseFragment<T: ViewDataBinding>(@LayoutRes private val layoutId: Int): Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!
    private val adapter: WriteAdapter by lazy { WriteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initView()
        initObservers()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * UI関連初期化
     */
    open fun initView() { }

    /**
     * LiveDataなどの初期化
     */
    open fun initObservers() { }
}
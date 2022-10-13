package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.app.DirectAction
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.ItemWriteListBinding
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.view.fragment.CalendarFragment
import jp.co.archive_asia.onedaycouplediary.view.fragment.CalendarFragmentDirections
import jp.co.archive_asia.onedaycouplediary.view.fragment.DiaryFragment
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModel

class WriteAdapter(private val calendarViewModel: CalendarViewModel) :
    RecyclerView.Adapter<WriteAdapter.MyViewHolder>() {

    private var writeList = emptyList<Write>()

    class MyViewHolder(private var binding: ItemWriteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var write: Write
        lateinit var calendarViewModel: CalendarViewModel

        fun bind(write: Write, calendarViewModel: CalendarViewModel) {
            binding.write = write
            this.calendarViewModel = calendarViewModel

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTypde: Int): MyViewHolder {
        val binding =
            ItemWriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(writeList[position], calendarViewModel)
        holder.itemView.setOnClickListener {
            val action = CalendarFragmentDirections.actionCalendarFragmentToWriteInsideFragment(writeList[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return writeList.size
    }

    fun setData(write: List<Write>) {
        writeList = write
        notifyDataSetChanged()
    }
}
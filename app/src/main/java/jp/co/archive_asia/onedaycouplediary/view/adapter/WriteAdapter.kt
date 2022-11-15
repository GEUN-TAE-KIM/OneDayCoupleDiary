package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.databinding.ItemWriteListBinding
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.view.fragment.CalendarFragmentDirections
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.view.util.dateToString
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModel
import java.util.*

class WriteAdapter(private val calendarViewModel: CalendarViewModel) :
    RecyclerView.Adapter<WriteAdapter.MyViewHolder>() {

    private var monthlyWriteList = emptyList<Write>()
    private var currentDay = CalendarUtils.selectedDate.toString()
    private val dailyWriteList: List<Write>
        get() {
            return monthlyWriteList.filter {
                val date = Date(it.date)
                date.dateToString("yyyy-MM-dd") == currentDay
            }
        }

    class MyViewHolder(private var binding: ItemWriteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var write: Write
        lateinit var calendarViewModel: CalendarViewModel

        fun bind(write: Write, calendarViewModel: CalendarViewModel) {
            binding.write = write
            this.calendarViewModel = calendarViewModel
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTypde: Int): MyViewHolder {
        val binding =
            ItemWriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dailyWriteList[position], calendarViewModel)
        holder.itemView.setOnClickListener {
            val action = CalendarFragmentDirections.actionCalendarFragmentToWriteInsideFragment(
                monthlyWriteList[position]
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dailyWriteList.size
    }

    fun setData(write: List<Write>) {
        monthlyWriteList = write
        notifyDataSetChanged()
    }

    fun update(current: String) {
        this.currentDay = current
        notifyDataSetChanged()
    }

}
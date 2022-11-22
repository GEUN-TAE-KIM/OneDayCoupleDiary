package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.databinding.ItemDiaryListBinding
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.view.fragment.CalendarFragmentDirections
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils

class WriteAdapter() :
    RecyclerView.Adapter<WriteAdapter.MyViewHolder>() {

    private var monthlyDiaryList = emptyList<Diary>()
    private var currentDay = CalendarUtils.selectedDate.toString()
  /*  private val dailyDiaryList: List<Diary>
        get() {
            return monthlyDiaryList.filter {
                val date = Date(it.date)
                date.dateToString("yyyy-MM-dd") == currentDay
            }
        }
*/
    class MyViewHolder(private var binding: ItemDiaryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var diary: Diary


        fun bind(diary: Diary) {
            binding.titleArea.text
            binding.contentArea.text
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTypde: Int): MyViewHolder {
        val binding =
            ItemDiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(monthlyDiaryList[position])
        holder.itemView.setOnClickListener {
            val action = CalendarFragmentDirections.actionCalendarFragmentToDiaryFragment(
                monthlyDiaryList[position]
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return monthlyDiaryList.size
    }

    fun setData(diary: List<Diary>) {
        monthlyDiaryList = diary
        notifyDataSetChanged()
    }

    fun update(current: String) {
        this.currentDay = current
        notifyDataSetChanged()
    }

}
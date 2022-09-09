package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.databinding.WriteItemBinding
import jp.co.archive_asia.onedaycouplediary.model.Write

class WriteAdapter : RecyclerView.Adapter<WriteAdapter.MyViewHolder>() {

    private var writeList = emptyList<Write>()

    class MyViewHolder(private var binding: WriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(write: Write) {
            binding.write = write
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = WriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = writeList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return writeList.size
    }
}
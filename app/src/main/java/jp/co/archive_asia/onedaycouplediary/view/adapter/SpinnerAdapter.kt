package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.ItemSpinnerBinding
import jp.co.archive_asia.onedaycouplediary.model.ColorSpinner

class SpinnerAdapter(
    context: Context,
    @LayoutRes private val resId: Int,
    private val values: MutableList<ColorSpinner>
) : ArrayAdapter<ColorSpinner>(context, resId, values) {

    override fun getCount() = values.size

    override fun getItem(position: Int) = values[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val model = values[position]
        try {
            binding.imgSpinner.setImageResource(model.color_image)
         //   binding.imgSpinner.setColorFilter(ContextCompat.getColor(context, R.color.black))
            binding.txtName.text = model.color_name
          //  binding.txtName.setTextColor(ContextCompat.getColor(context, R.color.black))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val model = values[position]
        try {
            binding.imgSpinner.setImageResource(model.color_image)
            binding.txtName.text = model.color_name

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }

}


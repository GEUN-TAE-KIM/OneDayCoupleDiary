package jp.co.archive_asia.onedaycouplediary.viewmodel

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.database.DiaryDatabase
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.repository.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel(application: Activity) : ViewModel() {

    private val writeDao = DiaryDatabase.getDatabase(application).writeDao()
    private val repository: DiaryRepository = DiaryRepository(writeDao)

    fun addData(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addData(diary)
        }
    }

    fun updateData(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(diary)
        }
    }

    fun deleteData(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(diary)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Diary>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(p0: AdapterView<*>?) {}
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when(position){
                0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.main_pink)) }
                1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.pink_100)) }
                2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.pink_200)) }
                3 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.pink_300)) }
                4 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.pink_400)) }
                /*0 -> {(parent?.getChildAt(0) as ImageView).setImageDrawable(ContextCompat.getDrawable(application, R.drawable.ic_baseline_fiber_manual_record_24))}
*/
            }
        }
    }

}
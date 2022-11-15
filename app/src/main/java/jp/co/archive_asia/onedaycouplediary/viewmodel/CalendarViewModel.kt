package jp.co.archive_asia.onedaycouplediary.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.archive_asia.onedaycouplediary.database.WriteDatabase
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.repository.WriteRepository
import jp.co.archive_asia.onedaycouplediary.view.util.toDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarViewModel(application: Activity) : ViewModel() {

    private val writeDao = WriteDatabase.getDatabase(application).writeDao()
    private val repository: WriteRepository = WriteRepository(writeDao)

    // val getAllData: LiveData<List<Write>> = repository.getAllData.asLiveData()

    private var _currentData = MutableLiveData<List<Write>>()
    val currentData: LiveData<List<Write>> = _currentData

    /*fun readDateData(date: String): LiveData<List<Write>> {
        viewModelScope.launch(Dispatchers.IO) {
            _currentData.postValue(repository.readDateData(date))
        }
        return _currentData
    }*/

    fun selectDateData(currentMonth: LocalDate) {

        // 현재 월(currentMonth)을 가지고 저번달 다음달 구하기
        val firstDay = currentMonth.withDayOfMonth(1)
        val preDay = firstDay.dayOfMonth

        val yearMonth = YearMonth.from(currentMonth)
        val nextDay = yearMonth.lengthOfMonth()

        // currentMonth 의 dayOfMonth(일) 1일로 설정(2022/11/01) -> -1일 하면? 2022/10/31 preMonth
        // currentMonth 의 dayOfMonth(일) 마지막 날로 설정(※)(2022/11/30) -> +1일 2022/12/01 nextMonth
        val pre = currentMonth.withDayOfMonth(preDay).minusDays(1)
        val next = currentMonth.withDayOfMonth(nextDay).plusDays(1)

        /////////////
        // LocalDate -> String
        // TODO 2. LocalDate -> Long 변환
        val stringPre = pre.format(DateTimeFormatter.ISO_LOCAL_DATE)
        val stringNext = next.format(DateTimeFormatter.ISO_LOCAL_DATE)

        // String -> Date
        val datePre = stringPre.toDate()
        val dateNext = stringNext.toDate()

        // Date -> Long
        val longPre = datePre?.time ?: 0
        val longNext = dateNext?.time ?: 0

        viewModelScope.launch(Dispatchers.IO) {
            _currentData.postValue(repository.selectDateData(longPre, longNext))
        }
    }

}


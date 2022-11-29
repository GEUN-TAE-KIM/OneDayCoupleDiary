package jp.co.archive_asia.onedaycouplediary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.archive_asia.onedaycouplediary.firestore.repository.DiaryRepository
import jp.co.archive_asia.onedaycouplediary.firestore.repository.UserRepository
import jp.co.archive_asia.onedaycouplediary.firestore.response.ResultStatus
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.view.util.dateToString
import jp.co.archive_asia.onedaycouplediary.view.util.toDate
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarViewModel() : ViewModel() {

    private val repository: DiaryRepository by lazy { DiaryRepository() }
    private val errorMessage: MutableLiveData<String> = MutableLiveData()

    private var allDiaryList: List<Diary> = listOf()
    private var _currentData = MutableLiveData<List<Diary>>()
    val currentData: LiveData<List<Diary>> = _currentData

    init {
        val uid = UserRepository().currentUser!!.uid
        repository.getAllDiary(uid) {
            when (it) {
                is ResultStatus.Success<List<Diary>> -> {
                    allDiaryList = it.data
                    val currentDate = CalendarUtils.selectedDate
                    getMonthlyDiary(currentDate)
                }
                is ResultStatus.Error -> {
                    // TODO: 실패처리
                    errorMessage.value = it.message
                }
            }
        }
    }

    fun selectDateData(currentMonth: LocalDate) {
        getMonthlyDiary(currentMonth)
    }

    private fun getMonthlyDiary(currentMonth: LocalDate) {
        val currentMonthString =
            CalendarUtils.monthYearFromDate(currentMonth) // TODO "yyyy年 MM月" "202210"

        val monthlyDiaryList = allDiaryList.filter { diary ->

            val diaryMonth =
                Date(diary.date).dateToString("yyyy年 MM月") // TODO  ""yyyy年 MM月" "202210"
            currentMonthString == diaryMonth
        }
        _currentData.postValue(monthlyDiaryList)
    }

    fun selectDateData1(currentMonth: LocalDate) {

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


    }

}


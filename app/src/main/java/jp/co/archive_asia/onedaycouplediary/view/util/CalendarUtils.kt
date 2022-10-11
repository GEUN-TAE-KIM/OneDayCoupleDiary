package jp.co.archive_asia.onedaycouplediary.view.util

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object CalendarUtils {

    var selectedDate = LocalDate.now()!!

    fun dayInMonthArray(date: LocalDate?): ArrayList<LocalDate?> {

        val dayList = ArrayList<LocalDate?>()

        val yearMonth = YearMonth.from(date)

        // 月の最後の日付を持ってくる。
        val lastDay = yearMonth.lengthOfMonth()

        // 月の最前の日付を持ってくる。
        val firstDay = selectedDate.withDayOfMonth(1)

        // 初日の日を持ってくる。
        val dayOfWeek = firstDay.dayOfWeek.value

        for (i in 1..41) {
            if (i <= dayOfWeek || i > (lastDay + dayOfWeek)) {
                dayList.add(null)
            } else {
                // LocalDate
                dayList.add(
                    LocalDate.of(
                        selectedDate.year,
                        selectedDate.month, i - dayOfWeek
                    )
                )
            }
        }
        return dayList
    }

    fun monthYearFromDate(date: LocalDate): String {

        val formatter = DateTimeFormatter.ofPattern("yyyy年 MM月")

        //受け取った日付を該当formatに変更
        return date.format(formatter)
    }

}
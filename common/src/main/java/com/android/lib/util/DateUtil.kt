package com.android.lib.util

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * date: 2019/1/30
 * desc: 日期处理相关
 */
object DateUtil {
    private const val yyyyMMddHHmm = "yyyy-MM-dd HH:mm"
    @SuppressLint("SimpleDateFormat")
    fun currentTime(): String {
        val date = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd") // 格式化对象
        val calendar = Calendar.getInstance()
        calendar.time = date
        return sdf.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(dateStr: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val parse = simpleDateFormat.parse(dateStr)
            return simpleDateFormat.format(parse)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dateStr
    }

    fun formateStringH(dateStr: String?, pattren: String?): String? {
        val date = parseDate(dateStr, pattren)
        return try {
            dateToString(date, yyyyMMddHHmm)
        } catch (e: Exception) {
            e.printStackTrace()
            dateStr
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun parseDate(dateStr: String?, type: String?): Date? {
        val df = SimpleDateFormat(type)
        var date: Date? = null
        try {
            date = df.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToString(date: Date?, pattern: String?): String {
        return SimpleDateFormat(pattern).format(date)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(Exception::class)
    fun stringToDate(dateStr: String?, pattern: String?): Date {
        return SimpleDateFormat(pattern).parse(dateStr)
    }

    @SuppressLint("SimpleDateFormat")
    fun currentTimeDeatil(date: Date?): String {
        val sdf =
            SimpleDateFormat("yyyy-MM-dd HH:mm") // 格式化对象
        val calendar = Calendar.getInstance()
        calendar.time = date
        return sdf.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun currentMonth(): String {
        val date = Date()
        val sdf = SimpleDateFormat("yyyy-MM") // 格式化对象
        val calendar = Calendar.getInstance()
        calendar.time = date
        return sdf.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun lastMonth(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM")
        val d: Date
        try {
            d = sdf.parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = d
            calendar.add(Calendar.MONTH, -1)
            return sdf.format(calendar.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    @SuppressLint("SimpleDateFormat")
    fun nextMonth(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM")
        val d: Date
        try {
            d = sdf.parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = d
            calendar.add(Calendar.MONTH, +1)
            return sdf.format(calendar.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    @SuppressLint("SimpleDateFormat")
    fun lastDay(): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd") // 格式化对象
        var date = Date(System.currentTimeMillis())
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        date = calendar.time
        return sdf.format(date)
    }

    /**
     * 前7天
     */
    @SuppressLint("SimpleDateFormat")
    fun lastSevenDay(): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd") // 格式化对象
        var date = Date(System.currentTimeMillis())
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -7)
        date = calendar.time
        return sdf.format(date)
    }

    /**
     * 前14天
     */
    @SuppressLint("SimpleDateFormat")
    fun lastFourteenDay(): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd") // 格式化对象
        var date = Date(System.currentTimeMillis())
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -14)
        date = calendar.time
        return sdf.format(date)
    }

    /**
     * start
     * 本周开始时间戳 - 以星期一为本周的第一天
     *
     * @return 时间
     */
    val weekStartTime: String
        get() {
            val simpleDateFormat =
                SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            val cal = Calendar.getInstance()
            var day_of_week = cal[Calendar.DAY_OF_WEEK] - 1
            if (day_of_week == 0) {
                day_of_week = 7
            }
            cal.add(Calendar.DATE, -day_of_week + 1)
            return simpleDateFormat.format(cal.time) + ""
        }

    /**
     * end
     * 本周结束时间戳 - 以星期一为本周的第一天
     *
     * @return 时间
     */
    val weekEndTime: String
        get() {
            val simpleDateFormat =
                SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            val cal = Calendar.getInstance()
            var day_of_week = cal[Calendar.DAY_OF_WEEK] - 1
            if (day_of_week == 0) {
                day_of_week = 7
            }
            cal.add(Calendar.DATE, -day_of_week + 7)
            return simpleDateFormat.format(cal.time) + ""
        }

    /**
     * 本月第一天数据
     */
    @SuppressLint("SimpleDateFormat")
    fun currentFDay(): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd") // 格式化对象
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        sdf.format(calendar.time)
        return sdf.format(calendar.time)
    }

    /**
     * 本月最后天数据
     */
    @SuppressLint("SimpleDateFormat")
    fun currentLDay(): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd") // 格式化对象
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        sdf.format(calendar.time)
        return sdf.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun currentLDaySchedule(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd") // 格式化对象
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        sdf.format(calendar.time)
        return sdf.format(calendar.time)
    }

    /**
     * 上个月第一天数据
     */
    @SuppressLint("SimpleDateFormat")
    fun currentFFday(): String {
        val df = SimpleDateFormat("yyyy.MM.dd")
        val gcLast =
            Calendar.getInstance() as GregorianCalendar
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MONTH, -1)
        val theDate = calendar.time
        gcLast.time = theDate
        gcLast[Calendar.DAY_OF_MONTH] = 1
        return df.format(gcLast.time)
    }

    /**
     * 上个月最后一天数据
     */
    @SuppressLint("SimpleDateFormat")
    fun currentLLday(): String {
        val df = SimpleDateFormat("yyyy.MM.dd") // 格式化对象
        val calendar = Calendar.getInstance() // 此时打印它获取的是系统当前时间
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH - 1)
        val theDate = calendar.time
        return df.format(theDate)
    }

    /**
     * 上个月最后一天数据
     */
    @SuppressLint("SimpleDateFormat")
    fun currentLLdaySchedule(date: String): String {
        try {
            val aa = "$date-01"
            val df = SimpleDateFormat("yyyy-MM-dd") // 格式化对象
            val calendar = Calendar.getInstance() // 此时打印它获取的是系统当前时间
            calendar.time = df.parse(aa)
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val theDate = calendar.time
            return df.format(theDate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    /**
     * 下一个月最后一天数据
     */
    @SuppressLint("SimpleDateFormat")
    fun lastLLdaySchedule(date: String): String {
        try {
            val aa = "$date-01"
            val df = SimpleDateFormat("yyyy-MM-dd") // 格式化对象
            val calendar = Calendar.getInstance() // 此时打印它获取的是系统当前时间
            calendar.time = df.parse(aa)
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val theDate = calendar.time
            return df.format(theDate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    /**
     * 获取最近三十天的数据
     */
    @SuppressLint("SimpleDateFormat")
    fun lastThrDay(): String {
        val df = SimpleDateFormat("yyyy.MM.dd") // 格式化对象
        val calendar = Calendar.getInstance() // 此时打印它获取的是系统当前时间
        // calendar.add(Calendar.MONTH, -1); //
// if (calendar.get(calendar.DAY_OF_MONTH)==1) {
//
// }
        calendar.add(Calendar.DATE, -30)
        val theDate = calendar.time
        return df.format(theDate)
    }

    var weekName =
        arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")

    fun getMonthDays(year: Int, month: Int): Int {
        var year = year
        var month = month
        if (month > 12) {
            month = 1
            year += 1
        } else if (month < 1) {
            month = 12
            year -= 1
        }
        val arr = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        var days = 0
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            arr[1] = 29
        }
        try {
            days = arr[month - 1]
        } catch (e: Exception) {
            e.stackTrace
        }
        return days
    }

    val year: Int
        get() = Calendar.getInstance()[Calendar.YEAR]

    val month: Int
        get() = Calendar.getInstance()[Calendar.MONTH] + 1

    val currentMonthDay: Int
        get() = Calendar.getInstance()[Calendar.DAY_OF_MONTH]

    val weekDay: Int
        get() = Calendar.getInstance()[Calendar.DAY_OF_WEEK]

    val hour: Int
        get() = Calendar.getInstance()[Calendar.HOUR_OF_DAY]

    val minute: Int
        get() = Calendar.getInstance()[Calendar.MINUTE]

    //    public static CustomDate getNextSunday() {
//
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, 7 - getWeekDay()+1);
//        CustomDate date = new CustomDate(c.get(Calendar.YEAR),
//                c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
//        return date;
//    }
    fun getWeekSunday(year: Int, month: Int, day: Int, pervious: Int): IntArray {
        val time = IntArray(3)
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = day
        c.add(Calendar.DAY_OF_MONTH, pervious)
        time[0] = c[Calendar.YEAR]
        time[1] = c[Calendar.MONTH] + 1
        time[2] = c[Calendar.DAY_OF_MONTH]
        return time
    }

    fun getWeekDayFromDate(year: Int, month: Int): Int {
        val cal = Calendar.getInstance()
        cal.time = getDateFromString(year, month)
        var week_index = cal[Calendar.DAY_OF_WEEK] - 1
        if (week_index < 0) {
            week_index = 0
        }
        return week_index
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFromString(year: Int, month: Int): Date? {
        val dateString =
            year.toString() + "-" + (if (month > 9) month else "0$month") + "-01"
        var date: Date? = null
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            date = sdf.parse(dateString)
        } catch (e: ParseException) {
            println(e.message)
        }
        return date
    }
    //    public static boolean isToday(CustomDate date){
//        return(date.year == DateUtils.getYear() &&
//                date.month == DateUtils.getMonth()
//                && date.day == DateUtils.getCurrentMonthDay());
//    }
//
//    public static boolean isCurrentMonth(CustomDate date){
//        return(date.year == DateUtils.getYear() &&
//                date.month == DateUtils.getMonth());
//    }
    /**
     * 获取做给定年月的最后一天
     *
     * @param year  年份
     * @param month 月份
     * @return 天数
     */
    @SuppressLint("SimpleDateFormat")
    fun getMonthEnd(year: Int, month: Int): String {
        val calendar = Calendar.getInstance()
        // 设置时间,当前时间不用设置
// calendar.setTime(new Date());
// 设置日期为本月最大日期
        calendar[Calendar.MONTH] = month - 1
        calendar[Calendar.YEAR] = year
        calendar[Calendar.DATE] = calendar.getActualMaximum(Calendar.DATE)
        // 打印
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        println(format.format(calendar.time))
        return format.format(calendar.time)
    }

    /**
     * 获取做给定年月的第一天
     * Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1
     *
     * @param year  年份
     * @param month 月份
     * @return 天数
     */
    @SuppressLint("SimpleDateFormat")
    fun getMonthStart(year: Int, month: Int): String {
        val calendar = Calendar.getInstance()
        // 设置时间,当前时间不用设置
// calendar.setTime(new Date());
// 设置日期为本月最大日期
        calendar[Calendar.MONTH] = month - 1
        calendar[Calendar.YEAR] = year
        calendar[Calendar.DATE] = 1
        // 打印
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        //        System.out.println(format.format(calendar.getTime()));
        return format.format(calendar.time)
    }

    /**
     * 昨天
     */
    @SuppressLint("SimpleDateFormat")
    fun currentYesterday(): String {
        val date = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd") // 格式化对象
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        calendar.time = date
        return sdf.format(calendar.time)
    }

    /**
     * 本周的第一天
     */
    @SuppressLint("SimpleDateFormat")
    fun currentWeekone(): String {
        val date = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd") // 格式化对象
        val calendar = Calendar.getInstance()
        calendar.time = date
        if (calendar[Calendar.DAY_OF_WEEK] == 0) {
            calendar.add(Calendar.DATE, -6)
        } else {
            calendar.add(Calendar.DATE, 2 - calendar[Calendar.DAY_OF_WEEK])
        }
        return sdf.format(calendar.time)
    }

    /**
     * 获取当前的时分
     */
    @SuppressLint("SimpleDateFormat")
    fun currentHourMinute(): String {
        val date = Date()
        val sdf = SimpleDateFormat("HH:mm") // 格式化对象
        return sdf.format(date)
    }

    /**
     * 根据提供的年月日获取该月份的第一天
     */
    fun getSupportBeginDayofMonth(date: Date): String {
        date.time
        val startDate = Calendar.getInstance()
        startDate.time = date
        startDate[Calendar.DAY_OF_MONTH] = 1
        startDate[Calendar.HOUR_OF_DAY] = 0
        startDate[Calendar.MINUTE] = 0
        startDate[Calendar.SECOND] = 0
        startDate[Calendar.MILLISECOND] = 0
        val firstDate = startDate.time
        return (firstDate.time.toString() + "").substring(0, 10)
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     */
    fun getSupportEndDayofMonth(date: Date?): String {
        val startDate = Calendar.getInstance()
        startDate.time = date
        startDate[Calendar.DAY_OF_MONTH] = startDate.getActualMaximum(Calendar.DAY_OF_MONTH)
        startDate[Calendar.HOUR_OF_DAY] = 23
        startDate[Calendar.MINUTE] = 59
        startDate[Calendar.SECOND] = 59
        startDate[Calendar.MILLISECOND] = 999
        val firstDate = startDate.time
        return (firstDate.time.toString() + "").substring(0, 10)
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     * 返回为true第一个日期大于或等于第二个日期
     *
     * @param startDate the first date
     * @param endDate   the second date
     * @return true <br></br>false
     */
    @SuppressLint("SimpleDateFormat")
    fun isDateOneBigger(startDate: String?, endDate: String?): Boolean {
        var isBigger = false
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dt1: Date
        val dt2: Date
        try {
            dt1 = sdf.parse(startDate)
            dt2 = sdf.parse(endDate)
            when {
                dt1.time > dt2.time -> {
                    isBigger = true
                }
                dt1.time == dt2.time -> {
                    isBigger = true
                }
                dt1.time < dt2.time -> {
                    isBigger = false
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return isBigger
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     * 返回为true第二个日期大于或等于第一个日期
     *
     * @param startDate the first date
     * @param endDate   the second date
     * @return true <br></br>false
     */
    @SuppressLint("SimpleDateFormat")
    fun isDateTwoBigger(startDate: String?, endDate: String?): Boolean {
        var isBigger = false
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dt1: Date
        val dt2: Date
        try {
            dt1 = sdf.parse(startDate)
            dt2 = sdf.parse(endDate)
            if (dt1.time > dt2.time) {
                isBigger = false
            } else if (dt1.time <= dt2.time) {
                isBigger = true
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return isBigger
    }

    /**
     * 时间戳转换成字符串
     *
     * @param milSecond 时间戳
     * @param pattern   日期格式
     * @return 时间
     */
    @SuppressLint("SimpleDateFormat")
    fun getDateToString(milSecond: Long, pattern: String?): String {
        val date = Date(milSecond)
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }

    /**
     * 字符串转为时间戳
     *
     * @param dateString 时间
     * @param pattern    格式
     * @return 时间戳
     */
    @SuppressLint("SimpleDateFormat")
    fun getStringToDate(dateString: String?, pattern: String?): Long {
        val dateFormat = SimpleDateFormat(pattern)
        var date = Date()
        try {
            date = dateFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date.time
    }

    /**
     * 毫秒换算成时间
     *
     * @param mss 毫秒数
     * @return 时间
     */
    fun formatDateTimes(mss: Long): String { //        String dateTimes = "";
//        long days = mss / (60 * 60 * 24);
//        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
//        long minutes = (mss % (60 * 60)) / 60;
//        long seconds = mss % 60;
//        if (days > 0) {
////            dateTimes = days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
//            long d = (days * 24) + hours;
//            if (minutes > 0) {
//                dateTimes = d + "h" + minutes + "min";
//            } else {
//                dateTimes = d + "h";
//            }
//        } else if (hours > 0) {
//            if (minutes > 0) {
//                dateTimes = hours + "h" + minutes + "min";
//            } else {
//                dateTimes = hours + "h";
//            }
//        } else if (minutes > 0) {
//            if (seconds >= 30) {
//                dateTimes = (minutes + 1) + "min";
//            } else {
//                dateTimes = minutes + "min";
//            }
//        } else {
//            if (seconds >= 30) {
//                dateTimes = 1 + "min";
//            } else {
//                dateTimes = "0";
//            }
//        }
//        return dateTimes;
        var dateTimes = ""
        val days = mss / (60 * 60 * 24)
        val hours = mss % (60 * 60 * 24) / (60 * 60)
        val minutes = mss % (60 * 60) / 60
        val seconds = mss % 60
        dateTimes = when {
            days > 0 -> {
                days.toString() + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒"
            }
            hours > 0 -> {
                hours.toString() + "小时" + minutes + "分钟" + seconds + "秒"
            }
            minutes > 0 -> {
                minutes.toString() + "分钟" + seconds + "秒"
            }
            else -> {
                seconds.toString() + "秒"
            }
        }
        return dateTimes
    }

    /**
     * 根据时间戳计算时间
     * 计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟
     * 根据差值返回多长之间前或多长时间后
     *
     * @param time1 开始时间戳
     * @param time2 结束时间戳
     * @return 几天 几个小时 或 几分钟
     */
    fun getDistanceTime(time1: Long, time2: Long): String {
        var day: Long = 0
        var hour: Long = 0
        var min: Long = 0
        var sec: Long = 0
        var diff: Long = 0
        diff = if (time1 < time2) {
            time2 - time1
        } else {
            time1 - time2
        }
        day = diff / (24 * 60 * 60 * 1000)
        hour = diff / (60 * 60 * 1000) - day * 24
        min = diff / (60 * 1000) - day * 24 * 60 - hour * 60
        sec = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60
        if (day != 0L) {
            return day.toString() + "天" + hour + "小时" + min + "分钟" + sec + "秒"
        }
        if (hour != 0L) {
            return hour.toString() + "小时" + min + "分钟" + sec + "秒"
        }
        if (min != 0L) {
            return min.toString() + "分钟" + sec + "秒"
        }
        return if (sec != 0L) {
            sec.toString() + "秒"
        } else "0秒"
    }
}
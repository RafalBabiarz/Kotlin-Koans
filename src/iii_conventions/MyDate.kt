package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year.compareTo(other.year) != 0)
            return year.compareTo(other.year)
        if (month.compareTo(other.month) != 0)
            return month.compareTo(other.month)
        return dayOfMonth.compareTo(other.dayOfMonth)
    }

    operator fun plus(timeInterval: TimeInterval): MyDate {
        return addTimeIntervals(timeInterval, 1)
    }

    operator fun plus(multipliedTimeInterval: MultipliedTimeInterval): MyDate {
        return addTimeIntervals(multipliedTimeInterval.timeInterval, multipliedTimeInterval.times)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(multiplier: Int): MultipliedTimeInterval = MultipliedTimeInterval(this, multiplier)
}

class MultipliedTimeInterval(val timeInterval: TimeInterval, val times: Int)


class DateRange(val start: MyDate, val endInclusive: MyDate) {
    operator fun contains(date: MyDate): Boolean {
        return start < date && endInclusive >= date
    }

    operator fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start

            override fun hasNext(): Boolean = current <= endInclusive

            override fun next(): MyDate {
                val result = current
                current = current.nextDay()
                return result
            }

        }
    }
}
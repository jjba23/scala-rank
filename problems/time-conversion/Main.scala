package scalarank.timeconversion

def timeConversion(s: String): String =
  val isPm = s.endsWith("PM")
  val timeData = s.dropRight(2)
  val hours = timeData.take(2).toInt
  val minSec = timeData.drop(3)
  val newHours =
    if (isPm) then (if hours == 12 then hours else hours + 12) else hours

  s"$newHours:$minSec"

def printTimeConversion(s: String): Unit =
  println(s"timeConversion $s ")
  println(timeConversion(s))

object Main extends App:
  printTimeConversion("12:10:57AM")
  printTimeConversion("09:10:54PM")
  printTimeConversion("09:10:57AM")
  printTimeConversion("12:45:00PM")

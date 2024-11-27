package id.handlips.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatDate(inputDate: String) : String{
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return outputFormat.format(date as Date)
}
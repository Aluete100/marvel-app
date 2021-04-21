package com.alan.intermediatest.utils

import android.app.Activity
import android.view.View
import com.alan.intermediatest.ui.base.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ViewUtils(private val activity: Activity) {
    companion object {
        private const val DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss"

        fun formatDate(dateToFormat: String, pattern: String): String {
            val formatIncomingDate =
                SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault())
            val formatOutcomingDate = SimpleDateFormat(pattern, Locale.getDefault())

            val date = formatIncomingDate.parse(dateToFormat)

            return formatOutcomingDate.format(date!!)
        }


    }

    fun showLoading(show: Boolean) {
        (activity as MainActivity).loading_overlay.visibility =
            if (show) View.VISIBLE else View.GONE
    }

}
package de.syntax.androidabschluss.ui.events

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.fragment.app.DialogFragment


class TimePickerFragment(val listener: (String) -> Unit) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {


    //create a time picker dialog
    override fun onTimeSet(view: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {
        val formattedTime = String.format("%02d:%02d", hourOfDay, minute) + "h"
        listener(formattedTime)
    }

    // create a calendar picker dialog
    override fun onCreateDialog(savedInstanceState: android.os.Bundle?): android.app.Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(activity as Context, this, hour, minute, true)
    }
}
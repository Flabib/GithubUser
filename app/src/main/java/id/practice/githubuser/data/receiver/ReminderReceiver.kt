package id.practice.githubuser.data.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.practice.githubuser.App.Companion.context
import id.practice.githubuser.R
import id.practice.githubuser.views.activities.MainActivity
import id.practice.githubuser.helper.Toolkit
import java.util.*

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val reminderHeader = context.resources?.getString(R.string.reminder_header)
        val reminderMessage = context.resources?.getString(R.string.reminder_message)
        val pendingIntent = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)

        Toolkit.showNotification(reminderHeader, reminderMessage, 0, pendingIntent)
    }

    fun enable() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(context, 100, Intent(context, ReminderReceiver::class.java), 0)
        val calendar = Calendar.getInstance()

        val time = "11:26:00"

        val hour = time.split(":")[0].toInt()
        val minute = time.split(":")[0].toInt()
        val second = time.split(":")[0].toInt()

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, second)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun disable() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(context, 100, Intent(context, ReminderReceiver::class.java), 0)

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}

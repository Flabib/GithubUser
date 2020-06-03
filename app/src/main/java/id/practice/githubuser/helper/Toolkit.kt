package id.practice.githubuser.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import id.practice.githubuser.App.Companion.context
import id.practice.githubuser.R
import id.practice.githubuser.data.receiver.ReminderReceiver

class Toolkit {
    companion object {
        fun darkMode(enable: Boolean, showNotif: Boolean = false) {
            val nightmodeEnable = context?.resources?.getString(R.string.night_mode_enable)
            val nightmodeDisable = context?.resources?.getString(R.string.night_mode_disable)

            if (enable) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                if (showNotif) Toast.makeText(context, nightmodeEnable, Toast.LENGTH_SHORT).show()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                if (showNotif) Toast.makeText(context, nightmodeDisable, Toast.LENGTH_SHORT).show()
            }
        }

        fun reminder(enable: Boolean, showNotif: Boolean = false) {
            val reminderReceiver = ReminderReceiver()

            val reminderEnable = context?.resources?.getString(R.string.reminder_enable)
            val reminderDisable = context?.resources?.getString(R.string.reminder_disable)

            if (enable) {
                reminderReceiver.enable()
                if (showNotif) Toast.makeText(context, reminderEnable, Toast.LENGTH_SHORT).show()
            } else {
                reminderReceiver.disable()
                if (showNotif) Toast.makeText(context, reminderDisable, Toast.LENGTH_SHORT).show()
            }
        }

        fun showNotification(title: String?, message: String?, notifId: Int, pendingIntent: PendingIntent? = null) {
            val channelId = "Channel_1"
            val channelName = "AlarmManager channel"

            val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val builder = NotificationCompat.Builder(context as Context, channelId)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context as Context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
                .setAutoCancel(true)

            if (pendingIntent != null) builder.setContentIntent(pendingIntent)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

                builder.setChannelId(channelId)

                notificationManagerCompat.createNotificationChannel(channel)
            }

            val notification = builder.build()

            notificationManagerCompat.notify(notifId, notification)
        }
    }
}
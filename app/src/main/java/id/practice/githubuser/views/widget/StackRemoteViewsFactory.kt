package id.practice.githubuser.views.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import id.practice.githubuser.R

internal class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val widgetItems = ArrayList<Bitmap>()

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        widgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.img_coffee))
        widgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.img_coffee))
        widgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.img_coffee))
        widgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.img_coffee))
        widgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.img_coffee))
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_item)
        remoteViews.setImageViewBitmap(R.id.imageView, widgetItems[position])

        val extras = bundleOf(
            FavoriteUserWidget.EXTRA_ITEM to position
        )

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent)

        return remoteViews
    }

    override fun getCount(): Int = widgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}
}
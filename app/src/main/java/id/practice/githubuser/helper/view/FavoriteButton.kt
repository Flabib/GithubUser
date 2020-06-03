package id.practice.githubuser.helper.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import id.practice.githubuser.R

class FavoriteButton : AppCompatButton {

    private var enabledBackground: Drawable? = null
    private var disabledBackground: Drawable? = null
    private var txtColor: Int = 0
    var isFavorite = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        background = when {
            isFavorite -> disabledBackground
            else -> enabledBackground
        }

        setTextColor(txtColor)

        textSize = 12f
        gravity = Gravity.CENTER
        text = when {
            isFavorite -> resources.getString(R.string.unfavorite)
            else -> resources.getString(R.string.favorite)
        }
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_enable, null)
        disabledBackground = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_disable, null)
    }
}
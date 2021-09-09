package com.moetaz.customviewsdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class Badge @JvmOverloads constructor(
    context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attributes, defStyleAttr) {
    var textBadge = "22"
    lateinit var paint: Paint

    init {
        paint = Paint()
        paint.color = -0x1
        paint.isAntiAlias = true
        paint.textSize = 32f
        //resources.getDimensionPixelSize(R.dimen.badge_text).toFloat() //canvas text size

        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val xPos: Float = width.toFloat() / 2
        val yPos: Float = (height.toFloat() / 2 - (paint.descent() + paint.ascent()) / 2)

        canvas.drawText(textBadge, xPos, yPos, paint)
    }

    fun getText(): String? {
        return textBadge
    }

    fun setText(text: String?) {
        this.textBadge = text!!
        invalidate()
    }
}
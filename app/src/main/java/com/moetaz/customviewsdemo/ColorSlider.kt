package com.moetaz.customviewsdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import java.util.jar.Attributes

class ColorSlider @JvmOverloads constructor(
    context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = R.attr.seekBarStyle
) : AppCompatSeekBar(context, attributes, defStyleAttr) {
    private var colors: ArrayList<Int> = arrayListOf(Color.BLACK, Color.BLUE, Color.DKGRAY)
    val w = getPixelValuesFromDP(16f)
    val h = getPixelValuesFromDP(16f)
    val halfW = if (w >= 0) w / 2f else 1f
    val halfH = if (h >= 0) h / 2f else 1f
    val paint = Paint()
    private var drawable: Drawable? = null
        set(value) {
            w2 = value?.intrinsicWidth ?: 0
            h2 = value?.intrinsicHeight ?: 0
            halfW2 = if (w2 >= 0) w2 / 2 else 1
            halfH2 = if (h2 >= 0) h2 / 2 else 1
            value?.setBounds(-halfW2, -halfH2, halfW2, halfH2)
            field = value
        }

    var w2 = 0
    var h2 = 0
    var halfW2 = 1
    var halfH2 = 1

    init {
        val typeArray = context.obtainStyledAttributes(attributes, R.styleable.ColorSlider)
        try {
            colors = typeArray.getTextArray(R.styleable.ColorSlider_colors)
                .map {
                    Color.parseColor(it.toString())
                } as ArrayList<Int>

        } finally {
            typeArray.recycle()
        }
        colors.add(0, android.R.color.transparent)
        max = colors.size - 1
        progressBackgroundTintList =
            ContextCompat.getColorStateList(context, android.R.color.transparent)
        progressTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        splitTrack = false
        setPadding(
            paddingLeft,
            paddingTop,
            paddingRight,
            paddingBottom + getPixelValuesFromDP(16f).toInt()
        )
        thumb = context.getDrawable(R.drawable.ic_arrow)
        drawable = context.getDrawable(R.drawable.ic_remove)
        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                listeners.forEach {
                    it(colors[progress])
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    var selectedColorValue: Int = android.R.color.transparent
        set(value) {
            var index = colors.indexOf(value)
            if (index == -1) {
                progress = 0
            } else {
                progress = index
            }
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawTickMarks(canvas)
    }

    private fun drawTickMarks(canvas: Canvas?) {
        canvas?.let {
            val count = colors.size
            val saveCount = canvas.save()
            canvas.translate(
                paddingLeft.toFloat(),
                (height / 2).toFloat() + getPixelValuesFromDP(16f)
            )

            if (count > 1) {
                for (i in 0 until count) {

                    val spacing = (width - paddingLeft - paddingRight) / (count - 1).toFloat()
                    if (i == 0) {
                        drawable?.draw(canvas)
                    } else {
                        paint.color = colors[i]
                        canvas.drawRect(-halfW, -halfH, halfW, halfH, paint)
                    }

                    canvas.translate(spacing, 0f)

                }
                canvas.restoreToCount(saveCount)
            }
        }
    }

    private var listeners: ArrayList<(Int) -> Unit> = arrayListOf()
    fun addListener(function: (Int) -> Unit) {
        listeners.add(function)
    }

    private fun getPixelValuesFromDP(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            context.resources.displayMetrics
        )
    }
}
package com.moetaz.customviewsdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class DashView @JvmOverloads constructor(
    context: Context, attributes: AttributeSet? = null
    , defStyleAttr: Int = 0
) : View(context, attributes, defStyleAttr) {

    val ORIENTATION_HORIZONTAL = 0

    private var mPaint: Paint
    private var circlePaint: Paint

    private var orientation = 0
    private var circlePositionPercentage = 0.0f
        set(value) {
            field = when {
                value < 0f -> 0f
                value > 1f -> 1f
                else -> value
            }
        }

    private var circleDiameter = 10f.toPixel(context)

    var dashGap: Int
    var dashLength: Int
    var dashThickness: Int
    var color: Int
    var circleColor: Int

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(
                attributes,
                R.styleable.DashView, 0, 0
            )

        try {

            typedArray.apply {
                dashGap = getDimensionPixelSize(R.styleable.DashView_dashGap, 5)
                dashLength =  getDimensionPixelSize(R.styleable.DashView_dashLength, 5)
                dashThickness =  getDimensionPixelSize(R.styleable.DashView_dashThickness, 3)
                color =  getColor(R.styleable.DashView_color, -0x1000000)
                orientation =  getInt(R.styleable.DashView_orientation, ORIENTATION_HORIZONTAL)
                circleColor =  getColor(R.styleable.DashView_circelColor, Color.parseColor("#00AAEC"))
                circlePositionPercentage =
                     getFloat(R.styleable.DashView_circlePostionPercentage, circlePositionPercentage)

                circleDiameter = getDimension(R.styleable.DashView_circleRadius, circleDiameter)
            }

        } finally {
            typedArray.recycle()
        }

        mPaint = Paint().apply {
            isAntiAlias = true
            this.color = this@DashView.color
            style = Paint.Style.STROKE
            strokeWidth = dashThickness.toFloat()
            pathEffect = DashPathEffect(
                floatArrayOf(dashLength.toFloat(), dashGap.toFloat()),
                0f
            )
        }

        circlePaint = Paint().apply {
            color = circleColor
            isAntiAlias = true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = resolveSizeAndState(
            (circleDiameter * 2).toInt(),
            widthMeasureSpec,
            0
        )
        val height = resolveSizeAndState(
            (circleDiameter * 2).toInt(), heightMeasureSpec,
            0
        )
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        if (orientation == ORIENTATION_HORIZONTAL) {
            canvas?.let {
                val center = height * .5f
                it.drawLine(0f, center, width.toFloat(), center, mPaint)
                val cx = (circlePositionPercentage * (width - circleDiameter * 2)) + circleDiameter
                it.drawCircle(cx, height / 2f, circleDiameter, circlePaint)
            }
        } else {
            canvas?.let {
                val center = width * .5f
                it.drawLine(center, 0f, center, height.toFloat(), mPaint)
                val cy = (circlePositionPercentage * (height - circleDiameter * 2)) + circleDiameter
                it.drawCircle(width / 2f, cy, circleDiameter, circlePaint)
            }

        }

    }

}
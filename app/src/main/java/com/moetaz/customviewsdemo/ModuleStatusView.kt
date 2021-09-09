package com.moetaz.customviewsdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.os.bundleOf


class ModuleStatusView @JvmOverloads constructor(
    context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attributes, defStyleAttr, defStyleRes) {

    private val EDIT_MODE_MODULE_COUNT: Int = 7
    var modulesStatus = ArrayList<Boolean>()
    var mModuleRectangles = ArrayList<Rect>()
    var outlineColor = Color.BLACK
    val fillColor = Color.YELLOW
    //view knows only pixels so we convert this 5 dp into pixels
    var mOutlineWidth = 5f.toPixel(context)
    val paintfilled = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = fillColor
    }

    val paintOutline = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE

        color = outlineColor
    }


    var mShapeSize = 100f.toPixel(context)
    var mSpacing =  11f.toPixel(context)

    val raduis = (mShapeSize + mOutlineWidth) / 2
    var shape = 0

    init {

        if (isInEditMode)
            setEditModeValues()

        val typedArray = context.obtainStyledAttributes(
            attributes,
            R.styleable.ModuleStatusView
        )

        try {

            outlineColor = typedArray.getColor(
                R.styleable.ModuleStatusView_outlineColor,
                outlineColor
            )

            shape = typedArray.getInt(R.styleable.ModuleStatusView_shape, 0)

            mOutlineWidth =
                typedArray.getDimension(R.styleable.ModuleStatusView_outlineWidth, mOutlineWidth)
        } finally {
            typedArray.recycle()
        }


        paintOutline.strokeWidth = mOutlineWidth
    }

    private fun setEditModeValues() {
        for (index in 0 until EDIT_MODE_MODULE_COUNT)
            modulesStatus.add(true)
    }

    private fun setupModuleRectangles(width: Int) {

        val availavbleWidth = width - paddingStart - paddingEnd
        val horizontalModulesThatCanFit = (availavbleWidth / (mShapeSize + mSpacing))
        val mMaxHorizontalModules =
            Math.min(horizontalModulesThatCanFit.toInt(), modulesStatus.size)
        modulesStatus.forEachIndexed { index, b ->

            val col = index % mMaxHorizontalModules
            val row = index / mMaxHorizontalModules

            val x = ((mShapeSize + mSpacing) * col).toInt() + paddingStart
            val y = (row * (mSpacing + mShapeSize)).toInt() + paddingTop

            val rect = Rect(x, y, x + mShapeSize.toInt(), y + mShapeSize.toInt())

            mModuleRectangles.add(rect)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        setupModuleRectangles(w)
    }

    var maxHorizontalModules = 1
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var desiredWidth = 0
        var desiredHeight = 0
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val availableWidth = specWidth - paddingEnd - paddingStart
        val horizontalModulesThatCanFit = (availableWidth / (mShapeSize + mSpacing))
        maxHorizontalModules = Math.min(horizontalModulesThatCanFit.toInt(), modulesStatus.size)

        desiredWidth =
            (maxHorizontalModules * (mShapeSize + mSpacing) - mSpacing).toInt()
        + paddingStart + paddingEnd


        val rows = ((modulesStatus.size - 1) / maxHorizontalModules) + 1

        desiredHeight =
            ((rows * (mShapeSize + mSpacing)) - mSpacing).toInt() + paddingTop + paddingBottom

        val width = resolveSizeAndState(desiredWidth, widthMeasureSpec, 0)
        val height = resolveSizeAndState(desiredHeight, heightMeasureSpec, 0)

        setMeasuredDimension(width, height)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_UP -> {
                val moduleIndex = findItemAtPoint(event.x, event.y)
                onModuleSelected(moduleIndex)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun onModuleSelected(moduleIndex: Int) {
        if (moduleIndex == INVALID_INDEX)
            return
        modulesStatus[moduleIndex] = modulesStatus[moduleIndex].not()
        invalidate()
    }

    private fun findItemAtPoint(x: Float, y: Float): Int {
        val moduleIndex = INVALID_INDEX
        mModuleRectangles.forEachIndexed { index, rect ->
            if (rect.contains(x.toInt(), y.toInt()))
                return index
        }
        return moduleIndex
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mModuleRectangles.forEachIndexed { index, rect ->
            if (shape == 0) {
                val x = rect.centerX()
                val y = rect.centerY()

                if (modulesStatus[index])
                    canvas!!.drawCircle(x.toFloat() + mOutlineWidth, y.toFloat() + mOutlineWidth, raduis, paintfilled)
                canvas!!.drawCircle(x.toFloat() + mOutlineWidth, y.toFloat() + mOutlineWidth, raduis, paintOutline)
            } else {
                drawSquare(canvas!!, index)
            }
        }
    }

    private fun drawSquare(canvas: Canvas, moduleIndex: Int) {
        val moduleRectangle = mModuleRectangles[moduleIndex]
        if (modulesStatus.get(moduleIndex)) canvas.drawRect(moduleRectangle, paintfilled)
        canvas.drawRect(
            moduleRectangle.left + mOutlineWidth / 2,
            moduleRectangle.top + mOutlineWidth / 2,
            moduleRectangle.right - mOutlineWidth / 2,
            moduleRectangle.bottom - mOutlineWidth / 2,
            paintOutline
        )
    }

    companion object {
        const val INVALID_INDEX = -1
    }
}
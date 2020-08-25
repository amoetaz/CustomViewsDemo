package com.moetaz.customviewsdemo

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


class DualProgressView @JvmOverloads constructor(
    context: Context, attributes: AttributeSet? = null
    , defStyleAttr: Int = 0): View(context, attributes, defStyleAttr) {

    private var mPaint : Paint
    private var mRec : RectF
    private var mNestedRec : RectF
    private var mIndeterminateSweep : Float = 10f
    private var mNestedIndeterminateSweep : Float = 30f
    private var mstartAngle : Float = 0f
    private var mNestedstartAngle : Float = 100f
    private var outerSize = 100f
    private var innerSize = 50f

    init {

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 8f
            strokeCap = Paint.Cap.BUTT

        }
        mRec = RectF(-outerSize, -outerSize, outerSize, outerSize)
        mNestedRec = RectF(-innerSize, -innerSize, innerSize, innerSize)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.translate(outerSize , outerSize)
            it.drawArc(mRec  , mstartAngle, mIndeterminateSweep, false, mPaint)
            it.drawArc(mNestedRec  , mNestedstartAngle, mNestedIndeterminateSweep, false, mPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val xPad = paddingLeft + paddingRight
        val yPad = paddingTop + paddingBottom
        val width = measuredWidth - xPad
        val height = measuredHeight - yPad
        val mSize = if (width < height) width else height

        setMeasuredDimension(mSize + xPad, mSize + yPad)
    }

    private fun animateArch() {
        val frontEndExtend = ValueAnimator.ofFloat(0f, 360f)
        frontEndExtend.duration = 3000
        frontEndExtend.interpolator = LinearInterpolator()
        frontEndExtend.addUpdateListener { animation ->
            mstartAngle = animation.animatedValue as Float
            mIndeterminateSweep += 2f
            if (mIndeterminateSweep > 300) mIndeterminateSweep = 15f

            invalidate()
        }
        frontEndExtend.start()
        frontEndExtend.addListener(object : AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                animateArch()
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

    private fun animateNestedArch() {
        val frontEndExtend = ValueAnimator.ofFloat(0f, 360f)
        frontEndExtend.duration = 1000
        frontEndExtend.interpolator = LinearInterpolator()
        frontEndExtend.addUpdateListener { animation ->
            mNestedstartAngle = animation.animatedValue as Float
            mNestedIndeterminateSweep += 2f
            if (mNestedIndeterminateSweep > 300) mNestedIndeterminateSweep = 15f
            invalidate()
        }
        frontEndExtend.start()
        frontEndExtend.addListener(object : AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                animateNestedArch()
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animateArch()
        animateNestedArch()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

}
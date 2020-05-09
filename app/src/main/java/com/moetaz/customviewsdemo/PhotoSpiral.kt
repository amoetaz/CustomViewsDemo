package com.moetaz.customviewsdemo

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class PhotoSpiral : ViewGroup {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet? , devStylArr : Int) : super(context, attrs , devStylArr) {

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val first = getChildAt(0)
        val childWidth = first.measuredWidth
        val childHeight = first.measuredHeight

        for (i in 0 until childCount){
            val child = getChildAt(i)
            var x = 0
            var y = 0

            when(i){
                1 ->{
                    x = childWidth
                    y = 0
                }

                2 ->{
                    x = childHeight
                    y = childWidth
                }

                3 ->{
                    x = 0
                    y = childHeight
                }
            }
            child.layout(x , y , x + child.measuredWidth , y + child.measuredHeight)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec , heightMeasureSpec)
        val first = getChildAt(0)
        val size = first.measuredWidth + first.measuredHeight

        val width = ViewGroup.resolveSize(size , widthMeasureSpec)
        val height = ViewGroup.resolveSize(size , heightMeasureSpec)

        setMeasuredDimension(width , height)

    }




}
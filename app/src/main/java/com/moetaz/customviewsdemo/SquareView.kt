package com.moetaz.customviewsdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup

class SquareView : ViewGroup{
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
     }

    constructor(context: Context) : super(context) {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }



    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        val infalter = LayoutInflater.from(context)
        infalter.inflate(R.layout.square_view , this)
    }
}
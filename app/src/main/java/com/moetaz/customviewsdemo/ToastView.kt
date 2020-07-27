package com.moetaz.customviewsdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout

class ToastView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init()
    }

    private fun init(){
        val infalter = LayoutInflater.from(context)
        infalter.inflate(R.layout.toast_view_layout, this)
    }
}
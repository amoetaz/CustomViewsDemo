package com.moetaz.customviewsdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class LengthPiker : LinearLayout {

    lateinit var mPlusButton: View
    lateinit var mTextview: TextView
    lateinit var mMinusButton: View
    var mNumInches = 0

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        val infalter = LayoutInflater.from(context)
        infalter.inflate(R.layout.lenght_piker_layout, this)

        mPlusButton = findViewById(R.id.plus_button);
        mTextview = findViewById(R.id.text);
        mMinusButton = findViewById(R.id.minus_button);

        updateControls()
        val listener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                when (v!!.id) {
                    R.id.plus_button -> {
                        mNumInches++;
                        updateControls();
                    }

                    R.id.minus_button -> {
                        if (mNumInches > 0) {
                            mNumInches--;
                            updateControls();
                        }
                    }

                }
            }
        }

        mPlusButton.setOnClickListener(listener)
        mMinusButton.setOnClickListener(listener)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun updateControls() {
        val feet = mNumInches / 12
        val inches = mNumInches % 12

        var text = String.format("%d' %d\"", feet, inches)
        if (feet == 0) {
            text = String.format("%d\"", inches)
        } else {
            if (inches == 0) {
                text = String.format("%d'", feet)
            }
        }
        mTextview.setText(text)

        mMinusButton.isEnabled = mNumInches > 0
    }
}
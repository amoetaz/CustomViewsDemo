package com.moetaz.customviewsdemo

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class LengthPiker : LinearLayout {

    private val KEY_SUPER_STATE: String = "KEY_SUPER_STATE"
    private val KEY_NUM_INCHES: String = "KEY_NUM_INCHES"
    lateinit var mPlusButton: View
    lateinit var mTextview: TextView
    lateinit var mMinusButton: View
    var mNumInches = 0


    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle){
            val bundle : Bundle = state
            mNumInches = bundle.getInt(KEY_NUM_INCHES)
            super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE))
        }else
        super.onRestoreInstanceState(state)
        updateControls()
    }
    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(KEY_SUPER_STATE , super.onSaveInstanceState())
        bundle.putInt(KEY_NUM_INCHES , mNumInches)
        return bundle
    }
    constructor(context: Context) : super(context) {
        init()
    }



    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        val infalter = LayoutInflater.from(context)
        infalter.inflate(R.layout.lenght_piker_layout, this)

        mPlusButton = findViewById(R.id.plus_button);
        mTextview = findViewById(R.id.text);
        mMinusButton = findViewById(R.id.minus_button);

        updateControls()
        val listener = OnClickListener { v ->
            when (v!!.id) {
                R.id.plus_button -> {
                    mNumInches++;
                    updateControls()
                    if (this::inchesListener.isInitialized){
                        inchesListener.onValueChangeLisener(mNumInches)
                    }
                }

                R.id.minus_button -> {
                    if (mNumInches > 0) {
                        mNumInches--
                        updateControls()
                        if (this::inchesListener.isInitialized){
                            inchesListener.onValueChangeLisener(mNumInches)
                        }
                    }
                }

            }
        }

        mPlusButton.setOnClickListener(listener)
        mMinusButton.setOnClickListener(listener)

        orientation = LinearLayout.HORIZONTAL
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

    lateinit var inchesListener: InchesListener

     fun setOnInchesChangeLisener(inchesListener: InchesListener){
        this.inchesListener = inchesListener
    }
    interface InchesListener{
        fun onValueChangeLisener(inches : Int)
    }
}
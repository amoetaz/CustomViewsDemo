package com.moetaz.customviewsdemo

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/*
fun Float.toDP(context : Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    )
}*/

fun String.getOppiset() {

}


fun Float.toPixel(context: Context ): Float =
    this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun Float.toDp(context: Context ): Float =
    this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
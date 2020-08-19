package com.moetaz.customviewsdemo

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
        setPadding(5 , 5 , 5 , 5)

        val textCl = findViewById<ConstraintLayout>(R.id.cl_text)
        val text = findViewById<TextView>(R.id.tv_content)
        val image = findViewById<ImageView>(R.id.image)
        textCl.background = getDrawableShape()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun getDrawableShape() : GradientDrawable{

        val shape = GradientDrawable()
        shape.apply {
            setShape(GradientDrawable.RECTANGLE);
            setColor(Color.GREEN);
            setStroke(1, Color.BLACK);
            cornerRadii = floatArrayOf(50f,50f,50f,50f,10f,10f,10f,10f)
        }
        return shape
    }

    public fun show(){
        visibility = View.VISIBLE
        val animate = ObjectAnimator.ofFloat(this , "alpha" ,1.0f  , 0.0f).apply {
            duration = 2000

            start()
        }

        animate.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                visibility = View.INVISIBLE
             }

            override fun onAnimationCancel(animation: Animator?) {
             }

            override fun onAnimationStart(animation: Animator?) {
             }
        })
    }
}
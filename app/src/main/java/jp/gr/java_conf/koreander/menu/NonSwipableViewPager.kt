package jp.gr.java_conf.koreander.menu

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.ArrayList

class NonSwipableViewPager : ViewPager {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {}

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}
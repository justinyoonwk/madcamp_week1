package com.example.madcamp_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.animation.ValueAnimator
import android.view.MenuItem
import android.animation.Animator
import android.animation.AnimatorListenerAdapter


abstract class BaseActivity : AppCompatActivity() {

    private var instance: BaseActivity? = null
    private var mToolbarHeight = 0
    private var mAnimDuration = 0

    private var mVaActionBar: ValueAnimator? = null
    protected abstract var viewId: Int
    protected abstract var toolbarId: Int?

    protected abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        setContentView(viewId)

        if (toolbarId != null)
            findViewById<androidx.appcompat.widget.Toolbar>(toolbarId!!).let {
                setSupportActionBar(it)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        onCreate()
    }

    fun hideActionBar() {
        toolbarId?.let {
            val mToolbar = findViewById<androidx.appcompat.widget.Toolbar>(toolbarId!!)

            if (mToolbarHeight == 0)
                mToolbarHeight = mToolbar.height

            if (mVaActionBar != null && mVaActionBar!!.isRunning)
                return@let

            mVaActionBar = ValueAnimator.ofInt(mToolbarHeight, 0)
            mVaActionBar?.addUpdateListener { animation ->
                val updateListener = ValueAnimator.AnimatorUpdateListener { animator ->
                    val value = animator.animatedValue as Int
                    mToolbar.layoutParams.height = value
                    mToolbar.requestLayout()
                }
                updateListener.onAnimationUpdate(animation)
            }

            mVaActionBar?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    supportActionBar?.hide()
                }
            })






            mVaActionBar!!.duration = mAnimDuration.toLong()
            mVaActionBar?.start()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
    // <- 버튼 누를 시 뒤로가기

}
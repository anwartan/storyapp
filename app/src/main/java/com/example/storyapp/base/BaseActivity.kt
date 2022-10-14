package com.example.storyapp.base

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.storyapp.R

abstract class BaseActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    private var progressBarLayout :ConstraintLayout? = null

    override fun setContentView(view: View?) {
        super.setContentView(view)
        progressBar=view?.findViewById(R.id.progress_bar)
        progressBarLayout=view?.findViewById(R.id.progress_bar_layout)
    }

    fun showLoading(status:Boolean){
        progressBarLayout?.let {
            if(status){
                progressBar?.visibility=View.VISIBLE
                it.visibility=View.VISIBLE
            }else{
                progressBar?.visibility=View.GONE
                it.visibility=View.GONE
            }
        }
    }

    fun showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}
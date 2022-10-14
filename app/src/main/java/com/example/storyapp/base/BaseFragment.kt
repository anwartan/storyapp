package com.example.storyapp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.storyapp.R

abstract class BaseFragment: Fragment() {
    private var progressBar: ProgressBar? = null
    private var progressBarLayout : ConstraintLayout? = null
    private lateinit var safeContext: Context
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_bar)
        progressBarLayout = view.findViewById(R.id.progress_bar_layout)

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
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
        Toast.makeText(safeContext,message,Toast.LENGTH_SHORT).show()
    }
}
package com.paperkite.test.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.paperkite.test.R
import com.paperkite.test.databinding.ActivityMainBinding
import com.paperkite.test.di.Injection
import com.paperkite.test.model.Cat
import com.paperkite.test.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CatListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mLinearSnapHelper: LinearSnapHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = CatListAdapter(viewModel.catList.value ?: emptyList())
        binding.listview.layoutManager = LinearLayoutManager(this)
        binding.listview.adapter = adapter

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.listview.setLayoutManager(layoutManager)

        mLinearSnapHelper = LinearSnapHelper()
        mLinearSnapHelper.attachToRecyclerView(binding.listview)
        binding.listview.addOnScrollListener(mOnScrollListener)


        setupViewModel()
    }


    var offset = 0
    private val mOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                offset += dx
                val childCount = recyclerView.childCount
                for (i in 0 until childCount) {
                    val child = recyclerView.getChildAt(i) as ConstraintLayout
                    val imageView = child.findViewById<ImageView>(R.id.image01)
                    imageView.visibility = View.GONE
                }
                val view = mLinearSnapHelper.findSnapView(layoutManager)
                val position = recyclerView.getChildAdapterPosition(view!!)
                Log.v("GXL", "position = $position")
                val imageView = view.findViewById<ImageView>(R.id.image01)
                imageView.visibility = View.VISIBLE
                Log.v("GXL", "imageView = $imageView")
            }
        }


    private val viewModel by viewModels<MainViewModel> {
        Injection.provideViewModelFactory()
    }

    private fun setupViewModel() {
        viewModel.catList.observe(this, renderCat)

    }


    //observers
    private val renderCat = Observer<List<Cat>> {
        adapter.update(it)
    }
}
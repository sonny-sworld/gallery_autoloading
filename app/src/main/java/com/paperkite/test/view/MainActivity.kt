package com.paperkite.test.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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

class MainActivity : AppCompatActivity(), CatListAdapter.ImageLoading {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CatListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mLinearSnapHelper: LinearSnapHelper
    private var middleView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBinding()
        initListView()
        initButton()
        setupViewModel()
    }

    private fun setBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initListView() {
        adapter = CatListAdapter(viewModel.catList.value ?: emptyList(), this)
        binding.listview.adapter = adapter
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.listview.layoutManager = layoutManager
        mLinearSnapHelper = LinearSnapHelper()
        mLinearSnapHelper.attachToRecyclerView(binding.listview)
        binding.listview.addOnScrollListener(mOnScrollListener)
    }

    private val viewModel by viewModels<MainViewModel> {
        Injection.provideViewModelFactory()
    }

    private fun setupViewModel() {
        viewModel.catList.observe(this, renderCatObserver)
        viewModel.isViewLoading.observe(this, progressBarObserver)
    }


    //observers
    private val renderCatObserver = Observer<List<Cat>> {
        adapter.update(it)
    }
    private val progressBarObserver = Observer<Boolean> {
        binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
    }


    private val mOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dx == 0) return
                for (i in 0 until recyclerView.childCount) {
                    val child = recyclerView.getChildAt(i) as ConstraintLayout
                    val imageView = child.findViewById<ImageView>(R.id.circle_image)
                    imageView.visibility = View.GONE
                }
                updateSelected()
            }
        }

    private fun updateSelected() {
        middleView = mLinearSnapHelper.findSnapView(layoutManager)

        middleView?.findViewById<ImageView>(R.id.circle_image)?.visibility =
            middleView?.findViewById<ImageView>(R.id.image)?.visibility ?: View.GONE
        Log.v("GXL", "imageView = $middleView")
    }

    private fun initButton() {
        binding.button.setOnClickListener {
            val position:Int = binding.listview.getChildAdapterPosition(middleView!!)
            val builder = AlertDialog.Builder(this)
            val catData  = viewModel.catList.value?.get(position)
            builder.setTitle("ID: ${catData?.id}")
            builder.setMessage("URL: ${catData?.url} \n\n" +
                    "WIDTH: ${catData?.width} \nHEIGHT: ${catData?.height}")
            builder.setPositiveButton("OK", null)
            builder.show()
        }
    }


    override fun finishLoading() {
        updateSelected()
    }
}
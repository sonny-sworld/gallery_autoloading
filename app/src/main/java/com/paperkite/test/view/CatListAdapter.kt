package com.paperkite.test.view

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.paperkite.test.R
import com.paperkite.test.model.Cat


class CatListAdapter(private var cats: List<Cat>, private var callback: ImageLoading) :
    RecyclerView.Adapter<CatListAdapter.MViewHolder>() {

    interface ImageLoading{
        fun finishLoading()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return MViewHolder(view, callback)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        vh.bind(cats[position])
    }

    override fun getItemCount(): Int {
        return cats.size
    }

    fun update(data: List<Cat>) {
        cats = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View ,private var callback: ImageLoading) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.image)
        fun bind(cat: Cat) {
            Glide.with(imageView.context).load(cat.url).addListener(
                object:RequestListener<Drawable?>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback.finishLoading()
                    return false
                }
            }).into(imageView)
        }
    }
}
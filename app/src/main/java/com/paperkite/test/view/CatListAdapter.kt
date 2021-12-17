package com.paperkite.test.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paperkite.test.R
import com.paperkite.test.model.Cat


class CatListAdapter(private var cats: List<Cat>) :
    RecyclerView.Adapter<CatListAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return MViewHolder(view)
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

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.image)
        fun bind(cat: Cat) {
            Glide.with(imageView.context).load(cat.url).into(imageView)
        }
    }
}
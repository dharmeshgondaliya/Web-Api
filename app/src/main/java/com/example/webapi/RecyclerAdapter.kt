package com.example.webapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_tiles.view.*

class RecyclerAdapter(var list: ArrayList<DataModel>): RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        return RecyclerHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_tiles,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(list[position])
    }

    class RecyclerHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(dataModel: DataModel) {
            itemView.nametext.text = dataModel.name
            itemView.mobiletext.text = dataModel.mobile
            itemView.datetext.text = dataModel.date.subSequence(0,10)
            Picasso.get().load("http://10.0.3.2/webexa/image/"+dataModel.image).into(itemView.findViewById(R.id.userimage) as ImageView)
        }
    }
}
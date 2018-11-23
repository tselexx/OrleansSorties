package com.tselexx.orleanssorties.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tselexx.orleanssorties.Common_Util_functions.Companion.contx
import com.tselexx.orleanssorties.R
import com.tselexx.orleanssorties.model.ModelAffItem
import kotlinx.android.synthetic.main.regular_item_liste.view.*
import java.util.*


class ListeAdapter(val modelAffItem: ArrayList<ModelAffItem>,
                   val itemListener  : (ModelAffItem) -> Unit)
      : RecyclerView.Adapter<ListeAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val itemview = LayoutInflater.from(parent.context).inflate(
                R.layout.regular_item_liste,
                parent,
                false
        )
        return MyViewHolder(itemview)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {

        holder.bindItem(modelAffItem[position])

    }

    override fun getItemCount(): Int = modelAffItem.size


    //    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    {
//        private var city = itemView.findViewById<TextView>(R.id.city)
//        private var title =  itemView.findViewById<TextView>(R.id.title)
//        private var placename =  itemView.findViewById<LinearLayout>(R.id.placename)
//        private var address =  itemView.findViewById<LinearLayout>(R.id.address)


        fun bindItem(modelAffItem: ModelAffItem) = with (itemView){
            var imageTemp = modelAffItem.image

            val option = RequestOptions.centerInsideTransform()
            Glide.with(contx!!).load(imageTemp).apply(option).into(image_liste)
            city_liste.setText(modelAffItem.city)
            title_liste.setText(modelAffItem.title)
            setOnClickListener { itemListener(modelAffItem)}
        }
    }
}

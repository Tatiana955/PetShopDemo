package by.petshop.petshopdemo.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import by.petshop.petshopdemo.R
import by.petshop.petshopdemo.RemoteModel.*
import com.squareup.picasso.Picasso
import java.util.*

class ShopAdapter (val list: MutableList<ShopCatalog>, val fragment: ShopFragment,
                   val activity: MainActivity, val arraylist: ArrayList<ShopCatalog>):
        RecyclerView.Adapter<ShopAdapter.MyViewHolder>() {

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.textName)
        var textPhoto = itemView.findViewById<ImageView>(R.id.textPhoto)
        val textPrice = itemView.findViewById<TextView>(R.id.textPrice)
        val ageOrWeightOrSize = itemView.findViewById<TextView>(R.id.ageOrWeightOrSize)
        var ivFavorites = itemView.findViewById<ImageView>(R.id.ivFavorites)
        val ivBasket = itemView.findViewById<ImageView>(R.id.ivBasket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_shop, parent, false)
        val holder = MyViewHolder(itemView)
        holder.itemView.setOnClickListener {
            fragment.onCatalogSelect(holder.adapterPosition)
        }
        holder.ivFavorites.setOnClickListener {
            val position = holder.adapterPosition
            if (!fragment.checkInFavorites(position)){
                fragment.addToFavorites(position)
            } else {
                fragment.deleteFromFavorites(position)
            }
        }
        holder.ivBasket.setOnClickListener {
            val position = holder.adapterPosition
            if (!fragment.checkInBasket(position)){
                fragment.addToBasket(position)
            } else {
                fragment.deleteFromBasket(position)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textName.text = list[position].name
        holder.textPrice.text = list[position].price.toString()
        when {
            list[position] is Pet -> {
                holder.ageOrWeightOrSize.text = (list[position] as Pet).age
            }
            list[position] is Food -> {
                holder.ageOrWeightOrSize.text = (list[position] as Food).weight
            }
            list[position] is Stuff -> {
                holder.ageOrWeightOrSize.text = (list[position] as Stuff).size
            }
            list[position] is PetLitter -> {
                holder.ageOrWeightOrSize.text = (list[position] as PetLitter).weight
            }
        }
        if (fragment.checkInFavorites(position)) {
            holder.ivFavorites.setImageResource(R.drawable.ic_favorites)
        } else {
            holder.ivFavorites.setImageResource(R.drawable.ic_not_favorites)
        }
        if (fragment.checkInBasket(position)) {
            holder.ivBasket.setImageResource(R.drawable.ic_basket)
        } else {
            holder.ivBasket.setImageResource(R.drawable.ic_not_basket)
        }
        if(list[position].imgUrl == null) {
            holder.textPhoto.setImageResource(R.drawable.no_photo)
        } else {
            Picasso.with(activity)
                .load(list[position].imgUrl)
                .error(R.drawable.no_photo)
                .placeholder(R.drawable.no_photo)
                .resize(80, 80)
                .into(holder.textPhoto)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun filter(charText: String) {
        val char = charText.toLowerCase(Locale.getDefault())
        list.clear()
        if (char.isEmpty()) {
            list.addAll(arraylist)
        } else {
            for (i in arraylist) {
                if (i.name!!.toLowerCase(Locale.getDefault()).contains(char)) {
                    list.add(i)
                }
            }
        }
    }
}
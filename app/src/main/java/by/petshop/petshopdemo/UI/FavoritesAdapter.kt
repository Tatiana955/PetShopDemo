package by.petshop.petshopdemo.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.petshop.petshopdemo.R
import by.petshop.petshopdemo.RemoteModel.*
import com.squareup.picasso.Picasso

class FavoritesAdapter (private val list: List<ShopCatalog>, val fragment: FavoritesFragment, val activity: MainActivity):
        RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val textName = itemView.findViewById<TextView>(R.id.textName)
        val textPhoto = itemView.findViewById<ImageView>(R.id.textPhoto)
        val textPrice = itemView.findViewById<TextView>(R.id.textPrice)
        val ageOrWeightOrSize = itemView.findViewById<TextView>(R.id.ageOrWeightOrSize)
        val ivBasket = itemView.findViewById<ImageView>(R.id.ivBasket)
        val ivFavorites = itemView.findViewById<ImageView>(R.id.ivFavorites)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_shop, parent, false)
        val holder = ViewHolder(itemView)
        holder.itemView.setOnClickListener {
            fragment.onCatalogSelect(holder.adapterPosition)
        }
        holder.ivBasket.setOnClickListener {
            val position = holder.adapterPosition
            if (!fragment.checkInBasket(position)){
                fragment.addToBasket(position)
                fragment.deleteFromFavorites(position)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textName.text = list[position].name
        holder.textPrice.text = list[position].price.toString()
        holder.ivFavorites.visibility = View.GONE
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
}
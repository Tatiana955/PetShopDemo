package by.petshop.petshopdemo.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.petshop.petshopdemo.R
import by.petshop.petshopdemo.RemoteModel.ShopCatalog
import by.petshop.petshopdemo.ViewModel.ShopViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var adapter: FavoritesAdapter
    private val list = mutableListOf<ShopCatalog>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shopViewModel = ViewModelProvider(activity as MainActivity).get(ShopViewModel::class.java)
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        shopViewModel.selectFavoritesData()
        list.clear()
        list.addAll(shopViewModel.favorites)

        adapter = FavoritesAdapter(list, this, activity as MainActivity)
        recycleViewFf.adapter = adapter
        recycleViewFf.layoutManager = LinearLayoutManager(activity)

        val callback = FavoritesCallback(this, adapter)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(recycleViewFf)
    }

    fun onCatalogSelect(position: Int){
        shopViewModel.selectedCatalog = list[position]
        navController.navigate(R.id.detailsFragment)
    }

    fun deleteFromFavorites(position: Int){
        shopViewModel.updateFavoritesData(list[position].id, 0)
        shopViewModel.favorites.remove(list[position])
        list.clear()
        list.addAll(shopViewModel.favorites)
        adapter.notifyDataSetChanged()
    }

    fun addToBasket(position: Int){
        shopViewModel.updateBasketData(list[position].id, 1)
        shopViewModel.basket.add(list[position])
        list.clear()
        list.addAll(shopViewModel.favorites)
        adapter.notifyDataSetChanged()
    }

    fun checkInBasket(position: Int): Boolean{
        return shopViewModel.basket.contains(list[position])
    }
}
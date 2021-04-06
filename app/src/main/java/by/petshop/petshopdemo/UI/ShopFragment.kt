package by.petshop.petshopdemo.UI

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.petshop.petshopdemo.R
import by.petshop.petshopdemo.RemoteModel.*
import by.petshop.petshopdemo.ViewModel.ShopViewModel
import by.petshop.petshopdemo.databinding.FragmentShopBinding

class ShopFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var frBinding: FragmentShopBinding? = null
    private lateinit var navController: NavController
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var adapter: ShopAdapter
    private val shopCatalog = mutableListOf<ShopCatalog>()
    private val arrayList = arrayListOf<ShopCatalog>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        shopViewModel = ViewModelProvider(activity as MainActivity).get(ShopViewModel::class.java)
        frBinding = FragmentShopBinding.inflate(inflater, container, false)
        (activity as MainActivity).binding.appbar.visibility = View.VISIBLE
        (activity as MainActivity).binding.navViewBottom.visibility = View.VISIBLE
        return frBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        shopViewModel.getDataVm()
        shopViewModel.selectFavoritesData()
        shopViewModel.selectBasketData()

        shopViewModel.catalogLive.observe(activity as MainActivity, {
            shopCatalog.clear()
            shopCatalog.addAll(it)
            frBinding?.recyclerViewFs?.adapter?.notifyDataSetChanged()
            frBinding?.recyclerViewFs?.visibility = View.VISIBLE
            frBinding?.relativeLayout?.visibility = View.VISIBLE
            frBinding?.progressBarFs?.visibility = View.GONE
            arrayList.clear()
            arrayList.addAll(shopCatalog.toTypedArray())
        })

        adapter = ShopAdapter(shopCatalog, this, activity as MainActivity, arrayList)
        frBinding?.recyclerViewFs?.adapter = adapter
        frBinding?.recyclerViewFs?.layoutManager = LinearLayoutManager(activity)

        ArrayAdapter.createFromResource(activity as MainActivity, R.array.spinner_array, R.layout.spinner_shop
        ).also { adapterArray ->
            adapterArray.setDropDownViewResource(R.layout.spinner_shop_dropdown)
            frBinding?.spinnerFs?.adapter = adapterArray
            frBinding?.spinnerFs?.prompt = "Фильтр"
        }
        frBinding?.spinnerFs?.onItemSelectedListener = this

        frBinding?.searchViewFs?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter(query!!)
                adapter.notifyDataSetChanged()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText!!)
                adapter.notifyDataSetChanged()
                return false
            }
        })
    }

    fun onCatalogSelect(position: Int){
        shopViewModel.selectedCatalog = shopCatalog[position]
        navController.navigate(R.id.detailsFragment)
    }

    fun addToFavorites(position: Int){
        shopViewModel.updateFavoritesData(shopCatalog[position].id, 1)
        shopViewModel.favorites.add(shopCatalog[position])
        adapter.notifyItemChanged(position)
    }

    fun deleteFromFavorites (position: Int){
        shopViewModel.updateFavoritesData(shopCatalog[position].id, 0)
        shopViewModel.favorites.remove(shopCatalog[position])
        adapter.notifyItemChanged(position)
    }

    fun checkInFavorites(position: Int): Boolean {
        return shopViewModel.favorites.contains(shopCatalog[position])
    }

    fun addToBasket(position: Int){
        shopViewModel.updateBasketData(shopCatalog[position].id, 1)
        shopViewModel.basket.add(shopCatalog[position])
        adapter.notifyItemChanged(position)
    }

    fun deleteFromBasket(position: Int){
        shopViewModel.updateBasketData(shopCatalog[position].id, 0)
        shopViewModel.basket.remove(shopCatalog[position])
        adapter.notifyItemChanged(position)
    }

    fun checkInBasket(position: Int): Boolean{
        return shopViewModel.basket.contains(shopCatalog[position])
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            1 -> {
                shopViewModel.filterDataVm("PET")
            }
            2 -> {
                shopViewModel.filterDataVm("FOOD")
            }
            3 -> {
                shopViewModel.filterDataVm("STUFF")
            }
            4 -> {
                shopViewModel.filterDataVm("PET_LITTER")
            }
            0 -> {
                shopViewModel.filterDataVm("ALL")
            }
        }
        frBinding?.recyclerViewFs?.adapter?.notifyDataSetChanged()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onDestroyView() {
        frBinding = null
        super.onDestroyView()
    }
}
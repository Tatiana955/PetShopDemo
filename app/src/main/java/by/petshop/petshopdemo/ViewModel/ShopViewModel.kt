package by.petshop.petshopdemo.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.petshop.petshopdemo.RemoteModel.*
import by.petshop.petshopdemo.Repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopViewModel (val repository: Repository) : ViewModel() {

    val scope = CoroutineScope(Dispatchers.IO)
    val catalogLive: MutableLiveData<MutableList<ShopCatalog>> by lazy {
        MutableLiveData<MutableList<ShopCatalog>>()
    }
    var selectedCatalog: ShopCatalog? = null
    var favorites = mutableSetOf<ShopCatalog>()
    var basket = mutableSetOf<ShopCatalog>()

    fun getDataVm() {
        scope.launch {
            val data = repository.getData()
            catalogLive.postValue(data)
        }
    }

    fun updateFavoritesData(id: Int, isSelected: Int) {
        scope.launch {
            repository.updateFavorites(id, isSelected)
        }
    }

    fun selectFavoritesData() {
        scope.launch {
            val data = repository.selectFavorites()
            favorites.clear()
            favorites.addAll(data.toMutableSet())
        }
    }

    fun updateBasketData(id: Int, isSelected: Int) {
        scope.launch {
            repository.updateBasket(id, isSelected)
        }
    }

    fun selectBasketData() {
        scope.launch {
            val data = repository.selectBasket()
            basket.clear()
            basket.addAll(data.toMutableSet())
        }
    }

    fun filterDataVm(type: String) {
        scope.launch {
            val data = repository.filterData(type)
            catalogLive.postValue(data)
        }
    }
}
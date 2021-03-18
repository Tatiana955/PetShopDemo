package by.petshop.petshopdemo.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.petshop.petshopdemo.Repository.Repository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ShopViewModelFactory @Inject constructor(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShopViewModel(repository) as T
    }
}
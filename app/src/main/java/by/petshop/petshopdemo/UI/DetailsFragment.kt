package by.petshop.petshopdemo.UI

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.petshop.petshopdemo.R
import by.petshop.petshopdemo.RemoteModel.*
import by.petshop.petshopdemo.ViewModel.ShopViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val tInflater = TransitionInflater.from(requireContext())
        enterTransition = tInflater.inflateTransition(R.transition.slide_right)
        exitTransition = tInflater.inflateTransition(R.transition.slide_left)
        shopViewModel = ViewModelProvider(activity as MainActivity).get(ShopViewModel::class.java)
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        shopViewModel.selectedCatalog?.let {
            nameFd.text = it.name
            descriptionFd.text = it.description
            priceFd.text = it.price.toString()
            when (it) {
                is Pet -> {
                    typeOrSizeFd.text = it.petType
                    ageOrNullFd.text = it.age
                    genderOrWeightFd.text = it.gender
                    breedOrConstituentsFd.text = it.breed
                    colorOrNullFd.text = it.color
                }
                is Food -> {
                    typeOrSizeFd.text = it.petType
                    ageOrNullFd.visibility = GONE
                    genderOrWeightFd.text = it.weight
                    breedOrConstituentsFd.text = it.constituents
                    colorOrNullFd.visibility = GONE
                }
                is Stuff -> {
                    typeOrSizeFd.text = it.size
                    ageOrNullFd.visibility = GONE
                    genderOrWeightFd.text = it.maxWeight
                    breedOrConstituentsFd.text = it.material
                    colorOrNullFd.text = it.color
                }
                is PetLitter -> {
                    typeOrSizeFd.text = it.petType
                    ageOrNullFd.visibility = GONE
                    genderOrWeightFd.text = it.weight
                    breedOrConstituentsFd.text = it.constituents
                    colorOrNullFd.visibility = GONE
                }
            }
            Picasso.with(activity)
                    .load(it.imgUrl)
                    .error(R.drawable.no_photo)
                    .placeholder(R.drawable.no_photo)
                    .resize(120, 120)
                    .into(photoFd)

            ivFavoritesFd.setOnClickListener {
                if (!checkInFavorites()){
                    addToFavorites()
                    ivFavoritesFd.setImageResource(R.drawable.ic_favorites)
                } else {
                    deleteFromFavorites()
                    ivFavoritesFd.setImageResource(R.drawable.ic_not_favorites)
                }
            }
            ivBasketFd.setOnClickListener {
                if (!checkInBasket()){
                    addToBasket()
                    ivBasketFd.setImageResource(R.drawable.ic_basket)
                } else {
                    deleteFromBasket()
                    ivBasketFd.setImageResource(R.drawable.ic_not_basket)
                }
            }
        }
    }

    private fun checkInFavorites(): Boolean {
        return shopViewModel.favorites.contains(shopViewModel.selectedCatalog)
    }

    private fun addToFavorites() {
        shopViewModel.updateFavoritesData(shopViewModel.selectedCatalog!!.id, 1)
        shopViewModel.favorites.add(shopViewModel.selectedCatalog!!)
    }

    private fun deleteFromFavorites() {
        shopViewModel.updateFavoritesData(shopViewModel.selectedCatalog!!.id, 0)
        shopViewModel.favorites.remove(shopViewModel.selectedCatalog!!)
    }

    private fun checkInBasket(): Boolean {
        return shopViewModel.basket.contains(shopViewModel.selectedCatalog)
    }

    private fun addToBasket() {
        shopViewModel.updateBasketData(shopViewModel.selectedCatalog!!.id, 1)
        shopViewModel.basket.add(shopViewModel.selectedCatalog!!)
    }

    private fun deleteFromBasket() {
        shopViewModel.updateBasketData(shopViewModel.selectedCatalog!!.id, 0)
        shopViewModel.basket.remove(shopViewModel.selectedCatalog!!)
    }
}
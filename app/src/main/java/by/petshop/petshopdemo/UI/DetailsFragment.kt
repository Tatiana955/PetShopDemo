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
import by.petshop.petshopdemo.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        shopViewModel.selectedCatalog?.let {
            binding.nameFd.text = it.name
            binding.descriptionFd.text = it.description
            binding.priceFd.text = it.price.toString()
            when (it) {
                is Pet -> {
                    binding.typeOrSizeFd.text = it.petType
                    binding.ageOrNullFd.text = it.age
                    binding.genderOrWeightFd.text = it.gender
                    binding.breedOrConstituentsFd.text = it.breed
                    binding.colorOrNullFd.text = it.color
                }
                is Food -> {
                    binding.typeOrSizeFd.text = it.petType
                    binding.ageOrNullFd.visibility = GONE
                    binding.genderOrWeightFd.text = it.weight
                    binding.breedOrConstituentsFd.text = it.constituents
                    binding.colorOrNullFd.visibility = GONE
                }
                is Stuff -> {
                    binding.typeOrSizeFd.text = it.size
                    binding.ageOrNullFd.visibility = GONE
                    binding.genderOrWeightFd.text = it.maxWeight
                    binding.breedOrConstituentsFd.text = it.material
                    binding.colorOrNullFd.text = it.color
                }
                is PetLitter -> {
                    binding.typeOrSizeFd.text = it.petType
                    binding.ageOrNullFd.visibility = GONE
                    binding.genderOrWeightFd.text = it.weight
                    binding.breedOrConstituentsFd.text = it.constituents
                    binding.colorOrNullFd.visibility = GONE
                }
            }
            Picasso.with(activity)
                    .load(it.imgUrl)
                    .error(R.drawable.no_photo)
                    .placeholder(R.drawable.no_photo)
                    .resize(120, 120)
                    .into(binding.photoFd)

            binding.ivFavoritesFd.setOnClickListener {
                if (!checkInFavorites()){
                    addToFavorites()
                    binding.ivFavoritesFd.setImageResource(R.drawable.ic_favorites)
                } else {
                    deleteFromFavorites()
                    binding.ivFavoritesFd.setImageResource(R.drawable.ic_not_favorites)
                }
            }
            binding.ivBasketFd.setOnClickListener {
                if (!checkInBasket()){
                    addToBasket()
                    binding.ivBasketFd.setImageResource(R.drawable.ic_basket)
                } else {
                    deleteFromBasket()
                    binding.ivBasketFd.setImageResource(R.drawable.ic_not_basket)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
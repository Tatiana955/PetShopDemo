package by.petshop.petshopdemo.UI

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.petshop.petshopdemo.R
import by.petshop.petshopdemo.RemoteModel.ShopCatalog
import by.petshop.petshopdemo.ViewModel.ShopViewModel
import by.petshop.petshopdemo.databinding.FragmentBasketBinding

class BasketFragment : Fragment() {
    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var adapter: BasketAdapter
    private val list = mutableListOf<ShopCatalog>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        shopViewModel = ViewModelProvider(activity as MainActivity).get(ShopViewModel::class.java)
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        (activity as MainActivity).binding.appbar.visibility = View.VISIBLE
        (activity as MainActivity).binding.navViewBottom.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        shopViewModel.selectBasketData()
        list.clear()
        list.addAll(shopViewModel.basket)

        adapter = BasketAdapter(list, this, activity as MainActivity)
        binding.recyclerViewFb.adapter = adapter
        binding.recyclerViewFb.layoutManager = LinearLayoutManager(requireContext())

        binding.buttonClick.setOnClickListener {
            val toast = Toast.makeText(context, "Заказ оформлен", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP or Gravity.END, 0, 0)
            toast.show()
        }
    }

    fun onCatalogSelect(position: Int){
        shopViewModel.selectedCatalog = list[position]
        navController.navigate(R.id.detailsFragment)
    }

    fun deleteFromBasket(position: Int){
        shopViewModel.updateBasketData(list[position].id, 0)
        shopViewModel.basket.remove(list[position])
        list.clear()
        list.addAll(shopViewModel.basket)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
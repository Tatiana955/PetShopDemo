package by.petshop.petshopdemo.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import by.petshop.petshopdemo.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.petshop.petshopdemo.LocalModel.LocalModel
import by.petshop.petshopdemo.RemoteModel.RemoteModel
import by.petshop.petshopdemo.Repository.Repository
import by.petshop.petshopdemo.ViewModel.ShopViewModel
import by.petshop.petshopdemo.ViewModel.ShopViewModelFactory
import by.petshop.petshopdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navControllerBottom: NavController
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var shopViewModel: ShopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        
        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.navHost)
        setupActionBarWithNavController(navController, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)

        bottomNavView = findViewById(R.id.nav_view_bottom)
        navControllerBottom = findNavController(R.id.navHost)
        setupActionBarWithNavController(navControllerBottom, binding.drawerLayout)
        bottomNavView.setupWithNavController(navControllerBottom)

        val remoteModel = RemoteModel()
        val localModel = LocalModel(this)
        val repository = Repository(remoteModel, localModel)
        val factory = ShopViewModelFactory(repository)
        shopViewModel = ViewModelProvider(this, factory).get(ShopViewModel::class.java)
        shopViewModel.selectFavoritesData()
        shopViewModel.selectBasketData()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navControllerBottom) ||
                super.onOptionsItemSelected(item)
    }
}
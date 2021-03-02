package by.petshop.petshopdemo.LocalModel

import android.content.Context
import androidx.room.Room
import by.petshop.petshopdemo.RemoteModel.*

class LocalModel (context: Context){

    private val database: ShopDatabase = Room.databaseBuilder(
            context,
            ShopDatabase::class.java, "shop_db"
    ).build()

    suspend fun getPetCatalog(): MutableList<Pet> {
        return database.shopDao().getAllPetCatalog()
    }
    suspend fun getFoodCatalog(): MutableList<Food> {
        return database.shopDao().getAllFoodCatalog()
    }
    suspend fun getStuffCatalog(): MutableList<Stuff> {
        return database.shopDao().getAllStuffCatalog()
    }
    suspend fun getPetLitterCatalog(): MutableList<PetLitter> {
        return database.shopDao().getAllPetLitterCatalog()
    }

    suspend fun insertPetCatalog(petCatalog: MutableList<Pet>) {
        return database.shopDao().insertAllPetCatalog(petCatalog)
    }
    suspend fun insertFoodCatalog(foodCatalog: MutableList<Food>) {
        return database.shopDao().insertAllFoodCatalog(foodCatalog)
    }
    suspend fun insertStuffCatalog(stuffCatalog: MutableList<Stuff>) {
        return database.shopDao().insertAllStuffCatalog(stuffCatalog)
    }
    suspend fun insertPetLitterCatalog(petLitterCatalog: MutableList<PetLitter>) {
        return database.shopDao().insertPetLitterCatalog(petLitterCatalog)
    }

    suspend fun updatePetForFavorites(id: Int, isSelected: Int) {
        database.shopDao().updatePetForFavorites(id, isSelected)
    }
    suspend fun updateFoodForFavorites(id: Int, isSelected: Int) {
        database.shopDao().updateFoodForFavorites(id, isSelected)
    }
    suspend fun updateStuffForFavorites(id: Int, isSelected: Int) {
        database.shopDao().updateStuffForFavorites(id, isSelected)
    }
    suspend fun updatePetLitterForFavorites(id: Int, isSelected: Int) {
        database.shopDao().updatePetLitterForFavorites(id, isSelected)
    }

    suspend fun selectFavoritesOfPet():MutableList<Pet> {
        return database.shopDao().selectFavoritesOfPet()
    }
    suspend fun selectFoodOfFavorites():MutableList<Food> {
        return database.shopDao().selectFavoritesOfFood()
    }
    suspend fun selectFavoritesOfStuff():MutableList<Stuff> {
        return database.shopDao().selectFavoritesOfStuff()
    }
    suspend fun selectFavoritesOfPetLitter():MutableList<PetLitter> {
        return database.shopDao().selectFavoritesOfPetLitter()
    }

    suspend fun updatePetForBasket(id: Int, isSelected: Int) {
        database.shopDao().updatePetForBasket(id, isSelected)
    }
    suspend fun updateFoodForBasket(id: Int, isSelected: Int) {
        database.shopDao().updateFoodForBasket(id, isSelected)
    }
    suspend fun updateStuffForBasket(id: Int, isSelected: Int) {
        database.shopDao().updateStuffForBasket(id, isSelected)
    }
    suspend fun updatePetLitterForBasket(id: Int, isSelected: Int) {
        database.shopDao().updatePetLitterForBasket(id, isSelected)
    }

    suspend fun selectBasketOfPet():MutableList<Pet> {
        return database.shopDao().selectBasketOfPet()
    }
    suspend fun selectBasketOfFood():MutableList<Food> {
        return database.shopDao().selectBasketOfFood()
    }
    suspend fun selectBasketOfStuff():MutableList<Stuff> {
        return database.shopDao().selectBasketOfStuff()
    }
    suspend fun selectBasketOfPetLitter():MutableList<PetLitter> {
        return database.shopDao().selectBasketOfPetLitter()
    }
}
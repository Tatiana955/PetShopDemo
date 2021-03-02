package by.petshop.petshopdemo.LocalModel

import androidx.room.*
import by.petshop.petshopdemo.RemoteModel.*

@Dao
interface ShopDao {

    @Query("SELECT * FROM pet_shopCatalog")
    suspend fun getAllPetCatalog(): MutableList<Pet>
    @Query("SELECT * FROM food_shopCatalog")
    suspend fun getAllFoodCatalog(): MutableList<Food>
    @Query("SELECT * FROM stuff_shopCatalog")
    suspend fun getAllStuffCatalog(): MutableList<Stuff>
    @Query("SELECT * FROM petLitter_shopCatalog")
    suspend fun getAllPetLitterCatalog(): MutableList<PetLitter>

    @Insert
    suspend fun insertAllPetCatalog(petCatalog: MutableList<Pet>)
    @Insert
    suspend fun insertAllFoodCatalog(foodCatalog: MutableList<Food>)
    @Insert
    suspend fun insertAllStuffCatalog(stuffCatalog: MutableList<Stuff>)
    @Insert
    suspend fun insertPetLitterCatalog(petLitterCatalog: MutableList<PetLitter>)

    @Query("UPDATE pet_shopCatalog SET isFavorite = :isSelected WHERE id = :id")
    suspend fun updatePetForFavorites(id:Int, isSelected:Int)
    @Query("UPDATE food_shopCatalog SET isFavorite = :isSelected WHERE id = :id")
    suspend fun updateFoodForFavorites(id:Int, isSelected:Int)
    @Query("UPDATE stuff_shopCatalog SET isFavorite = :isSelected WHERE id = :id")
    suspend fun updateStuffForFavorites(id:Int, isSelected:Int)
    @Query("UPDATE petLitter_shopCatalog SET isFavorite = :isSelected WHERE id = :id")
    suspend fun updatePetLitterForFavorites(id:Int, isSelected:Int)

    @Query("SELECT * FROM pet_shopCatalog WHERE isFavorite = 1")
    suspend fun selectFavoritesOfPet(): MutableList<Pet>
    @Query("SELECT * FROM food_shopCatalog WHERE isFavorite = 1")
    suspend fun selectFavoritesOfFood(): MutableList<Food>
    @Query("SELECT * FROM stuff_shopCatalog WHERE isFavorite = 1")
    suspend fun selectFavoritesOfStuff(): MutableList<Stuff>
    @Query("SELECT * FROM petLitter_shopCatalog WHERE isFavorite = 1")
    suspend fun selectFavoritesOfPetLitter(): MutableList<PetLitter>

    @Query("UPDATE pet_shopCatalog SET isBasket = :isSelected WHERE id = :id")
    suspend fun updatePetForBasket(id:Int, isSelected:Int)
    @Query("UPDATE food_shopCatalog SET isBasket = :isSelected WHERE id = :id")
    suspend fun updateFoodForBasket(id:Int, isSelected:Int)
    @Query("UPDATE stuff_shopCatalog SET isBasket = :isSelected WHERE id = :id")
    suspend fun updateStuffForBasket(id:Int, isSelected:Int)
    @Query("UPDATE petLitter_shopCatalog SET isBasket = :isSelected WHERE id = :id")
    suspend fun updatePetLitterForBasket(id:Int, isSelected:Int)

    @Query("SELECT * FROM pet_shopCatalog WHERE isBasket = 1")
    suspend fun selectBasketOfPet(): MutableList<Pet>
    @Query("SELECT * FROM food_shopCatalog WHERE isBasket = 1")
    suspend fun selectBasketOfFood(): MutableList<Food>
    @Query("SELECT * FROM stuff_shopCatalog WHERE isBasket = 1")
    suspend fun selectBasketOfStuff(): MutableList<Stuff>
    @Query("SELECT * FROM petLitter_shopCatalog WHERE isBasket = 1")
    suspend fun selectBasketOfPetLitter(): MutableList<PetLitter>
}
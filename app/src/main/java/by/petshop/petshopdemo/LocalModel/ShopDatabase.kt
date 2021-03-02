package by.petshop.petshopdemo.LocalModel

import androidx.room.Database
import androidx.room.RoomDatabase
import by.petshop.petshopdemo.RemoteModel.*

@Database(entities = [Pet::class, Food::class, Stuff::class, PetLitter::class], exportSchema = false, version = 1)
abstract class ShopDatabase: RoomDatabase() {
    abstract fun shopDao(): ShopDao
}
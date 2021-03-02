package by.petshop.petshopdemo.RemoteModel

import androidx.room.*

open class ShopCatalog(
        val id: Int,
        val type: String?,
        val name: String?,
        val description: String?,
        val price: Double,
        val count: Int,
        val imgUrl: String?,
        val entrance: String?,
        val importer: String?
        )

@Entity(tableName = "pet_shopCatalog")
open class Pet(
        id : Int,
        type: String?,
        name: String?,
        description: String?,
        price: Double,
        count: Int,
        imgUrl: String?,
        entrance: String?,
        importer: String?,
        var isFavorite: Int = 0,
        var isBasket: Int = 0,
        val petType: String?,
        val age: String?,
        val gender: String?,
        val color: String?,
        val breed: String?,
        @PrimaryKey (autoGenerate = true)
        val prKey: Int
        ): ShopCatalog(id, type, name, description, price, count, imgUrl, entrance, importer)

@Entity(tableName = "food_shopCatalog")
open class Food(
        id : Int,
        type: String?,
        name: String?,
        description: String?,
        price: Double,
        count: Int,
        imgUrl: String?,
        entrance: String?,
        importer: String?,
        var isFavorite: Int = 0,
        var isBasket: Int = 0,
        val expiration: String?,
        val petType: String?,
        val weight: String?,
        val constituents: String?,
        @PrimaryKey (autoGenerate = true)
        val prKey: Int
        ): ShopCatalog(id, type, name, description, price, count, imgUrl, entrance, importer)

@Entity(tableName = "stuff_shopCatalog")
open class Stuff(
        id : Int,
        type: String?,
        name: String?,
        description: String?,
        price: Double,
        count: Int,
        imgUrl: String?,
        entrance: String?,
        importer: String?,
        var isFavorite: Int = 0,
        var isBasket: Int = 0,
        val size: String?,
        val material: String?,
        val color: String?,
        val maxWeight: String?,
        @PrimaryKey (autoGenerate = true)
        val prKey: Int
        ): ShopCatalog(id, type, name, description, price, count, imgUrl, entrance, importer)

@Entity(tableName = "petLitter_shopCatalog")
open class PetLitter(
        id : Int,
        type: String?,
        name: String?,
        description: String?,
        price: Double,
        count: Int,
        imgUrl: String?,
        entrance: String?,
        importer: String?,
        var isFavorite: Int = 0,
        var isBasket: Int = 0,
        val petType: String?,
        val weight: String?,
        val constituents: String?,
        @PrimaryKey (autoGenerate = true)
        val prKey: Int
        ): ShopCatalog(id, type, name, description, price, count, imgUrl, entrance, importer)
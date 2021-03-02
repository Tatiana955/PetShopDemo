package by.petshop.petshopdemo.Repository

import by.petshop.petshopdemo.LocalModel.LocalModel
import by.petshop.petshopdemo.RemoteModel.*

class Repository (val remoteModel: RemoteModel, val localModel: LocalModel) {

    suspend fun getData(): MutableList<ShopCatalog> {
        val catalog = mutableListOf<ShopCatalog>()
        val petList = localModel.getPetCatalog()
        val foodList = localModel.getFoodCatalog()
        val stuffList = localModel.getStuffCatalog()
        val petLitterList = localModel.getPetLitterCatalog()
        return if (petList.isEmpty() || foodList.isEmpty() || stuffList.isEmpty() || petLitterList.isEmpty()) {
            val list = remoteModel.getRemoteData()
            for (i in list) {
                if (i.type == "PET") {
                    petList.addAll(listOf(i as Pet))
                }
                if (i.type == "FOOD") {
                    foodList.addAll(listOf(i as Food))
                }
                if (i.type == "STUFF") {
                    stuffList.addAll(listOf(i as Stuff))
                }
                if (i.type == "PET_LITTER") {
                    petLitterList.addAll(listOf(i as PetLitter))
                }
            }
            localModel.insertPetCatalog(petList)
            localModel.insertFoodCatalog(foodList)
            localModel.insertStuffCatalog(stuffList)
            localModel.insertPetLitterCatalog(petLitterList)
            list
        } else {
            catalog.addAll(petList.plus(foodList).plus(stuffList).plus(petLitterList))
            catalog
        }
    }

    suspend fun updateFavorites(id: Int, isSelected: Int) {
        val data = mutableListOf<ShopCatalog>()
        val petList = localModel.getPetCatalog()
        val foodList = localModel.getFoodCatalog()
        val stuffList = localModel.getStuffCatalog()
        val petLitterList = localModel.getPetLitterCatalog()
        data.addAll(petList.plus(foodList).plus(stuffList).plus(petLitterList))
        for (i in data) {
            if (i.type == "PET") {
                localModel.updatePetForFavorites(id, isSelected)
            }
            if (i.type == "FOOD") {
                localModel.updateFoodForFavorites(id, isSelected)
            }
            if (i.type == "STUFF") {
                localModel.updateStuffForFavorites(id, isSelected)
            }
            if (i.type == "PET_LITTER") {
                localModel.updatePetLitterForFavorites(id, isSelected)
            }
        }
    }

    suspend fun selectFavorites(): MutableList<ShopCatalog> {
        val favorite = mutableListOf<ShopCatalog>()

        val pet = localModel.selectFavoritesOfPet()
        favorite.addAll(pet)

        val food = localModel.selectFoodOfFavorites()
        favorite.addAll(food)

        val stuff = localModel.selectFavoritesOfStuff()
        favorite.addAll(stuff)

        val petLitter = localModel.selectFavoritesOfPetLitter()
        favorite.addAll(petLitter)

        return favorite
    }

    suspend fun updateBasket(id: Int, isSelected: Int) {
        val data = mutableListOf<ShopCatalog>()
        val petList = localModel.getPetCatalog()
        val foodList = localModel.getFoodCatalog()
        val stuffList = localModel.getStuffCatalog()
        val petLitterList = localModel.getPetLitterCatalog()
        data.addAll(petList.plus(foodList).plus(stuffList).plus(petLitterList))
        for (i in data) {
            if (i.type == "PET") {
                localModel.updatePetForBasket(id, isSelected)
            }
            if (i.type == "FOOD") {
                localModel.updateFoodForBasket(id, isSelected)
            }
            if (i.type == "STUFF") {
                localModel.updateStuffForBasket(id, isSelected)
            }
            if (i.type == "PET_LITTER") {
                localModel.updatePetLitterForBasket(id, isSelected)
            }
        }
    }

    suspend fun selectBasket(): MutableList<ShopCatalog> {
        val basket = mutableListOf<ShopCatalog>()

        val pet = localModel.selectBasketOfPet()
        basket.addAll(pet)

        val food = localModel.selectBasketOfFood()
        basket.addAll(food)

        val stuff = localModel.selectBasketOfStuff()
        basket.addAll(stuff)

        val petLitter = localModel.selectBasketOfPetLitter()
        basket.addAll(petLitter)

        return basket
    }

    suspend fun filterData(type: String): MutableList<ShopCatalog> {
        val list = mutableListOf<ShopCatalog>()
        val petList = localModel.getPetCatalog()
        val foodList = localModel.getFoodCatalog()
        val stuffList = localModel.getStuffCatalog()
        val petLitterList = localModel.getPetLitterCatalog()
        when (type) {
            "PET" -> {
                list.clear()
                list.addAll(petList)
            }
            "FOOD" -> {
                list.clear()
                list.addAll(foodList)
            }
            "STUFF" -> {
                list.clear()
                list.addAll(stuffList)
            }
            "PET_LITTER" -> {
                list.clear()
                list.addAll(petLitterList)
            }
            "ALL" -> {
                list.clear()
                list.addAll(petList.plus(foodList).plus(stuffList).plus(petLitterList))
            }
        }
        return list
    }
}
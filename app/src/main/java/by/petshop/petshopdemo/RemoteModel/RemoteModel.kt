package by.petshop.petshopdemo.RemoteModel

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class RemoteModel {
    val shopApiService = ShopApiService.create()
    val mapper = jacksonObjectMapper()

    suspend fun getRemoteData(): MutableList<ShopCatalog>{
        return try {
            val catalog = shopApiService.getCatalog()
            val list = mutableListOf<ShopCatalog>()
            for (i in catalog) {
                when {
                    i["type"].toString() == "PET" -> {
                        list.add(mapper.convertValue(i, Pet::class.java))
                    }
                    i["type"].toString() == "FOOD" -> {
                        list.add(mapper.convertValue(i, Food::class.java))
                    }
                    i["type"].toString() == "STUFF" -> {
                        list.add(mapper.convertValue(i, Stuff::class.java))
                    }
                    i["type"].toString() == "PET_LITTER" -> {
                        list.add(mapper.convertValue(i, PetLitter::class.java))
                    }
                }
            }
            list
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}
package com.samueljuma.petapp.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext


class PetsRepositoryImpl (
    private val catsAPI: CatsAPI,
    private val dispatcher: CoroutineDispatcher,
    private val catDao: CatDao
): PetsRepository {
    override suspend fun getPets(): Flow<List<Cat>> {

        //withContext(dispatcher) helps to switch the context of the coroutine
        return withContext(dispatcher){
            catDao.getCats()
                .map { petsCached ->
                    petsCached.map { catEntity ->
                        Cat(
                            id = catEntity.id,
                            owner = catEntity.owner,
                            tags = catEntity.tags,
                            createdAt = catEntity.createdAt,
                            updatedAt = catEntity.updatedAt
                        )

                    }

                }.onEach {
                    if (it.isEmpty()) {
                        fetchRemotePets()
                    }
                }
        }
    }

    override suspend fun fetchRemotePets() {
        withContext(dispatcher){
            val response = catsAPI.fetchCats("cute")
            if(response.isSuccessful){
                response.body()!!.map {
                    catDao.insert(CatEntity(
                        id = it.id,
                        owner = it.owner,
                        tags = it.tags,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt
                    ))
                }
            }
        }
    }


}
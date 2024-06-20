package com.samueljuma.petapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CatEntity::class], version = 1)
@TypeConverters(PetsTypeConverters::class)
abstract class CatDatabase: RoomDatabase(){
    abstract fun catDao(): CatDao
}
package com.samueljuma.petapp.data

class PetsRepositoryImpl: PetsRepository {
    override fun getPets(): List<Pet> {
        return listOf(
            Pet(1, "Fluffy", "Cat"),
            Pet(2, "Spot", "Dog"),
            Pet(3, "Whiskers", "Cat"),
            Pet(4, "Bella", "Dog"),
            Pet(5, "Max", "Cat"),
            Pet(6, "Charlie", "Dog"),
            Pet(7, "Buddy", "Cat"),
            Pet(8, "Chloe", "Dog"),
            Pet(9, "Milo", "Cat"),
            Pet(10, "Rocky", "Dog"),
            Pet(11, "Simba", "Cat"),
            Pet(12, "Lola", "Dog"),
            Pet(13, "Chloe", "Cat"),
            Pet(14, "Milo", "Dog"),
            Pet(15, "Rocky", "Cat"),
            Pet(16, "Simba", "Dog"),
            Pet(17, "Lola", "Cat"),
            Pet(18, "Chloe", "Dog"),
            Pet(19, "Milo", "Cat"),
            Pet(20, "Rocky", "Dog"),
            Pet(21, "Simba", "Cat"),
            Pet(22, "Lola", "Dog"),
            Pet(23, "Chloe", "Cat"),
            Pet(24, "Milo", "Dog"),
            Pet(25, "Rocky", "Cat"),
            Pet(26, "Simba", "Dog"),
            Pet(27, "Lola", "Cat"),
            Pet(28, "Chloe", "Dog"),
            Pet(29, "Milo", "Cat"),
            Pet(30, "Rocky", "Dog"),
        )
    }

}
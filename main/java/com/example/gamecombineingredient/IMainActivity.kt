package com.example.gamecombineingredient

interface IMainActivity {
    fun clearAll()
    fun addBun(bun: Bun)
    fun addBurger(burger: Burger)
    fun addMeat(meat: Meat)
    fun addCustomer(customer: Customer)
    fun addSausage(sausage: Sausage)
    fun addHotdog(hotdog: Hotdog)
}
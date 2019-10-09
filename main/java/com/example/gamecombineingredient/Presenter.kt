package com.example.gamecombineingredient

import java.util.*
import kotlin.collections.ArrayList

class Presenter (mView : IMainActivity){
    var arrMeat : LinkedList<Meat>
    var arrBun : LinkedList<Bun>
    var arrBurger : LinkedList<Burger>
    var arrHotdog : LinkedList<Hotdog>
    var arrCustomer : LinkedList<Customer>
    var arrSausage : LinkedList<Sausage>
    init{
        this.arrMeat = LinkedList()
        this.arrBun = LinkedList()
        this.arrBurger = LinkedList()
        this.arrCustomer = LinkedList()
        this.arrSausage = LinkedList()
        this.arrHotdog = LinkedList()
    }

    fun clear(){
        this.arrBun.clear()
        this.arrMeat.clear()
        this.arrBurger.clear()
        this.arrCustomer.clear()
        this.arrSausage.clear()
        this.arrHotdog.clear()
    }

    fun addMeat(meat:Meat){
        this.arrMeat.add(meat)
    }

    fun addBun(bun:Bun){
        this.arrBun.add(bun)
    }

    fun addBurger(burger:Burger){
        this.arrBurger.add(burger)
    }

    fun addCustomer(customer: Customer){
        this.arrCustomer.add(customer)
    }

    fun addSausage(sausage: Sausage){
        this.arrSausage.add(sausage)
    }

    fun addHotdog(hotdog: Hotdog){
        this.arrHotdog.add(hotdog)
    }

}
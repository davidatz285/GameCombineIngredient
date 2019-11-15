package com.example.gamecombineingredient

import java.util.*

class Presenter(mView: InterfaceGameActivity) {
    var arrMeat: LinkedList<Meat>
    var arrBun: LinkedList<Bun>
    var arrBurger: LinkedList<Burger>
    var arrHotdog: LinkedList<Hotdog>
    var arrCustomer: LinkedList<Customer>
    var arrSausage: LinkedList<Sausage>
    var positionBun: LinkedList<Position>
    var positionMeat: LinkedList<Position>
    var positionSausage: LinkedList<Position>
    var positionBunFilled: BooleanArray
    var positionMeatFilled: BooleanArray
    var positionSausageFilled: BooleanArray
    var positionCustomer : LinkedList<Position>

    init {
        this.arrMeat = LinkedList()
        this.arrBun = LinkedList()
        this.arrBurger = LinkedList()
        this.arrCustomer = LinkedList()
        this.arrSausage = LinkedList()
        this.arrHotdog = LinkedList()
        this.positionBun = LinkedList()
        this.positionMeat = LinkedList()
        this.positionSausage = LinkedList()
        this.positionCustomer = LinkedList()
        this.positionBunFilled = booleanArrayOf(false, false, false)
        this.positionMeatFilled = booleanArrayOf(false, false, false)
        this.positionSausageFilled = booleanArrayOf(false, false, false)

    }

    fun clear() {
        this.arrBun.clear()
        this.arrMeat.clear()
        this.arrBurger.clear()
        this.arrCustomer.clear()
        this.arrSausage.clear()
        this.arrHotdog.clear()
    }

    fun addMeat(meat: Meat) {
        this.arrMeat.add(meat)
    }

    fun addBun(bun: Bun) {
        this.arrBun.add(bun)
    }

    fun addBurger(burger: Burger) {
        this.arrBurger.add(burger)
    }

    fun addCustomer(customer: Customer) {
        this.arrCustomer.add(customer)
    }

    fun addSausage(sausage: Sausage) {
        this.arrSausage.add(sausage)
    }

    fun addHotdog(hotdog: Hotdog) {
        this.arrHotdog.add(hotdog)
    }

    fun getTouchedFood(xTouch: Int, yTouch: Int): Food {
        var active : Food = Food(0,0,0,0)
        for(item in arrBun){
            if(item.rect.contains(xTouch,yTouch)){
                active = item
                break
            }
        }
        for(item in arrMeat){
            if(item.rect.contains(xTouch,yTouch)){
                active = item
                break
            }
        }
        for(item in arrSausage){
            if(item.rect.contains(xTouch,yTouch)){
                active = item
                break
            }
        }
        for(item in arrBurger){
            if(item.rect.contains(xTouch,yTouch)){
                active = item
                break
            }
        }
        for(item in arrHotdog){
            if(item.rect.contains(xTouch,yTouch)){
                active = item
                break
            }
        }
        return active
    }

}
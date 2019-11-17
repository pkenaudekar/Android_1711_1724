package com.example.emenu.data

class MenuItem {
    var id: String? = null
    var menuName: String? = null
    var price: Double? = null

    constructor() {}

    constructor(id: String, menuName: String, price: Double) {
        this.id = id
        this.menuName = menuName
        this.price = price
    }

    constructor(menuName: String, price: Double) {
        this.menuName = menuName
        this.price = price
    }

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put("Menu Item", menuName!!)
        result.put("Price", price!!)

        return result
    }
}
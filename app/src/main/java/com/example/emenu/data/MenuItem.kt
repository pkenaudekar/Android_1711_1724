package com.example.emenu.data

class MenuItem {
    var id: String? = null
    var menuName: String? = null
    var price: String? = null

    constructor() {}

    constructor(id: String, menuName: String, price: String) {
        this.id = id
        this.menuName = menuName
        this.price = price
    }

    constructor(menuName: String, price: String) {
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
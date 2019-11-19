package com.example.emenu.data

data class MenuItem(
    var id: String? ="",
    var menuName: String?="",
    var menuPrice: String = "",
    var menuDesc: String? ="",
    var imageUrl: String? = ""
    ){


    fun toMap(): HashMap<String, Any> {
        val result = HashMap<String, Any>()
        result["menuItem"] = menuName!!
        result["menuPrice"] = menuPrice
        result["menuDesc"] = menuDesc!!
        result["imageUrl"] = imageUrl!!

        return result
    }
}
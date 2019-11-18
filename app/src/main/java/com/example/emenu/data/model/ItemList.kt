package com.example.emenu.data.model

//data class ItemList(val username: String = "", val name: String, val password: String, val phoneNo: String,val accType: String, val itemNumber: Number)
import java.util.HashMap

class ItemList {
    var id: String? = null
    var username: String?=null
    var name: String?=null
    var password: String?=null
    var phoneNo: String?=null
    var accType: String?=null
    var itemNumber:Number?=null

    constructor(){}

    constructor(id:String, username: String, name: String, password: String, phoneNo: String, accType: String, itemNumber: Number) {
        this.id=id
        this.username = username
        this.name = name
        this.password = password
        this.phoneNo = phoneNo
        this.accType = accType
        this.itemNumber = itemNumber
    }
    constructor(username: String, name: String, password: String, phoneNo: String, accType: String, itemNumber: Number) {
        this.username = username
        this.name = name
        this.password = password
        this.phoneNo = phoneNo
        this.accType = accType
        this.itemNumber = itemNumber
    }

    fun toMap(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result.put("username", username!!)
        result.put("name", name!!)
        result.put("password", password!!)
        result.put("phoneNo", phoneNo!!)
        result.put("accType", accType!!)
        result.put("itemNumber", itemNumber!!)
        return result
    }
}
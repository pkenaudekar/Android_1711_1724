package com.example.emenu.data.model


import java.util.HashMap

class ItemList {
    var id: String? = null
    var username: String?= null
    var name: String?=null
    var password: String?=null
    var phoneNo: String?=null
    var accType: String?=null
    var itemNumber:String?=null

    constructor(){}

    constructor(id:String, username: String, name: String, password: String, phoneNo: String, accType: String, itemNumber: String) {
        this.id=id
        this.username = username
        this.name = name
        this.password = password
        this.phoneNo = phoneNo
        this.accType = accType
        this.itemNumber = itemNumber
    }
    constructor(username: String, name: String, password: String, phoneNo: String, accType: String, itemNumber: String) {
        this.username = username
        this.name = name
        this.password = password
        this.phoneNo = phoneNo
        this.accType = accType
        this.itemNumber = itemNumber
    }

    fun toMap(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result["username"] = username!!
        result["name"] = name!!
        result["password"] = password!!
        result["phoneNo"] = phoneNo!!
        result["accType"] = accType!!
        result["itemNumber"] = itemNumber!!
        return result
    }
}
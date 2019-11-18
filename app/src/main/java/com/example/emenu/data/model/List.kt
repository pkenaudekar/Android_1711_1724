package com.example.emenu.data.model

class List {
    constructor(username: String?, name: String?, password: String?, phoneNo: Number?, accType: String?, itemNumber: Int) {
        this.username = username
        this.name = name
        this.password = password
        this.phoneNo = phoneNo
        this.accType = accType
        this.itemNumber = itemNumber
    }

    private var username: String? = null
        private get() {return username}
    private var name: String? = null
        private get() {return name}
    private var password: String? = null
        private get() {return password}
    private var phoneNo: Number? = null
        private get() {return phoneNo}
    private var accType: String? = null
        private get() {return accType}
    private var itemNumber: Int? = null
        private get() {return itemNumber}
}
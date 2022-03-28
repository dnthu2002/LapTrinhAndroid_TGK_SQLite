package com.example.personal_in4

import java.util.*


data class PersonalModel(
    var id : Int = getautoID(),
    var name : String="",
    var email: String="",
    var address: String="",
    var phone: String=""
) {
    companion object{
        fun getautoID():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }

}
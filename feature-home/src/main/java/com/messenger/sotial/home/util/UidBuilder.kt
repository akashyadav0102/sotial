package com.messenger.sotial.home.util

fun UidBuilder (uid1 : String, uid2 : String) : String{
    return if (uid1 < uid2)
        uid1.plus(uid2)
    else{
        uid2.plus(uid1)
    }
}
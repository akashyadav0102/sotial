package com.messenger.sotial.firestoreservice

import com.google.firebase.auth.FirebaseAuth

class FirebaseUser {

    companion object{
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val userName = FirebaseAuth.getInstance().currentUser?.displayName
    }

}
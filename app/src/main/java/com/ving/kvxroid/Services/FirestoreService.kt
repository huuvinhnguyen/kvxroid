package com.ving.kvxroid.Services

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService {

    private val db = FirebaseFirestore.getInstance()

    inner class Item {

        val imageUrl: String? = null
    }

    fun getItems(finished: (List<Item> ) -> Unit) {
        val items = db.collection("items")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    print("#abcd")
                    document.get("aaa")
                    print("${document.id} => ${document.data}")
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                    print("Error 911")
//                print("Error getting documents: ", exception)
//                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}
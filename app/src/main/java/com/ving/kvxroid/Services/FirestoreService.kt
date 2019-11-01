package com.ving.kvxroid.Services

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService {

    private val db = FirebaseFirestore.getInstance()

    data class Item(
        var id: String = "",
        var name: String = "",
        var imageUrl: String = ""
    )

    data class Collection (
        var items: ArrayList<Item> = arrayListOf()

    )

    fun getItems(finished: (ArrayList<Item> ) -> Unit) {
        val items = db.collection("items").document("list")
            .addSnapshotListener()
             { result, e ->

                val collection = result?.toObject(Collection::class.java) ?: Collection()

                collection.items.forEach {
                    println(it.name)
                }

                finished(collection.items)
            }


    }
}
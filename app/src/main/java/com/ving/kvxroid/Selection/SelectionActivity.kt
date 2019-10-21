package com.ving.kvxroid.Selection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.R
import kotlinx.android.synthetic.main.activity_item_detail.*

import io.realm.Realm
import io.realm.kotlin.createObject


class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        initView()
    }

    private fun initView() {


        recyclerView.layoutManager =  LinearLayoutManager(this@SelectionActivity)


        val itemListAdapter = SelectionAdapter(ArrayList()).apply {

        }

        recyclerView.adapter = itemListAdapter

        Realm.init(this@SelectionActivity)

        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { realm ->
            realm.deleteAll()
        }

        basicCRUD(realm)




//        val dog = Dog()


//        // Use them like regular java objects
//        //
//        Dog dog = new Dog();
//        dog.setName("Rex");
//        dog.setAge(1);




        itemListAdapter.setItems()
    }

    @Suppress("NAME_SHADOWING")
    private fun basicCRUD(realm: Realm) {

        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.executeTransaction { realm ->
            // Add a person
            val person = realm.createObject(Person::class.java, "fsdfdsfs".toString())
        }

        // Find the first person (no query conditions) and read a field
//        val person = realm.where<Person>("gogl").findFirst()!!

//        // Update person in a transaction
//        realm.executeTransaction { _ ->
//            person.name = "Senior Person"
//        }

//        val results = realm.where<Person>().equalTo("", "").findAll()

//        var guest: Person = realm.createObject(Person::class.java, UUID.randomUUID().toString())
         var list = realm.where(Person::class.java).findAll()
        list.forEach { person ->

            println(person)
        }
//          User user = realm.createObject(User.class, UUID.randomUUID().toString());
//           user.setFirstName("Theo");
//           user.setLastName("Larsen");

    }

}

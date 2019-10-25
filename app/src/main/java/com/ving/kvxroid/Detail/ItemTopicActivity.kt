package com.ving.kvxroid.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.ConnectionActionAdd
import com.ving.kvxroid.Redux.ConnectionActionLoad
import com.ving.kvxroid.Redux.TopicActionAdd
import com.ving.kvxroid.Redux.mainStore
import com.ving.kvxroid.Selection.SelectionActivity
import com.ving.kvxroid.Selection.ServerRealm
import com.ving.kvxroid.setOnSafeClickListener
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_item_topic.*
import java.util.*

class ItemTopicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_topic)
        btnConnect.setOnSafeClickListener {
            val text = "Connect fails!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.LEFT, 0, 0)

            toast.show()

            mainStore.dispatch(ConnectionActionAdd())


        }

        btnSelect.setOnSafeClickListener {
            val intent = Intent(this, SelectionActivity::class.java)
            startActivity(intent)
        }

        btnTopic.setOnSafeClickListener {
            mainStore.dispatch(TopicActionAdd())

        }


    }
}

package com.ving.kvxroid.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.ving.kvxroid.R
import com.ving.kvxroid.Selection.SelectionActivity
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.synthetic.main.activity_item_topic.*

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
        }

        btnSelect.setOnSafeClickListener {
            val intent = Intent(this, SelectionActivity::class.java)
            startActivity(intent)
        }
    }
}

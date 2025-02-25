package com.example.habit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    private val adapter = HabitAdapter(this)
    private var editLauncher: ActivityResultLauncher<Intent>? = null
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val sP = sharedPreferences.all
        for ((key) in sP) {
            var ass = HabitCard(key, sP[key].toString())
            adapter.addCard(ass as HabitCard)
        }
        init()

        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                adapter.addCard(it.data?.getSerializableExtra("habit") as HabitCard)
            }


        }

    }

    override fun onResume() {

        super.onResume()
        adapter.clearItems()
        val sP = sharedPreferences.all
        for ((key) in sP) {
            var ass = HabitCard(key, sP[key].toString())
            adapter.addCard(ass as HabitCard)
        }

    }

    override fun onPause() {
        super.onPause()



    }

    private fun init() {
        bindingClass.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
            addHabitButton2.setOnClickListener {
                editLauncher?.launch(Intent(this@MainActivity, EditActivity::class.java))


            }

        }

    }
}
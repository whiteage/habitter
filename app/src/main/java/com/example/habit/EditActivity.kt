package com.example.habit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habit.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        editor = sharedPreferences.edit()


        val title = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        binding.editName.setText(title)
        binding.editDesc.setText(desc)


        initButtons()



    }


    private fun initButtons() = with(binding){
        val habitName = editName.text.toString()
        saveButton.setOnClickListener {
                    val card = HabitCard(editName.text.toString(), editDesc.text.toString())
                    editor.putString(editName.text.toString(), editDesc.text.toString())
                    editor.apply()
                    val editIntent = Intent().apply {
                        putExtra("habit", card)
                    }
                    setResult(RESULT_OK, editIntent)
                    finish()
        }



        buttonBack.setOnClickListener {
            finish()

        }
    }
}

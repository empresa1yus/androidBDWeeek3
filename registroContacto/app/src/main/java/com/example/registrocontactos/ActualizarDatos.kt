package com.example.registrocontactos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActualizarDatos : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var dbHelper: DatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actualizar_datos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameEditText = findViewById(R.id.nameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        updateButton = findViewById(R.id.updateButton)
        dbHelper = DatabaseHelper(this)

        contactId = intent.getIntExtra("CONTACT_ID", -1)

        if (contactId != -1) {
            val contact = dbHelper.getAllContacts().find { it.id == contactId }
            if (contact != null) {
                nameEditText.setText(contact.nombreUsuario)
                phoneEditText.setText(contact.numeroCelular)
            }
        }

        updateButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val contact = DataClas(contactId, name, phone)
                if (dbHelper.updateContact(contactId, contact)) {
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar el contacto", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
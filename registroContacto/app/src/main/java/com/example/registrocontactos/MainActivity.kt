package com.example.registrocontactos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var contactsListView: ListView
    private lateinit var addButton: Button
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var dbHelper: DatabaseHelper
    private var selectedContactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        contactsListView = findViewById(R.id.contactsListView)
        addButton = findViewById(R.id.addButton)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)
        dbHelper = DatabaseHelper(this)

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadContacts()

        contactsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedContact = contactsListView.getItemAtPosition(position) as DataClas
            selectedContactId = selectedContact.id
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivityForResult(intent, ADD_CONTACT_REQUEST)
        }

        updateButton.setOnClickListener {
            if (selectedContactId == -1) {
                Toast.makeText(this, "Seleccione un contacto", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, UpdateContactActivity::class.java).apply {
                    putExtra("CONTACT_ID", selectedContactId)
                }
                startActivityForResult(intent, UPDATE_CONTACT_REQUEST)
            }
        }

        deleteButton.setOnClickListener {
            if (selectedContactId == -1) {
                Toast.makeText(this, "Seleccione un contacto", Toast.LENGTH_SHORT).show()
            } else {
                if (dbHelper.deleteContact(selectedContactId)) {
                    Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show()
                    loadContacts()
                    selectedContactId = -1
                } else {
                    Toast.makeText(this, "Error al eliminar el contacto", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun loadContacts() {
        val contacts = dbHelper.getAllContacts()
        val adapter = ContactAdapter(this, contacts)
        contactsListView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            loadContacts()
        }
    }

    companion object {
        const val ADD_CONTACT_REQUEST = 1
        const val UPDATE_CONTACT_REQUEST = 2
    }
}
package com.example.registrocontactos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "contacts.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "contactos"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
    }

    override fun onCreate(bd: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $COLUMN_NAME TEXT, 
                $COLUMN_PHONE TEXT)""".trimIndent()

        bd?.execSQL(createTableQuery)
    }

    override fun onUpgrade(bd: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery =
            "DROP TABLE IF EXISTS $TABLE_NAME"
        bd?.execSQL(dropTableQuery)

        onCreate(bd)

    }

    //insertar contactos
    fun insertContact(dataClas: DataClas):Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, dataClas.nombreUsuario)
            put(COLUMN_PHONE, dataClas.numeroCelular)
        }

        val result = db.insert(TABLE_NAME, null, values)
        db.close()  // Cierra la base de datos

        return result != -1L
    }

    //Método para actualizar
    fun updateContact(id: Int, dataClas: DataClas): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, dataClas.nombreUsuario)
            put(COLUMN_PHONE, dataClas.numeroCelular)
        }

        val result = db.update(TABLE_NAME, values, "id=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    //Método para eliminar
    fun deleteContact(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    //Método para poder ver los contactos
    fun getAllContacts(): List<DataClas> {
        val contactsList = mutableListOf<DataClas>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))

                contactsList.add(DataClas(id, name, phone))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return contactsList
    }




}
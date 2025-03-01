package com.example.registrocontactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactAdapter(context: Context, private val contacts: List<DataClas>) : ArrayAdapter<DataClas>(context, 0, contacts) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = contacts[position]
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)

        val contactName = view.findViewById<TextView>(R.id.contactName)
        val contactPhone = view.findViewById<TextView>(R.id.contactPhone)

        contactName.text = contact.nombreUsuario
        contactPhone.text = contact.numeroCelular

        return view
    }
}
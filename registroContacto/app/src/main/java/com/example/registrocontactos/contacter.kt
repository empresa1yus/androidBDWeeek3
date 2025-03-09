package com.example.registrocontactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class contacter(private val context: Context, private val contactList: List<DataClas>) : BaseAdapter() {

    override fun getCount(): Int = contactList.size
    override fun getItem(position: Int): Any = contactList[position]
    override fun getItemId(position: Int): Long = contactList[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val contactName = view.findViewById<TextView>(R.id.contactName)
        val contactPhone = view.findViewById<TextView>(R.id.contactPhone)

        val contact = contactList[position]
        contactName.text = contact.nombreUsuario
        contactPhone.text = contact.numeroCelular

        return view
    }
}
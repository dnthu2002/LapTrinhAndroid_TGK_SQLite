package com.example.personal_in4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonalAdapter: RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder>() {
    private var psnList: ArrayList<PersonalModel> = ArrayList()
    private var onclickItem: ((PersonalModel) -> Unit)? =null
    private var onclickDeleteItem: ((PersonalModel) -> Unit)? =null

    fun addItems(items: ArrayList<PersonalModel>){
        this.psnList =items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback: (PersonalModel)-> Unit){
        this.onclickItem = callback
    }
    fun setOnClickDeleteItem(callback: (PersonalModel) -> Unit){
        this.onclickDeleteItem=callback
    }

    class PersonalViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var address = view.findViewById<TextView>(R.id.tvAddress)
        private var phone = view.findViewById<TextView>(R.id.tvPhone)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std: PersonalModel){
            id.text="Mã: ${std.id.toString()}"
            name.text ="Tên: ${std.name}"
            email.text="Email: ${std.email}"
            address.text ="Địa chỉ: ${std.address}"
            phone.text="SDT: ${std.phone}"
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= PersonalViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.items_person, parent, false)
    )

    override fun onBindViewHolder(holder: PersonalViewHolder, position: Int) {
        val psn =psnList[position]
        holder.bindView(psn)
        holder.itemView.setOnClickListener{onclickItem?.invoke(psn)}
        holder.btnDelete.setOnClickListener{onclickDeleteItem?.invoke(psn)}
    }

    override fun getItemCount(): Int {
        return psnList.size
    }
}
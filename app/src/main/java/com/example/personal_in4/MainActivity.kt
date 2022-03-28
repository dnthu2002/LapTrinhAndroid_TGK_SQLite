package com.example.personal_in4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var eName:EditText
    private lateinit var eEmail:EditText
    private lateinit var eAddress:EditText
    private lateinit var ePhone:EditText

    private lateinit var btnAdd:Button
    private lateinit var btnUpdate:Button
    private  lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerview: RecyclerView
    private var adapter: PersonalAdapter? = null
    private var psn:PersonalModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initRecycleView()
        sqliteHelper = SQLiteHelper(this)
        getPersonal()
        btnAdd.setOnClickListener{addPersonal() }
        btnUpdate.setOnClickListener{updatePersonal() }

        adapter?.setOnClickItem {
            Toast.makeText(this,it.name,Toast.LENGTH_SHORT).show()
            eName.setText(it.name)
            eEmail.setText(it.email)
            eAddress.setText(it.address)
            ePhone.setText(it.phone)
            psn =it
        }
        adapter?.setOnClickDeleteItem {
            deletePersonal(it.id)
        }


    }

    private fun getPersonal() {
        val psnList = sqliteHelper.getAllStudent()
        Log.e("tt","${psnList.size}")
        adapter?.addItems(psnList)
    }

    private  fun addPersonal(){
        val name = eName.text.toString()
        val email= eEmail.text.toString()
        val address = eAddress.text.toString()
        val phone= ePhone.text.toString()
        if(name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show()
        }else{
            val psn = PersonalModel(name = name, email = email, address = address, phone = phone)
            val status = sqliteHelper.insertStudent(psn)
            if(status >-1){
                Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT).show()
                clearEditText()
                getPersonal()
            }else{
                Toast.makeText(this,"Thêm thất bại",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updatePersonal(){
        val name = eName.text.toString()
        val email = eEmail.text.toString()
        val address = eAddress.text.toString()
        val phone = ePhone.text.toString()
        if(name == psn?.name && email == psn?.email && address == psn?.address && phone == psn?.phone){
            Toast.makeText(this,"Dữ liệu không thay đổi", Toast.LENGTH_SHORT).show()
            return
        }
        if(psn ==null) return
        val psn = PersonalModel(id = psn!!.id,name=name,email=email,address=address,phone=phone)
        val status= sqliteHelper.updateStudent(psn)
        if(status > -1){
            Toast.makeText(this,"Cập nhật thành công", Toast.LENGTH_SHORT).show()
            clearEditText()
            getPersonal()
        }else{
            Toast.makeText(this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show()
        }

    }
    private fun  deletePersonal(id : Int){
        val builder= AlertDialog.Builder(this)
        builder.setMessage("Bạn có muốn xoá không")
        builder.setCancelable(true)
        builder.setPositiveButton("Có"){dialog, _ ->
            sqliteHelper.deletebyID(id)
            Toast.makeText(this,"Xoá thành công", Toast.LENGTH_SHORT).show()
            getPersonal()
            dialog.dismiss()
        }
        builder.setNegativeButton("Không"){dialog, _ ->
            dialog.dismiss()
        }
        val alert =builder.create()
        alert.show()
    }
    private fun clearEditText(){
        eName.setText("")
        eEmail.setText("")
        eAddress.setText("")
        ePhone.setText("")
        eName.requestFocus()
    }
    private fun  initView(){

        eName = findViewById(R.id.eName)
        eEmail = findViewById(R.id.eEmail)
        eAddress = findViewById(R.id.eAddress)
        ePhone = findViewById(R.id.ePhone)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerview=findViewById(R.id.recyclerView)

    }
    private fun initRecycleView(){
        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = PersonalAdapter()
        recyclerview.adapter = adapter
    }
}
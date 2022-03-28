package com.example.personal_in4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private  const val DATABASE_NAME = "personal.db"
        private const val DATABASE_VERSION= 1
        private const val TBL_PERSONAL= "tbl_personal"
        private const val ID= "id"
        private const val NAME= "name"
        private const val EMAIL= "email"
        private const val ADDRESS= "address"
        private const val PHONE= "phone"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblStudent = ("CREATE TABLE " + TBL_PERSONAL+ "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " VARCHAR(60),"+ EMAIL + " VARCHAR(50),"+ ADDRESS + " VARCHAR(60)," +
                 PHONE + " VARCHAR(20)" +")")
        db?.execSQL(createTblStudent)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PERSONAL")
        onCreate(db)
    }
    fun insertStudent(psn: PersonalModel): Long{
        val db= this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,psn.id)
        contentValues.put(NAME,psn.name)
        contentValues.put(EMAIL, psn.email)
        contentValues.put(ADDRESS, psn.address)
        contentValues.put(PHONE, psn.phone)
        val succes = db.insert(TBL_PERSONAL, null, contentValues)
        db.close()
        return succes
    }
    fun getAllStudent(): ArrayList<PersonalModel> {
        val stdList: ArrayList<PersonalModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_PERSONAL"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e : Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var name:String
        var email:String
        var address:String
        var phone:String
        if(cursor.moveToFirst()) {
            do{
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
                val std = PersonalModel(id=id, name = name, email = email, address=address, phone=phone)
                stdList.add(std)
            }while (cursor.moveToNext())
        }
        return  stdList
    }
    fun updateStudent(psn: PersonalModel):Int{
        val db=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,psn.id)
        contentValues.put(NAME,psn.name)
        contentValues.put(EMAIL,psn.email)
        contentValues.put(ADDRESS,psn.address)
        contentValues.put(PHONE,psn.phone)
        val succes = db.update(TBL_PERSONAL,contentValues, "id=" + psn.id,null)
        db.close()
        return succes
    }
    fun deletebyID(id :Int):Int{
        val db =this.writableDatabase
        val contentValues= ContentValues()
        contentValues.put(ID,id)
        val succes = db.delete(TBL_PERSONAL,"id="+id,null)
        db.close()
        return succes
    }

}
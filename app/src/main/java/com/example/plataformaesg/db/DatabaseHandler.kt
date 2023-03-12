package com.example.plataformaesg.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.plataformaesg.model.Usuario

class DatabaseHandler (ctx: Context): SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION){

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NOME TEXT, $APELIDO TEXT, $EMAIL TEXT, $SENHA TEXT);"
        p0?.execSQL(CREATE_TABLE)
    }

    fun addUsuario(usuario: Usuario){
        val p0: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues().apply {
            put(NOME, usuario.nome)
            put(APELIDO, usuario.apelido)
            put(EMAIL, usuario.email)
            put(SENHA, usuario.senha)
        }
        p0.insert(TABLE_NAME, null, values)
    }

    fun getUsuario(email: String, senha: String) : Usuario {
        val p0 : SQLiteDatabase = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $EMAIL = $email AND $SENHA = $senha;"
        val cursor: Cursor = p0.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        val usuario = montarUsuario(cursor)
        cursor.close()
        return usuario
    }

    fun montarUsuario(cursor: Cursor): Usuario {
        val usuario = Usuario()
            usuario.id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
            usuario.nome = cursor.getString(cursor.getColumnIndexOrThrow(NOME))
            usuario.apelido = cursor.getString(cursor.getColumnIndexOrThrow(APELIDO))
            usuario.email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL))
            usuario.senha = cursor.getString(cursor.getColumnIndexOrThrow(SENHA))
        return usuario
    }

    companion object{
        private val DB_VERSION = 1
        private val DB_NAME = "esgDatabase"
        private val TABLE_NAME = "Usuario"
        private val ID = "Id"
        private val NOME = "Nome"
        private val APELIDO = "Apelido"
        private val EMAIL = "Email"
        private val SENHA = "Senha"
    }
}
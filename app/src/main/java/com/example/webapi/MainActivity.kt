package com.example.webapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.lang.Exception
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {
    var requestQueue: RequestQueue? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginbutton.setOnClickListener {
            if(check()){
                requestQueue = Volley.newRequestQueue(this)
                DataLogin()
            }
            else{
                return@setOnClickListener
            }
        }
        signuptext.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        }
    }

    fun DataLogin(){
        var url = "http://10.0.3.2/webexa/login.php"
        var request: StringRequest = object: StringRequest(
            Method.POST,url,Response.Listener {response->
                try{
                    var jarr = JSONArray(response)
                    var jobj = jarr.getJSONObject(0)
                    if(jobj.getString("login").toString() == "success"){

                        var pref: SharedPreferences = this.getSharedPreferences("api_pref", Context.MODE_PRIVATE)
                        var edit: SharedPreferences.Editor = pref.edit()
                        edit.putString("name",jobj.getString("name").toString())
                        edit.putString("mobile",mobile.text.toString().trim())
                        edit.putString("password",password.text.toString().trim())
                        edit.apply()
                        edit.commit()

                        mobile.text.clear()
                        password.text.clear()
                        startActivity(Intent(this,HomeActivity::class.java))
                    }
                    else{
                        Toast.makeText(applicationContext,"Mobile Number and Password Incorrect",Toast.LENGTH_LONG).show()
                        return@Listener
                    }
                }
                catch(e: Exception){
                    Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext,it.message.toString(),Toast.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                var params: MutableMap<String,String> = HashMap()
                params.put("mobile",mobile.text.toString().trim())
                params.put("password",password.text.toString().trim())
                return params
            }
        }
        requestQueue?.add(request)
    }

    fun check(): Boolean{
        if(mobile.text.toString().trim().length != 10){
            Toast.makeText(applicationContext,"Enter Valid Mobile Number",Toast.LENGTH_LONG).show()
            return false
        }
        if(password.text.toString().trim().isEmpty()){
            Toast.makeText(applicationContext,"Enter Password",Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
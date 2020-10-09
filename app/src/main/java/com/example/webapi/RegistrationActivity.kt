package com.example.webapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class RegistrationActivity : AppCompatActivity() {
    var requestQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerbutton.setOnClickListener {
            if(check()){
                requestQueue = Volley.newRequestQueue(this)
                DataSignUp()
            }
            else{
                return@setOnClickListener
            }
        }
    }

    fun DataSignUp(){
        var url = "http://10.0.3.2/webexa/register.php"
        var request: StringRequest = object: StringRequest(
            Method.POST,url,Response.Listener {response->
                try{
                    var jarr = JSONArray(response)
                    var jobj = jarr.getJSONObject(0)

                    if(jobj.getString("register").toString() == "success"){

                        var pref: SharedPreferences = this.getSharedPreferences("api_pref", Context.MODE_PRIVATE)
                        var edit: SharedPreferences.Editor = pref.edit()
                        edit.putString("name",name.text.toString().trim())
                        edit.putString("mobile",mobile.text.toString().trim())
                        edit.putString("password",password.text.toString().trim())
                        edit.apply()
                        edit.commit()

                        startActivity(Intent(this,HomeActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext,"SignUp Fail",Toast.LENGTH_LONG).show()
                    }
                    name.text.clear()
                    mobile.text.clear()
                    password.text.clear()
                }
                catch (e: Exception){
                    Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_LONG).show()
                }
            },Response.ErrorListener {
                Toast.makeText(applicationContext,it.message.toString(),Toast.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                var params: MutableMap<String,String> = HashMap();
                params.put("name",name.text.toString().trim())
                params.put("mobile",mobile.text.toString().trim())
                params.put("password",password.text.toString().trim())
                return params
            }
        };
        requestQueue?.add(request)
    }

    fun check():Boolean{
        if(name.text.toString().trim().isEmpty()){
            Toast.makeText(applicationContext,"Enter Name",Toast.LENGTH_LONG).show()
            return false
        }
        if(mobile.text.toString().trim().length != 10){
            Toast.makeText(applicationContext,"Enter Proper Mobile Number",Toast.LENGTH_LONG).show()
            return false
        }
        if(password.text.toString().trim().isEmpty()){
            Toast.makeText(applicationContext,"Enter Password",Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
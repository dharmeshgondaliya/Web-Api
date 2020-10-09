package com.example.webapi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile_.*
import kotlinx.android.synthetic.main.fragment_profile_.view.*
import kotlinx.android.synthetic.main.fragment_profile_.view.changemyimage
import org.json.JSONArray
import java.lang.Exception


class Profile_Fragment(context: Context) : Fragment() {
    var requestQuequ: RequestQueue? = null
    lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_profile_, container, false)

        requestQuequ = Volley.newRequestQueue(context)
        pref = context!!.getSharedPreferences("api_pref",0)
        view.MyProfileName.text = pref.getString("name","defaultname")
        view.MyProfileMobile.text = pref.getString("mobile","defaultname")
        LoadImage(view)

        view.changemyimage.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/"
            startActivityForResult(intent,999)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK  && requestCode == 999){
            var uri = data?.data
            if(uri != null){
                myimage.setImageURI(uri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun LoadImage(view: View) {
        var request: StringRequest = object: StringRequest(
            Method.POST,"http://10.0.3.2/webexa/getimage.php",Response.Listener {response->
                try {
                    var jarr = JSONArray(response)
                    var jobj = jarr.getJSONObject(0)
                    Picasso.get().load("http://10.0.3.2/webexa/image/"+jobj.getString("image").toString()).into(view.findViewById(R.id.myimage) as ImageView)
                }
                catch(e: Exception){
                    Toast.makeText(context,e.message.toString(),Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(context,it.message.toString(),Toast.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                var params: MutableMap<String,String> = HashMap()
                params.put("mobile",pref.getString("mobile","defaultname").toString())
                params.put("password",pref.getString("password","defaultname").toString())
                return params
            }
        }
        requestQuequ!!.add(request)
    }
}
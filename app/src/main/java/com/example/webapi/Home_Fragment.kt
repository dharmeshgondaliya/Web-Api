package com.example.webapi

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home_.*
import kotlinx.android.synthetic.main.fragment_home_.view.*
import org.json.JSONArray
import java.lang.Exception

class Home_Fragment(context: Context) : Fragment() {
    var list: ArrayList<DataModel>? = null
    var requestQueue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View =  inflater.inflate(R.layout.fragment_home_, container, false)
        list = ArrayList()
        getItems()
        view?.recycler?.layoutManager = LinearLayoutManager(context)
        view?.recycler?.adapter = RecyclerAdapter(list!!)

        view.search_btn.setOnClickListener {
            var namelike = searchbar.text.trim().toString()
            if(searchbar.text.trim().isEmpty())
                getItems()
            else
                getItems("select * from users where name like '%$namelike%'")

            view?.recycler?.layoutManager = LinearLayoutManager(context)
            view?.recycler?.adapter = RecyclerAdapter(list!!)
        }
        return view
    }

    fun getItems(query: String = "select * from users"){
        var url = "http://10.0.3.2/webexa/list.php"
        requestQueue = Volley.newRequestQueue(context)
        list!!.clear()
        var request: StringRequest = object: StringRequest(
            Method.POST,url,Response.Listener {response ->
                try {
                    var jarr = JSONArray(response)
                    for(i in 0 until jarr.length()){
                        var jobj = jarr.getJSONObject(i)
                        var name = jobj.getString("name").toString()
                        var mobile = jobj.getString("mobile").toString()
                        var date = jobj.getString("date").toString()
                        var image = jobj.getString("image").toString()
                        list!!.add(DataModel(name,mobile,date,image))
                    }
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
                params.put("get_user","true")
                params.put("query",query)
                return params
            }
        }
        requestQueue?.add(request)
    }
}
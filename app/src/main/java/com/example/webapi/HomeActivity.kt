package com.example.webapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        replace(Home_Fragment(applicationContext))
        bottombar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.profilemenu->{
                    replace(Profile_Fragment(applicationContext))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.homemenu->{
                    replace(Home_Fragment(applicationContext))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }
    fun replace(fragment: Fragment){
        var fr = supportFragmentManager.beginTransaction()
        fr.replace(R.id.framview,fragment)
        fr.commit()
    }
}
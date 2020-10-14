package com.mughitszufar.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mughitszufar.instagram.fragment.HomeFragment
import com.mughitszufar.instagram.fragment.NotificationFragment
import com.mughitszufar.instagram.fragment.ProfileFragment
import com.mughitszufar.instagram.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    //untuk mengaktifkan bottomnavigation di activity
    private val onNavigationItemRSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.nav_home ->{
                moveToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_search ->{
                moveToFragment(SearchFragment())
                return@OnNavigationItemSelectedListener  true
            }

            R.id.nav_add_post ->{
                item.isChecked
                startActivity(Intent(this, TambahPostActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_notification ->{
                moveToFragment(NotificationFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_profile ->{
                moveToFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }

        }

        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //untuk membuild bottomnavigationya
        val nawView: BottomNavigationView = findViewById(R.id.nav_view)
        nawView.setOnNavigationItemSelectedListener(onNavigationItemRSelectedListener)

        //supaya home menjadi deafult ketika aplikasi pertama di jalankan
        moveToFragment(HomeFragment())

    }

    //function untuk pindah antar fragment
    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()
    }

}
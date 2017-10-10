package com.example.developer.notes.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu


abstract class BaseActivity : AppCompatActivity() {
    abstract val layoutResId: Int
    open val menuRes: Int? get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount != 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuRes?.let { menuInflater.inflate(it, menu) }
        return super.onCreateOptionsMenu(menu)
    }

    fun replaceFragment(containerId: Int, fragment: Fragment, addToBackStack: Boolean = false, backStackName: String? = null) {
        val transaction = supportFragmentManager.beginTransaction().replace(containerId, fragment, backStackName)
        if (addToBackStack) {
            transaction.addToBackStack(backStackName)
        }
        transaction.commit()
    }
}
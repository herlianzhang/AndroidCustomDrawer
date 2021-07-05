package com.pmberjaya.customdrawer

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.pmberjaya.customdrawer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val shapeDrawable = GradientDrawable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shapeDrawable.apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.RED)
        }

        val actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.app_name,
            R.string.app_name
        ) {
            private val scaleFactor = 6f
            private val radius = 24f

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = binding.drawer.width * slideOffset
                shapeDrawable.cornerRadius = convertToDp(binding.root.context, radius * slideOffset)
                binding.content.apply {
                    translationX = slideX
                    scaleX = 1 - (slideOffset / scaleFactor)
                    scaleY = 1 - (slideOffset / scaleFactor)
                    background = shapeDrawable
                }
            }
        }

        binding.content.background = shapeDrawable
        binding.drawerLayout.apply {
            setScrimColor(Color.TRANSPARENT)
            drawerElevation = 0f
            addDrawerListener(actionBarDrawerToggle)
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    fun convertToDp(context: Context, size: Float) =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            size,
            context.resources.displayMetrics
        )
}
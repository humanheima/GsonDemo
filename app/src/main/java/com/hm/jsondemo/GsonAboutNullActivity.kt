package com.hm.jsondemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hm.jsondemo.beans.Category
import com.hm.jsondemo.gson_adapter.StringAdapterFactory
import kotlinx.android.synthetic.main.activity_gson_about_null.*

class GsonAboutNullActivity : AppCompatActivity() {


    private val TAG: String? = "GsonAboutNullActivity"

    private val gson: Gson by lazy {

        createGson()
    }

    private fun createGson(): Gson {

        val gson = GsonBuilder()
                //.registerTypeAdapterFactory(StringAdapterFactory())
                .create()
        return gson
    }

    private lateinit var category: Category

    companion object {

        @JvmStatic
        fun launch(context: Context) {
            val intent = Intent(context, GsonAboutNullActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gson_about_null)

        category = Category()
        tvResult.text = category.toString()


        btnNoField.setOnClickListener {


            val jsonStr = "{\"contentType\": \"music\"," +
                    "  \"coverPath\": \"http://xxx.png\"," +
                    "  \"id\": 99," +
                    "  \"title\": \"音乐让我自由\"" +
                    "}"


            category = gson.fromJson(jsonStr, Category::class.java)

            Log.d(TAG, "url字段为: ${category.url}")
            tvResult.text = category.toString()
        }

        btnFieldIsNull.setOnClickListener {

            val jsonStr = "{\"contentType\": \"music\"," +
                    "  \"coverPath\": \"http://xxx.png\"," +
                    "  \"id\": 99," +
                    "  \"title\": \"音乐让我自由\"," +
                    "  \"url\": null" +
                    "}"

            category = gson.fromJson(jsonStr, Category::class.java)

            Log.d(TAG, "url字段为: ${category.url}")
            tvResult.text = category.toString()

        }
    }
}

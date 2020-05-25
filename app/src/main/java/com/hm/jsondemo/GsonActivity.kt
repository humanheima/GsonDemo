package com.hm.jsondemo

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hm.jsondemo.beans.Person
import com.hm.jsondemo.beans.UserSimple
import com.hm.jsondemo.databinding.ActivityGsonBinding
import kotlinx.android.synthetic.main.activity_gson.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class GsonActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "GsonActivity"

        @JvmStatic
        fun launch(context: Context) {
            val intent = Intent(context, GsonActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val gson = Gson()

    private var binding: ActivityGsonBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gson)
        testPrimitiveType()
        val person = Person("1", "dumingwei", "2016")
        person.age = 10
        val json = gson.toJson(person)
        Log.d(TAG, "onCreate: person json =$json")

        //String jsonStr = "{\"age\":0,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}";
        //String jsonStr = "{\"age\":10,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2020\"}";
        //String jsonStr = "{\"age\":10.00,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}";
        //String jsonStr = "{\"age\":10.01,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}";
        val jsonStr = "{\"age\":\"10\",\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}"
        //String jsonStr = "{\"age\":\"10.11\",\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}";

        //注意age这个字段
        //String jsonStr = "{\"age\":0,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\",\"age\":\"12.01\"}";
        val person1 = gson.fromJson(jsonStr, Person::class.java)
        Log.d(TAG, "onCreate: $person1")

        //testConvertList();

        btnConvertMap.setOnClickListener {
            covertMap()
        }

        btnConvertSet.setOnClickListener {
            convertSet()
        }

        btnMatchMultiField.setOnClickListener {
            matchMultiField()
        }
        btnPolymorphic.setOnClickListener {

        }
    }

    private fun testPrimitiveType() {
        Log.d(TAG, gson.toJson(1)) //把一个int类型的数字转化为json字符串
        Log.d(TAG, gson.toJson(true)) //把一个boolean类型的值转化为json字符串
        Log.d(TAG, gson.toJson(1.3f)) //把一个float类型的数据转化为json字符串
        Log.d(TAG, gson.toJson(1.4)) //把一个double类型的数据转化为json字符串
        Log.d(TAG, gson.toJson('a')) //把一个char类型的数据转化为json字符串
        Log.d(TAG, gson.toJson("hello world")) //把一个String转化为json字符串
    }

    private fun testConvertList() {
        val personList: MutableList<Person?> = ArrayList()
        for (i in 0..2) {
            personList.add(null)
        }

        //String jsonPersonList = gson.toJson(personList);
        //String jsonPersonList = "[null,null,null]";
        val jsonPersonList = "[{\"age\":0,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"},,]"
        Log.d(TAG, "onCreate: $jsonPersonList")
        binding!!.tvResult.text = jsonPersonList
        val personList1 = gson.fromJson<List<Person>>(jsonPersonList, object : TypeToken<List<Person?>?>() {}.type)
        Log.d(TAG, "onCreate: personList1 is null ? $personList1")
        Log.d(TAG, "onCreate: personList1.size = " + personList1.size)
        for (person2 in personList1) {
            //int age = person2.getAge();
        }
    }

    private fun covertMap() {
        val employees: HashMap<String, List<String>> = HashMap()
        employees["A"] = listOf("Andreas", "Arnold", "Aden")
        employees["C"] = listOf("Christian", "Carter")
        employees["M"] = listOf("Marcus", "Mary")


        val result = gson.toJson(employees)
        tvResult.text = result

        val type = object : TypeToken<HashMap<String, List<String>>>() {}.type

        val map = gson.fromJson<HashMap<String, List<String>>>(result, type)

        map.forEach {
            Log.d(TAG, "covertMap: ${it.key},${it.value}")
        }
    }

    private fun convertSet() {
        val users: HashSet<String> = HashSet()
        users.add("Christian")
        users.add("Marcus")
        users.add("Norman")
        users.add("Marcus")

        val result = gson.toJson(users)

        tvResult.text = result

        val set = gson.fromJson<HashSet<String>>(result, object : TypeToken<HashSet<String>>() {}.type)

        set.forEach {
            Log.d(TAG, "convertSet: $it")
        }
    }

    /**
     * 反序列化过程中匹配多个字段的问题
     */
    private fun matchMultiField() {
        val json1 = "{" +
                "    \"name\": \"Norman\"," +
                "    \"fullName\": \"Hello world\"," +
                "    \"email\": \"norman@futurestud.io\"," +
                "    \"age\": 26," +
                "    \"isDeveloper\": true" +
                "}"

        val user1 = gson.fromJson<UserSimple>(json1, UserSimple::class.java)
        Log.d(TAG, "matchMultiField: ${user1.name}")

        val json2 = "{" +
                "    \"fullName\": \"Hello world\"," +
                "    \"name\": \"Norman\"," +
                "    \"email\": \"norman@futurestud.io\"," +
                "    \"age\": 26," +
                "    \"isDeveloper\": true" +
                "}"

        val user2 = gson.fromJson<UserSimple>(json2, UserSimple::class.java)
        Log.d(TAG, "matchMultiField: ${user2.name}")

    }

}
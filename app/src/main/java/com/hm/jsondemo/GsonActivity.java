package com.hm.jsondemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hm.jsondemo.beans.Person;
import com.hm.jsondemo.databinding.ActivityGsonBinding;

import java.util.ArrayList;
import java.util.List;

public class GsonActivity extends AppCompatActivity {

    private static final String TAG = "GsonActivity";

    private Gson gson = new Gson();

    private ActivityGsonBinding binding;

    public static void launch(Context context) {
        Intent intent = new Intent(context, GsonActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gson);

        testPrimitiveType();

        Person person = new Person("1", "dumingwei", "2016");
        person.setAge(10);
        String json = gson.toJson(person);
        Log.d(TAG, "onCreate: person json =" + json);

        //String jsonStr = "{\"age\":0,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}";
        //String jsonStr = "{\"age\":10,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2020\"}";
        //String jsonStr = "{\"age\":10.00,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}";
        //String jsonStr = "{\"age\":10.01,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}";

        String jsonStr = "{\"age\":\"10\",\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}";
        //String jsonStr = "{\"age\":\"10.11\",\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"}";

        //注意age这个字段
        //String jsonStr = "{\"age\":0,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\",\"age\":\"12.01\"}";
        Person person1 = gson.fromJson(jsonStr, Person.class);
        Log.d(TAG, "onCreate: " + person1.toString());

        //testConvertList();
    }

    private void testPrimitiveType() {
        Log.d(TAG, gson.toJson(1));//把一个int类型的数字转化为json字符串
        Log.d(TAG, gson.toJson(true));//把一个boolean类型的值转化为json字符串
        Log.d(TAG, gson.toJson(1.3f));//把一个float类型的数据转化为json字符串
        Log.d(TAG, gson.toJson(1.4));//把一个double类型的数据转化为json字符串
        Log.d(TAG, gson.toJson('a'));//把一个char类型的数据转化为json字符串
        Log.d(TAG, gson.toJson("hello world"));//把一个String转化为json字符串
    }

    private void testConvertList() {

        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            personList.add(null);
        }

        //String jsonPersonList = gson.toJson(personList);
        //String jsonPersonList = "[null,null,null]";
        String jsonPersonList = "[{\"age\":0,\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2016\"},,]";

        Log.d(TAG, "onCreate: " + jsonPersonList);

        binding.tvResult.setText(jsonPersonList);

        List<Person> personList1 = gson.fromJson(jsonPersonList, new TypeToken<List<Person>>() {
        }.getType());
        Log.d(TAG, "onCreate: personList1 is null ? " + personList1);
        Log.d(TAG, "onCreate: personList1.size = " + personList1.size());

        for (Person person2 : personList1) {
            //int age = person2.getAge();
        }
    }


}


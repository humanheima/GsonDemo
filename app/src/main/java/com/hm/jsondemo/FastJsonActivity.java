package com.hm.jsondemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hm.jsondemo.beans.Group;
import com.hm.jsondemo.beans.User;
import com.hm.jsondemo.databinding.ActivityFastJsonBinding;

import java.util.Date;
import java.util.Map;

public class FastJsonActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private ActivityFastJsonBinding binding;

    public static void launch(Context context) {
        Intent intent = new Intent(context, FastJsonActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fast_json);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_simple_encode:
                simpleEncode();
                break;
            case R.id.btn_simple_decode:
                simpleDecode();
                break;
            case R.id.btn_encode_date:
                encodeDate();
                break;
            case R.id.btn_test_JSONField:
                testJSONField();
                break;
            default:
                break;
        }
    }

    private void testJSONField() {
        User guestUser = new User();
        guestUser.setId(2L);
        guestUser.setAddress("guangzhou");
        String json = JSON.toJSONString(guestUser);
        Log.e(TAG, json);

        /*String json = "{\"id\":2,\"addr\":\"shanghai\"}";
        User user = JSON.parseObject(json, User.class);
        Log.e(TAG, user.getAddress());*/
    }

    private void encodeDate() {
        Date date = new Date();
        String dateJson = JSON.toJSONString(date, SerializerFeature.WriteDateUseDateFormat);
        Log.e(TAG, "encodeDate: " + dateJson);
        String dateJsonFormat = JSON.toJSONStringWithDateFormat(date, "yyyy/MM/dd");
        Log.e(TAG, "encodeDate: " + dateJsonFormat);
    }

    private void simpleEncode() {
        Group group = new Group();
        group.setId(0L);
        group.setName("admin");

        User guestUser = new User();
        guestUser.setId(2L);
        guestUser.setAddress("shanghai");

        User rootUser = new User();
        rootUser.setId(3L);
        rootUser.setAddress("beijing");

        group.addUser(guestUser);
        group.addUser(rootUser);
        //String jsonString = JSON.toJSONString(group);
        String jsonString = JSON.toJSONString(group, true);//这个方法可以直接输出json格式化的字符串
        Log.e(TAG, "\n\r" + jsonString);
    }

    private void simpleDecode() {
        String json = "{\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"address\":\"guest\"},{\"id\":3,\"address\":\"root\"}]}";
        Group group = JSON.parseObject(json, Group.class);
        Log.e(TAG, "simpleDecode: " + group);
        String genericJson = "{\"user\":{\"address\":\"Beijing\",\"id\":24}}";
        Map<String, User> map = JSON.parseObject(genericJson, new TypeReference<Map<String, User>>() {
        });
        Log.e(TAG, map.get("user").toString());
    }
}

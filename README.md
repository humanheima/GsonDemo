## JSON DEMO

this repository is about using fastjson,gson and jackjson.

## fastjson

[FastJson在Android中的使用](http://blog.csdn.net/leilifengxingmw/article/details/79477883)
1. @JSONField(serialize = false) 作用于字段上 当 java对象转化为json字符串的时候，生成的json字符串不包括这个字段
2. @JSONField(deserialize = false) 作用于字段上不起作用。
3. @JSONField(deserialize = false) 作用于set方法上，当把json字符串反序列化为对象的时候，对象对应的字段不会被赋值。
```java
private String address;

@JSONField(deserialize = false)
    public void setAddress(String address) {
        this.address = address;
    }
```
```java
String userJson = "{\"address\":\"Beijing\",\"id\":24}";
        User user = JSON.parseObject(userJson, User.class);
        Log.e(TAG, user.toString());
```
输出结果
```java
User{id=24, address='null'}
```



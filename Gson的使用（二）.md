本篇文章验证一些自己在开发过程中遇到的疑问

比如说我定义了一个Java数据类如下所示：
```java
public class Category {

    private String contentType;
    private String coverPath;
    private int id;
    private String title;
    //注释1处，url默认值不为空
    private String url = "http://baidu.com";
    //省略getter/setter
}
```
注释1处，url默认是有值的，也就是说我们在使用的时候可以不判空。

### 疑问1
如果json字符串中没有url这个字段，那么在反序列化的时候，model的url字段是否为null？

```json
{
  "contentType": "music",
  "coverPath": "http://xxx.png",
  "id": 99,
  "title": "音乐让我自由",
}
```

测试结果：Category类的url字段保持默认值不变。

### 疑问2

如果json字符串中有url这个字段，那么在串反序列化的时候，model的url字段是否为null？

```json
{
  "contentType": "music",
  "coverPath": "http://xxx.png",
  "id": 99,
  "title": "音乐让我自由",
  "url": null
}
```

测试结果：Category类的url字段变为null。

### 疑问3

在反序列化过程中，如果我们发现json字符串中字段值为null

3.1 是否可以忽略这个字段呢，这样就可以不改变我们的默认值。

测试结果：目前没有看到这样的解决方法。

3.2 或者用一个默认值替代赋值给model类的字段？

测试结果：可以，自定义`TypeAdapterFactory`和`TypeAdapter<T>`

```kotlin
class StringAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType: Class<in T> = type.rawType

        if (rawType != String::class.java) {
            return null
        }
        //注释1处
        return StringAdapter() as (TypeAdapter<T>)
    }

    /**
     * Desc: 在将json字符串反序列化为model类的时候，如果String字段为null，
     * 那么就用空字符串（""）替换，然后将空字符串（""）赋值给model类的字段
     */
    class StringAdapter : TypeAdapter<String>() {

        override fun write(out: JsonWriter, value: String?) {
            out.value(value)
        }

        override fun read(reader: JsonReader): String {
            //注释2处
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            return reader.nextString()
        }
    }
}
```
注释1处，如果在反序列化过程中字段类型是String，就使用自定义的StringAdapter。

注释2处，如果String字段为null，那么就用空字符串`""`替换，然后将空字符串`""`赋值给model类的字段。

使用
```kotlin
private fun createGson(): Gson {
    val gson = GsonBuilder()
          .registerTypeAdapterFactory(StringAdapterFactory())
          .create()
    return gson
}
```
构建Gson实例的时候，使用GsonBuilder来构建并注册StringAdapterFactory即可。


### 疑问4
Gson在反序列化的过程中对model类的构造函数是否有要求。

比如Category类，现在我们添加了一个无参构造函数（如果不手动添加，编译器会帮我们默认添加无参构造函数）。这时候反序列化是没有问题的。在反序列化过程中，会调用这个无参构造函数。

```java
public class Category {

    private String contentType;
    private String coverPath;
    private int id;
    private String title;
    //注释1处，url默认值不为空
    private String url = "http://baidu.com";
    
    //无参构造函数
    public Category() {
        Log.d(TAG, "Category: construct");
    }
    
    //省略getter/setter
}
```

主动添加有参构造函数，这样编译器也不会给我们添加无参构造函数了。

```java
public class Category {

    private static final String TAG = "Category";
    private String contentType;
    private String coverPath;
    private int id;
    private String title;
    private String url = "http://baidu.com";

    public Category(String contentType, String coverPath, int id, String title, String url) {
        Log.d(TAG, "Category: construct");
        this.contentType = contentType;
        this.coverPath = coverPath;
        this.id = id;
        this.title = title;
        this.url = url;
    }
    //...
}    
```
反序列化成功，但是不会调用这个构造函数。是使用`sun.misc.Unsafe`来创建实例的。细节可以参考这个链接 [Is default no-args constructor mandatory for Gson?](https://stackoverrun.com/cn/q/5084470)


###疑问5
反序列化过程中匹配多个字段名的问题
```java
public class UserSimple {

    @SerializedName(value = "name", alternate = {"fullName"})
    String name;
    String email;
    int age;
    boolean isDeveloper;
}
```
我们定义了UserSimple类，在反序列化过程中，name字段可以匹配`name`或者`fullName`。那么，如果在json字符串中`name`和`fullName`都有，那么最终model类的name字段的值是多少呢？验证一下：

json字符串中name字段在前，fullName字段在后
```json
{
    "name": "Norman",
    "fullName": "Hello world",
    "email": "norman@futurestud.io",
    "age": 26,
    "isDeveloper": true
}
```
验证结果：UserSimple类的name字段结果是`Hello world`。

json字符串中fullName字段在前，name字段在后

```json
{
    "fullName": "Hello world",
    "name": "Norman",
    "email": "norman@futurestud.io",
    "age": 26,
    "isDeveloper": true
}
```
验证结果：UserSimple类的name字段结果是`Norman`。

结论：当json字符串中有多个值可以匹配model类的字段，那么model类的字段的最终取值是json字符串中后出现的值。




参考链接：
* [Gson反序列化中的NULL值替换（各种类型）](https://blog.csdn.net/Ever69/article/details/86469320)
* [Gson - Java-JSON 序列化和反序列化入门](https://www.jianshu.com/p/a03bc97875b8)
*  [Is default no-args constructor mandatory for Gson?](https://stackoverrun.com/cn/q/5084470)
* [GSON的使用（一）](https://blog.csdn.net/leilifengxingmw/article/details/51234007)







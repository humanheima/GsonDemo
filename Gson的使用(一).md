先扯点别的。在写东西之前我总是疑问到底写不写，网上已经有很多人早就写完了，写的比我写的好，比我写的早，那我写这些东西还有意义吗？答案是：对我是有意义的。这就好比别人已经有了女朋友，那我就不找女朋友了？愚蠢。当然我也得找！最近腰疼的我什么心情也没有，身体健康才是第一位。明天请一天的假，去医院看病！

GSON很早一前就听说过，可我没怎么自已研究使用过，就是因为《第一行代码》中关于GSON的一句话，让我望而止步。弱者总是畏惧未知领域，而强者总是充满冒险精神！
就是这句话：“如果需要解析的是一段 JSON数组会稍微麻烦一点，我们需要借助 TypeToken将期望解析成的数据类型传入到 fromJson()方法中”

今天用一个例子分别使用GSON和JAVA中的方法进行处理。关于GSON的详细用法，我会在后续的文章中再陈述。（没想到现在都2020年4月1号愚人节了，还是没有后续，哈哈）。

**上面的废话有点多，开搞：在Android Studio中使用GSON很简单，在你的build文件中添加一行**

```
 compile 'com.google.code.gson:gson:2.2.4'
```

 1. 先定义一个类

```
public class Person {

    private String id;
    private String name;
    private String version;

    public Person(String id,String name,String version){
        this.id=id;
        this.name=name;
        this.version=version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

```

**GSON常用的方法有两个**

```
gson.toJson(Object src);//把一个对象转化成json格式的数据，这个方法有很多重载的方法

fromJson(String json, Class<T> classOfT)//把json格式的数据还原成对应的对象。这个方法有很多重载方法
```

**把基本数据类型转化成json格式的数据**

```
gson.toJson(1);//把一个int类型的数字转化为json字符串
gson.toJson(true);//把一个boolean类型的值转化为json字符串
gson.toJson(1.3f);//把一个float类型的数据转化为json字符串
gson.toJson(1.4);//把一个double类型的数据转化为json字符串
gson.toJson('a');//把一个char类型的数据转化为json字符串
gson.toJson("hello world");//把一个String转化为json字符串
//自定义一个对象。
Person person = new Person("1", "dumingwei", "2016");
gson.toJson(person);//把一个自定义的对象转化成gson字符串
```
**注意：private transient int value3 = 3;  transient 标记的变量默认情况下不会被转化成json数据**

输出结果：
![这里写图片描述](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTYwNDI0MTUyNTE3MDg4?x-oss-process=image/format,png)

**把基本的数据类型的json数据还原成基本数据类型**

```
 gson.fromJson("100", int.class);
 gson.fromJson("1.3f", float.class);
 gson.fromJson("99.99", double.class);
 gson.fromJson("true", boolean.class);
 gson.fromJson("\"hello world\"", String.class);
 Person person = gson.fromJson("{\"id\":\"1\",\"name\":\"dumingwei\",\"version\":\"2015\"}", Person.class);
```
**输出结果**

![这里写图片描述](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTYwNDI0MTU0MzUyNjEx?x-oss-process=image/format,png)

**请注意gson.fromJson("\"hello world\"", String.class);，如果换成gson.fromJson("hello world", String.class);的话就会报一个错误，这是因为"hello world"只是一个字符串，而不是json格式的数据。**

```
com.google.gson.JsonSyntaxException: com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 8
```

**把数组转化成json格式的数据**

```
 Gson gson = new Gson();
 int[] arrayInt = {2, 3, 4, 5, 1, 6};//基本数据类型的数组
 String arrayJson = gson.toJson(arrayInt);
 Person[] arrayPerson = {new Person("1", "dumingwei", "2014"), new Person("1", "human", "2015"), new Person("1", "dumingwei", "2016")};//自定义对象的数组
 String arrayPersonJson = gson.toJson(arrayPerson);
       
```
**输出结果**

![这里写图片描述](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTYwNDI0MTU0OTI2Mzc3?x-oss-process=image/format,png)

**把json数据还原成数组**

```
 Gson gson = new Gson();
int[] result = gson.fromJson(arrayJson, int[].class);//还原基本数据类型数组
for (int i : result) {
    Log.e("TAG",i+" ");
}
Person people[] = gson.fromJson(arrayPersonJson, Person[].class);//还原自定义对象数组
for (Person person : people) {
     Log.e("TAG","id:" + person.getId() + ",name:" + person.getName() + ",version:" + person.getVersion() + "\n");
}
```
**输出结果**

![这里写图片描述](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTYwNDI0MTU1MzM0MzM0?x-oss-process=image/format,png)


**2.定义一个List,放进去几个person,然后把这个List集合转化成json数据。**

```

 Gson gson = new Gson();
 
 List<Person> list2 = new ArrayList<>();
 list2.add(new Person("1", "human", "2016"));
 list2.add(new Person("2", "dumingwei", "2016"));
 list2.add(new Person("3", "heima", "2016"));
 list2.add(new Person("4", "hello", "2016"));

//把一个list转化成json串
 String listPersonJson = gson.toJson(list2);
 Log.e("TAG",listPersonJson );

```
**输出结果：**

```
[
    {
        "id": "1",
        "name": "human",
        "version": "2016"
    },
    {
        "id": "2",
        "name": "dumingwei",
        "version": "2016"
    },
    {
        "id": "3",
        "name": "heima",
        "version": "2016"
    },
    {
        "id": "4",
        "name": "hello",
        "version": "2016"
    }
]
```
**注意：如果你把Log打印出来的字符串复制到java文件中是这样的：**

```
 String s="[{\"id\":\"1\",\"name\":\"human\",\"version\":\"2016\"},{\"id\":\"2\",\"name\":\"dumingwei\",\"version\":\"2016\"},{\"id\":\"3\",\"name\":\"heima\",\"version\":\"2016\"},{\"id\":\"4\",\"name\":\"hello\",\"version\":\"2016\"}]";
   
```

**就是这么简单。接下来我们试着使用GSON和java中的方法解析这段字符串。**

 1. 使用GSON

```
Gson gson = new Gson();
// 借助 TypeToken将期望解析成的数据类型传入到 fromJson()方法中
List<Person> list4 = gson.fromJson(listPersonJson, new TypeToken<List<Person>>() {}.getType());

for (Person person : list4) {
    //把解析出来的内容打印出来
    Log.e("TAG","id:" + person.getId() + ",name:" + person.getName() + ",version:" + person.getVersion());
}
```

```
//打印结果
TAG: id:1,name:human,version:2016
TAG: id:2,name:dumingwei,version:2016
TAG: id:3,name:heima,version:2016
TAG: id:4,name:hello,version:2016

```
2.使用java自带的方法

```
 List<Person>people=new ArrayList<>();
try {
     JSONArray jsonArray=new JSONArray(listPersonJson);//解析的内容是一个json数组
     //遍历得到jsonArray中的每一项
     for (int i=0;i<jsonArray.length();i++){
          JSONObject jsonObject=jsonArray.getJSONObject(i);
		  //解析出每一项的字段，还原成一个person
          Person person=new Person(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("version"));
                
          //把person添加到people集合中
          people.add(person);
     }
} catch (JSONException e) {
     e.printStackTrace();
}
//遍历输出集合中的每一项。得到打印结果
for (Person person : people) {
    // tvResult.append("id:" + person.getId() + ",name:" + person.getName() + ",version:" + person.getVersion() + "\n");
    Log.e("TAG","id:" + person.getId() + ",name:" + person.getName() + ",version:" + person.getVersion());
}
```

```
//打印结果和上面一样一样的。
TAG: id:1,name:human,version:2016
TAG: id:2,name:dumingwei,version:2016
TAG: id:3,name:heima,version:2016
TAG: id:4,name:hello,version:2016
```
**可以看出来使用GSON解析json数据确实简单，推荐使用。本篇讲的是GSON的基本用法，关于GSON的更多用法，以后有时间，有精力，我还会再写。**

参考链接：
[github上gson的官方地址](https://github.com/google/gson)

[Google Gson 使用简介](http://www.cnblogs.com/haippy/archive/2012/05/20/2509329.html)




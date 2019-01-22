package com.hm.jsondemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hm.jsondemo.beans.Album;
import com.hm.jsondemo.beans.Albums;
import com.hm.jsondemo.beans.Animal;
import com.hm.jsondemo.beans.Artist;
import com.hm.jsondemo.beans.Dataset;
import com.hm.jsondemo.beans.Elephant;
import com.hm.jsondemo.beans.Lion;
import com.hm.jsondemo.beans.Zoo;
import com.hm.jsondemo.databinding.ActivityJacksonBinding;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JacksonActivity extends AppCompatActivity {

    ActivityJacksonBinding binding;

    private static final String TAG = "JacksonActivity";

    private Album album;
    private ObjectMapper mapper;

    public static void launch(Context context) {
        Intent intent = new Intent(context, JacksonActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jackson);

        mapper = new ObjectMapper();
        //缩进
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //按照变量名称自然排序顺序生成json字符串
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //如果一个属性值为null或者是empty的，则不对该属性进行序列化
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {

            @Override
            public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
                if (field.getFullName().equals("com.hm.jsondemo.beans.Artist#name")) {
                    return "Artist-Name";
                }
                return super.nameForField(config, field, defaultName);
            }

            @Override
            public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
                if (method.getAnnotated().getDeclaringClass().equals(Album.class) && defaultName.equals("title")) {
                    return "Album-Title";
                }
                return super.nameForGetterMethod(config, method, defaultName);
            }
        });

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mapper.setDateFormat(format);

        album = new Album("改革春风吹满地");
        album.setLinks(new String[]{"link1", "link2"});
        List<String> songs = new ArrayList<>();
        songs.add("So What");
        songs.add("Flamenco Sketches");
        songs.add("Freddie Freeloader");
        album.setSongs(songs);

        Artist artist = new Artist();
        artist.name = "Miles Davis";
        try {
            artist.birthday = format.parse("26-05-1926");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        album.artist = artist;

        album.addMusician("Miles Davis", "Trumpet, Band leader");
        album.addMusician("Julian Adderley", "Alto Saxophone");
        album.addMusician("Paul Chambers", "double bass");

        try {
            binding.tvJson.setText(mapper.writeValueAsString(album));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            treeModelExample();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jsonJavaDataBinding();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //processObjectHaveList();


        processListDirectly();
    }

    private void treeModelExample() throws IOException {
        // Create the node factory that gives us nodes.
        JsonNodeFactory factory = new JsonNodeFactory(false);
        // create a json factory to write the treenode as json.
        //JsonFactory jsonFactory = new JsonFactory();
        //JsonGenerator generator = jsonFactory.createGenerator(System.out);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);


        // the root node - album
        JsonNode album = factory.objectNode();
        ((ObjectNode) album).put("Album-Title", "Kind Of Blue");
        ArrayNode links = factory.arrayNode();
        links.add("link1").add("link2");
        ((ObjectNode) album).set("links", links);

        ObjectNode artist = factory.objectNode();
        artist.put("Artist-Name", "Miles Davis");
        artist.put("birthDate", "26 May 1926");
        ((ObjectNode) album).set("artist", artist);

        ObjectNode musicians = factory.objectNode();
        musicians.put("Julian Adderley", "Alto Saxophone");
        musicians.put("Miles Davis", "Trumpet, Band leader");
        ((ObjectNode) album).set("musicians", musicians);

        //mapper.writeTree(generator, album);
        binding.tvTreeModelExample.setText(mapper.writeValueAsString(album));

    }

    /**
     * 把json转化成java 对象
     */
    private void jsonJavaDataBinding() throws IOException {
        Albums albums = new Albums();
        albums.setTitle("你们不要再打了");
        Dataset[] datasets = new Dataset[10];
        for (int i = 0; i < 10; i++) {
            Dataset dataset = new Dataset();
            dataset.setAlbum_id("id=" + i);
            dataset.setAlbum_title("title:" + i);
            datasets[i] = dataset;
        }
        albums.setDataset(datasets);

        String json = mapper.writeValueAsString(albums);

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Albums albumsObj = mapper.readValue(json, Albums.class);
        Dataset[] datasetsObj = albumsObj.getDataset();
        for (Dataset dataset : datasetsObj) {
            Log.d(TAG, "jsonJavaDataBinding:" + dataset.getAlbum_title());
        }


    }

    /**
     * 处理一个Object有List的情况
     */
    private void processObjectHaveList() {
        Zoo zoo = new Zoo("London Zoo", "London");
        Lion lion = new Lion("Simba");
        Elephant elephant = new Elephant("Manny");
        zoo.addAnimal(elephant).add(lion);
        try {
            Log.d(TAG, "processList: " + mapper.writeValueAsString(zoo));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接处理一个List的情况
     */
    private void processListDirectly() {
        List<Animal> animals = new ArrayList<>();
        Lion lion = new Lion("Simba");
        Elephant elephant = new Elephant("Manny");
        animals.add(lion);
        animals.add(elephant);
        try {
            Log.d(TAG, "processListDirectly: " + mapper.writerFor(new TypeReference<List<Animal>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            }).writeValueAsString(animals));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}

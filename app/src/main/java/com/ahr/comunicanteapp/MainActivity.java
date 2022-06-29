package com.ahr.comunicanteapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahr.comunicanteapp.adapter.PostAdapter;
import com.ahr.comunicanteapp.model.Post;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_POSTS = "https://comunicante.cl/wp-json/wp/v2/posts";

    public static final String ID_POST = "1";

    private static final String TAG = "TAG";
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts = new ArrayList<>();

        recyclerView = findViewById(R.id.entry_list_recycler);

        extractPost(URL_POSTS);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(lm);

        postAdapter = new PostAdapter(posts, this);
        recyclerView.setAdapter(postAdapter);
    }

    public void extractPost(String URL) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length() ; i++){
                    // extraemos la data
                    try {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        Post post = new Post();
                        JSONObject jsonObject = response.getJSONObject(i);

                        //format date creat entry
                        Date date = simpleDateFormat.parse(jsonObject.getString("date"));
                        String entryDate = simpleDateFormat.format(date);


                        //extract id
                        post.setId_post(jsonObject.getString("id"));

                        //extract date
                        post.setDate_post(entryDate);

                        //extract title
                        JSONObject titleObject = jsonObject.getJSONObject("title");
                        post.setTitle_post(titleObject.getString("rendered"));

                        //extract link
                        post.setLink_post(jsonObject.getString("link"));

                        //extract content
                        JSONObject contentObject = jsonObject.getJSONObject("content");
                        post.setContent_post(contentObject.getString("rendered"));

                        //extract featrue image
                        JSONObject imageObject = jsonObject.getJSONObject("better_featured_image");
                        post.setFeature_image(imageObject.getString("source_url"));

                        posts.add(post);
                        postAdapter.notifyDataSetChanged();

                        //Log.d(TAG, "Titulo: " + titleObject.getString("rendered"));

                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}
package com.example.uas.System.Database;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.uas.System.Object.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostJSON {
    private RequestQueue requestQueue;
    public PostJSON(Context ctx) {
        requestQueue = Volley.newRequestQueue(ctx);
    }

    public void parseData(final PostJSONCallback callback) {
        ArrayList<Post> data = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(
                "https://jsonplaceholder.typicode.com/posts",
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);

                            data.add(new Post(
                                    object.getInt("userId"),
                                    object.getString("id"),
                                    object.getString("title"),
                                    object.getString("body")
                            ));
                        }
                        callback.onDataLoaded(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    if (error instanceof TimeoutError) {
                        callback.onDataError(true, "Request timeout.");
                    } else {
                        error.printStackTrace();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    public interface PostJSONCallback {
        void onDataLoaded(ArrayList<Post> postList);
        void onDataError(boolean status, @Nullable String errorMessage);
    }
}

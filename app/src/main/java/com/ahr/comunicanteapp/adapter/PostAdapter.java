package com.ahr.comunicanteapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahr.comunicanteapp.ActivityDetail;
import com.ahr.comunicanteapp.MainActivity;
import com.ahr.comunicanteapp.R;
import com.ahr.comunicanteapp.model.Post;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{

    private List<Post> listPost;
    private Activity activity;

    public PostAdapter(List<Post> listPost, Activity activity) {
        this.listPost = listPost;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post = listPost.get(position);
        holder.id = post.getId_post();
        holder.title = post.getTitle_post();
        holder.content = post.getContent_post();
        holder.link = post.getLink_post();
        holder.txt_date.setText(post.getDate_post());
        holder.txt_title.setText(post.getTitle_post());
        Picasso.get().load(listPost.get(position).getFeature_image()).into(holder.feature_image);
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{

        TextView txt_date, txt_title;
        ImageView feature_image;
        MaterialCardView item_post;

        String id;
        String title;
        String content;
        String link;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.date_entry);
            txt_title = itemView.findViewById(R.id.title_entry);
            feature_image = itemView.findViewById(R.id.entry_image);
            item_post = itemView.findViewById(R.id.item_post);

            item_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent (activity, ActivityDetail.class);
                    intent.putExtra(MainActivity.ID_POST, id);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("link", link);
                    activity.startActivity(intent);
                }
            });
        }

    }

}

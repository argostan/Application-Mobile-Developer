package com.example.FirmaAppSocial;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> posts;
    private Activity activity;


    public PostAdapter(Activity activity, ArrayList<Post> posts) {
        this.posts = posts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item, viewGroup, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post currentPost = posts.get(position);
        holder.title.setText(currentPost.getTitle());
        Glide.with(activity).load(currentPost.getThumbnailUrl()).into(holder.thumbnail);
        TextView container;

        holder.container.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent r = new Intent(activity, PostIDetailActivity.class);
                //serializzazione
                String objSerialized = new Gson().toJson(currentPost);
                r.putExtra("post",new Gson().toJson(currentPost));
                activity.startActivity(r);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        private final CardView container;
        private final AppCompatImageView thumbnail;
        private final TextView title;


        public PostViewHolder(View view){
            super(view);
            //agancio la post item card xml
            container = view.findViewById(R.id.card_item);

            thumbnail = view.findViewById(R.id.thumbnail);
            title = view.findViewById(R.id.tvTitle);
        }

        public AppCompatImageView getThumbnail() {
            return thumbnail;
        }

        public TextView getTitle() {
            return title;
        }
    }

}


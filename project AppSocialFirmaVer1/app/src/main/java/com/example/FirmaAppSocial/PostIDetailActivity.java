package com.example.FirmaAppSocial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.w3c.dom.Text;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PostIDetailActivity extends AppCompatActivity {
    private Post post;
    TextView title,descrizione,tags,data_pubblicazione;
    ImageView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_idetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //deseriliazzazione
        String postObject = getIntent().getStringExtra("post");
        Post post =new Gson().fromJson(postObject,Post.class);

        String linguaCorrent = Locale.getDefault().getDisplayLanguage();
        String dataFormattata = null;
        if (linguaCorrent.equals("English")) {
            dataFormattata=post.getData_pubblicazione();
        }else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date data_pubblicazione_date = new Date();
            try{
                data_pubblicazione_date = dateFormat.parse(post.getData_pubblicazione());
                //dataFormattata = dateFormat.format(data_pubblicazione_date);
            }catch (ParseException e){
                throw new RuntimeException(e);
            }

            SimpleDateFormat simpledateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dataFormattata = simpledateFormat.format(data_pubblicazione_date);

        }

        TextView title = findViewById(R.id.title_detail);
        ImageView tv = findViewById(R.id.img_detail);
        TextView data_pubblicazione = findViewById(R.id.data_pubblicazione);
        TextView descrizione = findViewById(R.id.descrizione);
        TextView tags = findViewById(R.id.tags);
        title.setText(post.getTitle());
        
        data_pubblicazione.setText(dataFormattata);
        descrizione.setText(post.getDescrizione());
        tags.setText(post.getTags());
        Glide.with(PostIDetailActivity.this).load(post.getUrl()).into(tv);

    }
    /*public boolean onOptionItemSelected(MenuItem item){

        if (item.getItemId()== R.id.home){
            finish();
            return  true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }*/
}
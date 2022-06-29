package com.ahr.comunicanteapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleView = findViewById(R.id.post_title);
        WebView contentView = findViewById(R.id.post_content);

        Intent data = getIntent();
        String title = data.getStringExtra("title");
        String content = data.getStringExtra("content");
        String link = data.getStringExtra("link");

        titleView.setText(title);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        contentView.loadDataWithBaseURL("https://comunicante.cl", content, mimeType, encoding, "");

        ImageButton btnLink = findViewById(R.id.btn_link);
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });

    }
}
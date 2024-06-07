package com.masterofanalysis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.masterofanalysis.Constants;
import com.masterofanalysis.R;

public class Activity_PrivacyPolicy extends AppCompatActivity {
    CardView cardview_disagree,cardview_agree;
    WebView webview;
    LinearLayout layout_bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        cardview_agree = findViewById(R.id.cardview_agree);
        cardview_disagree = findViewById(R.id.cardview_disagree);
        layout_bottom = findViewById(R.id.layout_bottom);
        webview = findViewById(R.id.webview);

        Intent intent = getIntent();
        if (intent != null) {
            boolean GetActivity = intent.getBooleanExtra("fragment_privacy",false);
            if (GetActivity){
                layout_bottom.setVisibility(View.GONE);
            }
        }

        if (Constants.Privacy_Policy_Url != null) {
            webview.loadUrl(Constants.Privacy_Policy_Url);
            webview.setWebViewClient(new WebViewClient());
        }
        cardview_agree.setOnClickListener(view -> {
            startActivity(new Intent(Activity_PrivacyPolicy.this, activity_Login.class).putExtra("Privacy_Policy_Status",true).putExtra("GetActivity","PrivacyPolicy"));
        });
        cardview_disagree.setOnClickListener(view -> {
            startActivity(new Intent(Activity_PrivacyPolicy.this, activity_Login.class).putExtra("Privacy_Policy_Status",false).putExtra("GetActivity","PrivacyPolicy"));
        });
    }
}
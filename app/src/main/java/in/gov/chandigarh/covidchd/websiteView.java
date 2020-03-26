package in.gov.chandigarh.covidchd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class websiteView extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_view);
        getSupportActionBar().hide();

        Intent i = getIntent();
        String url = i.getStringExtra("url");

        webView = (WebView)findViewById(R.id.website);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        Toast.makeText(websiteView.this,"Loading URL...",Toast.LENGTH_SHORT).show();
    }
}

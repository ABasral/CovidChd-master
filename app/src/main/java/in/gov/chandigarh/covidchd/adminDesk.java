package in.gov.chandigarh.covidchd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class adminDesk extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_desk);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotices);
        recyclerView.setHasFixedSize(true);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(adminDesk.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);


        myref = FirebaseDatabase.getInstance().getReference().child("/videos");
        FirebaseRecyclerAdapter<admin_modal, adminDesk.BlogViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<admin_modal, adminDesk.BlogViewHolder>(
                admin_modal.class,
                R.layout.admin_row,
                adminDesk.BlogViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(adminDesk.BlogViewHolder viewHolder, final admin_modal model, int position) {
                viewHolder.setname(model.gettitle());
                viewHolder.setvideo(model.getyoutube());

            }
        };

        recyclerView.setAdapter(recyclerAdapter);

    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView t_name;
       WebView  myWebView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            myWebView = (WebView) itemView.findViewById( R.id.webView_vid);
            t_name = (TextView)itemView.findViewById(R.id.vid_tit);

        }
        public void setvideo(String link)
        {

                myWebView.setClickable(true);
                myWebView.getSettings().setSupportMultipleWindows(true);
                myWebView.getSettings().setDisplayZoomControls(true);
                myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                myWebView.getSettings().setJavaScriptEnabled(true);


                String playVideo= "<html><body><iframe class=\"youtube-player\" type=\"text/html\" width=\"100%\" height=\"100%\" src="+link+"frameborder=\"0\"></body></html>";
                myWebView.loadData(playVideo, "text/html", "utf-8");
        }
        public void setname(String address)
        {
            t_name.setText(address);
        }


    }
}

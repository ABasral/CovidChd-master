package in.gov.chandigarh.covidchd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UTNotices extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference myref;
    ProgressBar progressBar;
    Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utnotices);
        getSupportActionBar().hide();

        backbtn = (Button) findViewById(R.id.backbut6);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UTNotices.this,Homepage.class));
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotices);
        recyclerView.setHasFixedSize(true);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(UTNotices.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);

        myref = FirebaseDatabase.getInstance().getReference().child("/notices_testing");
        FirebaseRecyclerAdapter<utnotices_modal, BlogViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<utnotices_modal, UTNotices.BlogViewHolder>(
                utnotices_modal.class,
                R.layout.utnotices_card,
                UTNotices.BlogViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(UTNotices.BlogViewHolder viewHolder, final utnotices_modal model, int position) {
                viewHolder.setname(model.getName());
//                viewHolder.setaddress(model.getLink());
                viewHolder.download.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(View v) {

                        Intent Getintent = new Intent(Intent.ACTION_VIEW,Uri.parse(model.getLink()));
                        startActivity(Getintent);
                    }
                });
            }
        };
        recyclerView.setAdapter(recyclerAdapter);

        progressBar.setVisibility(View.GONE);


    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView t_name;
        Button download;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            t_name = (TextView)itemView.findViewById(R.id.name);
            download=(Button) itemView.findViewById(R.id.open);
        }
        public void setname(String name)
        {
            t_name.setText(name+"");
        }
    }
}

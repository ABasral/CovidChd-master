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

import java.util.Locale;

public class quarantine extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference myref;
    Button backbut;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quarantine);
        getSupportActionBar().hide();
        backbut = (Button) findViewById(R.id.backbut7);
        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(quarantine.this,Homepage.class);
                startActivity(intent);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myref = FirebaseDatabase.getInstance().getReference().child("/quarantine");
        FirebaseRecyclerAdapter<hospital_modal, hospitals.BlogViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<hospital_modal, hospitals.BlogViewHolder>(
                hospital_modal.class,
                R.layout.hospital_card,
                hospitals.BlogViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(hospitals.BlogViewHolder viewHolder,final hospital_modal model, int position) {
                viewHolder.setname(model.getname());
                viewHolder.setaddress(model.getaddress());
                final String num = model.getcontact_num();
                final double lat = model.getlatitude();
                final double longi = model.getlongitude();

                viewHolder.contactb.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", num, null));
                        startActivity(intent);
                    }
                });
                viewHolder.locate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f"+"?q=<%f>,<%f>", lat, longi,lat, longi);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
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
        TextView t_address;
        Button contactb;
        Button locate;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            t_name = (TextView)itemView.findViewById(R.id.name);
            t_address = (TextView) itemView.findViewById(R.id.address);
            contactb=(Button) itemView.findViewById(R.id.contactbut);
            locate=(Button) itemView.findViewById(R.id.locate);
        }
        public void setname(String name)
        {
            t_name.setText(name+"");
        }
        public void setaddress(String address)
        {
            t_address.setText(address);
        }


    }
}

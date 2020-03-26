package in.gov.chandigarh.covidchd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Awareness extends AppCompatActivity {
    private Button backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awareness);

        getSupportActionBar().hide();

        backbtn = (Button) findViewById(R.id.backbut1);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Awareness.this, Homepage.class);
                startActivity(intent);
            }
        });
    }
}

package in.gov.chandigarh.covidchd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Guidelines extends AppCompatActivity {

    ImageView img;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);

        getSupportActionBar().hide();

        img = (ImageView) findViewById(R.id.imgv);


        back = (Button) findViewById(R.id.backbut5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Guidelines.this, Homepage.class);
                startActivity(intent);
            }
        });
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(img);
        pAttacher.update();
    }
}

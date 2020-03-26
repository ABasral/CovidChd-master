package in.gov.chandigarh.covidchd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class report_gather extends AppCompatActivity {

    private Button backbtn;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView captureButton;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_gather);
        getSupportActionBar().hide();

        backbtn = (Button) findViewById(R.id.backbut3);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(report_gather.this, Homepage.class);
                startActivity(intent);
            }
        });

        captureButton = (ImageView) findViewById(R.id.imageView);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        submit = (Button) findViewById(R.id.button3);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = (EditText) findViewById(R.id.editText);
                if(editText.getText().toString().trim().isEmpty()){
                    editText.setFocusable(true);
                    editText.setError("Enter details about the Image");
                }else{
                    Toast.makeText(report_gather.this,"Submitting...",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            captureButton.setImageBitmap(imageBitmap);
        }
    }
}

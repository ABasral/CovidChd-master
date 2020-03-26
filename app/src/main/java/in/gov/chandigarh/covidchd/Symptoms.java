package in.gov.chandigarh.covidchd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Symptoms extends AppCompatActivity {
    private Boolean breathing =false;

    private Boolean chest =false;

    private Boolean consi =false;

    private Boolean fever =false;

    private Boolean sneez =false;

    private Boolean travel =false;

    private Boolean cough = false;

    private Boolean contact = false;

    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        submit = (Button) findViewById(R.id.checksymptoms);
        submit.setText("Check Symptoms");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(cough && fever && sneez){
                    Toast.makeText(Symptoms.this,"Immediately Visit a Flu Clinic !",Toast.LENGTH_SHORT).show();
                    activitychange();
                }

                if(fever && cough && breathing){
                    Toast.makeText(Symptoms.this,"Immediately Visit a Flu Clinic !",Toast.LENGTH_SHORT).show();
                    activitychange();
                }

                if(travel || contact){
                    Toast.makeText(Symptoms.this,"Immediately Visit a Flu Clinic !",Toast.LENGTH_SHORT).show();
                    activitychange();
                }else{
                    if(breathing){
                        Toast.makeText(Symptoms.this,"Immediately Visit a Flu Clinic !",Toast.LENGTH_SHORT).show();
                        activitychange();
                    }else{
                        if(consi){
                            Toast.makeText(Symptoms.this,"Immediately Visit a Flu Clinic !",Toast.LENGTH_SHORT).show();
                            activitychange();
                        }else{
                            if(chest){
                                Toast.makeText(Symptoms.this,"Immediately Visit a FLu Clinic !",Toast.LENGTH_SHORT).show();
                                activitychange();
                            }else{
                                if(cough){
                                    Toast.makeText(Symptoms.this,"You should Visit a Flu Clinic or self isolation until further symptoms !",Toast.LENGTH_SHORT).show();

                                }else{
                                    if(sneez){
                                        Toast.makeText(Symptoms.this,"You should Visit a Flu Clinic or self isolation until further symptoms !",Toast.LENGTH_SHORT).show();
                                    }else{
                                        if(fever){
                                            Toast.makeText(Symptoms.this,"You must Visit a Flu Clinic or self isolation until further symptoms !",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Symptoms.this,"Choose an option first  !",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    public void activitychange(){
        startActivity(new Intent(Symptoms.this,fluclinics.class));
    }

    public void onCheckboxClicked(View view){

        switch (view.getId()){
            case R.id.breathing:
                if(breathing){
                    breathing = false;
                    TextView textView = (TextView) findViewById(R.id.breathing);
                    textView.setBackgroundResource(R.drawable.grad5);
                }else{
                    breathing = true;
                    TextView textView = (TextView) findViewById(R.id.breathing);
                    textView.setBackgroundResource(R.drawable.grad4);
                }
                break;
            case R.id.chestpain:
                if(chest){
                    chest = false;
                    TextView textView = (TextView) findViewById(R.id.chestpain);
                    textView.setBackgroundResource(R.drawable.grad5);
                }else{
                    chest = true;
                    TextView textView = (TextView) findViewById(R.id.chestpain);
                    textView.setBackgroundResource(R.drawable.grad4);
                }
                break;

            case R.id.consi:
                if(consi){
                    consi = false;
                    TextView textView = (TextView) findViewById(R.id.consi);
                    textView.setBackgroundResource(R.drawable.grad5);
                }else{
                    consi = true;
                    TextView textView = (TextView) findViewById(R.id.consi);
                    textView.setBackgroundResource(R.drawable.grad4);
                }
                break;
            case R.id.fever:
                if(fever){
                    fever = false;
                    TextView textView = (TextView) findViewById(R.id.fever);
                    textView.setBackgroundResource(R.drawable.grad5);
                }else{
                    fever = true;
                    TextView textView = (TextView) findViewById(R.id.fever);
                    textView.setBackgroundResource(R.drawable.grad4);
                }
                break;
            case R.id.cough:
                if(cough){
                    cough = false;
                    TextView textView = (TextView) findViewById(R.id.cough);
                    textView.setBackgroundResource(R.drawable.grad5);
                }else{
                    cough = true;
                    TextView textView = (TextView) findViewById(R.id.cough);
                    textView.setBackgroundResource(R.drawable.grad4);
                }
                break;
            case R.id.travel:
                if(travel){
                    travel = false;
                    TextView textView = (TextView) findViewById(R.id.travel);
                    textView.setBackgroundResource(R.drawable.grad5);
                }else{
                    travel = true;
                    TextView textView = (TextView) findViewById(R.id.travel);
                    textView.setBackgroundResource(R.drawable.grad4);
                }
                break;
            case R.id.sneez:
                if(sneez){
                    sneez = false;
                    TextView textView = (TextView) findViewById(R.id.sneez);
                    textView.setBackgroundResource(R.drawable.grad5);
                }else{
                    sneez = true;
                    TextView textView = (TextView) findViewById(R.id.sneez);
                    textView.setBackgroundResource(R.drawable.grad4);
                }
                break;
            case R.id.contactPerson:
                if(contact){
                    contact = false;
                    TextView textView = (TextView) findViewById(R.id.contactPerson);
                    textView.setBackgroundResource(R.drawable.grad5);
                }else{
                    contact = true;
                    TextView textView = (TextView) findViewById(R.id.contactPerson);
                    textView.setBackgroundResource(R.drawable.grad4);
                }

        }

    }
}

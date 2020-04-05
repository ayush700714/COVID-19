package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
     private FirebaseAuth firebaseAuth;
     private EditText edit;
     private RadioGroup gendergroup,tempgroup,histgroup;
     private RadioButton genderbt,tempbt,histbt;
     private CheckBox cough,smell,sore,weak,dfb,none;
     private CheckBox dia,bp,heart,lung,nota;
     private FloatingActionButton fab;
     private  int histId,tempId,genderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       firebaseAuth = FirebaseAuth.getInstance();

       edit = (EditText)findViewById(R.id.age);

     gendergroup = (RadioGroup)findViewById(R.id.gen);
        genderId =  gendergroup.getCheckedRadioButtonId();

        tempgroup = (RadioGroup)findViewById(R.id.temperature);
         tempId =  tempgroup.getCheckedRadioButtonId();

        histgroup = (RadioGroup)findViewById(R.id.history);
         histId =  histgroup.getCheckedRadioButtonId();

        genderbt = (RadioButton)findViewById(genderId);
        tempbt = (RadioButton)findViewById(tempId);
        histbt = (RadioButton)findViewById(histId);

        cough=(CheckBox)findViewById(R.id.cough);
         smell=(CheckBox)findViewById(R.id.smell);
         sore=(CheckBox)findViewById(R.id.sore);
        weak=(CheckBox)findViewById(R.id.weak);
        dfb=(CheckBox)findViewById(R.id.bl);
        none=(CheckBox)findViewById(R.id.none);

        dia=(CheckBox)findViewById(R.id.dia);
        bp=(CheckBox)findViewById(R.id.bp);
        heart=(CheckBox)findViewById(R.id.heart);
        lung=(CheckBox)findViewById(R.id.lung);
        nota=(CheckBox)findViewById(R.id.nota);

        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int age = Integer.parseInt(edit.getText().toString().trim());
               Boolean diaB = dia.isChecked();
               Boolean heartB = heart.isChecked();
             String factor = riskFactor(age,diaB,heartB);
               int count=0;
               if(cough.isChecked())
               {
                   count++;
               }
                if(smell.isChecked())
               {
                   count++;
               }
                if(sore.isChecked())
               {
                   count++;
               }
              if(weak.isChecked())
               {
                   count++;
               }
                if(dfb.isChecked())
               {
                   count++;
               }
            if(none.isChecked())
               {
                   count--;
               }
           String prob = probabilityChecker(count);
            Intent intent = new Intent(MainActivity.this,ProgressActivity.class);
            intent.putExtra("prob",prob);
            intent.putExtra("factor",factor);
            startActivity(intent);
            }
        });

    }
    public String riskFactor(int age,Boolean a,Boolean b)
    {
        String risk= "";
        if(age>70)
        {
            risk="HIGH";
        }
        else if(age>50)
        {
            if(a || b)
            {
                risk="HIGH";
            }
            else if(a && b)
            {
                risk="MEDIUM";
            }
            else
            {
                risk="LOW";
            }
        }
        else if(age>40 && age<50)
        {
           if(a && b)
           {
               risk="HIGH";
           }
           else
           {
               risk="LOW";
           }
        }
        else
        {
            risk="LOW";
        }
        return risk;
    }
    public String probabilityChecker(int count)
    {
        String probability= "";
        if(histId!=R.id.no)
        {
            Log.i("check",String.valueOf(tempId));
            Log.i("check",String.valueOf(genderId));
            Log.i("check",String.valueOf(histId));
            Log.i("check",String.valueOf(R.id.highfever));
            if(tempId==R.id.highfever)
            {
                Log.i("check","231h");
                probability="HIGH";
            }
            else if(tempId==R.id.fever)
            {
                Log.i("check","231m");
                if(count>=1)
                {
                    probability="HIGH";
                }
                else
                {
                    probability="MEDIUM";
                }
            }
            else if(tempId==R.id.normal)
            {
                Log.i("check","231l");
                if(count>=2)
                {
                    probability="HIGH";
                }
                else if(count==1)
                {
                    probability="MEDIUM";
                }
                else
                {
                    probability="LOW";
                }
            }
        }
        else
        {
            Log.i("check","124");
            if(tempId==R.id.highfever)
            {
                if(count>=2)
                {
                    probability="HIGH";
                }
                else
                {
                    probability="MEDIUM";
                }
            }
            else if(tempId==R.id.fever)
            {
                if(count>=2)
                {
                    probability="MEDIUM";
                }
                else
                {
                    probability="LOW";
                }
            }
            else
            {
                probability="LOW";
            }
        }
        return probability;
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null)
        {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}

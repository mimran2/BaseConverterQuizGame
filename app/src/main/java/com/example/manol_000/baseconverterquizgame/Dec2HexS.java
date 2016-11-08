package com.example.manol_000.baseconverterquizgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class Dec2HexS extends AppCompatActivity {
    //globals needed
    int scoreD2HS;
    int attemptsD2HS;
    TextView question;
    private boolean tooBig;
    private boolean tooSmall;
    private int randomNum;
    private TextView scoreD2HsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dec2_hex_s);
        //left spinner for signed values
        Spinner spinnerLS = (Spinner) findViewById(R.id.spinnerLeftS);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterLS = ArrayAdapter.createFromResource(this,
                R.array.hexVals, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterLS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerLS.setAdapter(adapterLS);
        //  String leftHex=spinnerL.getSelectedItem().toString();


        //set the right spinner for signed values
        Spinner spinnerRS = (Spinner) findViewById(R.id.spinnerRightS);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterRS = ArrayAdapter.createFromResource(this,
                R.array.hexVals, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterRS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerRS.setAdapter(adapterRS);
        //  String rightHex=spinnerR.getSelectedItem().toString();


        //get extras
        Bundle B=getIntent().getExtras();
        //set score
        scoreD2HsTV=(TextView)findViewById(R.id.scoreD2HS);
        String S1=B.getString("score");
        String S2=B.getString("attempts");
        scoreD2HsTV.setText("Score: "+S1+"/"+S2);

       //get the ints as well to update accordingly
        scoreD2HS=Integer.parseInt(S1);
        attemptsD2HS=Integer.parseInt(S2);

        question=(TextView)findViewById(R.id.questionD2HS);

        question.setText(genQuestion());
        question.setTextSize(20);

    }

    public String genQuestion()
    {
        Random rand= new Random();

        randomNum=rand.nextInt(401)-200;//random value i chose
        String q="For Signed (two's complement) numbers, what is the 8-bit hex value for the decimal value "+randomNum+"?";
        tooBig=false;
        tooSmall=false;
        if(randomNum>127)
        {
            tooBig=true;
        }
        else if(randomNum<-128)
        {
            tooSmall=true;
        }
        return q;
    }


    boolean checkAns=true;
    int radioId=0;//initialize value for
    public void radioCheck(View v) {
        radioId = v.getId();
       // checked = ((RadioButton) v).isChecked();
    }
    public void checkAnswersD2HS(View v)
    {
        Button b= (Button)findViewById(R.id.checkD2HS);
        TextView answer=(TextView)findViewById(R.id.answerD2HS);
        Spinner spR= (Spinner)findViewById(R.id.spinnerRightS);
        Spinner spL =(Spinner)findViewById(R.id.spinnerLeftS);
        if(checkAns)
        {
            String sLeft=((Spinner)findViewById(R.id.spinnerLeftS)).getSelectedItem().toString();
            String sRight=((Spinner)findViewById(R.id.spinnerRightS)).getSelectedItem().toString();
            String ans=""+Integer.toHexString(randomNum);
            ans=ans.replace("f","");//remove any leading fs
            switch(radioId)
            {
                case 0:
                    Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                    if(tooSmall)
                    {
                        answer.setText("Correct Answer: Too Small");
                        attemptsD2HS+=1;
                    }
                    else if(tooBig)
                    {
                        answer.setText("Correct Answer: Too Big");
                        attemptsD2HS+=1;
                    }
                    else
                    {
//                        ans=""+Integer.toHexString(randomNum);
//                        ans=ans.replace("f","");//remove any leading fs
                        answer.setText("Correct Answer: "+ ans);
                        attemptsD2HS+=1;
                    }

                case R.id.tooLargeS:
                    if(tooBig)
                    {
                        answer.setText("Correct");
                        scoreD2HS+=1;
                        attemptsD2HS+=1;
                    }
                    else
                    {
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                        answer.setText(ans);
                        attemptsD2HS+=1;
                    }

                    break;


                case R.id.tooSmallS:
                    if(tooSmall)
                    {
                        answer.setText("Correct");
                        scoreD2HS+=1;
                        attemptsD2HS+=1;
                    }
                    else
                    {
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                        //set the correct answer
                        answer.setText(ans);
                        attemptsD2HS+=1;
                    }
                    break;

                case R.id.valueS:
                    //get left 4 bits
                    String a=sLeft+sRight;

                     ans=""+Integer.toHexString(randomNum);

                    if(sLeft.equals("0"))
                    {
                        a=sRight;//just get the right 4 bits
                    }
                    ans=ans.replace("f","");//remove any leading fs
                    ans=ans.replace("0x","");//get rid of the 0x at the beginning
                    Toast.makeText(this, "this is ur input "+a, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "this is the correct ans "+ans, Toast.LENGTH_LONG).show();

                    if(a.equalsIgnoreCase(ans))
                    {
                        //answer is correct
                        scoreD2HS+=1;
                        attemptsD2HS+=1;
                        answer.setText("Correct");
                    }
                    else
                    {
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                        answer.setText("Correct Answer: "+ans);
                        attemptsD2HS+=1;
                    }
                    break;


            }
             scoreD2HsTV.setText("Score: "+scoreD2HS+"/"+attemptsD2HS);
            checkAns=false;
            b.setText("CLICK FOR ANOTHER QUESTION");
        }
        else
        {
            checkAns=true;
            answer.setText("               ");
            RadioButton R=(RadioButton)findViewById(radioId);
            if(R!=null)
            {
                R.setChecked(false);
            }

            spL.setSelection(0);
            spR.setSelection(0);
            question.setText(genQuestion());
            b.setText("CHECK MY ANSWER");
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("score",Integer.toString(scoreD2HS));
        intent.putExtra("attempts",Integer.toString(attemptsD2HS));
        // Toast.makeText(this, "res activity 2 "+finalRes, Toast.LENGTH_LONG).show();
        setResult(2,intent);
        finish();
    }


}

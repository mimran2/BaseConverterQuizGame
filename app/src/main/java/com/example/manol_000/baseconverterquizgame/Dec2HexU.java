package com.example.manol_000.baseconverterquizgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Dec2HexU extends AppCompatActivity {
    int scoreD2Hu;
    int attemptsD2Hu;
    TextView question;
    private boolean tooBig;
    private boolean tooSmall;
    private int randomNum;
    TextView scoreD2HuTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dec2_hex_u);
        //set the left spinner
        Spinner spinnerL = (Spinner) findViewById(R.id.spinnerLeft);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterL = ArrayAdapter.createFromResource(this,
                R.array.hexVals, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerL.setAdapter(adapterL);
      //  String leftHex=spinnerL.getSelectedItem().toString();


        //set the right spinner
        Spinner spinnerR = (Spinner) findViewById(R.id.spinnerRight);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterR = ArrayAdapter.createFromResource(this,
                R.array.hexVals, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerR.setAdapter(adapterR);
      //  String rightHex=spinnerR.getSelectedItem().toString();

        //set the score in this window
        Bundle B=getIntent().getExtras();

        //set score
        String S1=B.getString("score");
        String S2=B.getString("attempts");
        scoreD2HuTV=(TextView)findViewById(R.id.scoreD2HU);
        scoreD2HuTV.setText("Score: "+S1+"/"+S2);

        //get the ints as well to update accordingly
        scoreD2Hu=Integer.parseInt(S1);
        attemptsD2Hu=Integer.parseInt(S2);

        question=(TextView)findViewById(R.id.questionD2H);
        question.setText(genQuestion());
        question.setTextSize(20);
    }
    public String genQuestion()
    {
        Random rand= new Random();

        randomNum=rand.nextInt(601)-200;//random value i chose
        String q="For Unsigned numbers, what is the 8-bit hex value for the decimal value "+randomNum+"?";
        tooBig=false;
        tooSmall=false;
        if(randomNum>255)
        {
            tooBig=true;
        }
        else if(randomNum<0)
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

    public void checkAns(View v)
    {
        Button b= (Button)findViewById(R.id.checkD2HU);
        TextView answer=(TextView)findViewById(R.id.answerD2Hu);
        Spinner spR= (Spinner)findViewById(R.id.spinnerRight);
        Spinner spL =(Spinner)findViewById(R.id.spinnerLeft);
        if(checkAns)
        {
            String Left=((Spinner)findViewById(R.id.spinnerLeft)).getSelectedItem().toString();
            String Right=((Spinner)findViewById(R.id.spinnerRight)).getSelectedItem().toString();

            String ans=""+Integer.toHexString(randomNum);
            ans=ans.replace("f","");//remove any leading fs
            switch(radioId)
            {
                case 0:
                    if(tooSmall)
                    {
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                        answer.setText("Correct Answer: Too Small");
                        attemptsD2Hu+=1;
                    }
                    else if(tooBig)
                    {
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                        answer.setText("Correct Answer: Too Big");
                        attemptsD2Hu+=1;
                    }
                    else
                    {
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                        answer.setText("Correct Answer: 0x"+ ans);
                        attemptsD2Hu+=1;
                    }

                case R.id.tooLarge:
                    if(tooBig)
                    {
                        answer.setText("Correct");
                        scoreD2Hu+=1;
                        attemptsD2Hu+=1;
                    }
                    else
                    {
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                        answer.setText("Correct Answers: 0x" +ans);
                        attemptsD2Hu+=1;
                    }

                    break;


                case R.id.tooSmall:
                    if(tooSmall)
                    {
                        answer.setText("Correct");
                        scoreD2Hu+=1;
                        attemptsD2Hu+=1;
                    }
                    else
                    {
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                        //set the correct answer
                        answer.setText("Correct Answer: 0x"+ans);
                        attemptsD2Hu+=1;
                    }
                    break;

                case R.id.value:
                    //get left 4 bits
                    String a=Left+Right;

                    ans=""+Integer.toHexString(randomNum);

                    if(Left.equals("0"))
                    {
                        a=Right;//just get the right 4 bits
                    }
                    ans=ans.replace("f","");//remove any leading fs
                    ans=ans.replace("0x","");//get rid of the 0x at the beginning
//                    Toast.makeText(this, "this is ur input "+a, Toast.LENGTH_LONG).show();
//                    Toast.makeText(this, "this is the correct ans "+ans, Toast.LENGTH_LONG).show();

                    if(a.equalsIgnoreCase(ans))
                    {
                        //answer is correct
                        scoreD2Hu+=1;
                        attemptsD2Hu+=1;
                        answer.setText("Correct");
                    }
                    else
                    {
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                        answer.setText("Correct Answer: 0x"+ans);
                        attemptsD2Hu+=1;
                    }
                    break;


            }
            scoreD2HuTV.setText("Score: "+scoreD2Hu+"/"+attemptsD2Hu);
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
        intent.putExtra("score",Integer.toString(scoreD2Hu));
        intent.putExtra("attempts",Integer.toString(attemptsD2Hu));
        setResult(2,intent);
        finish();
    }
}

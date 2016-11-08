package com.example.manol_000.baseconverterquizgame;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class Hex2Dec extends AppCompatActivity {
    String answerU;
    String answerS;
    int scoreH2D;
    int attemptsH2D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex2_dec);

        //get the score from the main
        Bundle bundle = getIntent().getExtras();
        //set the score in this window
        TextView sH2D=(TextView)(findViewById(R.id.scoreH2D));
        String S1=bundle.getString("score");
        String S2=bundle.getString("attempts");
         //Toast.makeText(this, "score "+S1, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "attempts "+S2 , Toast.LENGTH_LONG).show();
        scoreH2D=Integer.parseInt(S1);
        attemptsH2D=Integer.parseInt(S2);

        String S= "Score: "+scoreH2D+"/"+attemptsH2D;
        sH2D.setText(S);

        TextView questionTxt= (TextView)(findViewById(R.id.question));
        questionTxt.setText(getQuestion());

    }
    public String getQuestion()
    {
        Random rand= new Random();
        int upper4 = rand.nextInt(16);//get the upper 4 bits
        int lower4= rand.nextInt(16);//lower 4 bits
        String hexVal;
        if(upper4==0)
        {
            hexVal=""+Integer.toHexString(lower4);//ommit top msb if zero
        }
        else
        {
            hexVal=Integer.toHexString(upper4)+""+Integer.toHexString(lower4);
        }
        String hex="0x"+hexVal;//string value

        String q="What are the signed and unsigned values for the 8-bit value "+hex+"?";
        doAllH2D(hexVal);
        return q;
    }

    public void doAllH2D(String hexVal)
    {
        //gets all the answers ready
        //get the int value
       answerU= Integer.toString(Integer.parseInt(hexVal, 16), 10);//convert from hex to dec;//
        int a=Integer.parseInt(answerU);//get the bits fo the unsigned value
        a=a&0x80;//check for leading bit

      //answer for signed values
      if(a==128)//negative leading bit if a is 1xxxxxx and 0x80 is 1000 0000 then anded together they should be 1000 0000
      {
          int intermediate=Integer.parseInt(answerU);//takes the value as an int
          intermediate-=256;
          answerS=Integer.toString(intermediate);
      }
      else
      {
        answerS=answerU;
      }

    }
    boolean check=true;
    public void checkH2D(View view)
    {
        //to display answers
        TextView signDisplay=(TextView)findViewById(R.id.correctSigned);
        TextView unsignDisplay=(TextView)findViewById(R.id.correctUnsigned);

        //to retrieve answers
        TextView answerH2DS= (TextView)findViewById(R.id.signedInput);
        TextView answerH2DU=(TextView)findViewById(R.id.unsignedInput);
        if(check)
        {

            if(answerH2DS==null)
            {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                signDisplay.setText(answerS);
                attemptsH2D+=1;
            }
            else if(answerH2DS.getText().toString().equals(answerS))
            {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                signDisplay.setText("Signed Correct");
                attemptsH2D+=1;
                scoreH2D+=1;
            }
            else
            {
                signDisplay.setText("Signed = "+answerS);
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                attemptsH2D+=1;
            }

            //check for correct unsigned representation
            if(answerH2DU==null)
            {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                unsignDisplay.setText("Unsigned = "+ answerU);
                attemptsH2D+=1;
            }
            else if(answerH2DU.getText().toString().equals(answerU))
            {
                Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();
                unsignDisplay.setText("Unsigned Correct");
                attemptsH2D+=1;
                scoreH2D+=1;
            }
            else
            {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_LONG).show();
                unsignDisplay.setText("Unsigned "+answerU);
                attemptsH2D+=1;
            }
            String S= "Score: "+scoreH2D+"/"+attemptsH2D;
            TextView sH2D=(TextView)(findViewById(R.id.scoreH2D));
            sH2D.setText(S);
            Button b1=(Button)findViewById(R.id.checkButton);
            b1.setText("CLICK FOR ANOTHER QUESTION");
            check=false;
        }
        else if(check==false)
        {
            TextView questionTxt= (TextView)(findViewById(R.id.question));
            questionTxt.setText(getQuestion());
            Button b1=(Button)findViewById(R.id.checkButton);
            b1.setText("CHECK MY ANSWER");
            unsignDisplay.setText("             ");
            signDisplay.setText("             ");
            answerH2DS.setText("");
            answerH2DU.setText("");
            answerH2DS.setHint("Decimal Number");
            answerH2DU.setHint("Decimal Number");

            check=true;//set back toggle to check for answers
        }


    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("score",Integer.toString(scoreH2D));
        intent.putExtra("attempts",Integer.toString(attemptsH2D));
        // Toast.makeText(this, "res activity 2 "+finalRes, Toast.LENGTH_LONG).show();
        setResult(2,intent);
        finish();
    }



}

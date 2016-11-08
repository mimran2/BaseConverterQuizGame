package com.example.manol_000.baseconverterquizgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int score=0;//wins
    int attempts=0;//tries
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView scoreTV= (TextView)findViewById(R.id.scoreMain);
        scoreTV.setText("Score: "+score+"/"+attempts);
        Spinner spinner = (Spinner) findViewById(R.id.optionSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.qTypeArray, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void goFunction(View v)
    {
        String optionSelected=((Spinner)findViewById(R.id.optionSpinner)).getSelectedItem().toString();
        //get the score
//        Toast.makeText(this, message,
//                Toast.LENGTH_LONG).show();
        switch (optionSelected){
            case "Hex to Decimal":
                Intent intentD2H = new Intent(this, Hex2Dec.class);
                intentD2H.putExtra("score", Integer.toString(score));
                intentD2H.putExtra("attempts",Integer.toString(attempts));
               // Toast.makeText(this, intentD2H.getExtras().getString("score"), Toast.LENGTH_LONG).show();
                startActivityForResult(intentD2H,2);
                break;

            case "Decimal to Hex Signed":
                Intent intentH2Ds = new Intent(this, Dec2HexS.class);
                intentH2Ds.putExtra("score", ""+score);

                intentH2Ds.putExtra("attempts", ""+attempts);
                startActivityForResult(intentH2Ds,2);
                break;

            case "Decimal to Hex Unsigned":
                Intent intentH2Du = new Intent(this, Dec2HexU.class);
                intentH2Du.putExtra("score", ""+score);
                intentH2Du.putExtra("attempts",""+attempts);
                startActivityForResult(intentH2Du,2);
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String sRes=data.getStringExtra("score");
            score=Integer.parseInt(sRes);
            String aRes=data.getStringExtra("attempts");
            attempts=Integer.parseInt(aRes);
            TextView scoreTV= (TextView)findViewById(R.id.scoreMain);
            scoreTV.setText("Score: "+sRes+"/"+aRes);//set the score in the main
        }
    }

}

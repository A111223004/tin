package com.example.explicitintentdemo2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NonNls;

import java.lang.annotation.Documented;

public class MainActivity extends AppCompatActivity {

    private TextView output;

    ActivityResultContracts launcher = registerForActivityResult(new ResultContract(),
        new ActivityResultCallback<String>() {
            @Override
            public void onActivityResult(String o) {
                output.setText("計算結果: " + o);
            }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.lblOutput);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               launcher.launch(true);
            }
        });
    }

    class ResultContract extends ActivityResultContracts<String, String> {
        @NonNls
        @Override
        public Intent createIntent(@NonNls Context context, String s) {
            Intent intent = new Intent(MainActivity.this, OpActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("OPERANDO1", ((EditText) findViewById(R.id.txtOpd1)).getText().toString());
            bundle.putString("OPERANDO2", ((EditText) findViewById(R.id.txtOpd2)).getText().toString());
            intent.putExtras(bundle);
            Log.v("CHUCK", s);
            return intent;
        }

        @Override
        public String parseResult(int i, @NonNls Intent intent) {
            Bundle bundle = intent.getExtras();
            Double result = bundle.getDouble("Result");
            return result.toString();
        }
    }
}
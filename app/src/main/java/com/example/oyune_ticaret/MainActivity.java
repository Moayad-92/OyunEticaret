package com.example.oyune_ticaret;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUser(Name.getText().toString(),Password.getText().toString());

            }
        });


    }

    private void checkUser (String userName ,String password) {

        if ((userName.equals("moayad")) && (password.equals("123qwe"))){
            Intent intent = new Intent(MainActivity.this,GamesList.class);
            startActivity(intent);
        }

        else {

            Context context = getApplicationContext();
            CharSequence text = "Please Check Username and Password";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();

        }



    }
}

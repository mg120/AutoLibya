package com.autoliba.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.autoliba.R;

public class LoginType extends AppCompatActivity implements View.OnClickListener {

    Button login_btn, member_sign_btn, autoShow_sign_btn;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);

        login_btn = findViewById(R.id.login_btn_id);
        member_sign_btn = findViewById(R.id.member_sign_btn_id);
        autoShow_sign_btn = findViewById(R.id.autoShow_sign_btn_id);

        login_btn.setOnClickListener(this);
        member_sign_btn.setOnClickListener(this);
        autoShow_sign_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login_btn_id:
//                startActivity(new Intent(LoginType.this, LogIn.class));
                startActivity(new Intent(LoginType.this, LogIn.class));
                break;
            case R.id.member_sign_btn_id:
                // For User Register ...
                type = 1;
                Intent intent = new Intent(LoginType.this, SignUp.class);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
            case R.id.autoShow_sign_btn_id:
                // For Auto Register ....
                type = 2;
                Intent intent2 = new Intent(LoginType.this, SignUp.class);
                intent2.putExtra("type", type);
                startActivity(intent2);
                break;
        }
    }

}

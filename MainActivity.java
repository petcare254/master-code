package com.example.ksb.petcare3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button LogInButton, RegisterButton;
    EditText Email, Password;
    String EmailHolder, PasswordHolder;
    String a = "admin@gmail.com";
    String b = "admin";
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogInButton = (Button) findViewById(R.id.buttonLogin);

        RegisterButton = (Button) findViewById(R.id.buttonRegister);

        Email = (EditText) findViewById(R.id.editEmail);
        Password = (EditText) findViewById(R.id.editPassword);

        sqLiteHelper = new SQLiteHelper(this);

        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Email.getText().toString().matches(a)) && (Password.getText().toString().matches(b))) {
                    //  Intent intent = new Intent(LoginActivity.this, AdminHome.class);
                    //startActivity(intent);

                } else {
                    // Calling EditText is empty or no method.
                    CheckEditTextStatus();

                    // Calling login method.
                    LoginFunction();
                }


            }
        });

        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(MainActivity.this, registration.class);
                startActivity(intent);

            }
        });

    }

    // Login function starts from here.
    public void LoginFunction() {


        if (EditTextEmptyHolder) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_Password));

                    // Closing cursor.
                    cursor.close();
                }
            }

            // Calling method to check final result ..
            CheckFinalResult();

        } else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(MainActivity.this, "Please Enter UserName or Password.", Toast.LENGTH_LONG).show();

        }

    }

    // Checking EditText is empty or not.
    public void CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            EditTextEmptyHolder = false;

        } else {

            EditTextEmptyHolder = true;
        }
    }

    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult() {

        if (TempPassword.equalsIgnoreCase(PasswordHolder)) {

            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();


            // Going to Dashboard activity after login success message.
            Intent intent = new Intent(MainActivity.this, userhome.class);

            // Sending Email to Dashboard Activity using intent.
            // intent.putExtra(UserEmail, EmailHolder);

            startActivity(intent);


        } else {

            Toast.makeText(MainActivity.this, "UserName or Password is Wrong, Please Try Again.", Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND";

    }
}
//    public void CheckAdmin()
//    {
//        if(EmailHolder.matches(a))
//        {
//            Intent intent = new Intent(LoginActivity.this, AdminHome.class);
//            startActivity(intent);
//
//        }
//    }



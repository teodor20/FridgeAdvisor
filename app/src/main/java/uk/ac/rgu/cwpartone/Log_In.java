package uk.ac.rgu.cwpartone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class Log_In extends AppCompatActivity {
    public  static myDatabase MyDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //Sets action bar with title "Fridge World"
        getSupportActionBar().setTitle("Fridge World");

        //Sets the button for the signing in
        Button signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrieves the users inputs for username and password
                EditText userID = findViewById(R.id.userID);
                EditText userPass = findViewById(R.id.userPassword);

                //Converts users inputs into strings
                String username = userID.getText().toString();
                String password = userPass.getText().toString();

                //Loads the users database and retrieves the names and passwords
                MyDatabase = Room.databaseBuilder(getApplicationContext(), myDatabase.class,"users").allowMainThreadQueries().build();
                List<User> users = MyDatabase.myDao().getUsers();
                for(User usr : users) {
                    String uname = usr.getName();
                    String upass = usr.getPassword();
                    //If the username and password the user entered matches an entry in database the user logs in
                    if (username.equals(uname) && password.equals(upass)){
                        startActivity(new Intent(Log_In.this, MainActivity.class));
                        Toast.makeText(Log_In.this, "Signed in", Toast.LENGTH_SHORT).show();
                    }
                    if (username.equals("Admin") && password.equals("Admin")){
                        startActivity(new Intent(Log_In.this, Admin.class));
                        Toast.makeText(Log_In.this, "Admin View", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Sets button for signing up
        Button signUpPage = findViewById(R.id.signUpPage);
        //Links to the sign up page when the sign up button is selected
        signUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Log_In.this, signUp.class));
            }
        });
    }
}

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

public class signUp extends AppCompatActivity {
    public  static myDatabase MyDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Sets action bar with title "Sign Up" and adds option to go back
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Sets button for signing up
        final Button signUp = findViewById(R.id.signUpBtn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrieves the users inputs for username and password
                EditText userID = findViewById(R.id.userID);
                EditText passID = findViewById(R.id.passID);
                EditText passIDcheck = findViewById(R.id.passIDcheck);

                //Converts the users inputs into strings
                String pass1 = passID.getText().toString();
                String pass2 = passIDcheck.getText().toString();

                //Checks if username is longer than 3 characters
                if (userID.length() >=4){
                    //Checks if password is longer than 5 characters
                    if (pass1.length() >=6){
                        //Checks if the passwords match then saves the users info and brings you back to the log in page
                        if (pass1.equals(pass2)){

                            String userid = userID.getText().toString();

                            int x = (int)(Math.random()*((99999-10000)+1))+10000;
                            String y = String.valueOf(x);


                            MyDatabase = Room.databaseBuilder(getApplicationContext(), myDatabase.class,"users").allowMainThreadQueries().build();
                            User user = new User();
                            user.setId(x);
                            user.setName(userid);
                            user.setPassword(pass2);
                            MyDatabase.myDao().addUser(user);

                            String info = "";

                            List<User> users = MyDatabase.myDao().getUsers();
                            for(User usr : users){
                                String uname = usr.getName();
                                String upass = usr.getPassword();
                                info = info+"\n\n"+"Name: "+uname+"\n"+"Password: "+upass;
                            }

                            Toast.makeText(uk.ac.rgu.cwpartone.signUp.this, "Signed Up", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(uk.ac.rgu.cwpartone.signUp.this, Log_In.class));
                        }
                        //Alerts if passwords do not match
                        else {
                            Toast.makeText(uk.ac.rgu.cwpartone.signUp.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Alerts if password does not contain more than 5 characters
                    else {
                        Toast.makeText(uk.ac.rgu.cwpartone.signUp.this, "Password must contain more than 5 characters", Toast.LENGTH_SHORT).show();
                    }
                }
                //Alerts if username does not contain more than 3 characters
                else {
                    Toast.makeText(uk.ac.rgu.cwpartone.signUp.this, "Username must be longer than 3 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

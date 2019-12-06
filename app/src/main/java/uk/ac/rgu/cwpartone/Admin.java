package uk.ac.rgu.cwpartone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    public  static myDatabase MyDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Sets action bar with title "Sign Up" and adds option to go back
        getSupportActionBar().setTitle("Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView userinfo = findViewById(R.id.userList);
        String info = userinfo.getText().toString();
        info = "";

        //Loads the users database and retrieves the names and passwords
        MyDatabase = Room.databaseBuilder(getApplicationContext(), myDatabase.class,"users").allowMainThreadQueries().build();
        List<User> users = MyDatabase.myDao().getUsers();
        for(User usr : users) {
            String uname = usr.getName();
            String upass = usr.getPassword();
            int IDnum = usr.getId();
            String ID = Integer.toString(IDnum);
            info = info + "\n\n" + "Name: " + uname + "\n" + "Password: " + upass+ "\n" + "ID: " + ID;
            //userinfo.setText("\n\nUsername: "+uname+"\nPassword: "+upass);
        }
        userinfo.setText(info);

    }
}

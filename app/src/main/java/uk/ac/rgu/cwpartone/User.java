package uk.ac.rgu.cwpartone;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Creates the table users and all it's columns with a primary key
@Entity(tableName = "users")
public class User {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "username")
    private String name;

    @ColumnInfo(name = "userpassword")
    private String password;

    @ColumnInfo(name = "userEmail")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}



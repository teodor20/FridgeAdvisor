package uk.ac.rgu.cwpartone;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 1)
public abstract class myDatabase extends RoomDatabase {

    public abstract myDao myDao();

}

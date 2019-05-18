package edu.stts.quizmdp6541;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class appDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}

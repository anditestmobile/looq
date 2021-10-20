package sg.carpark.looq.utils.application;

import android.app.Application;
import android.content.Context;

import sg.carpark.looq.data.database.AppDatabase;

/**
 * Created by TED on 31-Aug-20
 */
public class App extends Application {

    @Override
    public void onCreate() {
        //TODO uncomment untuk aktifin db local
        AppDatabase.init(this);
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}

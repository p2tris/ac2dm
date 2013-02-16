package ut.ee.asycn;
 
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;

public class C2DManager {

        Activity main;
        private String emailOfSender = "davidpaniagualiv@gmail.com";

        public C2DManager(Activity main) {
                this.main = main;
        }

        public void registerDevice() {
                Intent registrationIntent = new Intent(
                                "com.google.android.c2dm.intent.REGISTER");
                registrationIntent.putExtra("app",
                                PendingIntent.getBroadcast(main, 0, new Intent(), 0)); // boilerplate
                registrationIntent.putExtra("sender", emailOfSender);
                main.startService(registrationIntent);
        }
}

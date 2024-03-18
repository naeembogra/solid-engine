import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private Handler handler;
    private boolean isAnnouncementEnabled = false;
    private TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextView = findViewById(R.id.timeTextView);
        handler = new Handler();
        
        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("bn_IN")); // Bengali (India)
            }
        });
    }

    public void startAnnouncement(View view) {
        isAnnouncementEnabled = true;
        handler.postDelayed(announcementRunnable, 15 * 60 * 1000); // Announce every 15 minutes
        Toast.makeText(this, "Announcement started", Toast.LENGTH_SHORT).show();
    }

    public void stopAnnouncement(View view) {
        isAnnouncementEnabled = false;
        handler.removeCallbacks(announcementRunnable);
        Toast.makeText(this, "Announcement stopped", Toast.LENGTH_SHORT).show();
    }

    private Runnable announcementRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAnnouncementEnabled) {
                announceTime();
                handler.postDelayed(this, 15 * 60 * 1000); // Schedule next announcement
            }
        }
    };

    private void announceTime() {
        String time = getTimeInBengali();
        timeTextView.setText("Current time: " + time);
        textToSpeech.speak(time, TextToSpeech.QUEUE_FLUSH, null);
    }

    private String getTimeInBengali() {
        // Here you can implement logic to get current time in Bengali
        // For simplicity, we'll just use a placeholder
        return "এখন সময়";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
    }

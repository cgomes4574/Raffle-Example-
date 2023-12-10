package com.example.raffleexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Random number generator and raffle entry flags
    Random random = new Random();
    boolean raffle1entered = false;
    boolean raffle2entered = false;
    boolean raffle3entered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView countdownText = findViewById(R.id.countdown_text);
        Button shoe1Bttn = findViewById(R.id.shoe1Bttn);
        Button shoe2Bttn = findViewById(R.id.shoe2Bttn);
        Button shoe3Bttn = findViewById(R.id.shoe3Bttn);

        // Sets target date and time for timer
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String targetDateString = "2023-12-10 15:30:00";
        Date targetDate;

        try {
            targetDate = sdf.parse(targetDateString);

            // Calculates time difference between current time and target time
            long currentTime = System.currentTimeMillis();
            long targetTime = targetDate.getTime();
            long timeDifference = targetTime - currentTime;

            // Creates and starts countdown timer
            CountDownTimer timer = new CountDownTimer(timeDifference, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // Formats countdown text to 00:00:00
                    String formattedTime = formatTime(millisUntilFinished);
                    countdownText.setText("Time left: " + formattedTime);
                }

                @Override
                public void onFinish() {
                    // When timer finishes, raffle buttons are no longer clickable and raffle closes
                    shoe1Bttn.setEnabled(false);
                    shoe2Bttn.setEnabled(false);
                    shoe3Bttn.setEnabled(false);
                    countdownText.setText("Raffle Closed");

                    // User is notified of raffle results if they entered a raffle
                    if (raffle1entered) {
                        raffleResults();
                    }
                    if (raffle2entered) {
                        raffleResults();
                    }
                    if (raffle3entered) {
                        raffleResults();
                    }
                }
            };

            // Timer starts when user opens screen
            timer.start();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // When user clicks on shoe1, they enter the raffle unless they have already entered another raffle
        shoe1Bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (raffle2entered) {
                    Toast.makeText(MainActivity.this, "You've already entered in raffle 2.", Toast.LENGTH_SHORT).show();
                } else if (raffle3entered) {
                    Toast.makeText(MainActivity.this, "You've already entered in raffle 3.", Toast.LENGTH_SHORT).show();
                } else {
                    raffle1entered = true;
                    Toast.makeText(MainActivity.this, "Raffle 1 entered.", Toast.LENGTH_SHORT).show();
                    shoe1Bttn.setText("Entered");
                }
            }
        });
        // When user clicks on shoe2, they enter the raffle unless they have already entered another raffle
        shoe2Bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (raffle1entered) {
                    Toast.makeText(MainActivity.this, "You've already entered in raffle 1.", Toast.LENGTH_SHORT).show();
                } else if (raffle3entered) {
                    Toast.makeText(MainActivity.this, "You've already entered in raffle 3.", Toast.LENGTH_SHORT).show();
                } else {
                    raffle2entered = true;
                    Toast.makeText(MainActivity.this, "Raffle 2 entered.", Toast.LENGTH_SHORT).show();
                    shoe2Bttn.setText("Entered");
                }
            }
        });
        // When user clicks on shoe3, they enter the raffle unless they have already entered another raffle
        shoe3Bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (raffle1entered) {
                    Toast.makeText(MainActivity.this, "You've already entered in raffle 1.", Toast.LENGTH_SHORT).show();
                } else if (raffle2entered) {
                    Toast.makeText(MainActivity.this, "You've already entered in raffle 2.", Toast.LENGTH_SHORT).show();
                } else {
                    raffle3entered = true;
                    Toast.makeText(MainActivity.this, "Raffle 3 entered.", Toast.LENGTH_SHORT).show();
                    shoe3Bttn.setText("Entered");
                }
            }
        });
    }

    // Assigns the user a random number from 0 to 9 when raffle is entered
    public static int getUserEntry(Random random) {
        return random.nextInt(10);
    }
    // Generates a random winning number from 0 to 9
    public static int getWinningNum(Random random) {
        return random.nextInt(10);
    }
    // Checks and notifies if user won the raffle
    private void raffleResults() {
        int userEntry = getUserEntry(random);
        int winningNum = getWinningNum(random);

            if (winningNum == userEntry) {
                Toast.makeText(MainActivity.this, "Congratulations! You won!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Sorry, maybe next time!", Toast.LENGTH_SHORT).show();
            }
    }

    // Formats time in 00:00:00
    private String formatTime(long millis) {
        long seconds = millis / 1000 % 60;
        long minutes = millis / (60 * 1000) % 60;
        long hours = millis / (60 * 60 * 1000);

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);

    }
}

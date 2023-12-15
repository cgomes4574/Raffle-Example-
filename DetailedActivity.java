package org.meicode.rafflescreennew.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import org.meicode.rafflescreennew.R;
import org.meicode.rafflescreennew.utils.model.ShoeItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailedActivity extends AppCompatActivity {

    private ImageView shoeImageView;
    private TextView shoeNameTV, shoeBrandNameTV, shoePriceTV;
    private AppCompatButton addToCartBtn;
    private ShoeItem shoe;

    // SharedPreferences stores boolean values
    //private SharedPreferences sharedPreferences;

    // Retrieve SharedPreferences
    //private static final String prefName = "prefName";

    // Retrieve button state from SharedPreferences
    //private static final String bttnState = "buttonState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        initializeVariables();


        shoe = getIntent().getParcelableExtra("shoeItem");
        if (shoe != null) {
            setDataToWidgets();
        }
        /*
        moved in order to remove NullPointerException
        initializeVariables();
        */

        // Set up timer
        TextView countDownText = findViewById(R.id.countDownText);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US.getDefault());
        String targetDateString = "2023-12-17 24:00:00";
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
                    countDownText.setText("Time left: " + formattedTime);
                }

                @Override
                public void onFinish() {
                    // When timer finishes, raffle buttons are no longer clickable and raffle closes
                    countDownText.setText("Raffle Closed");

                }
            };

            // Timer starts when user opens screen
            timer.start();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        /*
        // Retrieve the SharedPreferences
        sharedPreferences = getSharedPreferences("prefName" + shoe, MODE_PRIVATE);
        // Retrieve button state from SharedPreferences
        boolean isEntered = sharedPreferences.getBoolean("buttonState", false);
        // Sets button text based on the retrieved state
        addToCartBtn.setText(isEntered ? "Entered" : "Enter Raffle");
        */

        // When user clicks on "Enter Raffle", text is changed to "Entered"
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartBtn.setText("Entered");
                /*
                // Saves the entered state in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(bttnState, true);
                editor.apply();
                */
            }
        });
    }
    private void setDataToWidgets() {
        shoeNameTV.setText(shoe.getShoeName());
        shoeBrandNameTV.setText(shoe.getShoeBrandName());
        shoePriceTV.setText(String.valueOf(shoe.getShoePrice()));
        shoeImageView.setImageResource(shoe.getShoeImage());

    }

    private void initializeVariables(){
        shoeImageView = findViewById(R.id.detailActivityShoeIV);
        shoeNameTV = findViewById(R.id.detailActivityShoeNameTv);
        shoeBrandNameTV = findViewById(R.id.detailActivityShoeBrandNameTv);
        shoePriceTV = findViewById(R.id.detailActivityShoePriceTv);
        addToCartBtn = findViewById(R.id.detailActivityAddToCartBtn);

    }

    // Formats time in 00:00:00
    private String formatTime(long millis) {
        long seconds = millis / 1000 % 60;
        long minutes = millis / (60 * 1000) % 60;
        long hours = millis / (60 * 60 * 1000);

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);

    }
}

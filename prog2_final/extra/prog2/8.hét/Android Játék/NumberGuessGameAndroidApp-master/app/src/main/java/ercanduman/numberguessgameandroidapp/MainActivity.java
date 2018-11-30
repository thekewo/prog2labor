package ercanduman.numberguessgameandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private TextView remainTrailsTextView, resultTextView;
    private EditText userGuessEditText;

    private static int TRIALS = 10;
    private static int GUESSED_NUMBER;

    private Button checkButton;

    boolean isFound = false;
    boolean isTrialFinished = false;

    static int usersTempNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
    }

    private void initializeViews() {
        //whenever app created, a new number will be picked randomly
        GUESSED_NUMBER = new Random().nextInt(100);

        //Buttons
        checkButton = (Button) findViewById(R.id.checkButton);
        checkButton.setOnClickListener(this);

        Button replayButton = (Button) findViewById(R.id.buttonReplay);
        replayButton.setOnClickListener(this);


        //TextViews
        resultTextView = (TextView) findViewById(R.id.tvResult);
        remainTrailsTextView = (TextView) findViewById(R.id.tvRemainTrials);


        //User input EditTexts
        userGuessEditText = (EditText) findViewById(R.id.userGuessText);
        //handle key listener for EditText (enter/done clicked)
        userGuessEditText.setOnKeyListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkButton:
                checkNumberAndStartGame();
                break;
            case R.id.buttonReplay:
                startNewGame();
                break;
        }

        //hide keyboard after button clicks
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void checkNumberAndStartGame() {
        if (isFound) {
            Toast.makeText(this, "Already found! (" + GUESSED_NUMBER + ")\nClick REPLAY button to start new game", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isTrialFinished) {
            Toast.makeText(this, "Trials are finished! \nClick REPLAY button to start new game", Toast.LENGTH_SHORT).show();
            return;
        }
        String userCurrentInput = userGuessEditText.getText().toString().trim();

        if (userCurrentInput.length() == 0 || userCurrentInput.length() > 2) {
            Toast.makeText(this, "Please enter a number between 0 and 100", Toast.LENGTH_SHORT).show();
            return;
        }

        int usersNumberGuess = Integer.parseInt(userCurrentInput);

        //check previous number to prevent losing trials number
        if (usersTempNumber == usersNumberGuess) {
            Toast.makeText(this, "Number already checked, please try another one!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usersNumberGuess > GUESSED_NUMBER) {
            updateViews("It is smaller than your guess");
        } else if (usersNumberGuess < GUESSED_NUMBER) {
            updateViews("It is bigger than your guess");
        } else {
            //(usersNumberGuess == GUESSED_NUMBER)
            resultTextView.setText("Found " + GUESSED_NUMBER + " in " + (10 - TRIALS) + " trials!");
            remainTrailsTextView.setText("Congratulations!");
            isFound = true;
            TRIALS = 10;

            //when found, disable EditText
            userGuessEditText.setKeyListener(null);
        }

        if (TRIALS == 0) {
            resultTextView.setText("Trials are finished! The picked number is " + GUESSED_NUMBER);
            remainTrailsTextView.setText("GAME OVER!");
            isTrialFinished = true;
            TRIALS = 10;
        }

        //assign input to a temp number
        usersTempNumber = usersNumberGuess;
    }

    private void updateViews(String text) {
        resultTextView.setText(text);
        TRIALS--;
        remainTrailsTextView.setText("You have " + TRIALS + " trials left");
    }

    private void startNewGame() {
        Toast.makeText(this, "New Game started!", Toast.LENGTH_SHORT).show();

        Intent startFromScratch = new Intent(MainActivity.this, MainActivity.class);
        startFromScratch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startFromScratch);

    }

    //handle key listener for edittext (enter/done clicked)
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
            checkNumberAndStartGame();
            return true;
        }
        return false;
    }
}

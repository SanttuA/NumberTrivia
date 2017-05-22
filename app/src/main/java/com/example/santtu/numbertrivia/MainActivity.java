package com.example.santtu.numbertrivia;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnTriviaChangeListener{

    private JsonHelper helper;
    private TextView randomTriviaText;
    private RadioGroup triviaTypeRadioGroup;
    private EditText numberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helper = new JsonHelper();
        helper.SetListener(this);
        helper.FetchTrivia();

        randomTriviaText = (TextView) findViewById(R.id.randomTriviaText);
        triviaTypeRadioGroup = (RadioGroup) findViewById(R.id.triviaTypeRadioGroup);

        numberEditText = (EditText) findViewById(R.id.numberEditText);
        numberEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    SearchTrivia();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(numberEditText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        final Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                SearchTrivia();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(numberEditText.getWindowToken(), 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void UpdateTrivia()
    {
        //set trivia text etc here
        Trivia trivia = helper.getTriviaObject();
        if(trivia.isFound())
            randomTriviaText.setText(helper.getTriviaObject().getText());
        else
            randomTriviaText.setText(R.string.NoTriviaFound);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_trivia:
                if (checked)
                {
                    numberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    numberEditText.setHint(R.string.number_random_if_empty);
                }
                    break;
            case R.id.radio_math:
                if (checked)
                {
                    numberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    numberEditText.setHint(R.string.number_random_if_empty);
                }
                    break;
            case R.id.radio_date:
                if (checked)
                {
                    numberEditText.setHint(R.string.date_random_if_empty);
                    numberEditText.setInputType(InputType.TYPE_CLASS_DATETIME);
                }
                    break;
            case R.id.radio_year:
                if (checked)
                {
                    numberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    numberEditText.setHint(R.string.year_random_if_empty);
                }
                    break;
        }
    }

    private void SearchTrivia()
    {
        if(triviaTypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_trivia)
        {
            String searchNumber = numberEditText.getText().toString();
            if(TextUtils.isEmpty(searchNumber))
                helper.FetchTrivia();
            else
                helper.FetchTrivia(Integer.parseInt(searchNumber));
        }
        else if(triviaTypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_math)
        {
            String searchNumber = numberEditText.getText().toString();
            if(TextUtils.isEmpty(searchNumber))
                helper.FetchMathTrivia();
            else
                helper.FetchMathTrivia(Integer.parseInt(searchNumber));
        }
        else if(triviaTypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_date)
        {
            String searchNumber = numberEditText.getText().toString();
            if(TextUtils.isEmpty(searchNumber))
                helper.FetchDateTrivia();
            else
            {
                searchNumber = searchNumber.replaceAll("[<>\\[\\],-]", "");
                String[] tokens = searchNumber.split("[/]");
                if(tokens.length == 2)
                    helper.FetchDateTrivia(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Incorrect date input format!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        }
        else if(triviaTypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_year)
        {
            String searchNumber = numberEditText.getText().toString();
            if(TextUtils.isEmpty(searchNumber))
                helper.FetchYearTrivia();
            else
                helper.FetchYearTrivia(Integer.parseInt(searchNumber));
        }
    }


}

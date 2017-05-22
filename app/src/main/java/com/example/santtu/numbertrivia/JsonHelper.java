package com.example.santtu.numbertrivia;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

interface OnTriviaChangeListener
{
    void UpdateTrivia();
}

/**
 *  Handles getting new number trivia
 */

public class JsonHelper {

    private OnTriviaChangeListener triviaChangeListener;

    private Trivia triviaObject;
    private String baseAddressUrl = "http://numbersapi.com/";

    public JsonHelper()
    {
        triviaObject = null;
    }

    public void SetListener(OnTriviaChangeListener lis) {triviaChangeListener = lis;}
    
    public void setTriviaObject(Trivia trivia)
    {
        triviaObject = trivia;

        if(triviaChangeListener != null)
        {
            triviaChangeListener.UpdateTrivia();
        }
    }

    public Trivia getTriviaObject()
    {
        return triviaObject;
    }

    /**
     * Starts http fetch process for a random number trivia.
     */
    public void FetchTrivia()
    {
        new GetTriviaJsonTask().execute(baseAddressUrl+"random/trivia?json");
    }

    /**
     * Starts http fetch process for a specific number trivia.
     */
    public void FetchTrivia(int number)
    {
        new GetTriviaJsonTask().execute(baseAddressUrl+number+"/trivia?json");
    }

    /**
     * Starts http fetch process for a random math trivia.
     */
    public void FetchMathTrivia()
    {
        new GetTriviaJsonTask().execute(baseAddressUrl+"random/math?json");
    }

    /**
     * Starts http fetch process for a specific math trivia.
     */
    public void FetchMathTrivia(int number)
    {
        new GetTriviaJsonTask().execute(baseAddressUrl+number+"/math?json");
    }

    /**
     * Starts http fetch process for a random date trivia.
     */
    public void FetchDateTrivia()
    {
        new GetTriviaJsonTask().execute(baseAddressUrl+"random/date?json");
    }

    /**
     * Starts http fetch process for a specific date trivia.
     * @param month int number
     * @param day int number
     */
    public void FetchDateTrivia(int month, int day)
    {
        new GetTriviaJsonTask().execute(baseAddressUrl+month+"/"+day+"/date?json");
    }

    /**
     * Starts http fetch process for a random year trivia.
     */
    public void FetchYearTrivia()
    {
        new GetTriviaJsonTask().execute(baseAddressUrl+"random/year?json");
    }

    /**
     * Starts http fetch process for a specific year trivia.
     */
    public void FetchYearTrivia(int number)
    {
        new GetTriviaJsonTask().execute(baseAddressUrl+number+"/year?json");
    }

    private static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        return new JSONObject(jsonString);
    }


    private class GetTriviaJsonTask extends AsyncTask<String, Void, Trivia> {
        protected Trivia doInBackground(String... params) {
            String urlString = params[0];
            Trivia triviaObject = new Trivia();
            try{
                JSONObject jsonObject = getJSONObjectFromURL(urlString);

                // Parse json here
                boolean found = jsonObject.getBoolean("found");
                triviaObject.setFound(found);
                if(found)
                {
                    //the trivia was found
                    triviaObject.setText(jsonObject.getString("text"));
                    triviaObject.setNumber((float) jsonObject.getDouble("number"));
                    triviaObject.setType(jsonObject.getString("type"));
                    triviaObject.setDate(jsonObject.getString("date"));
                    triviaObject.setYear(jsonObject.getString("year"));
                }
                else
                {
                    //the trivia was not found
                    triviaObject.setText(null);
                    triviaObject.setNumber(-1);
                    triviaObject.setType(null);
                    triviaObject.setDate(null);
                    triviaObject.setYear(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return triviaObject;
        }

        protected void onPostExecute(Trivia result) {
            setTriviaObject(result);
        }
    }
}

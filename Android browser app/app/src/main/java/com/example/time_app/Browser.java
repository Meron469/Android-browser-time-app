package com.example.time_app;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


import static com.example.time_app.File_menu.i;
import static com.example.time_app.File_menu.names;


public class Browser extends AppCompatActivity {
    //object of DatabaseHandler class
    public static  int I=0;
    public static String ID;
    public static String end_t;
    public static String start_t;
    public static String numberAsString;
    public static ArrayList<String> ID_1=new ArrayList<String>();
    public static ArrayList<String> ID_2=new ArrayList<String>();
    public static ArrayList<String> ID_3=new ArrayList<String>();
    public static ArrayList<String> ID_4=new ArrayList<String>();
    public static ArrayList<String> ID_5=new ArrayList<String>();
    public static ArrayList<String> names_2=new ArrayList<String>();
    private static class SomeComplicatedObject {

        public final String mPath;
        public final Integer mDuration;




        public SomeComplicatedObject(final String path, final int duration) {
            mPath = path;
            mDuration = duration;
        }

        String getPath() {
            return mPath;
        }

        Integer getDureation() {
            return mDuration;
        }
    }

    public static final List<SomeComplicatedObject> complicatedList = new ArrayList<>();

    static {
        for (int i=0;i<names.size();i++)
        {
            complicatedList.add(new SomeComplicatedObject(names.get(i),8000));
            names_2.add(names.get(i));



        }


    }



    public static final String TAG = "Browser";
    public final ArrayList<Pair<String, Integer>> mSitesToDisplay = new ArrayList<>();
    public final Timer mTimer = new Timer("LoadNext");
    public final Handler mHandler = new Handler();
    public WebView mWebView = null;
    public int mIndex = -1;
    public final Context context = this;


    public void fillSitesToDisplay(List<SomeComplicatedObject> list) {
        mIndex = -1;
        mSitesToDisplay.clear();
        for (SomeComplicatedObject item : list) {
            mSitesToDisplay.add(new Pair<>(item.getPath(), item.getDureation()));

        }



    }

    public Pair<String, Integer> getNext() {
        mIndex = (mIndex + 1) % mSitesToDisplay.size();
        return mSitesToDisplay.get(mIndex);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        fillSitesToDisplay(complicatedList);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        mWebView = (WebView) findViewById(R.id.webview);



        //initial start delayed by 1 sec
        mTimer.schedule(createTimerTask(), TimeUnit.SECONDS.toMillis(8));
       // load_javascript();






    }

    public Runnable mDisplayNextPage = new Runnable() {
        @Override
        public void run() {
            final Pair<String, Integer> pathTimePair = getNext();
            Log.e(TAG, "DisplayNext::run :  Start loading next page : " + pathTimePair.first);
            mWebView.setWebViewClient(new WaitPageLoadedClient(mTimer, createTimerTask(), pathTimePair.second));
            mWebView.loadUrl(pathTimePair.first);



        }
    };

    public TimerTask createTimerTask() {
        return new TimerTriggered(mHandler, mDisplayNextPage);
    }

    //simple utility class for notifying timer triggered event
    //on UI thread.
    public  static class TimerTriggered extends TimerTask {

        Runnable mOnTriggered;
        Handler mHandler;

        protected TimerTriggered(Handler handler, Runnable onTriggered) {
            super();

            mHandler = handler;
            mOnTriggered = onTriggered;

        }

        @Override
        public void run() {
            //Timer runs int it's own thread so we need to
            //pass execution to MainThread for accessing WebView.
            I++;
            mHandler.post(mOnTriggered);


        }
    }

    public class WaitPageLoadedClient extends WebViewClient {

        public final TimerTask mRunOnFinished;
        public final Timer mTimer;
        public final Integer mDelay;
        //creating the webview variable
        WebView wb;
        //the end variable takes in the finshed load time(it is a long function because of the number length
        long end;
        //this is just a varibale to be used for the alert code

        //this is the total number converted as a string because the alert function does not take in float

        //the start variable takes in the start load time(it is a long function because of the number length
        long start;
        //The total variable takes in the substraction of the end and start and is a float variale do to its shorter length
        float total;

        Date end_Time;
        Date start_Time;



        WebView mWebView=(WebView) findViewById(R.id.webview);



        public WaitPageLoadedClient(Timer timer, TimerTask toRunOnFinished, Integer delay) {


            mRunOnFinished = toRunOnFinished;
            mTimer = timer;
            mDelay = delay;


        }

        @Override
        //this function records the load time
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            start = System.currentTimeMillis();
            start_Time = Calendar.getInstance().getTime();
        }


        @Override
        public void onPageFinished(WebView view, String url) {


             ID=Integer.toString(I);


              Log.e(TAG, "onPageFinished: Schedule timer for " + mDelay);
            mTimer.schedule(mRunOnFinished, mDelay);


            end = System.currentTimeMillis();
            total = end - start;
            end_Time = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
              end_t = sdf.format(end_Time);
              start_t=sdf.format(start_Time);
            // Total is the difference in milliseconds.
            // Dividing by 1000, you convert it to seconds
               numberAsString = String.valueOf(total / 1000);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // set title
            alertDialogBuilder.setTitle("Your Load Time");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Elapsed Time:" + numberAsString + "\n" + "Start Time:" +  start_t + "\n" + "EndTime:" + end_t + "\n" + "Number id:" +ID)
                    .setCancelable(false)
                    .setNegativeButton("Stop", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();

                            Go_Data();

                        }
                    });


            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

            ID_1.add(ID);
            for(int i=0;i<names_2.size();i++)
            {
                ID_2.add(getHostName(names_2.get(i)));
            }

            ID_3.add(numberAsString);
            ID_4.add(start_t);
            ID_5.add(end_t);











        }











    }

    public void Go_Data()
    {



        Intent intent=new Intent(this, GridViewActivity.class);
        startActivity(intent);



    }


  /*  public void load_javascript()
    {
        //declaring and setting the web setting variable
        WebSettings webSettings = mWebView.getSettings();
        //setting up the javascript to allow thw browser to use javascript
        webSettings.setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.clearCache(true);
    }*/
    public String getHostName(String url)  {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String hostname = uri.getHost();
        // to provide faultproof result, check if not null then return only hostname, without www.
        if (hostname != null) {
            return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
        }
        return hostname;
    }



}

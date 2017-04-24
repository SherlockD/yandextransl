package com.sherlock.translator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static android.R.layout.simple_expandable_list_item_1;


public class MainActivity extends AppCompatActivity {

    public  Button button1;
    public Button button2;
    public EditText editText;
    public EditText editText2;
    public EditText editText3;
    public Button button3;
    public static String a,m,j,z;
    public static String b,langop,lan;
    ScrollView myScroll;

    public  int c,d,e=0,t=0,r,y=0,f=0,am=0,gs=0,slov=0,tm,tms;
    public String[] language = new String[160];
    public  String[] languages = {"af","am","ar","az","ba","be","bg","bn","bs","ca","ceb","cs","cy","da","de","el","en","eo","es","et","eu","fa","fi","fr","ga","gd","gl","gu","he","hi","hr","ht","hu","hy","id","is","it","ja","jv","ka","kk","km","kn","ko","ky","la","lb","lo","lt","lv","mg","mhr","mi","mk","ml","mn","mr","mrj","ms","mt","my","ne","nl","no","pa","pap","pl","pt","ro","ru","si","sk","sl","sq","sr","su","sv","sw","ta","te","tg","th","tl","tr","tt","udm","uk","ur","uz","vi","xh","yi","zh"};
    public String[] languagename = new String[93];
    public String[] words = new String[100];
    public String[] pos = new String[100];
    public String[] ts = new String[100];
    public String[] Slovv = new String[100];
    public String[] wordss = new String[100];
    private int[] chislos = new int[8];
    private static final String TAG = "My logs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //определение компонентов
        Log.d(TAG, "ищем компоненты");
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        button3 = (Button) findViewById(R.id.button3);
        myScroll = (ScrollView) findViewById(R.id.myscroll);
        final AutoCompleteTextView mAutoCompleteTextView;
        final AutoCompleteTextView mAutoCompleteTextView1;

        editText.setRawInputType(0x00000000);
        editText3.setRawInputType(0x00000000);

        final ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        final ArrayList<String> children1 = new ArrayList<String>();
        ArrayList<String> children2 = new ArrayList<String>();


        Log.d(TAG, "объявляем компоненты");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(null);
                editText2.setText(null);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                editText3.setText(null);
                if (editText2.getText().length() > 0) {
                    a = "";
                    Thread ne = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "поток перевода запущен");
                            Log.d(TAG, "первый язык" + languages[c]);
                            Log.d(TAG, "первый язык" + languages[d]);
                            String url = ("https://translate.yandex.net//api/v1.5/tr.json/translate?lang=" + languages[c] + "-" + languages[d] + "&key=trnsl.1.1.20170401T073931Z.d433cfb9c2b37db3.a92fff0df645ad214d4f259c55d587ab909f3725&text=" + editText2.getText());
                            HttpURLConnection connection = null;
                            try {
                                connection = (HttpURLConnection) new URL(url).openConnection();
                                connection.setRequestMethod("GET");
                                connection.setUseCaches(false);
                                connection.connect();
                                StringBuilder sb = new StringBuilder();

                                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                                    Log.d(TAG, "коннект к переводу удался");
                                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                    String line;
                                    while ((line = in.readLine()) != null) {
                                        sb.append(line);
                                        sb.append("\n");
                                    }
                                    JSONObject json = new JSONObject(sb.toString());
                                    JSONArray urls = json.getJSONArray("text");
                                    String name = urls.getString(0);
                                    Log.d(TAG, "перевод = " + name);
                                    a = name.toString();
                                }
                            } catch (Throwable cause) {
                                cause.printStackTrace();
                            } finally {
                                connection.disconnect();
                            }
                        }
                    });
                    ne.start();
                    try {
                        ne.join();
                        System.out.println(a);

                        editText.setText(a.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    editText.setText(null);
                }
                //////////////////////////////////////////////////////
                Thread dn = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "поток словаря запущен");
                        Log.d(TAG, "первый язык" + languages[c]);
                        Log.d(TAG, "первый язык" + languages[d]);
                        String url = ("https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20170412T121616Z.352664dc5ea62567.9d3594e5dd1544f774efb2d632e0847bd68b9351&lang=en-ru&text=" + editText.getText());
                        HttpURLConnection connection = null;
                        try {
                            connection = (HttpURLConnection) new URL(url).openConnection();
                            connection.setRequestMethod("GET");
                            connection.setUseCaches(false);
                            connection.connect();
                            StringBuilder sb = new StringBuilder();

                            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                                Log.d(TAG, "коннект к словарю удался");
                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String line;
                                while ((line = in.readLine()) != null) {
                                    sb.append(line);
                                    sb.append("\n");
                                }
                                JSONObject json = new JSONObject(sb.toString());
                                JSONArray let = json.getJSONArray("def");
                                String let1 = let.getString(0); //
                                am=0;
                                for (int p = 0; p < let.length(); p++) {
                                    JSONArray tr = json.getJSONArray("def").getJSONObject(p).getJSONArray("tr");
                                    JSONObject letall = let.getJSONObject(p);
                                    String let2 = letall.getString("text");
                                    words[p] = let2;
                                    String let6 = letall.getString("pos");
                                    pos[p] = let6;
                                   // String let7 = letall.getString("ts");
                                    //ts[p] = let7;
                                    Log.d(TAG, let1);
                                    Log.d(TAG, words[p]);
                                    Log.d(TAG, pos[p]);
                                    f=0;
                                    for (int s = 0; s < tr.length(); s++) {
                                        JSONObject sl = tr.getJSONObject(s);
                                        String let5 = sl.getString("text");
                                        wordss[am] = let5;
                                        am=am+1;
                                        Log.d(TAG, "слово " + wordss[s]);
                                        f = f+1;
                                    }
                                    chislos[p] = f;
                                    Log.d(TAG, String.valueOf(f));
                                    Log.d(TAG, ts[p]);
                                    m = (words[p]+" "+pos[p]+ " " +ts[p]).toString();
                                    Slovv[p] = m.toString();
                                    Log.d(TAG, "словвыв " + Slovv[p]);

                                }
                            }
                            } catch(Throwable cause){
                                cause.printStackTrace();
                            } finally{
                                connection.disconnect();
                            }
                    }
                });
                dn.start();
                try {
                    dn.join();
                   for(int gh=0;gh<chislos.length;gh++){
                       if(chislos[gh]!= 0) {
                           Log.d(TAG, "массив" + chislos[gh]);
                       }
                   }
                    for(int l=0;l<wordss.length;l++){
                        if(wordss[l]!=null) {
                            Log.d(TAG, "массив" + wordss[l]);
                        }
                    }
                    gs=0;
                   for(int g = 0;g<Slovv.length;g++) {
                       if (Slovv[g] != null) {
                               editText3.append(Slovv[g] + "\n");
                           for (tm=0; tm < chislos[gs]; tm++) {
                                   editText3.append(wordss[slov] + "\n");
                               slov = slov + 1;
                               }
                               Log.d(TAG, String.valueOf(slov));
                           gs=gs+1;
                       }
                   }
                    for (int u = 0; u < wordss.length; u++) {
                        wordss[u] = null;
                    }
                    slov = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            });

        Thread lg = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "поток языков запущен");
                String urll = ("https://translate.yandex.net/api/v1.5/tr.json/getLangs?&key=trnsl.1.1.20170401T073931Z.d433cfb9c2b37db3.a92fff0df645ad214d4f259c55d587ab909f3725&ui=ru");
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL(urll).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setUseCaches(false);
                    connection.connect();
                    StringBuilder bs = new StringBuilder();

                    if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                        Log.d(TAG, "коннект к языкам удался");
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String langs1;
                        while ((langs1 = in.readLine()) != null) {
                            bs.append(langs1);
                        }
                        JSONObject json1 = new JSONObject(bs.toString());
                        JSONObject urls1 = json1.getJSONObject("langs");
                        for (int n = 0; n < urls1.length(); n++) {
                            String lang1 = urls1.getString(languages[n]);
                            languagename[n] = lang1.toString();
                        }
                    }
                } catch (Throwable cause) {
                    cause.printStackTrace();
                } finally {
                    connection.disconnect();
                }
            }
        });
        lg.start();
        try {
            lg.join();
            System.out.println(b);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.mact);
        mAutoCompleteTextView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, languagename));
        mAutoCompleteTextView1 = (AutoCompleteTextView) findViewById(R.id.mact1);
        mAutoCompleteTextView1.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, languagename));
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = 0;
                c = 0;
                e = 0;
                d = 0;
                y = 0;
                 lan = mAutoCompleteTextView.getText().toString();
                if(lan.length() == 0){
                    mAutoCompleteTextView.setText("определить");
                    lan = mAutoCompleteTextView.getText().toString();
                }
                    if (lan.equals("определить")) {
                        if(editText2.getText().length()==0){
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Для определения необходимо ввести текст", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            Log.d(TAG, "определяем");
                            Thread lg = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "поток определения языка запущен");
                                    String urlop = ("https://translate.yandex.net/api/v1.5/tr.json/detect?&key=trnsl.1.1.20170401T073931Z.d433cfb9c2b37db3.a92fff0df645ad214d4f259c55d587ab909f3725&text=" + editText2.getText());
                                    HttpURLConnection connection = null;
                                    try {
                                        connection = (HttpURLConnection) new URL(urlop).openConnection();
                                        connection.setRequestMethod("GET");
                                        connection.setUseCaches(false);
                                        connection.connect();
                                        StringBuilder bs = new StringBuilder();

                                        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                                            Log.d(TAG, "коннект к опеделению языка удался");
                                            BufferedReader op = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                            String langsop;
                                            while ((langsop = op.readLine()) != null) {
                                                bs.append(langsop);
                                            }
                                            JSONObject json1 = new JSONObject(bs.toString());
                                            langop = json1.getString("lang");
                                            Log.d(TAG, "определенный язык = " + langop);
                                        }
                                    } catch (Throwable cause) {
                                        cause.printStackTrace();
                                    } finally {
                                        connection.disconnect();
                                    }
                                }
                            });
                            lg.start();
                            try {
                                lg.join();
                                System.out.println(b);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for (String s : languages) {
                                if (s.startsWith(langop)) {
                                    Log.d(TAG, "Найден язык = " + langop);
                                    y = 1;
                                } else {
                                    if (y == 0) {
                                        c = c + 1;
                                    }
                                }
                            }

                            Log.d(TAG, "номер определенного языка " + c);
                        }
                    } else {
                        for (String s : languagename) {
                            if (s.startsWith(lan)) {
                                Log.d(TAG, "Найден язык = " + lan);
                                t = 1;
                            } else {
                                if (t == 0) {
                                    c = c + 1;
                                }
                            }
                        }
                        Log.d(TAG, "номер языка 1 " + c);
                    }
                    String lan2 = mAutoCompleteTextView1.getText().toString();
                    if (lan2.length() == 0) {
                        d = c;
                    } else {
                        for (String s : languagename) {
                            if (s.startsWith(lan2)) {
                                Log.d(TAG, "Найден язык = " + lan2);
                                e = 1;
                            } else {
                                if (e == 0) {
                                    d = d + 1;
                                }
                            }
                        }
                    }
                    Log.d(TAG, "номер языка 2 " + d);
            }
        });
    }
}


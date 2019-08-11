package com.example.lalam_000.introsliderexemple;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Inicial2Activity extends Activity {

    //Variáveis
    TabLayout tabLayout2;
    ListView listView;
    ListView listView2;
    ListView listView3;
    ImageButton imageButton;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pag_inicial2);

        //Tab2
        tabLayout2 = (TabLayout) findViewById(R.id.tabs2);
        tabLayout2.addTab(tabLayout2.newTab().setText("Histórico"));

        //Json
        listView = (ListView) findViewById(R.id.list_agua);
        listView2 = (ListView) findViewById(R.id.list_energia);
        listView3 = (ListView) findViewById(R.id.list_horario);
        getJSON("http://192.168.0.100/measurer/historico.php");

        //Botão de atualizar
        mActivity = Inicial2Activity.this;
        ImageButton imageButton = (ImageButton) findViewById(R.id.atualizar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity(mActivity);
            }
        });
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] medidas = new String[jsonArray.length()];
        String[] medidas2 = new String[jsonArray.length()];
        String[] medidas3 = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            medidas[i] = obj.getString("vazao");
            medidas2[i] = obj.getString("energia");
            medidas3[i] = obj.getString("horario");

        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medidas);
        listView.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medidas2);
        listView2.setAdapter(arrayAdapter2);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medidas3);
        listView3.setAdapter(arrayAdapter3);
    }

    public void telaAnterior (View view){

        Intent intent = new Intent(this, InicialActivity.class);
        startActivity(intent);
    }

    public  void restartActivity(Activity activity) {
        activity.recreate();
    }
}
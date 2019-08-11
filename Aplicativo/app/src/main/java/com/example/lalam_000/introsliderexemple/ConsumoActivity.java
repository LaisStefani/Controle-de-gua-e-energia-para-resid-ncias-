package com.example.lalam_000.introsliderexemple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ConsumoActivity extends Activity {

    //Variáveis
    TabLayout tabLayout;
    TextView textView;
    TextView textView2;
    TextView textView4;
    TextView textView5;
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;
    ImageButton imageButton;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_consumo);

        //Tab
        tabLayout = (TabLayout) findViewById(R.id.tabs3);
        tabLayout.addTab(tabLayout.newTab().setText("Gastos"));

        //JSON
        new GetTextViewData(context).execute();
        textView = (TextView) findViewById(R.id.valor_vazao);
        textView2 = (TextView) findViewById(R.id.valor_energia);

        //Fontes
        textView4 = (TextView) findViewById(R.id.textTab1);
        textView5 = (TextView) findViewById(R.id.textTab2);

        Typeface font = Typeface.createFromAsset(getAssets(), "Fontes/Roboto-Thin.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(),"Fontes/Roboto-Medium.ttf");
        textView.setTypeface(font);
        textView2.setTypeface(font);
        textView4.setTypeface(font2);
        textView5.setTypeface(font2);

        //Botão de atualizar
        mActivity = ConsumoActivity.this;
        ImageButton imageButton = (ImageButton) findViewById(R.id.atualizar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity(mActivity);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private class GetTextViewData extends AsyncTask<Void, Void, Void> {
        public Context context;

        public GetTextViewData(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost("http://192.168.0.100/measurer/money.php");

            try {
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONArray jArray = new JSONArray(str);
                json = jArray.getJSONObject(0);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {

            try {
                textView.setText("R$ " + json.getString("agua"));
                textView2.setText("R$ " + json.getString("energia"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public  void restartActivity(Activity activity) {
        activity.recreate();
    }

    public void telaAnterior (View view){

        Intent intent = new Intent(this, InicialActivity.class);
        startActivity(intent);
    }
}

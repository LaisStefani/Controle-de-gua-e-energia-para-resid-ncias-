package com.example.lalam_000.introsliderexemple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class InicialActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    //Variáveis
    Button button;
    Button nova_tela;
    ImageButton imageButton;
    TabLayout tabLayout;
    Activity mActivity;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pag_inicial);

        //Tab
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Medições"));

        //Botão de atualizar
        mActivity = InicialActivity.this;
        ImageButton imageButton = (ImageButton) findViewById(R.id.atualizar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity(mActivity);
            }
        });

        //JSON
        new GetTextViewData(context).execute();
        textView = (TextView) findViewById(R.id.valor_vazao);
        textView2 = (TextView) findViewById(R.id.valor_energia);
        textView3 = (TextView) findViewById(R.id.horario);

        //Fontes
        textView4 = (TextView) findViewById(R.id.textTab1);
        textView5 = (TextView) findViewById(R.id.textTab2);

        Typeface font = Typeface.createFromAsset(getAssets(), "Fontes/Roboto-Thin.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(),"Fontes/Roboto-Medium.ttf");
        textView.setTypeface(font);
        textView2.setTypeface(font);
        textView3.setTypeface(font);
        textView4.setTypeface(font2);
        textView5.setTypeface(font2);
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
            HttpPost myConnection = new HttpPost("http://192.168.0.100/measurer/send.php");

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
                textView.setText(json.getString("vazao") + " " + "L/min");
                textView2.setText(json.getString("energia") + " " + "Kw/h");
                textView3.setText("Atualizado em: " + json.getString("horario"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public  void restartActivity(Activity activity) {
        activity.recreate();
    }

    public void proximaTela(View view){
        Intent intent = new Intent(this, Inicial2Activity.class);
        startActivity(intent);
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main3);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.new_game:
                Intent intent = new Intent(this, ConsumoActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}

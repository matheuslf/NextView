package desenvolve.nextcode.next;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import desenvolve.nextcode.next.api.API;
import desenvolve.nextcode.next.structure.NextViewCampanha;
import desenvolve.nextcode.next.structure.NextViewUsuarioCoins;
import desenvolve.nextcode.next.structure.RespostaNextView;
import desenvolve.nextcode.next.util.Shared;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 97:E9:74:1A:0C:CD:CC:27:06:42:BB:9D:BC:3E:B8:8C:31:3F:64:D7
public class MainActivity extends YouTubeBaseActivity {

    private Button btnPlay;
    private YouTubePlayerView youtube;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private Toolbar toolbar;
    private TextView textTotalMoedas, textMoedas, textSegundos;
    private ArrayList<NextViewCampanha> arlCampanhas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("NextView");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //
                // Apresenta a mensagem de erro.
                //
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Campaigns")
                        .setContentText("What do you do?")
                        .setCancelText("List")
                        .setConfirmText("Create")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                startActivity(new Intent(MainActivity.this, CampanhaActivity.class));
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                startActivity(new Intent(MainActivity.this, MinhaCampanhasActivity.class));
                            }
                        })
                        .show();
            }
        });

        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setText("RUNNING");
                btnPlay.setEnabled(false);
                youtube.initialize("AIzaSyCmrBjyD21uAZYj0Ekc2bkowCwPpjRE0NU", onInitializedListener);
                youtube.setClickable(false);
            }
        });

        youtube = findViewById(R.id.youtube);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                contaSegundos(youTubePlayer);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        textTotalMoedas = findViewById(R.id.textTotalMoedas);
        textTotalMoedas.setText("Total Coins: "+ Shared.getLong(MainActivity.this, Shared.KEY_COINS));
        textMoedas = findViewById(R.id.textMoedas);
        textSegundos = findViewById(R.id.textSegundos);

        //
        // Apresenta o alert.
        //
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Info");
        pDialog.setContentText("Loading campaigns ...");
        pDialog.setCancelable(false);
        pDialog.show();

        API.getTodasCampanhas(new Callback<ArrayList<NextViewCampanha>>() {
            @Override
            public void onResponse(Call<ArrayList<NextViewCampanha>> call, Response<ArrayList<NextViewCampanha>> response) {
                pDialog.dismissWithAnimation();

                if (response != null && response.code() == 200) {
                    arlCampanhas = response.body();
                    NextViewCampanha c = arlCampanhas.get(0);
                    textMoedas.setText(""+c.getWatch_seconds()+" Coins");
                    textSegundos.setText(""+c.getWatch_seconds()+" Sec");
                }
                else {
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Campaigns failed [001]")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NextViewCampanha>> call, Throwable t) {
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("Campaigns failed [001]")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //
        // Apresenta o alert.
        //
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Info");
        pDialog.setContentText("Loading campaigns ...");
        pDialog.setCancelable(false);
        pDialog.show();

        API.getTodasCampanhas(new Callback<ArrayList<NextViewCampanha>>() {
            @Override
            public void onResponse(Call<ArrayList<NextViewCampanha>> call, Response<ArrayList<NextViewCampanha>> response) {
                pDialog.dismissWithAnimation();

                if (response != null && response.code() == 200) {
                    arlCampanhas = response.body();
                    NextViewCampanha c = arlCampanhas.get(0);
                    textMoedas.setText(""+c.getWatch_seconds()+" Coins");
                    textSegundos.setText(""+c.getWatch_seconds()+" Sec");
                }
                else {
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Campaigns failed [001]")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NextViewCampanha>> call, Throwable t) {
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("Campaigns failed [001]")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onUserLeaveHint() {
        System.exit(0);
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

    public int contaVideo = 0;
    public NextViewCampanha campanha;

    private void contaSegundos(final YouTubePlayer youTubePlayer) {

        try {
            campanha = arlCampanhas.get(contaVideo);
        }
        catch (Exception ex) {
            contaVideo = 0;
            campanha = arlCampanhas.get(contaVideo);
        }

        String link;
        if (campanha.getUrl().contains("=")) {
            link = campanha.getUrl().split("=")[1];
        } else {
            link = campanha.getUrl().substring(campanha.getUrl().length() - 11);
        }

        textMoedas.setText(""+campanha.getWatch_seconds()+" Coins");
        textSegundos.setText(""+campanha.getWatch_seconds()+" Sec");

        youTubePlayer.cueVideo(link);
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                youTubePlayer.play();

                new CountDownTimer(campanha.getWatch_seconds() * 1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        textSegundos.setText("" + (millisUntilFinished / 1000)+" Sec");
                    }

                    public void onFinish() {
                        textSegundos.setText("0 Sec");
                        contaVideo++;
                        NextViewUsuarioCoins updateCoins = new NextViewUsuarioCoins();
                        updateCoins.setId(Shared.getLong(MainActivity.this, Shared.KEY_ID_USUARIO));
                        updateCoins.setId_usuario(Shared.getLong(MainActivity.this, Shared.KEY_ID_USUARIO));

                        final long myCoins = Shared.getLong(MainActivity.this, Shared.KEY_COINS) + campanha.getWatch_seconds();
                        updateCoins.setCoins(myCoins);

                        Shared.put(MainActivity.this, Shared.KEY_COINS, myCoins);

                        API.postUsuarioCoins(updateCoins, new Callback<RespostaNextView>() {
                            @Override
                            public void onResponse(Call<RespostaNextView> call, Response<RespostaNextView> response) {
                                Toast.makeText(MainActivity.this, "Refreshing Total Coins. Wait...", Toast.LENGTH_LONG).show();
                                textTotalMoedas.setText("Total Coins: "+ myCoins);
                            }

                            @Override
                            public void onFailure(Call<RespostaNextView> call, Throwable t) {

                            }
                        });

                        campanha.setCount_quantity(campanha.getCount_quantity() + 1);
                        //campanha.setId_usuario(Shared.getLong(MainActivity.this, Shared.KEY_ID_USUARIO));
                        API.postCampanha(campanha, new Callback<RespostaNextView>() {
                            @Override
                            public void onResponse(Call<RespostaNextView> call, Response<RespostaNextView> response) {
                                contaSegundos(youTubePlayer);
                            }

                            @Override
                            public void onFailure(Call<RespostaNextView> call, Throwable t) {

                            }
                        });

                    }
                }.start();
            }
        }, 5000);
    }
}

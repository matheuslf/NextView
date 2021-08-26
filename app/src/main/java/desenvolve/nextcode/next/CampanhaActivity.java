package desenvolve.nextcode.next;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import desenvolve.nextcode.next.api.API;
import desenvolve.nextcode.next.structure.NextViewCampanha;
import desenvolve.nextcode.next.structure.NextViewUsuarioCoins;
import desenvolve.nextcode.next.structure.RespostaNextView;
import desenvolve.nextcode.next.util.Shared;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampanhaActivity extends YouTubeBaseActivity {

    private EditText edtURL;
    private Button btnAddURL;
    private YouTubePlayerView youtube;
    private Spinner spinnerViewQuantity;
    private Spinner spinnerWatchSeconds;
    private Button btnTotal;
    private Button btnDONE;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private String linkVideo = "";
    private TextView textTotalMoedas;
    private int countClick = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha);

        textTotalMoedas = findViewById(R.id.textTotalMoedas);
        textTotalMoedas.setText("Total Coins: "+ Shared.getLong(CampanhaActivity.this, Shared.KEY_COINS));

        edtURL = findViewById(R.id.edtURL);
        btnAddURL = findViewById(R.id.btnAddURL);
        btnAddURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtURL.getText().toString().contains("=")) {
                    linkVideo = edtURL.getText().toString().split("=")[1];
                }
                else {
                    linkVideo = edtURL.getText().toString().substring(edtURL.getText().toString().length() - 11);
                }
                youtube.initialize("AIzaSyBtG_DyXGTtTLkKVXjVTaP5fUGpYIXbfME", onInitializedListener);
            }
        });
        youtube = findViewById(R.id.youtube);
        spinnerViewQuantity = findViewById(R.id.spinnerViewQuantity);
        spinnerViewQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnTotal.setText(""+(Integer.parseInt(spinnerViewQuantity.getSelectedItem().toString()) * Integer.parseInt(spinnerWatchSeconds.getSelectedItem().toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] lsviewQuantity = getResources().getStringArray(R.array.view_quantity);
        ArrayAdapter<String> a = new ArrayAdapter<String>(CampanhaActivity.this, R.layout.spinner_item, lsviewQuantity);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerViewQuantity.setAdapter(a);
        spinnerViewQuantity.setSelection(0);

        spinnerWatchSeconds = findViewById(R.id.spinnerWatchSeconds);
        spinnerWatchSeconds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnTotal.setText(""+(Integer.parseInt(spinnerViewQuantity.getSelectedItem().toString()) * Integer.parseInt(spinnerWatchSeconds.getSelectedItem().toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] lswatch = getResources().getStringArray(R.array.watch);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(CampanhaActivity.this, R.layout.spinner_item, lswatch);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWatchSeconds.setAdapter(aa);
        spinnerWatchSeconds.setSelection(0);

        btnTotal = findViewById(R.id.btnTotal);
        //btnTotal.setEnabled(false);
        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countClick++;
                if (countClick == 10) {
                    countClick = 0;
                    btnTotal.setText("0");
                }
            }
        });
        btnTotal.setText(""+(Integer.parseInt(spinnerViewQuantity.getSelectedItem().toString()) * Integer.parseInt(spinnerWatchSeconds.getSelectedItem().toString())));

        btnDONE = findViewById(R.id.btnDONE);
        btnDONE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!linkVideo.isEmpty()) {
                    if (Long.parseLong(btnTotal.getText().toString()) <= Shared.getLong(CampanhaActivity.this, Shared.KEY_COINS)) {

                        Toast.makeText(CampanhaActivity.this, "Validating campaigns", Toast.LENGTH_LONG).show();

                        API.getCampanhas(Shared.getLong(CampanhaActivity.this, Shared.KEY_ID_USUARIO), new Callback<ArrayList<NextViewCampanha>>() {
                            @Override
                            public void onResponse(Call<ArrayList<NextViewCampanha>> call, Response<ArrayList<NextViewCampanha>> response) {

                                if ((response != null && response.code() == 200 && response.body().size() < 4) || Long.parseLong(btnTotal.getText().toString()) == 0) {
                                    final NextViewCampanha campanha = new NextViewCampanha();
                                    campanha.setUrl(edtURL.getText().toString());
                                    campanha.setCount_quantity(0);
                                    campanha.setIdConta(999);
                                    campanha.setId_usuario(Shared.getLong(CampanhaActivity.this, Shared.KEY_ID_USUARIO));
                                    campanha.setTotal_coins(Long.parseLong(btnTotal.getText().toString()));
                                    campanha.setView_quantity(Long.parseLong(spinnerViewQuantity.getSelectedItem().toString()));
                                    campanha.setWatch_seconds(Long.parseLong(spinnerWatchSeconds.getSelectedItem().toString()));

                                    //
                                    // Apresenta o alert.
                                    //
                                    final SweetAlertDialog pDialog = new SweetAlertDialog(CampanhaActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Sending");
                                    pDialog.setContentText("Wait a moment ...");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    NextViewUsuarioCoins updateCoins = new NextViewUsuarioCoins();
                                    updateCoins.setId(Shared.getLong(CampanhaActivity.this, Shared.KEY_ID_USUARIO));
                                    updateCoins.setId_usuario(Shared.getLong(CampanhaActivity.this, Shared.KEY_ID_USUARIO));

                                    final long myCoins = Shared.getLong(CampanhaActivity.this, Shared.KEY_COINS) - Long.parseLong(btnTotal.getText().toString());
                                    updateCoins.setCoins(myCoins);

                                    Shared.put(CampanhaActivity.this, Shared.KEY_COINS, myCoins);

                                    API.postUsuarioCoins(updateCoins, new Callback<RespostaNextView>() {
                                        @Override
                                        public void onResponse(Call<RespostaNextView> call, Response<RespostaNextView> response) {
                                            Toast.makeText(CampanhaActivity.this, "Refreshing Total Coins. Wait...", Toast.LENGTH_LONG).show();

                                            API.postCampanha(campanha, new Callback<RespostaNextView>() {
                                                @Override
                                                public void onResponse(Call<RespostaNextView> call, Response<RespostaNextView> response) {
                                                    pDialog.dismissWithAnimation();

                                                    if (response != null && response.code() == 200) {
                                                        new SweetAlertDialog(CampanhaActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                                .setTitleText("Success")
                                                                .setContentText("Campaign created")
                                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                    @Override
                                                                    public void onClick(SweetAlertDialog sDialog) {
                                                                        sDialog.dismissWithAnimation();

                                                                        Intent newIntent = new Intent(CampanhaActivity.this,MainActivity.class);
                                                                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                        startActivity(newIntent);
                                                                    }
                                                                })
                                                                .show();
                                                    }
                                                    else {
                                                        new SweetAlertDialog(CampanhaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                                .setTitleText("Error")
                                                                .setContentText("Campaign failed [001]")
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
                                                public void onFailure(Call<RespostaNextView> call, Throwable t) {
                                                    new SweetAlertDialog(CampanhaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                            .setTitleText("Error")
                                                            .setContentText("Campaign failed [002]")
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
                                        public void onFailure(Call<RespostaNextView> call, Throwable t) {
                                            new SweetAlertDialog(CampanhaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Error")
                                                    .setContentText("Refresh Coins failed")
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
                                else {
                                    new SweetAlertDialog(CampanhaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Error")
                                            .setContentText("You have 3 or more campaigns. Remove one of them or wait for it to finish.")
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

                            }
                        });
                    }
                    else{
                        new SweetAlertDialog(CampanhaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setContentText("Insufficient Coins to this operation!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {

                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                }
            }
        });

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(linkVideo); // Link do vídeo
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        youTubePlayer.play();
                    }
                }, 5000);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
    }
}

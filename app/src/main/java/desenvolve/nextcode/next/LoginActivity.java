package desenvolve.nextcode.next;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import desenvolve.nextcode.next.api.API;
import desenvolve.nextcode.next.structure.NextViewUsuario;
import desenvolve.nextcode.next.structure.RespostaNextView;
import desenvolve.nextcode.next.util.Shared;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView privacyPolicy;
    private Button btnOK;
    private EditText emailEdit;
    private Button btnLogin;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdit = findViewById(R.id.emailEdit);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailEdit.getText().toString().isEmpty()) {
                    emailEdit.setError("E-mail or username is invalid");
                    return;
                } else {

                    String personEmail = emailEdit.getText().toString();
                    String imei = getDeviceIMEI();

                    //
                    // Apresenta o alert.
                    //
                    final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Authenticate");
                    pDialog.setContentText("Wait a moment ...");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    NextViewUsuario usuario = new NextViewUsuario();
                    usuario.setIdConta(999);
                    usuario.setPersonEmail(personEmail);
                    usuario.setImei(imei);

                    if (Shared.getLong(LoginActivity.this, Shared.KEY_ID_USUARIO) != 0) {
                        usuario.setId(Shared.getLong(LoginActivity.this, Shared.KEY_ID_USUARIO));
                    }

                    API.postUsuario(usuario, new Callback<RespostaNextView>() {
                        @Override
                        public void onResponse(Call<RespostaNextView> call, Response<RespostaNextView> response) {

                            pDialog.dismissWithAnimation();

                            if (response != null && response.code() == 200) {

                                if (response.body().Sucesso) {
                                    Shared.put(LoginActivity.this, Shared.KEY_ID_USUARIO, response.body().Id);
                                    Shared.put(LoginActivity.this, Shared.KEY_COINS, response.body().Coins);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                                else {
                                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Error")
                                            .setContentText("Authentication failed - Smartphone haven't IMEI")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {

                                                    sDialog.dismissWithAnimation();
                                                }
                                            })
                                            .show();
                                }
                            }
                            else {
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error")
                                        .setContentText("Authentication failed [001]")
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
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Authentication failed [002]")
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
            }
        });

        privacyPolicy = findViewById(R.id.privacyPolicy);
        privacyPolicy.setPaintFlags(privacyPolicy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria o AlertDialog.
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                // Guarda a opção de inflate.
                LayoutInflater inflater = LoginActivity.this.getLayoutInflater();

                // Faz a inflação do layout de configuração.
                final View viewInf = inflater.inflate(R.layout.dialog_layout, null);
                builder.setView(viewInf);

                (btnOK = (Button) viewInf.findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog != null)
                            dialog.cancel();
                    }
                });
                (dialog = builder.create()).show();
            }
        });

        emailEdit.setText(Shared.getString(LoginActivity.this, Shared.KEY_EMAIL));
    }

    /**
     * Returns the unique identifier for the device
     *
     * @return unique identifier for the device
     */
    @SuppressLint("MissingPermission")
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier =  Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceUniqueIdentifier;
    }
}

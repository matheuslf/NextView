package desenvolve.nextcode.next;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import desenvolve.nextcode.next.adapter.CampanhaListaAdapter;
import desenvolve.nextcode.next.api.API;
import desenvolve.nextcode.next.structure.NextViewCampanha;
import desenvolve.nextcode.next.util.Shared;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MinhaCampanhasActivity extends AppCompatActivity {

    private ListView listaCampanhas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_campanhas);

        listaCampanhas = findViewById(R.id.listaCampanhas);

        API.getCampanhas(Shared.getLong(MinhaCampanhasActivity.this, Shared.KEY_ID_USUARIO), new Callback<ArrayList<NextViewCampanha>>() {
            @Override
            public void onResponse(Call<ArrayList<NextViewCampanha>> call, Response<ArrayList<NextViewCampanha>> response) {

                if (response != null && response.code() == 200) {
                    CampanhaListaAdapter adapter = new CampanhaListaAdapter(MinhaCampanhasActivity.this, response.body());
                    listaCampanhas.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NextViewCampanha>> call, Throwable t) {

            }
        });
    }
}

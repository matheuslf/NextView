package desenvolve.nextcode.next.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

import desenvolve.nextcode.next.R;
import desenvolve.nextcode.next.api.API;
import desenvolve.nextcode.next.structure.NextViewCampanha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampanhaListaAdapter extends BaseAdapter {

    private Activity act;
    private ArrayList<NextViewCampanha> campanhas;

    public CampanhaListaAdapter(Activity act, ArrayList<NextViewCampanha> campanhas) {
        this.act = act;
        this.campanhas = campanhas;
    }

    @Override
    public int getCount() {
        return campanhas.size();
    }

    @Override
    public Object getItem(int position) {
        return campanhas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = act.getLayoutInflater().inflate(R.layout.item_lista_campanha, parent, false);
        }

        YouTubeThumbnailView thumb = view.findViewById(R.id.thumb);
        thumb.initialize("AIzaSyBtG_DyXGTtTLkKVXjVTaP5fUGpYIXbfME", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                String linkVideo = "";
                if (campanhas.get(position).getUrl().contains("=")) {
                    linkVideo = campanhas.get(position).getUrl().split("=")[1];
                }
                else {
                    linkVideo = campanhas.get(position).getUrl().substring(campanhas.get(position).getUrl().length() - 11);
                }

                youTubeThumbnailLoader.setVideo(linkVideo);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        TextView WatchSeconds = view.findViewById(R.id.WatchSeconds);
        WatchSeconds.setText("Watch Seconds: : "+campanhas.get(position).getWatch_seconds());

        TextView Counter = view.findViewById(R.id.Counter);
        Counter.setText(""+(campanhas.get(position).getCount_quantity()) + " / "+(campanhas.get(position).getView_quantity()));

        Button btnRemover = view.findViewById(R.id.btnRemover);
        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API.deleteCampanha(campanhas.get(position).getId(), new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        campanhas.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }
}

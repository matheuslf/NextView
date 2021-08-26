package desenvolve.nextcode.next.api;

import java.util.ArrayList;

import desenvolve.nextcode.next.api.endpoint.NextViewEndPoint;
import desenvolve.nextcode.next.structure.NextViewCampanha;
import desenvolve.nextcode.next.structure.NextViewUsuario;
import desenvolve.nextcode.next.structure.NextViewUsuarioCoins;
import desenvolve.nextcode.next.structure.RespostaNextView;
import retrofit2.Callback;

public class API {

    public static void postUsuario(final NextViewUsuario usuario, Callback<RespostaNextView> callback) {
        APIUtil.retrofit.create(NextViewEndPoint.class).postUsuario(usuario).enqueue(callback);
    }

    public static void postUsuarioCoins(final NextViewUsuarioCoins usuario, Callback<RespostaNextView> callback) {
        APIUtil.retrofit.create(NextViewEndPoint.class).postUsuarioCoins(usuario).enqueue(callback);
    }

    public static void postCampanha(final NextViewCampanha campanha, Callback<RespostaNextView> callback) {
        APIUtil.retrofit.create(NextViewEndPoint.class).postCampanha(campanha).enqueue(callback);
    }

    public static void getCampanhas(long id, Callback<ArrayList<NextViewCampanha>> callback) {
        APIUtil.retrofit.create(NextViewEndPoint.class).getCampanhas(id).enqueue(callback);
    }

    public static void getTodasCampanhas(Callback<ArrayList<NextViewCampanha>> callback) {
        APIUtil.retrofit.create(NextViewEndPoint.class).getTodasCampanhas().enqueue(callback);
    }

    public static void deleteCampanha(final long Id, Callback<Boolean> callback) {
        APIUtil.retrofit.create(NextViewEndPoint.class).deleteCampanha(Id).enqueue(callback);
    }
}

package desenvolve.nextcode.next.api.endpoint;

import java.util.ArrayList;

import desenvolve.nextcode.next.structure.NextViewCampanha;
import desenvolve.nextcode.next.structure.NextViewUsuario;
import desenvolve.nextcode.next.structure.NextViewUsuarioCoins;
import desenvolve.nextcode.next.structure.RespostaNextView;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NextViewEndPoint {

    @POST("/api/nextview/usuario/inserir")
    Call<RespostaNextView> postUsuario(@Body NextViewUsuario usuario);

    @POST("/api/nextview/usuario/coins/inserir")
    Call<RespostaNextView> postUsuarioCoins(@Body NextViewUsuarioCoins usuario);

    @POST("/api/nextview/campanhas/inserir")
    Call<RespostaNextView> postCampanha(@Body NextViewCampanha campanha);

    @GET("/api/nextview/campanhas/listar")
    Call<ArrayList<NextViewCampanha>> getCampanhas(@Query("id") long id);

    @GET("/api/nextview/campanhas/listarall")
    Call<ArrayList<NextViewCampanha>> getTodasCampanhas();

    @POST("/api/nextview/campanhas/remover")
    Call<Boolean> deleteCampanha(@Query("Id") long Id);
}

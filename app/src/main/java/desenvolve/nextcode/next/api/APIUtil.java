package desenvolve.nextcode.next.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtil {

    //
    // Cria a URL PRINCIPAL.
    //
    public static final String BASE_URL = "http://api.observatorio.tec.br/";

    //
    // Cria o objeto JSON.
    //
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    //
    // Define o WebService.
    //
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

}

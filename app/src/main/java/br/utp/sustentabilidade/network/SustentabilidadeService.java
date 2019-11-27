package br.utp.sustentabilidade.network;

import java.util.List;

import br.utp.sustentabilidade.models.Agrotoxico;
import br.utp.sustentabilidade.models.Organico;
import br.utp.sustentabilidade.models.Reciclagem;
import br.utp.sustentabilidade.models.Residuo;
import br.utp.sustentabilidade.models.RespostaJSON;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SustentabilidadeService {

    @GET("organico/{id}")
    Call<RespostaJSON<Organico>> listarOrganico(@Path("id") int id);

    @GET("organico/all/{pagina}")
    Call<RespostaJSON<List<Organico>>> listarOrganicos(@Path("pagina") int pagina);

    @POST("organico/new")
    Call<RespostaJSON<Organico>> inserirOrganico(@Body Organico organico);

    @PUT("organico")
    Call<RespostaJSON<Organico>> atualizarOrganico(@Body Organico organico);

    @GET("organico/{id}")
    Call<RespostaJSON<Organico>> removerOrganico(@Path("id") int id);

    // Reciclagem
    @GET("reciclagem/all/{pagina}")
    Call<RespostaJSON<List<Reciclagem>>> listarAllReciclagem(@Path("pagina") int pagina);

    @DELETE("reciclagem/{id}")
    Call<RespostaJSON> deleteReciclagem(@Path("id") int id);

    @POST("reciclagem/new")
    @FormUrlEncoded
    Call<RespostaJSON>inserirReciclagem(
            @Field("titulo") String titulo,
            @Field("descricao") String descricao,
            @Field("foto") String foto
    );

    // AGROTÓXICOS
    @GET("agrotoxico/all/{pagina}")
    Call<RespostaJSON<List<Agrotoxico>>> listarAllAgrotoxicos(@Path("pagina") int pagina);

    @DELETE("agrotoxico/{id}")
    Call<RespostaJSON> deleteAgrotoxico(@Path("id") int id);

    @POST("agrotoxico/new")
    @FormUrlEncoded
    Call<Agrotoxico>inserirAgrotoxico(
            @Field("titulo") String titulo,
            @Field("descricao") String descricao,
            @Field("foto") String foto
    );

    // RESIDUOS
    @GET("residuo/all/{pagina}")
    Call<RespostaJSON<List<Residuo>>> listarAllResiduo(@Path("pagina") int pagina);

    @DELETE("residuo/{id}")
    Call<RespostaJSON> deleteResiduo(@Path("id") int id);

    @POST("residuo/new")
    @FormUrlEncoded
    Call<Residuo>inserirResiduo(
            @Field("titulo") String titulo,
            @Field("descricao") String descricao,
            @Field("foto") String foto
    );
}

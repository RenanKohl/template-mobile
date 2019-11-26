package br.utp.sustentabilidade.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.List;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityResiduoDetailBinding;
import br.utp.sustentabilidade.models.Residuo;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import br.utp.sustentabilidade.widgets.adapters.modal.OpenModalImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResiduoDetailActivity extends AppCompatActivity {
    private ActivityResiduoDetailBinding mBinding;
    private Residuo residuo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Criando botão de voltar no Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_residuo_detail);
        if(getIntent().hasExtra("titulo")){
            residuo = new Residuo();
            Bundle extras = getIntent().getExtras();

            residuo.setTitulo(extras.getString("titulo"));
            residuo.setDescricao(extras.getString("descricao"));
            residuo.setFoto(extras.getString("foto"));
            residuo.setId(extras.getString("id"));

            mBinding.textViewTitleDescriptionRecycle.setText(residuo.getTitulo());
            mBinding.textViewDescriptionDetailRecycle.setText(residuo.getDescricao());
            mBinding.setImageUrl(residuo.getFoto());
            mBinding.imageRecycleDetail.setOnClickListener(e -> openModalImage());
        }
    }

    private void openModalImage(){
        OpenModalImage openModal = new OpenModalImage();
        openModal.OpenModalImageUrl(this, residuo.getFoto());
    }

    /**
     * Método que retorna o evento da actionlbar
     * @param item Botão de voltar
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_residuo_excluir:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Você tem certeza que deseja deletar este item?");
                builder.setPositiveButton(R.string.confirm_buttom, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItemRecycling();
                    }
                });
                builder.setNegativeButton(R.string.cancel_buttom, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancelateMensage();
                    }
                });
                builder.create();
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cancelateMensage(){
        String msg = "cancelado";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void deleteItemRecycling(){
        String msg = "Deletando Item: " + residuo.getId() ;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        Call<RespostaJSON> call = NetworkManager.service().deleteResiduo(Integer.parseInt(residuo.getId()));
        call.enqueue(new Callback<RespostaJSON>() {

            @Override
            public void onResponse(final Call<RespostaJSON> call, final Response<RespostaJSON> response) {
                RespostaJSON<List<Residuo>> resposta = response.body();
                Log.d("TAG", "onResponse: " + resposta);
                Log.d("TAG", "onResponse: " + resposta.getStatus());
                if (resposta != null && resposta.getStatus() == 0) {
                    finish();
                } else {
                    exibirMensagemErro();
                }
            }

            @Override
            public void onFailure(final Call<RespostaJSON> call, final Throwable t) {
                Log.e("TAG", "onFailure: ",t );
                exibirMensagemErro();
            }
        });
    }

    /**
     * Método que cria o menu conforme o arquivo XML
     * @param menu Botão de voltar
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_residuo, menu);
        return true;
    }

    private void exibirMensagemErro() {
        String msg = "Erro ao deletar o item";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

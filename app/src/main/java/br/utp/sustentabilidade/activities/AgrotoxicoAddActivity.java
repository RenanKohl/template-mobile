package br.utp.sustentabilidade.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityAgrotoxicoAddBinding;
import br.utp.sustentabilidade.models.Agrotoxico;
import br.utp.sustentabilidade.network.NetworkManager;
import br.utp.sustentabilidade.widgets.adapters.modal.OpenModalImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgrotoxicoAddActivity extends AppCompatActivity  {
    private ActivityAgrotoxicoAddBinding mBinding;
    private ImageView imageViewUpload;
    private String pathImg = "";
    private Uri imageUri;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_agrotoxico_add);

        mBinding.buttonBack.setOnClickListener(e -> closeActivity());

        mBinding.imageViewUpload.setOnClickListener(v -> openModalImage());
        mBinding.buttonEnviar.setOnClickListener(e -> postAgrotoxicoItem());
        imageViewUpload = (ImageView) mBinding.imageViewUpload;

        // Iniciando loading como GONE
        mBinding.agrotoxicoLoadingAdd.setVisibility(View.GONE);
        mBinding.buttonUploadImage.setOnClickListener(e -> getDevicePhoto());
    }

    private void postAgrotoxicoItem(){
        // Exibe a progressbar
        mBinding.agrotoxicoLoadingAdd.setVisibility(View.VISIBLE);
        // Verificando se os campos estão preenchidos
        if(
        mBinding.editTextAddTitleAgrotoxico.getText().toString().length() > 0 &&
        mBinding.editTextAddDescricaoAgrotoxico.getText().toString().length() > 0 &&
        pathImg.length() > 0
        ){
            Call<Agrotoxico> call = NetworkManager.service().inserirAgrotoxico(
                    mBinding.editTextAddTitleAgrotoxico.getText().toString(),
                    mBinding.editTextAddDescricaoAgrotoxico.getText().toString(),
                    "https://d1qmdf3vop2l07.cloudfront.net/azure-candy.cloudvent.net/compressed/_min_/4b9098a09523e8f3810cc79aca61623f.jpg"
            );

            call.enqueue(new Callback<Agrotoxico>() {
                @Override
                public void onResponse(Call<Agrotoxico> call, Response<Agrotoxico>response) {
                    if(response.isSuccessful()) {
                        responseAddAgrotoxico();
                    }
                }

                @Override
                public void onFailure(Call<Agrotoxico> call, Throwable t) {
                    erroFailure();
                }
            });
        } else {
            String msg = "Preencha todos os campos!" ;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            mBinding.agrotoxicoLoadingAdd.setVisibility(View.GONE);
        }
    }

    public void responseAddAgrotoxico(){
        String msg = "Enviado" ;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        mBinding.agrotoxicoLoadingAdd.setVisibility(View.GONE);
        closeActivity();
    }

    public void erroFailure(){
        String msg = "Algo saio errado!" ;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        mBinding.agrotoxicoLoadingAdd.setVisibility(View.GONE);
        closeActivity();
    }

    private void openModalImage(){
        OpenModalImage openModal = new OpenModalImage();
        openModal.OpenModalImageUri(this, imageUri);
    }

    private void getDevicePhoto(){
        Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(it, 200);
    }

    private void closeActivity(){
        finish();
    }
    /**
     * Classe que retorna o evento da actionlbar
     * @param item Botão de voltar
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {

            // Verifique se a solicitação foi bem-sucedida
            // Obter o URI que aponta para o contato selecionado
            imageUri = data.getData();

            // Precisamos apenas da coluna NUMBER, porque haverá apenas uma linha no resultado
            String[] projection = {MediaStore.Images.Media.DATA};

            // Usando cursor para consultar local storage
            Cursor cursor = getContentResolver()
                    .query(imageUri, projection, null, null, null);

            cursor.moveToFirst();

            int indexColuna = cursor.getColumnIndex(projection[0]); // Resgatando id projection para busca de arquivo
            pathImg = cursor.getString(indexColuna); // Resgatando String com o caminho para a imagem
            cursor.close();

            // Convertendo Image
            image = (BitmapFactory.decodeFile(pathImg));

            imageViewUpload.setImageURI(imageUri); // inserindo imagem na view
        } else
            Log.i("Problem ActivityResult", "Something wrong in onActivityResult method!");
    }

}

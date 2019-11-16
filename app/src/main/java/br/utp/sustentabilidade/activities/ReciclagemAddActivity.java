package br.utp.sustentabilidade.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityReciclagemAddBinding;
import br.utp.sustentabilidade.models.Organico;
import br.utp.sustentabilidade.models.Reciclagem;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import br.utp.sustentabilidade.utils.OpenModalImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReciclagemAddActivity extends AppCompatActivity  {
    private ActivityReciclagemAddBinding mBinding;
    private ImageView imageViewUpload;
    private String pathImg = "";
    private Uri imageUri;
    private Reciclagem reciclagem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reciclagem_add);
        mBinding.buttonBack.setOnClickListener(e -> closeActivity());
        mBinding.imageViewUpload.setOnClickListener(v -> openModalImage());
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        mBinding.buttonEnviar.setOnClickListener(e -> postReciclageItem());
        imageViewUpload = (ImageView) mBinding.imageViewUpload;
        mBinding.buttonUploadImage.setOnClickListener(e -> tirarFoto());
    }

    private void postReciclageItem(){
        reciclagem.setTitulo(mBinding.editTextAddTitleReciclagem.getText().toString());
        reciclagem.setDescricao(mBinding.editTextAddDescricaoReciclagem.getText().toString());
//        Snackbar.make(mBinding.getRoot(), mBinding.editTextAddTitleReciclagem.getText().toString(), Snackbar.LENGTH_SHORT)
//                .show();

        Call<RespostaJSON<List<Reciclagem>>>  call = NetworkManager.service().inserirReciclagem(reciclagem);
        call.enqueue(new Callback<RespostaJSON<List<Reciclagem>>> () {

            @Override
            public void onResponse(final Call<RespostaJSON<List<Reciclagem>>> call, final Response<RespostaJSON<List<Reciclagem>>> response) {
                RespostaJSON<List<Reciclagem>> resposta = response.body();
                Log.d("TAG", "onResponse: " + resposta);
                Log.d("TAG", "onResponse: " + resposta.getStatus());
                if (resposta != null && resposta.getStatus() == 0) {

                } else {
                }
            }

            @Override
            public void onFailure(final Call<RespostaJSON<List<Reciclagem>>> call, final Throwable t) {
                Log.e("TAG", "onFailure: ",t );
            }
        });
    }


    private void openModalImage(){
        OpenModalImage openModal = new OpenModalImage();
        openModal.OpenModalImageUri(this, imageUri);
    }
    private void tirarFoto(){
        Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(it, 200);
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
            Bitmap image = (BitmapFactory.decodeFile(pathImg));

            imageViewUpload.setImageURI(imageUri); // inserindo imagem na view
        } else
            Log.i("Problem ActivityResult", "Something wrong in onActivityResult method!");
    }

    private void closeActivity(){
        finish();
    }
}

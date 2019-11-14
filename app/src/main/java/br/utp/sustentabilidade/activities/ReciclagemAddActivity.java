package br.utp.sustentabilidade.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityReciclagemAddBinding;

public class ReciclagemAddActivity extends AppCompatActivity  {
    private ActivityReciclagemAddBinding mBinding;
    WebView web;
    ProgressBar progressBar;
    ImageView imageViewUpload;

    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reciclagem_add);
        mBinding.buttonBack.setOnClickListener(e -> closeActivity());
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        imageViewUpload = (ImageView) mBinding.imageViewUpload;
        mBinding.buttonUploadImage.setOnClickListener(e -> tirarFoto());
    }
    private void tirarFoto(){
        Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        it.setType("image/*");
        startActivityForResult(it, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {

            // Verifique se a solicitação foi bem-sucedida
            // Obter o URI que aponta para o contato selecionado
            Uri contactUri = data.getData();

            // Precisamos apenas da coluna NUMBER, porque haverá apenas uma linha no resultado
            String[] projection = {MediaStore.Images.Media.DATA};

            // Usando cursor para consultar local storage
            Cursor cursor = getContentResolver()
                    .query(contactUri, projection, null, null, null);

            cursor.moveToFirst();

            int indexColuna = cursor.getColumnIndex(projection[0]); // Resgatando id projection para busca de arquivo
            String pathImg = cursor.getString(indexColuna); // Resgatando String com o caminho para a imagem
            cursor.close();

            // Convertendo Image
            Bitmap image = (BitmapFactory.decodeFile(pathImg));

            imageViewUpload.setImageURI(contactUri); // inserindo imagem na view
        } else
            Log.i("Problem ActivityResult", "Something wrong in onActivityResult method!");
    }

    private void closeActivity(){
        finish();
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }
*/
}

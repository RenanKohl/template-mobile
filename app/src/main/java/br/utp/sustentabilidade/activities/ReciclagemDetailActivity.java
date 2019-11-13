package br.utp.sustentabilidade.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityReciclagemDetailBinding;
import br.utp.sustentabilidade.models.Reciclagem;

public class ReciclagemDetailActivity extends AppCompatActivity {
    private ActivityReciclagemDetailBinding mBinding;
    private Reciclagem reciclagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reciclagem_detail);
        mBinding.buttonBack.setOnClickListener(e -> closeActivity());
        if(getIntent().hasExtra("titulo")){
            reciclagem = new Reciclagem();
            Bundle extras = getIntent().getExtras();

            reciclagem.setTitulo(extras.getString("titulo"));
            reciclagem.setDescricao(extras.getString("descricao"));
            reciclagem.setFoto(extras.getString("foto"));
            mBinding.textViewTitleDescriptionRecycle.setText(reciclagem.getTitulo());
            mBinding.textViewDescriptionDetailRecycle.setText(reciclagem.getDescricao());
            mBinding.setImageUrl(reciclagem.getFoto());
        }
    }

    private void closeActivity(){
        Toast.makeText(this, "Voltar", Toast.LENGTH_SHORT).show();
        finish();
    }

}

package br.utp.sustentabilidade.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityAgrotoxicoDetailBinding;
import br.utp.sustentabilidade.models.Agrotoxico;
import br.utp.sustentabilidade.widgets.adapters.modal.OpenModalImage;

public class AgrotoxicoDetailActivity extends AppCompatActivity {
    private ActivityAgrotoxicoDetailBinding mBinding;
    private Agrotoxico agrotoxico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_agrotoxico_detail);
        mBinding.buttonBack.setOnClickListener(e -> closeActivity());
        if(getIntent().hasExtra("titulo")){
            agrotoxico = new Agrotoxico();
            Bundle extras = getIntent().getExtras();

            agrotoxico.setTitulo(extras.getString("titulo"));
            agrotoxico.setDescricao(extras.getString("descricao"));
            agrotoxico.setFoto(extras.getString("foto"));
            mBinding.textViewTitleDescriptionRecycle.setText(agrotoxico.getTitulo());
            mBinding.textViewDescriptionDetailRecycle.setText(agrotoxico.getDescricao());
            mBinding.setImageUrl(agrotoxico.getFoto());
            mBinding.imageRecycleDetail.setOnClickListener(e -> openModalImage());
        }
    }

    private void openModalImage(){
        OpenModalImage openModal = new OpenModalImage();
        openModal.OpenModalImageUrl(this, agrotoxico.getFoto());
    }

    private void closeActivity(){
        finish();
    }

}

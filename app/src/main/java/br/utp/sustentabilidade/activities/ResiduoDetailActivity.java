package br.utp.sustentabilidade.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityResiduoDetailBinding;
import br.utp.sustentabilidade.models.Residuo;
import br.utp.sustentabilidade.utils.OpenModalImage;

public class ResiduoDetailActivity extends AppCompatActivity {
    private ActivityResiduoDetailBinding mBinding;
    private Residuo residuo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_residuo_detail);
        mBinding.buttonBack.setOnClickListener(e -> closeActivity());
        if(getIntent().hasExtra("titulo")){
            residuo = new Residuo();
            Bundle extras = getIntent().getExtras();

            residuo.setTitulo(extras.getString("titulo"));
            residuo.setDescricao(extras.getString("descricao"));
            residuo.setFoto(extras.getString("foto"));
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

    private void closeActivity(){
        finish();
    }

}

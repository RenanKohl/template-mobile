package br.utp.sustentabilidade.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityMainBinding;
import br.utp.sustentabilidade.fragments.AgrotoxicosFragment;
import br.utp.sustentabilidade.fragments.ResiduosFragment;
import br.utp.sustentabilidade.fragments.OrganicoFragment;
import br.utp.sustentabilidade.fragments.OrganicoReducaoLixoFragment;
import br.utp.sustentabilidade.fragments.ReciclagemFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Visibilidade";
    private ActivityMainBinding mBinding;
    private String pageTitle = null;
    // Inicializa os fragmentos
    Fragment mFragmentoOrganico = OrganicoFragment.newInstance();
    Fragment mOrganicoReciclagemFragment = ReciclagemFragment.newInstance();
    Fragment mOrganicoAgrotoxicosFragment = AgrotoxicosFragment.newInstance();
    Fragment mOrganicoReducaoLixoFragment = OrganicoReducaoLixoFragment.newInstance();
    Fragment mOrganicoFragmentResiduos = ResiduosFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Cadastra os eventos da bottom navigation
        mBinding.mainBottomNavigation.setOnNavigationItemSelectedListener(this::onSelecionarFragmento);

        // Cadastra evento botão addItem
        mBinding.addItem.setOnClickListener(v -> {
            addItem();
        });

        // Define o primeiro fragmento a ser visualizado
        trocarFragmentos(mFragmentoOrganico);
    }

    private void addItem(){
        if(pageTitle != null){
            switch (pageTitle){
                case "Reciclagem ♻":
                    // Abrir activity para adicionar: Reciclagem
                    Intent it = new Intent(this, ReciclagemAddActivity.class);
                    startActivity(it);
                    return;
                case "Agrotóxicos ♨":
                    // Abrir activity para adicionar: Agrotóxicos
                    Intent agrotoxico = new Intent(this, AgrotoxicoAddActivity.class);
                    startActivity(agrotoxico);
                    // Exibe um snackbar Para test apagar quando for implementar
                    //Snackbar.make(mBinding.getRoot(), "Agrotóxicos", Snackbar.LENGTH_SHORT).show();
                    return;
                case "Redução de lixos \uD83D\uDDD1":
                    // Abrir activity para adicionar: Redução de lixos
                    // Exibe um snackbar Para test apagar quando for implementar
                    Snackbar.make(mBinding.getRoot(), "Redução de lixos", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                case "Resíduos \uD83D\uDEAF":
                    // Abrir activity para adicionar: Resíduos
                    Intent residuo = new Intent(this, ResiduoAddActivity.class);
                    startActivity(residuo);
                    // Exibe um snackbar Para test apagar quando for implementar
                    // Snackbar.make(mBinding.getRoot(), "Resíduos", Snackbar.LENGTH_SHORT).show();
                    return;
            }
        }
    }

    private boolean onSelecionarFragmento(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_organicos_reciclagem:
                pageTitle = "Reciclagem ♻";
                mBinding.mainTextTitle.setText(pageTitle); // Definindo titulo de página
                trocarFragmentos(mOrganicoReciclagemFragment);
                return true;
            case R.id.action_organicos_agrotoxicos:
                pageTitle = "Agrotóxicos ♨";
                mBinding.mainTextTitle.setText(pageTitle); // Definindo titulo de página
                trocarFragmentos(mOrganicoAgrotoxicosFragment);
                return true;
            case R.id.action_organicos_reducao_lixo:
                pageTitle = "Redução de lixos \uD83D\uDDD1";
                mBinding.mainTextTitle.setText(pageTitle); // Definindo titulo de página
                trocarFragmentos(mOrganicoReducaoLixoFragment);
                return true;
            case R.id.action_organicos_residuos:
                pageTitle = "Resíduos \uD83D\uDEAF";
                mBinding.mainTextTitle.setText(pageTitle); // Definindo titulo de página
                trocarFragmentos(mOrganicoFragmentResiduos);
                return true;
        }
        return false;
    }

    private void trocarFragmentos(final Fragment fragmento) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.main_content, fragmento);
        transaction.commit();
    }
}

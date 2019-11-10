package br.utp.sustentabilidade.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityMainBinding;
import br.utp.sustentabilidade.fragments.OrganicoFragment;
import br.utp.sustentabilidade.fragments.OrganicoFragmentResiduos;
import br.utp.sustentabilidade.fragments.ReciclagemFragment;
import br.utp.sustentabilidade.fragments.OrganicoAgrotoxicosFragment;
import br.utp.sustentabilidade.fragments.OrganicoReducaoLixoFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Visibilidade";
    private ActivityMainBinding mBinding;

    // Inicializa os fragmentos
    Fragment mFragmentoOrganico = OrganicoFragment.newInstance();
    Fragment mOrganicoReciclagemFragment = ReciclagemFragment.newInstance();
    Fragment mOrganicoAgrotoxicosFragment = OrganicoAgrotoxicosFragment.newInstance();
    Fragment mOrganicoReducaoLixoFragment = OrganicoReducaoLixoFragment.newInstance();
    Fragment mOrganicoFragmentResiduos = OrganicoFragmentResiduos.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Cadastra os eventos da bottom navigation
        mBinding.mainBottomNavigation.setOnNavigationItemSelectedListener(this::onSelecionarFragmento);
        // Define o primeiro fragmento a ser visualizado
        trocarFragmentos(mFragmentoOrganico);
    }

    private boolean onSelecionarFragmento(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_organicos_reciclagem:
                trocarFragmentos(mOrganicoReciclagemFragment);
                return true;
            case R.id.action_organicos_agrotoxicos:
                trocarFragmentos(mOrganicoAgrotoxicosFragment);
                return true;
            case R.id.action_organicos_reducao_lixo:
                trocarFragmentos(mOrganicoReducaoLixoFragment);
                return true;
            case R.id.action_organicos_residuos:
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

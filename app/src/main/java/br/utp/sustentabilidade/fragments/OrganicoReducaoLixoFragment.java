package br.utp.sustentabilidade.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.FragmentOrganicoBinding;
import br.utp.sustentabilidade.databinding.FragmentOrganicoReducaoLixoBinding;
import br.utp.sustentabilidade.models.Organico;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import br.utp.sustentabilidade.widgets.adapters.OrganicoAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganicoReducaoLixoFragment extends Fragment {

    private FragmentOrganicoReducaoLixoBinding mBinding;
    private List<Organico> mOrganicos;

    /**
     * Construtor de fragmentos.
     *
     * @return Retorna uma instância do fragmento de produtos orgânicos.
     */
    public static OrganicoReducaoLixoFragment newInstance() {
        return new OrganicoReducaoLixoFragment();
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_organico_reducao_lixo, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NetworkManager.cancelRequests();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void carregarWebService(final int pagina) {

    }

    private void atualizarListaOrganicos(final List<Organico> organicos) {

    }

    private void exibirMensagemErro() {

    }

}

package br.utp.sustentabilidade.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.FragmentOrganicoBinding;
import br.utp.sustentabilidade.models.Organico;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import br.utp.sustentabilidade.widgets.adapters.OrganicoAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganicoFragment extends Fragment {

    private FragmentOrganicoBinding mBinding;
    private List<Organico> mOrganicos;

    /**
     * Construtor de fragmentos.
     *
     * @return Retorna uma instância do fragmento de produtos orgânicos.
     */
    public static OrganicoFragment newInstance() {
        return new OrganicoFragment();
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_organico, container, false);

        // Inicializa a lista de produtos orgânicos
        mOrganicos = new ArrayList<>();

        // Inicializa o recycler view
        OrganicoAdapter adapter = new OrganicoAdapter(mOrganicos);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mBinding.organicoRecyclerView.setAdapter(adapter);
        mBinding.organicoRecyclerView.setLayoutManager(layout);

        // Exibe a progressbar
        // mBinding.organicoLoading.setVisibility(View.VISIBLE);

        // Chama o Webservice
        // carregarWebService(0); <- Verificar erro WebService

        // Esconde a progressbar
        mBinding.organicoLoading.setVisibility(View.GONE);

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
        Call<RespostaJSON<List<Organico>>> call = NetworkManager.service().listarOrganicos(0);
        call.enqueue(new Callback<RespostaJSON<List<Organico>>>() {

            @Override
            public void onResponse(final Call<RespostaJSON<List<Organico>>> call, final Response<RespostaJSON<List<Organico>>> response) {
                RespostaJSON<List<Organico>> resposta = response.body();
                Log.d("TAG", "onResponse: " + resposta);
                Log.d("TAG", "onResponse: " + resposta.getStatus());
                if (resposta != null && resposta.getStatus() == 0) {
                    atualizarListaOrganicos(resposta.getObject());
                } else {
                    exibirMensagemErro();
                }
            }

            @Override
            public void onFailure(final Call<RespostaJSON<List<Organico>>> call, final Throwable t) {
                Log.e("TAG", "onFailure: ",t );
                exibirMensagemErro();
            }
        });
    }

    private void atualizarListaOrganicos(final List<Organico> organicos) {
        // Atualiza os elementos da lista
        mOrganicos.addAll(organicos);
        mBinding.organicoRecyclerView.getAdapter().notifyDataSetChanged();

        // Esconde a progressbar
        mBinding.organicoLoading.setVisibility(View.GONE);
    }

    private void exibirMensagemErro() {
        // Esconde a progressbar
        mBinding.organicoLoading.setVisibility(View.GONE);

        // Exibe um snackbar com a mensagem de erro
        Snackbar.make(mBinding.getRoot(), R.string.organico_erro_webservice, Snackbar.LENGTH_SHORT)
                .show();
    }

}

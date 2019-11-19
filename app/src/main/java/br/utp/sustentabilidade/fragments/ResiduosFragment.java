package br.utp.sustentabilidade.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.activities.ResiduoDetailActivity;
import br.utp.sustentabilidade.databinding.FragmentResiduosBinding;
import br.utp.sustentabilidade.models.Residuo;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import br.utp.sustentabilidade.widgets.adapters.modal.OpenModalImage;
import br.utp.sustentabilidade.widgets.adapters.ResiduoAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResiduosFragment extends Fragment implements ResiduoAdapter.ResiduoListener {

    private FragmentResiduosBinding mBinding;
    private List<Residuo> mResiduos;
    private int pagina = 0;

    /**
     * Construtor de fragmentos.
     *
     * @return Retorna uma instância do fragmento de resíduos.
     */
    public static ResiduosFragment newInstance() {
        return new ResiduosFragment();
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        pagina = 0;
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_residuos, container, false);
        mResiduos = new ArrayList<>();

        ResiduoAdapter adapter = new ResiduoAdapter(mResiduos, this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mBinding.residuoRecyclerView.setAdapter(adapter);
        mBinding.residuoRecyclerView.setLayoutManager(layout);
        mBinding.residuoRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        carregarWebService(pagina);

        return mBinding.getRoot();
    }

    /**
     * Método que controla a páginação da tela via Scroll do RecyclerView
     */
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        // Método acionado quando é feito scroll da tela
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            // Verifica se o scroll chegou no inicio da tela
            if (!recyclerView.canScrollVertically(-1)) {
                onScrolledToTop();
                // Verifica se o scroll chegou no fim da tela
            } else if (!recyclerView.canScrollVertically(1)) {
                onScrolledToBottom();
                // Verifica se a direção do scroll é para cima
            } else if (dy < 0) {
                onScrolledUp();
                // Verifica se a direção do scroll é para baixo
            } else if (dy > 0) {
                onScrolledDown();
            }
        }
    };

    // Aqui faz a ação do scrom em movimento para cima
    public void onScrolledUp() {}
    // Aqui faz a ação do scrom em movimento para baixo
    public void onScrolledDown() {}
    // Aqui faz a ação do scrom quando chega ao topo
    public void onScrolledToTop() {
    }
    // Aqui faz a ação do scrom quando chega ao final de tela
    public void onScrolledToBottom() {
        // Exibe a progressbar
        mBinding.residuoLoading.setVisibility(View.VISIBLE);

        // soma a paginação
        pagina = pagina + 1;

        // Carrega o Web Service
        carregarWebService(pagina);
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
        Call<RespostaJSON<List<Residuo>>> call = NetworkManager.service().listarAllResiduo(pagina);
        call.enqueue(new Callback<RespostaJSON<List<Residuo>>>() {

            @Override
            public void onResponse(final Call<RespostaJSON<List<Residuo>>> call, final Response<RespostaJSON<List<Residuo>>> response) {
                RespostaJSON<List<Residuo>> resposta = response.body();
                Log.d("TAG", "onResponse: " + resposta);
                Log.d("TAG", "onResponse: " + resposta.getStatus());
                if (resposta != null && resposta.getStatus() == 0) {
                    atualizarListaOrganicos(resposta.getObject());
                } else {
                    exibirMensagemErro();
                }
            }

            @Override
            public void onFailure(final Call<RespostaJSON<List<Residuo>>> call, final Throwable t) {
                Log.e("TAG", "onFailure: ",t );
                exibirMensagemErro();
            }
        });
    }

    private void atualizarListaOrganicos(final List<Residuo> organicos) {
        if(organicos.size() > 0){
            // For de verificação item a item para evitar itens inconsistentes
            for(int i = 0; i < organicos.size(); i++){
                /*
                 *  IF que verifica se existe dados no objeto que foi recebido do webService
                 *  para evitar quebra da aplicação colocando um card com itens inconsistentes
                 */
                if(
                        organicos.get(i).getTitulo().length() > 0 &&
                                organicos.get(i).getDescricao().length() > 0 &&
                                organicos.get(i).getFoto().length() > 0
                ){
                    mResiduos.add(organicos.get(i));
                }
            }
            // Atualiza os elementos da lista na view
            mBinding.residuoRecyclerView.getAdapter().notifyDataSetChanged();
        } else {
            String msg = "Sem dados para carga";
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            pagina = pagina - 1;
        }
        // esconde a progressbar
        mBinding.residuoLoading.setVisibility(View.GONE);
    }

    private void exibirMensagemErro() {
        String msg = "Sem dados para carga";
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFotoClick(Residuo residuo) {
        OpenModalImage openModal = new OpenModalImage();
        openModal.OpenModalImageUrl(getContext(), residuo.getFoto());
    }

    @Override
    public void onDetalheClick(Residuo residuo) {
        // String msg = "Detalhes: " + residuo.getTitulo();
        // Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        Intent it = new Intent(getContext(), ResiduoDetailActivity.class);
        it.putExtra("titulo", residuo.getTitulo());
        it.putExtra("descricao", residuo.getDescricao());
        it.putExtra("foto", residuo.getFoto());
        startActivity(it);
    }

    @Override
    public void onClickDelete(Residuo residuo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Você tem certeza que deseja deletar este item?");

        builder.setPositiveButton(R.string.confirm_buttom, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String msg = "Item de ID foi deletado: " + residuo.getId();
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel_buttom, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String msg = "Ação cancelada!";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        builder.create();
        builder.show();

    }
}
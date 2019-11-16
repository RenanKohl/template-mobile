package br.utp.sustentabilidade.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.activities.ReciclagemDetailActivity;
import br.utp.sustentabilidade.databinding.FragmentReciclagemBinding;
import br.utp.sustentabilidade.models.Reciclagem;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import br.utp.sustentabilidade.utils.OpenModalImage;
import br.utp.sustentabilidade.widgets.adapters.ReciclagemAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReciclagemFragment extends Fragment implements ReciclagemAdapter.ReciclagemListener {
    private FragmentReciclagemBinding mBinding;
    private List<Reciclagem> mReciclagem;
    private int pagina = 0;

    /**
     * Construtor de fragmentos.
     *
     * @return Retorna uma instância do fragmento de produtos orgânicos.
     */
    public static ReciclagemFragment newInstance() {
        return new ReciclagemFragment();
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reciclagem, container, false);
        // Inicializa a lista de produtos orgânicos
        mReciclagem = new ArrayList<>();

        // Inicializa o recycler view
        ReciclagemAdapter adapter = new ReciclagemAdapter(mReciclagem, this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mBinding.reciclagemRecyclerView.setAdapter(adapter);
        mBinding.reciclagemRecyclerView.setLayoutManager(layout);

        // Exibe a progressbar
        mBinding.reciclagemLoading.setVisibility(View.VISIBLE);

        //Controle de páginação via RecyclerView
        mBinding.reciclagemRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        carregarWebService(pagina);

        // atualizarListaOrganicos(null);
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
        mBinding.reciclagemLoading.setVisibility(View.VISIBLE);

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
        Call<RespostaJSON<List<Reciclagem>>> call = NetworkManager.service().listarAllReciclagem(pagina);
        call.enqueue(new Callback<RespostaJSON<List<Reciclagem>>>() {

            @Override
            public void onResponse(final Call<RespostaJSON<List<Reciclagem>>> call, final Response<RespostaJSON<List<Reciclagem>>> response) {
                RespostaJSON<List<Reciclagem>> resposta = response.body();
                Log.d("TAG", "onResponse: " + resposta);
                Log.d("TAG", "onResponse: " + resposta.getStatus());
                if (resposta != null && resposta.getStatus() == 0) {
                    atualizarListaOrganicos(resposta.getObject());
                } else {
                    exibirMensagemErro();
                }
            }

            @Override
            public void onFailure(final Call<RespostaJSON<List<Reciclagem>>> call, final Throwable t) {
                Log.e("TAG", "onFailure: ",t );
                exibirMensagemErro();
            }
        });
    }

    private void atualizarListaOrganicos(final List<Reciclagem> organicos) {
        if(organicos.size() > 0){
            // For de verificação item a item para evitar itens inconsistentes
            for(int i = 0; i < organicos.size(); i++){
                /*
                 *  IF que verifica se existe dados no objeto que foi recebido do webService
                 *  para evitar quebra da aplicação colocando um card com itens inconsistentes
                 */
                if(organicos.get(i).getTitulo().length() > 0 && organicos.get(i).getDescricao().length() > 0){
                    mReciclagem.add(organicos.get(i));
                }
            }

            // Atualiza os elementos da lista na view
            mBinding.reciclagemRecyclerView.getAdapter().notifyDataSetChanged();
        } else {
            String msg = "Sem dados para carga";
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            pagina = pagina - 1;
        }
        // esconde a progressbar
        mBinding.reciclagemLoading.setVisibility(View.GONE);
    }

    private void exibirMensagemErro() {
         String msg = "Sem dados para carga";
         Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFotoClick(Reciclagem reciclagem) {
        OpenModalImage openModal = new OpenModalImage();
        openModal.OpenModalImageUrl(getContext(), reciclagem.getFoto());
    }

    @Override
    public void onDetalheClick(Reciclagem reciclagem) {
        // String msg = "Detalhes: " + reciclagem.getTitulo();
        // Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        Intent it = new Intent(getContext(), ReciclagemDetailActivity.class);
        it.putExtra("titulo", reciclagem.getTitulo());
        it.putExtra("descricao", reciclagem.getDescricao());
        it.putExtra("foto", reciclagem.getFoto());
        startActivity(it);
    }
}

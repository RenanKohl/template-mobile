package br.utp.sustentabilidade.widgets.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.utp.sustentabilidade.databinding.ItemReciclagemBinding;
import br.utp.sustentabilidade.models.Reciclagem;

public class ReciclagemAdapter extends RecyclerView.Adapter<ReciclagemAdapter.ReciclagemViewHolder> {

    private final List<Reciclagem> mReciclagem;
    private final ReciclagemListener mListener;

    public ReciclagemAdapter(List<Reciclagem> reciclagem, ReciclagemListener listener) {
        mReciclagem = reciclagem;
        mListener = listener;
    }

    @NonNull
    @Override
    public ReciclagemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReciclagemBinding binding = ItemReciclagemBinding.inflate(layoutInflater, parent, false);
        return new ReciclagemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReciclagemViewHolder holder, final int position) {
        holder.bind(mReciclagem.get(position));
    }

    @Override
    public int getItemCount() {
        return mReciclagem.size();
    }

    /**
     * Eventos de callback do adapter
     */
    public interface ReciclagemListener{
        void onFotoClick(Reciclagem reciclagem);
        void onDetalheClick(Reciclagem reciclagem);
        void onClickDelete(Reciclagem reciclagem);
    }

    /**
     * Armazena os dados da view.
     */
    class ReciclagemViewHolder extends RecyclerView.ViewHolder {

        private final ItemReciclagemBinding mBinding;

        public ReciclagemViewHolder(final ItemReciclagemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Reciclagem reciclagem) {
            mBinding.setReciclagem(reciclagem);

            // TODO: Amarrar eventos
            mBinding.reciclagemImgFoto.setOnClickListener(v -> mListener.onFotoClick(reciclagem));
            mBinding.reciclagemButtomDetail.setOnClickListener(v -> mListener.onDetalheClick(reciclagem));
        }
    }
}

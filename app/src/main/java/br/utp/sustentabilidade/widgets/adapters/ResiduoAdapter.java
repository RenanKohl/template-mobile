package br.utp.sustentabilidade.widgets.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.utp.sustentabilidade.databinding.ItemResiduoBinding;
import br.utp.sustentabilidade.models.Residuo;

public class ResiduoAdapter extends RecyclerView.Adapter<ResiduoAdapter.ResiduoViewHolder> {
    private final List<Residuo> mResiduo;
    private final ResiduoListener mListener;

    public ResiduoAdapter(List<Residuo> residuo, ResiduoListener listener) {
        mResiduo = residuo;
        mListener = listener;
    }

    @NonNull
    @Override
    public ResiduoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemResiduoBinding binding = ItemResiduoBinding.inflate(layoutInflater, parent, false);
        return new ResiduoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResiduoViewHolder holder, final int position) {
        holder.bind(mResiduo.get(position));
    }

    @Override
    public int getItemCount() {
        return mResiduo.size();
    }

    /**
     * Eventos de callback do adapter
     */
    public interface ResiduoListener{
        void onFotoClick(Residuo residuo);
        void onDetalheClick(Residuo residuo);
        void onClickDelete(Residuo residuo);
    }

    /**
     * Armazena os dados da view.
     */
    class ResiduoViewHolder extends RecyclerView.ViewHolder {

        private final ItemResiduoBinding mBinding;

        public ResiduoViewHolder(final ItemResiduoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Residuo residuo) {
            mBinding.setResiduo(residuo);

            // TODO: Amarrar eventos
            mBinding.residuoImgFoto.setOnClickListener(v -> mListener.onFotoClick(residuo));
            mBinding.residuoButtomDetail.setOnClickListener(v -> mListener.onDetalheClick(residuo));
            mBinding.buttonDeleteCardDelete.setOnClickListener(v -> mListener.onClickDelete(residuo));
        }
    }
}

package br.utp.sustentabilidade.widgets.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.utp.sustentabilidade.databinding.ItemAgrotoxicoBinding;
import br.utp.sustentabilidade.models.Agrotoxico;

public class AgrotoxicoAdapter extends RecyclerView.Adapter<AgrotoxicoAdapter.AgrotoxicoViewHolder> {
    private final List<Agrotoxico> mAgrotoxico;
    private final AgrotoxicoListener mListener;

    public AgrotoxicoAdapter(List<Agrotoxico> agrotoxico, AgrotoxicoListener listener) {
        mAgrotoxico = agrotoxico;
        mListener = listener;
    }

    @NonNull
    @Override
    public AgrotoxicoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAgrotoxicoBinding binding = ItemAgrotoxicoBinding.inflate(layoutInflater, parent, false);
        return new AgrotoxicoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final AgrotoxicoViewHolder holder, final int position) {
        holder.bind(mAgrotoxico.get(position));
    }

    @Override
    public int getItemCount() {
        return mAgrotoxico.size();
    }

    /**
     * Eventos de callback do adapter
     */
    public interface AgrotoxicoListener{
        void onFotoClick(Agrotoxico agrotoxico);
        void onDetalheClick(Agrotoxico agrotoxico);
        void onClickDelete(Agrotoxico agrotoxico);
    }

    /**
     * Armazena os dados da view.
     */
    class AgrotoxicoViewHolder extends RecyclerView.ViewHolder {

        private final ItemAgrotoxicoBinding mBinding;

        public AgrotoxicoViewHolder(final ItemAgrotoxicoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Agrotoxico agrotoxico) {
            mBinding.setAgrotoxico(agrotoxico);

            // TODO: Amarrar eventos
            mBinding.agrotoxicoImgFoto.setOnClickListener(v -> mListener.onFotoClick(agrotoxico));
            mBinding.agrotoxicoButtomDetail.setOnClickListener(v -> mListener.onDetalheClick(agrotoxico));
            //mBinding.buttonDeleteCardDelete.setOnClickListener(v -> mListener.onClickDelete(agrotoxico));
        }
    }
}

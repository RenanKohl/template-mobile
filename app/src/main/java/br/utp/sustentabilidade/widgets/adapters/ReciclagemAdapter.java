package br.utp.sustentabilidade.widgets.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.utp.sustentabilidade.databinding.ItemReciclagemBinding;
import br.utp.sustentabilidade.models.Reciclagem;

public class ReciclagemAdapter extends RecyclerView.Adapter<ReciclagemAdapter.OrganicoViewHolder> {

    private final List<Reciclagem> mOrganicos;

    public ReciclagemAdapter(List<Reciclagem> organicos) {
        mOrganicos = organicos;
    }

    @NonNull
    @Override
    public OrganicoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReciclagemBinding binding = ItemReciclagemBinding.inflate(layoutInflater, parent, false);
        return new OrganicoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrganicoViewHolder holder, final int position) {
        holder.bind(mOrganicos.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrganicos.size();
    }

    /**
     * Armazena os dados da view.
     */
    class OrganicoViewHolder extends RecyclerView.ViewHolder {

        private final ItemReciclagemBinding mBinding;

        public OrganicoViewHolder(final ItemReciclagemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Reciclagem organico) {
            mBinding.setReciclagem(organico);

            // TODO: Exibir foto

            // TODO: Amarrar eventos
        }
    }
}

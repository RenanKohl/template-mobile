package br.utp.sustentabilidade.widgets.adapters;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.utp.sustentabilidade.R;

public class DataBindingAdapters {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_reciclagem_background)
                .into(view);
    }
}



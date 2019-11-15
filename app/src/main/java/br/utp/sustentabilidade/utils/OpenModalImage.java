package br.utp.sustentabilidade.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import java.io.InputStream;
import java.net.URL;

import br.utp.sustentabilidade.R;

public class OpenModalImage {
    private Dialog MyDialog;
    private ImageView img;
    private Drawable image;
    private MaterialButton button;

    public void OpenModalImageUrl(Context mContext, String imageUrl){
        // Instanciando dialog
        MyDialog = new Dialog(mContext);

        // Cadastrando o evendo de fechar o modal
        button = (MaterialButton)MyDialog.findViewById(R.id.button_exit_modal);
//        button.setOnClickListener(e -> exitModal());
        // Definindo XML de comunicação do dialog
        MyDialog.setContentView(R.layout.modal_open_image);

        // Inserindo imagem pelo ID do modal inscrito no XML
        img = (ImageView)MyDialog.findViewById(R.id.img_open);

        // Convertendo URL image em Drawable
        image = loadImageFromWebURL(imageUrl);

        // setando imagem no modal View
        img.setImageDrawable(image);

        // Exibindo Modal
        MyDialog.show();
    }

    private void exitModal(){
        MyDialog.dismiss();
    }

    public void OpenModalImageUri(Context mContext, Uri imageUri){
        // Instanciando dialog
        MyDialog = new Dialog(mContext);

        // Cadastrando o evendo de fechar o modal
        button = (MaterialButton)MyDialog.findViewById(R.id.button_exit_modal);
//        button.setOnClickListener(e -> exitModal());
        // Definindo XML de comunicação do dialog
        MyDialog.setContentView(R.layout.modal_open_image);

        // Inserindo imagem pelo ID do modal inscrito no XML
        img = (ImageView)MyDialog.findViewById(R.id.img_open);

        // setando imagem no modal View
        img.setImageURI(imageUri);

        // Exibindo Modal
        MyDialog.show();
    }

    private static Drawable loadImageFromWebURL(String url) {
        try {
            InputStream iStream = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(iStream, "src name");
            return drawable;
        } catch (Exception e) {
            return null;
    }}
}

package com.valdiviezomazautp.lockerapp.Fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.valdiviezomazautp.lockerapp.R;


public class F_AcercaDe extends Fragment {

    ImageView Ir_facebook, Ir_Instagram, Ir_Youtube, Ir_Twitter;
    Button Ir_terminos_politicas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__acerca_de, container, false);

        Ir_facebook = view.findViewById(R.id.Ir_facebook);
        Ir_Instagram = view.findViewById(R.id.Ir_Instagram);
        Ir_Youtube = view.findViewById(R.id.Ir_Youtube);
        Ir_Twitter = view.findViewById(R.id.Ir_Twitter);

        Ir_terminos_politicas = view.findViewById(R.id.Ir_terminos_politicas);

        Ir_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir_p_facebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
                startActivity(ir_p_facebook);
            }
        });

        Ir_Instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir_p_instagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"));
                startActivity(ir_p_instagram);
            }
        });

        Ir_Youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir_p_youtube = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
                startActivity(ir_p_youtube);
            }
        });

        Ir_Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir_p_twitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com"));
                startActivity(ir_p_twitter);
            }
        });

        Ir_terminos_politicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir_p_terminos_politicas = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/politicasyterminos/inicio"));
                startActivity(ir_p_terminos_politicas);
            }
        });

        return view;
    }
}
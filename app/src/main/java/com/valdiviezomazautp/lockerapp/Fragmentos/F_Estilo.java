// F_Estilo.java

package com.valdiviezomazautp.lockerapp.Fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.valdiviezomazautp.lockerapp.R;

public class F_Estilo extends Fragment {

        private static final String PREF_NAME = "color_preferences";
        private static final String KEY_PRIMARY_DARK = "primary_dark";
        private static final String KEY_PRIMARY = "primary";
        private static final String KEY_BACKGROUND = "background";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                return inflater.inflate(R.layout.fragment_f__estilo, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);

                view.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Corrected color code
                                String primary = "#871616"; // Corrected color code
                                String background = "#FFFFFF"; // Corrected color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });
                view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#333333"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#F44236"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#CC33CC"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#FFBB86FC"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#FF6200EE"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#000C7B"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#03A9F5"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#08FCF2"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#367E7F"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#0b7855"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button12).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#29c96b"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#81c10a"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button14).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#D81B60"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button15).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#EA3680"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button16).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#75163F"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button17).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#774342"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button18).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#808080"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button18).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#7F82BB"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });

                view.findViewById(R.id.button19).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String primaryDark = "#00000000"; // Example color code
                                String primary = "#7F82BB"; // Example color code
                                String background = "#FFFFFF"; // Example color code
                                guardarColorSeleccionado(primaryDark, primary, background);
                                cambiarColor(primaryDark, primary, background);
                        }
                });


                cargarColorAlmacenado();
        }

        private void cambiarColor(String primaryDark, String primary, String background) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                        //colorPrimaryDark
                        activity.getWindow().setStatusBarColor(Color.parseColor(primaryDark));
                        //colorPrimary
                        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
                        //background
                        activity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
                        //bottom navigation
                        activity.getWindow().setNavigationBarColor(Color.parseColor(primary));
                }
        }

        private void guardarColorSeleccionado(String primaryDark, String primary, String background) {
                SharedPreferences preferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_PRIMARY_DARK, primaryDark);
                editor.putString(KEY_PRIMARY, primary);
                editor.putString(KEY_BACKGROUND, background);
                editor.apply();
        }

        private void cargarColorAlmacenado() {
                SharedPreferences preferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                String primaryDark = preferences.getString(KEY_PRIMARY_DARK, "#00000000");
                String primary = preferences.getString(KEY_PRIMARY, "#871616");
                String background = preferences.getString(KEY_BACKGROUND, "#FFFFFF");
                cambiarColor(primaryDark, primary, background);
        }
}

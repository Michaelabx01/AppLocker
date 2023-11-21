package com.valdiviezomazautp.lockerapp.Fragmentos;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.valdiviezomazautp.lockerapp.Adaptador.Adaptador_password;
import com.valdiviezomazautp.lockerapp.BaseDeDatos.BDHelper;
import com.valdiviezomazautp.lockerapp.BaseDeDatos.Constants;
import com.valdiviezomazautp.lockerapp.OpcionesPassword.Agregar_Password;
import com.valdiviezomazautp.lockerapp.R;

public class F_Todas extends Fragment {

    BDHelper helper;
    RecyclerView recyclerView_Registros;
    FloatingActionButton FAB_AgregarPassword;

    Dialog dialog, dialog_ordenar;

    String ordenarNuevos = Constants.C_TIEMPO_REGISTRO + " DESC";
    String ordenarPasados = Constants.C_TIEMPO_REGISTRO + " ASC";
    String ordenarTituloAsc = Constants.C_TITULO + " ASC";
    String ordenarTituloDes = Constants.C_TITULO + " DESC";

    String EstadoOrden = ordenarTituloAsc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Bloquear captura de pantalla
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE , WindowManager.LayoutParams.FLAG_SECURE);
        View view = inflater.inflate(R.layout.fragment_f__todas, container, false);

        recyclerView_Registros = view.findViewById(R.id.recyclerView_Registros);
        FAB_AgregarPassword = view.findViewById(R.id.FAB_AgregarPassword);
        helper = new BDHelper(getActivity());
        dialog = new Dialog(getActivity());
        dialog_ordenar = new Dialog(getActivity());

        CargarRegistros(ordenarTituloAsc);

        FAB_AgregarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Agregar_Password.class);
                intent.putExtra("MODO_EDICION", false);
                startActivity(intent);
            }
        });

        return view;
    }

    private void CargarRegistros(String orderby) {
        EstadoOrden = orderby;
        Adaptador_password adaptador_password = new Adaptador_password(getActivity(), helper.ObtenerTodosRegistros(
                orderby));
        recyclerView_Registros.setAdapter(adaptador_password);
    }

    private void BuscarRegistros(String consulta){
        Adaptador_password adaptador_password = new Adaptador_password(getActivity(), helper.BuscarRegistros(consulta));
        recyclerView_Registros.setAdapter(adaptador_password);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragmento_todos, menu);

        MenuItem item = menu.findItem(R.id.menu_Buscar_registros);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*Realiza la búsqueda de un registro cuando el usuario presiona en el botón de búsqueda
                 * del teclado del dispositivo*/
                BuscarRegistros(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*Realiza la búsqueda mientras el usuario escribe*/
                BuscarRegistros(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_Ordenar_registros){
            Ordenar_Registros();
            return true;
        }
        if (id == R.id.menu_Numero_registros){
            Visualizar_Total_Registros();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        CargarRegistros(EstadoOrden);
        super.onResume();
    }

    private void Visualizar_Total_Registros(){
        TextView Total;
        Button BtnEntendidoTotal;

        dialog.setContentView(R.layout.cuadro_dialogo_total_registros);

        Total = dialog.findViewById(R.id.Total);
        BtnEntendidoTotal = dialog.findViewById(R.id.BtnEntendidoTotal);

        //Obtener el valor entero de registros
        int total = helper.ObtenerNumeroRegistros();
        //Convertir a cadena el número total de registros
        String total_string = String.valueOf(total);
        Total.setText(total_string);

        BtnEntendidoTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);

    }

    private void Ordenar_Registros(){
        Button Btn_Nuevos, Btn_Pasados, Btn_Asc, Btn_Des;

        dialog_ordenar.setContentView(R.layout.cuadro_dialogo_ordenar_registros);


        Btn_Nuevos = dialog_ordenar.findViewById(R.id.Btn_Nuevos);
        Btn_Pasados = dialog_ordenar.findViewById(R.id.Btn_Pasados);
        Btn_Asc = dialog_ordenar.findViewById(R.id.Btn_Asc);
        Btn_Des = dialog_ordenar.findViewById(R.id.Btn_Des);


        Btn_Nuevos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarRegistros(ordenarNuevos);
                dialog_ordenar.dismiss();
            }
        });

        Btn_Pasados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarRegistros(ordenarPasados);
                dialog_ordenar.dismiss();
            }
        });

        Btn_Asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarRegistros(ordenarTituloAsc);
                dialog_ordenar.dismiss();
            }
        });

        Btn_Des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarRegistros(ordenarTituloDes);
                dialog_ordenar.dismiss();
            }
        });

        dialog_ordenar.show();
        dialog_ordenar.setCancelable(true);



    }
}
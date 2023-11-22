package com.valdiviezomazautp.lockerapp.Fragmentos;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.valdiviezomazautp.lockerapp.R;
import java.security.SecureRandom;

public class F_Password extends Fragment {

    private EditText lengthEditText;
    private TextView passwordTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f__password, container, false);

        lengthEditText = view.findViewById(R.id.editTextLength);
        passwordTextView = view.findViewById(R.id.textViewPassword);
        Button generateButton = view.findViewById(R.id.buttonGenerate);
        Button copyButton = view.findViewById(R.id.buttonCopy);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePassword();
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyPasswordToClipboard();
            }
        });

        return view;
    }

    private void generatePassword() {
        String lengthStr = lengthEditText.getText().toString();
        if (TextUtils.isEmpty(lengthStr)) {
            lengthEditText.setError("Ingrese la longitud");
            return;
        }

        int length = Integer.parseInt(lengthStr);
        if (length <= 0) {
            lengthEditText.setError("La longitud debe ser mayor que 0");
            return;
        }

        String password = generateRandomPassword(length);
        passwordTextView.setText(password);
    }

    private String generateRandomPassword(int length) {
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*()_-+=<>?";

        String allCharacters = uppercase + lowercase + numbers + symbols;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allCharacters.length());
            char randomChar = allCharacters.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

    private void copyPasswordToClipboard() {
        String password = passwordTextView.getText().toString();
        if (!TextUtils.isEmpty(password)) {
            ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Contraseña generada", password);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(requireContext(), "Contraseña copiada al portapapeles", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.pennypig.ui.send;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.pennypig.MainActivity;
import com.example.pennypig.R;
import com.example.pennypig.SharedPreference.SaveSharedPreference;

public class LogoutFragment extends Fragment {

    private LogoutViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel = ViewModelProviders.of(this).get(LogoutViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_send, container, false);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(root.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        alertBuilder.setTitle("Do you want to logout?");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveSharedPreference.setUserId(getActivity(), "");
                SaveSharedPreference.setUserEmail(getActivity(), "");
                SaveSharedPreference.setUserName(getActivity(), "");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        return root;
    }
}
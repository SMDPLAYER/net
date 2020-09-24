package net.my.test.dialog;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import net.my.test.R;
import net.my.test.adapter.UserScoresAdapter;

public class PersonalBestDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_personal_best_alternative, null);
        RecyclerView recordsRV = dialogView.findViewById(R.id.rv_records);
        recordsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        recordsRV.setAdapter(new UserScoresAdapter(getContext()));
        AlertDialog dialog = builder.setView(dialogView).create();

        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }
}

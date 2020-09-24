package net.my.test.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

import net.my.test.utils.PrefManager;
import net.my.test.R;
import net.my.test.activities.MainActivity;

public class GameOverDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_game_over, null);
        TextView personalRecordView = dialogView.findViewById(R.id.personal_record);
        TextView currentPointsView = dialogView.findViewById(R.id.tv_rules);

        ArrayList<Integer> leaderboardPoints = PrefManager.getLeaderboardPoints(getActivity());
        personalRecordView.setText(getString(R.string.personal_record_dialog) + " " + String.valueOf(leaderboardPoints.get(0)));
        final int currentPoints = PrefManager.getPoints(getActivity());
        currentPointsView.setText(getString(R.string.current_points) + " " + String.valueOf(currentPoints));

        final Button mainBtn = dialogView.findViewById(R.id.mainBtn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.setLeaderboardPoints(currentPoints, getActivity());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().finish();
                getActivity().startActivity(intent);
            }
        });

        AlertDialog dialog = builder.setView(dialogView).create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, android.view.KeyEvent event) {

                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                    mainBtn.performClick();
                    return true; // pretend we've processed it
                } else
                    return false; // pass on to be processed as normal
            }
        });

        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

}

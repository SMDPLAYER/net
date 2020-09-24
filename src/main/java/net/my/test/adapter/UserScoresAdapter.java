package net.my.test.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import net.my.test.utils.PrefManager;
import net.my.test.R;

public class UserScoresAdapter extends RecyclerView.Adapter<UserScoresAdapter.UserScoreView> {

    List<Integer> userScoreList;
    Context context;

    public UserScoresAdapter(Context context) {
        this.context = context;
        userScoreList = PrefManager.getLeaderboardPoints(context);
    }

    @NonNull
    @Override
    public UserScoreView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserScoreView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserScoreView holder, int position) {
        int place = position + 1;
        holder.score.setText(place + ". " + userScoreList.get(position));
    }

    @Override
    public int getItemCount() {
        if (userScoreList != null)
            return userScoreList.size();
        else
            return 0;
    }

    class UserScoreView extends RecyclerView.ViewHolder {

        TextView score;

        UserScoreView(View itemView) {
            super(itemView);
            score = itemView.findViewById(R.id.score);
        }
    }
}

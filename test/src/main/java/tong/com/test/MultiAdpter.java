package tong.com.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MultiAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> list;
    private final int A_VIEW_TYPE = 0;
    private final int B_VIEW_TYPE = 1;

    public MultiAdpter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {

        if (Integer.parseInt(list.get(position).substring(0, 1)) % 3 == 0) {
            return A_VIEW_TYPE;
        } else {
            return B_VIEW_TYPE;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == A_VIEW_TYPE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_layout_a, parent, false);
            return new AViewHolder(view);
        }
        if (viewType == B_VIEW_TYPE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_layout_b, parent, false);
            return new BViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AViewHolder extends RecyclerView.ViewHolder {

        public AViewHolder(View itemView) {
            super(itemView);
        }
    }

    class BViewHolder extends RecyclerView.ViewHolder {

        public BViewHolder(View itemView) {
            super(itemView);
        }
    }
}

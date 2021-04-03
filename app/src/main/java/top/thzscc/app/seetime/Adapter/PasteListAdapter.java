package top.thzscc.app.seetime.Adapter;
import android.app.Application;
import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Utils.ContextUtils;
import top.thzscc.app.seetime.Utils.TransmitUtils;

public class PasteListAdapter extends RecyclerView.Adapter<PasteListAdapter.ViewHolder>{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(ContextUtils.getContext().getApplicationContext()).inflate(R.layout.fload_copy_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(TransmitUtils.data.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d("debug",""+TransmitUtils.data.size());
        return TransmitUtils.data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.float_list_item_TextView);
        }
    }
}

package top.thzscc.app.seetime.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Utils.ContextUtils;
import top.thzscc.app.seetime.Utils.JumpViewTo;
import top.thzscc.app.seetime.Utils.ServerUtils;
import top.thzscc.app.seetime.Utils.TransmitUtils;
import top.thzscc.app.seetime.ViewData.NoteData;

public class NoteListAdapter extends  RecyclerView.Adapter<NoteListAdapter.ViewHolder>{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ContextUtils.getContext()).inflate(R.layout.ry_node_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.init(TransmitUtils.noteDataList.get(position),position);
    }

    public int addNote(NoteData nd){
        TransmitUtils.noteDataList.add(nd);
        notifyDataSetChanged();
        return TransmitUtils.noteDataList.size()-1;
    }
    public void setNode(NoteData nd,int position){
        TransmitUtils.noteDataList.set(position,nd);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return TransmitUtils.noteDataList.size();
    }

    //日记项处理
    class ViewHolder extends RecyclerView.ViewHolder{
        public int index;
        public NoteData noteData;
        public TextView time;        //时间标签
        public TextView content;     //内容标签
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.time=itemView.findViewById(R.id.ryNoteItemTime);
            this.content=itemView.findViewById(R.id.ryNoteItemContent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果为删除状态删除日志，不然跳转到详情页
                    if(TransmitUtils.remove==true){
                        ServerUtils.DeleteIfExits(TransmitUtils.noteDataList.get(index).date.getTime()+"");
                        TransmitUtils.noteDataList.remove(index);
                        notifyDataSetChanged();
                        TransmitUtils.remove=false;
                        return;
                    }
                    JumpViewTo.NoteView(index);
                }
            });
        }
        public void init(NoteData nd,int index){
            time.setText(nd.getDataFormat().format(nd.date));
            content.setText(nd.content);
            this.noteData=nd;
            this.index=index;
        }
    }
}

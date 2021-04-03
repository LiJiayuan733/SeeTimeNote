package top.thzscc.app.seetime.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Struck.PasteItem;
import top.thzscc.app.seetime.Utils.ContextUtils;

import static android.content.Context.CLIPBOARD_SERVICE;

/*  黏贴板Ry适配器
* */
public class PasteBoardAdapter extends RecyclerView.Adapter<PasteBoardAdapter.ViewHolder>{
    private List<PasteItem> PasteList;
    private ClipboardManager cm;
    public PasteBoardAdapter(List<PasteItem> PasteList){
        this.PasteList=PasteList;
        this.cm= (ClipboardManager) ContextUtils.getContext().getSystemService(CLIPBOARD_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ContextUtils.getContext()).inflate(R.layout.paste_board_item,parent,false)).set(PasteList.get(viewType));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new ClickCopy(cm,this.PasteList.get(position)));
    }

    @Override
    public int getItemCount() {
        return PasteList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView date;          //显示时间
        TextView content;       //显示内容
        PasteItem pasteContent;

        public ViewHolder(View itemView) {
            super(itemView);
            this.date=itemView.findViewById(R.id.pbi_date);
            this.content=itemView.findViewById(R.id.pbi_content);
        }
        //设置时间
        public void setDate(Date date){
            this.pasteContent.date=date;
            this.date.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(this.pasteContent.date));
        }
        //设置粘贴板内容 & 设置内容显示
        public void setPasteContent(String Content){
            this.pasteContent.content=Content;
            this.content.setText(pasteContent.content);
        }
        public ViewHolder set(Date date, String Content){
            return set(new PasteItem(date,Content));
        }
        public ViewHolder set(PasteItem pasteContent){
            this.pasteContent=pasteContent;
            setDate(this.pasteContent.date);
            setPasteContent(this.pasteContent.content);
            return this;
        }
    }
    public void addPasteItem(PasteItem item){
        this.PasteList.add(item);
        notifyDataSetChanged();
    }
    class ClickCopy implements View.OnClickListener{
        private ClipboardManager cm;
        private PasteItem pi;
        public ClickCopy(ClipboardManager cm,PasteItem pi){
            this.cm=cm;
            this.pi=pi;
        }
        @Override
        public void onClick(View v) {
            cm.setPrimaryClip(ClipData.newPlainText("Label",pi.content));
            Toast.makeText(ContextUtils.getContext(),"已复制内容到剪切板",Toast.LENGTH_SHORT).show();
        }
    }
}

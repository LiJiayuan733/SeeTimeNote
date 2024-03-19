package top.thzscc.app.seetime.View;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.hjq.xtoast.XToast;

import top.thzscc.app.seetime.Adapter.MainPager2Adapter;
import top.thzscc.app.seetime.Listener.Pager2TouchListener;
import top.thzscc.app.seetime.R;
import top.thzscc.app.seetime.Utils.CommonUtils;
import top.thzscc.app.seetime.Utils.ContextUtils;
import top.thzscc.app.seetime.Utils.TransmitUtils;

public class NoteView extends AppCompatActivity {
    public static NoteView noteView;
    private boolean xToastIsShow=false;
    public ViewPager2 vp2;
    private XToast xToast;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteView.noteView=this;
        ContextUtils.setNowContext(this);
        setContentView(R.layout.activity_mainv2);
        vp2=findViewById(R.id.mainPager);
        vp2.setAdapter(new MainPager2Adapter(this,TransmitUtils.pasteItemList));
        vp2.setOnTouchListener(new Pager2TouchListener());
        vp2.setCurrentItem(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        CommonUtils.FullScreen(this);
        if (xToastIsShow == false) {
            showXToast();
            xToastIsShow = true;
        }
    }

    private void showXToast(){
        ClipData clipData=((ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE)).getPrimaryClip();
        //TODO: 实现粘贴板跟踪
        /*this.xToast=new XToast<>(getApplication())
                .setView(R.layout.float_copy)
                .setDraggable().setOnClickListener(new XToast.OnClickListener<View>() {
                    private boolean cbc=false;
                    private LinearLayout floadroot;
                    private ImageView TextChange;
                    private boolean main=true;
                    @Override
                    public void onClick(XToast<?> toast, View view) {
                        floadroot=view.findViewById(R.id.float_root);
                        if (main==true){
                            setView(R.layout.float_copy_menu,getlp(128,96),-1);
                            initEvent();
                            main=false;
                        }else if(main==false&&!cbc){
                            ViewGroup vsiew=(ViewGroup) LayoutInflater.from(getApplicationContext()).inflate(R.layout.float_copy,null,false);
                            setView(vsiew.findViewById(R.id.float_main_image));
                            main=true;
                        }
                    }
                    public ViewGroup.LayoutParams getlp(int w, int h){
                        if(w<0){
                            return new LinearLayout.LayoutParams(w,CommonUtils.dip2px(getApplicationContext(),h));
                        }else if(h<0){
                            return new LinearLayout.LayoutParams(CommonUtils.dip2px(getApplicationContext(),w),h);
                        }else if(h<0&&w<0){
                            return new LinearLayout.LayoutParams(w,h);
                        }
                        return new LinearLayout.LayoutParams(CommonUtils.dip2px(getApplicationContext(),w),CommonUtils.dip2px(getApplicationContext(),h));
                    }
                    public void clear(){
                        floadroot.removeViewAt(0);
                    }
                    public void setView(View view){
                        if(((ViewGroup)view.getParent())!=null){
                            ((ViewGroup)view.getParent()).removeView(view);
                        }
                        floadroot.addView(view);
                        clear();
                    }
                    public void setView(int id, ViewGroup.LayoutParams layoutp,int vid){
                        View view=LayoutInflater.from(getApplicationContext()).inflate(id,null,false);
                        if(vid==-1){
                            view.setLayoutParams(layoutp);
                            setView(view);
                            return;
                        }
                        View v=view.findViewById(vid);
                        v.setLayoutParams(layoutp);
                        setView(v);
                    }
                    public void initEvent(){

                        TextChange=floadroot.findViewById(R.id.float_edit);
                        TextChange.setOnClickListener(new View.OnClickListener() {
                            private RecyclerView rv;
                            @Override
                            public void onClick(View v) {
                                //TODO: 完成剪切板列表获取
                                setView(R.layout.fload_copy_list,getlp(320,520),R.id.float_copy_list);
                                cbc=true;
                                rv=(RecyclerView)xToast.getView().findViewById(R.id.float_copy_list);
                                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                rv.setAdapter(new PasteListAdapter());
                               /*setView(R.layout.float_copy_edit_change,getlp(320,-2),-1);
                                cbc=true;
                                editText=xToast.getView().findViewById(R.id.float_edit_editor);
                                ClipData data=((ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).getPrimaryClip();
                                editText.setText(data.getItemAt(data.getItemCount()-1).coerceToText(ContextUtils.getContext()));
                                editText.setFocusable(true);
                                editText.setFocusableInTouchMode(true);
                                editText.requestFocus();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                    @Override
                                    public void onFocusChange(View v, boolean hasFocus) {
                                        if(hasFocus){

                                        }
                                    }
                                });
                                xToast.getView().findViewById(R.id.float_edit_ok).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cbc=false;
                                        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("ChangeAfter",editText.getText().toString()));
                                        Toast.makeText(getApplicationContext(),"已应用修改",Toast.LENGTH_SHORT).show();
                                        setView(R.layout.float_copy_menu,getlp(128,96),-1);
                                    }
                                });
                            }
                        });
                    }
                }).show();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //xToast.cancel();
    }
}

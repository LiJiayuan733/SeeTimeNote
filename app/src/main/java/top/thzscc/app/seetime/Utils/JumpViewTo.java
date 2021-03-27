package top.thzscc.app.seetime.Utils;

import android.content.Intent;

import top.thzscc.app.seetime.View.NoteView;

public class JumpViewTo {
    public static void NoteView(int index){
        Intent i=new Intent(ContextUtils.getContext(), NoteView.class);
        TransmitUtils.useLog="Jump:setNoteData";
        TransmitUtils.index=index;
        ContextUtils.getContext().startActivity(i);
    }
    public static void NoteView(){
        Intent i=new Intent(ContextUtils.getContext(), NoteView.class);
        ContextUtils.getContext().startActivity(i);
    }
}

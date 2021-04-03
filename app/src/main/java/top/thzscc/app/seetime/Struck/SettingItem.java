package top.thzscc.app.seetime.Struck;

import android.view.View;

public class SettingItem {
    public View.OnClickListener OnClickListener;
    public String IconUrl;
    public String SettingName;
    public SettingItem(){}
    public SettingItem(String IconUrl,String SettingName){
        this.IconUrl= IconUrl;
        this.SettingName=SettingName;
    }
    public SettingItem(String IconUrl,String SettingName,View.OnClickListener OnClickListener){
        this.IconUrl= IconUrl;
        this.SettingName=SettingName;
        this.OnClickListener=OnClickListener;
    }
}

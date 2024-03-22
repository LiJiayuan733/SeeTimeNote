let menu_button;
let menu;


const RenderHtmlApp = {
    data() {
        return {

        }
    },
    methods:{
        menu_show(call_id){
            menu=$('#daily-map-note-menu');
            menu_button=$('#daily-map-note-menu-button');
            if(menu_status.value){
                if(call_id===1){
                    hide_menu_button(menu_button,menu);
                }
            }else{
                if(call_id===2){
                    show_menu_button(menu_button,menu);
                }
            }
            menu_status.value = !menu_status.value;
        }
    }
}

let app=Vue.createApp(RenderHtmlApp).mount('#daily-map-note-container');

setTimeout(function(){
    menu=$('#daily-map-note-menu');
    menu_button=$('#daily-map-note-menu-button');
    hide_menu_button(menu_button,menu);
    $("#daily-map-note-menu-button").addClass('layui-anim-scalesmall');
},350);

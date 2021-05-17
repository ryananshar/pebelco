$(function selected(){
        var i;
        for (i = 0; i < listTags.length; i++){
            if(listTags[i]==1){
                $('#inlineCheckbox1').attr('checked',true);
            }
            else if(listTags[i]==2){
                $('#inlineCheckbox2').attr('checked',true);
            }
            else if(listTags[i]==3){
                $('#inlineCheckbox3').attr('checked',true);
            }
            else if(listTags[i]==4){
                $('#inlineCheckbox4').attr('checked',true);
            }
            else if(listTags[i]==5){
                $('#inlineCheckbox5').attr('checked',true);
            }
        }
    }
)
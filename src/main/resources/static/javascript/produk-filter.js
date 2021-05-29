console.log("masuk bener");
var index = 5;
var tipe="";
var listTagBefore = [];
var listTag = [];
var tagMeja = document.getElementById("tag-1");
var tagKursi = document.getElementById("tag-2");
var tagLemari = document.getElementById("tag-3");
var tagLaci = document.getElementById("tag-4");
var tagRak = document.getElementById("tag-5");
var keywordVal = document.getElementById("keywordInput");
var baseUrl = "/filter/"
var url="";


function selectOnChange(selectObject){
    tipe = selectObject.value;
    console.log(tipe)
}

function filterSubmit(){
    if (tipe !== ""){
        url = "?tipe=" + tipe;
    }
    console.log("url awal = " + url);
    listTagBefore.push(tagMeja);
    listTagBefore.push(tagKursi);
    listTagBefore.push(tagLemari);
    listTagBefore.push(tagLaci);
    listTagBefore.push(tagRak);
    // console.log(listTagBefore);
    // console.log(listTagBefore[0].checked);
    var i;
    for (i=1; i <= index; i++){
        if (listTagBefore[i-1].checked === true){
            listTag.push(listTagBefore[i-1]);
        }
    }
    if (listTag.length > 0){

        url = url + "&tag=";
        var i;
        for (i=0; i < listTag.length; i++){
            url = url + listTag[i].value + "+";
        }
        url = url.slice(0, url.length-1);
    }
    // if (keywordVal !== null && keywordVal.value === ""){
    //     url = url + "&keyword=" + keywordVal.value;
    // }
    if (url === ""){
        document.getElementById("submitButtonFilter").setAttribute("href", "/produk");
        document.getElementById("submitButtonFilter").submit();
    } else if(tipe === ""){
        if (keywordVal !== null){
            document.getElementById("submitButtonFilter").setAttribute("href", baseUrl + "?tipe=" + url + "&keyword=" + keywordVal.value);
        } else{
            document.getElementById("submitButtonFilter").setAttribute("href", baseUrl + "?tipe=" + url);
        }
        document.getElementById("submitButtonFilter").submit();
    } else if(listTag.length === 0){
        if (keywordVal !== null){
            document.getElementById("submitButtonFilter").setAttribute("href", baseUrl + url + "&tag=" + "&keyword=" + keywordVal.value);
        }else{
            document.getElementById("submitButtonFilter").setAttribute("href", baseUrl + url + "&tag=");
        }
        document.getElementById("submitButtonFilter").submit();
    }

    if(keywordVal !== null){
        document.getElementById("submitButtonFilter").setAttribute("href", baseUrl + url + "&keyword=" + keywordVal.value);
    }else{
        document.getElementById("submitButtonFilter").setAttribute("href", baseUrl + url);
    }
    document.getElementById("submitButtonFilter").submit();
}
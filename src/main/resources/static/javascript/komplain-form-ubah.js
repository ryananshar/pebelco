console.log("masuk");
var divNamaBarang=document.getElementById("col-nama-barang");
var divJumlah=document.getElementById("col-jumlah");
var divDeskripsi=document.getElementById("col-deskripsi");
var divBtn=document.getElementById("col-btn");

var reqTemplate=document.getElementById("reqTemplate");

function hapusBaris(selectObject) {
    var index = selectObject.id;
    document.getElementById("namaBarang-" + index.substring(11)).value = "Deleted";
    document.getElementById("jumlah-" + index.substring(11)).style.display = "none";
    document.getElementById("description-" + index.substring(11)).style.display = "none";
    document.getElementById("namaBarang-" + index.substring(11)).style.display = "none";
    document.getElementById("btn-barang-" + index.substring(11)).style.display = "none";

    listMaxJumlah.splice(listId.indexOf(parseInt(index.substring(11))),1);
    listId.splice(listId.indexOf(parseInt(index.substring(11))),1);

}

function appendTransaksi(){
    var i;
    var temp = "";
    var namaBarang;
    var jumlah;
    var description;
    for (i = 0; i < listId.length; i++){
        namaBarang = "namaBarang-" + listId[i];
        jumlah = "jumlah-" + listId[i];
        description = "description-" + listId[i];
        listBarang.push(document.getElementById(namaBarang).value);
        listJumlah.push(document.getElementById(jumlah).value);
        if (document.getElementById(description).value.length > 0){
            listDesc.push(document.getElementById(description).value);
        }else{
            listDesc.push("nullDescription404");
        }

    }
    if(listDesc.includes("nullDescription404")) {
        listBarang = [];
        listDesc = [];
        listJumlah = [];
        alert("Deskripsi tidak boleh kosong");
        return;
    }
    for (i=0; i < listId.length; i++) {
        if (listJumlah[i] <= 0) {
            listBarang = [];
            listDesc = [];
            listJumlah = [];
            alert("Jumlah tidak boleh kurang dari 1");
            return;
        }
    }
    temp = temp+ listBarang.length + "---";
    for (i=0; i<listBarang.length;i++){
        temp = temp + listBarang[i] + ",,,";
    }
    temp = temp + "---";
    for (i=0; i<listDesc.length;i++){

        temp = temp + listDesc[i] + ",,," ;
    }
    temp = temp + "---";
    for (i=0; i<listJumlah.length;i++){
        temp = temp + String(listJumlah[i]) + ",,," ;
    }

    temp = temp + "---";
    for (i=0; i<listId.length;i++){
        temp = temp + String(listId[i]) + ",,," ;
    }

    temp = temp + "---";
    checkerForm(temp);
    if (reqTemplate.value[0] == 0){
        alert("Silahkan pilih salah satu pesanan untuk di komplain");
    }else{
        console.log(listBarang);
        console.log(listJumlah);
        console.log(listDesc);
        console.log(listId);
        console.log(listMaxJumlah);
        console.log(reqTemplate.value);
        document.getElementById("submitForm").submit();
    }
}

function checkerForm(temp){
    var i;
    var j;
    var adder = "";
    for (i=0; i < listId.length; i++){
        if (listJumlah[i] <= 0){
            adder = "jumlahInvalid";
            // reqTemplate.setAttribute("value", temp);
            break;
        } else if (listDesc[i].includes(",,,") || listDesc[i].includes("---")){
            adder = "descInvalid";
            break;
        }
        else if (listJumlah[i] > listMaxJumlah[i]) {
            adder = "jumlahInvalid";
            break;
        }
    }

    if(adder.length > 0){
        temp = temp + adder;
        reqTemplate.setAttribute("value", temp);
    } else{
        adder =  "aman";
        temp = temp + adder;
        reqTemplate.setAttribute("value", temp);
    }
}
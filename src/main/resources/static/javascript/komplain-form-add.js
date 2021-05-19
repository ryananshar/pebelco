var selectPesanan=document.getElementById("selectPesanan");
var divNamaBarang=document.getElementById("col-nama-barang");
var divJumlah=document.getElementById("col-jumlah");
var divDeskripsi=document.getElementById("col-deskripsi");
var divBtn=document.getElementById("col-btn");
var jumlahBaris=1;
var counterBaris=document.getElementById("counter-baris");
var counterRow=document.getElementById("counter");
var counter = 0;
var listCounter = [];
var reqTemplate=document.getElementById("reqTemplate");
var checker = [];
var listId = [];

function selectOnChange(selectObject) {
    var index = selectObject.value;
    var i;
    for (i = 0; i < pesananList.length; i++) {
        if (pesananList[i]["idPesananPenjualan"] == index){
            var pesananIndex = i;
        }
    }
    if (listCounter.length >= 1){
        deleteBaris(listCounter.length);
    }
    changeNamaAndAlamat(pesananIndex);
    addBaris(pesananIndex);

}

function changeNamaAndAlamat(pesananIndex){
    document.getElementById("namtok").setAttribute('value',pesananList[pesananIndex]["namaToko"]);
    document.getElementById("altok").setAttribute('value',pesananList[pesananIndex]["alamatToko"]);
}

function addCounterBaris(idTransaksi){
    listId.push(idTransaksi);
    jumlahBaris+=1;
    listCounter.push(jumlahBaris);
    counterBaris.setAttribute("value",jumlahBaris);
    counter +=1;
    counterRow.setAttribute("value",counter);
}

function hapusBaris(selectObject) {
    var index = selectObject.id;
    counterBaris.setAttribute("value", jumlahBaris);
    document.getElementById("namaBarang-" + index.substring(11)).value = "Deleted";
    document.getElementById("jumlah-" + index.substring(11)).remove();
    document.getElementById("description-" + index.substring(11)).remove();
    document.getElementById("namaBarang-" + index.substring(11)).remove();
    document.getElementById("btn-barang-" + index.substring(11)).remove();

    jumlahBaris-=1;
    counter-=1;

    counterRow.setAttribute("value",counter);
    listId.splice(listCounter.indexOf(parseInt(event.target.id.substring(11))),1);
    listCounter.splice(listCounter.indexOf(parseInt(event.target.id.substring(11))),1);

}

function deleteBaris(panjang){
    var i;
    for (i = 0; i < panjang; i++) {
        // console.log(listCounter[i]);
        document.getElementById("jumlah-" + listCounter[i]).remove();
        document.getElementById("description-" + listCounter[i]).remove();
        document.getElementById("namaBarang-" + listCounter[i]).remove();
        document.getElementById("btn-barang-" + listCounter[i]).remove();
    }
    listCounter = [];
    listId = [];
    counter = 0;
    counterRow.setAttribute("value",counter);
}

function addBaris(pesananIndex){
    var i;
    for (i = 0; i < transaksiList[pesananIndex].length; i++) {
        addCounterBaris(transaksiList[pesananIndex][i]["idTransaksiPesanan"]);
        divNamaBarang.innerHTML+='<input style="margin-bottom: 24px" class="form-control" type="text" id="namaBarang-'+jumlahBaris+'" readonly="readonly" required="required" value="'+transaksiList[pesananIndex][i]["namaBarang"]+'"/>';
        divJumlah.innerHTML+='<input style="margin-bottom: 24px" class="form-control" type="number" id="jumlah-'+jumlahBaris+'" min="1" required="required" value="'+transaksiList[pesananIndex][i]["jumlah"]+'"/>';
        divDeskripsi.innerHTML+='<input style="margin-bottom: 24px" class="form-control" type="text" required="required" id="description-'+jumlahBaris+'"/>';
        divBtn.innerHTML+='<div style="margin-bottom: 11px" ></div><button id="btn-barang-'+jumlahBaris+'" type="button" onclick="hapusBaris(this)" class="btn but-danger"> <i class="fa fa-trash"></i></button>';
    }
    checker = transaksiList[pesananIndex];
}

function appendTransaksi(){
    var i;
    var temp = "";
    var namaBarang;
    var jumlah;
    var description;
    for (i = 0; i < listCounter.length; i++){
        namaBarang = "namaBarang-" + listCounter[i];
        jumlah = "jumlah-" + listCounter[i];
        description = "description-" + listCounter[i];
        listBarang.push(document.getElementById(namaBarang).value);
        listJumlah.push(document.getElementById(jumlah).value);
        if (document.getElementById(description).value.length > 0){
            listDesc.push(document.getElementById(description).value);
        }else{
            listDesc.push("nullDescription404");
        }

    }
    if(listDesc.includes("nullDescription404")) {
        console.log(listDesc);
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

    checkerForm(temp);
    if (reqTemplate.value[0] == 0){
        alert("Silahkan pilih salah satu pesanan untuk di komplain");
    }else{
        console.log(listDesc);
        document.getElementById("submitForm").submit();
    }
}

function checkerForm(temp){
    var i;
    var j;
    var adder = "";
    for (i=0; i < listId.length; i++){
        if (listJumlah[i] <= 0){
            listBarang = [];
            listDesc = [];
            listJumlah = [];
            alert("Jumlah tidak boleh kurang dari 1");
            break;
        } else if (listDesc[i].includes(",,,") || listDesc[i].includes("---")){
            console.log("descripsi mengandung koma tiga atau strip tiga");
            adder = "descInvalid";
            break;
        } else{
            for (j=0; j < checker.length; j++){
                if (listId[i] == checker[j]["idTransaksiPesanan"]) {
                    if (listJumlah[i] > checker[j]["jumlah"]) {
                        console.log(listJumlah[i] + " lebih dari " + checker[j]["jumlah"])
                        console.log("salah");
                        adder = "jumlahInvalid";
                        break;
                    }
                }
            }
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
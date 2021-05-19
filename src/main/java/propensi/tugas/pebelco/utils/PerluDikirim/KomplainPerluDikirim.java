package propensi.tugas.pebelco.utils.PerluDikirim;

import propensi.tugas.pebelco.model.KomplainModel;

public class KomplainPerluDikirim extends PerluDikirim {
    public KomplainPerluDikirim(KomplainModel komplain) {
        super(
                komplain.getIdKomplain(),
                komplain.getKodeKomplain(),
                komplain.getNamaToko(),
                komplain.getAlamatToko(),
                komplain.getTanggalPersetujuan(),
                komplain.getStatusKomplain(),
                "komplain"
        );
    }
}

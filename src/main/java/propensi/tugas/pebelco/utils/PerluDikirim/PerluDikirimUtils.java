package propensi.tugas.pebelco.utils.PerluDikirim;

import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.PesananPenjualanModel;

public interface PerluDikirimUtils {
    public PerluDikirim getPerluDikirimFromKomplain(KomplainModel komplain);
    public PerluDikirim getPerluDikirimFromPesanan(PesananPenjualanModel pesanan);
}

package propensi.tugas.pebelco.utils.PerluDikirim;

import org.springframework.stereotype.Component;
import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.PesananPenjualanModel;

@Component
public class PerluDikirimUtilsImpl implements PerluDikirimUtils {
    public PerluDikirim getPerluDikirimFromKomplain(KomplainModel komplain) {
        return new KomplainPerluDikirim(komplain);
    }

    public PerluDikirim getPerluDikirimFromPesanan(PesananPenjualanModel pesanan) {
        return new PesananPerluDikirim(pesanan);
    }
}

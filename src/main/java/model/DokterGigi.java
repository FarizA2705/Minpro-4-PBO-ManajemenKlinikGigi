package model;

public class DokterGigi extends Orang {
    private String spesialisasi;

    public DokterGigi(String idDokter, String namaDokter, String spesialisasi) {
        super(idDokter, namaDokter);
        this.spesialisasi = spesialisasi;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    @Override
    public void tampilkanProfil() {
        System.out.println("[Dokter] ID: " + id + " | Nama: " + nama + " | Spesialisasi: " + spesialisasi);
    }
}
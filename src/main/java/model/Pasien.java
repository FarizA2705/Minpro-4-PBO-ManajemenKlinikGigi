package model;

public class Pasien extends Orang implements StatusLayanan {
    private DokterGigi dokter;
    private RekamMedis rekamMedis;

    public Pasien(String idPasien, String namaPasien, DokterGigi dokter, RekamMedis rekamMedis) {
        super(idPasien, namaPasien);
        this.dokter = dokter;
        this.rekamMedis = rekamMedis;
    }

    public DokterGigi getDokter() {
        return dokter;
    }

    public void setDokter(DokterGigi dokter) {
        this.dokter = dokter;
    }

    public RekamMedis getRekamMedis() {
        return rekamMedis;
    }

    public void setRekamMedis(RekamMedis rekamMedis) {
        this.rekamMedis = rekamMedis;
    }

    @Override
    public void tampilkanProfil() {
        System.out.println("[Pasien] ID: " + id + " | Nama: " + nama + " | Dokter PJ: " + dokter.getNama());
    }

    @Override
    public void cetakKartuBerobat() {
        System.out.println("--> KARTU BEROBAT AKTIF: Pasien " + nama + " (ID: " + id + ")");
    }
}
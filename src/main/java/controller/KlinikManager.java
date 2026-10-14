package controller;

import model.DokterGigi;
import model.Pasien;
import model.RekamMedis;
import model.dao.PasienDAO;
import model.dao.PasienDAOImpl;

import java.util.List;

public class KlinikManager {
    private PasienDAO pasienDAO;

    public KlinikManager() {
        this.pasienDAO = new PasienDAOImpl();
    }

    public List<DokterGigi> getDaftarDokter() {
        return pasienDAO.getAllDokter();
    }

    public void tambahPasien(Pasien pasien) {
        pasienDAO.tambahPasien(pasien);
        pasien.cetakKartuBerobat();
    }

    public void tambahPasien(String id, String nama, DokterGigi dokter, String idRM, String diagnosa, String tindakan) {
        RekamMedis rm = new RekamMedis(idRM, diagnosa, tindakan);
        Pasien pasienBaru = new Pasien(id, nama, dokter, rm);
        tambahPasien(pasienBaru);
    }

    public List<Pasien> getDaftarPasien() {
        return pasienDAO.getAllPasien();
    }

    public void ubahPasien(Pasien pasien) {
        pasienDAO.ubahPasien(pasien);
    }

    public void hapusPasien(String idPasien) {
        pasienDAO.hapusPasien(idPasien);
    }

    public Pasien cariPasienById(String idPasien) {
        return pasienDAO.getPasienById(idPasien);
    }
}
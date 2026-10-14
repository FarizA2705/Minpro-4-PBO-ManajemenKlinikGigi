package model.dao;

import model.DokterGigi;
import model.Pasien;
import java.util.List;

public interface PasienDAO {
    void tambahPasien(Pasien pasien);
    List<Pasien> getAllPasien();
    List<DokterGigi> getAllDokter(); 
    void ubahPasien(Pasien pasien);
    void hapusPasien(String idPasien);
    Pasien getPasienById(String idPasien);
}
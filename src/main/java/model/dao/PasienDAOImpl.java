package model.dao;

import config.klinikgigidb;
import model.DokterGigi;
import model.Pasien;
import model.RekamMedis;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasienDAOImpl implements PasienDAO {

    @Override
    public void tambahPasien(Pasien pasien) {
        String sqlRM = "INSERT INTO rekam_medis (id_rekam_medis, diagnosa, catatan_tindakan) VALUES (?, ?, ?)";
        String sqlPasien = "INSERT INTO pasien_gigi (id_pasien, nama, id_dokter, id_rekam_medis) VALUES (?, ?, ?, ?)";

        try (Connection conn = klinikgigidb.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtRM = conn.prepareStatement(sqlRM)) {
                stmtRM.setString(1, pasien.getRekamMedis().getIdRekamMedis());
                stmtRM.setString(2, pasien.getRekamMedis().getDiagnosa());
                stmtRM.setString(3, pasien.getRekamMedis().getCatatanTindakan());
                stmtRM.executeUpdate();
            }

            try (PreparedStatement stmtPasien = conn.prepareStatement(sqlPasien)) {
                stmtPasien.setString(1, pasien.getId());
                stmtPasien.setString(2, pasien.getNama());
                stmtPasien.setString(3, pasien.getDokter().getId());
                stmtPasien.setString(4, pasien.getRekamMedis().getIdRekamMedis());
                stmtPasien.executeUpdate();
            }

            conn.commit();
            System.out.println("--> [Database] Data pasien dan rekam medis berhasil disimpan!");
        } catch (SQLException e) {
            System.err.println("Gagal menambah pasien: " + e.getMessage());
        }
    }

    @Override
    public List<Pasien> getAllPasien() {
        List<Pasien> listPasien = new ArrayList<>();
        String sql = "SELECT p.id_pasien, p.nama AS nama_pasien, " +
                     "d.id_dokter, d.nama AS nama_dokter, d.spesialisasi, " +
                     "rm.id_rekam_medis, rm.diagnosa, rm.catatan_tindakan " +
                     "FROM pasien_gigi p " +
                     "JOIN dokter_gigi d ON p.id_dokter = d.id_dokter " +
                     "JOIN rekam_medis rm ON p.id_rekam_medis = rm.id_rekam_medis";

        try (Connection conn = klinikgigidb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DokterGigi dokter = new DokterGigi(
                    rs.getString("id_dokter"),
                    rs.getString("nama_dokter"),
                    rs.getString("spesialisasi")
                );

                RekamMedis rm = new RekamMedis(
                    rs.getString("id_rekam_medis"),
                    rs.getString("diagnosa"),
                    rs.getString("catatan_tindakan")
                );

                Pasien pasien = new Pasien(
                    rs.getString("id_pasien"),
                    rs.getString("nama_pasien"),
                    dokter,
                    rm
                );

                listPasien.add(pasien);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data pasien: " + e.getMessage());
        }
        return listPasien;
    }

    @Override
    public List<DokterGigi> getAllDokter() {
        List<DokterGigi> listDokter = new ArrayList<>();
        String sql = "SELECT * FROM dokter_gigi";

        try (Connection conn = klinikgigidb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DokterGigi dokter = new DokterGigi(
                    rs.getString("id_dokter"),
                    rs.getString("nama"),
                    rs.getString("spesialisasi")
                );
                listDokter.add(dokter);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data dokter dari database: " + e.getMessage());
        }
        return listDokter;
    }

    @Override
    public void ubahPasien(Pasien pasien) {
        String sqlPasien = "UPDATE pasien_gigi SET nama = ? WHERE id_pasien = ?";
        String sqlRM = "UPDATE rekam_medis SET diagnosa = ?, catatan_tindakan = ? WHERE id_rekam_medis = ?";

        try (Connection conn = klinikgigidb.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPasien = conn.prepareStatement(sqlPasien)) {
                stmtPasien.setString(1, pasien.getNama());
                stmtPasien.setString(2, pasien.getId());
                stmtPasien.executeUpdate();
            }

            try (PreparedStatement stmtRM = conn.prepareStatement(sqlRM)) {
                stmtRM.setString(1, pasien.getRekamMedis().getDiagnosa());
                stmtRM.setString(2, pasien.getRekamMedis().getCatatanTindakan());
                stmtRM.setString(3, pasien.getRekamMedis().getIdRekamMedis());
                stmtRM.executeUpdate();
            }

            conn.commit();
            System.out.println("--> [Database] Data pasien berhasil diperbarui!");
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui data pasien: " + e.getMessage());
        }
    }

    @Override
    public void hapusPasien(String idPasien) {
        String sqlRMId = "SELECT id_rekam_medis FROM pasien_gigi WHERE id_pasien = ?";
        String sqlDeletePasien = "DELETE FROM pasien_gigi WHERE id_pasien = ?";
        String sqlDeleteRM = "DELETE FROM rekam_medis WHERE id_rekam_medis = ?";

        try (Connection conn = klinikgigidb.getConnection()) {
            conn.setAutoCommit(false);
            String idRM = null;

            try (PreparedStatement stmtGetRM = conn.prepareStatement(sqlRMId)) {
                stmtGetRM.setString(1, idPasien);
                ResultSet rs = stmtGetRM.executeQuery();
                if (rs.next()) {
                    idRM = rs.getString("id_rekam_medis");
                }
            }

            try (PreparedStatement stmtPasien = conn.prepareStatement(sqlDeletePasien)) {
                stmtPasien.setString(1, idPasien);
                stmtPasien.executeUpdate();
            }

            if (idRM != null) {
                try (PreparedStatement stmtRM = conn.prepareStatement(sqlDeleteRM)) {
                    stmtRM.setString(1, idRM);
                    stmtRM.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("--> [Database] Data pasien berhasil dihapus!");
        } catch (SQLException e) {
            System.err.println("Gagal menghapus data pasien: " + e.getMessage());
        }
    }

    @Override
    public Pasien getPasienById(String idPasien) {
        for (Pasien p : getAllPasien()) {
            if (p.getId().equalsIgnoreCase(idPasien)) {
                return p;
            }
        }
        return null;
    }
}
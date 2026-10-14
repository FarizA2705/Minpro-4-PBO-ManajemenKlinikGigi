package view;

import controller.KlinikManager;
import model.DokterGigi;
import model.Pasien;
import java.util.List;
import java.util.Scanner;

public class KlinikGigiManajemen4 {
   private KlinikManager manager = new KlinikManager();
    private Scanner scanner = new Scanner(System.in);

    public void jalankanProgram() {
        int pilihan = 0;

        do {
            System.out.println("\n=================================================");
            System.out.println("       SISTEM MANAJEMEN KLINIK GIGI (CLI)        ");
            System.out.println("=================================================");
            System.out.println("1. Tambah Pasien Baru");
            System.out.println("2. Tampilkan Semua Pasien");
            System.out.println("3. Ubah Data Pasien");
            System.out.println("4. Hapus Data Pasien");
            System.out.println("5. Keluar");
            System.out.println("-------------------------------------------------");
            System.out.print("Pilih menu (1-5): ");

            if (scanner.hasNextInt()) {
                pilihan = scanner.nextInt();
                scanner.nextLine(); 

                switch (pilihan) {
                    case 1:
                        System.out.println("\n=== Tambah Pasien Baru ===");
                        System.out.print("Masukkan ID Pasien     : ");
                        String id = scanner.nextLine().trim();
                        System.out.print("Masukkan Nama Pasien   : ");
                        String nama = scanner.nextLine().trim();
                        System.out.print("Masukkan ID Rekam Medis: ");
                        String idRM = scanner.nextLine().trim();
                        System.out.print("Masukkan Diagnosa      : ");
                        String diagnosa = scanner.nextLine().trim();
                        System.out.print("Masukkan Catatan Tindakan: ");
                        String tindakan = scanner.nextLine().trim();

                        if (id.isEmpty() || nama.isEmpty() || idRM.isEmpty()) {
                            System.out.println("--> Error: Field utama tidak boleh kosong!");
                            break;
                        }

                        
                        System.out.println("\n== Pilih Dokter Gigi ===");
                        List<DokterGigi> daftarDokter = manager.getDaftarDokter();
                        if (daftarDokter.isEmpty()) {
                            System.out.println("--> Error: Data dokter di database kosong!");
                            break;
                        }

                        for (DokterGigi d : daftarDokter) {
                            System.out.println("[" + d.getId() + "] " + d.getNama() + " - " + d.getSpesialisasi());
                        }

                        System.out.print("Masukkan ID Dokter pilihan: ");
                        String idDokterPilihan = scanner.nextLine().trim();

                        DokterGigi dokterPilihan = null;
                        for (DokterGigi d : daftarDokter) {
                            if (d.getId().equalsIgnoreCase(idDokterPilihan)) {
                                dokterPilihan = d;
                                break;
                            }
                        }

                        if (dokterPilihan == null) {
                            System.out.println("--> Error: ID Dokter tidak valid!");
                            break;
                        }

                        manager.tambahPasien(id, nama, dokterPilihan, idRM, diagnosa, tindakan);
                        break;

                    case 2:
                        System.out.println("\n=== Daftar Pasien Terdaftar (Dari Database) ===");
                        List<Pasien> daftarPasien = manager.getDaftarPasien();
                        if (daftarPasien.isEmpty()) {
                            System.out.println("Belum ada data pasien di dalam database.");
                        } else {
                            for (Pasien p : daftarPasien) {
                                System.out.println("=================================================");
                                p.tampilkanProfil();
                                System.out.println("Rekam Medis [" + p.getRekamMedis().getIdRekamMedis() + "]:");
                                System.out.println("  - Diagnosa : " + p.getRekamMedis().getDiagnosa());
                                System.out.println("  - Tindakan : " + p.getRekamMedis().getCatatanTindakan());
                                p.cetakKartuBerobat();
                            }
                            System.out.println("==================================================");
                        }
                        break;

                    case 3:
                        System.out.println("\n=== Ubah Data Pasien ===");
                        System.out.print("Masukkan ID Pasien yang ingin diubah: ");
                        String idUbah = scanner.nextLine().trim();
                        Pasien targetUbah = manager.cariPasienById(idUbah);

                        if (targetUbah != null) {
                            System.out.print("Masukkan Nama Baru          : ");
                            String namaBaru = scanner.nextLine().trim();
                            System.out.print("Masukkan Diagnosa Baru      : ");
                            String diagBaru = scanner.nextLine().trim();
                            System.out.print("Masukkan Catatan Tindakan Baru: ");
                            String tindBaru = scanner.nextLine().trim();

                            targetUbah.setNama(namaBaru);
                            targetUbah.getRekamMedis().setDiagnosa(diagBaru);
                            targetUbah.getRekamMedis().setCatatanTindakan(tindBaru);

                            manager.ubahPasien(targetUbah);
                        } else {
                            System.out.println("--> Pasien dengan ID tersebut tidak ditemukan.");
                        }
                        break;

                    case 4:
                        System.out.println("\n=== Hapus Data Pasien ===");
                        System.out.print("Masukkan ID Pasien yang akan dihapus: ");
                        String idHapus = scanner.nextLine().trim();
                        manager.hapusPasien(idHapus);
                        break;

                    case 5:
                        System.out.println("\nTerima kasih telah menggunakan Sistem Manajemen Klinik Gigi.");
                        break;

                    default:
                        System.out.println("Pilihan tidak valid. Silakan pilih angka 1 sampai 5.");
                }
            } else {
                System.out.println("Input harus berupa angka!");
                scanner.next();
            }
        } while (pilihan != 5);

        scanner.close();
    }
}
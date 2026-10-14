package model;

public class RekamMedis {
    private String idRekamMedis;
    private String diagnosa;
    private String catatanTindakan;

    public RekamMedis(String idRekamMedis, String diagnosa, String catatanTindakan) {
        this.idRekamMedis = idRekamMedis;
        this.diagnosa = diagnosa;
        this.catatanTindakan = catatanTindakan;
    }

    public String getIdRekamMedis() {
        return idRekamMedis;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getCatatanTindakan() {
        return catatanTindakan;
    }

    public void setCatatanTindakan(String catatanTindakan) {
        this.catatanTindakan = catatanTindakan;
    }
}
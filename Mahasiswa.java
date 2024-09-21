

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS
 */
public class Mahasiswa {

    private long NIM;
    private String NamaMahasiswa;
    private String KelaminMahasiswa;
    private int UmurMahasiswa;

    public Mahasiswa(long NIM, String NamaMahasiswa, String KelaminMahasiswa, int UmurMahasiswa) {
        this.NIM = NIM;
        this.NamaMahasiswa = NamaMahasiswa;
        this.KelaminMahasiswa = KelaminMahasiswa;
        this.UmurMahasiswa = UmurMahasiswa;
    }

    // Getter dan Setter
    public long getNIM() {
        return NIM;
    }

    public void setNIM(long NIM) {
        this.NIM = NIM;
    }

    public String getNamaMahasiswa() {
        return NamaMahasiswa;
    }

    public void setNamaMahasiswa(String NamaMahasiswa) {
        this.NamaMahasiswa = NamaMahasiswa;
    }

    public String getKelaminMahasiswa() {
        return KelaminMahasiswa;
    }

    public void setKelaminMahasiswa(String KelaminMahasiswa) {
        this.KelaminMahasiswa = KelaminMahasiswa;
    }

    public int getUmurMahasiswa() {
        return UmurMahasiswa;
    }

    public void setUmurMahasiswa(int UmurMahasiswa) {
        this.UmurMahasiswa = UmurMahasiswa;
    }
}

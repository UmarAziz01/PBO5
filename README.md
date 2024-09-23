# PBO5
Penugasan matakuliah pemrograman berorientasi obyek pertemuan kelima yakni membuat CRUD yang mengimplementasikan JFrame Form dari Java dengan contoh [CRUD](https://github.com/UmarAziz01/PBO4) kemarin.


# Langkah Pertama  
**Membuat Custom Exception** berupa kelas ini digunakan untuk membuat sebuah exception khusus yang bisa dilempar (throw) ketika data yang dicari tidak ditemukan
```java
public class DataTidakDitemukanException extends Exception {

    public DataTidakDitemukanException(String message) {
        super(message);
    }
}
```
# Langkah ke 2
**Membuat table untuk mahasiswa**
```sql
CREATE TABLE Mahasiswa (
NIM INT PRIMARY KEY,
NamaMahasiswa VARCHAR(30) NOT NULL,
KelaminMahasiswa CHAR(1) NOT NULL,
UmurMahasiswa INT NOT NULL,
CONSTRAINT CK_Umur CHECK (UmurMahasiswa >= 16),
CONSTRAINT CK_Kelamin CHECK(KelaminMahasiswa IN ('L', 'P'))
);

# Langkah Ke 3
**Membuat Class Mahasiswa** Kelas ini diguanakan sebagai representasi dari entitas mahasiswa yang memiliki atribut-atribut umum, seperti NIM, nama, jenis kelamin, dan umur. Kelas ini juga dilengkapi dengan getter dan setter untuk memanipulasi database
```java
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
```

# Langkah Ke 4
**Membuat class CRUDKu** yang digunakan untuk mengelola operasi database menggunakan JDBC (Java Database Connectivity). Kelas ini berinteraksi dengan database PostgreSQL untuk menjalankan operasi CRUD (Create, Read, Update, Delete) pada tabel Mahasiswa

````java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDKu {

    Connection cn;
    PreparedStatement ps;
    Statement st;

    String driver = "org.postgresql.Driver";
    String koneksi = "jdbc:postgresql://localhost:5432/PBO_P4_T4_A";
    String user = "postgres";
    String password = " ";

    public CRUDKu() {

        try {
            Class.forName(driver);
            cn = DriverManager.getConnection(koneksi, user, password);
            System.out.println("Konksi Sukses");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean tambah(long NIM, String NamaMahasiswa, String KelaminMahasiswa, int UmurMahasiswa) {
        String sql = "INSERT INTO Mahasiswa (NIM, NamaMahasiswa, KelaminMahasiswa, UmurMahasiswa) VALUES (?, ?, ?, ?)";
        try {
            ps = cn.prepareStatement(sql);
            ps.setLong(1, NIM);
            ps.setString(2, NamaMahasiswa);
            ps.setString(3, KelaminMahasiswa);
            ps.setInt(4, UmurMahasiswa);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Mahasiswa> tampil() {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM Mahasiswa";
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Mahasiswa m = new Mahasiswa(
                        rs.getLong("NIM"),
                        rs.getString("NamaMahasiswa"),
                        rs.getString("KelaminMahasiswa"),
                        rs.getInt("UmurMahasiswa")
                );
                list.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean hapus(long NIM) {
        String sql = "DELETE FROM Mahasiswa WHERE NIM = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setLong(1, NIM);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(long NIM, String NamaMahasiswa, String KelaminMahasiswa, int UmurMahasiswa) {
        String sql = "UPDATE Mahasiswa SET NamaMahasiswa = ?, KelaminMahasiswa = ?, UmurMahasiswa = ? WHERE NIM = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, NamaMahasiswa);
            ps.setString(2, KelaminMahasiswa);
            ps.setInt(3, UmurMahasiswa);
            ps.setLong(4, NIM);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void hapusSemua() {
        String sql = "DELETE FROM Mahasiswa";
        try {
            st = cn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Mahasiswa cariDataByNIM(long NIM) throws DataTidakDitemukanException {
        String sql = "SELECT * FROM Mahasiswa WHERE NIM = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setLong(1, NIM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Mahasiswa(
                        rs.getLong("NIM"),
                        rs.getString("NamaMahasiswa"),
                        rs.getString("KelaminMahasiswa"),
                        rs.getInt("UmurMahasiswa")
                );
            } else {
                throw new DataTidakDitemukanException("Data dengan NIM " + NIM + " tidak ditemukan.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataTidakDitemukanException("Terjadi kesalahan saat mencari data.");
        }
    }

    public void tutupKoneksi() {
        try {
            if (ps != null) {
                ps.close();
            }
            if (st != null) {
                st.close();
            }
            if (cn != null) {
                cn.close();
            }
            System.out.println("Koneksi Sudah Ditutup");
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
````
# Langkah Ke 5
**Membuat JFrame Form - CRUDForm** yang digunakan untuk mendesain tampilan untuk program sesuai dengan menu ataupun method yang telah dideskripsikan sebelumnya

# Langkah Ke 6
**Memberikan perintah pada setiap button** supaya setiap button berfungsi dengan baik

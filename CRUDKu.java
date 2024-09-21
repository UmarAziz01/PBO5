

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS
 */
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
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package ecosmartwaste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:data/ecosmartwaste.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS bin_reports (id INTEGER PRIMARY KEY AUTOINCREMENT, location TEXT, status TEXT, waste_type TEXT, eco_score REAL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT, role TEXT)");
            stmt.execute("INSERT OR IGNORE INTO users (username, password, role) VALUES ('admin', 'pass123', 'admin')");
            stmt.execute("INSERT OR IGNORE INTO users (username, password, role) VALUES ('user', 'pass123', 'resident')");
        } catch (SQLException e) {
            System.out.println("Database init error: " + e.getMessage());
        }
    }

    public static void addReport(BinReport report) {
        String sql = "INSERT INTO bin_reports (location, status, waste_type, eco_score) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, report.getLocation());
            pstmt.setString(2, report.getStatus());
            pstmt.setString(3, report.getWasteType());
            pstmt.setDouble(4, report.getEcoScore());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add report error: " + e.getMessage());
        }
    }

    public static List<BinReport> getAllReports() {
        List<BinReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM bin_reports";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reports.add(new BinReport(rs.getInt("id"), rs.getString("location"), rs.getString("status"),
                        rs.getString("waste_type")));
            }
        } catch (SQLException e) {
            System.out.println("Get reports error: " + e.getMessage());
        }
        return reports;
    }
}

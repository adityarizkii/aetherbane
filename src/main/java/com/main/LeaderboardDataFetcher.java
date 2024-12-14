package com.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDataFetcher {

    public static List<Object[]> getLeaderboardData() {
        List<Object[]> leaderboardData = new ArrayList<>();
        String query = "SELECT username, score FROM leaderboard ORDER BY score DESC LIMIT 10"; // Ambil 10 skor
                                                                                               // tertinggi

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                leaderboardData.add(new Object[] { username, score });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboardData;
    }

    public static boolean saveLeaderboardData(String username, int score) {
        String insertQuery = "INSERT INTO leaderboard (username, score) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, username);
            pstmt.setInt(2, score);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getTenthPlaceScore() {
        String query = "SELECT score FROM leaderboard ORDER BY score DESC LIMIT 1 OFFSET 9"; // Baris ke-10 (offset 9)
        int tenthScore = -1; // Default jika tidak ada data

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                tenthScore = rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tenthScore;
    }

}

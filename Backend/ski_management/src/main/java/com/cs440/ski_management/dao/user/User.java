package com.cs440.ski_management.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import com.cs440.ski_management.util.DBConnection.*;

public class User {
    // signup, login methods go here (US.15, US.16)
	
	public boolean signup(String username, String email, String password, String firstName, String lastName) {
		
		String checkSQL = "SELECT username FROM Users WHERE username = ? or email = ?;";
		String insertSQL = "INSERT INTO Users(Username, FirstName, LastName, Email, `password`, Salt, `Role`) values(?,?,?,?,?,?,?);";
		Connection conn = DBConnection.getConnection(); 
		
		try{
			
			try(PreparedStatement psCheck = conn.prepareStatement(checkSQL);){
				
				psCheck.setString(1, username);
				psCheck.setString(2,email);
				
				try(ResultSet checkResponse = psCheck.executeQuery();){
					//username or email is taken
					if(checkResponse.next()) {
						return false;
					}
				}
			}
			
			byte[] salt = getSalt();
			char[] pass = password.toCharArray();
			byte[] newPass = hashPass(pass,salt); // runs a hash function on this string to get final password before storing
			

			try(PreparedStatement psInsert = conn.prepareStatement(insertSQL);) {
				psInsert.setString(1, username);
				psInsert.setString(2, firstName);
				psInsert.setString(3, lastName);
				psInsert.setString(4, email);
				psInsert.setBytes(5, newPass);
				psInsert.setBytes(6, salt);
				psInsert.setString(7, "skier");
					
				return psInsert.executeUpdate() == 1;
			}
		}catch(SQLException e) {
			System.out.println("SQL Error");
			e.printStackTrace();
			return false;
		}finally {
			DBConnection.closeConnection();
		}
	}
	
	
	private static byte[] getSalt() {
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		return salt;
	}
	
		/*
		 * Takes on a char array and byte array we pass those arrays into a PBEKeySpec object this is how the password will be changed.
		 * Then the SecretKeyFactory is told the function and hash to use these are the Java's standard function using the hash.
		 * Then we run the encoding and return the results.
		 */
	private static byte[] hashPass(char[] password, byte[] salt) {
		try {
			PBEKeySpec spec = new PBEKeySpec(password,salt,9127,256); 
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		    return skf.generateSecret(spec).getEncoded(); 
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
	}

	public boolean login(String username, String inputPass) {
		
		Connection conn = DBConnection.getConnection();
				
		String sql = "SELECT * FROM users WHERE username LIKE ?;";
				
		try(PreparedStatement psCheck = conn.prepareStatement(sql);){
			psCheck.setString(1, username);
			try(ResultSet response = psCheck.executeQuery();){
				if(!response.next()) {
					//incorrect username
					return false;
				}
				byte[] dbPass = response.getBytes("password"); 
				byte[] salt = response.getBytes("salt");
				char[] pass = inputPass.toCharArray();
				byte[] hashedPass = hashPass(pass, salt);
				
				if(!MessageDigest.isEqual(dbPass, hashedPass)) {
					//incorrect password
					return false;
				}
				return true;
			}
		}catch (SQLException e) {
			System.err.println("Login failed.");
			e.printStackTrace();
			return false;
		}finally {
			DBConnection.closeConnection();
		}
	}
}
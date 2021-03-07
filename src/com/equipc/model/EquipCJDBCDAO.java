package com.equipc.model;

import java.util.*;
import java.sql.*;

public class EquipCJDBCDAO implements EquipCDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";

	private static final String INSERT_STMT = "INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT EPC_NO,EPC_NAME FROM EQUIPC ORDER BY EPC_NO";
	private static final String GET_ONE_STMT = "SELECT EPC_NO,EPC_NAME FROM EQUIPC WHERE EPC_NO = ?";
	private static final String DELETE = "DELETE FROM EQUIPC WHERE EPC_NO = ?";
	private static final String UPDATE = "UPDATE EQUIPC set EPC_NAME =?,EPC_NO =? WHERE EPC_NO =?";

	@Override
	public void insert(EquipCVO equipCVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, equipCVO.getEpc_no());
			pstmt.setString(2, equipCVO.getEpc_name());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(EquipCVO equipCVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, equipCVO.getEpc_name());
			pstmt.setString(2, equipCVO.getEpc_no());
			pstmt.setString(3, equipCVO.getEpc_no());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String epc_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, epc_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public EquipCVO findByPrimaryKey(String epc_no) {

		EquipCVO equipCVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, epc_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// EpcVO 也稱為 Domain objects
				equipCVO = new EquipCVO();
				equipCVO.setEpc_no(rs.getString("epc_no"));
				equipCVO.setEpc_name(rs.getString("epc_name"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return equipCVO;
	}

	@Override
	public List<EquipCVO> getAll() {
		List<EquipCVO> list = new ArrayList<EquipCVO>();
		EquipCVO equipCVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// EpcVO 也稱為 Domain objects
				equipCVO = new EquipCVO();
				equipCVO.setEpc_no(rs.getString("epc_no"));
				equipCVO.setEpc_name(rs.getString("epc_name"));

				list.add(equipCVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {

		EquipCJDBCDAO dao = new EquipCJDBCDAO();

		// 新增
//		EquipCVO epcVO1 = new EquipCVO();
//		epcVO1.setEpc_no("ETEST2");
//		epcVO1.setEpc_name("測試2");
//		dao.insert(epcVO1);

		// 修改
		EquipCVO epcVO2 = new EquipCVO();
		epcVO2.setEpc_no("EQTSS");
		epcVO2.setEpc_name("測試5");
		dao.update(epcVO2);

		//刪除
//		dao.delete("EQTSS");
		
		// 查詢
//		EquipCVO epcVO3 = dao.findByPrimaryKey("EQAH");
//		System.out.print(epcVO3.getEpc_no() + ",");
//		System.out.print(epcVO3.getEpc_name() + ",");
//		System.out.println("---------------------");

		// 查詢
//		List<EquipCVO> list = dao.getAll();
//		for (EquipCVO aEmp : list) {
//			System.out.print(aEmp.getEpc_no() + ",");
//			System.out.print(aEmp.getEpc_name() + ",");
//			System.out.println();
//		}
		
	}
}
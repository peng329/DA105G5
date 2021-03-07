package com.dspic.model;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.dsrm_record.model.Dsrm_recordVO;

public class DspicJDBCDAO implements DspicDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";

	private static final String INSERT_STMT = "INSERT INTO DSPIC VALUES ('DSP'||LPAD(to_char(DPIC_SEQ.NEXTVAL), 3, '0'),?,?)";

	private static final String DELETE_DSPIC = "DELETE FROM DSPIC WHERE DPIC_SEQ = ?";

	private static final String UPDATE = "UPDATE DSPIC SET DS_NO = ?, DPIC = ? WHERE DPIC_SEQ = ?";

	private static final String GET_ALL_DSPIC = "SELECT * FROM DSPIC";
	private static final String GET_ONE_DSPIC = "SELECT * FROM DSPIC WHERE DPIC_SEQ = ?";
	private static final String GET_DSPIC_BY_DSNO = "SELECT * FROM DSPIC WHERE DS_NO = ?";

	@Override
	public void insert(DspicVO dspicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dspicVO.getDs_no());
			pstmt.setBytes(2, dspicVO.getDpic());

			pstmt.executeUpdate();

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
	public void update(DspicVO dspicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, dspicVO.getDs_no());
			pstmt.setBytes(2, dspicVO.getDpic());
			pstmt.setString(3, dspicVO.getDpic_seq());

			pstmt.executeUpdate();
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
	public void delete(String dpic_seq) {
		int updateCount_record = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_DSPIC);
			pstmt.setString(1, dpic_seq);
			updateCount_record = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除紀錄編號:" + dpic_seq + ":" + updateCount_record + "筆");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public DspicVO findByPrimaryKey(String dpic_seq) {
		DspicVO dspicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_DSPIC);

			pstmt.setString(1, dpic_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dspicVO = new DspicVO();
				dspicVO.setDpic_seq(rs.getString("dpic_seq"));
				dspicVO.setDs_no(rs.getString("ds_no"));
				dspicVO.setDpic(rs.getBytes("dpic"));
			}
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
		return dspicVO;
	}

	@Override
	public List<DspicVO> findByDs_no(String ds_no) {
		List<DspicVO> list = new LinkedList<DspicVO>();
		DspicVO dspicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_DSPIC_BY_DSNO);
			pstmt.setString(1, ds_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dspicVO = new DspicVO();
				dspicVO.setDpic_seq(rs.getString("dpic_seq"));
				dspicVO.setDs_no(rs.getString("ds_no"));
				dspicVO.setDpic(rs.getBytes("dpic"));
				list.add(dspicVO);
			}
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

	@Override
	public List<DspicVO> getAll() {
		List<DspicVO> list = new ArrayList<DspicVO>();
		DspicVO dspicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_DSPIC);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dspicVO = new DspicVO();
				dspicVO.setDpic_seq(rs.getString("dpic_seq"));
				dspicVO.setDs_no(rs.getString("ds_no"));
				dspicVO.setDpic(rs.getBytes("dpic"));
				list.add(dspicVO);
			}
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

	@Override
	public void inert2(DspicVO dspicVO, Connection con) {
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dspicVO.getDs_no());
			pstmt.setBytes(2, dspicVO.getDpic());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-dspic");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		}

	}

	public static void main(String[] args) throws IOException {
		DspicJDBCDAO dao = new DspicJDBCDAO();

		// insert
//		DspicVO dspicVO1 = new DspicVO();
//		dspicVO1.setDs_no("DS0002");
//		
//		byte[] pic = getPictureByteArray("WebContent/back-end/image/p15.png");
//		
//		dspicVO1.setDpic(pic);
//		dao.insert(dspicVO1);

		// update
//		DspicVO dspicVO2 = new DspicVO();
//		dspicVO2.setDs_no("DS0002");
//		dspicVO2.setDpic(null);
//		dspicVO2.setDpic_name("diveshop1");
//		dspicVO2.setDpic_seq("DSP001");
//		dao.update(dspicVO2);

		// delete
//		dao.delete("DSP006");

		// 查詢圖片(PK)
//		DspicVO dspicVO3 = dao.findByPrimaryKey("DSP005");
//		System.out.print(dspicVO3.getDpic_seq() + ",");
//		System.out.print(dspicVO3.getDs_no() + ",");
//		System.out.print(dspicVO3.getDpic() + ",");
//		System.out.print(dspicVO3.getDpic_name());
//		System.out.println();

		// 查詢圖片(潛店編號)
		List<DspicVO> set = dao.findByDs_no("DS0006");
		for(DspicVO apic : set) {
			System.out.print(apic.getDpic_seq() + ",");
			System.out.print(apic.getDs_no() + ",");
			System.out.print(apic.getDpic() + ",");
			
			System.out.println();
		}

		// 查詢全部圖片
//		List<DspicVO> list = dao.getAll();
//		for(DspicVO apic : list) {
//			System.out.print(apic.getDpic_seq() + ",");
//			System.out.print(apic.getDs_no() + ",");
//			System.out.print(apic.getDpic() + ",");
//			System.out.print(apic.getDpic_name());
//			System.out.println();
//		}

	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

}

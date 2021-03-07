package com.lespic.model;

import java.sql.*;
import java.util.*;

import com.dspic.model.DspicVO;

public class LespicJDBCDAO implements LespicDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";

	private static final String INSERT_STMT = "INSERT INTO LESPIC VALUES ('LSP'||LPAD(to_char(LPIC_SEQ.NEXTVAL), 3, '0'),?,?)";

	private static final String DELETE_LESPIC = "DELETE FROM LESPIC WHERE LPIC_SEQ = ?";

	private static final String UPDATE = "UPDATE LESPIC SET LES_NO = ?, LPIC = ? WHERE LPIC_SEQ = ?";

	private static final String GET_ALL_LESPIC = "SELECT * FROM LESPIC";
	private static final String GET_ONE_LESPIC = "SELECT * FROM LESPIC WHERE LPIC_SEQ = ?";
	private static final String GET_LPIC_BY_LESNO = "SELECT * FROM LESPIC WHERE LES_NO = ?";

	@Override
	public void insert2(LespicVO lespicVO, Connection con) {
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, lespicVO.getLes_no());
			pstmt.setBytes(2, lespicVO.getLpic());
			
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
	
	
	@Override
	public void insert(LespicVO lespicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, lespicVO.getLes_no());
			pstmt.setBytes(2, lespicVO.getLpic());

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
	public void update(LespicVO lespicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, lespicVO.getLes_no());
			pstmt.setBytes(2, lespicVO.getLpic());
			pstmt.setString(3, lespicVO.getLpic_seq());

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
	public void delete(String lpic_seq) {
		int updateCount_record = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_LESPIC);
			pstmt.setString(1, lpic_seq);
			updateCount_record = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除圖片編號:" + lpic_seq + ":" + updateCount_record + "筆");
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
	public LespicVO findByPrimaryKey(String lpic_seq) {
		LespicVO lespicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_LESPIC);

			pstmt.setString(1, lpic_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lespicVO = new LespicVO();
				lespicVO.setLpic_seq(rs.getString("lpic_seq"));
				lespicVO.setLes_no(rs.getString("les_no"));
				lespicVO.setLpic(rs.getBytes("lpic"));
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
		return lespicVO;
	}

	@Override
	public List<LespicVO> findByLes_no(String les_no) {
		List<LespicVO> list = new ArrayList<LespicVO>();
		LespicVO lespicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_LPIC_BY_LESNO);
			pstmt.setString(1, les_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lespicVO = new LespicVO();
				lespicVO.setLpic_seq(rs.getString("lpic_seq"));
				lespicVO.setLes_no(rs.getString("les_no"));
				lespicVO.setLpic(rs.getBytes("lpic"));
				list.add(lespicVO);
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
	public List<LespicVO> getAll() {
		List<LespicVO> list = new ArrayList<LespicVO>();
		LespicVO lespicVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_LESPIC);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lespicVO = new LespicVO();
				lespicVO.setLpic_seq(rs.getString("lpic_seq"));
				lespicVO.setLes_no(rs.getString("les_no"));
				lespicVO.setLpic(rs.getBytes("lpic"));
				list.add(lespicVO);
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

	public static void main(String[] args) {
		LespicJDBCDAO dao = new LespicJDBCDAO();
		
		//insert
//		LespicVO lespicVO1 = new LespicVO();
//		lespicVO1.setLes_no("LE0006");
//		lespicVO1.setLpic(null);
//		dao.insert(lespicVO1);
		
		//update
//		LespicVO lespicVO2 = new LespicVO();
//		lespicVO2.setLes_no("LE0007");
//		lespicVO2.setLpic(null);
//		lespicVO2.setLpic_seq("LSP003");
//		dao.update(lespicVO2);
		
		//delete
//		dao.delete("LSP006");
		
		//查詢圖片(PK)
//		LespicVO lespicVO3 = dao.findByPrimaryKey("LSP004");
//		System.out.print(lespicVO3.getLpic_seq() + ",");
//		System.out.print(lespicVO3.getLes_no() + ",");
//		System.out.print(lespicVO3.getLpic() + ",");
//		System.out.println();
		
		//查詢圖片(LES_NO)
//		Set<LespicVO> set = dao.findByLes_no("LE0007");
//		for(LespicVO apic : set) {
//		System.out.print(apic.getLpic_seq() + ",");
//		System.out.print(apic.getLes_no() + ",");
//		System.out.print(apic.getLpic() + ",");
//		System.out.println();
//		}
		
		//查詢全部圖片
		List<LespicVO> list = dao.getAll();
		for(LespicVO apic : list) {
			System.out.print(apic.getLpic_seq() + ",");
			System.out.print(apic.getLes_no() + ",");
			System.out.print(apic.getLpic() + ",");
			System.out.println();
		}
	}

	
}

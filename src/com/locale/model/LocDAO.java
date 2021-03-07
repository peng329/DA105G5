package com.locale.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.dive_point.model.DpVO;

public class LocDAO implements LocDAO_interface {
	private static DataSource ds = null;
	// JNDI��
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// JDBC�s�u
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "123456";
	// ��CRUD���Ǽg
	private static final String INSERT_STMT = "INSERT INTO locale ( loc_no,ctry,sub_region,weather) "
			+ "VALUES ('LOC'||LPAD(to_char(MPO_SEQ.NEXTVAL),6,'0'), ?,?,? )";
	private static final String GET_ALL_STMT = "SELECT loc_no,ctry,sub_region,weather FROM locale order by loc_no";
	private static final String GET_ONE_STMT = "SELECT loc_no,ctry,sub_region,weather FROM locale where loc_no = ?";
	private static final String DELETE_LOCs = "DELETE FROM locale where loc_no = ?";
	private static final String DELETE_Dps = "DELETE FROM dive_point where loc_no = ?";
	private static final String UPDATE = "UPDATE locale set ctry=?,sub_region=?,weather=? where loc_no = ?";
	private static final String GET_Dps_By_Loc_no_STMT = "SELECT dp_no,loc_no,dp_name,dp_lat,dp_lng,dp_info,dp_pic1,dp_pic2,dp_pic3,dp_pic4 FROM dive_point where loc_no = ? order by dp_no";

	@Override
	public void insert(LocVO locVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, locVO.getCtry());
			pstmt.setString(2, locVO.getSub_region());
			pstmt.setString(3, locVO.getWeather());
			pstmt.executeUpdate();

//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found" + ce.getMessage());
		} catch (SQLException se) {
			if (con != null) {
			}
			throw new RuntimeException("a database error occurred" + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
			}
		}
	}

	@Override
	public void update(LocVO locVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, locVO.getCtry());
			pstmt.setString(2, locVO.getSub_region());
			pstmt.setString(3, locVO.getWeather());
			pstmt.setString(4, locVO.getLoc_no());
			pstmt.executeUpdate();
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found." + ce.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String loc_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_Dps);
			pstmt.setString(1, loc_no);
			con.commit();

			pstmt = con.prepareStatement(DELETE_LOCs);
			pstmt.setString(1, loc_no);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found" + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException rbackexc) {
							throw new RuntimeException("rollback error occured" + rbackexc.getMessage());
						}
					}
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public LocVO findByPrimaryKey(String loc_no) {
		LocVO locVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, loc_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				locVO = new LocVO();
				locVO.setLoc_no(rs.getString("loc_no"));
				locVO.setCtry(rs.getString("ctry"));
				locVO.setSub_region(rs.getString("sub_region"));
				locVO.setWeather(rs.getString("weather"));
			}
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found" + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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
		return locVO;
	}

	@Override
	public List<LocVO> getAll() {
		List<LocVO> list = new ArrayList<LocVO>();
		LocVO locVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
System.out.println("getall");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				locVO = new LocVO();
				locVO.setLoc_no(rs.getString("loc_no"));
				locVO.setCtry(rs.getString("ctry"));
				locVO.setSub_region(rs.getString("sub_region"));
				locVO.setWeather(rs.getString("weather"));
				list.add(locVO);
			}
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found" + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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
	public Set<DpVO> getDpsByLocno(String loc_no) {
		Set<DpVO> set = new LinkedHashSet<DpVO>();
		DpVO dpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Dps_By_Loc_no_STMT);
			pstmt.setString(1, loc_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dpVO = new DpVO();
				dpVO.setDp_no(rs.getString("dp_no"));
				dpVO.setLoc_no(rs.getString("loc_no"));
				dpVO.setDp_name(rs.getString("dp_name"));
				dpVO.setDp_lat(rs.getDouble("dp_lat"));
				dpVO.setDp_lng(rs.getDouble("dp_lng"));
				dpVO.setDp_info(rs.getString("dp_info"));
				dpVO.setDp_pic1(rs.getBytes("dp_pic1"));
				dpVO.setDp_pic2(rs.getBytes("dp_pic2"));
				dpVO.setDp_pic3(rs.getBytes("dp_pic3"));
				dpVO.setDp_pic4(rs.getBytes("dp_pic4"));
				set.add(dpVO);
			}
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found" + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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
		return set;
	}

	public static void main(String[] args) {
		LocDAO dao = new LocDAO();
		Timestamp currTstamp = new Timestamp(System.currentTimeMillis());
		dao.getDpsByLocno("LOC000001");
//		System.out.println(currTstamp);
		// �s�W:OK
//		LocVO locVOI1 = new LocVO();
//		locVOI1.setLoc_no("DP999999");//���M�g�F���O�ä��|�]�w�i�h
//		locVOI1.setCtry("TWN");
//		locVOI1.setSub_region("test Area");
//		locVOI1.setWeather("1st test Weather for LocDAO");
//		dao.insert(locVOI1);

//		LocVO locVOI2 = new LocVO();
//		locVOI2.setLoc_no("DP888888");//���M�g�F���O�ä��|�]�w�i�h
//		locVOI2.setCtry("TWN");
//		locVOI2.setSub_region("test Area");
//		locVOI2.setWeather("2nd test Weather for LocDAO");
//		dao.insert(locVOI2);
//
//		LocVO locVOI3 = new LocVO();
//		locVOI3.setLoc_no("DP777777");//���M�g�F���O�ä��|�]�w�i�h
//		locVOI3.setCtry("TWN");
//		locVOI3.setSub_region("test Area");
//		locVOI3.setWeather("3rd test Weather for LocDAO");
//		dao.insert(locVOI3);

//		// �ק�(���w��ƶi��ק�A�|���q��Ʈw���վ\���w��PK):OK
//		LocVO locVOU1 = new LocVO();
//		System.out.println("by Testing , insert the Loc_po which is exist.");
//		Scanner sc = new Scanner(System.in);
//		String pkforUpdate = sc.nextLine();
//
//		locVOU1 = dao.findByPrimaryKey(pkforUpdate);
//		locVOU1.setCtry("TRY");
//		locVOU1.setSub_region("test update");
//		locVOU1.setWeather("It is a good day to dive.");
//		dao.update(locVOU1);

		// �R��:OK
//		dao.delete("LOC000018");

//		// �d�ߤ@��:OK
//		LocVO locVO4 = dao.findByPrimaryKey(pkforUpdate);
//		System.out.print(locVO4.getLoc_no() + ",");
//		System.out.print(locVO4.getCtry()+ ",");
//		System.out.print(locVO4.getSub_region() + ",");
//		System.out.print(locVO4.getWeather() + ",");
//		System.out.print("\n---------------------");

		// �d��:OK
//		List<LocVO> list = dao.getAll();
//		for (LocVO aLoc : list) {
//			System.out.print(aLoc.getLoc_no() + ",");
//			System.out.print(aLoc.getCtry() + ",");
//			System.out.print(aLoc.getSub_region() + ",");
//			System.out.print(aLoc.getWeather() + ",");
//			
//
//
//			System.out.println();
//		}

	}

}

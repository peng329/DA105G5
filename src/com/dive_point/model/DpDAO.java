package com.dive_point.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.locale.model.LocDAO;
import com.locale.model.LocVO;

public class DpDAO implements DpDAO_interface {
	private static DataSource ds = null;
// JNDI	
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
	// 11 attributes

	private static final String INSERT_STMT = "INSERT INTO DIVE_POINT (dp_no,loc_no,dp_name,dp_lat,	dp_lng,	dp_info,dp_pic1,dp_pic2,dp_pic3,dp_pic4)\r\n"
			+ "VALUES ('DP'||LPAD(to_char(DP_SEQ.NEXTVAL),6,'0'),?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT dp_no,loc_no,dp_name,dp_lat,	dp_lng,	dp_info,dp_pic1,dp_pic2,dp_pic3,dp_pic4 FROM dive_point ORDER BY dp_no";
	private static final String GET_ONE_STMT = "SELECT dp_no,loc_no,dp_name,dp_lat,	dp_lng,	dp_info,dp_pic1,dp_pic2,dp_pic3,dp_pic4 FROM dive_point WHERE dp_no = ?";
	private static final String DELETE = "DELETE FROM dive_point WHERE dp_no = ?";
	private static final String UPDATE = "UPDATE dive_point SET loc_no=?,dp_name=?,dp_lat=?,dp_lng=?,dp_info=?,dp_pic1=?,dp_pic2=?,dp_pic3=?,dp_pic4=? WHERE dp_no = ?";

	@Override
	public void insert(DpVO dpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, dpVO.getLoc_no());
			pstmt.setString(2, dpVO.getDp_name());
			pstmt.setDouble(3, dpVO.getDp_lat());
			pstmt.setDouble(4, dpVO.getDp_lng());
			pstmt.setString(5, dpVO.getDp_info());
			pstmt.setBytes(6, dpVO.getDp_pic1());
			pstmt.setBytes(7, dpVO.getDp_pic2());
			pstmt.setBytes(8, dpVO.getDp_pic3());
			pstmt.setBytes(9, dpVO.getDp_pic4());
			pstmt.executeUpdate();
		}
//		catch (ClassNotFoundException ce) {
//			throw new RuntimeException("driver not found" + ce.getMessage());
//		}
		catch (SQLException se) {
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(DpVO dpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, dpVO.getLoc_no());
			pstmt.setString(2, dpVO.getDp_name());
			pstmt.setDouble(3, dpVO.getDp_lat());
			pstmt.setDouble(4, dpVO.getDp_lng());
			pstmt.setString(5, dpVO.getDp_info());
			pstmt.setBytes(6, dpVO.getDp_pic1());
			pstmt.setBytes(7, dpVO.getDp_pic2());
			pstmt.setBytes(8, dpVO.getDp_pic3());
			pstmt.setBytes(9, dpVO.getDp_pic4());
			pstmt.setString(10, dpVO.getDp_no());// set the primaryKey
			pstmt.executeUpdate();
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found." + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void delete(String dp_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			con.setAutoCommit(false);

			pstmt.setString(1, dp_no);// set the primaryKey
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found." + ce.getMessage());
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public DpVO findByPrimaryKey(String dp_no) {
		DpVO dpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, dp_no);// set the primaryKey
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

			}
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found." + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
		return dpVO;
	}

	@Override
	public List<DpVO> getAll() {
		List<DpVO> list = new ArrayList<DpVO>();
		DpVO dpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_STMT);

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
				list.add(dpVO);
			}
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Driver not found." + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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

//	���աG�s�X�a�Ͻs���M��A��K���ӿ�J�٨S�g���C
//	public List<LocVO> getLoc_no(){

//	}

	public static void main(String[] args) {

		DpDAO dao = new DpDAO();
		List<LocVO> locDAO = new LocDAO().getAll();// �ɥήM��getAll()
		System.out.println("Loc_no:\t\tCtry\tSub_region\tWeather\t\n---------------------------");
		for (LocVO locs : locDAO) {
			System.out.print(locs.getLoc_no() + "\t");
			System.out.print(locs.getCtry() + "\t");
			System.out.print(locs.getSub_region() + "\t");
			System.out.print(locs.getWeather() + "\t\n");
		}
		// �s�W�GOK
//		DpVO dpVOI1 = new DpVO();
//		dpVOI1.setDp_no("M000004");
//		dpVOI1.setLoc_no("LOC000009");
//		dpVOI1.setDp_name("Banana Bay");
//		dpVOI1.setDp_info("All divers");
//		dpVOI1.setDp_lat(21.92433);
//		dpVOI1.setDp_lng(120.83233);
//		dpVOI1.setDp_pic1(new byte[1]);
//		dpVOI1.setDp_pic2(new byte[1]);
//		dpVOI1.setDp_pic3(new byte[1]);
//		dpVOI1.setDp_pic4(new byte[1]);
//		dao.insert(dpVOI1);

//		DpVO dpVOI2 = new DpVO();
//		dpVOI2.setDp_no("M000004");
//		dpVOI2.setLoc_no("LOC000009");
//		dpVOI2.setDp_name("Ceasars Rock");
//		dpVOI2.setDp_lat(21.93017);
//		dpVOI2.setDp_lng(120.79983);
//		dpVOI2.setDp_info("CMAS ** / AOW");
//		dpVOI2.setDp_pic1(new byte[1]);
//		dpVOI2.setDp_pic2(new byte[1]);
//		dpVOI2.setDp_pic3(new byte[1]);
//		dpVOI2.setDp_pic4(new byte[1]);
//		dao.insert(dpVOI2);

//		DpVO dpVOI3 = new DpVO();
//		dpVOI3.setDp_no("M000004");
//		dpVOI3.setLoc_no("LOC000009");
//		dpVOI3.setDp_name("Ho-Jie");
//		dpVOI3.setDp_lat(21.93017);
//		dpVOI3.setDp_lng(120.79983);
//		dpVOI3.setDp_info("CMAS ** / AOW");
//		dpVOI3.setDp_pic1(new byte[1]);
//		dpVOI3.setDp_pic2(new byte[1]);
//		dpVOI3.setDp_pic3(new byte[1]);
//		dpVOI3.setDp_pic4(new byte[1]);
//		dao.insert(dpVOI3);

		// �ק�(���w��ƶi��ק�A�|���q��Ʈw���վ\���w��PK)�GOK
//		DpVO dpVOU1 = new DpVO();
//		System.out.println("by Testing , insert the Dp_no which is exist.");
//		Scanner sc = new Scanner(System.in);
//		String pkforUpdate = sc.nextLine();
//
//		dpVOU1=dao.findByPrimaryKey(pkforUpdate);
//		dpVOU1.setLoc_no("LOC000003");
//		dpVOU1.setDp_name("FR07");
//		dpVOU1.setDp_info("this is and update for Article publish in FR09 from mempoVO12" );
//		dpVOU1.setDp_pic4(null);
//		dao.update(dpVOU1);

//		// �R��
//		dao.delete("MP000011");

		// �d�ߤ@�ӡGOK
//		DpVO dpVO4 = dao.findByPrimaryKey("DP000001");
//		System.out.print(dpVO4.getDp_no() + "\t");
//		System.out.print(dpVO4.getLoc_no() + "\t");
//		System.out.print(dpVO4.getDp_name() + "\t");
//		System.out.print(dpVO4.getDp_lat() + "\t");
//		System.out.print(dpVO4.getDp_lng() + "\t");
//		System.out.print(dpVO4.getDp_info() + "\t");
//		System.out.print(dpVO4.getDp_pic1() + "\t");
//		System.out.print(dpVO4.getDp_pic2() + "\t");
//		System.out.print(dpVO4.getDp_pic3() + "\t");
//		System.out.print(dpVO4.getDp_pic4() + "\t\n");
//		System.out.println("---------------------");

//		// �d�ߥ����GOK
//		List<DpVO> list = dao.getAll();
//		for (DpVO aDp : list) {
//			System.out.print(aDp.getDp_no() + ",");
//			System.out.print(aDp.getLoc_no() + ",");
//			System.out.print(aDp.getDp_name() + ",");
//			System.out.print(aDp.getDp_lat() + ",");
//			System.out.print(aDp.getDp_lng() + ",");
//			System.out.print(aDp.getDp_info() + ",");
//			System.out.print(aDp.getDp_pic1() + ",");
//			System.out.print(aDp.getDp_pic2() + ",");
//			System.out.print(aDp.getDp_pic3() + ",");
//			System.out.print(aDp.getDp_pic4() + ",");
//
//			System.out.println();
//		}
	}

	public static void readPicture(byte[] bytes) throws IOException {
		FileOutputStream fos = new FileOutputStream("Output/2.png");
		fos.write(bytes);
		fos.flush();
		fos.close();
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];// byte�}�C �ۭq�w��
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();// �^�ǭȬOByte[]�}�C
	}

}

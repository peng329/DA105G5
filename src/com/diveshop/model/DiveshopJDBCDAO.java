package com.diveshop.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.dspic.model.DspicJDBCDAO;
import com.dspic.model.DspicVO;

public class DiveshopJDBCDAO implements DiveshopDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";

	private static final String INSERT_STMT = "INSERT INTO DIVESHOP VALUES ('DS'||LPAD(to_char(DSNO_SEQ.NEXTVAL), 4, '0'),?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String GET_ALL_STMT = "SELECT * FROM DIVESHOP";
	private static final String GET_ONE_STMT = "SELECT * FROM DIVESHOP WHERE DS_NO = ?";
	private static final String GET_LOC_DIVESHOP = "SELECT * FROM DIVESHOP WHERE ADDRESS LIKE ? ORDER BY DS_NO";

	private static final String DELETE_DIVESHOP = "DELETE FROM DIVESHOP WHERE DS_NO = ?";

	private static final String UPDATE = "UPDATE DIVESHOP SET DS_NAME = ?,BRCID = ?, TEL = ?, ADDRESS = ?,DSACCOUNT = ?, DSPAW = ?, DSMAIL = ?, DSINFO = ? WHERE DS_NO = ?";

	private static final String GET_DSPIC_SEQ = "SELECT DPIC_SEQ FROM DSPIC WHERE DS_NO =?";
	
	 private static final String GET_ONE_DSACCOUNT_STMT = 
	            "SELECT * FROM DIVESHOP where DSACCOUNT = ?";


	@Override
	public void insert(DiveshopVO diveshopVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, diveshopVO.getDs_name());
			pstmt.setInt(2, diveshopVO.getBrcid());
			pstmt.setString(3, diveshopVO.getTel());
			pstmt.setString(4, diveshopVO.getAddress());
			pstmt.setString(5, diveshopVO.getDsaccount());
			pstmt.setString(6, diveshopVO.getDspaw());
			pstmt.setString(7, diveshopVO.getDsmail());
			pstmt.setString(8, diveshopVO.getDsinfo());
			pstmt.setString(9, diveshopVO.getDs_latlng());
			pstmt.setString(11, diveshopVO.getDs_state());
			pstmt.setInt(12, diveshopVO.getDs_ascore());
			pstmt.setInt(13, diveshopVO.getDs_rep_no());

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
	public void insertWithDspic(DiveshopVO diveshopVO, List<DspicVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			try {
				Class.forName(driver);

				con = DriverManager.getConnection(url, userid, passwd);

				// 新增潛店
				String cols[] = { "DS_NO" };
				pstmt = con.prepareStatement(INSERT_STMT, cols);
				pstmt.setString(1, diveshopVO.getDs_name());
				pstmt.setInt(2, diveshopVO.getBrcid());
				pstmt.setString(3, diveshopVO.getTel());
				pstmt.setString(4, diveshopVO.getAddress());
				pstmt.setString(5, diveshopVO.getDsaccount());
				pstmt.setString(6, diveshopVO.getDspaw());
				pstmt.setString(7, diveshopVO.getDsmail());
				pstmt.setString(8, diveshopVO.getDsinfo());
				pstmt.setString(9, diveshopVO.getDs_latlng());
				pstmt.setString(11, diveshopVO.getDs_state());
				pstmt.setInt(12, diveshopVO.getDs_ascore());
				pstmt.setInt(13, diveshopVO.getDs_rep_no());
				pstmt.executeUpdate();

				// 擷取對應的自增主鍵值
				String next_dsno = null;
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					next_dsno = rs.getString(1);
					System.out.println("自增主鍵值=" + next_dsno + "(剛新增成功的潛店編號)");
				} else {
					System.out.println("未取得自增主鍵值");
				}
				rs.close();

				// 在同時新增潛店照片
				DspicJDBCDAO dao = new DspicJDBCDAO();
				System.out.println("list.size()-A=" + list.size());
				for (DspicVO Dspics : list) {
					Dspics.setDs_no(next_dsno);
					dao.inert2(Dspics, con);
				}

				// 設定於pstmt.exexuteUpdate()之後
				con.commit();
				con.setAutoCommit(true);
				System.out.println("list.size()-B=" + list.size());
				System.out.println("新增潛店編號" + next_dsno + "時,共有" + list.size() + "張照片同時被新增");

				// handle any driver errors
			} catch (SQLException se) {
				if (con != null) {
					try {
						// 3●設定於當有exception發生時之catch區塊內
						System.err.print("Transaction is being ");
						System.err.println("rolled back-由-dept");
						con.rollback();
					} catch (SQLException excep) {
						throw new RuntimeException("rollback error occured. " + excep.getMessage());
					}
				}
				throw new RuntimeException("A database error occured. " + se.getMessage());
				// Clean up JDBC resources
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void update(DiveshopVO diveshopVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, diveshopVO.getDs_name());
			pstmt.setInt(2, diveshopVO.getBrcid());
			pstmt.setString(3, diveshopVO.getTel());
			pstmt.setString(4, diveshopVO.getAddress());
			pstmt.setString(5, diveshopVO.getDsaccount());
			pstmt.setString(6, diveshopVO.getDspaw());
			pstmt.setString(7, diveshopVO.getDsmail());
			pstmt.setString(8, diveshopVO.getDsinfo());
			pstmt.setString(9, diveshopVO.getDs_no());

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
	public void delete(String ds_no) {
		int updateCount_DiveShop = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_DIVESHOP);
			pstmt.setString(1, ds_no);
			updateCount_DiveShop = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除潛店編號:" + ds_no + ":" + updateCount_DiveShop + "筆");
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
	public DiveshopVO findByPrimaryKey(String ds_no) {

		DiveshopVO diveshopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ds_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				diveshopVO = new DiveshopVO();
				diveshopVO.setDs_name(rs.getString("ds_name"));
				diveshopVO.setBrcid(rs.getInt("brcid"));
				diveshopVO.setTel(rs.getString("tel"));
				diveshopVO.setAddress(rs.getNString("address"));
				diveshopVO.setDsaccount(rs.getString("dsaccount"));
				diveshopVO.setDspaw(rs.getString("dspaw"));
				diveshopVO.setDsmail(rs.getString("dsmail"));
				diveshopVO.setDsinfo(rs.getString("dsinfo"));
				diveshopVO.setDs_latlng(rs.getString("ds_latlng"));
				diveshopVO.setDs_state(rs.getString("ds_state"));
				diveshopVO.setDs_ascore(rs.getInt("ds_ascore"));
				diveshopVO.setDs_rep_no(rs.getInt("ds_rep_no"));
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

		return diveshopVO;
	}

	@Override
	public List<DiveshopVO> getAll() {
		List<DiveshopVO> list = new ArrayList<DiveshopVO>();
		DiveshopVO diveshopVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				diveshopVO = new DiveshopVO();
				diveshopVO.setDs_name(rs.getString("ds_name"));
				diveshopVO.setBrcid(rs.getInt("brcid"));
				diveshopVO.setTel(rs.getString("tel"));
				diveshopVO.setAddress(rs.getNString("address"));
				diveshopVO.setDsaccount(rs.getString("dsaccount"));
				diveshopVO.setDspaw(rs.getString("dspaw"));
				diveshopVO.setDsmail(rs.getString("dsmail"));
				diveshopVO.setDsinfo(rs.getString("dsinfo"));
				diveshopVO.setDs_latlng(rs.getString("ds_latlng"));
				diveshopVO.setDs_state(rs.getString("ds_state"));
				diveshopVO.setDs_ascore(rs.getInt("ds_ascore"));
				diveshopVO.setDs_rep_no(rs.getInt("ds_rep_no"));
				list.add(diveshopVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public List<String> getDpic_seqByDs_no(String ds_no) {
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_DSPIC_SEQ);
			
			pstmt.setString(1, ds_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("DPIC_SEQ"));
			}
			
		}catch (ClassNotFoundException e) {
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
	public Set<DiveshopVO> getShopByAddress(String address) {
		Set<DiveshopVO> set = new LinkedHashSet<DiveshopVO>();
		DiveshopVO diveshopVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_LOC_DIVESHOP);

			pstmt.setString(1, address);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				diveshopVO = new DiveshopVO();
				diveshopVO.setDs_name(rs.getString("ds_name"));
				diveshopVO.setBrcid(rs.getInt("brcid"));
				diveshopVO.setTel(rs.getString("tel"));
				diveshopVO.setAddress(rs.getNString("address"));
				diveshopVO.setDsaccount(rs.getString("dsaccount"));
				diveshopVO.setDspaw(rs.getString("dspaw"));
				diveshopVO.setDsmail(rs.getString("dsmail"));
				diveshopVO.setDsinfo(rs.getString("dsinfo"));
				diveshopVO.setDs_latlng(rs.getString("ds_latlng"));
				diveshopVO.setDs_state(rs.getString("ds_state"));
				diveshopVO.setDs_ascore(rs.getInt("ds_ascore"));
				diveshopVO.setDs_rep_no(rs.getInt("ds_rep_no"));
				set.add(diveshopVO);
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

		return set;
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

	@Override
	public DiveshopVO findByDsaccount(String dsaccount) {
		DiveshopVO diveshopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_DSACCOUNT_STMT);

			pstmt.setString(1, dsaccount);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				diveshopVO = new DiveshopVO();
				diveshopVO.setDs_name(rs.getString("ds_name"));
				diveshopVO.setBrcid(rs.getInt("brcid"));
				diveshopVO.setTel(rs.getString("tel"));
				diveshopVO.setAddress(rs.getNString("address"));
				diveshopVO.setDsaccount(rs.getString("dsaccount"));
				diveshopVO.setDspaw(rs.getString("dspaw"));
				diveshopVO.setDsmail(rs.getString("dsmail"));
				diveshopVO.setDsinfo(rs.getString("dsinfo"));
				diveshopVO.setDs_latlng(rs.getString("ds_latlng"));
				diveshopVO.setDs_state(rs.getString("ds_state"));
				diveshopVO.setDs_ascore(rs.getInt("ds_ascore"));
				diveshopVO.setDs_rep_no(rs.getInt("ds_rep_no"));
				diveshopVO.setDs_no(rs.getString("ds_no"));
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

		return diveshopVO;

	}
	
	public static void main(String[] args) throws IOException {

		DiveshopJDBCDAO dao = new DiveshopJDBCDAO();

		// insert
//		DiveshopVO diveshopVO1 = new DiveshopVO();
//		diveshopVO1.setDs_name("testshop");
//		diveshopVO1.setBrcid(12345678);
//		diveshopVO1.setTel("03918743");
//		diveshopVO1.setAddress("testAddress");
//		diveshopVO1.setDsaccount("testaccount");
//		diveshopVO1.setDspaw("testpassword");
//		diveshopVO1.setDsmail("test@mail.com");
//		diveshopVO1.setDsinfo("testInfo");
//		diveshopVO1.setDs_lng("13.2158");
//		diveshopVO1.setDs_lat("12.2186");
//		diveshopVO1.setDs_state("審核通過");
//		diveshopVO1.setDs_ascore(90);
//		diveshopVO1.setDs_rep_no(0);
//		dao.insert(diveshopVO1);

		//自增主鍵綁定
		DiveshopVO diveshopVO = new DiveshopVO();
		diveshopVO.setDs_name("自增主鍵綁定");
		diveshopVO.setBrcid(12345678);
		diveshopVO.setTel("03918743");
		diveshopVO.setAddress("testAddress");
		diveshopVO.setDsaccount("testaccount");
		diveshopVO.setDspaw("testpassword");
		diveshopVO.setDsmail("test@mail.com");
		diveshopVO.setDsinfo("testInfo");
		diveshopVO.setDs_latlng("13.2158");
		diveshopVO.setDs_state("審核通過");
		diveshopVO.setDs_ascore(90);
		diveshopVO.setDs_rep_no(0);
		
		List<DspicVO> testList = new ArrayList<DspicVO>(); //準備置入圖片
		DspicVO pic1 = new DspicVO();
		byte[] dpic = getPictureByteArray("WebContent/image/p18.jpg");;
		pic1.setDpic(dpic);
		
		
		DspicVO pic2 = new DspicVO();
		byte[] dpic2 = getPictureByteArray("WebContent/image/p18.jpg");;
		pic2.setDpic(dpic2);
	
		
		testList.add(pic1);
		testList.add(pic2);
		
		dao.insertWithDspic(diveshopVO, testList);
		
		// update
//		DiveshopVO diveshopVO2 = new DiveshopVO();
//		diveshopVO2.setDs_name("updatetest");
//		diveshopVO2.setBrcid(65412578);
//		diveshopVO2.setTel("0354876541");
//		diveshopVO2.setAddress("updateAddress");
//		diveshopVO2.setDsaccount("updateaccount");
//		diveshopVO2.setDspaw("updatepassword");
//		diveshopVO2.setDsmail("update@mail.com");
//		diveshopVO2.setDsinfo("updateInfo");
//		diveshopVO2.setDs_no("DS0005");
//		dao.update(diveshopVO2);

		// delete
//		dao.delete("DS0005");

		// 查詢某間潛店
//		DiveshopVO diveshopVO3 = dao.findByPrimaryKey("DS0003");
//		System.out.print(diveshopVO3.getDs_name() + ",");
//		System.out.print(diveshopVO3.getAddress() + ",");
//		System.out.print(diveshopVO3.getBrcid() + ",");
//		System.out.print(diveshopVO3.getTel() + ",");
//		System.out.print(diveshopVO3.getAddress() + ",");
//		System.out.print(diveshopVO3.getDsaccount() + ",");
//		System.out.print(diveshopVO3.getDspaw() + ",");
//		System.out.print(diveshopVO3.getDsmail() + ",");
//		System.out.print(diveshopVO3.getDsinfo() + ",");
//		System.out.print(diveshopVO3.getDs_lng() + ",");
//		System.out.print(diveshopVO3.getDs_lat()+ ",");
//		System.out.print(diveshopVO3.getDs_state() + ",");
//		System.out.print(diveshopVO3.getDs_ascore() + ",");
//		System.out.print(diveshopVO3.getDs_rep_no());
//		System.out.println();

		// 查詢潛店
//		List<DiveshopVO> list = dao.getaAll();
//		for (DiveshopVO aDive : list) {
//			System.out.print(aDive.getDs_name() + ",");
//			System.out.print(aDive.getAddress() + ",");
//			System.out.print(aDive.getBrcid() + ",");
//			System.out.print(aDive.getTel() + ",");
//			System.out.print(aDive.getAddress() + ",");
//			System.out.print(aDive.getDsaccount() + ",");
//			System.out.print(aDive.getDspaw() + ",");
//			System.out.print(aDive.getDsmail() + ",");
//			System.out.print(aDive.getDsinfo() + ",");
//			System.out.print(aDive.getDs_lng() + ",");
//			System.out.print(aDive.getDs_lat() + ",");
//			System.out.print(aDive.getDs_state() + ",");
//			System.out.print(aDive.getDs_ascore() + ",");
//			System.out.print(aDive.getDs_rep_no());
//			System.out.println();
//		}

		// 查詢某地址的潛店
//		Set<DiveshopVO> set = dao.getShopByAddress("%台北%");
//		for (DiveshopVO adiveshop : set) {
//			System.out.print(adiveshop.getDs_name() + ",");
//			System.out.print(adiveshop.getTel() + ",");
//			System.out.print(adiveshop.getAddress() + ",");
//			System.out.print(adiveshop.getDsmail() + ",");
//			System.out.print(adiveshop.getDs_ascore());
//			System.out.println();
//		}
		
		
		
		
	}

	
	

}

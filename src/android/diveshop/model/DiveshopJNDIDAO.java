package android.diveshop.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import android.dspic.model.DspicJNDIDAO;
import android.dspic.model.DspicVO;

public class DiveshopJNDIDAO implements DiveshopDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO DIVESHOP VALUES ('DS'||LPAD(to_char(DSNO_SEQ.NEXTVAL), 4, '0'),?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String GET_ALL_STMT = "SELECT * FROM DIVESHOP";
	private static final String GET_ONE_STMT = "SELECT * FROM DIVESHOP WHERE DS_NO = ?";
	private static final String GET_ONE_BY_ID = "SELECT * FROM DIVESHOP WHERE DSACCOUNT=?";
	private static final String GET_LOC_DIVESHOP = "SELECT * FROM DIVESHOP WHERE ADDRESS LIKE ? ORDER BY DS_NO";
    private static final String GET_BY_ID_PSW = "SELECT * FROM DIVESHOP WHERE DSACCOUNT=? AND DSPAW=?";
	private static final String DELETE_DIVESHOP = "DELETE FROM DIVESHOP WHERE DS_NO = ?";

	private static final String UPDATE = "UPDATE DIVESHOP SET DS_NAME = ?,BRCID = ?, TEL = ?, ADDRESS = ?,DSACCOUNT = ?, DSPAW = ?, DSMAIL = ?, DSINFO = ? WHERE DS_NO = ?";

	private static final String GET_DSPIC_SEQ = "SELECT DPIC_SEQ FROM DSPIC WHERE DS_NO =?";

	
	
	public boolean isDiveshopMem(String dsaccount,String dspaw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean isDiveshopMem=false;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_ID_PSW);
			pstmt.setString(1, dsaccount);
			pstmt.setString(2, dspaw);
			ResultSet rs = pstmt.executeQuery();
			isDiveshopMem = rs.next();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isDiveshopMem;
	}
	
	
	
	@Override
	public DiveshopVO findByPrimaryKey(String ds_no) {

		DiveshopVO diveshopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ds_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				diveshopVO = new DiveshopVO();
				diveshopVO.setDs_no(rs.getString("ds_no"));
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
	public DiveshopVO findById(String dsaccount) {

		DiveshopVO diveshopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_ID);

			pstmt.setString(1, dsaccount);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				diveshopVO = new DiveshopVO();
				diveshopVO.setDs_no(rs.getString("ds_no"));
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
	public List<DiveshopVO> getaAll() {
		List<DiveshopVO> list = new ArrayList<DiveshopVO>();
		DiveshopVO diveshopVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				diveshopVO = new DiveshopVO();
				diveshopVO.setDs_no(rs.getString("ds_no"));
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
		}  catch (SQLException se) {
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
	public Set<DiveshopVO> getShopByAddress(String address) {
		Set<DiveshopVO> set = new LinkedHashSet<DiveshopVO>();
		DiveshopVO diveshopVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
            con = ds.getConnection();
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
		}  catch (SQLException se) {
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

	

	
	
	public static void main(String[] args) throws IOException {

		DiveshopJNDIDAO dao = new DiveshopJNDIDAO();

		

		List<DiveshopVO> list = dao.getaAll();
		for (DiveshopVO aDive : list) {
			System.out.print(aDive.getDs_name() + ",");
			System.out.print(aDive.getAddress() + ",");
			System.out.print(aDive.getBrcid() + ",");
			System.out.print(aDive.getTel() + ",");
			System.out.print(aDive.getAddress() + ",");
			System.out.print(aDive.getDsaccount() + ",");
			System.out.print(aDive.getDspaw() + ",");
			System.out.print(aDive.getDsmail() + ",");
			System.out.print(aDive.getDsinfo() + ",");
			System.out.print(aDive.getDs_latlng() + ",");
			System.out.print(aDive.getDs_state() + ",");
			System.out.print(aDive.getDs_ascore() + ",");
			System.out.print(aDive.getDs_rep_no());
			System.out.println();
		}

	
		
		
		
		
	}
	

}


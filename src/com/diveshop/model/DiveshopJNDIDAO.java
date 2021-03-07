package com.diveshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.dspic.model.DspicJDBCDAO;
import com.dspic.model.DspicVO;

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
	private static final String GET_LOC_DIVESHOP = "SELECT * FROM DIVESHOP WHERE ADDRESS LIKE ? ORDER BY DS_NO";

	private static final String DELETE_DIVESHOP = "DELETE FROM DIVESHOP WHERE DS_NO = ?";

	private static final String UPDATE = "UPDATE DIVESHOP SET DS_NAME = ?,BRCID = ?, TEL = ?, ADDRESS = ?,DSACCOUNT = ?, DSPAW = ?, DSMAIL = ?, DSINFO = ? WHERE DS_NO = ?";
	private static final String GET_DSPIC_SEQ = "SELECT DPIC_SEQ FROM DSPIC WHERE DS_NO =?";

	//用帳號查找某間潛店
    private static final String GET_ONE_DSACCOUNT_STMT = 
            "SELECT * FROM DIVESHOP where DSACCOUNT = ?";

	
	@Override
	public void insert(DiveshopVO diveshopVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
			con.setAutoCommit(false);
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
			for (DspicVO aDspic : list) {
				aDspic.setDs_no(next_dsno);
				dao.inert2(aDspic, con);
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

			con = ds.getConnection();
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

			// Handle any driver errors
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_DIVESHOP);

			con.setAutoCommit(false);

			pstmt.setString(1, ds_no);
			updateCount_DiveShop = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除潛店編號:" + ds_no + ":" + updateCount_DiveShop + "筆");
			// Handle any driver errors
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
	public List<DiveshopVO> getAll() {
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

	@Override
	public List<String> getDpic_seqByDs_no(String ds_no) {
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_DSPIC_SEQ);

			pstmt.setString(1, ds_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("DPIC_SEQ"));
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

		return list;
	}
	
	@Override
	public DiveshopVO findByDsaccount(String dsaccount) {

		DiveshopVO diveshopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

}

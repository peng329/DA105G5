package android.dspic.model;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class DspicJNDIDAO implements DspicDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	  private static final String GET_ALL_DSPIC = "SELECT * FROM DSPIC";
	  private static final String GET_ONE_DSPIC = "SELECT * FROM DSPIC WHERE DPIC_SEQ = ?";
	  private static final String GET_DSPIC_BY_DSNO = "SELECT * FROM DSPIC WHERE DS_NO = ?";
      private static final String GET_DPIC_BY_DSNO = "SELECT DPIC FROM DSPIC WHERE DS_NO = ?";
      private static final String GET_PICCOUNT_BY_DSNO = "SELECT COUNT(*) AS COUNT FROM DSPIC WHERE DS_NO=?";
	
      @Override
	 public DspicVO findByPrimaryKey(String dpic_seq) {
		DspicVO dspicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_DSPIC);

			pstmt.setString(1, dpic_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dspicVO = new DspicVO();
				dspicVO.setDpic_seq(rs.getString("dpic_seq"));
				dspicVO.setDs_no(rs.getString("ds_no"));
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_DSPIC_BY_DSNO);
			pstmt.setString(1, ds_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dspicVO = new DspicVO();
				dspicVO.setDpic_seq(rs.getString("dpic_seq"));
				dspicVO.setDs_no(rs.getString("ds_no"));
				list.add(dspicVO);
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
	public List<DspicVO> getAll() {
		List<DspicVO> list = new ArrayList<DspicVO>();
		DspicVO dspicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_DSPIC);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dspicVO = new DspicVO();
				dspicVO.setDpic_seq(rs.getString("dpic_seq"));
				dspicVO.setDs_no(rs.getString("ds_no"));
				list.add(dspicVO);
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
		return list;
	}
	@Override
	public byte[] getDpic(String ds_no, int position) {
		byte[] dpic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try {
        	con = ds.getConnection();
			pstmt = con.prepareStatement(GET_DPIC_BY_DSNO, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, ds_no);
			rs = pstmt.executeQuery();
			
			if (position == -1) {
				rs.next();
				dpic = rs.getBytes("dpic");
			} else {
				rs.absolute(position + 1);
				dpic = rs.getBytes("dpic");
			}
			
			// Handle any driver errors
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
		return dpic;
	}

	@Override
	public int getDpicCount(String ds_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        int count = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PICCOUNT_BY_DSNO);

			pstmt.setString(1, ds_no);

			rs = pstmt.executeQuery();

			rs.next();
			
			count=rs.getInt("count");
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
		return count;
	}

}



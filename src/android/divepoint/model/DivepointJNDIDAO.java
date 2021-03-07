package android.divepoint.model;


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



public class DivepointJNDIDAO implements DivepointDAO_interface {
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



	private static final String INSERT_STMT = "INSERT INTO DIVE_POINT (dp_no,loc_no,dp_name,dp_lat,	dp_lng,	dp_info,dp_pic1,dp_pic2,dp_pic3,dp_pic4)\r\n"
			+ "VALUES ('DP'||LPAD(to_char(DP_SEQ.NEXTVAL),6,'0'),?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM dive_point ORDER BY dp_no";
	private static final String GET_ONE_STMT = "SELECT dp_no,loc_no,dp_name,dp_lat,	dp_lng,	dp_info,dp_pic1,dp_pic2,dp_pic3,dp_pic4 FROM dive_point WHERE dp_no = ?";
	private static final String DELETE = "DELETE FROM dive_point WHERE dp_no = ?";
	private static final String UPDATE = "UPDATE dive_point SET loc_no=?,dp_name=?,dp_lat=?,dp_lng=?,dp_info=?,dp_pic1=?,dp_pic2=?,dp_pic3=?,dp_pic4=? WHERE dp_no = ?";
    private static final String GET_ONE_PIC = "SELECT DP_PIC1 FROM DIVE_POINT WHERE DP_NO=?";
	private static final String GET_ALL_PIC = "SELECT DP_PIC1,DP_PIC2,DP_PIC3,DP_PIC4 FROM DIVE_POINT WHERE DP_NO = ?";
    
	
	public List<byte[]> getAllPic(String dp_no){
		List<byte[]> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(GET_ALL_PIC);
			pstmt.setString(1, dp_no);// set the primaryKey
			rs = pstmt.executeQuery();
			
			
			   	
			   list.add(rs.getBytes("dp_pic1"));
			   list.add(rs.getBytes("dp_pic2"));
			   list.add(rs.getBytes("dp_pic3"));
			   list.add(rs.getBytes("dp_pic4"));

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
	
	
	
	
	
	
	
	@Override
    public byte[] getOnePic(String dp_no) {
    	byte[] image = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(GET_ONE_PIC);
			pstmt.setString(1, dp_no);// set the primaryKey
			rs = pstmt.executeQuery();
			
			rs.next();
			image = rs.getBytes("dp_pic1");

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
		return image;
    	
    }
	
	
	@Override
	public void insert(DivepointVO divepointVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, divepointVO.getLoc_no());
			pstmt.setString(2, divepointVO.getDp_name());
			pstmt.setDouble(3, divepointVO.getDp_lat());
			pstmt.setDouble(4, divepointVO.getDp_lng());
			pstmt.setString(5, divepointVO.getDp_info());
			pstmt.setBytes(6, divepointVO.getDp_pic1());
			pstmt.setBytes(7, divepointVO.getDp_pic2());
			pstmt.setBytes(8, divepointVO.getDp_pic3());
			pstmt.setBytes(9, divepointVO.getDp_pic4());
			pstmt.executeUpdate();
		}

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
	public void update(DivepointVO divepointVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, divepointVO.getLoc_no());
			pstmt.setString(2, divepointVO.getDp_name());
			pstmt.setDouble(3, divepointVO.getDp_lat());
			pstmt.setDouble(4, divepointVO.getDp_lng());
			pstmt.setString(5, divepointVO.getDp_info());
			pstmt.setBytes(6, divepointVO.getDp_pic1());
			pstmt.setBytes(7, divepointVO.getDp_pic2());
			pstmt.setBytes(8, divepointVO.getDp_pic3());
			pstmt.setBytes(9, divepointVO.getDp_pic4());
			pstmt.setString(10, divepointVO.getDp_no());// set the primaryKey
			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			con.setAutoCommit(false);

			pstmt.setString(1, dp_no);// set the primaryKey
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);

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
	public DivepointVO findByPrimaryKey(String dp_no) {
		DivepointVO divepointVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, dp_no);// set the primaryKey
			rs = pstmt.executeQuery();
			while (rs.next()) {
				divepointVO = new DivepointVO();
				divepointVO.setDp_no(rs.getString("dp_no"));
				divepointVO.setLoc_no(rs.getString("loc_no"));
				divepointVO.setDp_name(rs.getString("dp_name"));
				divepointVO.setDp_lat(rs.getDouble("dp_lat"));
				divepointVO.setDp_lng(rs.getDouble("dp_lng"));
				divepointVO.setDp_info(rs.getString("dp_info"));
				divepointVO.setDp_pic1(rs.getBytes("dp_pic1"));
				divepointVO.setDp_pic2(rs.getBytes("dp_pic2"));
				divepointVO.setDp_pic3(rs.getBytes("dp_pic3"));
				divepointVO.setDp_pic4(rs.getBytes("dp_pic4"));

			}

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
		return divepointVO;
	}

	@Override
	public List<DivepointVO> getAll() {
		List<DivepointVO> list = new ArrayList<DivepointVO>();
		DivepointVO divepointVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				divepointVO = new DivepointVO();
				divepointVO.setDp_no(rs.getString("dp_no"));
				divepointVO.setLoc_no(rs.getString("loc_no"));
				divepointVO.setDp_name(rs.getString("dp_name"));
				divepointVO.setDp_lat(rs.getDouble("dp_lat"));
				divepointVO.setDp_lng(rs.getDouble("dp_lng"));
				divepointVO.setDp_info(rs.getString("dp_info"));
//				divepointVO.setDp_pic1(rs.getBytes("dp_pic1"));
//				divepointVO.setDp_pic2(rs.getBytes("dp_pic2"));
//				divepointVO.setDp_pic3(rs.getBytes("dp_pic3"));
//				divepointVO.setDp_pic4(rs.getBytes("dp_pic4"));
				list.add(divepointVO);
			}

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

	

	
	
	
	
	
	
	

}


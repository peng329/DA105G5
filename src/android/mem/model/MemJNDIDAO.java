package android.mem.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;

public class MemJNDIDAO implements MemDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MEM(MEM_NO,MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX,MEM_BD,MEM_MAIL,MEM_PHONE,MEM_ADD,MEM_PIC,REG_TIME,MEM_REP_NO,MEM_STATE) VALUES('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), ?, ?, ?, ?, ?, ?, ?, ?,EMPTY_BLOB(),?,?,?)";
	private static final String UPDATE_STMT = "UPDATE MEM SET MEM_NAME = ?, MEM_SEX = ?, MEM_BD = ? MEM_MAIL=? MEM_PHONE=? MEM_ADD=?  WHERE MEM_NO = ?";
//private static final String UPDATE_PSW_STMT = "UPDATE MEM SET MEM_PSW=? WHERE MEM_ID=? AND MEM_MAIL=?" ;
	private static final String FIND_BY_ID_PSW = "SELECT * FROM MEM WHERE MEM_ID = ? AND MEM_PSW = ?";
	private static final String FIND_PK_BY_ID = "SELECT * FROM MEM WHERE MEM_ID = ?";
	private static final String FIND_ALL = "SELECT * FROM MEM";
	private static final String FIND_NAME_BY_PK = "SELECT * FROM MEM WHERE MEM_NO = ?";
	private static final String CHECK_ID_EXIST = "SELECT MEM_ID FROM MEM WHERE MEM_ID = ?";
	private static final String FIND_PIC_BY_PK = "SELECT MEM_PIC FROM MEM WHERE MEM_NO=?";
    private static final String UPDATE_PIC = "UPDATE MEM SET MEM_PIC = ? WHERE MEM_NO=?";

	
    @Override
    public boolean updatePic(String mem_no,byte[] mem_pic) {
    	Connection con =null;
    	PreparedStatement pstmt = null;
    	boolean isPicUpdated = false;
    	try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PIC);
			pstmt.setString(2, mem_no);
			pstmt.setBytes(1, mem_pic);
			int count = pstmt.executeUpdate();
			
			isPicUpdated = count > 0? true:false;
		} catch (SQLException se) {
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
    	return isPicUpdated;
    }
    
    
    @Override
    public byte[] findOnePicByPk(String mem_no) {
    	byte[] mempic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try {
            con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_PIC_BY_PK);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				mempic = rs.getBytes("mem_pic");
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
		return mempic;
	}
    	
    	
    	
    	
    	
    

	@Override
	public boolean add(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean isAdded = false;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memVO.getMem_id());
			pstmt.setString(2, memVO.getMem_psw());
			pstmt.setString(3, memVO.getMem_name());
			pstmt.setInt(4, memVO.getMem_sex());
			pstmt.setDate(5, memVO.getMem_bd());
			pstmt.setString(6, memVO.getMem_mail());
			pstmt.setString(7, memVO.getMem_phone());
			pstmt.setString(8, memVO.getMem_add());
			pstmt.setTimestamp(9, memVO.getReg_time());
			pstmt.setInt(10, memVO.getMem_rep_no());
			pstmt.setString(11, memVO.getMem_state());
			int count = pstmt.executeUpdate();
			isAdded = count > 0 ? true : false;

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
		return isAdded;

	}

	@Override
	public boolean update(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean isUpdated = false;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, memVO.getMem_name());
			pstmt.setInt(2, memVO.getMem_sex());
			pstmt.setDate(3, memVO.getMem_bd());
			pstmt.setString(5, memVO.getMem_mail());
			pstmt.setString(6, memVO.getMem_phone());
			pstmt.setString(7, memVO.getMem_add());
            pstmt.setString(8, memVO.getMem_no());

			int count = pstmt.executeUpdate();
			isUpdated = count > 0 ? true : false;

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
		return isUpdated;
	}

	
	@Override
	public MemVO findOneByIdPsw(String mem_id, String mem_psw) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
            con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ID_PSW);
            pstmt.setString(1, mem_id);
            pstmt.setString(2, mem_psw);
            rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("mem_no"));
				memVO.setMem_id(rs.getString("mem_id"));
				memVO.setMem_psw(rs.getString("mem_psw"));
				memVO.setMem_name(rs.getString("mem_name"));
				memVO.setMem_sex(rs.getInt("mem_sex"));
				memVO.setMem_bd(rs.getDate("mem_bd"));
				memVO.setMem_mail(rs.getString("mem_mail"));
				memVO.setMem_phone(rs.getString("mem_phone"));
				memVO.setMem_add(rs.getString("mem_add"));
				memVO.setMem_pic(rs.getBytes("mem_pic"));
				memVO.setReg_time(rs.getTimestamp("reg_time"));
				memVO.setMem_rep_no(rs.getInt("mem_rep_no"));
				memVO.setMem_state(rs.getString("mem_state"));
			}
			
			
		}catch (SQLException se) {
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

		return memVO;
	}
	
	
	@Override
	public boolean isMember(String mem_id, String mem_psw) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean isMember = false;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(FIND_BY_ID_PSW);
			ps.setString(1, mem_id);
			ps.setString(2, mem_psw);
			ResultSet rs = ps.executeQuery();
			isMember = rs.next();
			return isMember;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isMember;
	}

	@Override
	public boolean delete(String mem_id) {
		// TODO Auto-generated method stub
		return false;
	}
    @Override
    public String findNameByPk(String mem_no) {
    	MemVO memVO = null;
    	String mem_name;
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_NAME_BY_PK);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("mem_no"));
				memVO.setMem_id(rs.getString("mem_id"));
				memVO.setMem_psw(rs.getString("mem_psw"));
				memVO.setMem_name(rs.getString("mem_name"));
				memVO.setMem_sex(rs.getInt("mem_sex"));
				memVO.setMem_bd(rs.getDate("mem_bd"));
				memVO.setMem_mail(rs.getString("mem_mail"));
				memVO.setMem_phone(rs.getString("mem_phone"));
				memVO.setMem_add(rs.getString("mem_add"));
				memVO.setMem_pic(rs.getBytes("mem_pic"));
				memVO.setReg_time(rs.getTimestamp("reg_time"));
				memVO.setMem_rep_no(rs.getInt("mem_rep_no"));
				memVO.setMem_state(rs.getString("mem_state"));
			}
			mem_name = memVO.getMem_name();
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
		return mem_name;
	}
		
    
	@Override
	public String findPkById(String mem_id) {
		String mem_no = null;
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
            con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_PK_BY_ID);
            pstmt.setString(1, mem_id);
            rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("mem_no"));
				memVO.setMem_id(rs.getString("mem_id"));
				memVO.setMem_psw(rs.getString("mem_psw"));
				memVO.setMem_name(rs.getString("mem_name"));
				memVO.setMem_sex(rs.getInt("mem_sex"));
				memVO.setMem_bd(rs.getDate("mem_bd"));
				memVO.setMem_mail(rs.getString("mem_mail"));
				memVO.setMem_phone(rs.getString("mem_phone"));
				memVO.setMem_add(rs.getString("mem_add"));
				memVO.setMem_pic(rs.getBytes("mem_pic"));
				memVO.setReg_time(rs.getTimestamp("reg_time"));
				memVO.setMem_rep_no(rs.getInt("mem_rep_no"));
				memVO.setMem_state(rs.getString("mem_state"));
			}
			mem_no = memVO.getMem_no();
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
		
		return mem_no;
	}
	
	
	@Override
	public boolean isMemIdExist(String mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean isUserIdExist = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_ID_EXIST);
			pstmt.setString(1, mem_id);
			ResultSet rs = pstmt.executeQuery();
			isUserIdExist = rs.next();
			return isUserIdExist;
		} catch (SQLException e) {
			e.printStackTrace();
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
		return isUserIdExist;
	}

	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<>();
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
            con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_ALL);
            rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("mem_no"));
				memVO.setMem_id(rs.getString("mem_id"));
				memVO.setMem_psw(rs.getString("mem_psw"));
				memVO.setMem_name(rs.getString("mem_name"));
				memVO.setMem_sex(rs.getInt("mem_sex"));
				memVO.setMem_bd(rs.getDate("mem_bd"));
				memVO.setMem_mail(rs.getString("mem_mail"));
				memVO.setMem_phone(rs.getString("mem_phone"));
				memVO.setMem_add(rs.getString("mem_add"));
//				memVO.setMem_pic(rs.getBytes("mem_pic"));
				memVO.setReg_time(rs.getTimestamp("reg_time"));
				memVO.setMem_rep_no(rs.getInt("mem_rep_no"));
				memVO.setMem_state(rs.getString("mem_state"));
				list.add(memVO);
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
		
		return list;
	}

	

	    
	    
	        

}



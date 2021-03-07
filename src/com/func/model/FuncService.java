package com.func.model;

import java.util.List;

public class FuncService {
	private FuncDAO_interface dao;

	public FuncService() {
	    dao = new FuncDAO();
	}

	public FuncVO addFunc(String fc_name) {
	    FuncVO funcVO = new FuncVO();

	    funcVO.setFc_name(fc_name);
	    dao.insert(funcVO);

	    return funcVO;
	}

	public FuncVO updateFunc(String fc_no, String fc_name) {
	    FuncVO funcVO = new FuncVO();

	    funcVO.setFc_no(fc_no);
	    funcVO.setFc_name(fc_name);
	    dao.update(funcVO);

	    return funcVO;
	}

	public void deleteFunc(String fc_no) {
	    dao.delete(fc_no);
	}

	public FuncVO getOneFunc(String fc_no) {
	    return dao.findByPrimaryKey(fc_no);
	}

	public List<FuncVO> getAll(){
	    return dao.getAll();
	}

}

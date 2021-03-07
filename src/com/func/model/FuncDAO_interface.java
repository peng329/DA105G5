package com.func.model;

import java.util.List;


//不能刪，只能改，改是為了新增打錯字用時
public interface FuncDAO_interface {
    public void insert(FuncVO funcVO);
    public void update(FuncVO funcVO);
    public void delete(String fc_no);
    public FuncVO findByPrimaryKey(String fc_no);
    public List<FuncVO> getAll();
    
    
    //查找這功能編號

}

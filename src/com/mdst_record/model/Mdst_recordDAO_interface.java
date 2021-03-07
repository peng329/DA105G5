package com.mdst_record.model;

import java.util.*;

public interface Mdst_recordDAO_interface {
    public void insert(Mdst_recordVO  mdst_recordVO) ;
    public void update(Mdst_recordVO  mdst_recordVO);
    public void delete(String mem_no,String ds_no);
    public Mdst_recordVO findByPrimaryKey(String mem_no,String ds_no);
    public List<Mdst_recordVO> getAll();
    
    //查找一位會員的所有文章追蹤
    public List<Mdst_recordVO> getMdstrsByMem_no(String mem_no);
}

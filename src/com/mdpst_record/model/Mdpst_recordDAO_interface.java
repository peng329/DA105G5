package com.mdpst_record.model;

import java.util.*;

import com.mdst_record.model.Mdst_recordVO;

public interface Mdpst_recordDAO_interface {
    public void insert(Mdpst_recordVO mdpst_recordVO);
    public void update(Mdpst_recordVO mdpst_recordVO);
    public void delete(String mem_no, String dp_no);
    public Mdpst_recordVO findByPrimaryKey(String mem_no, String dp_no);
    public List<Mdpst_recordVO> getAll();
    
    //查找一位會員的所有文章追蹤
    public List<Mdpst_recordVO> getMdpstrsByMem_no(String mem_no);
    
}

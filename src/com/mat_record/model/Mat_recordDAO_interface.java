package com.mat_record.model;

import java.util.*;



public interface Mat_recordDAO_interface {
    public void insert(Mat_recordVO mat_recordVO);
    public void update(Mat_recordVO mat_recordVO);
    public void delete(String mem_no, String mpo_no);
    public Mat_recordVO findByPrimaryKey(String mem_no, String mpo_no);
    public List<Mat_recordVO> getAll();
    
    //查找一位會員的所有文章追蹤
    public List<Mat_recordVO> getMatrsByMem_no(String mem_no);
}

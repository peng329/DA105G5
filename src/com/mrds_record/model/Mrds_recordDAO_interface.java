package com.mrds_record.model;

import java.util.List;

public interface Mrds_recordDAO_interface {
    public void insert(Mrds_recordVO  mrds_recordVO);
    public void update(Mrds_recordVO  mrds_recordVO);
    public void delete(String mrds_no);
    public Mrds_recordVO findByPrimaryKey(String mrds_record);
    public List<Mrds_recordVO>  getAll();
}

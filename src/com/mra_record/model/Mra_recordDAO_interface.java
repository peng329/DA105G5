package com.mra_record.model;
import java.util.*;

public interface Mra_recordDAO_interface {
    public void insert(Mra_recordVO mra_recordVO);
    public void update(Mra_recordVO mra_recordVO);
    public void delete(String rar_no);
    public Mra_recordVO findByPrimaryKey(String rar_no);
    public List<Mra_recordVO> getAll();
}

package com.nexmore.sims.excel.service;

import com.nexmore.sims.excel.vo.ExcelVO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ExcelService {
    // 엑셀 데이터 저장
    public ArrayList<ExcelVO> saveExcel(List<ExcelVO> list) throws SQLException;

}

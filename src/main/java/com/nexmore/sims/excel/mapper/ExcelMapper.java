package com.nexmore.sims.excel.mapper;

import com.nexmore.sims.excel.vo.ExcelVO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ExcelMapper {

    public int saveExcel(List<ExcelVO> list) throws SQLException;

    // 탱고 연동 테이블에 있는지 체크
    public int isTangoEqpId(String eqpId) throws SQLException;

    public int isIpAddress(String eqpId) throws SQLException;

    // 우리 DB에 있는지 체크
    public int isEqpId(String eqpId) throws SQLException;
}

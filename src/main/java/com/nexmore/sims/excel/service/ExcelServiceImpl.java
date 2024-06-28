package com.nexmore.sims.excel.service;

import com.nexmore.sims.excel.mapper.ExcelMapper;
import com.nexmore.sims.excel.vo.ExcelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService{

    private final ExcelMapper excelMapper;

    @Override
    public ArrayList<ExcelVO> saveExcel(List<ExcelVO> list) throws SQLException {

        ArrayList<ExcelVO> nonInsertList = new ArrayList<>();
        List<ExcelVO> insertList = new ArrayList<>();
        for (ExcelVO excelVO : list) {
            String eqpId = excelVO.getEQP_ID();

            int result = excelMapper.isEqpId(eqpId);

            if(result > 0){
                int tangoResult = excelMapper.isTangoEqpId(eqpId);
                if(tangoResult == 0){
                    excelVO.setResultCode(ExcelVO.ERROR_TYPE.NON_TANGO_EQP_ID);
                    nonInsertList.add(excelVO);
                }else{
                    int ipCheckResult = excelMapper.isIpAddress(eqpId);

                    if(ipCheckResult > 0){
                        insertList.add(excelVO);
                    }else{
                        excelVO.setResultCode(ExcelVO.ERROR_TYPE.NON_IP_ADDRESS);
                        nonInsertList.add(excelVO);
                    }
                }
            }else{
                excelVO.setResultCode(ExcelVO.ERROR_TYPE.NON_EQP_ID);
                nonInsertList.add(excelVO);
            }
        }

        excelMapper.saveExcel(insertList);

        return nonInsertList;
    }

}

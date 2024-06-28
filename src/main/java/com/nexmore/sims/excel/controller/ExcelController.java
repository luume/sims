package com.nexmore.sims.excel.controller;


import com.nexmore.sims.excel.service.ExcelService;
import com.nexmore.sims.excel.vo.ExcelVO;
import com.nexmore.sims.util.PoiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/index")
    public String excelTest(){
        return "/excel/simsExcel";
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> downloadExcel() throws IOException {
        String fileDirectory = "C:\\sims\\excel_file";
        String fileName = "excel_sample.xlsx"; // 다운로드할 파일의 이름

        Path filePath = Paths.get(fileDirectory).resolve(fileName).normalize();
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            throw new RuntimeException("파일을 찾을수 없습니다. " + fileName);
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/excel/fileUpload")
    public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file){

        if (file.isEmpty()) {
            return new ResponseEntity<>("업로드할 파일을 선택해주세요.", HttpStatus.BAD_REQUEST);
        }
        ArrayList<ExcelVO> nonInsertList = new ArrayList<>();
        try{
            List<ExcelVO> list = PoiUtil.parseExcel(file.getBytes());

            nonInsertList = excelService.saveExcel(list);


        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(nonInsertList, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(!nonInsertList.isEmpty()) return ResponseEntity.status(200).body(nonInsertList);

        return new ResponseEntity<>("파일 업로드 성공!", HttpStatus.OK);
    }
}

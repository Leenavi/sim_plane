package com.simplane.controller;

import com.simplane.domain.AttachFileDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class UploadController {

    // /uploadForm Get요청시 uploadForm.jsp로 이동
    @GetMapping("/uploadForm")
    public void uploadForm() {
        log.info("uploadForm.......");
    }

    // post방식으로 /uploadForm 요청 들어올때 폼 방식 파일 업로드 처리
    @PostMapping("/uploadForm")
    public void uploadFormPost(MultipartFile[] uploadFile, Model model, MultipartRequest multipartRequest){

        String uploadFolder = "c:\\upload";

        for(MultipartFile file : uploadFile){ //배열로 넘어온 파일들을 하나씩 반복 처리
            log.info("----------------------");
            log.info("Upload File Name : " + file.getOriginalFilename());
            log.info("Upload File Size : " + file.getSize());

            File savedFile = new File(uploadFolder, file.getOriginalFilename()); //저장할 파일 객체 생성

            try{
                file.transferTo(savedFile);
            }catch(IllegalStateException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //오늘 날짜 경로 생성 메소드
    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();
        String str = sdf.format(date); //2025-06-30
        return str.replace("-", "/"); //윈도우 : 2025-06-30 -> 2025\06\30
    }

    //이미지 파일인지 확인하는 메소드
    private boolean checkImageType(File file){

        try{
            String contentType = Files.probeContentType(file.toPath());//파일 MIME 타입 확인
            return contentType.startsWith("image"); //이미지 타입이면 true
        }catch(IOException e){
            e.printStackTrace();
        }
        return false; // 이미지가 아니면(예외발생시) false
    }
                                                //형식을 알 수 없는 모든 종류의 파일에 사용할 수 있는 기본값 MIME(다운로드 시)
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(String fileName){

        FileSystemResource resource = new FileSystemResource("c:\\upload\\" + fileName);

        String resourceName = resource.getFilename();
        log.info("resourceName >> " + resourceName);

        //UUID 제외한 원본 파일명 추출
        String downloadName = resourceName.substring(resourceName.indexOf("_")+1);
        log.info("downLoadName >> " + downloadName);

        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downloadName, "utf-8"));
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);//파일과 헤더 포함 응답 반환 
    }

    @GetMapping("/display") //이미지 썸네일 표시 요청 처리
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName){
        File file = new File("c:\\upload\\"+ fileName);
        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders header = new HttpHeaders();
            header.add("content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch(IOException e){
            e.printStackTrace();
        }
        return result; //MIME 타입을 포함한 바이너리 데이터로 응답(상태코드 포함)
    }

    //파일 삭제 처리
    @PostMapping(value = "/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName, String type) {
        File file;

        try{
            file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "utf-8"));
            file.delete();

            if(type.equals("image")){
                String largeFileName= file.getAbsolutePath().replace("s_", "");
                log.info("largeFileName >> " + largeFileName);

                file = new File(largeFileName);
                file.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    @GetMapping("/uploadAjax")
    public void uploadAjax() {
        log.info("uploadAjax.......");
    }

    // Ajax 업로드 처리
    @PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDTO>> uploadAjaxAction(MultipartFile[] uploadFile, Model model) {

        List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();

        String uploadFolder = "c:\\upload";
        String uploadFolderPath = getFolder(); // 오늘날짜가 들어감 //2025\\05\\27

        File uploadPath = new File(uploadFolder, uploadFolderPath); //날짜이름으로 폴더 만들어서 uploadPath가 가지고 있으니까

        if(uploadPath.exists() == false) { //폴더 생성
            uploadPath.mkdirs();
        }

        for(MultipartFile multipartFile : uploadFile) {
//			log.info("------------------------------");
//			log.info("Upload File Name : " + multipartFile.getOriginalFilename()); //파일이름 알 수 있음
//			log.info("Upload File size : " + multipartFile.getSize());

            AttachFileDTO attachFileDTO = new AttachFileDTO();

            String uploadFileName = multipartFile.getOriginalFilename();

            UUID uuid = UUID.randomUUID();

            uploadFileName = uuid.toString() + "_" + uploadFileName;

            attachFileDTO.setUuid(uuid.toString());
            attachFileDTO.setFileName(multipartFile.getOriginalFilename()); //원본파일명
            attachFileDTO.setUploadPath(uploadFolderPath);

            try {

                File savedFile = new File(uploadPath, uploadFileName);
                multipartFile.transferTo(savedFile);

                //썸네일 파일 생성 - 썸네일 만들어주는 코드가 들어감// 원본이랑, s_가 붙은 썸네일 하나 더 만들어짐
                if(checkImageType(savedFile)) {

                    attachFileDTO.setImage(true);

                    FileOutputStream thumbnail = new FileOutputStream(
                            new File(uploadPath, "s_" + uploadFileName)); //썸네일엔 s가 하나 더 붙는거

                    Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);

                    thumbnail.close(); // 삭제하라는뜻
                }
                list.add(attachFileDTO); //list에 추가

            }catch(IllegalStateException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }

        } //end for

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 구버전 Ajax 업로드 처리
    @PostMapping("/uploadAjaxAction_old")
    public @ResponseBody String uploadAjaxAction_old(MultipartFile[] uploadFile, Model model) {

        String uploadFolder = "c:\\upload";

        File uploadPath = new File(uploadFolder, getFolder());

        for(MultipartFile multipartFile : uploadFile) {
            log.info("------------------------------");
            log.info("Upload File Name : " + multipartFile.getOriginalFilename()); //파일이름 알 수 있음
            log.info("Upload File size : " + multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();

            File savedFile = new File(uploadPath, uploadFileName);

            try {
                multipartFile.transferTo(savedFile);
            }catch(IllegalStateException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "success";
    }


}




























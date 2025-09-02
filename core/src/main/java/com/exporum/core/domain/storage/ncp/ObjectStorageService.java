package com.exporum.core.domain.storage.ncp;

import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.model.FileInfo;
import com.exporum.core.exception.FileNotFoundException;
import com.exporum.core.exception.OperationFailException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectStorageService {

    private final HttpServletRequest request;

    private final S3Client s3Client;

    @Value("${ncp.object-storage.bucket}")
    private String bucket;

    @Value("${resource.storage.path.upload}")
    private String uploadPath;

    @Value("${resource.storage.path.download}")
    private String downloadPath;

    @Value("${ncp.acl.public}")
    private String aclPublic;


    public void upload(MultipartFile file, FileDTO fileDTO) {
        try {


            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileDTO.getFilePath())
                    .contentType(fileDTO.getMimeType())
                    .acl(aclPublic)
                    .build();

            // RequestBody로 파일 내용 전달
            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

            // 파일 업로드
            s3Client.putObject(putObjectRequest, requestBody);
        }catch (S3Exception e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void upload(File file, FileDTO fileDTO) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileDTO.getFilePath())
                    .contentType(fileDTO.getMimeType())
                    .acl(aclPublic)
                    .build();

            // RequestBody로 파일 내용 전달
            RequestBody requestBody = RequestBody.fromFile(file);

            // 파일 업로드
            s3Client.putObject(putObjectRequest, requestBody);

            // 업로드 성공 후 파일 삭제
            if (file.delete()) {
                log.info("File uploaded and deleted successfully: " + file.getAbsolutePath());
            } else {
                log.info("File uploaded but failed to delete: " + file.getAbsolutePath());
            }
        }catch (S3Exception e){
            e.printStackTrace();
        }
    }


    public ResponseEntity<StreamingResponseBody> download(FileInfo fileInfo) {
        Path path = Paths.get(STR."\{downloadPath}/\{fileInfo.getFileName()}");
        log.info("Download file {} processing", path.toString());
        try{
            Files.deleteIfExists(path);

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileInfo.getFilePath())
                    .build();

            s3Client.getObject(getObjectRequest, ResponseTransformer.toFile(Paths.get(path.toString())) );
            log.info("Download file {} successfully", path.toString());


            String contentType = Optional.ofNullable(request.getServletContext().getMimeType(path.toString()))
                    .orElse("application/octet-stream");

            StreamingResponseBody responseBody = outputStream -> this.streamFileAndDelete(path, outputStream);

            String fileName = URLEncoder.encode(fileInfo.getOriginFileName(), StandardCharsets.UTF_8).replaceAll("\\+", "%20");;

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + fileName)
                    .body(responseBody);


        }catch (Exception e){
            log.info(e.toString());
            e.printStackTrace();
            throw new OperationFailException("Failed to download S3 object");
        }
    }

    private void streamFileAndDelete(Path path, OutputStream outputStream) throws FileNotFoundException {
            try (InputStream inputStream = Files.newInputStream(path)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            } catch (IOException e){
                throw new FileNotFoundException("File not found: " + path.toString());
            } finally {
                deleteFile(path);
            }
    }


    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
            log.info("Deleted temporary file: {}", path);
        } catch (IOException e) {
            log.warn("Failed to delete temporary file: {}", path, e);
        }
    }



}

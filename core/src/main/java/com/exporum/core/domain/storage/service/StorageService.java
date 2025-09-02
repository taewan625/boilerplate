package com.exporum.core.domain.storage.service;

import com.exporum.core.domain.storage.mapper.StorageMapper;
import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.model.FileInfo;
import com.exporum.core.domain.storage.ncp.ObjectStorageService;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.FileNotFoundException;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageMapper storageMapper;

    private final ObjectStorageService objectStorageService;

    @Value("${resource.storage.path.download}")
    private String downloadPath;

    public void downloadFile(String fileUrl, String fileName) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 저장할 경로 설정
            Path targetPath = Paths.get(downloadPath, fileName);

            // 스트림을 받아서 파일로 저장
            try (InputStream inputStream = httpConn.getInputStream()) {
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            throw new OperationFailException("storage.file.download.error");
        }
        httpConn.disconnect();
    }


    @Transactional
    public FileDTO ncpUpload(MultipartFile file, String path) {
        if (file.isEmpty() || file == null) {
            throw new FileNotFoundException();
        }

        String uuid = UUID.randomUUID().toString();
        String originalName = Optional.ofNullable(file.getOriginalFilename())
                .orElseThrow(() -> new FileNotFoundException("File name is missing"));
        String extension = originalName.contains(".") ?
                originalName.substring(originalName.lastIndexOf(".") + 1) : "";

        // Build file path and name
        String fileName = String.format("%s.%s", uuid, extension);
        String filePath = String.format("%s%s", path, fileName);

       this.validateMimeType(file);

        FileDTO fileDTO = FileDTO.builder()
                .mimeType(file.getContentType())
                .uuid(uuid)
                .fileName(fileName)
                .originFileName(originalName)
                .ext(extension)
                .fileSize(file.getSize())
                .filePath(filePath)
                .build();

        this.saveFileMetadata(fileDTO);

        objectStorageService.upload(file, fileDTO);
        return fileDTO;
    }


    @Transactional
    public FileDTO ncpUpload(File file, String path) {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException("File not found.");
        }

        String uuid = UUID.randomUUID().toString();
        String originalName = Optional.ofNullable(file.getName())
                .orElseThrow(() -> new FileNotFoundException("File name is missing"));
        String extension = originalName.contains(".") ?
                originalName.substring(originalName.lastIndexOf(".") + 1) : "";

        // Build file path and name
        String fileName = String.format("%s.%s", uuid, extension);
        String filePath = String.format("%s%s", path, fileName);


        FileDTO fileDTO = FileDTO.builder()
                .mimeType(this.detectMimeType(file))
                .uuid(uuid)
                .fileName(fileName)
                .originFileName(originalName)
                .ext(extension)
                .fileSize(file.length())
                .filePath(filePath)
                .build();

        this.saveFileMetadata(fileDTO);

        objectStorageService.upload(file, fileDTO);
        return fileDTO;
    }




    public ResponseEntity<StreamingResponseBody> ncpDownload(String uuid) throws IOException {
        return objectStorageService.download(getFileInfo(uuid));
    }

    private void saveFileMetadata(FileDTO fileDTO) {
        try {
            this.insertFiles(fileDTO);
        } catch (Exception e) {
            throw new OperationFailException("Insert File Invalid MIME type detected");
        }
    }

    private String detectMimeType(File file) {
        try {
            Tika tika = new Tika();
            return tika.detect(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to detect MIME type", e);
        }
    }


    private void validateMimeType(MultipartFile file) {
        Tika tika = new Tika();
        try {
            String detectedMimeType = tika.detect(file.getInputStream());
            if (!file.getContentType().equals(detectedMimeType)) {
                throw new OperationFailException("Invalid MIME type detected");
            }
        } catch (IOException e) {
            throw new OperationFailException("Failed to detect MIME type");
        }
    }

    private void insertFiles(FileDTO fileDTO) {
        if(!(storageMapper.insertFiles(fileDTO) > 0)) {
            throw new OperationFailException();
        }
    }

    private FileInfo getFileInfo(String uuid) throws DataNotFoundException {
        return Optional.ofNullable(storageMapper.getFileByUUID(uuid)).orElseThrow(FileNotFoundException::new);
    }





}

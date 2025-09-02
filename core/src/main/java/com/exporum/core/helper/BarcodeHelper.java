package com.exporum.core.helper;

import com.exporum.core.exception.OperationFailException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */

@Component
public class BarcodeHelper {

    @Value("${resource.storage.path.upload}")
    private String uploadPath;

    public File getBarcode(){

        try {
            int barcodeWidth = 300; // 바코드 너비
            int barcodeHeight = 100; // 바코드 높이
            int textHeight = 20; // 텍스트 영역 높이

            String barcode = String.valueOf(System.currentTimeMillis());
            BitMatrix bitMatrix = new Code128Writer().encode(barcode, BarcodeFormat.CODE_128, barcodeWidth, barcodeHeight);

            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // 2. 텍스트를 포함한 최종 이미지 생성
            int finalImageHeight = barcodeHeight + textHeight;
            BufferedImage finalImage = new BufferedImage(barcodeWidth, finalImageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = finalImage.createGraphics();

            // 배경 흰색으로 설정
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, barcodeWidth, finalImageHeight);

            // 바코드 이미지 추가
            g2d.drawImage(barcodeImage, 0, 0, null);

            // 텍스트 스타일 설정
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));

            // 텍스트 위치 계산
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(barcode);
            int textX = (barcodeWidth - textWidth) / 2; // 텍스트 중앙 정렬
            int textY = barcodeHeight + fontMetrics.getAscent();

            // 텍스트 추가
            g2d.drawString(barcode, textX, textY);

            g2d.dispose();

            // 3. 최종 이미지 저장
            Path outputPath = Paths.get(uploadPath, STR."\{barcode}.png");
            ImageIO.write(finalImage, "png", outputPath.toFile());

            // 저장된 파일을 MultipartFile로 변환
            return outputPath.toFile();

        }catch (Exception e){
            e.printStackTrace();
            throw new OperationFailException("failed to generate barcode");
        }

    }

}

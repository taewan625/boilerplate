//package com.exporum.helper;
//
//import com.exporum.core.helper.BarcodeHelper;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.oned.Code128Writer;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Date;
//
///**
// * @author: Lee Hyunseung
// * @date : 2025. 1. 20.
// * @description :
// */
//
//@SpringBootTest(classes = BarcodeHelper.class)
//public class BarcodeHelperTest {
//
//
//    @Test
//    void barcodeTest() {
//        String uploadPath ="/Users/leehyunseung/Desktop/files/temp/woc/upload";
//        String code = "mid-240429135549-dlcg";
//        String exhibitionCode = "68494";
//
//        int barcodeWidth = 300; // 바코드 너비
//        int barcodeHeight = 142; // 바코드 높이
//        int textHeight = 20; // 텍스트 영역 높이
//
//        String barcode = String.valueOf(System.currentTimeMillis());
//
//        long start = System.currentTimeMillis();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        String formattedTime = formatter.format(new Date(start));
//        System.out.println(formattedTime);
//
//        try {
//            //BitMatrix bitMatrix = new Code128Writer().encode(exhibitionCode+String.format(code), BarcodeFormat.CODE_128, 300, 120);
//            //Path path = Paths.get(uploadPath, STR."\{code}.png");
//
//            BitMatrix bitMatrix = new Code128Writer().encode(barcode, BarcodeFormat.CODE_128, barcodeWidth, barcodeHeight);
//
//            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//
////            ADd20240130134122
//            // 2. 텍스트를 포함한 최종 이미지 생성
//            int finalImageWidth = barcodeWidth;
//            int finalImageHeight = barcodeHeight + textHeight;
//            BufferedImage finalImage = new BufferedImage(finalImageWidth, finalImageHeight, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g2d = finalImage.createGraphics();
//
//
//            Path path = Paths.get(uploadPath, STR."\{barcode}.png");
//
//            // 배경 흰색으로 설정
//            g2d.setColor(Color.WHITE);
//            g2d.fillRect(0, 0, finalImageWidth, finalImageHeight);
//
//            // 바코드 이미지 추가
//            g2d.drawImage(barcodeImage, 0, 0, null);
//
//            // 텍스트 스타일 설정
//            g2d.setColor(Color.BLACK);
//            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
//
//            // 텍스트 위치 계산
//            FontMetrics fontMetrics = g2d.getFontMetrics();
//            int textWidth = fontMetrics.stringWidth(barcode);
//            int textX = (finalImageWidth - textWidth) / 2; // 텍스트 중앙 정렬
//            int textY = barcodeHeight + fontMetrics.getAscent();
//
//            // 텍스트 추가
//            g2d.drawString(barcode, textX, textY);
//
//            g2d.dispose();
//
//            // 3. 최종 이미지 저장
//            Path outputPath = Paths.get(uploadPath, STR."\{barcode}.png");
//            ImageIO.write(finalImage, "png", outputPath.toFile());
//
//
//
//            //MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//
//
//
//    }
//}

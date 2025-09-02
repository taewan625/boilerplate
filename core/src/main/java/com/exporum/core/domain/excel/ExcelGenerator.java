package com.exporum.core.domain.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public interface ExcelGenerator<T> {

    void write(OutputStream outputStream) throws IOException;

    void addRows(List<T> rows);

}
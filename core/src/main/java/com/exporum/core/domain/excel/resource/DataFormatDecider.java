package com.exporum.core.domain.excel.resource;

import org.apache.poi.ss.usermodel.DataFormat;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public interface DataFormatDecider {
    short getDataFormat(DataFormat dataFormat, Class<?> type);
}

package com.exporum.admin.domain.dashbord.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 25.
 * @description :
 */


@Getter
@Setter
public class TotalPlatformCount {
    public String groupedReferer;
    public int totalVisitCount;
}

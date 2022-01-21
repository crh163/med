package com.bootdo.common.domain.page;

import lombok.Data;

/**
 * @author rory.chen
 * @date 2021-01-07 14:39
 */
@Data
public class PageDto {

    Long currentPage;

    Long pageSize;

    Long totalPages;

    Long totalRows;
}

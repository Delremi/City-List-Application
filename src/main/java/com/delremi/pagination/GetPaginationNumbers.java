package com.delremi.pagination;

import com.delremi.model.City;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetPaginationNumbers {

    public List<PaginationButton> execute(Page<City> currentPage) {
        List<PaginationButton> pageNumbers = new ArrayList<>();
        int currentPageNumber = currentPage.getNumber();
        int totalPages = currentPage.getTotalPages();
        if (currentPageNumber > 1) {
            pageNumbers.add(PaginationButton.of(0));
        }
        if (currentPageNumber > 2) {
            if (currentPageNumber > 3) {
                pageNumbers.add(new PaginationDisabledButton());
            } else {
                pageNumbers.add(PaginationButton.of(1));
            }
        }
        if (currentPageNumber > 0) {
            pageNumbers.add(PaginationButton.of(currentPageNumber-1));
        }
        pageNumbers.add(PaginationButton.of(currentPageNumber));
        if (currentPageNumber < totalPages-1) {
            pageNumbers.add(PaginationButton.of(currentPageNumber+1));
        }
        if (currentPageNumber < totalPages-3) {
            if (currentPageNumber < totalPages-4) {
                pageNumbers.add(new PaginationDisabledButton());
            } else {
                pageNumbers.add(PaginationButton.of(totalPages-2));
            }
        }
        if (currentPageNumber < totalPages-2) {
            pageNumbers.add(PaginationButton.of(totalPages-1));
        }
        return pageNumbers;
    }
}

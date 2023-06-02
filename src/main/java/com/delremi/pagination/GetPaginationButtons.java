package com.delremi.pagination;

import com.delremi.model.City;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
public class GetPaginationButtons {

    public List<PaginationButton> execute(Page<City> currentPage) {
        var nPages = currentPage.getTotalPages();
        if (nPages <= 1) {
            return emptyList();
        }

        var page = currentPage.getNumber();
        var lastPage = nPages - 1;

        var buttons = IntStream.rangeClosed(max(0, page - 3), min(page + 3, lastPage))
                .boxed()
                .map(PaginationButton::of)
                .collect(toList());


        var firstButton = buttons.get(0);
        if (firstButton.getPageNumber() != 0) {
            firstButton.setPageNumber(0);
            buttons.get(1).disable();
        }

        var lastButton = buttons.get(buttons.size() - 1);
        if (lastButton.getPageNumber() != lastPage) {
            lastButton.setPageNumber(lastPage);
            buttons.get(buttons.size() - 2).disable();
        }

        return buttons;
    }
}

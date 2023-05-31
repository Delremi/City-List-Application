package com.delremi.pagination;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PaginationButton {
    private int pageNumber;

    public Optional<Integer> getPageNumber() {
        return Optional.of(pageNumber);
    }

    public boolean isEnabled() {
        return true;
    }
}


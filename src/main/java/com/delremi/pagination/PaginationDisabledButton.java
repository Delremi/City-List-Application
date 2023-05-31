package com.delremi.pagination;

import java.util.Optional;

public class PaginationDisabledButton extends PaginationButton {

    @Override
    public Optional<Integer> getPageNumber() {
        return Optional.empty();
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

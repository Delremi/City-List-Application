package com.delremi.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PaginationButton {
    private Integer pageNumber;

    public boolean isEnabled() {
        return this.pageNumber != null;
    }

    public void disable() {
        this.pageNumber = null;
    }
}


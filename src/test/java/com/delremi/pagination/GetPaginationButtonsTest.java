package com.delremi.pagination;

import com.delremi.model.City;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class GetPaginationButtonsTest {

    private final GetPaginationButtons getPaginationButtons = new GetPaginationButtons();

    @Test
    void execute_shouldGetSequentialButtons() {
        // given
        var page = buildPage(3, 7);

        // when
        var buttons = getPaginationButtons.execute(page);

        // then
        assertThat(buttons.size()).isEqualTo(7);
        for (int i = 0; i <= 6; i++) {
            var button = buttons.get(i);
            assertThat(button.isEnabled()).isTrue();
            assertThat(button.getPageNumber()).isEqualTo(i);
        }
    }

    @Test
    void execute_shouldDisableSecondAndSecondToLastButtons() {
        // given
        var nPages = 9;
        var page = buildPage(4, nPages);

        // when
        var buttons = getPaginationButtons.execute(page);

        // then
        assertThat(buttons.size()).isEqualTo(7);
        assertFirstAndLastButtons(buttons, nPages);
        assertThat(buttons.get(1).isEnabled()).isFalse();
        assertThat(buttons.get(buttons.size()-2).isEnabled()).isFalse();
        for (int i = 2; i <= 4; i++) {
            var button = buttons.get(i);
            assertThat(button.isEnabled()).isTrue();
            assertThat(button.getPageNumber()).isEqualTo(i+1);
        }
    }

    private void assertFirstAndLastButtons(List<PaginationButton> buttons, int nPages) {
        assertThat(buttons.get(0).getPageNumber()).isEqualTo(0);
        assertThat(buttons.get(buttons.size()-1).getPageNumber()).isEqualTo(nPages-1);
    }

    private Page<City> buildPage(int page, int pages) {
        return new PageImpl<>(emptyList(), PageRequest.of(page, 1), pages);
    }
}
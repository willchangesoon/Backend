package com.es3.order.common.pagination;

import java.util.List;

public record CursorPageResponse<T>(List<T> content, Long nextCursor, boolean hasNext) {

    public static <T> CursorPageResponse<T> of(List<T> content, int pageSize) {
        Long nextCursor = null;
        boolean hasNext = false;

        if (content.size() == pageSize) {
            hasNext = true;
            if (content.get(content.size() - 1) instanceof Identifiable) {
                nextCursor = ((Identifiable) content.get(content.size() - 1)).getId();
            }
        }

        return new CursorPageResponse<>(content, nextCursor, hasNext);
    }
}


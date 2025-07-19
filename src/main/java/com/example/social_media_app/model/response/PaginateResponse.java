package com.example.social_media_app.model.response;

import java.util.List;

public record PaginateResponse<T>(
        List<T> content,
        int currentPage,
        int totalPages,
        Long totalItems,
        boolean hasNext,
        boolean hasPrevious,
        String nextPageUrl,
        String previousPageUrl

) {

}

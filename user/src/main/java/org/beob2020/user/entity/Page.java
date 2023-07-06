package org.beob2020.user.entity;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Page<T> {
    private final int page;
    private final int size;
    private final long pageCount;
    private final List<T> content;
}

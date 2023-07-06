package org.beob2020.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class EntityPage<T> {
    private final List<T> content;
    private final long page;
    private final long pageCount;
    private final long totalElements;
}

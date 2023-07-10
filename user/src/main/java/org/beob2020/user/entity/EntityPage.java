package org.beob2020.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class EntityPage<T> {
    private final Set<T> content;
    private final long page;
    private final long pageCount;
    private final long totalElements;
}

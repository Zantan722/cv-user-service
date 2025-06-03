package com.neutec.blog.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@JsonPropertyOrder({"total", "list", "skip", "limit"})
public class Page<E> {

    private long total;

    private long skip;

    private int limit;

    private List<E> list;

    private Page(long total, @Nullable List<E> list, long skip, int limit) {
        this.total = total;
        this.skip = skip;
        this.limit = limit;
        this.list = Optional.ofNullable(list).orElseGet(ArrayList::new);
    }

    public static <E> Page<E> of(long total, @Nullable List<E> list, long skip, int limit) {
        return new Page<>(total, list, skip, limit);
    }
}

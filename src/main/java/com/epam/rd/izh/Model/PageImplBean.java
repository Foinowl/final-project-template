package com.epam.rd.izh.Model;


import com.epam.rd.izh.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class PageImplBean<T> extends PageImpl<T> {
    private static final long serialVersionUID = 1L;
    private int number;
    private int size;
    private int totalPages;
    private int numberOfElements;
    private long totalElements;
    private boolean previousPage;
    private boolean first;
    private boolean nextPage;
    private boolean last;
    private List<T> content;
    @JsonIgnore
    private Sort sort;

    public PageImplBean(List<UserDto> list, Pageable pageable, int count) {
        super(new ArrayList<T>());
    }

    public PageImplBean(Page pagina){
        super(new ArrayList<T>());
        this.number = pagina.getNumber();
        this.size = pagina.getSize();
        this.totalPages = pagina.getTotalPages();
        this.numberOfElements = pagina.getNumberOfElements();
        this.totalElements = pagina.getTotalElements();
        this.previousPage = pagina.hasPrevious();
        this.first = pagina.isFirst();
        this.nextPage = pagina.hasNext();
        this.last = pagina.isLast();
        this.content = pagina.getContent();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(boolean previousPage) {
        this.previousPage = previousPage;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "PageImplBean{" +
                "number=" + number +
                ", size=" + size +
                ", totalPages=" + totalPages +
                ", numberOfElements=" + numberOfElements +
                ", totalElements=" + totalElements +
                ", previousPage=" + previousPage +
                ", first=" + first +
                ", nextPage=" + nextPage +
                ", last=" + last +
                ", content=" + content +
                ", sort=" + sort +
                '}';
    }
}

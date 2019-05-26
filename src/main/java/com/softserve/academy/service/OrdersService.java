package com.softserve.academy.service;

public interface OrdersService {
    boolean orderCopy(String copyId, String readerId, String bookId, int creatorId)
        throws IllegalArgumentException;
}

package org.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class Room {
    private int roomId;
    private List<Integer> userIds;
}

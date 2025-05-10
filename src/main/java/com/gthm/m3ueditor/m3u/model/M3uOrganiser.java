package com.gthm.m3ueditor.m3u.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class M3uOrganiser {


    M3uType type;
    String groupName;
    List<M3UEntry> m3uList;


}
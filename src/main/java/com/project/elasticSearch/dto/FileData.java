package com.project.elasticSearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FileData {
  private String path;
  private byte[] file;
}
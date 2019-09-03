package com.yourl.mapper;

import com.yourl.controller.dto.ShortenUrlRequest;
import com.yourl.controller.dto.UrlResponseDto;
import com.yourl.domain.UrlEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UrlMapper {

    UrlMapper INSTANCE = Mappers.getMapper(UrlMapper.class);

    UrlResponseDto urlEntityToUrlResponseDto(UrlEntity urlEntity);
    List<UrlResponseDto> urlEntitiesToUrlResponseDTOs(List<UrlEntity> urlEntities);

    ShortenUrlRequest shortenUrlRequestToUrlEntity(ShortenUrlRequest shortenUrlRequest);
    List<ShortenUrlRequest> shortenUrlRequestToUrlEntity(List<ShortenUrlRequest> shortenUrlRequest);

}

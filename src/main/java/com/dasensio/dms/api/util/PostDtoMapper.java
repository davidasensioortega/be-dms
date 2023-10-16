package com.dasensio.dms.api.util;

import com.dasensio.dms.api.dto.PostDto;
import com.dasensio.dms.domain.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostDtoMapper {

    PostDto toPostDto(Post post);

}
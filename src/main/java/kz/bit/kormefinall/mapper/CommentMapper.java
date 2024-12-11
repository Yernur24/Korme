package kz.bit.kormefinall.mapper;

import kz.bit.kormefinall.dto.CommentDTO;
import kz.bit.kormefinall.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.fullName", target = "authorName")
    @Mapping(source = "product.id", target = "productId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "productId", target = "product.id")
    Comment toModel(CommentDTO commentDTO);

    List<CommentDTO> toDtoList(List<Comment> comments);
    List<Comment> toModelList(List<CommentDTO> commentDTOs);
}

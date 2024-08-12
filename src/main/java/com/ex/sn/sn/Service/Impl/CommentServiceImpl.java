package com.ex.sn.sn.Service.Impl;

import com.cloudinary.utils.StringUtils;
import com.ex.sn.sn.DTO.Comment.CommentReqPostDto;
import com.ex.sn.sn.DTO.Comment.CommentReqPutDto;
import com.ex.sn.sn.DTO.Comment.CommentResDto;
import com.ex.sn.sn.DTO.FileUpload.FileResDto;
import com.ex.sn.sn.DTO.Utils.Comment.CommentResponseUtils;
import com.ex.sn.sn.Entity.Comment;
import com.ex.sn.sn.Entity.FileUpload;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Entity.User;
import com.ex.sn.sn.Exception.AppException;
import com.ex.sn.sn.Exception.ErrorCode;
import com.ex.sn.sn.Repository.CommentRepository;
import com.ex.sn.sn.Repository.PostRepository;
import com.ex.sn.sn.Repository.UserRepository;
import com.ex.sn.sn.Service.CommentService;
import com.ex.sn.sn.Service.FileService;
import com.ex.sn.sn.Service.PostService;
import com.ex.sn.sn.Utils.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private  final PostRepository  postRepository;
    private  final UserRepository  userRepository;
    private  final FileService fileService;
    private final PostService postService;
    private final CommentResponseUtils commentResponseUtils;

    /**
     * create comment
     */
    @Override
    public CommentResDto createComment(Long postId, CommentReqPostDto reqDto, MultipartFile files) {

        if (StringUtils.isBlank(reqDto.getContent()) && files == null) {
            throw new AppException(ErrorCode.COMMENT_UPLOAD_WRONG);
        }

        Post post = postRepository.findOneById(postId);

        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

        if (post == null || !postService.checkRightAccessPost(post, user)) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }

        Comment comment = new Comment();
        comment.setContent(reqDto.getContent());
        comment.setUser(user);
        comment.setPost(post);

        Comment fixedComment = commentRepository.save(comment);
        if (files != null) {
            MultipartFile[] file = { files };
            List<FileResDto> imageResDto = fileService.uploadFile(file);
            if (imageResDto.size() > 0) {
                FileUpload fileE = fileService.findOneById(imageResDto.get(0).getId());
                fileE.setComment(comment);
                fileService.save(fileE);
                fixedComment.setFileUpload(fileE);
            }
        }
        return commentResponseUtils.convert(fixedComment);
    }

    @Override
    public CommentResDto updateComment(Long commentId, CommentReqPutDto reqDto, MultipartFile files) {

        Comment comment = commentRepository.findOneById(commentId);

        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

        if (comment == null || !postService.checkRightAccessPost(comment.getPost(), user)) {
            throw new AppException(ErrorCode.COMMENT_NOTEXISTED);
        }

        if (commentRepository.findByUserAndId(user, commentId) == null) {
            throw new AppException(ErrorCode.COMMENT_NOT_RIGHT);
        }

        if (!checkDataUpdateOK(comment, reqDto, files)) {
            throw new AppException(ErrorCode.COMMENT_UPLOAD_WRONG);
        }

        comment.setContent(reqDto.getContent());
        Comment updateComment = commentRepository.save(comment);

        // delete
        if (reqDto.getDeleteImageId() != null) {
            fileService.deleteById(reqDto.getDeleteImageId());
            updateComment.setFileUpload(null);
        }

        if (files != null) {
            MultipartFile[] file = { files };
            List<FileResDto> imageResDto = fileService.uploadFile(file);
            if (imageResDto.size() > 0) {

                FileUpload fileE = fileService.findOneById(imageResDto.get(0).getId());
                fileE.setComment(comment);
                fileService.save(fileE);
                updateComment.setFileUpload(fileE);
            }
        }
        return commentResponseUtils.convert(updateComment);
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findOneById(id);

        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

        if (comment == null || !postService.checkRightAccessPost(comment.getPost(), user)) {
            throw new AppException(ErrorCode.COMMENT_NOTEXISTED);
        }

        if (commentRepository.findByUserAndId(user, comment.getId()) == null) {
            throw new AppException(ErrorCode.COMMENT_NOT_RIGHT);
        }
        commentRepository.delete(comment);
    }

    @Override
    public Page<CommentResDto> getAllComment(Long id, Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 10);
        Post post = postRepository.findOneById(id);

        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

        if (post == null || !postService.checkRightAccessPost(post, user)) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }
        Page<Comment> pagedResult = commentRepository.findByPost(post, paging);
        List<CommentResDto> commentResponseList = pagedResult.stream().map(commentResponseUtils::convert)
                .collect(Collectors.toList());
        return new PageImpl<>(commentResponseList, paging, commentResponseList.size());
    }

    /**
     * check if data input update comment is ok
     *
     * @param comment {@link Comment}
     * @param reqDto  data input
     * @param files   file upload
     * @return true if data input ok, false otherwise
     */
    private boolean checkDataUpdateOK(Comment comment, CommentReqPutDto reqDto, MultipartFile files) {
        if (checkDeleteImage(comment, reqDto)) {
            throw new AppException(ErrorCode.DELETE_IMAGE_COMMENT_WRONG);
        }
        // case only have content, if update no content and no upload image will be
        // error
        if ((comment.getFileUpload() == null)) {
            if (files == null && StringUtils.isBlank(reqDto.getContent())) {
                return false;
            }
        } else {
            if (files == null && reqDto.getDeleteImageId() != null && StringUtils.isBlank(reqDto.getContent())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDeleteImage(Comment comment, CommentReqPutDto reqDto) {
        if (reqDto.getDeleteImageId() != null && comment.getFileUpload() == null) {
            return true;
        }

        if (reqDto.getDeleteImageId() != null && comment.getFileUpload() != null
                && reqDto.getDeleteImageId() != comment.getFileUpload().getId()) {
            return true;
        }

        return false;
    }
}

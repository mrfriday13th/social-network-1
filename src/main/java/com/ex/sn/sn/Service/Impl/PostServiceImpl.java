package com.ex.sn.sn.Service.Impl;
import com.ex.sn.sn.DTO.Post.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import com.cloudinary.utils.StringUtils;
import com.ex.sn.sn.DTO.FileUpload.FileResDto;
import com.ex.sn.sn.DTO.Utils.Post.PostResponseUtils;
import com.ex.sn.sn.Entity.FileUpload;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Entity.User;
import com.ex.sn.sn.Exception.AppException;
import com.ex.sn.sn.Exception.ErrorCode;
import com.ex.sn.sn.Mapper.Post.PostRequestMapper;
import com.ex.sn.sn.Repository.PostRepository;
import com.ex.sn.sn.Repository.UserRepository;
import com.ex.sn.sn.Service.FileService;
import com.ex.sn.sn.Service.PostService;
import com.ex.sn.sn.Utils.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRequestMapper postMapper;

    private final FileService fileService;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final PostResponseUtils postResponseUtils;

    /**
     * create post
     */
    @Override
    public PostPostResDto createPost(PostPostReqDto reqDto, MultipartFile[] files) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userEntity = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        // validate input
        if (StringUtils.isBlank(reqDto.getContent()) && (files == null || files.length < 1)) {
            throw new AppException(ErrorCode.POST_UPLOAD_WRONG);
        }

        Post post = postMapper.dtoToEntity(reqDto);

        post.setUser(userEntity);

        Post insertedPost = postRepository.save(post);
        // đẩy file lên cloudnary
        if (files != null && files.length > 0) {
            List<FileUpload> fileList = new ArrayList<>();
            List<FileResDto> fileResDto = fileService.uploadFile(files);
            if (!CollectionUtils.isEmpty(fileResDto)) {
                fileResDto.stream().forEach(imageDto -> {
                    FileUpload fileUpload = fileService.findOneById(imageDto.getId());
                    fileUpload.setPost(post);
                    fileService.save(fileUpload);
                    fileList.add(fileUpload);

                });
            }
            insertedPost.setFileUploads(fileList);
        }

        return postResponseUtils.convert(insertedPost);
    }

    /**
     * update post
     */
    @Override
    public PostPostResDto update(Long postId, PostPutReqDto reqDto, MultipartFile[] files) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        Post post = postRepository.findOneById(postId);

        if (post == null || !checkRightAccessPost(post, user)) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }
        // check if user have right to update the post
        if (postRepository.findByUserAndId(user, post.getId()) == null) {
            throw new AppException(ErrorCode.POST_NOT_RIGHT);
        }

        // check data input Post match condition: at least have content or image
        if (!checkDataUpdateOK(post, reqDto, files)) {
            throw new AppException(ErrorCode.POST_UPLOAD_WRONG);
        }

        post.setContent(reqDto.getContent());
        post.setStatus(reqDto.getStatus());

        // delete image from post
        if (!CollectionUtils.isEmpty(reqDto.getListImageIdDeletes())) {
            reqDto.getListImageIdDeletes().stream().forEach(id -> {
                post.getFileUploads().remove(fileService.findOneById(id));
                fileService.deleteById(id);
            });
        }

        Post insertedPost = postRepository.save(post);

        // upload image to cloudinary
        if (files != null && files.length > 0) {
            List<FileUpload> fileList = new ArrayList<>();
            List<FileResDto> fileResDto = fileService.uploadFile(files);
            if (!CollectionUtils.isEmpty(fileResDto)) {
                fileResDto.stream().forEach(imageDto -> {
                    FileUpload fileUpload = fileService.findOneById(imageDto.getId());
                    fileUpload.setPost(post);
                    fileService.save(fileUpload);
                    fileList.add(fileUpload);
                });
            }
            insertedPost.getFileUploads().addAll(fileList);
        }
        return postResponseUtils.convert(insertedPost);
    }

    /**
     * delete post
     */
    @Override
    public DeletePostResDto delete(Long id) {
        Post post = postRepository.findOneById(id);

        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        if (post == null || !checkRightAccessPost(post, user))
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        if (postRepository.findByUserAndId(user, id) == null) {
            throw new AppException(ErrorCode.POST_NOT_RIGHT);
        }
        postRepository.deleteById(id);

        return new DeletePostResDto(id);
    }

    /**
     * get detail of post by id
     */
    @Override
    public PostPostResDto getPostDetail(Long Id) {
        Post post = postRepository.findOneById(Id);

        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

        if (post == null || !checkRightAccessPost(post, user)) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }

        PostPostResDto resDto = postResponseUtils.convert(post);
        return resDto;
    }

    /**
     * get all post of user by id
     */
    @Override
    public Page<PostPostResDto> getUserAllPost(Long id, Integer pageNo) {
        // Tạo đối tượng phân trang
        Pageable paging = PageRequest.of(pageNo, 10);
        // Tìm user theo ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        // Lấy info user hiện tại từ context bảo mật.
        User userR = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        // Lấy các post của user với phân trang
        Page<Post> pagedResult = postRepository.findByUser(user, paging);
        // Lọc các post theo role của user và chuyển đổi sang DTO.
        List<PostPostResDto> postResponseList = pagedResult.stream()
                .filter(post -> checkRightAccessPost(post, userR))
                .map(postResponseUtils::convert)
                .collect(Collectors.toList());
        return new PageImpl<>(
                postResponseList, paging, postResponseList.size());
    }

    /**
     * Lấy tất cả các bài đăng chứa nội dung tìm kiếm.
     *
     * @param pageNo
     * @param searchContent
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public Page<PostPostResDto> getAllPostByKeyword(Integer pageNo, String searchContent) {

        Pageable paging = PageRequest.of(pageNo, 10);

        // Lấy list post dựa trên nội dung tìm kiếm hoặc tất cả post nếu nội dung tìm kiếm rỗng.
        Page<Post> pagedResult = StringUtils.isBlank(searchContent) ?
                postRepository.findAll(paging) :
                postRepository.findByContentContains(searchContent, paging);

        // Lấy thông tin user hiện tại từ context bảo mật.
        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

        // Lọc các post theo role của user và chuyển đổi sang DTO.
        List<PostPostResDto> postResponseList = pagedResult.stream()
                .filter(post -> checkRightAccessPost(post, user))
                .map(postResponseUtils::convert)
                .collect(Collectors.toList());


        return new PageImpl<>(postResponseList, paging, pagedResult.getTotalElements());
    }


    /**
     * update privacy of the post
     */
    @Override
    public PostPostResDto updatePrivacy(Long postId, PostStatusPutReqDto reqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        Post post = postRepository.findByUserAndId(user, postId);
        if (post == null) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }
        post.setStatus(reqDto.getStatus());
        postRepository.save(post);

        return postResponseUtils.convert(post);
    }

    /**
     * check if user have right with post
     */
    @Override
    public boolean checkRightAccessPost(Post post, User user) {
        return !(post.getUser().getId() != user.getId() && post.getStatus() == Post.Status.PRIVATE);
    }

    /**
     * check if data update post is ok
     *
     * @param post   {@link Post}
     * @param reqDto {@link PostPutReqDto}
     * @param files
     * @return true if ok
     */
    private boolean checkDataUpdateOK(Post post, PostPutReqDto reqDto, MultipartFile[] files) {
        // check if delete image not in post
        if (checkDeleteImageLink(post, reqDto)) {
            throw new AppException(ErrorCode.DELETE_IMAGE_POST_WRONG);
        }

        // if have only content, input empty content and have not image will be error
        if (CollectionUtils.isEmpty(post.getFileUploads())) {
            if (StringUtils.isBlank(reqDto.getContent()) && files == null) {
                return false;
            }
        } else {
            // if delete all image of post and content empty will be error
            if (StringUtils.isBlank(reqDto.getContent()) && files == null && (reqDto.getListImageIdDeletes() != null
                    && post.getFileUploads().size() == reqDto.getListImageIdDeletes().size())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDeleteImageLink(Post post, PostPutReqDto reqDto) {
        // delete link that
        if (CollectionUtils.isEmpty(post.getFileUploads()) && !CollectionUtils.isEmpty(reqDto.getListImageIdDeletes())) {
            return true;
        }

        // check delete link is wrong
        if (!CollectionUtils.isEmpty(post.getFileUploads()) && !CollectionUtils.isEmpty(reqDto.getListImageIdDeletes())) {
            boolean isExistFile = false;
            for (Long id : reqDto.getListImageIdDeletes()) {
                for (FileUpload file : post.getFileUploads()) {
                    if (file.getId().equals(id)) {
                        isExistFile = true;
                    }
                }
                if (!isExistFile) {
                    return true;
                }
            }
        }
        return false;
    }
}

package com.ex.sn.sn.Service.Impl;

import com.ex.sn.sn.Mapper.Post.PostRequestMapper;
import com.ex.sn.sn.Repository.UserRepository;
import com.ex.sn.sn.Service.FileService;
import com.ex.sn.sn.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRequestMapper postMapper;

    private final FileService fileService;

//    private final ImageService imageService;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final PostResponseUtils postResponseUtils;

    /**
     * create post
     */
    @Override
    public PostPostResDto createPost(PostPostReqDto reqDto, MultipartFile[] files) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfoEntity = userInfoRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        // validate input
        if (StringUtils.isBlank(reqDto.getContent()) && (files == null || files.length < 1)) {
            throw new AppException(ErrorCode.POST_UPLOAD_WRONG);
        }

        Post post = postMapper.dtoToEntity(reqDto);

        post.setUserInfo(userInfoEntity);

        Post insertedPost = postRepository.save(post);
        // add image to cloudnary
        if (files != null && files.length > 0) {
            List<Image> imageList = new ArrayList<>();
            List<ImageResDto> imageResDto = fileService.uploadImage(files);
            if (!CollectionUtils.isEmpty(imageResDto)) {
                imageResDto.stream().forEach(imageDto -> {
                    Image imageE = imageService.findOneById(imageDto.getId());
                    imageE.setPost(post);
                    imageService.save(imageE);
                    imageList.add(imageE);

                });
            }
            insertedPost.setImages(imageList);
        }

        return postResponseUtils.convert(insertedPost);
    }

    /**
     * update post
     */
    @Override
    public PostPostResDto update(Long postId, PostPutReqDto reqDto, MultipartFile[] files) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserInfo user = userInfoRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        Post post = postRepository.findOneById(postId);

        if (post == null || !checkRightAccessPost(post, user)) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }
        // check if user have right to update the post
        if (postRepository.findByUserInfoAndId(user, post.getId()) == null) {
            throw new AppException(ErrorCode.POST_NOT_RIGHT);
        }

        // check data input Post match condition: at least have content or image
        if (!checkDataUpdateOK(post, reqDto, files)) {
            throw new AppException(ErrorCode.POST_UPLOAD_WRONG);
        }

        post.setContent(reqDto.getContent());
        post.setPrivacy(reqDto.getPrivacy());

        // delete image from post
        if (!CollectionUtils.isEmpty(reqDto.getListImageIdDeletes())) {
            reqDto.getListImageIdDeletes().stream().forEach(id -> {
                post.getImages().remove(imageService.findOneById(id));
                imageService.deleteById(id);
            });
        }

        Post insertedPost = postRepository.save(post);

        // upload image to cloudinary
        if (files != null && files.length > 0) {
            List<Image> imageList = new ArrayList<>();
            List<ImageResDto> imageResDto = fileService.uploadImage(files);
            if (!CollectionUtils.isEmpty(imageResDto)) {
                imageResDto.stream().forEach(imageDto -> {
                    Image imageE = imageService.findOneById(imageDto.getId());
                    imageE.setPost(post);
                    imageService.save(imageE);
                    imageList.add(imageE);
                });
            }
            insertedPost.getImages().addAll(imageList);
        }
        return postResponseUtils.convert(insertedPost);
    }

    /**
     * delete post
     */
    @Override
    public DeletePostResDto delete(Long id) {
        Post post = postRepository.findOneById(id);

        UserInfo userInfor = userInfoRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        if (post == null || !checkRightAccessPost(post, userInfor))
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        if (postRepository.findByUserInfoAndId(userInfor, id) == null) {
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

        UserInfo userInfor = userInfoRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

        if (post == null || !checkRightAccessPost(post, userInfor)) {
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
        Pageable paging = PageRequest.of(pageNo, 10);
        UserInfo user = userInfoRepository.findOneById(id);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        Page<Post> pagedResult = postRepository.findByUserInfo(user, paging);
        UserInfo userInfor = userInfoRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        List<PostPostResDto> postResponseList = pagedResult.stream()
                .filter(post -> checkRightAccessPost(post, userInfor)).map(postResponseUtils::convert)
                .collect(Collectors.toList());
        return new PageImpl<>(postResponseList, paging, postResponseList.size());
    }

    /**
     * get post contain a input content
     */
    @Override
    public Page<PostPostResDto> getAllPostByKeyword(Integer pageNo, String searchContent) {
        Pageable paging = PageRequest.of(pageNo, 10);
        Page<Post> pagedResult;
        if (StringUtils.isBlank(searchContent)) {
            pagedResult = postRepository.findAll(paging);
        } else {
            pagedResult = postRepository.findByContentContains(searchContent, paging);
        }
        UserInfo userInfor = userInfoRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        List<PostPostResDto> postResponseList = pagedResult.stream()
                .filter(post -> checkRightAccessPost(post, userInfor)).map(postResponseUtils::convert)
                .collect(Collectors.toList());
        return new PageImpl<>(postResponseList, paging, postResponseList.size());
    }

    /**
     * update privacy of the post
     */
    @Override
    public PostPostResDto updatePrivacy(Long postId, PostPrivacyPutReqDto reqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserInfo user = userInfoRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        Post post = postRepository.findByUserInfoAndId(user, postId);
        if (post == null) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }
        post.setPrivacy(reqDto.getPrivacy());
        postRepository.save(post);

        return postResponseUtils.convert(post);
    }

    /**
     * check if user have right with post
     */
    @Override
    public boolean checkRightAccessPost(Post post, UserInfo user) {
        return !(post.getUserInfo().getId() != user.getId() && post.getPrivacy() == PostType.ONLY_ME.getCode());
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
        if (CollectionUtils.isEmpty(post.getImages())) {
            if (StringUtils.isBlank(reqDto.getContent()) && files == null) {
                return false;
            }
        } else {
            // if delete all image of post and content empty will be error
            if (StringUtils.isBlank(reqDto.getContent()) && files == null && (reqDto.getListImageIdDeletes() != null
                    && post.getImages().size() == reqDto.getListImageIdDeletes().size())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDeleteImageLink(Post post, PostPutReqDto reqDto) {
        // delete link that
        if (CollectionUtils.isEmpty(post.getImages()) && !CollectionUtils.isEmpty(reqDto.getListImageIdDeletes())) {
            return true;
        }

        // check delete link is wrong
        if (!CollectionUtils.isEmpty(post.getImages()) && !CollectionUtils.isEmpty(reqDto.getListImageIdDeletes())) {
            boolean isExistImage = false;
            for (Long id : reqDto.getListImageIdDeletes()) {
                for (Image image : post.getImages()) {
                    if (image.getId().equals(id)) {
                        isExistImage = true;
                    }
                }
                if (!isExistImage) {
                    return true;
                }
            }
        }
        return false;
    }
}

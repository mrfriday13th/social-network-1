package com.ex.sn.sn.Service;

import com.ex.sn.sn.DTO.InforUser.InforUserReqDto;
import com.ex.sn.sn.DTO.InforUser.InforUserResDto;
import com.ex.sn.sn.DTO.User.UserPutReqDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    InforUserResDto updateInfo(UserPutReqDto reqDto, MultipartFile[] multipartFile);

    UserInforResDto getUserInformation();

    Page<UserInforResDto> searchUserByName(Integer pageNo, String content);

    void updateUserInfo(UserInfo userInfo);

    UserInfo findOneById(Long id);

    UserInforResDto findDetailUser(Long id);
}

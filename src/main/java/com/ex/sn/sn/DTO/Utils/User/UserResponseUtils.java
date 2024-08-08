package com.ex.sn.sn.DTO.Utils.User;

import com.ex.sn.sn.DTO.User.UserResDto;
import com.ex.sn.sn.DTO.Utils.ResponseUtilsAdapter;
import com.ex.sn.sn.Entity.User;
import com.ex.sn.sn.Mapper.FileUpload.FileUploadResponseMapper;
import com.ex.sn.sn.Mapper.User.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UserResponseUtils extends ResponseUtilsAdapter<User, UserResDto> {
    private final UserMapper userMapper;

    @Override
    public UserResDto convert(User obj) {
        UserResDto userResponse = userMapper.entityToDto(obj);
        return userResponse;
    }

    @Override
    public List<UserResDto> convert(List<User> obj) {
        List<UserResDto> userResponseList = new ArrayList<>();
        obj.stream().forEach(o -> {
            UserResDto userResponse = userMapper.entityToDto(o);
            userResponseList.add(userResponse);
        });
        return userResponseList;
    }
}
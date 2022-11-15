package com.autumn.learn.mvc.controller;

import com.autumn.learn.mvc.domain.ResultMessage;
import com.autumn.learn.mvc.domain.User;
import com.autumn.learn.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author yl
 * @since 2022-11-12 20:49
 */
@RequestMapping("/v2")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/find_user", method = RequestMethod.GET)
    @ResponseBody
    public ResultMessage<User> findUser(@RequestParam("id") Integer userId) {
        User user = new User();
        user.setUserId(userId);
        user = userService.findUser(user);
        if (user == null) {
            return new ResultMessage<>(400, "未查询到用户信息，请检查工号是否正确！", null);
        }
        return new ResultMessage<>(200, "query successfully！", user);
    }
}

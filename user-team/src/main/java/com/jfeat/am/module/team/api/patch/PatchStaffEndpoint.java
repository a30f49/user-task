package com.jfeat.am.module.team.api.patch;

import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.team.api.model.PowerBusinessCode;
import com.jfeat.am.module.team.api.model.PowerBusinessException;
import com.jfeat.am.module.team.services.crud.service.StaffService;
import com.jfeat.am.module.team.services.domain.model.UniversalName;
import com.jfeat.am.module.team.services.domain.service.QueryStaffService;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUD;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Code Generator on 2017-11-20
 * @author Administrator
 */
@Api(value = "注册")
@RestController
@RequestMapping("/api")
public class PatchStaffEndpoint  {


    @Resource
    StaffService staffService;
    @Resource
    QueryStaffService queryStaffService;

    @ApiOperation(value = "获取当前登录用户")
    @GetMapping("/app/staff/profile")
    public Tip getCurrentStaff(){
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if(staff==null){
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamLoginUserNoStaff);
        }

        StaffNameModel model = CRUD.castObject(staff, StaffNameModel.class);
        model.setStaffName(new UniversalName(staff.getFirstName(), staff.getLastName()).toString());

        return SuccessTip.create(model);
    }

    @ApiOperation(value = "个人中心")
    @GetMapping("/app/staff/center")
    public Tip center(){
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if(staff==null){
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamLoginUserNoStaff);
        }
        return SuccessTip.create(queryStaffService.selfCenterRecord(staff.getId()));
    }
}



   /* @ApiOperation("登录")
    @PostMapping({"/staff/login"})
    public Tip login(@Valid @RequestBody StaffRegister login) {
        Subject currentUser = ShiroKit.getSubject();
        Object authenticationToken = null;
        if(login.getLoginType() == Const.LOGIN_TYPE_WECHAT) {
            AuthResult shiroUser = SnsAuthApi.validateAccessToken(login.getAccessToken(), login.getOpenid());
            logger.debug(shiroUser.getJson());
            if(!shiroUser.isSucceed()) {
                return ErrorTip.create(BizExceptionEnum.LOGIN_FAIL);
            }

            User token = this.userService.getByOpenid(login.getOpenid());
            if(token == null) {
                token = new User();
                token.setPassword(RandomStringUtils.randomAlphabetic(10));
                token.setAccount(login.getAccount());
                token.setOpenid(login.getOpenid());
                token.setTenantId(login.getTenantId());
                token.setName(login.getName());
                token.setAvatar(login.getAvatar());
                this.userService.saveUser(token, (List)null);
            }

            authenticationToken = new AccessTokenToken(token.getAccount(), token.getPassword());
        } else {
            authenticationToken = new UsernamePasswordToken(login.getAccount(), login.getPassword().toCharArray());
        }

        try {
            currentUser.login((AuthenticationToken)authenticationToken);
        } catch (AuthenticationException var7) {
            logger.warn("login failed: {}", var7.getMessage());
            return ErrorTip.create(BizExceptionEnum.LOGIN_FAIL);
        }

        ShiroUser shiroUser1 = ShiroKit.getUser();
        String token1 = JWTService.me().createToken(shiroUser1.getTenantId(), shiroUser1.getId(), shiroUser1.getAccount());
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(token1);
        accessToken.setExpiresIn(JWTService.me().getExpiresIn());
        accessToken.setTokenType(JWTService.me().getTokenType());
        return SuccessTip.create(accessToken);
    }*/


       /*//为员工添加角色
    @PostMapping("/team/{id}/roles")
    public Tip createStaffrole(@PathVariable long id,@RequestBody StaffRole entity) {
        // 需要判断是否已经存在在StaffRoles表中
        rolePatchService.isStaffInRole(entity.getRoleId(),id);
        entity.setStaffId(id);
        return SuccessTip.create(staffRoleService.createMaster(entity));
    }

    //移除员工某个角色
    @DeleteMapping("/team/roles/{id}")
    public Tip deleteStaffrole(@PathVariable Long id) {
        return SuccessTip.create(staffRoleService.deleteMaster(id));
    }
   */

    /*// 批量将员工从某个Roles 移除
    @PostMapping("/team/teams/role/{roleId}/bulk/remove")
    public Tip batchDeleteStaffToRole(@PathVariable long roleId, @RequestBody Ids ids){
        return SuccessTip.create(rolePatchService.batchDeleteStaffToRole(roleId,ids.getIds()));
    }

    // 批量将员工放到某个Roles
    @PostMapping("/team/role/{roleId}/bulk/staffs")
    public Tip batchAddStaffToRole(@PathVariable long roleId, @RequestBody Ids ids){
        return SuccessTip.create(rolePatchService.batchAddStaffToRole(roleId,ids.getIds()));
    }*/

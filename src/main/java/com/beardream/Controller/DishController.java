package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.DishMapper;
import com.beardream.ioc.*;
import com.beardream.model.*;
import com.beardream.service.CollectionService;
import com.beardream.service.DishService;
import com.beardream.service.NutritionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laxzh on 2017/5/6.
 * 菜品控制器
 */
@RestController
@RequestMapping("/api/mobile/dish")
@Api(value = "菜品服务",description = "提供RESTful风格API的菜品的增删改查服务")
public class DishController {

    @Autowired
    private DishService mDishService;

    @Autowired
    private NutritionService mNutritionService;

    @Autowired
    private CollectionService mCollectionService;


    @ApiOperation("获取单个菜品信息")
    @RequestMapping(value = "/{dishId}", method = RequestMethod.GET)
    public Result get(@PathVariable Integer dishId, HttpSession session){
        if (!TextUtil.isEmpty(dishId))
            return ResultUtil.error(-1,"菜品id不能为空");
        System.out.println(dishId);
        Dish dish = mDishService.find(dishId);

        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);

        if (user != null){
            UserCollection userCollection = new UserCollection(user.getUserId(), dishId, 1);
            userCollection = mCollectionService.queryBusinessDishCollect(userCollection);
            dish.setCollectionId(userCollection.getCollectionId());
        }
        return ResultUtil.success(dish);
    }

    @ApiOperation("添加菜品")
    @PostMapping
    public Result post(Dish dish){
        System.out.println(dish.getDishId());
        return ResultUtil.success(mDishService.post(dish));
    }

    @ApiOperation("删除菜品")
    @DeleteMapping
    @PermissionMethod(text = "删除菜品")
    public Result delete(Dish dish){
        System.out.println(dish.getDishId());
        return ResultUtil.success(mDishService.delete(dish));
    }

    @ApiOperation("更新菜品")
    @PutMapping
    public Result put(Dish dish, Nutrition nutrition){
        System.out.println(dish.getDishId());
        mNutritionService.put(nutrition);
        return ResultUtil.success(mDishService.put(dish));
    }

    @ApiOperation("分页查询菜品推荐")
    @GetMapping("/recommend")
    public Result getPage(Dish dish,
                          @RequestParam(value = "pageNum", required = false)  int pageNum,
                          @RequestParam(value = "pageSize", required = false)  int pageSize){
        return ResultUtil.success(mDishService.getPage(dish, pageNum, pageSize));
    }
}

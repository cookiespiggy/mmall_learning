package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    // 对于前端,保存和更新是两个操作,但是对于后台,可以用一个接口来处理,判断一下是保存还是更新
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {

            if (StringUtils.isNoneBlank(product.getSubImages())) {
                // 取第一个子图作为主图
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }

                if (product.getId() != null) {
                    // 更新
                    int rowCount = productMapper.updateByPrimaryKey(product);
                    if (rowCount > 0) {
                        return ServerResponse.createBySuccess("更新产品成功");
                    } else {
                        return ServerResponse.createByErrorMessage("更新产品失败");
                    }
                } else {
                    int rowCount = productMapper.insert(product);
                    if (rowCount > 0) {
                        return ServerResponse.createBySuccess("新增产品成功");
                    } else {
                        return ServerResponse.createByErrorMessage("新增产品失败");
                    }

                }
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }
}

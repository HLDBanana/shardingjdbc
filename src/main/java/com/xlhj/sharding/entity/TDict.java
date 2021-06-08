package com.xlhj.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Han LiDong
 * @since 2021-05-26
 */
@Data
//@TableName("t_dict")
@ApiModel(value="TDict对象", description="")
public class TDict extends Model<TDict> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "编码")
    private String dicCode;

    @ApiModelProperty(value = "字典名")
    private String dicName;

    @ApiModelProperty(value = "字典值")
    private String dicValue;

    @ApiModelProperty(value = "父编码")
    private String pcode;

    @ApiModelProperty(value = "0：失效 1：生效")
    private String status;

    @ApiModelProperty(value = "排序")
    private String dicSort;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}

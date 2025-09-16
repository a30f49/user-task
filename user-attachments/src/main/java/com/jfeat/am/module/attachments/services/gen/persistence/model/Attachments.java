package com.jfeat.am.module.attachments.services.gen.persistence.model;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Generator
 * @since 2019-04-08
 */
public class Attachments extends Model<Attachments> {

    @TableField(exist = false)
    private com.alibaba.fastjson.JSONObject extra;

    public com.alibaba.fastjson.JSONObject getExtra() {
        return extra;
    }
    public void setExtra(com.alibaba.fastjson.JSONObject extra) {
        this.extra = extra;
    }


    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 附件名
     */
	private String name;
    /**
     * 附件url
     */
	private String url;
    /**
     * 所属的表
     */
	@TableField("table_name")
	private String tableName;
    /**
     * 所属表里的id
     */
	@TableField("reference_id")
	private Long referenceId;
	/**
	 * 创建时间
	 */
	@TableField("create_date")
	private Date createDate;

	public Long getId() {
		return id;
	}

	public Attachments setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Attachments setName(String name) {
		this.name = name;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Attachments setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getTableName() {
		return tableName;
	}

	public Attachments setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public Attachments setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
		return this;
	}

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String URL = "url";

	public static final String TABLE_NAME = "table_name";

	public static final String REFERENCE_ID = "reference_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Attachments{" +
			"id=" + id +
			", name=" + name +
			", url=" + url +
			", tableName=" + tableName +
			", referenceId=" + referenceId +
			"}";
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}

package cn.techaction.pojo;

import java.util.Date;
import java.util.List;

public class ActionParam {
	private Integer id;
	private Integer parent_id;
	private String name;
	private boolean status;
	private Integer sort_order;
	private Integer level;
	private Date created;
	private Date updated;
	private List<ActionParam> children;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public boolean getStatus() {
		return status;
	}
	public Integer getSort_order() {
		return sort_order;
	}
	public void setSort_order(Integer sort_order) {
		this.sort_order = sort_order;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public List<ActionParam> getChildren() {
		return children;
	}
	public void setChildren(List<ActionParam> children) {
		this.children = children;
	}
	
	
//	public List<ActionParam> getChildren(){
//		return  children;
//	}
//	public void setChildren(List<ActionParam> children) {
//		this.children = children;
//	}
//	public ActionParam() {
//		super();
//		//TODO Auto-generated constructor stub
//	}
//	public ActionParam(int id,int parent_id,String name,boolean status,int sort_order,int level,Date created,
//			Date updated) {
//		super();
//		this.id=id;
//		this.parent_id=parent_id;
//		this.name=name;
//		this.status=status;
//		this.sort_order=sort_order;
//		this.level=level;
//		this.created=created;
//		this.updated=updated;
//	}
	
	

	
}

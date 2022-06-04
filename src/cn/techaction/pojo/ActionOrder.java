package cn.techaction.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ActionOrder {
	private Integer id;
	private Long orderNo;
	private Integer Uid;
	private Integer addrid;
	private BigDecimal ammount;
	private Integer type;
	private Integer freight;
	private Integer status;
	private Date payment_time;
	private Date delivery_time;
	private Date finish_time;
	private Date close_time;
	private Date updated;
	private Date created;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getUid() {
		return Uid;
	}
	public void setUid(Integer uid) {
		Uid = uid;
	}
	public Integer getAddrid() {
		return addrid;
	}
	public void setAddrid(Integer addrid) {
		this.addrid = addrid;
	}
	public BigDecimal getAmmount() {
		return ammount;
	}
	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getFreight() {
		return freight;
	}
	public void setFreight(Integer freight) {
		this.freight = freight;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getPayment_time() {
		return payment_time;
	}
	public void setPayment_time(Date payment_time) {
		this.payment_time = payment_time;
	}
	public Date getDelivery_time() {
		return delivery_time;
	}
	public void setDelivery_time(Date delivery_time) {
		this.delivery_time = delivery_time;
	}
	public Date getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}
	public Date getClose_time() {
		return close_time;
	}
	public void setClose_time(Date close_time) {
		this.close_time = close_time;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
}

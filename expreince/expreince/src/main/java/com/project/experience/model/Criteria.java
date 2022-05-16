package com.project.experience.model;

import java.util.Arrays;

/* 페이징, 키워드 검색 */
public class Criteria {
	
	/* 현재 페이지 */
	private int pageNum;
	
	/* 한 페이지 당 보여질 게시물 갯수 */
	private int amount;
	
	/* 스킵할 게시물 수 ((pageNum - 1) * amount) 
	 * ex) (pageNum - 1) * amount = 10, amount = 10, 10개를 건너뛰고 10개를 보여준다 */
	private int skip;
	
	/* 검색어 키워드 */
	private String keyword;
	
	/* 검색 타입(뷰에서 선택) */
	private String type;
	
	/* 검색 타입 배열(type을 배열로 변환) */
	private String[] typeArr;
	
	/* 기본세팅 pageNum = 1, amount = 10, 기본생성자 */
	public Criteria() {
		this(1,10);
	}
	
	/* 원하는 pageNum, 원하는 amount */
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.skip = (pageNum - 1) * amount;
	}

	public int getPageNum() {
		return pageNum;
	}

	// 새로 페이지 숫자를 설정했을 때 skip도 계산
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		this.skip = (pageNum-1) * amount;
	}

	public int getAmount() {
		return amount;
	}

	// 페이지당 갯수를 바꿀경우에도 skip을 다시 계산
	public void setAmount(int amount) {
		this.amount = amount;
		this.skip = (this.pageNum-1)*amount;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		this.typeArr = type.split(""); // 타입을 정하면 타입배열에 문자열을 분리해서 배열로 입력되게 함
	}

	public String[] getTypeArr() {
		return typeArr;
	}

	public void setTypeArr(String[] typeArr) {
		this.typeArr = typeArr;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", skip=" + skip + ", keyword=" + keyword
				+ ", type=" + type + ", typeArr=" + Arrays.toString(typeArr) + "]";
	}
	
}

package com.project.experience.model;

/* 페이지네이션 */
public class PageMaker {
	
	/* 시작 페이지 */
	private int startPage;
	
	/* 끝 페이지 */
	private int endPage;
	
	/* 이전, 다음 페이지 존재유뮤 */
	private boolean prev, next;
	
	/* 전체 게시물 수 */
	private int total;
	
	/* 현재 페이지, 페이지당 게시물 표시 수 정보 */
	private Criteria cri;

	public PageMaker(Criteria cri, int total) {
		// 현재 표시할 페이지, 한 페이지에 표시할 숫자, 전체 게시물 수
		this.total = total;
		this.cri = cri;
		
		/* 마지막 페이지 10 단위로 표시 Math.ceil => 소수점 이하 올림*/
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0)) * 10;
		
		/* 시작 페이지 */
		this.startPage = this.endPage - 9;
		
		/* 실제 마지막 페이지 */
		int realEnd = (int)(Math.ceil(total * 1.0/cri.getAmount()));
		
		/* 전체 마지막 페이지보다 화면에 보이는 마지막 페이지가 작은 경우에는 마지막 페이지를 전체 마지막 페이지의 값으로 조정 */
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		/* 이전페이지는 시작 페이지가 1 이상이어야 true */
		this.prev = this.startPage > 1; 
		
		/* 다음페이지는 전체 마지막 페이지가 마지막 페이지보다 작아야 true*/
		this.next = this.endPage < realEnd;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
}

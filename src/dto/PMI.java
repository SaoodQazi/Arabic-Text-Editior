package dto;

public class PMI {
	private int id;
	private int pageId;
	private String word;
	private int pmiScore;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getPmiScore() {
		return pmiScore;
	}

	public void setPmiScore(int pmiScore) {
		this.pmiScore = pmiScore;
	}
}

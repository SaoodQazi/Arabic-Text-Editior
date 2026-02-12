package dto;

public class Stemming {
	private int id;
	private int pageId;
	private String word;
	private String stem;

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

	public String getStem() {
		return stem;
	}

	public void setStem(String pos) {
		this.stem = pos;
	}
}

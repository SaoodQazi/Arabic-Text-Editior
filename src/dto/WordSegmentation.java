package dto;

public class WordSegmentation {
	private int id;
	private int pageId;
	private String word;
	private String segment;

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

	public String getSegment() {
		return segment;
	}

	public void setSegment(String pos) {
		this.segment = pos;
	}

}
